
// 버튼 이벤트 추가
addEvent_btn("#registerBtn", (e) => {
    location.href = '/board/register';
});

// 게시글 상세 보기
addEvent_a("td a", (e) => {
    e.preventDefault();

    const bno = e.currentTarget.getAttribute("href");

    location.href = `/board/get?bno=${bno}`;
});

// 페이징 시스템 버튼 시스템 추가
addEvent_a(".page-nation a", (e) => {
    e.preventDefault();
    let pageNum = e.currentTarget.getAttribute("href");
    setStorageData(pageNum, 10);
    location.href = `/board/list?pageNum=${pageNum}&amount=10`;
});

// 페이지 이동 등에서 사용하기 위한 cri 객체를 저장하는 방법
let pageNum = new URLSearchParams(location.search).get('pageNum');
let amount = new URLSearchParams(location.search).get('amount');
if (!pageNum || !amount) {
    pageNum = 1;
    amount = 10;
}

// 서버에 업로드된 첨부 파일 정리하기
// DB에 저장된 첨부파일과 실제 서버에 업로드된 첨부파일들을 비교하여
// DB와 실제 서버에 업로드된 첨부파일들을 일치하도록 만들기
// 스케줄러 대신 사용
// (function () {
//     fetch(`/uploadedController`, {
//         method: 'post',
//         body: JSON.stringify({
//             password: "admin"
//         }),
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     })
//         .then(response => response.json())
//         .then(data => {
//             console.log(data);
//         })
//         .catch(err => console.log(err));
// })();