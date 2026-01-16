let as = attachService;

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

    // 파일 업로드 관련
    let str = '';
    document.querySelectorAll(".uploadResult ul li").forEach((li, index) => {
        let path = li.getAttribute("path");
        let uuid = li.getAttribute("uuid");
        let fileName = li.getAttribute("fileName");
        // name의 attachList는 서버의 BoardVO의 List<BoardAttachVO>로 매핑이 되기 때문에
        // 인덱스를 사용하여 서버 기준의 이름으로 맞춰서 매핑이 자동으로 일어남
        str += `<input type="hidden" name="attachList[${index}].uploadPath" value="${path}" />`
        str += `<input type="hidden" name="attachList[${index}].uuid" value="${uuid}" />`
        str += `<input type="hidden" name="attachList[${index}].fileName" value="${fileName}" />`

    });

    //f.innerHTML += str;
    // 지금처럼 appendChild로 추가하는 것보다 insertAdjacentHTML을 사용하는 것이 더 안전함
    // 지금은 그냥 등록을 하면 파일 input이 추가되는 시점이 늦음(버그)
    f.insertAdjacentHTML('beforeend', str); //그래서 이 함수를 사용하여 파일 input을 추가함, 시점은 beforeend(끝나기 전에?)

    // 폼 내용을 post로 전송
    f.action = '/board/register';
    f.submit();
}

// 첨부파일 관련
as.addEvent_file('register');
