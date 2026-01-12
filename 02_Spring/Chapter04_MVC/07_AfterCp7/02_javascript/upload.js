const MAX_SIZE = 5242880; //5MB
const regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

// 파일의 크기와 종류 체크하는 함수
function checkExtension(fileName, fileSize) {
    if (fileSize >= MAX_SIZE) {
        alert("5MB 이하의 파일만 업로드 가능합니다.");
        return false;
    }
    if (regex.test(fileName)) {
        alert("해당 종류의 파일은 업로드할 수 없습니다.");
        return false;
    }
    return true;
}
// 비어있는 요소 복사를 위해 가져오기
let uploadDiv = document.querySelector(".uploadDiv");
// 하위 노드까지 복사
let cloneObj = uploadDiv.firstElementChild.cloneNode(true);

// input에서 파일 선택 후 바로 서버에 업로드 되는 이벤트를 거는 코드
document.querySelector("input[type=file]").addEventListener('change', () => {
    const inputFile = document.querySelector('input[type=file]');
    // 파일 정보는 프로퍼티로
    const files = inputFile.files;

    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
        if (!checkExtension(files[i].name, files[i].size)) {
            return false;
        }
        formData.append('uploadFile', files[i]);
    }
    //실제 파일 업로드
    fetch('/uploadAsyncAction', {
        method: 'post',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            //파일 추가 후 초기화
            inputFile.value = '';
            showUploadedFile(data);
        })
        .catch(err => console.log(err));
});

// 업로드 완료된 목록 보여주는 함수
let uploadResult = document.querySelector('.uploadResult ul');
function showUploadedFile(uploadResultArr) {
    let str = '';
    uploadResultArr.forEach(file => {
        // 특수 문자가 섞인 파일명의 경우 URL이 깨질 수 있음
        // 그래서 URL 인코딩을 해야 함
        let fileCallPath = encodeURIComponent(
            file.uploadPath +
            '/' +
            file.uuid +
            "_" +
            file.fileName
        );

        str += `<li path="${file.uploadPath}" uuid="${file.uuid}" fileName="${file.fileName}">`;
        // str += `<a href="/download/?fileName=${fileCallPath}">`;
        str += `${file.fileName}`;
        // str += `</a>`;
        str += `<span data-file="${fileCallPath}">  X</span>`;
        str += `</li>`;
    });
    uploadResult.innerHTML = str;
    addEvent_X();
}

// span 클릭 이벤트
function addEvent_X() {
    document.querySelectorAll("span").forEach(ele => {
        //tagName 을 통해 태그 비교 가능 => ele.tagName ==='SPAN' 같은 조건문으로
        // 하지만, 여기서 다른 span은 없으니 그냥 span을 사용
        ele.addEventListener("click", (e) => {
            console.log(`click event(delete), data-file : ${ele.getAttribute('data-file')}`);

            // 파일 삭제
            fetch('/deleteFile', {
                method: 'post',
                body: ele.getAttribute('data-file'),
                headers: {
                    'Content-Type': 'text/plain'
                }
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    // 삭제된 파일은 리스트에서 제거
                    let targetLi = e.target.closest('li');
                    targetLi.remove();
                })
                .catch(err => console.log(err));
        });
    });
}
