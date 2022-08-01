package com.rsy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QueryInCriterion<T> {
    String columnName;
    List<T> values;
}
