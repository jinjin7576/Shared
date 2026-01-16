let f = document.forms[0];

// 댓글 관련 스크립트
const rs = replyService; // reply.js에서 CRUD 담당 객체

// 모달(팝업창) 관련 스크립트
let ms = modalService;
ms.init(rs, showList); // 모달 서비스 초기화 (의존성 주입)

// 버튼 이벤트 추가
addEvent_btn(".panel-body-btns button, #replyBtn, .modal-footer button", (e) => {
    let me = e.currentTarget;
    if (me.id == 'modifyBtn') {
        modify();
    }
    else if (me.id == 'indexBtn') {
        const { pageNum, amount } = getStorageData(); // 구조분해할당을 통해 pageNum과 amount를 가져옴
        location.href = `/board/list?pageNum=${pageNum}&amount=${amount}`;
    }
    else if (me.id == 'replyBtn') {
        ms.registerModalPage();
    }
    else if (me.id == 'closeModalBtn') {
        ms.closeModal();
    }
    else if (me.id == 'addReplyBtn') {
        ms.registerReply(f.bno.value); // bno를 명시적으로 전달
    }
    else if (me.id == 'modifyReplyBtn') {
        ms.modifyReply();
    }
    else if (me.id == 'removeReplyBtn') {
        ms.removeReply();
    }
});
// 댓글 상세 보기
function modifyModalPage(reply) {
    ms.modifyModalPage(reply);
}
function modify() {
    let bno = f.bno.value;
    location.href = '/board/modify?bno=' + bno;
}

//-------------------------------------------------------------------------------------------------

function showList() {
    let bno = f.bno.value;
    let replyUL = document.querySelector(".chat");
    rs.getList(bno, jsonArray => {
        let msg = '';
        if (!jsonArray || jsonArray.length == 0) {
            msg = '<small>댓글이 없습니다.</small>';
        }
        //서버의 vo의 필드명과 일치하는지 확인할 것(특히 대소문자)
        jsonArray.forEach(reply => {
            msg += `<li data-rno="${reply.rno}" onclick="modifyModalPage(this)">`
            msg += '<div>'
            msg += '<div class="chat-header">'
            msg += `<strong>${reply.replyer}</strong>`
            msg += `<small class="pull-right">${displayTime(reply.replydate)}</small>`
            msg += '</div>'
            msg += `<p>${reply.reply}</p>`
            msg += '</div>'
            msg += '</li>'
        });
        replyUL.innerHTML = msg;
    })
}
showList();

//------------------------------------------------------------------------------
// 첨부파일
/*
    1. 다운로드 기능 추가
    2. 삭제 기능 방지
*/
let as = attachService;

// 첨부파일 리스트 가져오기
as.getAttachList('get');


