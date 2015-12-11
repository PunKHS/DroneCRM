package com.crm.grid;

import com.crm.model.Banks;

import java.util.List;

public class BanksGrid {
    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<Banks> banksData;

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

    public List<Banks> getBanksData() {
        return banksData;
    }

    public void setBanksData(List<Banks> banksData) {
        this.banksData = banksData;
    }
}
