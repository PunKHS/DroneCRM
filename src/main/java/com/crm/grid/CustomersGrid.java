package com.crm.grid;

import com.crm.model.Customers;

import java.util.List;

public class CustomersGrid {
    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<Customers> customersData;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Customers> getCustomersData() {
        return customersData;
    }

    public void setCustomersData(List<Customers> banksData) {
        this.customersData = banksData;
    }
}
