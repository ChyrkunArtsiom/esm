package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.datasource.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagDAO implements AbstractDAO {

    private final static String SQL_SELECT_COUNT = "SELECT COUNT(*) FROM esm_module2.tags";

    @Override
    public int getCount() throws SQLException {
        int count = 0;
        try(Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        }
        return count;
    }
}
