package com.memsys.web.controllers;

import com.memsys.web.Constants;
import com.memsys.web.vos.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdminController extends StandardWebController{

    @RequestMapping(value = "manager")   
    public String manager(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        boolean isSigned = 
                session.getAttribute(Constants.USER_VO_SESSION_ATTRIBUTE_NAME) != null &&
                session.getAttribute(Constants.USER_VO_SESSION_ATTRIBUTE_NAME) instanceof UserVo;
        if (isSigned) {
            // 메인 페이지 보여주기

            return "admin/main";
        } else {
            // 로그인 페이지 보여주기
            // prefix = /WEB-INF/jsp/
            // return = /WEB-INF/jsp/admin/login.jsp
            return "admin/login";
        }
    }


}
