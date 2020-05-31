package com.example.labretrofitdavid.models;

public class CountryInfo {
    private String Country;
    private int Cases;
    private java.util.Date Date;

    public CountryInfo(String country, int cases, java.util.Date date) {
        Country = country;
        Cases = cases;
        Date = date;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getCases() {
        return Cases;
    }

    public void setCases(int cases) {
        Cases = cases;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }
}
