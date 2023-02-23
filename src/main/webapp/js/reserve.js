document.addEventListener("DOMContentLoaded", function () {
    BtnBackSetter.setBtnBack();
    const ticketTypeCount = 4;
    let quantityList = document.querySelectorAll(".qty");
    for (let i = 0; i < ticketTypeCount; i++) {
        quantityList[i].addEventListener("click", function(evt) {
            let countBtn = new CountBtn();
            countBtn.countTicket(evt);
        });
    }
});

const BtnBackSetter = {
    setBtnBack: function () {
        let btn = document.querySelector(".btn_back");
        btn.setAttribute("href", "./detail?id=" + getParam("id"));
    }
}

/*
    티켓수 선택 기능
    + 버튼 클릭 시 개수와 전체 가격 증가(-버튼 활성화)
    - 버튼 클릭 시 개수와 전체 가격 감소(개수가 0되면 -버튼 비활성화)
*/
function CountBtn() {
}

CountBtn.prototype = {
    countTicket: function (evt) {
        if (evt.target.className.includes("ico_minus3")) {
            this.setMinusCount(evt);
        } else if (evt.target.className.includes("ico_plus3")) {
            this.setPlusCount(evt);
        }

    },
    setMinusCount: function (evt) {
        let currentCount = evt.target.nextElementSibling.value;
        if (currentCount === '0') {
            return;
        }

        currentCount--;
        evt.target.nextElementSibling.value = currentCount;
        if (currentCount === 0) {
            evt.target.classList.toggle("disabled");
            evt.target.nextElementSibling.classList.toggle("disabled");
        }

        let price = evt.currentTarget.getElementsByClassName('price')[0].innerText.replace(',', '');
        let totalPrice = evt.currentTarget.getElementsByClassName('total_price')[0].innerHTML;
        evt.currentTarget.getElementsByClassName('total_price')[0].innerHTML = Number(totalPrice) - Number(price);
    },
    setPlusCount: function (evt) {
        let currentCount = evt.target.previousElementSibling.value;
        if (currentCount === '0') {
            evt.target.previousElementSibling.classList.toggle("disabled");
            evt.target.previousElementSibling.previousElementSibling.classList.toggle("disabled");
        }

        currentCount++;
        evt.target.previousElementSibling.value = currentCount;

        let price = evt.currentTarget.getElementsByClassName('price')[0].innerText.replace(',', '');
        let totalPrice = evt.currentTarget.getElementsByClassName('total_price')[0].innerHTML;
        evt.currentTarget.getElementsByClassName('total_price')[0].innerHTML = Number(totalPrice) + Number(price);
    }
}