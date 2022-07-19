<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../include/static-head.jsp" %>

    <style>
        .board-list {
            width: 70%;
            margin: 0 auto;
        }

        .board-list .articles {
            margin: 250px auto 100px;
            border-collapse: collapse;
            font-size: 1.5em;
            border-radius: 10px;
        }

        .board-list .btn-write {
            /* background: orange; */
            text-align: right;
            position: relative;
            top: -70px;
        }

        header {
            background: #222;
            border-bottom: 1px solid #2c2c2c;
        }

    </style>
</head>

<body>

    <div class="wrap">

        <%@ include file="../include/header.jsp" %>

        <div class="board-list">
            <table class="table table-dark table-striped table-hover articles">
                <tr>
                    <th>번호</th>
                    <th>작성자</th>
                    <th>제목</th>
                    <th>조회수</th>
                    <th>작성시간</th>
                </tr>

                <c:forEach var="b" items="${bList}">
                    <tr>
                        <td>${b.boardNo}</td>
                        <td>${b.writer}</td>
                        <td title="${b.title}">${b.shortTitle}</td>
                        <td>${b.viewCnt}</td>
                        <td>${b.prettierDate}</td>
                    </tr>
                </c:forEach>
            </table>

            <div class="btn-write">
                <a class="btn btn-outline-danger btn-lg" href="/board/write">글쓰기</a>
            </div>
        </div>


        <%@ include file="../include/footer.jsp" %>

    </div>

    <script>

        const msg = '${msg}';
        console.log('msg: ', msg);

        if (msg == 'reg-success') {
            alert('게시물이 정상 등록되었습니다.');
        }


        //상세보기 요청 이벤트
        const $table = document.querySelector(".articles");

        // 테이블 자체에 한번 걸어서 자식 태그들이 버블링으로 자동으로 이벤트가 적용되게끔!!
        $table.addEventListener('click', e => {
            console.log('e.target - ', e.target);

            if (!e.target.matches('.articles td')) return; // td 구역이 아닌 곳(th 구간) 을 클릭하면 작동하지 않게 함.

            console.log('tr 클릭됨! - ', e.target);
            
            let bn = e.target.parentElement.firstElementChild.textContent; // 어떤 td를 클릭하든 글번호를 가져올 수 있다.
            console.log('글번호: ' + bn);

            location.href = '/board/content/' + bn;
        });

    </script>

</body>

</html>