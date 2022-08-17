


document.addEventListener("DOMContentLoaded", function() {
    initPromotion();
    initItemList(0);
  });

/*
    프로모션 관련 ajax 호출 및 작업 초기화
     - 프모션이 한 번 움직일 때마다, ajax 호출로 미리 2개 이미지 조회
     - 2개 이미지란 프로모션 메인 이미지와 다음에 보여질 이미지
*/
let items, container;
function initPromotion() {
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

            requestAnimationFrame(movePromotion);
        } else {
            alert("Server promotion error occured.");
        }
    });
    let url = "./api/promotions";
    oReq.open("GET", url);
    oReq.send();
}

/*
    requestAnimationFrame 이용한 프로모션 이동
*/
let start, previousTimeStamp;
let done = false
function movePromotion(timestamp) {
    if (start === undefined) {
        start = timestamp;
    }
    const elapsed = timestamp - start;

    if (previousTimeStamp !== timestamp) {
        const count = Math.min(0.1 * elapsed, container.clientWidth);
        container.style.transform = `translateX(-${count}px)`;

        if (count === container.clientWidth) done = true;
    }
    
    if (elapsed < container.clientWidth *10) { 
        previousTimeStamp = timestamp;
        if (!done) {
            requestAnimationFrame(movePromotion);
        } 
    } else {
        changePromotion();
        start = undefined;
        previousTimeStamp = undefined;
        done = false;
        requestAnimationFrame(movePromotion);
    }
}

/*
    프로모션 동작 후 다음 프로모션 템플릿 설정
     - 프로모션이 마지막에 도달하면 다시 처음 프로모션을 보이도록 설정
     - nextIdx가 2인 것은 초기화 작업 시 이미 2개의 이미지가 존재하기 때문에 2 설정
*/
let nextIdx = 2;
function changePromotion() {
    container = document.querySelector("#promotion_container").firstElementChild;
    container.removeChild(container.firstElementChild);
    let templateHtml = document.querySelector("#promotion-template").innerHTML;

    if (nextIdx === items.length) {
        nextIdx = 0;
    }
    container.innerHTML += templateHtml.replace("{imgUrl}", items[nextIdx].productImageUrl);
    nextIdx++;
}

/*
    메인페이지 접속 시 카테고리 개수 표시
*/
function initItemCnt() {
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
            alert("Server item count error occured.");
        }
    });
    let url = "./api/categories";
    oReq.open("GET", url);
    oReq.send();
}

/*
    product item을 카테고리에 따라 4개씩 보이도록 초기화
     - 메인 페이지 접속 시 호출
     - 카테고리 탭 클릭 시 호출
     - 사라졌던 '더보기' 버튼 다시 보여주도록 설정
*/
function initItemList(categoryId) {
    let eventBox = document.querySelectorAll(".lst_event_box");
    eventBox[0].innerHTML = "";
    eventBox[1].innerHTML = "";
    moreBtn.style.display = "";

    addItems(categoryId, 0);
    initItemCnt();
}

/*
    '더보기' 버튼 클릭 시 목록 추가
*/
let moreBtn = document.querySelector(".more").firstElementChild;
moreBtn.addEventListener("click", function(evt) {
    let categoryId = document.querySelector(".active").parentElement.dataset.category;
    let eventBox = document.querySelectorAll(".lst_event_box");
    let start = eventBox[0].childElementCount + eventBox[1].childElementCount;
    
    addItems(categoryId, start);
});

/*
    categoryId와 start에 따른 아이템 추가 
     - 새로운 카테고리 탭 클릭 시 start부터 시작하는 categoryId 아이템 호출
     - 새로운 카테고리 탭을 클릭하기 때문에 카테고리 개수 설정
*/
function addItems(categoryId, start) {
    let oReq = new XMLHttpRequest();
    oReq.addEventListener("load", function() {
        if (oReq.status === 200) {
            let resp = JSON.parse(oReq.responseText);
            products = resp.items;
            document.querySelector(".pink").innerHTML = resp.totalCount + "개";
            
            let eventBox = document.querySelectorAll(".lst_event_box");
            for(key = 0, len = products.length; key < len ; key++) {
                let template = document.querySelector("#itemList").innerHTML;
                template = template.replace("${id}", products[key].productId)
                            .replaceAll("${description}", products[key].productDescription)
                            .replace("${placeName}", products[key].placeName)
                            .replace("${content}", products[key].productContent)
                            .replace("${imgUrl}", products[key].productImageUrl);

                if((key&1) === 0) {
                    eventBox[0].innerHTML += template;
                } else {
                    eventBox[1].innerHTML +=  template;
                }
            };

            let cnt = eventBox[0].childElementCount + eventBox[1].childElementCount;
            if(resp.totalCount === cnt) {
                moreBtn.style.display = 'none';
            }
        } else {
            alert("Server error occured.");
        }
    });

    url = "./api/products?categoryId=" + categoryId + "&start=" + start;
    oReq.open("GET", url);
    oReq.send();
}

/*
    다른 카테고리 탭 클릭 시 화면 변경 
     - event delegation을 이용하여 a 또는 span 가 클릭 시 
       새 카테고리 아이템 다시 조회 
     - active class 재설정
*/
let tabs = document.querySelector(".section_event_tab");
tabs.addEventListener("click", function(evt){
    let tab = evt.target;

    if(tab.tagName === "A") {
        document.querySelector(".active").classList.remove("active");
        tab.classList.add("active");
        let categoryId = tab.parentElement.dataset.category;
        initItemList(categoryId);
    } else if(tab.tagName === "SPAN") {
        document.querySelector(".active").classList.remove("active");
        tab.parentElement.classList.add("active");

        let categoryId = tab.parentElement.parentElement.dataset.category;
        initItemList(categoryId);
    }
});