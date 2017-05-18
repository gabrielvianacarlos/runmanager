package com.runmanager.gabriel.runmanager.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.runmanager.gabriel.runmanager.R;
import com.runmanager.gabriel.runmanager.bd.BDHelper;
import com.runmanager.gabriel.runmanager.model.Corrida;
import com.runmanager.gabriel.runmanager.util.MaskUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by gabriel on 18/05/17.
 */

public class AddCorridaActivity extends AppCompatActivity {

    TextView distancia;
    EditText data;
    TextInputLayout distanciaLabel;
    TextInputLayout dataLabel;
    BDHelper bd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_corrida);

        bd = new BDHelper(this);

        distanciaLabel = (TextInputLayout) findViewById(R.id.til_add_distancia);
        dataLabel = (TextInputLayout) findViewById(R.id.til_add_data_programada);
        distancia = (TextView) findViewById(R.id.tv_add_distancia);
        data = (EditText) findViewById(R.id.tv_add_data_programada);
        data.addTextChangedListener(MaskUtil.mask(data, MaskUtil.FORMAT_DATE));

        Button incluir = (Button) findViewById(R.id.btn_incluir);

        incluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    Corrida corrida = new Corrida(distancia.getText().toString(), data.getText().toString(), false);
                    bd.add(corrida);
                    finish();
                }
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AddCorridaActivity.this, dateSetListener, 2017, 1, 1);
                dialog.show();
            }
        });
    }

    private boolean validateForm() {
        boolean isValido = true;
        if (distancia.getText() != null && distancia.getText().toString().isEmpty()) {
            distanciaLabel.setErrorEnabled(true);
            distanciaLabel.setError("Informe a distância da corrida. :-)");
            isValido = false;
        } else {
            distanciaLabel.setErrorEnabled(false);
            distanciaLabel.setError(null);
        }
        if (dataLabel.getEditText() != null
                && dataLabel.getEditText().getText() != null
                && dataLabel.getEditText().getText().toString().isEmpty()) {
            dataLabel.setErrorEnabled(true);
            dataLabel.setError("Informe a data da corrida. :-)");
            isValido = false;
        } else {
            dataLabel.setErrorEnabled(false);
            dataLabel.setError(null);
        }

        return isValido;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            try {
                int dia = datePicker.getDayOfMonth();
                int mes = datePicker.getMonth();
                int ano = datePicker.getYear();

                Calendar cal = new GregorianCalendar();
                cal.set(ano, mes, dia);

                final Locale locale = new Locale("pt", "BR");
                String novaData = String.format(locale, "%1$td/%1$tm/%1$tY", cal);

                data.setText(novaData);
            } catch (Exception e) {
                Log.d("data da corrida", e.getMessage());
            }
        }
    };
}
