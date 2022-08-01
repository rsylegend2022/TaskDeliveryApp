package com.rsy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class TaskPatch implements Serializable {
    String usrName;
    String taskName;
}
