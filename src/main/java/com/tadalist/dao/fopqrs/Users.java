package com.tadalist.dao.fopqrs;
import java.sql.Date;

public class Users {
//    private final Users.NotificationPreference NotificationPreference;
    private int UserID;
    private String UserEmail;
    private String UserName;
    private String UserPassword;
    private enum NotificationPreference {EMAIL,SMS,NOTIFICATION};
    java.sql.Date dt = new java.sql.Date(System.currentTimeMillis());
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String LastLogin = sdf.format(dt);

    public Users(int UserID, String UserEmail, String UserName, String UserPassword, String LastLogin) {
        this.UserID = UserID;
        this.UserEmail = UserEmail;
        this.UserName = UserName;
        this.UserPassword = UserPassword;
//        this.NotificationPreference = notificationPreference;
        this.LastLogin = LastLogin;
    }

    //UserID
    public int getUserID(){
        return UserID;
    }
    public void setUserID(int UserID){
        this.UserID = UserID;
    }

    //UserEmail
    public String getUserEmail(){
        return UserEmail;
    }
    public void setUserEmail(String UserEmail){
        this.UserEmail = UserEmail;
    }

    //UserName
    public String getUserName(){
        return UserName;
    }
    public void setUserName(String UserName){
        this.UserName = UserName;
    }

    //UserPassword
    public String getUserPassword(){
        return UserPassword;
    }
    public void setUserPassword(String UserPassword){
        this.UserPassword = UserPassword;
    }

    //LastLogin
    public String getLastLogin (){
        return LastLogin;
    }
    public void setLastLogin(){
        this.LastLogin = LastLogin;
    }

    @Override
    public String toString(){
        return "Users [UserID : " + UserID + ", UserEmail=" + UserEmail + ", UserName=" + UserName +", UserPassword: "+ UserPassword +", "+LastLogin+"]";
    }
}
