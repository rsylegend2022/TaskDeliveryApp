package com.neo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchCriteria {
    private List<QueryInCriterion<?>> inCriteria;
}
