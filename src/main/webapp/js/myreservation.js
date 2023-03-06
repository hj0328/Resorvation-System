document.addEventListener('DOMContentLoaded', function () {
    ConfirmedCard.init();
});

const ConfirmedCard = {
    init: function () {
        // 취소 클릭하면 팝업 레이더 표시
        let confirmedCardContainer = document.querySelectorAll('.confirmed .card_item');;
        confirmedCardContainer.forEach(confirmedCard => {
            confirmedCard.addEventListener('click', function (evt) {
                if (evt.target.className === 'btn') {
                    let canCelpopup = document.querySelector('.popup_booking_wrapper');
                    canCelpopup.style.display = 'block';

                    // 클릭했던 card에 clicked 표식 남기기
                    evt.currentTarget.classList.add('clicked');
                }
            });
        });

        // 팝업에서 예약취소 'x'버튼 클릭 수행
        ConfirmedCard.addCloseEvent();

        // 팝업에서 '예'버튼 클릭 수행
        ConfirmedCard.addCancelEvent();
    },
    addCloseEvent: function () {
        let closeBtn = document.querySelector('.popup_btn_close');
        closeBtn.addEventListener('click', function (evt) {
            let canCelpopup = document.querySelector('.popup_booking_wrapper');
            canCelpopup.style.display = 'none';

            // clicked 표식 제거
            clickedCard.classList.remove('clicked');
        });
    },
    addCancelEvent: function () {
        let greenBtn = document.querySelector('.btn_green');
        greenBtn.addEventListener("click", function (evt) {
            // 취소 팝업에서 '예' 클릭 시, '카드를 취소된 예약' 으로 이동
            let canCelpopup = document.querySelector('.popup_booking_wrapper');
            canCelpopup.style.display = 'none';

            let clickedCard = document.querySelector('.clicked');
            document.querySelector('.cancel').appendChild(clickedCard);

            let cancelButton = document.querySelector('.clicked .booking_cancel');
            cancelButton.remove();

            let shareButton = document.querySelector('.clicked .fn-share1');
            shareButton.remove();

			// '이용예정' 개수 감소
			let count = document.querySelectorAll('.link_summary_board')[1].lastElementChild.innerHTML;
			document.querySelectorAll('.link_summary_board')[1].lastElementChild.innerHTML = Number(count) - 1; 

			// '취소환불' 숫자 증가
			count = document.querySelectorAll('.link_summary_board')[3].lastElementChild.innerHTML;
			document.querySelectorAll('.link_summary_board')[3].lastElementChild.innerHTML = Number(count) + 1; 


            // clicked 표식 제거
            clickedCard.classList.remove('clicked');
        });

        let grayBtn = document.querySelector('.btn_gray');
        grayBtn.addEventListener("click", function (evt) {
            let canCelpopup = document.querySelector('.popup_booking_wrapper');
            canCelpopup.style.display = 'none';

            // clicked 표식 제거
            clickedCard.classList.remove('clicked');
        });
    }
}
