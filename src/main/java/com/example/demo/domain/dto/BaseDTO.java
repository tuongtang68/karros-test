package com.example.demo.domain.dto;

abstract public class BaseDTO<T> {
    private int id;

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    abstract public T toModel();
}
