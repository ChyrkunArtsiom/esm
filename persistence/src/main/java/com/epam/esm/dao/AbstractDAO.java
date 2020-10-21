package com.epam.esm.dao;

import java.sql.SQLException;

public interface AbstractDAO {
    int getCount() throws SQLException;
}
