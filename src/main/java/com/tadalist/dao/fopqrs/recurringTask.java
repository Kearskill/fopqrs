package com.tadalist.dao.fopqrs;


import java.sql.Date;

public class recurringTask {
    enum recurrenceType{
        DAILY,WEEKLY,MONTHLY
    }

    private int TaskID;
    private int recurringID;
    private recurrenceType recurrenceType;
    private Date NextDueDate;
    private Date recurrenceEnd;
    private int recurrenceFrequency;

    public recurringTask(int recurringID,int taskID,recurrenceType recurrenceType,
                         Date NextDueDate,Date recurrenceEnd,int recurrenceFrequency){
        this.recurringID = recurringID;
        this.TaskID = taskID;
        this.recurrenceType = recurrenceType;
        this.NextDueDate = NextDueDate;
        this.recurrenceEnd = recurrenceEnd;
        this.recurrenceFrequency = recurrenceFrequency;

    }

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public int getRecurringID() {
        return recurringID;
    }

    public void setRecurringID(int recurringID) {
        this.recurringID = recurringID;
    }

    public recurringTask.recurrenceType getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(recurringTask.recurrenceType recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public Date getNextDueDate() {
        return NextDueDate;
    }

    public void setNextDueDate(Date nextDueDate) {
        NextDueDate = nextDueDate;
    }

    public Date getRecurrenceEnd() {
        return recurrenceEnd;
    }

    public void setRecurrenceEnd(Date recurrenceEnd) {
        this.recurrenceEnd = recurrenceEnd;
    }

    public int getRecurrenceFrequency() {
        return recurrenceFrequency;
    }

    public void setRecurrenceFrequency(int recurrenceFrequency) {
        this.recurrenceFrequency = recurrenceFrequency;
    }
}
