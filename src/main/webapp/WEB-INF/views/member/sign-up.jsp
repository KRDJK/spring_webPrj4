<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <%@ include file="../include/static-head.jsp" %>

    <style>
        .wrap {
            margin: 200px auto;
        }

        .c-red {
            color: #e00;
        }

        .c-blue {
            color: rgb(22, 229, 252);
        }
    </style>

</head>

<body>
    <%@ include file="../include/header.jsp" %>


    <div class="container wrap">
        <div class="row">
            <div class="offset-md-2 col-md-4">
                <div class="card" style="width:200%;">
                    <div class="card-header text-white" style="background: #343A40;">
                        <h2><span style="color: gray;">MVC</span> 회원 가입</h2>
                    </div>
                    <div class="card-body">


                        <form action="/member/sign-up" name="signup" id="signUpForm" method="post"
                            style="margin-bottom: 0;">

                            <!-- 사용자가 지식만 있다면 서버로 보내기 전에 js로 ADMIN으로 바꿔버리면 되잖아 -->
                            <!-- <input type="hidden" name="auth" value="COMMON"> -->

                            <table style="cellpadding: 0; cellspacing: 0; margin: 0 auto; width: 100%">
                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>아이디를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;
                                            <span id="idChk"></span></p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="account" id="user_id"
                                            class="form-control tooltipstered" maxlength="14" required="required"
                                            aria-required="true"
                                            style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="숫자와 영어로 4-14자">
                                    </td>

                                </tr>

                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>비밀번호를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="pwChk"></span></p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="password" size="17" maxlength="20" id="password" name="password"
                                            class="form-control tooltipstered" maxlength="20" required="required"
                                            aria-required="true"
                                            style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="영문과 특수문자를 포함한 최소 8자"></td>
                                </tr>
                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>비밀번호를 재확인해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="pwChk2"></span>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="password" size="17" maxlength="20" id="password_check"
                                            name="pw_check" class="form-control tooltipstered" maxlength="20"
                                            required="required" aria-required="true"
                                            style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="비밀번호가 일치해야합니다."></td>
                                </tr>

                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>이름을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="nameChk"></span></p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="text" name="name" id="user_name" class="form-control tooltipstered"
                                            maxlength="6" required="required" aria-required="true"
                                            style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="한글로 최대 6자"></td>
                                </tr>


                                <tr>
                                    <td style="text-align: left">
                                        <p><strong>이메일을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="emailChk"></span>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input type="email" name="email" id="user_email"
                                            class="form-control tooltipstered" required="required" aria-required="true"
                                            style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
                                            placeholder="ex) abc@mvc.com"></td>
                                </tr>


                                <tr>
                                    <td style="padding-top: 10px; text-align: center">
                                        <p><strong>회원가입하셔서 더 많은 서비스를 사용하세요~~!</strong></p>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width: 100%; text-align: center; colspan: 2;">
                                        <input type="button" value="회원가입" class="btn form-control tooltipstered"
                                            id="signup-btn"
                                            style="background: gray; margin-top: 0; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8">
                                    </td>
                                </tr>

                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <%@ include file="../include/footer.jsp" %>

    <!-- 회원가입 폼 검증 -->
    <script>
        $(document).ready(function () {

            // 입력값 검증 정규표현식 start
            const getIdCheck = RegExp(/^[a-zA-Z0-9]{4,14}$/); 
                // ^는 ~~로 시작하는~~ 뜻이다. a로 시작해서 9로 끝나야 한다. 최소 4자, 최대 14자라는 뜻

            const getPwCheck = RegExp(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/);
                // 영문, 숫자가 들어가야 하고 특수기호를 하나라도 포함해야한다 
                                                        // 또는!! 시작을 특수문자로 해도 되고, 영문이나 숫자를 하나라도 포함해야 한다.

            const getName = RegExp(/^[가-힣]+$/);
                // 한글로 써야 한다는 정규표현식. 유니코드상 한글의 시작이 '가', 끝이 '힣'이라서

            const getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
                // 영어로 시작해야 하고 중간에 @가 들어가야 한다. 그 뒤에는 또 영어가 들어가야 하고 . 뒤에 다시 영어가 들어가야 한다.
            // end 정규표현식



            // 입력값 검증 배열
                        // 1: 아이디 / 2: 비번, 3: 비번확인, 4: 이름, 5: 이메일
            const checkArr = [false, false, false, false ,false];



            // 1. 아이디 검증
            const $idInput = $('#user_id');

            $idInput.on('keyup', e => {

                // 아이디를 입력하지 않은 경우
                if ($idInput.val().trim() === '') {
                    $idInput.css('border-color', 'red');
                    $('#idChk').html('<b class="c-red">[아이디는 필수 정보입니다.]</b>');
                    checkArr[0] = false;
                }


                // 아이디를 패턴에 맞지 않게 입력하였을 경우
                // test() 메서드는 정규표현식을 검증하여 입력값이 표현식과
                // 일치하면 true, 일치하지 않으면 false를 리턴한다.

                else if (!getIdCheck.test($idInput.val())) { // getIdCheck의 정규표현식이 맞는지 테스트하겠다는 뜻이다.
                    $idInput.css('border-color', 'red');
                    $('#idChk').html('<b class="c-red">[영문, 숫자로 4~14자 사이로 작성하세요.]</b>');
                    checkArr[0] = false;
                }


                // 정상적으로 입력한 경우
                else {
                    $idInput.css('border-color', 'skyblue');
                    $('#idChk').html('<b class="c-blue">[사용 가능한 아이디입니다.]</b>');
                    checkArr[0] = true;
                }
            })


            const $signUpBtn = document.getElementById('signup-btn');

        });
    </script>
</body>

</html>