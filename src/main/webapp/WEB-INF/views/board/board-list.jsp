<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../include/static-head.jsp" %>

    <style>
        
        
        .board-list {
            width: 70%;
            margin: 230px auto 0;
        }


        /* 해설 들으면서 */
        .board-list .amount {
            display: flex;
            /* background: skyblue; */

            /* 까먹었던 부분 */
            justify-content: flex-end;
        }

        .board-list .amount li {
            width: 5%;
            margin-right: 10px;
        }

        .board-list .amount li a {
            width: 100%;
        }

        /* end - 해설 들으면서 */


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

        /* 검색창 */
        .board-list .top-section {
            display: flex;
            justify-content: space-between;
        }
        .board-list .top-section .search {
            flex: 4;
        }
        .board-list .top-section .amount {
            flex: 4;
        }
        .board-list .top-section .search form {
            display: flex;
        }
        .board-list .top-section .search form #search-type {
            flex: 1;
            margin-right: 10px;
        }
        .board-list .top-section .search form input[name=keyword] {
            flex: 3;
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
            <div class="top-section">
                <!-- 검색창 영역 -->
                <div class="search">
                    <form action="/board/list" method="get">

                        <select class="form-select" name="type" id="search-type">
                            <option value="title">제목</option>
                            <option value="content">내용</option>
                            <option value="writer">작성자</option>
                            <option value="tc">제목+내용</option>
                        </select>

                        <input class="form-control" type="text" name="keyword" value="${search.keyword}">

                        <button class="btn btn-primary" type="submit">
                            <i class="fas fa-search"></i>
                        </button>

                    </form>
                </div>

                <!-- 목록 개수별 보기 영역 -->
                <ul class="amount">
                    <li><a class="btn btn-danger" href="/board/list?amount=10&type=${search.type}&keyword=${search.keyword}">10</a></li>
                    <li><a class="btn btn-danger" href="/board/list?amount=20&type=${search.type}&keyword=${search.keyword}">20</a></li>
                    <li><a class="btn btn-danger" href="/board/list?amount=30&type=${search.type}&keyword=${search.keyword}">30</a></li>                
                </ul>
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
                        <td title="${b.title}">
                            ${b.shortTitle} [${b.replyCount}]
                            <c:if test="${b.newArticle}">
                                <span class="badge rounded-pill bg-danger">new</span>
                            </c:if>
                        </td>
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
                          <li class="page-item"><a class="page-link" href="/board/list?pageNum=${pm.beginPage - 1}&amount=${pm.getPage().getAmount()}&type=${search.type}&keyword=${search.keyword}">Prev</a></li>
                      </c:if>  
                    
                                                        <!-- step=1인 경우,, 생략 가능!! -->
                      <c:forEach var="n" begin="${pm.beginPage}" end="${pm.endPage}" step="1"> 
                          <li data-page-num="${n}" class="page-item"><a class="page-link" href="/board/list?pageNum=${n}&amount=${pm.getPage().getAmount()}&type=${search.type}&keyword=${search.keyword}">${n}</a></li>
                      </c:forEach>
                      

                      <c:if test="${pm.next}">
                          <li class="page-item"><a class="page-link" href="/board/list?pageNum=${pm.endPage + 1}&amount=${pm.getPage().getAmount()}&type=${search.type}&keyword=${search.keyword}">Next</a></li>
                      </c:if>
                    </ul>
                  </nav>



                <!-- 글쓰기 버튼 영역 -->
                <div class="btn-write">
                    <a class="btn btn-outline-danger btn-lg" href="/board/write?pageNum=${pm.endPage + 1}&amount=${pm.getPage().getAmount()}&type=${search.type}&keyword=${search.keyword}">글쓰기</a>
                </div>
            </div>


        </div>


        <%@ include file="../include/footer.jsp" %>

    </div>

    <script>

        // 굳이 함수 안써도 됐나..?
        function fixSearchOption(){
            const $select = document.querySelector('.form-select');
            // const $input = $select.nextElementSibling;

            const $optionList = $select.children;

            console.log('${search.type}');
            console.log('${search.keyword}');


            for (let option of $optionList) {
                if ('${search.type}' === option.value) {
                    option.setAttribute('selected', 'selected');
                    return;
                }
            }
        }


        function appendPageAction() {

            // 현재 내가 보고 있는 페이지 넣기
            const curPageNum = '${pm.page.pageNum}';
            // console.log('현재 내가 보고 있는 페이지: ', curPageNum);


            // 페이지 li 태그들을 전부 확인해서 현재 위치한 페이지 넘버와
            // textContent가 일치하는 li를 찾아서 class active 부여
            const $ul = document.querySelector('.pagination');

            for (let $li of [...$ul.children]) {
                if (curPageNum === $li.dataset.pageNum) {
                    $li.classList.add('active');
                    return;
                }
            }
        }
        
        // 내가 한 버전 ( li에 active 태그 부여 )
        // const $liList = document.querySelectorAll('.page-item');

        // for (let li of $liList) {
        //     if (li.firstElementChild.textContent.trim() === '${pm.getPage().getPageNum()}') {
        //         li.classList.add('active');
        //         break;
        //     }
        // }



        function alertServerMessage() {
            const msg = '${msg}';
            console.log('msg: ', msg);
    
            if (msg == 'reg-success') {
                alert('게시물이 정상 등록되었습니다.');
            }
        }



        function detailRequestEvent() {
            //상세보기 요청 이벤트
            const $table = document.querySelector(".articles");

            // 테이블 자체에 한번 걸어서 자식 태그들이 버블링으로 자동으로 이벤트가 적용되게끔!!
            $table.addEventListener('click', e => {
                console.log('e.target - ', e.target);

                if (!e.target.matches('.articles td')) return; // td 구역이 아닌 곳(th 구간) 을 클릭하면 작동하지 않게 함.

                console.log('tr 클릭됨! - ', e.target);
                
                let bn = e.target.parentElement.firstElementChild.textContent; // 어떤 td를 클릭하든 글번호를 가져올 수 있다.
                console.log('글번호: ' + bn);

                location.href = '/board/content/' + bn + '?pageNum=${pm.page.pageNum}&amount=${pm.page.amount}';
            });
        }



        (function () {

            alertServerMessage();
            detailRequestEvent();
            appendPageAction();
            fixSearchOption();

        }) ();
    </script>

</body>

</html>