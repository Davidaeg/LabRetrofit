package com.example.labretrofitdavid.Interface;

import com.example.labretrofitdavid.models.Country;
import com.example.labretrofitdavid.models.CountryInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Covid19Api {
    //consulta por la lista de paises
    @GET(value = "countries")
    Call<List<Country>> getContries();

    //consulta por un país en específico, envia parámetros
    @GET("country/{name}/status/confirmed")
    Call<List<CountryInfo>> getCountryInfo(@Path("name") String country, @Query("from") String from, @Query("to") String to);

}
