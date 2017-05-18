package com.runmanager.gabriel.runmanager.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class EditCorridaActivity extends AppCompatActivity {

    BDHelper bd;
    EditText data;
    TextView distancia;
    CheckBox status;
    TextInputLayout distanciaLabel;
    TextInputLayout dataLabel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_corrida);
        bd = new BDHelper(this);

        distancia = (TextView) findViewById(R.id.tv_edit_km);
        data = (EditText) findViewById(R.id.tv_edit_data_programada);
        status = (CheckBox) findViewById(R.id.cb_edit_status);
        distanciaLabel = (TextInputLayout) findViewById(R.id.til_edit_km);
        dataLabel = (TextInputLayout) findViewById(R.id.til_edit_data_programada);

        final Corrida corrida = (Corrida) getIntent().getSerializableExtra("CORRIDA");
        distancia.setText(corrida.getDistancia());
        data.setText(corrida.getDataProgramada());
        data.addTextChangedListener(MaskUtil.mask(data, MaskUtil.FORMAT_DATE));
        status.setChecked(corrida.isCorridaFeita());


        Button salvar = (Button) findViewById(R.id.btn_salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    corrida.setDistancia(distancia.getText().toString());
                    corrida.setDataProgramada(data.getText().toString());
                    corrida.setCorridaFeita(status.isChecked());
                    bd.update(corrida);
                    EditCorridaActivity.this.finish();
                }
            }
        });

        Button excluir = (Button) findViewById(R.id.btn_excluir);
        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.delete(corrida.getId());
                EditCorridaActivity.this.finish();
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditCorridaActivity.this, dateSetListener, 2017, 1, 1);
                datePickerDialog.show();
            }
        });

    }

    private boolean validateForm() {
        boolean isValido = true;
        if (distancia.getText() != null && distancia.getText().toString().isEmpty()) {
            distanciaLabel.setErrorEnabled(true);
            distanciaLabel.setError("Informa a distância da corrida. :-)");
            isValido = false;
        } else {
            distanciaLabel.setErrorEnabled(false);
            distanciaLabel.setError(null);
        }
        if (dataLabel.getEditText() != null
                && dataLabel.getEditText().getText() != null
                && dataLabel.getEditText().getText().toString().isEmpty()) {
            dataLabel.setErrorEnabled(true);
            dataLabel.setError("Informa a data da corrida. :-)");
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
