package com.runmanager.gabriel.runmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.runmanager.gabriel.runmanager.R;
import com.runmanager.gabriel.runmanager.adapter.CorridaAdapter;
import com.runmanager.gabriel.runmanager.bd.BDHelper;
import com.runmanager.gabriel.runmanager.model.Corrida;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listaDeCorridas;
    private List<Corrida> corridas = new ArrayList<>();
    BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bdHelper = new BDHelper(this);

    }

    @Override
    protected void onStart() {

        super.onStart();
        this.listaDeCorridas = (ListView) findViewById(R.id.corridas);

//        Corrida c1 = new Corrida("8km", "22/06/2017", false);
//        Corrida c2 = new Corrida("18km", "24/06/2017", true);
//        Corrida c3 = new Corrida("16km", "27/06/2017", false);
//        Corrida c4 = new Corrida("5km", "30/06/2017", true);
//        corridas.add(c1);
//        corridas.add(c2);
//        corridas.add(c3);
//        corridas.add(c4);


        corridas = bdHelper.buscarTodasCorridas();
        TextView semCorrida = (TextView) findViewById(R.id.tv_sem_corridas);
        if (corridas != null && corridas.isEmpty()) {
            semCorrida.setVisibility(View.VISIBLE);
        } else {
            semCorrida.setVisibility(View.GONE);
        }

        ListAdapter adapter = new CorridaAdapter(this, corridas);
        this.listaDeCorridas.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, AddCorridaActivity.class);
                startActivity(intent);
            }
        });


        listaDeCorridas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Corrida corrida = (Corrida) adapterView.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this, corrida.getDistancia(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditCorridaActivity.class);
                intent.putExtra("CORRIDA", corrida);
                startActivity(intent);
            }
        });
    }
}
