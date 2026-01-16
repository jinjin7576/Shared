const modalService = (function () {
    let rs = null; // replyService
    let refreshCallback = null; // showList

    const modal = document.querySelector("#modal");
    const inputReply = document.querySelector('input[name=reply]');
    const inputReplyer = document.querySelector('input[name=replyer]');
    const inputReplydate = document.querySelector('input[name=replydate]');

    const addReplyBtn = document.querySelector('#addReplyBtn');
    const modifyReplyBtn = document.querySelector('#modifyReplyBtn');
    const removeReplyBtn = document.querySelector('#removeReplyBtn');

    // 초기화 함수
    function init(service, callback) {
        rs = service;
        refreshCallback = callback;
    }

    function openModal() {
        modal.style.display = 'block';
    }
    function closeModal() {
        modal.style.display = 'none';
        if (!inputReplyer.hasAttribute('readonly')) {
            inputReply.setAttribute('temp', inputReply.value);
            inputReplyer.setAttribute('temp', inputReplyer.value);
        }
    }
    function registerModalPage() {
        regReplyModalStyle();
        inputReply.value = inputReply.getAttribute('temp');
        inputReplyer.value = principal.name;
        openModal();
    }
    function regReplyModalStyle() {
        addReplyBtn.classList.remove('hide');
        modifyReplyBtn.classList.add('hide');
        removeReplyBtn.classList.add('hide');

        inputReply.removeAttribute('readonly');
        inputReplydate.closest('div').classList.add('hide');
        inputReplyer.setAttribute('readonly', 'readonly');
    }
    function registerReply(bno) {
        if (!inputReply.value || !inputReplyer.value) {
            alert("댓글 내용과 댓글 작성자 이름을 입력해주세요.");
            return;
        }
        let sendData = {
            bno: bno,
            reply: inputReply.value,
            replyer: inputReplyer.value
        };
        rs.add(sendData, () => {
            inputReply.value = '';
            inputReplyer.value = '';
            closeModal();
            if (refreshCallback) refreshCallback();
        })
    }

    let rno;
    function modifyModalPage(reply) {
        rno = reply.getAttribute('data-rno');
        rs.get(rno, vo => {
            inputReply.value = vo.reply;
            inputReplyer.value = vo.replyer;
            inputReplydate.value = displayTime(vo.replydate);
            modReplyModalStyle();
        });
        openModal();
    }
    function modReplyModalStyle() {
        addReplyBtn.classList.add(`hide`);
        if (principal.name == inputReplyer.value) {
            // console.log(principal.name);
            // console.log(inputReplyer.value);
            // console.log("같음");

            modifyReplyBtn.classList.remove(`hide`);
            removeReplyBtn.classList.remove(`hide`);
            inputReply.removeAttribute('readonly');
        }
        else {
            // console.log(principal.name);
            // console.log(inputReplyer.value);
            // console.log("다름");

            modifyReplyBtn.classList.add(`hide`);
            removeReplyBtn.classList.add(`hide`);
            inputReply.setAttribute('readonly', 'readonly');
        }
        inputReplydate.closest('div').classList.remove('hide');
        inputReplydate.setAttribute('readonly', 'readonly');
        inputReplyer.setAttribute('readonly', 'readonly');
    }

    function modifyReply() {
        if (!inputReply.value) {
            alert("댓글 내용을 입력해주세요.");
            return;
        }
        let obj = { reply: inputReply.value };
        rs.update(rno, obj, result => {
            if (result === 'success') {
                closeModal();
                if (refreshCallback) refreshCallback();
            } else {
                alert('수정 실패');
            }
        })
    }

    function removeReply() {
        if (!confirm('삭제하시겠습니까?')) return;
        rs.remove(rno, result => {
            if (result === 'success') {
                closeModal();
                if (refreshCallback) refreshCallback();
            } else {
                alert('삭제 실패');
            }
        })
    }
    return {
        init,
        openModal,
        closeModal,
        registerModalPage,
        registerReply,
        modifyModalPage,
        modifyReply,
        removeReply
    }
})();