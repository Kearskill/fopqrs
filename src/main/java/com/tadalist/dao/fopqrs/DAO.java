package com.tadalist.dao.fopqrs;

import java.sql.SQLException;
import java.util.List;

public interface DAO<Users> {
    Users get(int UserID) throws SQLException;
    List<Users> getAll() throws SQLException;
    int save (Users user) throws SQLException;
    int insert (Users user) throws SQLException;
    int update (Users user) throws SQLException;
    int delete (Users user) throws SQLException;
}
