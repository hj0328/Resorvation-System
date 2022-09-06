
document.addEventListener("DOMContentLoaded", function() {
    moveTop();
  });

function moveTop() {
    let lnkTop = document.querySelector(".lnk_top");
    lnkTop.addEventListener("click", function() {
        window.scrollTo({
            top:0,
            left:0,
            behavior:'smooth'});
    });
}

function getParam(key) {
    let params = location.search.slice(1);
    params = params.split("&");

    let value = "";
    params.forEach(function(param){
        let paramArr = param.split("=");
        if(paramArr[0] === key) value += paramArr[1];
    });

    return value;
}
