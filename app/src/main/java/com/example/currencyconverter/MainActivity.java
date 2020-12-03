package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.currencyconverter.R.layout.support_simple_spinner_dropdown_item;


public class MainActivity extends AppCompatActivity {
    private EditText currencyOriginal, currencyConverted;
    private Spinner firstSpinner, secondSpinner;
    Button btn_convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyOriginal = findViewById(R.id.et_first_conversion);
        currencyConverted = findViewById(R.id.et_second_conversion);
        firstSpinner = findViewById(R.id.spinner_first_conversion);
        secondSpinner = findViewById(R.id.spinner_second_conversion);
        btn_convert = findViewById(R.id.btn_convert);

        spinnerSetUp();
    }

    private void spinnerSetUp(){
        ArrayAdapter currencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.currencies,
                support_simple_spinner_dropdown_item);
        ArrayAdapter currencyAdapter2 = ArrayAdapter.createFromResource(this,
                R.array.currencies2,
                support_simple_spinner_dropdown_item);
        firstSpinner.setAdapter(currencyAdapter);
        secondSpinner.setAdapter(currencyAdapter2);

        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(firstSpinner.getSelectedItem().toString());

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        //Log.d("response", String.valueOf(response.body()));
                        JsonObject res = response.body();
                        JsonObject rates = res.getAsJsonObject("conversion_rates");
                        double currency = Double.valueOf(currencyOriginal.getText().toString());
                        double multiplier = Double.valueOf(String.valueOf(rates.get(secondSpinner.getSelectedItem().toString())));
                        double result =(double) Math.round((currency * multiplier)*100)/100;
                        currencyConverted.setText(String.valueOf(result));
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }
        });
    }

}