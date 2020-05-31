package com.example.labretrofitdavid.models;

public class Country {
    private String Country;
    private String Slug;
    private String ISO2;

    public void setCountry(String country) {
        Country = country;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public void setISO2(String ISO2) {
        this.ISO2 = ISO2;
    }

    public String getCountry() {
        return Country;
    }

    public String getSlug() {
        return Slug;
    }

    public String getISO2() {
        return ISO2;
    }
}
