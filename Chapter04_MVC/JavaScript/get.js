const CSS_FILE_PATH = "/resources/css/get.css";
addCss(CSS_FILE_PATH);

let f = document.forms[0];
// 버튼 이벤트 추가
addEvent_btn(".panel-body-btns button", (e) => {
    let me = e.currentTarget;
    if (me.id == 'modifyBtn') {
        modify();
    }
    else if (me.id == 'indexBtn') {
        const { pageNum, amount } = getStorageData(); // 구조분해할당을 통해 pageNum과 amount를 가져옴
        location.href = `/board/list?pageNum=${pageNum}&amount=${amount}`;
    }
});

function modify() {
    let bno = f.bno.value;
    location.href = '/board/modify?bno=' + bno;
}