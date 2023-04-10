package com.example.demo.dao;

public interface BaseDao<D, T> {

    D findWithGraph(T id, String graphName);
}