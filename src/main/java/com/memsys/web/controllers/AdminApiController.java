package com.memsys.web.controllers;

import com.memsys.web.enums.AdminLoginResult;
import com.memsys.web.enums.RegisterResult;
import com.memsys.web.services.AdminApiService;
import com.memsys.web.services.SecurityService;
import com.memsys.web.vos.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping(value = "/admin/apis/")
public class AdminApiController extends StandardRestController {
    private final AdminApiService adminApiService;
    private final SecurityService securityService;

    @Autowired
    public AdminApiController(AdminApiService adminApiService, SecurityService securityService) {
        this.adminApiService = adminApiService;
        this.securityService = securityService;
    }

    @RequestMapping(value = "login")
    public void login(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(name = "email", defaultValue = "") String email,
                      @RequestParam(name = "password", defaultValue = "") String password)
            throws IOException, SQLException {

        AdminLoginResult adminLoginResult;
        if (this.securityService.isIpBlocked(request.getRemoteAddr())) {
            adminLoginResult = AdminLoginResult.IP_BLOCKED;
        } else if (this.securityService.isLoginAttemptLimitExceeded(request.getRemoteAddr())) {     // ip가 로그인 시도 횟수를 초과했으면
            adminLoginResult = AdminLoginResult.LIMIT_EXCEEDED;                                     // 로그인 결과는 'LIMIT_EXCEEDED'
        } else {
            AdminLoginVo adminLoginVo = new AdminLoginVo(email, password);
            if (!adminLoginVo.isNormalized()) {
                adminLoginResult = AdminLoginResult.NORMALIZATION_FAILURE;
            } else {
                adminLoginResult = this.adminApiService.login(request.getSession(), adminLoginVo);
            }
            LoginAttemptVo loginAttemptVo = new LoginAttemptVo(
                    request.getRemoteAddr(),
                    adminLoginVo.getEmail(),
                    adminLoginVo.getPassword(),
                    adminLoginResult.name()
            );
            this.securityService.putLoginAttempt(loginAttemptVo);

            if (adminLoginResult == AdminLoginResult.FAILURE
                    && this.securityService.isLoginFailureLimitExceeded(request.getRemoteAddr())) {
                this.securityService.putBlockedIp(request.getRemoteAddr());
            }
        }

        response.getWriter().print(adminLoginResult.name());
        response.getWriter().close();
    }

    @RequestMapping(value = "/get-user")
    public void getUser(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(name="page", defaultValue = "1") String page) throws SQLException, IOException {
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException ignored) {
            pageNumber = 1;
        }

        GetUserVo getUserVo = this.adminApiService.getUser(pageNumber);
        JSONArray jsonUsers = new JSONArray();
        for (UserVo userVo : getUserVo.getUserVos()) {
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("index", userVo.getIndex());
            jsonUser.put("email", userVo.getEmail());
            jsonUser.put("name", userVo.getName());
            jsonUser.put("nickname", userVo.getNickname());
            jsonUser.put("contact", userVo.getContact());
            jsonUser.put("address", userVo.getAddress());
            jsonUser.put("birth", userVo.getBirth());
            jsonUser.put("isAdmin", userVo.isAdmin() ? "true" : "false");
            jsonUser.put("status", userVo.getStatus());
            jsonUser.put("createdAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userVo.getCreatedAt()));
            jsonUser.put("statusChangedAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userVo.getStatusChangedAt()));
            jsonUser.put("passwordModifiedAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userVo.getPasswordModifiedAt()));
            jsonUser.put("signedAt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userVo.getSignedAt()));
            jsonUsers.put(jsonUser);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("startPage", getUserVo.getStartPage());
        jsonObject.put("endPage", getUserVo.getEndPage());
        jsonObject.put("requestPage", getUserVo.getRequestPage());
        jsonObject.put("users", jsonUsers);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(jsonObject.toString(4));
    }

    //스프링에서는 알아서 json 으로 변환해서 화면에 보여줌
//  @RequestMapping(value = "get-user-test")
//  public GetUserVo getUserTest(HttpServletRequest request, HttpServletResponse response,
//                               @RequestParam(name = "page", defaultValue = "1") String page)
//          throws IOException, SQLException {
//      int pageNumber;
//      try {
//          pageNumber = Integer.parseInt(page);
//      } catch (NumberFormatException ignored) {
//          pageNumber = 1;
//      }
//
//      return this.adminApiService.getUser(pageNumber);
//  }

    @RequestMapping(value = "delete-user")
    public void deleteUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name = "index", defaultValue = "") String index) throws SQLException, IOException {
        DeleteUserVo deleteUserVo = new DeleteUserVo(index);
        if (!deleteUserVo.isNormalized()) {
            response.getWriter().print("FAILURE");
        } else {
            this.adminApiService.deleteUser(deleteUserVo);
            response.getWriter().print("SUCCESS");
        }
    }

    @RequestMapping(value = "register")
    public void register(HttpServletRequest request, HttpServletResponse response,
                         // name 은 input 에 적힌 name 과 동일
                         @RequestParam(name = "email", defaultValue = "") String email,
                         @RequestParam(name = "password", defaultValue = "") String password,
                         @RequestParam(name = "name", defaultValue = "") String name,
                         @RequestParam(name = "nickname", defaultValue = "") String nickname,
                         @RequestParam(name = "contact", defaultValue = "") String contact,
                         @RequestParam(name = "address", defaultValue = "") String address,
                         @RequestParam(name = "birth", defaultValue = "") String birth,
                         @RequestParam(name = "isAdmin", defaultValue = "") String isAdmin,
                         @RequestParam(name = "status", defaultValue = "") String status) throws IOException, SQLException {

        RegisterVo registerVo = new RegisterVo(email,password,name,nickname,contact,address,birth,isAdmin,status);
        if(!registerVo.isNormalized()) {
            response.getWriter().print("NORMALIZATION_FAILURE");
        } else {
            RegisterResult registerResult = this.adminApiService.register(registerVo);
            response.getWriter().print(registerResult.name());
        }
    }
}