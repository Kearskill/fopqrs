package com.tadalist.dao.fopqrs;
import java.sql.Date;

public class recurringTask {
    public enum recurrenceType {DAILY, WEEKLY, MONTHLY};

    private recurrenceType recurrenceType;
    private int RecurringID;
    private int TaskID;
    private String title;
    private String description;


    public recurringTask(int recurringID, String title,String description, recurrenceType recurrenceType) {
        this.RecurringID = recurringID;
        this.title = title;
        this.description = description;
        this.recurrenceType = recurrenceType;
    }


    public recurrenceType getRecurrenceType(){
        return recurrenceType;
    }

    public void setRecurrenceType(recurrenceType recurrenceType){
        this.recurrenceType = recurrenceType;
    }
    public int getRecurringID() {
        return RecurringID;
    }

    public void setRecurringID(int recurringID) {
        RecurringID = recurringID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "recurringTask{" +
                "recurrenceType=" + recurrenceType +
                ", RecurringID=" + RecurringID +
                ", TaskID=" + TaskID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
