package com.memsys.web.services;

import com.memsys.web.Constants;
import com.memsys.web.daos.CounterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class CounterService {
    private final CounterDao counterDao;
    private final DataSource dataSource;

    @Autowired
    public CounterService(CounterDao counterDao, DataSource dataSource) {
        this.counterDao = counterDao;
        this.dataSource = dataSource;
    }

    public int[] getMemberCount()
            throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.counterDao.selectMemberCount(connection);
        }
    }
}
