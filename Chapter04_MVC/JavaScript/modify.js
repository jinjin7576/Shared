// css 파일 추가 > 계속 반복되니까 유틸 js를 만들어서 함수로 사용
// 1. 파일 경로 설정
const CSS_FILE_PATH = "/resources/css/modify.css";
// 2. link 태그 생성 및 속성 추가
addCss(CSS_FILE_PATH);

let f = document.forms[0];
// 버튼 이벤트 추가
addEvent_btn(".panel-body-btns button", (e) => {
    let me = e.currentTarget;
    if (me.id == 'modifyBtn') {
        modify();
    }
    else if (me.id == 'indexBtn') {
        goIndex(); //menu.js의 함수 호출
    }
    else if (me.id == 'removeBtn') {
        remove();
    }
});
function modify() {
    if (!f.title.value) {
        alert("제목을 입력해주세요");
        return;
    }
    if (!f.content.value) {
        alert("내용을 입력해주세요");
        return;
    }
    f.action = '/board/modify';
    f.submit();
}
function remove() {
    if (!confirm("삭제하시겠습니까?")) {
        return;
    }
    //vo.bno만 post 방식으로 전달
    const bnoEle = f.bno;
    f.innerHTML = '';   //form 내부의 모든 엘리먼트를 제거
    f.appendChild(bnoEle); //vo.bno만 form 내부에 추가

    f.action = '/board/remove';
    f.submit();
}