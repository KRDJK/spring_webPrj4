<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form id="del-form" action="/board/delete" method="post">
        <input type="hidden" name="boardNo" value="${boardNo}">
        <!-- 비번 입력창을 한번 더 띄우면서 더 검증을 거치는 과정을 만들 수도 있다. -->
    </form>

    <script>
        const $form = document.getElementById('del-form');
        $form.submit();
    </script>

</body>
</html>