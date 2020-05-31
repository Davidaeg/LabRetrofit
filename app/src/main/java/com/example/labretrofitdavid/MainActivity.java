package com.example.labretrofitdavid;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labretrofitdavid.Interface.Covid19Api;
import com.example.labretrofitdavid.adapters.CountryListAdapter;
import com.example.labretrofitdavid.models.Country;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<Country>> {

    private RecyclerView countries_list_recycler;
    private List<Country> countriesList = new ArrayList<>();
    private CountryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //obtiene el recyclerview
        countries_list_recycler = findViewById(R.id.countries_recycler);

        //realaciona el adaptador con la lista y con el recyclerview
        adapter = new CountryListAdapter(countriesList, this);
        countries_list_recycler.setLayoutManager(new LinearLayoutManager(this));
        countries_list_recycler.setAdapter(adapter);

        //llama método que consulta el api
        loadCountries();


    }

    private void loadCountries(){
        //Gson para manipular el Json
        Gson gson = new GsonBuilder().setLenient().create();
        //Creación instancia retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19api.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //Se asigna la interface
        Covid19Api covid = retrofit.create(Covid19Api.class);

        //ejecuta la consulta
        Call<List<Country>> call = covid.getContries();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
        if(response.isSuccessful()){
            //captura la lista de la respuesta
            List<Country> countries = response.body();

            //la recorre y la agrega a la lista countriesList
            for (Country country: countries){
                countriesList.add(country);
            }
            //notifica al adapter para que cargue la vista cargada
            adapter.notifyDataSetChanged();

        }else {
            Toast.makeText(this, "ERROR"+response.message(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onFailure(Call<List<Country>> call, Throwable t) {
        Toast.makeText(this, "ERROR"+t.getMessage(), Toast.LENGTH_LONG).show();
    }

}
