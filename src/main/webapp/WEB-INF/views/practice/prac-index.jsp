<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원관리 연습용 메인</title>

<style>

</style>

</head>
<body>

    <h1>안녕 여긴 회원관리 연습용 페이지의 메인화면</h1>
    


    <c:if test="${loginUser == null}">    
        <div>
            <a href="/practice/register">회원가입</a>
        </div>
        <div>
            <a href="/practice/login">로그인</a>
        </div>
    </c:if>
    <c:if test="${loginUser != null}">
        <p><span>${loginUser.name}</span>님 안녕하세요</p>

        <div>
            <a href="/practice/logout">로그아웃</a>
        </div>
    </c:if>


    <script>

        

    </script>
</body>
</html>