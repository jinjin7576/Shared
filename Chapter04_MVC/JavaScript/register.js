// css 파일 추가
// 1. 파일 경로 설정
const CSS_FILE_PATH = "/resources/css/register.css";
// 2. link 태그 생성 및 속성 추가
addCss(CSS_FILE_PATH);

let f = document.forms[0];
// 버튼 이벤트 추가
addEvent_btn(".panel-body-btns button", (e) => {
    let me = e.currentTarget;
    if (me.id == 'registerBtn') {
        register();
    }
    else if (me.id == 'resetBtn') {
        f.reset();
    }
    else if (me.id == 'indexBtn') {
        goIndex(); //menu.js의 함수 호출
    }
});

function register() {
    // 검증
    let { title, writer, content } = f;
    if (!title.value || !writer.value || !content.value) {
        alert("모든 값을 입력해주세요");
        return;
    }

    // 폼 내용을 post로 전송
    f.action = '/board/register';
    f.submit();
}
