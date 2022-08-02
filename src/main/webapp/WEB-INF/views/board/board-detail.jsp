<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../include/static-head.jsp" %>

    <style>
        .content-container {
            width: 60%;
            margin: 150px auto;
            position: relative;
        }

        .content-container .main-title {
            font-size: 24px;
            font-weight: 700;
            text-align: center;
            border-bottom: 2px solid rgb(75, 73, 73);
            padding: 0 20px 15px;
            width: fit-content;
            margin: 20px auto 30px;
        }

        .content-container .main-content {
            border: 2px solid #ccc;
            border-radius: 20px;
            padding: 10px 25px;
            font-size: 1.1em;
            text-align: justify;
            min-height: 400px;
        }

        .content-container .custom-btn-group {
            position: absolute;
            bottom: -10%;
            left: 50%;
            transform: translateX(-50%);
        }


        /* 페이지 액티브 기능 */
        .pagination .page-item.p-active a {
            background: #333 !important;
            color: #fff !important;
            cursor: default;
            pointer-events: none;
        }

        .pagination .page-item:hover a {
            background: #888 !important;
            color: #fff !important;
        }
    </style>
</head>

<body>

    <div class="wrap">
        <%@ include file="../include/header.jsp" %>

        <div class="content-container">

            <h1 class="main-title">${b.boardNo}번 게시물</h1>

            <div class="mb-3">
                <label for="exampleFormControlInput1" class="form-label">작성자</label>
                <input type="text" class="form-control" id="exampleFormControlInput1" placeholder="이름" name="writer"
                    value="${b.writer}" disabled>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput2" class="form-label">글제목</label>
                <input type="text" class="form-control" id="exampleFormControlInput2" placeholder="제목" name="title"
                    value="${b.title}" disabled>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlTextarea1" class="form-label">내용</label>

                <p class="main-content">
                    ${b.content}
                </p>

            </div>

            <div class="btn-group btn-group-lg custom-btn-group" role="group">
                <button id="mod-btn" type="button" class="btn btn-warning">수정</button>
                <button id="del-btn" type="button" class="btn btn-danger">삭제</button>
                <button id="list-btn" type="button" class="btn btn-dark">목록</button>
            </div>


            <!-- 첨부파일 목록 영역 -->
            <div class="card-header text-white m-0" style="background: #343A40;">
                <div class="float-left">첨부파일 (<span id="fileCnt">0</span>)</div>
            </div>
            <div id="fileCollapse" class="card">
                <div id="fileData">

                </div>
            </div>

            <!-- 파일 첨부 영역
            <div class="form-group">
                <ul class="uploaded-list"></ul>
            </div> -->


            <!-- 댓글 영역 -->
            <div id="replies" class="row">
                <div class="offset-md-1 col-md-10">
                    <!-- 댓글 쓰기 영역 -->
                    <div class="card">
                        <div class="card-body">
                            <div class="row">


                                <div class="col-md-9">
                                    <div class="form-group">
                                        <label for="newReplyText" hidden>댓글 내용</label>
                                        <textarea rows="3" id="newReplyText" name="replyText" class="form-control"
                                            placeholder="댓글을 입력해주세요."></textarea>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label for="newReplyWriter" hidden>댓글 작성자</label>
                                        <input id="newReplyWriter" name="replyWriter" type="text" class="form-control"
                                            placeholder="작성자 이름" style="margin-bottom: 6px;">
                                        <button id="replyAddBtn" type="button"
                                            class="btn btn-dark form-control">등록</button>
                                    </div>
                                </div>



                            </div>
                        </div>
                    </div> <!-- end reply write -->

                    <!--댓글 내용 영역-->
                    <div class="card">
                        <!-- 댓글 내용 헤더 -->
                        <div class="card-header text-white m-0" style="background: #343A40;">
                            <div class="float-left">댓글 (<span id="replyCnt">0</span>)</div>
                        </div>

                        <!-- 댓글 내용 바디 -->
                        <div id="replyCollapse" class="card">
                            <div id="replyData">
                                <!-- 
                        < JS로 댓글 정보 DIV삽입 > 
                     -->
                            </div>

                            <!-- 댓글 페이징 영역 -->
                            <ul class="pagination justify-content-center">
                                <!-- 
                        < JS로 댓글 페이징 DIV삽입 > 
                     -->
                            </ul>
                        </div>
                    </div> <!-- end reply content -->
                </div>
            </div> <!-- end replies row -->

            <!-- 댓글 수정 모달 -->
            <div class="modal fade bd-example-modal-lg" id="replyModifyModal">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">

                        <!-- Modal Header -->
                        <div class="modal-header" style="background: #343A40; color: white;">
                            <h4 class="modal-title">댓글 수정하기</h4>
                            <button type="button" class="close text-white" data-bs-dismiss="modal">X</button>
                        </div>

                        <!-- Modal body -->
                        <div class="modal-body">
                            <div class="form-group">
                                <input id="modReplyId" type="hidden">
                                <label for="modReplyText" hidden>댓글내용</label>
                                <textarea id="modReplyText" class="form-control" placeholder="수정할 댓글 내용을 입력하세요."
                                    rows="3"></textarea>
                            </div>
                        </div>

                        <!-- Modal footer -->
                        <div class="modal-footer">
                            <button id="replyModBtn" type="button" class="btn btn-dark">수정</button>
                            <button id="modal-close" type="button" class="btn btn-danger"
                                data-bs-dismiss="modal">닫기</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- end replyModifyModal -->

        </div>


        <%@ include file="../include/footer.jsp" %>
    </div>



    <!-- 첨부파일 목록 불러오기 관련 script -->
    <script>
        function isImgFile(originFileName) {
            //정규표현식
            const pattern = /jpg$|gif$|png$/i; // jpg로 끝나거나~ gif로 끝나거나~ png로 끝나면 true를 리턴하라.

            return originFileName.match(pattern); // 패턴하고 매치되면 true다.
        }


        function checkExt(file) {

            let originFileName = file.substring(file.indexOf('_') + 1);

            if (isImgFile(originFileName)) {

                const $img = document.createElement('img');
                $img.setAttribute('src', '/loadFile?fileName=' + file);
                $img.setAttribute('alt', originFileName);

                document.getElementById('fileData').appendChild($img);
            } 
            else {
                const $a = document.createElement('a');
                $a.setAttribute('href', '/loadFile?fileName=' + file);


                const $img = document.createElement('img');
                $img.setAttribute('src', '/img/file_icon.jpg');
                $img.setAttribute('alt', originFileName);

                $a.appendChild($img);
                $a.innerHTML += '<span>' + originFileName + '</span>';


                document.getElementById('fileData').appendChild($a);
            }
        }


        function makeFileListDOM(files) {

            for (let file of files) {
                checkExt(file);
            }
        }


        // 첨부파일 목록 비동기 요청
        fetch('/board/file/' + '${b.boardNo}')
            .then(res => res.json())
            .then(files => {
                console.log(files);
                makeFileListDOM(files);

            }).catch(err => {
                alert('첨부파일 목록을 불러오기 위한 서버와의 통신에 실패하였습니다.');
            });
    </script>


    <!-- 게시글 상세보기 관련 script -->
    <script>
        const [$modBtn, $delBtn, $listBtn] = [...document.querySelector('div[role=group]').children];

        // const $modBtn = document.getElementById('mod-btn');
        //수정버튼
        $modBtn.onclick = e => {
            location.href = '/board/modify?boardNo=${b.boardNo}';
        };


        //삭제버튼
        $delBtn.onclick = e => {
            if (!confirm('정말 삭제하시겠습니까?')) {
                return;
            }
            location.href = '/board/delete?boardNo=${b.boardNo}';
        };


        //목록버튼
        $listBtn.onclick = e => {
            location.href = '/board/list/?pageNum=${p.pageNum}&amount=${p.amount}';
        };
    </script>


    <!-- 댓글관련 script -->
    <script>
        // 원본 글 번호
        let bno = '${b.boardNo}';
        // console.log('bno:', bno);



        // 댓글 요청 URL
        const URL = '/api/v1/replies';



        //날짜 포맷 변환 함수
        function formatDate(datetime) {

            //문자열 날짜 데이터를 날짜객체로 변환
            const dateObj = new Date(datetime);
            // console.log(dateObj);


            //날짜객체를 통해 각 날짜 정보 얻기
            let year = dateObj.getFullYear();


            //1월이 0으로 설정되어있음.
            let month = dateObj.getMonth() + 1;
            let day = dateObj.getDate();
            let hour = dateObj.getHours();
            let minute = dateObj.getMinutes();


            //오전, 오후 시간체크
            let ampm = '';

            if (hour < 12 && hour >= 6) {
                ampm = '오전';

            } else if (hour >= 12 && hour < 21) {
                ampm = '오후';
                if (hour !== 12) {
                    hour -= 12;
                }
            } else if (hour >= 21 && hour <= 24) {
                ampm = '밤';
                hour -= 12;
            } else {
                ampm = '새벽';
            }


            //숫자가 1자리일 경우 2자리로 변환
            (month < 10) ? month = '0' + month: month;
            (day < 10) ? day = '0' + day: day;
            (hour < 10) ? hour = '0' + hour: hour;
            (minute < 10) ? minute = '0' + minute: minute;

            return year + "-" + month + "-" + day + " " + ampm + " " + hour + ":" + minute;
        }


        // // 댓글 등록 이벤트 처리 핸들러 등록 함수
        // function makeReplyRegisterClickEvent(e) {

        //     document.getElementById('replyAddBtn').onclick = makeReplyRegisterClickHandler;
        // }



        // // 댓글 등록 이벤트 처리 핸들러 함수
        // function makeReplyRegisterClickHandler(e) {

        //     const $writerInput = document.getElementById('newReplyWriter');
        //     const $contentInput = document.getElementById('newReplyText');

        //     // 서버로 전송할 데이터들
        //     const replyData = {
        //         replyWriter: $writerInput.value,
        //         replyText: $contentInput.value,
        //         boardNo: bno
        //     };

        //     // POST 요청을 위한 요청 정보 객체
        //     const reqInfo = {
        //         method: 'POST',
        //         headers: {
        //             'content-type': 'application/json'
        //         },
        //         body: JSON.stringify(replyData)
        //     };

        //     fetch(URL, reqInfo)
        //         .then(res => res.text())
        //         .then(msg => {
        //             if (msg === 'insert-success') {
        //                 $writerInput.value = '';
        //                 $contentInput.value = '';
        //                 // 댓글 목록 재요청
        //                 showReplies();
        //             } else {
        //                 alert('댓글 등록 실패');
        //             }
        //         })
        // }


        // 댓글 등록 요청 이벤트
        const $addBtn = document.getElementById('replyAddBtn');
        $addBtn.onclick = e => {

            e.preventDefault();

            const reqObj = {
                method: 'POST',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify({
                    replyWriter: document.getElementById('newReplyWriter').value,
                    replyText: document.getElementById('newReplyText').value,
                    boardNo: bno
                })
            };


            fetch(URL, reqObj)
                .then(res => res.text())
                .then(msg => {
                    if (msg === 'insert-success') {
                        showReplies(document.querySelector('.pagination').dataset.fp);
                        document.getElementById('newReplyWriter').value = '';
                        document.getElementById('newReplyText').value = '';
                        alert('댓글 등록 성공!');
                    } else {
                        alert('댓글 등록 실패!');
                    }
                }).catch(err => {
                    alert('댓글 등록을 위한 서버와의 통신 실패!');
                })
        };



        // 댓글 페이지 태그 생성 렌더링 함수
        function makePageDOM(pageInfo) {

            let tag = "";

            const begin = pageInfo.beginPage;
            const end = pageInfo.endPage;


            //이전 버튼 만들기
            if (pageInfo.prev) {
                tag += "<li class='page-item'><a class='page-link page-active' href='" + (begin - 1) +
                    "'>이전</a></li>";
            }


            //페이지 번호 리스트 만들기
            for (let i = begin; i <= end; i++) {
                const active = (pageInfo.page.pageNum === i) ? 'p-active' : '';
                tag += "<li class='page-item " + active + "'><a class='page-link page-custom' href='" + i +
                    "'>" +
                    i + "</a></li>";
            }


            //다음 버튼 만들기
            if (pageInfo.next) {
                tag += "<li class='page-item'><a class='page-link page-active' href='" + (end + 1) +
                    "'>다음</a></li>";
            }



            // 페이지 태그 렌더링
            document.querySelector('.pagination').dataset.fp = pageInfo.finalPage;
            document.querySelector('.pagination').innerHTML = tag;

        }



        // 댓글 목록 DOM을 생성하는 함수
        function makeReplyDOM({
            replyList,
            count,
            maker
        }) { // 여기서 바로 디스트럭쳐링을 할 수 있다.
            let tag = '';

            if (replyList === null || replyList.length === 0) { // null 검증은 같이 해주는게 좋다.

                tag = "<div id='replyContent' class='card-body'>댓글이 아직 없습니다.ㅠㅠ</div>";

            } else {

                // 각 댓글 하나에 해당하는 태그.

                for (let rep of replyList) {
                    tag += "<div id='replyContent' class='card-body' data-replyId='" + rep.replyNo + "'>" +
                        "    <div class='row user-block'>" +
                        "       <span class='col-md-3'>" +
                        "         <b>" + rep.replyWriter + "</b>" +
                        "       </span>" +
                        "       <span class='offset-md-6 col-md-3 text-right'><b>" + formatDate(rep.replyDate) +
                        "</b></span>" +
                        "    </div><br>" +
                        "    <div class='row'>" +
                        "       <div class='col-md-6'>" + rep.replyText + "</div>" +
                        "       <div class='offset-md-2 col-md-4 text-right'>" +
                        "         <a id='replyModBtn' class='btn btn-sm btn-outline-dark' data-bs-toggle='modal' data-bs-target='#replyModifyModal'>수정</a>&nbsp;" +
                        "         <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>" +
                        "       </div>" +
                        "    </div>" +
                        " </div>";
                }
            }

            // 댓글 목록에 생성된 DOM 추가
            document.getElementById('replyData').innerHTML = tag;


            // 댓글 수 배치
            document.getElementById('replyCnt').textContent = count;


            // 댓글 페이지 렌더링
            makePageDOM(maker);


            // 페이지 버튼 클릭 이벤트 처리
            const $pageUl = document.querySelector('.pagination');

            $pageUl.onclick = e => {
                if (!e.target.matches('.page-item a')) return;

                e.preventDefault(); // a 태그의 원 기능을 막자

                // 누른 페이지 번호 가져오기
                const pageNum = e.target.getAttribute('href');
                console.log(pageNum);


                // 페이지 번호에 맞는 목록 비동기 요청
                showReplies(pageNum);

            };


            // closest 함수 활용법
            /*
                <li class="item" data-id="1">
                    <div>
                        <div class="content">
                            <a>삭제</a>
                        </div>
                    </div>
                </li>
                <li class="item" data-id="2">
                    <div>
                        <div class="content">
                            <a>삭제</a>
                        </div>
                    </div>
                </li>

                e.target ( a태그 )
                e.target.parentElement.parentElement.parentElement.dataset.id;
                e.target.closest('li').dataset.id;
            */


            // // 댓글 삭제 요청 및 수정 버튼 클릭 이벤트 처리
            // const $replyData = document.getElementById('replyData');
            // $replyData.onclick = e => {

            //     if (!e.target.matches('#replyDelBtn') && !e.target.matches('#replyModBtn')) return;

            //     if (e.target.matches('#replyDelBtn')) {
            //         console.log('삭제클릭!');

            //         e.preventDefault();

            //         const reqDelObj = {
            //             method: 'DELETE'
            //         }


            //         fetch(URL + '/' + e.target.parentElement.parentElement.parentElement.dataset.replyid, reqDelObj)
            //             .then(res => res.text())
            //             .then(msg => {
            //                 if (msg === 'del-success') {
            //                     showReplies();
            //                     alert('댓글 삭제 성공!');
            //                 } else {
            //                     alert('댓글 삭제 실패!');
            //                 }
            //             }).catch(err => {
            //                 alert('댓글 삭제를 위한 서버와의 통신 실패!')
            //             })
            //     }
            //     else if (e.target.matches('#replyModBtn')) {
            //         console.log('댓글 수정 클릭!', e.target.parentElement.parentElement.parentElement.dataset.replyid);
            //         document.getElementById('modReplyId').value = e.target.parentElement.parentElement.parentElement.dataset.replyid;
            //     }
            // };
        }


        // 댓글 목록을 서버로부터 비동기요청으로 불러오는 함수
        function showReplies(pageNum = 1) { // 기본값 1이라는 뜻.

            fetch(URL + '?boardNo=' + bno + '&pageNum=' + pageNum)
                .then(res => res.json())
                .then(replyMap => {
                    makeReplyDOM(replyMap);
                });
        }


        // // 댓글 수정 완료 요청 이벤트
        // const $replyModBtn = document.getElementById('replyModBtn');
        // $replyModBtn.onclick = e => {
        //     console.log('수정 완료 및 반영 요청!');
        //     console.log(document.getElementById('modReplyId').value);

        //     e.preventDefault();

        //     const reqModObj = {
        //         method: 'PUT',
        //         headers: {
        //             'content-type': 'application/json'
        //         }, 
        //         body: JSON.stringify({
        //             replyText: document.getElementById('modReplyText').value
        //         })
        //     }


        //     fetch(URL + '/' + document.getElementById('modReplyId').value, reqModObj)
        //         .then(res => res.text())
        //         .then(msg => {
        //             if (msg === 'mod-success') {
        //                 showReplies();
        //                 alert('댓글 수정 성공!');
        //             } else {
        //                 alert('댓글 수정 실패!');
        //             }
        //         }) .catch (err => {
        //             alert('댓글 수정을 위한 서버와의 통신 실패!');
        //         })
        // };


        // 댓글 수정화면 열기, 삭제 처리 핸들러 정의
        function makeReplyModAndDelHandler(e) {

            e.preventDefault();

            if (e.target.matches('#replyModBtn')) {
                processModifyShow(e);

            } else if (e.target.matches('#replyDelBtn')) {
                processRemove(e);

            }
        }

        // 댓글 수정화면 열기 상세처리
        function processModifyShow(e) {
            // console.log('수정버튼 클릭함!!');

            // 1. 클릭한 버튼 근처에 있는 댓글의 원래 내용 텍스트를 얻어온다.
            const replyText = e.target.parentElement.parentElement.firstElementChild.textContent;

            // console.log('댓글 내용:', replyText);

            // 2. 모달에 해당 댓글 내용을 배치한다.
            document.getElementById('modReplyText').textContent = replyText;


            // 3. 모달을 띄울 때 다음 작업 (수정완료처리)을 위해 댓글 번호를 모달에 달아두자.
            const $modal = document.querySelector('.modal');
            $modal.dataset.rno = e.target.parentElement.parentElement.parentElement.dataset.replyid;
        }

        // 댓글 삭제 상세처리
        function processRemove(e) {
            if (!confirm('진짜로 삭제합니까??')) return;

            const $modal = document.querySelector('.modal');
            $modal.dataset.rno = e.target.parentElement.parentElement.parentElement.dataset.replyid;

            const rno = e.target.closest('.modal').dataset.rno;

            fetch(URL + '/' + rno, {
                    method: 'DELETE'
                })
                .then(res => res.text())
                .then(msg => {
                    if (msg === 'del-success') {
                        alert('댓글 삭제 성공!!');
                    } else {
                        alert('댓글 삭제 실패!!');
                    }
                })
        }

        // 댓글 수정 화면 요청 이벤트
        function openModifyModalAndRemoveEvent() {

            const $replyData = document.getElementById('replyData');
            $replyData.onclick = makeReplyModAndDelHandler;
        }



        // 댓글 수정 비동기 처리 이벤트
        function replyModifyEvent() {

            // // 부트스트랩에서 지원하는 방식
            // const $modal = new bootstrap.Modal('#replyModifyModal', {
            //     keyboard: false
            // });

            // 쌤버전
            const $modal = $('#replyModifyModal');


            const $replyModBtn = document.getElementById('replyModBtn');

            $replyModBtn.onclick = e => {
                console.log('수정 완료 버튼 클릭!');

                const rno = e.target.closest('.modal').dataset.rno;
                // 1. 서버에 수정 비동기 요청 보내기

                const reqInfo = {
                    method: 'PUT',
                    headers: {
                        'content-type': 'application/json'
                    },
                    body: JSON.stringify({
                        // replyText: document.getElementById('modReplyText').textContent
                        replyText: $('#modReplyText').val() // jQuery 버전.
                            ,
                        replyNo: rno
                    })
                };

                fetch(URL + '/' + rno, reqInfo)
                    .then(res => res.text())
                    .then(msg => {
                        if (msg === 'mod-success') {
                            alert('수정 성공!!');
                            $modal.modal('hide'); // 모달창 닫기
                            showReplies(); // 댓글 새로 불러오기
                        } else {
                            alert('수정 실패!!');
                        }
                    });
            };
        }


        // 메인 실행부
        (function () {
            // 비동기처리가 되기 때문에 새로고침이 일어나지 않으면 즉시 실행부는 페이지가 로딩될 때 최초 한번만 실행된다.

            // // 페이지 버튼 클릭 이벤트 처리
            // const $pageUl = document.querySelector('.pagination');

            // $pageUl.onclick = e => {
            //     if (!e.target.matches('.page-item a')) return;

            //     e.preventDefault(); // a 태그의 원 기능을 막자

            //     // 누른 페이지 번호 가져오기
            //     const pageNum = e.target.getAttribute('href');
            //     console.log(pageNum);


            //     // 페이지 번호에 맞는 목록 비동기 요청
            //     showReplies(pageNum);

            // };



            // 초기 화면 렌더링시 댓글 1페이지 렌더링
            showReplies();

            // 댓글 수정 모달 오픈 이벤트 처리
            openModifyModalAndRemoveEvent();

            // 댓글 수정 완료 버튼 이벤트 처리
            replyModifyEvent();

        })();
    </script>
</body>

</html>