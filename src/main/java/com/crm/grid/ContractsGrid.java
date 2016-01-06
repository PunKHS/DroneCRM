package com.crm.grid;

import com.crm.model.Contracts;

import java.util.List;

public class ContractsGrid {
    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<Contracts> contractsData;

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

    public List<Contracts> getContractsData() {
        return contractsData;
    }

    public void setContractsData(List<Contracts> contractsData) {
        this.contractsData = contractsData;
    }
}
