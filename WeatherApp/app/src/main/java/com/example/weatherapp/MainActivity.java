package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

import static java.lang.Double.parseDouble;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void getWeather(View view) {
        TextInputEditText input = (TextInputEditText) findViewById(R.id.input);
        TextView errorBox = (TextView) findViewById(R.id.error);

        Toast.makeText(this,input.getEditableText(),Toast.LENGTH_SHORT).show();
        if(!input.getEditableText().toString().equals("")){
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://api.openweathermap.org/data/2.5/weather?q="+input.getEditableText()+"&appid=a961d34ebc6526f3d84c656678b74607";
            JsonObjectRequest request = new JsonObjectRequest(url, null,
                    response -> {
                        setWeather(response);                    }, error -> {
                errorBox.setText(error.toString());
            });
            queue.add(request);
        }else {
            errorBox.setText(R.string.Please_enter_city);
        }
    }

    public double KtoC(double f){
        return (-273.15)+f;
    }

    private void setWeather(JSONObject response) {
        TextView currTemp = (TextView) findViewById(R.id.curr_temp);
        TextView minTemp = (TextView) findViewById(R.id. min_temp);
        TextView maxTemp = (TextView) findViewById(R.id.max_temp);
        TextView feelsLike = (TextView) findViewById(R.id.feels_like);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        TextView textView6 = (TextView) findViewById(R.id.textView6);

        textView2.setVisibility(View.VISIBLE);
        textView3.setVisibility(View.VISIBLE);
        textView5.setVisibility(View.VISIBLE);
        textView6.setVisibility(View.VISIBLE);

        try {
            currTemp.setText((KtoC(parseDouble(response.getJSONObject("main").getString("temp")))+"  °C"));
            minTemp.setText((KtoC(parseDouble(response.getJSONObject("main").getString("temp_min")))+" °C"));
            maxTemp.setText((KtoC(parseDouble(response.getJSONObject("main").getString("temp_max")))+" °C"));
            feelsLike.setText((KtoC(parseDouble(response.getJSONObject("main").getString("feels_like")))+" °C"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}