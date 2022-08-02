
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>

<!-- jquery -->
<script src="/js/jquery-3.3.1.min.js"></script>

<style>

    .fileDrop {
        width: 800px;
        height: 400px;
        border: 1px dashed gray;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 1.5em;
    }

    .uploaded-list {
        display: flex;
    }

    .img-sizing {
        display: block;
        width: 100px;
        height: 100px;
    }
</style>
</head>


<body>

    <!-- 파일 업로드를 위한 form - 동기 처리 -->
    <form action="/upload" method="post" enctype="multipart/form-data"> <!-- 내가 지금 보내는건 file 이라고 명시해주는 것. -->
        <input type="file" name="file" multiple>
        <button type="submit">업로드</button>
    </form>



    <!-- 비동기 통신을 통한 실시간 파일 업로드 처리 -->
    <div class="fileDrop">
        <span>DROP HERE!!</span>
    </div>


    <!-- 
        - 파일 정보를 서버로 보내기 위해서는 <input type="file"> 이 필요
        - 해당 input태그는 사용자에게 보여주어 파일을 직접 선택하게 할 것이냐
          혹은 드래그앤 드롭으로만 처리를 할 것이냐에 따라 display를 상태를 결정
     -->
     <div class="uploadDiv">
        <input type="file" name="files" id="ajax-file" style="display:none;">
    </div>



    <!-- 업로드된 이미지의 썸네일을 보여주는 영역 -->
    <div class="uploaded-list">

    </div>



    <script>

        // start JQuery
        $(document).ready(function () { // jQuery의 즉시 실행 함수 느낌.


            // 파일명에서 확장자를 추출하여 이미지인지 확인하는 함수
            function isImageFile(originFileName) {
            
                //정규표현식
                const pattern = /jpg$|gif$|png$/i; // jpg로 끝나거나~ gif로 끝나거나~ png로 끝나면 true를 리턴하라.

                return originFileName.match(pattern); // 패턴하고 매치되면 true다.
            }



            // 파일의 확장자에 따른 렌더링 처리
            function checkExtType(fileName) {

                // 원본 파일명 추출
                let originFileName = fileName.substring(fileName.indexOf('_') + 1); // 언더바 다음 글자부터 끝까지 잘라라.


                // 확장자 추출 후 이미지인지까지 확인
                if (isImageFile(originFileName)) { // 파일이 이미지라면~~

                    const $img = document.createElement('img');
                    $img.classList.add('img-sizing');

                    // $img.setAttribute('src', fileName); // 풀 경로 
                    $img.setAttribute('src', '/loadFile?fileName=' + fileName); // 서버에서 순수 파일 데이터를 달라고 해야함. 비동기 GET 요청을 보내야 한다.

                    $img.setAttribute('alt', originFileName); // 원본 파일명만을 떼어낸 것.


                    $('.uploaded-list').append($img);
                } 


                else { // 파일이 이미지가 아니라면~~ 다운로드 링크를 생성해줄 것이다.

                    const $a = document.createElement('a');
                    $a.setAttribute('href', '/loadFile?fileName=' + fileName);


                    const $img = document.createElement('img');
                    $img.classList.add('img-sizing');

                    $img.setAttribute('src', '/img/file_icon.jpg'); // 이미지 파일이 아니면 처리할 이미지를 미리 넣어놨었다 ㅇㅇ

                    $img.setAttribute('alt', originFileName); // 원본 파일명만을 떼어낸 것.

                    $a.append($img);
                    $a.innerHTML += '<span>' + originFileName + '</span>';


                    $('.uploaded-list').append($a);

                }

            }




            // 드롭한 파일을 화면에 보여주는 함수
            function showFileData(fileNames) {

                // 이미지인지? 이미지가 아닌지에 따라 구분하여 처리하고자 함.
                // 이미지면 썸네일을 렌더링하고 아니면 다운로드 링크를 렌더링할 것이다.

                for (let fileName of fileNames) {
                    checkExtType(fileName);
                }
            }




            // drag & drop 이벤트
            const $dropBox = $('.fileDrop'); // document.querySelector로 잡은거랑 같은 효과다.


            // drag 진입 이벤트
            $dropBox.on('dragover dragenter', e => { // addEventListener랑 같다고 보면 된다.
                
                e.preventDefault(); // 기본 기능인 새창으로 이미지를 열어버리는걸 방지하고자 한다.

                $dropBox
                    .css('border-color', 'red')
                    .css('background', 'lightgray');
            }); 



            // drag 탈출 이벤트
            $dropBox.on('dragleave', e => { 
                
                e.preventDefault(); 

                $dropBox
                    .css('border-color', 'gray')
                    .css('background', 'transparent');
            }); 



            // drop 이벤트
            $dropBox.on('drop', e => { // drop 이벤트가 발생했을 때의 정보를 e에 다 담아준다.
                
                e.preventDefault(); 
                // 새 탭에 이미지 파일이 크게 열려버리는걸 방지. 
                // 또는 이미지가 아니라 파일이라면 다운로드나 브라우저에 바로 파일이 연결되는 등의 기본 행위를 막아줌.
                
                console.log('드롭 이벤트 작동!');



                // 드롭된 파일 정보를 서버로 전송해야 함.

                // 1. 드롭된 파일 데이터 읽기
                console.log(e); // e는 브라우저가 주는 데이터다. 
                                //이벤트의 종류에 따라 제공하는 정보가 달랐다. 누른 key에 대한 정보는 key 관련 이벤트일때만!!


                // 복습 1단계 : 바로 밑의 files 로그와 윗 e 로그가 다른 이유는 파일을 인식하기도 전에 순서상 로그가 먼저 찍혀서 그렇다고 함.. 흠..


                const files = e.originalEvent.dataTransfer.files;
                console.log('drop file data: ', files);



                // 2. 읽은 파일 데이터를 input[type=file] 태그에 저장
                const $fileInput = $('#ajax-file');
                console.log($fileInput); // 여기서도 그냥 잡히네..????

                $fileInput.prop('files', files); // input의 name이 files이기 때문에 1번째 파라미터에서 맞춰준 것..????

                // 복습 2단계 : input 태그가 파일을 받기 위해 type 속성을 file로 갖고 있다면!!
                //              눈에 보이지 않지만 자동적으로 files 라는 속성을 갖고 있다.
                //              어떤 파일을 선택해주기 전까진 files 속성값은 공란이며
                //              비동기 처리를 하려다보니 드랍이 일어난 순간 속성값으로 넣어준 것이다.

                console.log($fileInput); // input 태그를 배열에 담아서 보여준다.. 왜?? JQuery 에서는 q.s All 로 잡아온다.



                // 3. 파일 데이터를 비동기 전송하기 위해서는 FormData 객체가 필요하다.
                const formData = new FormData(); // form 태그를 사용하는 것처럼 수동으로 만든 것이다.



                // 4. 전송할 파일들을 전부 formData 안에 포장해야 한다. JSON.stringyfy 하듯이
                for (let file of $fileInput[0].files) {
                    formData.append('files', file); // 서버에서 files로 읽겠다는 뜻이다.
                }



                // 5. 비동기 요청 전송
                const reqInfo = {
                    method: 'POST',
                    // headers: { 'content-type' : 'multipart/form-data'} 이건 기본값이라 쓰지 않아도 된다.
                                // but,, form-data가 아니라 단순 img 파일을 던진다고 하면 명시해줘야 한다.
                    body: formData
                };


                fetch('/ajax-upload', reqInfo)
                    .then(res => {
                        console.log(res.status);
                        return res.json();
                    })
                    .then(fileNames => {
                        console.log(fileNames);

                        showFileData(fileNames);
                    });

            });


        }); 
        // end JQuery

    </script>
    
</body>
</html>