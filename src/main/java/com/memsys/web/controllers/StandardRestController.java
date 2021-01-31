package com.memsys.web.controllers;

import com.memsys.web.enums.ApiResponse;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class StandardRestController extends StandardController{
    @Override
    protected void handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        // TODO : API 전용 오류 페이지 구축
        try {
            response.getWriter().print(ApiResponse.INTERNAL_SERVER_ERROR.name());
            response.setStatus(500);  // 서버 에러시 "INTERNAL_SERVER_ERROR" 아닌 "#" 으로 표기

            exception.printStackTrace();
        } catch (Exception ignored) {
            // 사용하지 않을 예외 객체의 변수 이름은 'ignored'
            // 요청 처리 포기
        }
    }
}
