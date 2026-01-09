// const CSS_FILE_PATH = "/resources/css/get.css";
// const CSS_FILE_PATH2 = "/resources/css/reply.css";
// const CSS_FILE_PATH_MODAL = "/resources/css/modal.css";
// addCss(CSS_FILE_PATH);
// addCss(CSS_FILE_PATH2);
// addCss(CSS_FILE_PATH_MODAL);

let f = document.forms[0];
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
        registerModalPage();
    }
    else if (me.id == 'closeModalBtn') {
        closeModal();
    }
    else if (me.id == 'addReplyBtn') {
        registerReply(); // 진짜 댓글 등록 실행버튼
    }
    else if (me.id == 'modifyReplyBtn') {
        modifyReply();
    }
    else if (me.id == 'removeReplyBtn') {
        removeReply();
    }
});

function modify() {
    let bno = f.bno.value;
    location.href = '/board/modify?bno=' + bno;
}

//-------------------------------------------------------------------------------------------------
// 댓글 관련 스크립트
const rs = replyService; // reply.js에서 CRUD 담당 객체

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

function displayTime(unixTimeStemp) {
    // 초 기준
    let myDate = new Date(unixTimeStemp);
    //밀리초 기준
    //let myDate = new Date(unixTimeStemp * 1000);
    let year = myDate.getFullYear();
    let month = String(myDate.getMonth() + 1).padStart(2, '0');
    let date = String(myDate.getDate()).padStart(2, '0');
    return `${year}-${month}-${date}`;
}

// 모달 관련 스크립트
const modal = document.querySelector("#modal");

const inputReply = document.querySelector('input[name=reply]');
const inputReplyer = document.querySelector('input[name=replyer]');
const inputReplydate = document.querySelector('input[name=replydate]');

function openModal() {
    modal.style.display = 'block';
    // 모달이 열리면 body의 스크롤이 없어지게 함(선택)
    // document.body.style.overflow = "hidden";
}
function closeModal() {
    modal.style.display = 'none';
    // 모달이 닫히면 body의 스크롤이 다시 되게 함
    // document.body.style.overflow = "auto";

    // 작성자 정보를 받는 input이 readonly가 아니면 -> 댓글 추가 모드
    if (!inputReplyer.hasAttribute('readonly')) {
        // 입력한 정보들을 요소에 임시 속성(temp라는 이름)으로 저장
        inputReply.setAttribute('temp', inputReply.value);
        inputReplyer.setAttribute('temp', inputReplyer.value);
    }
}
function registerModalPage() {
    // 보여질 목록 수정
    regReplyModalStyle();
    // 입력 내용 초기화 및 불러오기
    inputReply.value = inputReply.getAttribute('temp');
    inputReplyer.value = inputReplyer.getAttribute('temp');

    openModal();
}
// 댓글 달기 창 스타일 변경 함수
function regReplyModalStyle() {
    //classList로 클래스 명을 관리? add , remove
    addReplyBtn.classList.remove('hide'); //숨기기 해제

    modifyReplyBtn.classList.add('hide'); //숨기기
    removeReplyBtn.classList.add('hide'); //숨기기
    inputReplydate.closest('div').classList.add('hide'); //closest : 가장 가까운 부모

    inputReplyer.removeAttribute('readonly'); //읽기 전용 해제
}
//진짜 댓글 등록 함수
function registerReply() {
    if (!inputReply.value || !inputReplyer.value) {
        alert("댓글 내용과 댓글 작성자 이름을 입력해주세요.");
        return;
    }
    let sendData = {
        bno: f.bno.value,
        reply: inputReply.value,
        replyer: inputReplyer.value
    };
    rs.add(sendData, () => {
        // 입력 내용 초기화
        inputReply.value = '';
        inputReplyer.value = '';
        closeModal();
        showList();
    })
}
//댓글 상세 보기 -> 수정 및 삭제
let rno; // 현재 선택한 댓글의 rno
function modifyModalPage(me) {
    //보여질 목록 수정
    modReplyModalStyle();
    //입력 내용 초기화 & 불러오기
    rno = me.getAttribute('data-rno');
    rs.get(rno, vo => {
        inputReply.value = vo.reply;
        inputReplyer.value = vo.replyer;
        inputReplydate.value = displayTime(vo.replydate);
    });
    openModal();
}
function modReplyModalStyle() {
    addReplyBtn.classList.add('hide'); //숨기기

    modifyReplyBtn.classList.remove('hide'); //숨기기 해제
    removeReplyBtn.classList.remove('hide'); //숨기기 해제
    inputReplydate.closest('div').classList.remove('hide'); //closest : 가장 가까운 부모

    inputReplyer.setAttribute('readonly', 'readonly'); //읽기 전용
    inputReplydate.setAttribute('readonly', 'readonly'); //읽기 전용
}

// 수정
function modifyReply() {
    if (!inputReply.value) {
        alert("댓글 내용을 입력해주세요.");
        return;
    }
    let obj = {
        reply: inputReply.value,
    }
    //여기서 사용한 rno는 전역변수
    rs.update(rno, obj, result => {
        if (result === 'success') {
            closeModal();
            showList();
        }
        else {
            alert('수정 실패');
        }
    })
}

// 삭제
function removeReply() {
    if (!confirm('삭제하시겠습니까?')) {
        return;
    }
    rs.remove(rno, result => {
        if (result === 'success') {
            closeModal();
            showList();
        }
        else {
            alert('삭제 실패');
        }
    })
}







// reply.js 테스트 코드들

// rs.add({
//     bno: f.bno.value,
//     reply: 'JS TEST',
//     replyer: 'JS Tester'
// },
//     function (result) {
//         console.log(result);
//     });

// rs.getList(f.bno.value, function (result) {
//     console.log(result);
// })

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

// rs.get(2, function (result) {
//     console.log(result);
// })

