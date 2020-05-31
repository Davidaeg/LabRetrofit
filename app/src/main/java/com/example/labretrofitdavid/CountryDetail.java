package com.example.labretrofitdavid;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labretrofitdavid.Interface.Covid19Api;
import com.example.labretrofitdavid.models.CountryInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.labretrofitdavid.R.layout;

public class CountryDetail extends AppCompatActivity implements Callback<List<CountryInfo>> {
    private TextView country_name;
    private TextView cases;
    private TextView date_tv;
    private  TextView status_tv;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());//formato de fecha
    private String yesterday;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_country_detail);

        country_name = findViewById(R.id.country_name_tv);
        cases = findViewById(R.id.tv_cases);
        date_tv = findViewById(R.id.tv_date);
        status_tv = findViewById(R.id.status_tv);

        getIncomingIntent();
        loadCountryDetails();
    }


    private void getIncomingIntent(){

        if(getIntent().hasExtra("name")){

            String countryName = getIntent().getStringExtra("name");
            country_name.setText(countryName);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            yesterday = dateFormat.format(cal.getTime());
            yesterday+="T00:00:00Z";

           currentDate = dateFormat.format(new Date());
           currentDate += "T00:00:00Z";
        }
    }

    private void loadCountryDetails(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19api.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Covid19Api interface1 = retrofit.create(Covid19Api.class);

        //Cambia el nombre para enviarlo al API con el formato adecuado
        String name = (String) country_name.getText();
        name = name.replace(" ","-");

       // d("david", "https://api.covid19api.com/country/"+name+"/status/confirmed?from="+yesterday+"&to="+currentDate);
        //Ejecuta la consulta con parámetros
        Call<List<CountryInfo>> call = interface1.getCountryInfo(name, yesterday, currentDate);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<CountryInfo>> call, Response<List<CountryInfo>> response) {
        if(response.isSuccessful()){
            List<CountryInfo> country = response.body();
            if (country.isEmpty()){ //Hay países sin datos o que responden con errores
                status_tv.setText("No hay registros");
                status_tv.setTextColor(Color.RED);
                cases.setText("No hay Datos");
                date_tv.setText("No hay Datos");
            }else{
                for (CountryInfo c: country){
                    status_tv.setText("Listo");
                    status_tv.setTextColor(Color.BLUE);
                    cases.setText(Integer.toString(c.getCases()));
                    date_tv.setText(dateFormat.format(new Date()));
                }
            }

        }else {
            Toast.makeText(this, "ERROR"+response.message(), Toast.LENGTH_LONG).show();
            status_tv.setText("A ocurrido un error");
            status_tv.setTextColor(Color.RED);
            cases.setText("No hay Datos");
            date_tv.setText("No hay Datos");
        }

    }

    @Override
    public void onFailure(Call<List<CountryInfo>> call, Throwable t) {
        Toast.makeText(this, "ERROR2"+t.getMessage(), Toast.LENGTH_LONG).show();
        status_tv.setText("A ocurrido un error");
        status_tv.setTextColor(Color.RED);
        cases.setText("No hay Datos");
        date_tv.setText("No hay Datos");
    }
}
