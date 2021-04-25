package com.example.demo.domain.dto;

import java.util.List;

public class Paging<T extends BaseDTO>
{
    private long totalRecords;
    private int returnedRecords;
    private int currentPage;
    private int pageSize;
    private List<T> data;

    public long getTotalRecords() {
        return this.totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getReturnedRecords() {
        return this.returnedRecords;
    }

    public void setReturnedRecords(int returnedRecords) {
        this.returnedRecords = returnedRecords;
    }

    public int getCurrentPage() {
        return  this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize  = pageSize;
    }

    public List<T> getData() {
        return  this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
