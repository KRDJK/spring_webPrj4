<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../include/static-head.jsp" %>

    <style>
        
        
        .board-list {
            width: 70%;
            margin: 230px auto;
        }

        .board-list .articles {
            margin: 20px auto 100px;
            border-collapse: collapse;
            font-size: 1.5em;
            border-radius: 10px;
        }


        .board-list .btn-amount a {
            float: right !important; 
        }


        header {
            background: #222;
            border-bottom: 1px solid #2c2c2c;
        }


        /* pagination style */
        .bottom-section {
            margin-top: -50px;
            margin-bottom: 100px;
            display: flex;
        }


        .bottom-section nav {
            flex: 9;
            display: flex;
            justify-content: center;
        }


        .bottom-section .btn-write {
            flex: 1;
        }


        .pagination-custom a {
            color: #444 !important;
        }


        .pagination-custom li.active a
        , .pagination-custom li:hover a
         {
            background: #333 !important;
            color: #fff !important; 
        }

    </style>


</head>

<body>

    <div class="wrap">

        <%@ include file="../include/header.jsp" %>
        
        <!-- <div class="amount-section">
            <div class="btn-amount10">
                <a href="/board/list?pageNum=${pm.page.pageNum}&amount=10">10개씩</a>
            </div>

            <div class="btn-amount20">
                <a href="/board/list?pageNum=${pm.page.pageNum}&amount=20">20개씩</a>
            </div>

            <div class="btn-amount30">
                <a href="/board/list?pageNum=${pm.page.pageNum}&amount=30">30개씩</a>
            </div>
        </div> -->
        
        

        <div class="board-list">

            <div class="btn-aomunt">
                <a class="btn btn-outline-danger" href="/board/list?pageNum=${pm.page.pageNum}&amount=10" role="button">10</a>
                <a class="btn btn-outline-danger" href="/board/list?pageNum=${pm.page.pageNum}&amount=20" role="button">20</a>
                <a class="btn btn-outline-danger" href="/board/list?pageNum=${pm.page.pageNum}&amount=30" role="button">30</a>
            </div>
            

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


            <!-- 게시글 목록 하단 영역 -->
            <div class="bottom-section">

                <!-- 페이지 버튼 영역 -->
                <nav aria-label="Page navigation example">
                    <ul class="pagination pagination-lg pagination-custom">

                      <c:if test="${pm.prev}">
                          <li class="page-item"><a class="page-link" href="/board/list?pageNum=${pm.beginPage - 1}">Prev</a></li>
                      </c:if>  
                    
                                                        <!-- step=1인 경우,, 생략 가능!! -->
                      <c:forEach var="n" begin="${pm.beginPage}" end="${pm.endPage}" step="1"> 
                          <li class="page-item"><a class="page-link" href="/board/list?pageNum=${n}">${n}</a></li>
                      </c:forEach>
                      

                      <c:if test="${pm.next}">
                          <li class="page-item"><a class="page-link" href="/board/list?pageNum=${pm.endPage + 1}">Next</a></li>
                      </c:if>
                    </ul>
                  </nav>



                <!-- 글쓰기 버튼 영역 -->
                <div class="btn-write">
                    <a class="btn btn-outline-danger btn-lg" href="/board/write">글쓰기</a>
                </div>
            </div>


        </div>


        <%@ include file="../include/footer.jsp" %>

    </div>

    <script>

        const $liList = document.querySelectorAll('.page-item');

        for (let li of $liList) {
            if (li.firstElementChild.textContent.trim() === '${pm.page.pageNum}') {
                li.classList.add('active');
                break;
            }
        }



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