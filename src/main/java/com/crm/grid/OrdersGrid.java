package com.crm.grid;

import com.crm.model.Orders;

import java.util.List;

public class OrdersGrid {
    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<Orders> ordersData;

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

    public List<Orders> getOrdersData() {
        return ordersData;
    }

    public void setOrdersData(List<Orders> ordersData) {
        this.ordersData = ordersData;
    }
}
