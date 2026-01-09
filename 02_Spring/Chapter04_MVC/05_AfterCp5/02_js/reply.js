const replyService = (function () {
    function add(reply, callback) {
        fetch('/reply/new', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(reply) // js 객체 -> JSON 문자열
        })
            .then(response => {
                if (!response.ok) {
                    console.log(response.status);
                    console.log(response.statusText);
                    throw new Error("에러 발생");
                }
                return response.text();
            })// 응답이 String이라서 text()로 변환
            .then(data => {
                callback(data);

            })
            .catch(err => console.log(err));
    }

    function getList(bno, callback) {
        fetch('/reply/pages/' + bno + ".json")
            .then(response => response.json()) // = JSON.parse(responseBodyString) JSON 문자열 -> js 객체
            .then(data => {
                callback(data);
            })
            .catch(err => console.log(err));
    }

    function remove(rno, callback) {
        fetch('/reply/' + rno, {
            method: 'delete', //deleteMapping이 된 메서드를 타야 해서 delete로
            //body는 필요없음(서버가 rno만 필요함)
        })
            .then(response => response.text())
            .then(data => {
                callback(data);
            })
            .catch(err => console.log(err));
    }

    function update(rno, vo, callback) {
        fetch('/reply/' + rno, {
            method: 'put', //put 또는 patch                           
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(vo)
        })
            .then(response => response.text())
            .then(data => {
                callback(data);
            })
            .catch(err => console.log(err));
    }

    function get(rno, callback) {
        fetch(`/reply/${rno}.json`) //getMapping이라 추가적으로 객체 생성할 필요 없음
            .then(response => response.json())
            .then(data => {
                callback(data);
            })
            .catch(err => console.log(err));
    }

    //replyService에 저장되는 것은 이 객체(함수들을 모아놓은 객체)
    return {
        add: add,
        getList: getList,
        remove: remove,
        update: update,
        get: get
    }
})();