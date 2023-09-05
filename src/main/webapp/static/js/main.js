


document.addEventListener("DOMContentLoaded", function() {
    PromotionLoader.setPromotion();
    CategoryLoader.init();
  });

/*
    프로모션 관련 ajax 호출 및 작업 초기화
     - 프모션이 한 번 움직일 때마다, ajax 호출로 미리 2개 이미지 조회
     - 2개 이미지란 프로모션 메인 이미지와 다음에 보여질 이미지
*/

const PromotionLoader = {
    setPromotion : function(){
        let oReq = new XMLHttpRequest();
        oReq.addEventListener("load", function() {
            if (oReq.status === 200) {
                items = JSON.parse(oReq.responseText).items;

                container = document.querySelector("#promotion_container").firstElementChild;
                let templateHtml = document.querySelector("#promotion-template").innerHTML;
                container.innerHTML += templateHtml.replace("{imgUrl}", items[0].productImageUrl);

                if (items.length > 1) {
                    templateHtml = document.querySelector("#promotion-template").innerHTML;
                    container.innerHTML += templateHtml.replace("{imgUrl}", items[1].productImageUrl);
                }


                requestAnimationFrame(PromotionLoader.movePromotion.bind(PromotionLoader));
            } else {
                alert("Promotion error occurred. Ajax error code: " + oReq.status);
            }
        });
        let url = "./api/promotions";
        oReq.open("GET", url);
        oReq.send();
    },
    done : false, 
    start : undefined, 
    previousTimeStamp : undefined,
    movePromotion : function(timestamp) {
        if (this.start === undefined) {
            this.start = timestamp;
        }
        const elapsed = timestamp - this.start;
    
        if (this.previousTimeStamp !== timestamp) {
            const count = Math.min(0.1 * elapsed, container.clientWidth);
            container.style.transform = `translateX(-${count}px)`;
    
            if (count === container.clientWidth) done = true;
        }

        if (elapsed < container.clientWidth *10) {
            this.previousTimeStamp = timestamp;
            if (!this.done) {
                requestAnimationFrame(this.movePromotion.bind(this));
            }
        } else {
            let change = this.changePromotion.bind(this);
            change();
            this.start = this.undefined;
            this.previousTimeStamp = undefined;
            done = false;
            requestAnimationFrame(this.movePromotion.bind(this));
        }
    },
    /*
        프로모션 동작 후 다음 프로모션 템플릿 설정
        - 프로모션이 마지막에 도달하면 다시 처음 프로모션을 보이도록 설정
        - nextIdx가 2인 것은 초기화 작업 시 이미 2개의 이미지가 존재하기 때문에 2 설정
    */
    nextIdx : 2,
    changePromotion : function() {
        container = document.querySelector("#promotion_container").firstElementChild;
        container.removeChild(container.firstElementChild);
        let templateHtml = document.querySelector("#promotion-template").innerHTML;

        if (this.nextIdx === items.length) {
            this.nextIdx = 0;
        }
        container.innerHTML += templateHtml.replace("{imgUrl}", items[this.nextIdx].productImageUrl);
        this.nextIdx++;
    }
}

const CategoryLoader = {
    init : function() {
        CategoryLoader.initItemList(0);
        CategoryLoader.initItemCnt();
        CategoryLoader.initClickMoreBtn();
        CategoryLoader.initClickOtherCategory();
    },
    /*
        product item을 카테고리에 따라 4개씩 보이도록 초기화
        - 메인 페이지 접속 시 호출
        - 카테고리 탭 클릭 시 호출
        - 사라졌던 '더보기' 버튼 다시 보여주도록 설정
    */
    initItemList : function(categoryId) {
        let eventBox = document.querySelectorAll(".lst_event_box");
        eventBox[0].innerHTML = "";
        eventBox[1].innerHTML = "";
        let moreBtn = document.querySelector(".more").firstElementChild;
        moreBtn.style.display = "";
    
        CategoryService.addItems(categoryId, 0);
    }, 
    /*
        메인페이지 접속 시 카테고리 개수 표시
    */
    initItemCnt : function() {
        let oReq = new XMLHttpRequest();
        oReq.addEventListener("load", function() {
            if (oReq.status === 200) {
                let categoryCnt = JSON.parse(oReq.responseText).items;

                let totalItemCnt = 0;
                Object.keys(categoryCnt).forEach(key => {
                    totalItemCnt += categoryCnt[key].count;
                });

                document.querySelector(".pink").innerHTML = totalItemCnt + "개";
            } else {
                alert("Server item count error occured. Ajax error code: " + oReq.status);
            }
        });
        let url = "./api/categories";
        oReq.open("GET", url);
        oReq.send();
    },
    /*
        '더보기' 버튼 클릭 시 목록 추가
    */
    initClickMoreBtn : function() {
        let moreBtn = document.querySelector(".more").firstElementChild;
        moreBtn.addEventListener("click", function(evt) {
            let categoryId = document.querySelector(".active").parentElement.dataset.category;
            let eventBox = document.querySelectorAll(".lst_event_box");
            let start = eventBox[0].childElementCount + eventBox[1].childElementCount;
        
            CategoryService.addItems(categoryId, start);
        });
    },

    /*
        다른 카테고리 탭 클릭 시 화면 변경
        - event delegation을 이용하여 a 또는 span 가 클릭 시
        새 카테고리 아이템 다시 조회
        - active class 재설정
    */
    initClickOtherCategory : function() {
        let tabs = document.querySelector(".section_event_tab");
        tabs.addEventListener("click", function(evt){
            let tab = evt.target;
        
            if(tab.tagName === "A") {
                document.querySelector(".active").className = "anchor";
                tab.className += " active";
                let categoryId = document.querySelector(".active").parentElement.dataset.category;
                CategoryLoader.initItemList(categoryId);
            } else if(tab.tagName === "SPAN") {
                document.querySelector(".active").className = "anchor";
                tab.parentElement.className += " active"
        
                let categoryId = document.querySelector(".active").parentElement.dataset.category;
                CategoryLoader.initItemList(categoryId);
            }
        });
    }
}

const CategoryService = {

    /*
        categoryId와 start에 따른 아이템 추가
        - 새로운 카테고리 탭 클릭 시 start부터 시작하는 categoryId 아이템 호출
        - 새로운 카테고리 탭을 클릭하기 때문에 카테고리 개수 설정
    */
    addItems : function(categoryId, start) {
        let oReq = new XMLHttpRequest();
        oReq.addEventListener("load", function() {
            if (oReq.status === 200) {
                let resp = JSON.parse(oReq.responseText);
                products = resp.items;
                document.querySelector(".pink").innerHTML = resp.totalCount + "개";
    
                let eventBox = document.querySelectorAll(".lst_event_box");
                for(key = 0, len = products.length; key < len ; key++) {
                    let template = document.querySelector("#itemList").innerHTML;
                    template = template.replace("{productId}", products[key].productId)
                                .replaceAll("{description}", products[key].productDescription)
                                .replace("{placeName}", products[key].placeName)
                                .replace("{content}", products[key].productContent)
                                .replace("{imgUrl}", products[key].productImageUrl)
                                .replace("{displayInfoId}",products[key].displayInfoId);
    
                    if((key&1) === 0) {
                        eventBox[0].innerHTML += template;
                    } else {
                        eventBox[1].innerHTML +=  template;
                    }
                };
    
                let cnt = eventBox[0].childElementCount + eventBox[1].childElementCount;
                if(resp.totalCount === cnt) {
                    let moreBtn = document.querySelector(".more").firstElementChild;
                    moreBtn.style.display = 'none';
                }
            } else {
                alert("Getting category items error occured. Ajax error code: " + oReq.status);
            }
        });
    
        url = "./api/products?categoryId=" + categoryId + "&start=" + start;
        oReq.open("GET", url);
        oReq.send();
    }
}



