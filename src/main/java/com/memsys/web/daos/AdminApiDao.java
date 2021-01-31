package com.memsys.web.daos;

import com.memsys.web.vos.*;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class AdminApiDao {
    public UserVo selectUser(Connection connection, AdminLoginVo adminLoginVo) throws
            SQLException {
        UserVo userVo = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT user_index, user_email, user_password, user_name, user_nickname, " +
                        "user_contact, user_address, user_birth, user_admin_flag, user_status, " +
                        "user_created_at, user_status_changed_at, user_password_modified_at, " +
                        "user_signed_at FROM `memsys`.`users` WHERE `user_email`=? AND `user_password`=? LIMIT 1")) {
            preparedStatement.setString(1, adminLoginVo.getEmail());
            preparedStatement.setString(2, adminLoginVo.getHashedPassword());
            System.out.println(adminLoginVo.getEmail());
            System.out.println(adminLoginVo.getHashedPassword());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    userVo = new UserVo(
                            resultSet.getInt("user_index"),
                            resultSet.getString("user_email"),
                            resultSet.getString("user_password"),
                            resultSet.getString("user_name"),
                            resultSet.getString("user_nickname"),
                            resultSet.getString("user_contact"),
                            resultSet.getString("user_address"),
                            resultSet.getString("user_birth"),
                            resultSet.getBoolean("user_admin_flag"),
                            resultSet.getString("user_status"),
                            resultSet.getDate("user_created_at"),
                            resultSet.getDate("user_status_changed_at"),
                            resultSet.getDate("user_password_modified_at"),
                            resultSet.getDate("user_signed_at")
                    );
                }
            }
        }
        return userVo;
    }

    private final int ROWS_PER_PAGE = 10; // 한 페이지 당 보여줄 회원의 수

    public GetUserVo selectUsers(Connection connection, int pageNumber) throws SQLException {
        int startPage = pageNumber < 6 ? 1 : pageNumber - 5;
        int endPage = pageNumber < 6 ? 10 : pageNumber + 5;
        int requestPage = pageNumber;
        ArrayList<UserVo> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users`")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    int totalUserCount = resultSet.getInt("count"); // 10
                    int maxPageNumber = (int)Math.ceil(totalUserCount / ROWS_PER_PAGE); // ceil 은 double 타입 (40 / 10)
                    if (endPage > maxPageNumber) {  // 10 > 4
                        endPage = maxPageNumber; // endPage = 4
                    }
                }
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT user_index, user_email, user_password, user_name, user_nickname, " +
                        "user_contact, user_address, user_birth, user_admin_flag, user_status, " +
                        "user_created_at, user_status_changed_at, user_password_modified_at, " +
                        "user_signed_at FROM `memsys`.`users` LIMIT ?, ?")) {
            preparedStatement.setInt(1, (pageNumber - 1) * ROWS_PER_PAGE);
            preparedStatement.setInt(2, ROWS_PER_PAGE);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    UserVo userVo = new UserVo(
                            resultSet.getInt("user_index"),
                            resultSet.getString("user_email"),
                            resultSet.getString("user_password"),
                            resultSet.getString("user_name"),
                            resultSet.getString("user_nickname"),
                            resultSet.getString("user_contact"),
                            resultSet.getString("user_address"),
                            resultSet.getString("user_birth"),
                            resultSet.getBoolean("user_admin_flag"),
                            resultSet.getString("user_status"),
                            resultSet.getDate("user_created_at"),
                            resultSet.getDate("user_status_changed_at"),
                            resultSet.getDate("user_password_modified_at"),
                            resultSet.getDate("user_signed_at")
                    );
                    users.add(userVo);  // users Array 객체에 userVo 넣음
                }
            }
        }
        return new GetUserVo(startPage, endPage, requestPage, users);
    }

    public void deleteUser(Connection connection, DeleteUserVo deleteUserVo) throws SQLException {
        for (int userIndex : deleteUserVo.getIndexArray()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE `memsys`.`users` SET `user_status` = 'DEL', `user_status_changed_at`=NOW() WHERE `user_index`=?")) {
                    preparedStatement.setInt(1, userIndex);
                    preparedStatement.execute();
            }
        }
    }

    public boolean isUserEmailDefined(Connection connection, String email) throws SQLException {
        boolean exist = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(`user_index`) AS `count` FROM `memsys`.`users` WHERE `user_email`=?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                exist = resultSet.getInt("count") > 0;
            }
            return exist;
        }
    }

    public boolean isUserNicknameDefined(Connection connection, String nickname) throws SQLException {
        boolean exist = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(`user_index`) AS `count` FROM `member`.`users` WHERE `user_nickname`=?")) {
            preparedStatement.setString(1, nickname);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                exist = resultSet.getInt("count") > 0;
            }
            return exist;
        }
    }

    public boolean insertUser(Connection connection, RegisterVo registerVo) throws SQLException {

        String query = "INSERT INTO `memsys`.`users` \n" +
                "(`user_email`, `user_password`, `user_name`, `user_nickname`, `user_contact`, " +
                "`user_address`, `user_birth`, `user_admin_flag`, `user_status`) VALUES(?,?,?,?,?,?,?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, registerVo.getEmail());
            preparedStatement.setString(2, registerVo.getHashedPassword());
            preparedStatement.setString(3, registerVo.getName());
            preparedStatement.setString(4, registerVo.getNickname());
            preparedStatement.setString(5, registerVo.getContact());
            preparedStatement.setString(6, registerVo.getAddress());
            preparedStatement.setString(7, registerVo.getBirth());
            preparedStatement.setBoolean(8, registerVo.getIsAdmin());
            preparedStatement.setString(9, registerVo.getStatus());
            preparedStatement.execute();
        }
        return this.isUserEmailDefined(connection, registerVo.getEmail());
    }
}