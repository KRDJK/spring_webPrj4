
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
</head>
<body>

    <!-- 파일 업로드를 위한 form -->
    <form action="/upload" method="post" enctype="multipart/form-data"> <!-- 내가 지금 보내는건 file 이라고 명시해주는 것. -->
        <input type="file" name="file">
        <button type="submit">업로드</button>
    </form>
    
</body>
</html>