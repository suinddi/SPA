package com.memsys.web.services;

import com.memsys.web.Constants;
import com.memsys.web.daos.AdminApiDao;
import com.memsys.web.enums.AdminLoginResult;
import com.memsys.web.enums.RegisterResult;
import com.memsys.web.vos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class AdminApiService {
    private final AdminApiDao adminApiDao;
    private final DataSource dataSource;

    @Autowired
    public AdminApiService(AdminApiDao adminApiDao, DataSource dataSource) {
        this.adminApiDao = adminApiDao;
        this.dataSource = dataSource;
    }

    public AdminLoginResult login(HttpSession session, AdminLoginVo adminLoginVo)
            throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            UserVo userVo = this.adminApiDao.selectUser(connection, adminLoginVo);
            if (userVo == null) {
                return AdminLoginResult.FAILURE;
            } else {
                session.setAttribute(Constants.USER_VO_SESSION_ATTRIBUTE_NAME, userVo);
                return AdminLoginResult.SUCCESS;
            }
        }
    }

    public GetUserVo getUser(int pageNumber) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.adminApiDao.selectUsers(connection, pageNumber);
        }
    }

    public void deleteUser(DeleteUserVo deleteUserVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            this.adminApiDao.deleteUser(connection, deleteUserVo);
        }
    }

    public RegisterResult register(RegisterVo registerVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            if (this.adminApiDao.isUserEmailDefined(connection, registerVo.getEmail())) {
                return RegisterResult.EMAIL_DUPLICATE;
            } else if (this.adminApiDao.isUserNicknameDefined(connection, registerVo.getNickname())) {
                return RegisterResult.EMAIL_DUPLICATE;
            } else if (registerVo.getStatus() == "OKY" | registerVo.getStatus() == "SUS" | registerVo.getStatus() == "DEL" ){
                //전달 받은 'status'가 'OKY', 'SUS', 'DEL' 중 하나 일 것. 이중 하나가 아니라면 'OKY' 로 갈음.
                return null;
            } else {
                boolean success = this.adminApiDao.insertUser(connection, registerVo);
                return success ? RegisterResult.SUCCESS : RegisterResult.FAILURE;
            }
        }
    }
}