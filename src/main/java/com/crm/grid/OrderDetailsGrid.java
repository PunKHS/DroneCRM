package com.crm.grid;

import com.crm.model.OrderDetails;

import java.util.List;

public class OrderDetailsGrid {
    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<OrderDetails> orderDetailsData;

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

    public List<OrderDetails> getOrderDetailsData() {
        return orderDetailsData;
    }

    public void setOrderDetailsData(List<OrderDetails> orderDetailsData) {
        this.orderDetailsData = orderDetailsData;
    }
}
