<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>혼자 해보자</title>

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
    <form action="/upload-to" method="post" enctype="multipart/form-data"> <!-- 내가 지금 보내는건 file 이라고 명시해주는 것. -->
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

        $(document).ready(function () {


            function isImageFile(originFileName) {

                //정규표현식
                const pattern = /jpg$|gif$|png$/i; // jpg로 끝나거나~ gif로 끝나거나~ png로 끝나면 true를 리턴하라.

                return originFileName.match(pattern); // 패턴하고 매치되면 true다.
            }



            function checkExtType(fileName) {

                // 원본 파일명 추출하기
                let originFileName = fileName.substring(fileName.indexOf('_') + 1);


                // 확장자를 추출해서 이미지 여부 확인
                if (isImageFile(originFileName)) {
                    
                    const $img = document.createElement('img');
                    $img.classList.add('img-sizing');


                    $img.setAttribute('src', '/loadingFile?fileName=' + fileName);

                    $img.setAttribute('alt', originFileName);

                    $('.uploaded-list').append($img);
                }
            }



            // 드랍한 파일을 화면에 보여게 할 함수
            function showFileData(fileNames) {
                for (let fileName of fileNames) {
                    checkExtType(fileName);
                }
            }



            const $dropBox = $('.fileDrop');

            // drag해서 박스에 진입했을 때 이벤트 처리
            $dropBox.on('dragover dragenter', e => {

                e.preventDefault();

                $dropBox
                    .css('border-color', 'tomato')
                    .css('background', 'gainsboro');
            });


            // drag한 채로 박스를 떠날 때 이벤트 처리
            $dropBox.on('dragleave', e => {

                e.preventDefault();

                $dropBox
                    .css('border-color', 'gray')
                    .css('background', 'transparent');
            });


            // 박스 안에 파일을 drop 했을 때 이벤트 처리
            $dropBox.on('drop', e => {

                e.preventDefault();

                console.log(e);

                // 1. 드롭된 파일의 데이터를 변수에 저장
                const files = e.originalEvent.dataTransfer.files;
                console.log(files);

                const $fileInput = $('#ajax-file');

                // 2. 변수에 저장된 드롭된 파일 데이터를 숨겨진 input[type=file] 태그의 files 속성에 채워줌.
                $fileInput.prop('files', files);
                console.log($fileInput);


                // 3. 파일 데이터를 비동기 전송하기 위해 formData 객체 생성
                const formData = new FormData();


                // 4. 전송할 파일들을 전부 formData 안에 포장해야 한다.
                for (let file of $fileInput[0].files) {
                    formData.append('files', file);
                }


                // 5. 비동기 전송 요청
                const Obj = {
                    method: 'POST',
                    // headers: {'content-type': 'multipart/form-data'}
                    body : formData
                }


                fetch('/ajax-self-upload', Obj)
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

    </script>

</body>
</html>