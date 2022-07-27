package com.example.android.recyclerview;

public class RoleWrapper {

    private boolean roleIsVolunteer;

    private static RoleWrapper roleWrapper;

    private static int taskMode;

    public static RoleWrapper getInstance() {
        if (roleWrapper == null) {
            roleWrapper = new RoleWrapper();
        }
        return roleWrapper;
    }

    public RoleWrapper() {
    }

    public void setRoleIsVolunteer(boolean is) {
        roleIsVolunteer = is;
    }

    public boolean getRoleIsVolunteer() {
        return roleIsVolunteer;
    }

    public void setTaskMode(int taskMode) {
        this.taskMode = taskMode;
    }

    public int getTaskMode() {
        return taskMode;
    }


}
