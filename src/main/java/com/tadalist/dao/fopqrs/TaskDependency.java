// taskdependencymodel//
package com.tadalist.dao.fopqrs;

public class TaskDependency {
    private int dependencyId;
    private int taskId;
    private int dependentTaskId;

    public TaskDependency() {}

    public TaskDependency(int dependencyId, int taskId, int dependentTaskId) {
        this.dependencyId = dependencyId;
        this.taskId = taskId;
        this.dependentTaskId = dependentTaskId;
    }

    public int getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(int dependencyId) {
        this.dependencyId = dependencyId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getDependentTaskId() {
        return dependentTaskId;
    }

    public void setDependentTaskId(int dependentTaskId) {
        this.dependentTaskId = dependentTaskId;
    }

    @Override
    public String toString() {
        return "TaskDependency{" +
                "dependencyId=" + dependencyId +
                ", taskId=" + taskId +
                ", dependentTaskId=" + dependentTaskId +
                '}';
    }

}