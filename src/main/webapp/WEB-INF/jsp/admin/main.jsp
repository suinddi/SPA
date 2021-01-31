<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="robots" content="noindex, nofollow">
    <meta name="viewport"
          content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <title>관리자 로그인 - MemSys&reg;</title>
    <link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap">
    <link type="text/css" rel="stylesheet" href="/admin/resources/stylesheets/dev.css">
    <script defer src="resources/scripts/user-list-handler.js"></script>
    <script defer src="resources/scripts/user-modify-handler.js"></script>
    <!-- 참조하는 요소가 없다면 가장 위에 위치 -->
    <script defer type="module" src="resources/scripts/dev.js"></script>
</head>
<body class="preload">
    <div id="full-screen-loading" class="body-item full-screen-loading visible">
        <div class="full-screen-loading-item shape"></div>
    </div>
    <div id="trans-loading" class="body-item trans-loading">
        <div class="trans-loading-item text"></div>
    </div>
    <div id="message" class="body-item message">
        <div class="message-item dialog">
            <div class="dialog-item caption">
                <div class="caption-item close-button" tabindex="0"></div>
                <div class="caption-item text"></div>
            </div>
            <div class="dialog-item content"></div>
            <div class="dialog-item buttons"></div>
        </div>
    </div>

    <div class="body-item pane">
        <div class="pane-item logo">
            <!-- <img src="/admin/resources/images/memsys_logo.png" alt=""> -->
        </div>
        <div class="pane-item user">

        </div>
        <div class="pane-item menu">

        </div>
    </div>
    <div class="body-item main">
        <div class="main-item top">

        </div>
        <div class="main-item content">
            <div id="counter-wrapper" class="content-item counter-wrapper">
                <div class="counter-wrapper-item counter total object-frame">
                    <!-- 전체 회원수 -->
                    <div class="counter-item title">전체 회원수</div>
                    <div class="counter-item value">0</div>
                </div>
                <div class="counter-wrapper-item counter register object-frame">
                    <!-- 오늘 가입한 회원수 -->
                    <div class="counter-item title">오늘 가입한 회원수</div>
                    <div class="counter-item value">0</div>
                </div>
                <div class="counter-wrapper-item counter delete object-frame">
                    <!-- 오늘 탈퇴한 회원수 -->
                    <div class="counter-item title">오늘 탈퇴한 회원수</div>
                    <div class="counter-item value">0</div>
                </div>
                <div class="counter-wrapper-item counter sum object-frame">
                    <!-- 지난 30일 누적 = 지난 30일간 가입한 회원 수 - 지난 30일간 탈퇴한 회원 수 -->
                    <div class="counter-item title">30일 누적</div>
                    <div class="counter-item value">+ 0</div>
                </div>
            </div>
            <div id="user-list" class="content-item user-list">
                <div class="user-list-item list-wrapper object-frame">
                    <div class="list-wrapper-item title">회원 목록</div>
                    <table class="list-wrapper-item table">

                        <thead>
                            <tr>
                                <th>No</th>
                                <th>이메일</th>
                                <th>이름</th>
                                <th>닉네임</th>
                                <th>연락처</th>
                                <th>주소</th>
                                <th>생년월일</th>
                                <th>관리자</th>
                                <th>상태</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>admin@sample.com</td>
                                <td>관리자</td>
                                <td>관리자</td>
                                <td>01012345678</td>
                                <td>대구시 중구</td>
                                <td>961021</td>
                                <td>여</td>
                                <td>정상</td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>admin@sample.com</td>
                                <td>관리자</td>
                                <td>관리자</td>
                                <td>01012345678</td>
                                <td>대구시 중구</td>
                                <td>961021</td>
                                <td>여</td>
                                <td>정상</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="user-list-item button-wrapper">
                    <div class="button-wrapper-item left">
                        <div class="left-item modify object-button blue">수정</div>
                    </div>
                    <div class="button-wrapper-item center">
                        <div class="object-page-number-button selected">1</div>
                        <div class="object-page-number-button">2</div>
                        <div class="object-page-number-button">3</div>
                        <div class="object-page-number-button">4</div>
                        <div class="object-page-number-button">5</div>
                        <div class="object-page-number-button">6</div>
                        <div class="object-page-number-button">7</div>
                        <div class="object-page-number-button">8</div>
                        <div class="object-page-number-button">9</div>
                        <div class="object-page-number-button">10</div>
                    </div>
                    <div class="button-wrapper-item right">
                        <div class="left-item delete object-button red">삭제</div>
                        <div class="left-item add object-button green">추가</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 회원등록 폼 -->
    <div id="register" class="register-container-item form-wrap object-window">
        <div class="register-container-item menu">
            <div class="menu-item register title">
                <ul>
                    <li class="menu-register active"><a href="#">회원 등록</a></li>
                </ul>
            </div>
        </div>
        <form class="content-item form">
            <div class="register-container-item content">
                <div class="content-item container">
                    <div class="container-item input">
                        <input name="email" type="email" placeholder="이메일" maxlength="100">
                    </div>
                    <div class="container-item input">
                        <input name="password" type="text" placeholder="비밀번호" maxlength="100">
                    </div>
                    <div class="container-item input">
                        <input name="name" type="text" placeholder="이름" maxlength="10">
                    </div>
                    <div class="container-item input">
                        <input name="nickname" type="text" placeholder="닉네임" maxlength="10">
                    </div>
                    <div class="container-item input">
                        <input name="contact" type="text" placeholder="연락처" maxlength="11">
                    </div>
                    <div class="container-item input">
                        <input name="address" type="text" placeholder="주소" maxlength="250">
                    </div>
                    <div class="container-item input">
                        <input name="birth" type="text" placeholder="생년월일" maxlength="6">
                    </div>
                    <div class="container-item isAdmin">
                         <label>
                            <input name="is_admin" type="checkbox" class="object-slider">
                            <span>관리자</span>
                        </label>
                    </div>
                    <div class="container-item select">
                        <select name="status">
                            <option value="OKY">정상</option>
                            <option value="SUS">정지</option>
                            <option value="DEL">탈퇴</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="container-item buttons register">
                <div class="buttons-item cancel button">취소</div>
                <div class="buttons-item add button ">추가</div>
            </div>
        </form>
    </div>
    <!--// 회원등록 폼 -->

    <!-- ctrl + r : 리팩토링 (Replace all) -->
    <!-- 회원수정 폼 -->
    <div id="modify" class="modify-container-item form-wrap object-window">
        <div class="modify-container-item menu">
            <div class="menu-item modify title">
                <ul>
                    <li class="menu-modify active"><a href="#">회원 수정</a></li>
                </ul>+-
            </div>
        </div>
        <form class="content-item form">
            <div class="modify-container-item content">
                <div class="content-item container">
                    <input name="index" type="number" hidden>
                    <div class="container-item input">
                        <input name="email" type="email" placeholder="이메일" maxlength="100">
                    </div>
                    <div class="container-item input">
                        <input name="password" type="text" placeholder="비밀번호" maxlength="100">
                    </div>
                    <div class="container-item input">
                        <input name="name" type="text" placeholder="이름" maxlength="10">
                    </div>
                    <div class="container-item input">
                        <input name="nickname" type="text" placeholder="닉네임" maxlength="10">
                    </div>
                    <div class="container-item input">
                        <input name="contact" type="text" placeholder="연락처" maxlength="11">
                    </div>
                    <div class="container-item input">
                        <input name="address" type="text" placeholder="주소" maxlength="250">
                    </div>
                    <div class="container-item input">
                        <input name="birth" type="text" placeholder="생년월일" maxlength="6">
                    </div>
                    <div class="container-item isAdmin">
                        <label>
                            <input name="is_admin" type="checkbox" class="object-slider">
                            <span>관리자</span>
                        </label>
                    </div>
                    <div class="container-item select">
                        <select name="status">
                            <option value="OKY">정상</option>
                            <option value="SUS">정지</option>
                            <option value="DEL">탈퇴</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="container-item buttons modify">
                <div class="buttons-item cancel button">취소</div>
                <div class="buttons-item modify button">수정</div>
            </div>
        </form>
    </div>
    <!--// 회원수정 폼 -->
</body>
</html>