package com.tadalist.dao.fopqrs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO{
    public static void addUser(Users user) throws SQLException{
        String sql = "insert into users(Email,UserName,UserPassword,NotificationPreference,LastLogin) values (?,?,?,?,?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1,user.getUserEmail());
            stmt.setString(2,user.getUserName());
            stmt.setString(3,user.getUserPassword());
            stmt.setString(4,user.getNotificationPreference().name());
            stmt.setTimestamp(5,user.getLastLogin());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()){
                    user.setUserID(generatedKeys.getInt(1));
                }
            }
        }
    }
    public static List<Users> getAllUsers() throws SQLException {
        List<Users> usersList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usersList.add(mapResultSetToUsers(rs));
            }
        }
        return usersList;
    }
    private static Users mapResultSetToUsers(ResultSet rs) throws SQLException {
        return new Users(
                rs.getInt("UserID"),
                rs.getString("Email"),
                rs.getString("UserName"),
                rs.getString("UserPassword"),
                Users.NotificationPreference.valueOf(rs.getString("NotificationPreference")),
                rs.getTimestamp("LastLogin")
        );
    }
    public static Users getUserById(int userID) throws SQLException {
        String sql = "SELECT * FROM users WHERE UserID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsers(rs);
                }
            }
        }
        return null;
    }

    public static void editUser(Users users) throws SQLException{
        String sql = "UPDATE users SET Email=?, UserName=?, UserPassword=?, NotificationPreference=?, LastLogin=? WHERE UserID=?";
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1,users.getUserEmail());
            stmt.setString(2,users.getUserName());
            stmt.setString(3,users.getUserPassword());
            stmt.setString(4,users.getNotificationPreference().name());
            stmt.setTimestamp(5,users.getLastLogin());
            stmt.setInt(6,users.getUserID());


            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    users.setUserID(generatedKeys.getInt(1));
                }
            }

        }
    }
    public static void deleteUser(int UserID) throws SQLException{
        String sql = "delete from users where UserID = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,UserID);
            stmt.executeUpdate();
        }
    }
}