<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="robots" content="noindex, nofollow">
    <title>관리자 로그인 - MemSys</title>
    <link rel="stylesheet" href="/admin/resources/stylesheets/dev.css">
    <link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap">

    <script defer src="/admin/resources/scripts/dev.js"></script>
</head>
<body class="login-body">
    <div class="body-item login-container">
        <div class="login-container-item logo">
            <!-- <img src="/admin/resources/images/memsys_logo.png" alt=""> -->
        </div>
        <div class="login-container-item form-wrap">
            <div class="login-container-item menu">
                <div class="menu-item title">
                    <ul>
                        <li class="menu-login active"><a href="#">Login</a></li>
                        <!-- <li class="menu-register"><a href="#">Register</a></li> -->
                    </ul>
                </div>
            </div>
            <div class="login-container-item content">
                <div class="content-item container">
                    <div class="container-item input email">
                        <input id="input-login-email" type="email" placeholder="이메일" maxlength="100">
                        <div class="input-item image"></div>
                    </div>
                    <div class="container-item input password">
                        <input id="input-login-password" type="password" placeholder="비밀번호" maxlength="100">
                        <div class="input-item image"></div>
                    </div>
                    <div class="container-item buttons">
                        <div id="button-login" class="buttons-item button">로그인</div>
                    </div>
                </div>
            </div>
            <!--
            <div class="register-container-item content">
                <div class="content-item container">
                    <div class="container-item input email">
                        <input id="input-register-email" type="email" placeholder="이메일" maxlength="100">
                    </div>
                    <div class="container-item input email">
                        <input id="input-register-password" type="password" placeholder="비밀번호" maxlength="100">
                    </div>
                    <div class="container-item input passwordCheck">
                        <input id="input-register-passwordCheck" type="password" placeholder="비밀번호 확인" maxlength="100">
                    </div>
                    <div class="container-item input email">
                        <input id="input-register-name" type="text" placeholder="이름" maxlength="100">
                    </div>
                    <div class="container-item input nickname">
                        <input id="input-register-nickname" type="text" placeholder="닉네임" maxlength="100">
                    </div>
                    <div class="container-item input contact">
                        <input id="input-register-contact" type="text" placeholder="연락처 ('-' 없이 기재)" maxlength="100">
                    </div>
                    <div class="container-item input address">
                        <input id="input-register-address" type="text" placeholder="주소" maxlength="100">
                    </div>
                    <div class="container-item input birth">
                        <input id="input-register-birth" type="text" placeholder="생년월일 (6자리)" maxlength="100">
                    </div>
                    <div class="container-item buttons">
                        <div id="button-register" class="buttons-item button">회원가입</div>
                    </div>
                </div>
            </div>
            -->
        </div>
        <div class="description">
           <!-- <p>Copyright 2020 by HwangBo Suin.</p> -->
        </div>
    </div>
</body>
</html>