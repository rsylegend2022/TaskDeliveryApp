package com.example.android.recyclerview;


public enum TaskStatus {
    Created("Created", 1),
    InProgress("InProgress", 2),
    Finished("Finished", 3);

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

