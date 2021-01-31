package com.memsys.web.daos;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CounterDao {
    public int[] selectMemberCount(Connection connection)
            throws SQLException {
        int[] countArray = new int[4];
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT (SELECT COUNT(`user_index`) FROM `memsys`.`users`) AS `totalCount`, " +
                        "       (SELECT COUNT(`user_index`) FROM `memsys`.`users` WHERE DATE (`user_created_at`) = CURDATE()) AS `registerCount`, " +
                        "       (SELECT COUNT(`user_index`) FROM `memsys`.`users` WHERE DATE(`user_status_changed_at`) = CURDATE() AND `user_status` = 'DEL') AS `deleteCount`, " +
                        "       (" +
                        "           (SELECT COUNT(`user_index`) FROM `memsys`.`users` WHERE `user_created_at` >= DATE_SUB(NOW(), INTERVAL 30 DAY)) -" +
                        "           (SELECT COUNT(`user_index`) FROM `memsys`.`users` WHERE `user_created_at` >= DATE_SUB(NOW(), INTERVAL 30 DAY) AND `user_status` = 'DEL')" +
                        "       ) AS `sumCount`")
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    countArray[0] = resultSet.getInt("totalCount");
                    countArray[1] = resultSet.getInt("registerCount");
                    countArray[2] = resultSet.getInt("deleteCount");
                    countArray[3] = resultSet.getInt("sumCount");
                }
            }
        }
        return countArray;
    }
}
