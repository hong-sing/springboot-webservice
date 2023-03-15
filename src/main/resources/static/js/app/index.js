var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
    },
    save : function () {
        var data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/'; // 글 등록이 성공하면 메인페이지(/)로 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

main.init();

/*
    1줄에 var main = {...}를 선언했다.
    굳이 index라는 변수의 속성으로 function을 추가한 이유는?
        만약 index.js가 아래와 같이 작성되었다고 가정해보자
        var init = function() {
            ...
        };
        var save = fucntion() {
            ...
        };
        init();

        index.mustache에서 a.js가 추가되어 a.js도 a.js만의 init과 save fucntion이 있다면
        브라우저의 스코프는 공용 공간으로 쓰이기 때문에 나중에 로딩된 js의 init, save가 먼저 로딩된 js의 function을
        덮어쓰게 된다.
        여러 사람이 참여하는 프로젝트에서는 중복된 함수 이름은 자주 발생할 수 있는데, 모든 function 이름을 확인하면서
        만들 수는 없다. 따라서 이런 문제를 피하기 위해 index.js만의 유효 범위를 만들어 사용한다.

        방법은 var index라는 객체를 만들어 해당 객체에서 필요한 모든 fucntion을 선언하는 것이다.

 */