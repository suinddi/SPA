package com.memsys.web.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class StandardController {
    @ExceptionHandler  // abstract 클래스로 만든 이유 : web 용과 rest 용에 오류 발생시 반환해야 할 값이 다르기 때문
    protected abstract void handleException(HttpServletRequest request, HttpServletResponse response, Exception exception);
}

