const attachService = (function () {
    const MAX_SIZE = 5242880; //5MB
    const regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");

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

    // 업로드 완료된 목록 보여주는 함수
    let uploadResult = document.querySelector('.uploadResult ul');
    function showUploadedFile(uploadResultArr, jspName) {
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
            if (jspName == 'get') {
                str += `<li path="${file.uploadPath}" uuid="${file.uuid}" fileName="${file.fileName}">`;
                str += `<a href="/download/?fileName=${fileCallPath}">`;
                str += `${file.fileName}`;
                str += `</a>`;
                str += `</li>`;
            }
            else if (jspName == 'modify') {
                str += `<li path="${file.uploadPath}" uuid="${file.uuid}" fileName="${file.fileName}">`;
                str += `${file.fileName}`;
                str += `</li>`;
            }
            else if (jspName == 'register') {

                str += `<li path="${file.uploadPath}" uuid="${file.uuid}" fileName="${file.fileName}">`;
                str += `${file.fileName}`;
                str += `<span data-file="${fileCallPath}">  X</span>`;
                str += `</li>`;
            }
        });
        uploadResult.innerHTML = str;
    }

    function getAttachList(jspName = 'get') {
        fetch(`/board/getAttachList/${f.bno.value}`)
            .then(response => {
                console.log(`response.status : ${response.status}`);
                return response.json();
            })
            .then(result => {
                showUploadedFile(result, jspName);
            })
            .catch(err => console.log(err));
    }
    function addEvent_file(jspName) {
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
                    showUploadedFile(data, jspName);
                    if (jspName == 'register') addEvent_X();
                })
                .catch(err => console.log(err));
        });
    }
    function addEvent_X() {
        document.querySelectorAll("span[data-file]").forEach(span => {
            span.addEventListener('click', () => {
                fetch(`/deleteFile`, {
                    method: 'post',
                    body: span.getAttribute('data-file'),
                    headers: {
                        'Content-Type': 'text/plain'
                    }
                })
                    .then(response => {
                        console.log(`response.status : ${response.status}`);
                        return response.text();
                    })
                    .then(result => {
                        console.log(result);
                        span.parentElement.remove();
                    })
                    .catch(err => console.log(err));
            });
        });
    }
    return {
        checkExtension,
        showUploadedFile,
        getAttachList,
        addEvent_file,
        addEvent_X
    }
})();