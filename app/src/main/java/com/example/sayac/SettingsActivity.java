package com.example.sayac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Set;


public class SettingsActivity extends AppCompatActivity {
    Button buttonUpperMinus,buttonUpperPlus, buttonLowerMinus, buttonLowerPlus;
    CheckBox upperSesliUyarı, upperTitresim, lowerSesliUyari, lowerTitresim;
    EditText editTextNumberSigned, editTextNumberSigned2;
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        buttonLowerMinus = (Button) findViewById(R.id.buttonLowerMinus);
        buttonLowerPlus = (Button) findViewById(R.id.buttonLowerPlus);
        buttonUpperMinus = (Button) findViewById(R.id.buttonUpperMinus);
        buttonUpperPlus = (Button) findViewById(R.id.buttonUpperPlus);

        upperSesliUyarı = (CheckBox) findViewById(R.id.upperSesliUyari);
        lowerSesliUyari = (CheckBox) findViewById(R.id.lowerSesliUyari);
        upperTitresim = (CheckBox) findViewById(R.id.upperTitresim);
        lowerTitresim = (CheckBox) findViewById(R.id.lowerTitresim);

        editTextNumberSigned = (EditText) findViewById(R.id.editTextNumberSigned);
        editTextNumberSigned2 = (EditText) findViewById(R.id.editTextNumberSigned2);

        settings = Settings.getSettings(getApplicationContext());

        buttonUpperPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.upperLimit++;
                editTextNumberSigned.setText(String.valueOf(settings.upperLimit));
            }
        });
        buttonUpperMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.upperLimit--;
                editTextNumberSigned.setText(String.valueOf(settings.upperLimit));
            }
        });
        buttonLowerPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.lowerLimit++;
                editTextNumberSigned2.setText(String.valueOf(settings.lowerLimit));
            }
        });
        buttonLowerMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.lowerLimit--;
                editTextNumberSigned2.setText(String.valueOf(settings.lowerLimit));
            }
        });

        upperSesliUyarı.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.upperSound = b;
            }
        });
        lowerSesliUyari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.lowerSound = b;
            }
        });
        upperTitresim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.upperVib = b;
            }
        });
        lowerTitresim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.lowerVib = b;
            }
        });
        editTextNumberSigned.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editTextNumberSigned.getText().toString().length() != 0){
                    settings.upperLimit = Integer.parseInt(editTextNumberSigned.getText().toString());
                }
            }
        });

        editTextNumberSigned2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editTextNumberSigned2.getText().toString().length() != 0){
                    settings.lowerLimit = Integer.parseInt(editTextNumberSigned2.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        settings.SaveValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        editTextNumberSigned.setText(String.valueOf(settings.upperLimit));
        editTextNumberSigned2.setText(String.valueOf(settings.lowerLimit));
        upperTitresim.setChecked(settings.upperVib);
        upperSesliUyarı.setChecked(settings.upperSound);
        lowerTitresim.setChecked(settings.lowerVib);
        lowerSesliUyari.setChecked(settings.lowerSound);
    }
}