package com.example.labretrofitdavid.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labretrofitdavid.CountryDetail;
import com.example.labretrofitdavid.R;
import com.example.labretrofitdavid.models.Country;

import java.util.List;


public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {


    private List<Country> countriesList;
    private Context context;


    public CountryListAdapter(List<Country> countriesList, Context context) {
        this.countriesList = countriesList;
        this.context = context;
    }

    /*
    Permite construir cada item en base al layout que asigne
    */
    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.country_item_list , parent, false);
        CountryViewHolder viewHolder = new CountryViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        final Country country = countriesList.get(position);
        holder.name.setText(country.getCountry());

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //envía el nombre del país seleccionado por medio de un extra del intent
                Intent i = new Intent(context, CountryDetail.class);

                i.putExtra("name", country.getCountry());

                context.startActivity(i);
            }
        });
    }

    /*
     Me permite conocer el tamaño de la lista en tiempo real
     */
    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    //View holder para lograr llenar el contenido de cada item
    public static class CountryViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ConstraintLayout itemLayout;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.tv_name);
            this.itemLayout = (ConstraintLayout) itemView.findViewById(R.id.cl_country_item_list);
        }
    }
}
