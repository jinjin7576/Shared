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

//-------------------------------------------------------------------------------------------------
// 댓글 관련 스크립트
const rs = replyService; // reply.js에서 CRUD 담당 객체

// rs.add({
//     bno: f.bno.value,
//     reply: 'JS TEST',
//     replyer: 'JS Tester'
// },
//     function (result) {
//         console.log(result);
//     });

rs.getList(f.bno.value, function (result) {
    console.log(result);
})

//삭제 테스트를 위한 rno의 값은 임의의 값(즉, DB에 존재하는 댓글의 PK)으로 지정
// rs.remove(1, function (result) {
//     rs.getList(f.bno.value, function (result) {
//         console.log(result);
//     })
// })

//수정 테스트를 위한 rno의 값은 임의의 값(즉, DB에 존재하는 댓글의 PK)으로 지정
// rs.update(2, {
//     reply: 'JS TEST',
//     replyer: 'JS Tester'
// }, function (result) {
//     console.log(result);
//     rs.getList(f.bno.value, function (result) {
//         JSON.stringify(result);
//         console.log(result);
//     })
// })

rs.get(2, function (result) {
    console.log(result);
})