package com.example.android.recyclerview;

public class TaskHolder {
    public String name;
    public String description;
    public String address;
    public String type;
    public String urgency;
    public String workload;
    public TaskStatus status;
    public String creator;
    public String owner;

    public TaskHolder(String name, String description, String address, String type, String urgency,
                      String workload, TaskStatus status, String creator, String owner) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.type = type;
        this.urgency = urgency;
        this.workload = workload;
        this.status = status;
        this.creator = creator;
        this.owner = owner;
    }

    public TaskHolder() {

    }
}
