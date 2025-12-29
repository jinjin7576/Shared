// .header 하위 a 태그들에 이벤트 부여
// 		1. 기존 이벤트 방지
// 		2. 해당 속성에서 값 꺼내서 분기 태우기

// css 파일 추가
function addCss(cssPath) {
    // link 태그 생성 및 속성 추가
    if (Array.isArray(cssPath)) {
        cssPath.forEach(path => {
            let linkEle = document.createElement('link');
            linkEle.rel = 'stylesheet';
            linkEle.type = 'text/css';
            linkEle.href = path;
            document.head.appendChild(linkEle);
        });
        return;
    }
    let linkEle = document.createElement('link');
    linkEle.rel = 'stylesheet';
    linkEle.type = 'text/css';
    linkEle.href = cssPath;
    // head 태그에 방금 만든 link 요소 추가
    document.head.appendChild(linkEle);
}
function addEvent_a(selector = "a", callback) {
    document.querySelectorAll(selector)
        .forEach(a => a.addEventListener("click", callback));
}
function addEvent_btn(selector = "button", callback) {
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
function goIndex() {
    const { pageNum, amount } = getStorageData(); // 구조분해할당을 통해 pageNum과 amount를 가져옴
    location.href = `/board/list?pageNum=${pageNum}&amount=${amount}`;
}
addEvent_a((e) => {
    e.preventDefault();
    const href = e.target.getAttribute("href");
    if (href == "mainPage") {
        location.href = "/";
    }
    else if (href == "boardList") {
        location.href = "board/list";
    }
    else console.log("wrong request");

});
