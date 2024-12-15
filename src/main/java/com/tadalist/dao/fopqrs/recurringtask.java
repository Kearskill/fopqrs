package com.tadalist.dao.fopqrs;
import java.sql.Date;

public class recurringtask {
    private int RecurringID;
    private int TaskID;
    private enum RecurrenceType{ONE, DAILY, WEEKLY,MONTHLY};
    private Date NextDueDate;
    private Date RecurrenceEnd;
    private int RecurrenceFrequency;
    private Date Reminder;

    public int getRecurringID() {
        return RecurringID;
    }

    public void setRecurringID(int recurringID) {
        RecurringID = recurringID;
    }

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public Date getNextDueDate() {
        return NextDueDate;
    }

    public void setNextDueDate(Date nextDueDate) {
        NextDueDate = nextDueDate;
    }

    public Date getRecurrenceEnd() {
        return RecurrenceEnd;
    }

    public void setRecurrenceEnd(Date recurrenceEnd) {
        RecurrenceEnd = recurrenceEnd;
    }

    public int getRecurrenceFrequency() {
        return RecurrenceFrequency;
    }

    public void setRecurrenceFrequency(int recurrenceFrequency) {
        RecurrenceFrequency = recurrenceFrequency;
    }

    public Date getReminder() {
        return Reminder;
    }

    public void setReminder(Date reminder) {
        Reminder = reminder;
    }

    @Override
    public String toString() {
        return "recurringtask{" +
                "RecurringID=" + RecurringID +
                ", TaskID=" + TaskID +
                ", NextDueDate=" + NextDueDate +
                ", RecurrenceEnd=" + RecurrenceEnd +
                ", RecurrenceFrequency=" + RecurrenceFrequency +
                ", Reminder=" + Reminder +
                '}';
    }
}
