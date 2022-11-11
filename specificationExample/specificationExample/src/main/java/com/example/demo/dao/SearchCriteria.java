package com.example.demo.dao;


public class SearchCriteria {
    private String key; // brand , price
    private Object value; // MI, 120000
    private SearchOperation operation; // EQUAL, GREATER

    public SearchCriteria() {
    }

    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", operation=" + operation +
                '}';
    }
}
