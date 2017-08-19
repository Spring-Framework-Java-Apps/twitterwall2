package org.woehlke.twitterwall.frontend.model;

import org.springframework.data.domain.Page;
import org.woehlke.twitterwall.oodm.model.Task;
import org.woehlke.twitterwall.oodm.model.TaskHistory;

import java.io.Serializable;

/**
 * Created by tw on 12.07.17.
 */
public class TaskResourceModel implements Serializable {

    private Task oneTask;
    private Page<TaskHistory> taskHistoryList;

    public TaskResourceModel(Task oneTask, Page<TaskHistory> taskHistoryList) {
        this.oneTask = oneTask;
        this.taskHistoryList = taskHistoryList;
    }

    public TaskResourceModel() {
    }

    public Task getOneTask() {
        return oneTask;
    }

    public void setOneTask(Task oneTask) {
        this.oneTask = oneTask;
    }

    public Page<TaskHistory> getTaskHistoryList() {
        return taskHistoryList;
    }

    public void setTaskHistoryList(Page<TaskHistory> taskHistoryList) {
        this.taskHistoryList = taskHistoryList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskResourceModel)) return false;

        TaskResourceModel that = (TaskResourceModel) o;

        if (oneTask != null ? !oneTask.equals(that.oneTask) : that.oneTask != null) return false;
        return taskHistoryList != null ? taskHistoryList.equals(that.taskHistoryList) : that.taskHistoryList == null;
    }

    @Override
    public int hashCode() {
        int result = oneTask != null ? oneTask.hashCode() : 0;
        result = 31 * result + (taskHistoryList != null ? taskHistoryList.hashCode() : 0);
        return result;
    }
}
