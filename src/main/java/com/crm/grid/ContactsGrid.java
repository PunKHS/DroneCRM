package com.crm.grid;

import com.crm.model.Contacts;

import java.util.List;

public class ContactsGrid {
    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<Contacts> contactsData;

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

    public List<Contacts> getContactsData() {
        return contactsData;
    }

    public void setContactsData(List<Contacts> contactsData) {
        this.contactsData = contactsData;
    }
}
