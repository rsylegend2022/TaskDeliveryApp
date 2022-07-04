package com.example.android.recyclerview;


public enum TaskStatus {
    Created("created", 1),
    InProgress("in_progress", 2),
    Finished("finished", 3);

    private final String literal;
    private final Integer code;

    TaskStatus(String literal, Integer code) {
        this.literal = literal;
        this.code = code;
    }

    @Override
    public String toString() {
        return literal;
    }

}

