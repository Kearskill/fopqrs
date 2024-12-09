package com.tadalist.dao.fopqrs;
import java.sql.Date;

public class Calendar {
    private int EntryID;
    private int TaskID;
    private Date Date;
    private int UserID;

    public int getEntryID() {
        return EntryID;
    }

    public void setEntryID(int entryID) {
        EntryID = entryID;
    }

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "EntryID=" + EntryID +
                ", TaskID=" + TaskID +
                ", Date=" + Date +
                ", UserID=" + UserID +
                '}';
    }
}
