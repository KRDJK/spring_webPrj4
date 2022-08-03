<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- header -->
<header data-scroll="${scroll}">
    <div class="inner-header">
        <h1 class="logo">
            <a href="/">
                <img src="/img/logo.png" alt="로고이미지">
            </a>
        </h1>
        <h2 class="intro-text">Welcome</h2>
        <a href="#" class="menu-open">
            <span class="menu-txt">MENU</span>
            <span class="lnr lnr-menu"></span>
        </a>
    </div>

    <nav class="gnb">
        <a href="#" class="close">
            <span class="lnr lnr-cross"></span>
        </a>
        <ul>
            <li><a href="#">Home</a></li>
            <li><a href="#">About</a></li>
            <li><a href="/board/list">Board</a></li>
            <li><a href="#">Contact</a></li>


            <%-- <c:if test="${requestScope.b.bardNo}"> --%>
                <!-- 아래와 같이 쓰면 session.getAttribute("loginUser")와 같은 코드다. -->
            <%-- <c:if test="${sessionScope.loginUser == null}" > --%>
                <!-- request 에는 없고 session에만 있다면 앞에 sessionScope. 구문을 생략할 수 있다. -->
            <c:if test="${loginUser == null}" >
                <li><a href="/member/sign-up">Sign Up</a></li>
                <li><a href="/member/sign-in">Sign In</a></li>
            </c:if>

            <c:if test="${loginUser != null}">
                <li><a href="#">My Page</a></li>
                <li><a href="/member/sign-out">Sign Out</a></li>
            </c:if>
        </ul>
    </nav>

</header>
<!-- //header -->