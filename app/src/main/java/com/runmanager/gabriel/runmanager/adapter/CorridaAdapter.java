package com.runmanager.gabriel.runmanager.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runmanager.gabriel.runmanager.activity.MainActivity;
import com.runmanager.gabriel.runmanager.R;
import com.runmanager.gabriel.runmanager.model.Corrida;

import java.util.List;

/**
 * Created by gabriel on 13/05/17.
 */

public class CorridaAdapter extends BaseAdapter {

    TextView km;
    TextView dataProgramada;
    MainActivity activity;
    private List<Corrida> corridas;

    public CorridaAdapter(MainActivity activity, List<Corrida> corridas) {
        this.corridas = corridas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return corridas.size();
    }

    @Override
    public Corrida getItem(int i) {
        return corridas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = activity.getLayoutInflater().inflate(R.layout.corrida, viewGroup, false);

        km = (TextView) linha.findViewById(R.id.tv_km);
        dataProgramada = (TextView) linha.findViewById(R.id.tv_data_programada);
        Corrida corrida = getItem(i);
        km.setText(corrida.getDistancia());
        dataProgramada.setText(corrida.getDataProgramada());

        if(!corrida.isCorridaFeita()){
            linha.setBackgroundColor(ContextCompat.getColor(activity, R.color.pink));
        } else {
            linha.setBackgroundColor(ContextCompat.getColor(activity, R.color.green));
        }

        return linha;
    }
}
