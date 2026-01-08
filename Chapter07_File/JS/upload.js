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

//uploadBtn 클릭 이벤트
document.querySelector("#uploadBtn").addEventListener('click', (e) => {
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

    fetch('/uploadAsyncAction', {
        method: 'post',
        body: formData
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
        })
        .catch(err => console.log(err));
});