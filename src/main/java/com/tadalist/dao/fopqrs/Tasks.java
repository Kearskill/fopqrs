//TASK MODEL CLASS

package com.tadalist.dao.fopqrs;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Calendar;
public class Tasks {

    public enum Priority{
        LOW,MEDIUM,HIGH
    }

    public enum Status{
        PENDING,IN_PROGRESS,COMPLETED // enum cannot have spaces
    }
    private int TaskId;
    private String Title;
    private String Description;
    private Date DueDate;
    private Priority priority;
    private Status status;
    private Timestamp CreatedAt;
    private Timestamp UpdatedAt;
    private short IsRecurring;//boolean
    private int ParentTaskID;
    private int StreakCount;
    private int CreatedByUser;
    private int AssignedToUser;


    //Add Task ID later on
    public Tasks(int TaskId,String Title,String Description,Date DueDate,Priority priority,Status status,Timestamp CreatedAt,
                 Timestamp UpdatedAt,short IsRecurring,int ParentTaskID,int StreakCount,
                 int CreatedByUser,int AssignedToUser){
        this.TaskId = TaskId;
        this.Title = Title;
        this.Description = Description;
        this.DueDate = DueDate;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.IsRecurring = IsRecurring;
        this.ParentTaskID = ParentTaskID;
        this.StreakCount = StreakCount;
        this.CreatedByUser = CreatedByUser;
        this.AssignedToUser = AssignedToUser;
        this.status = status;
        this.priority = priority;
    }

    //getters
    public int getTaskId(){
        return TaskId;
    }
    public String getTitle(){
        return Title;
    }
    public String getDescription() {
        return Description;
    }
    public Date getDueDate() {
        return DueDate;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public Timestamp getUpdatedAt() {
        return UpdatedAt;
    }

    public Tasks.Priority getPriority() {
        return priority;
    }

    public void setPriority(Tasks.Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public short getIsRecurring() {
        return IsRecurring;
    }

    public int getParentTaskID() {
        return ParentTaskID;
    }

    public int getStreakCount() {
        return StreakCount;
    }

    public int getCreatedByUser() {
        return CreatedByUser;
    }

    public int getAssignedToUser() {
        return AssignedToUser;
    }

    public Status getStatus() {
        return status;
    }
//setters



    public void setTaskId(int TaskId){
        this.TaskId = TaskId;
    }
    public void setTitle(String Title){
        this.Title = Title;
    }
    public void setDescription(String description) {
        Description = description;
    }

    public void setDueDate(Date dueDate) {
        DueDate = dueDate;
    }

    public void setCreatedAt(Timestamp createdAt) {
        CreatedAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        UpdatedAt = updatedAt;
    }

    public void setIsRecurring(short isRecurring) {
        IsRecurring = isRecurring;
    }

    public void setParentTaskID(int parentTaskID) {
        ParentTaskID = parentTaskID;
    }

    public void setStreakCount(int streakCount) {
        StreakCount = streakCount;
    }

    public void setCreatedByUser(int createdByUser) {
        CreatedByUser = createdByUser;
    }

    public void setAssignedToUser(int assignedToUser) {
        AssignedToUser = assignedToUser;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "TaskId=" + TaskId +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", DueDate=" + DueDate +
                ", CreatedAt=" + CreatedAt +
                ", UpdatedAt=" + UpdatedAt +
                ", IsRecurring=" + IsRecurring +
                ", ParentTaskID=" + ParentTaskID +
                ", StreakCount=" + StreakCount +
                ", CreatedByUser=" + CreatedByUser +
                ", AssignedToUser=" + AssignedToUser +
                ", Priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
