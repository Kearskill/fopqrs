package com.tadalist.dao.fopqrs;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Users {
    private int UserID;
    private String UserEmail;
    private String UserName;
    private String UserPassword;
    private String NotificationPreference;
    private Timestamp LastLogin;

    public Users(int UserID, String UserEmail, String UserName, String UserPassword, String NotificationPreference, Timestamp LastLogin) {
        this.UserID = UserID;
        this.UserEmail = UserEmail;
        this.UserName = UserName;
        this.UserPassword = UserPassword;
        this.NotificationPreference = NotificationPreference;
        this.LastLogin = LastLogin;
    }

    //Constructor without ID
    public Users(String UserEmail, String UserName, String UserPassword){
        this.UserEmail = UserEmail;
        this.UserName = UserName;
        this.UserPassword = UserPassword;
        this.NotificationPreference = "Email";
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

    //Notification Preference
    public String getNotificationPreference(){
        return NotificationPreference;
    }
    public void setNotificationPreference(String NotificationPreference){
        this.NotificationPreference = NotificationPreference;
    }

    //LastLogin
    public Timestamp getLastLogin (){
        return LastLogin;
    }
    public void setLastLogin(Timestamp LastLogin){
        this.LastLogin = LastLogin;
    }

    @Override
    public String toString(){
        return "Users [UserID : " + UserID + ", UserEmail=" + UserEmail + ", UserName=" + UserName +", UserPassword: "+ UserPassword + ", NotificationPreference: " + NotificationPreference +", LastLogin: "+LastLogin+"]";
    }
}
