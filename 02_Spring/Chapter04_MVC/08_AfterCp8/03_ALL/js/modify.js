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
    // 폼에 파일 데이터 추가
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
    f.innerHTML += str;

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

//--------------------------------------------------
/* 
    파일 첨부
    1. 파일 삭제 / 수정 기능 추가
    2. 파일 수정은 삭제 후 다시 업로드
*/
let as = attachService;
// 첨부파일 리스트 가져오기
as.getAttachList('modify');
// 비어있는 요소 복사를 위해 가져오기
let uploadDiv = document.querySelector(".uploadDiv");
// 하위 노드까지 복사
let cloneObj = uploadDiv.firstElementChild.cloneNode(true);
// input에서 파일 선택 후 바로 서버에 업로드 되는 이벤트를 거는 코드
as.addEvent_file('modify');