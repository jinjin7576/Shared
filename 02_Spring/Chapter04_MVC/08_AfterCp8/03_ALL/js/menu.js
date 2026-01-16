
// css 자동 매칭 적용 + 즉시 실행 함수(자기 호출 함수)
(function () {
    let jspName = [location.pathname.split('/').at(-1)]; //현재 경로를 통해 가져와야할 css 파일명
    if (jspName == '') {
        return;
    } else if (jspName == 'list') {
        jspName = ['boardList', 'page'];
    } else if (jspName == 'get') {
        jspName = ['get', 'modal', 'reply', 'upload'];
    } else if (jspName == 'modify') {
        jspName = ['modify', 'upload'];
    } else if (jspName == 'register') {
        jspName = ['register', 'upload'];
    }
    jspName.forEach(cssFileName => {
        let linkEle = document.createElement('link');
        linkEle.rel = 'stylesheet';
        linkEle.type = 'text/css';
        const CSS_FILE_PATH = '/resources/css/' + cssFileName + '.css';
        linkEle.href = CSS_FILE_PATH;
        document.head.appendChild(linkEle);
    });
})();


function addEvent_a(selector = "a", callback) {
    if (typeof selector === 'function') {
        callback = selector;
        selector = "a";
    }
    document.querySelectorAll(selector)
        .forEach(a => a.addEventListener("click", callback));
}
function addEvent_btn(selector = "button", callback) {
    if (typeof selector === 'function') {
        callback = selector;
        selector = "button";
    }
    document.querySelectorAll(selector)
        .forEach(b => b.addEventListener("click", callback));
}
// 전역 저장 공간( 페이징을 위해 추가 )
function setStorageData(pageNum, amount) {
    // 페이징 정보를 JSON으로 변환하기 위해 객체로 저장
    let pageData = {
        pageNum: pageNum,
        amount: amount
    };
    // JSON.stringify()를 사용하여 객체를 JSON 문자열로 변환하여 localStorage에 저장
    localStorage.setItem('page_data', JSON.stringify(pageData));
}
function getStorageData() {
    // JSON.parse()를 사용하여 JSON 문자열을 객체로 변환하여 반환
    return JSON.parse(localStorage.getItem('page_data'));
}
// 로그인 페이지로 이동
function loginPage() {
    location.href = "/customLogin";
}
// 회원가입 페이지로 이동
function joinPage() {
    location.href = "/customRegister";
}
function logout() {
    location.href = "/customLogout";
}
// 리스트 페이지로 이동
function goIndex() {
    const { pageNum, amount } = getStorageData(); // 구조분해할당을 통해 pageNum과 amount를 가져옴
    location.href = `/board/list?pageNum=${pageNum}&amount=${amount}`;
}
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

addEvent_a(".header a", (e) => {
    e.preventDefault();
    const href = e.target.getAttribute("href");
    if (href == "mainPage") {
        location.href = "/";
    }
    else if (href == "boardList") {
        location.href = "/board/list";
    }
    else console.log("wrong request");

});

//-----------principal 객체 가져오기
let principal;
async function getPrinciple() {
    try {
        const response = await fetch(`/api/currentUser.json`);
        const userPrincipal = await response.json();
        principal = userPrincipal;
        console.log(principal);
        console.log(principal.name);

    }
    catch (err) {
        console.log(`에러 : ${err}`);

    }
}
getPrinciple();