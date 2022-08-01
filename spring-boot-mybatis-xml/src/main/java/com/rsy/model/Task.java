package com.rsy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Task implements Serializable {
    String name;
    String description;
    String address;
    String type;
    String urgency;
    String status;
    String workload;
    String creator;
    String owner;
}
