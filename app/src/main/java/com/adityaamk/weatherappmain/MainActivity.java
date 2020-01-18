package com.adityaamk.weatherappmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView time, quote, city, temp, weather, title, subtitle, zip;
    ConstraintLayout constraintLayout;
    ImageView iv;
    EditText input;
    Button btnReturn, celsius;

    TextView time2, high2, low2;
    ImageView iv2;

    TextView time3, high3, low3;
    ImageView iv3;

    TextView time4, high4, low4;
    ImageView iv4;

    TextView time5, high5, low5;
    ImageView iv5;

    TextView time6, high6, low6;
    ImageView iv6;

    JSONObject weatherData, todayData, day;
    JSONArray week;

    String zipcode;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quote = findViewById(R.id.id_quote);
        city = findViewById(R.id.id_city);
        input = findViewById(R.id.id_input);
        weather = findViewById(R.id.id_weather);
        title = findViewById(R.id.id_title);
        subtitle = findViewById(R.id.id_subtitle);
        constraintLayout = findViewById(R.id.id_cl);
        btnReturn = findViewById(R.id.id_btnReturn);
        zip = findViewById(R.id.id_zipcode);
        celsius = findViewById(R.id.id_btn);

        temp = findViewById(R.id.id_temp);
        high2 = findViewById(R.id.id_high2);
        low2 = findViewById(R.id.id_low2);
        high3 = findViewById(R.id.id_high3);
        low3 = findViewById(R.id.id_low3);
        high4 = findViewById(R.id.id_high4);
        low4 = findViewById(R.id.id_low4);
        high5 = findViewById(R.id.id_high5);
        low5 = findViewById(R.id.id_low5);
        high6 = findViewById(R.id.id_high6);
        low6 = findViewById(R.id.id_low6);

        time = findViewById(R.id.id_time);
        time2 = findViewById(R.id.id_time2);
        time3 = findViewById(R.id.id_time3);
        time4 = findViewById(R.id.id_time4);
        time5 = findViewById(R.id.id_time5);
        time6 = findViewById(R.id.id_time6);

        iv = findViewById(R.id.id_iv);
        iv2 = findViewById(R.id.id_iv2);
        iv3 = findViewById(R.id.id_iv3);
        iv4 = findViewById(R.id.id_iv4);
        iv5 = findViewById(R.id.id_iv5);
        iv6 = findViewById(R.id.id_iv6);

        constraintLayout.setBackgroundResource(R.drawable.homescreen);
        btnReturn.setBackgroundResource(R.drawable.btnbackremovebgpreview);

        celsius.setTextColor(Color.WHITE);
        celsius.getBackground().setAlpha(0);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                zipcode = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    new AsyncThread().execute(zipcode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setBackgroundResource(R.drawable.homescreen);

                title.setVisibility(View.VISIBLE);
                subtitle.setVisibility(View.VISIBLE);
                input.setVisibility(View.VISIBLE);
                input.setEnabled(true);
                btnReturn.setVisibility(View.INVISIBLE);
                celsius.setVisibility(View.INVISIBLE);
                zip.setVisibility(View.VISIBLE);

                input.setText("");

                quote.setText("");
                weather.setText("");
                city.setText("");

                temp.setText("");
                high2.setText("");
                high3.setText("");
                high4.setText("");
                high5.setText("");
                high6.setText("");
                low2.setText("");
                low3.setText("");
                low4.setText("");
                low5.setText("");
                low6.setText("");

                time.setText("");
                time2.setText("");
                time3.setText("");
                time4.setText("");
                time5.setText("");
                time6.setText("");

                iv.setImageDrawable(null);
                iv2.setImageDrawable(null);
                iv3.setImageDrawable(null);
                iv4.setImageDrawable(null);
                iv5.setImageDrawable(null);
                iv6.setImageDrawable(null);
            }
        });
    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {
        String data = "";
        String data2 = "";

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (weatherData != null && zipcode.length()==5){
                try {
                    title.setVisibility(View.INVISIBLE);
                    subtitle.setVisibility(View.INVISIBLE);
                    input.setVisibility(View.INVISIBLE);
                    input.setEnabled(false);
                    btnReturn.setVisibility(View.VISIBLE);
                    celsius.setVisibility(View.VISIBLE);
                    zip.setVisibility(View.INVISIBLE);

                    week = weatherData.getJSONArray("list");
                    day = week.getJSONObject(0);

                    weather.setText(todayData.getJSONArray("weather").getJSONObject(0).getString("main"));
                    city.setText(weatherData.getJSONObject("city").getString("name"));

                    time.setText(getTime(todayData));
                    time6.setText(getTime(day));
                    time2.setText(getTime(week.getJSONObject(1)));
                    time3.setText(getTime(week.getJSONObject(2)));
                    time4.setText(getTime(week.getJSONObject(3)));
                    time5.setText(getTime(week.getJSONObject(4)));

                    temp.setText(""+todayData.getJSONObject("main").getInt("temp"));
                    high6.setText(""+day.getJSONObject("main").getInt("temp_max"));
                    high2.setText(""+week.getJSONObject(1).getJSONObject("main").getInt("temp_max"));
                    high3.setText(""+week.getJSONObject(2).getJSONObject("main").getInt("temp_max"));
                    high4.setText(""+week.getJSONObject(3).getJSONObject("main").getInt("temp_max"));
                    high5.setText(""+week.getJSONObject(4).getJSONObject("main").getInt("temp_max"));
                    low6.setText(""+day.getJSONObject("main").getInt("temp_min"));
                    low2.setText(""+week.getJSONObject(1).getJSONObject("main").getInt("temp_min"));
                    low3.setText(""+week.getJSONObject(2).getJSONObject("main").getInt("temp_min"));
                    low4.setText(""+week.getJSONObject(3).getJSONObject("main").getInt("temp_min"));
                    low5.setText(""+week.getJSONObject(4).getJSONObject("main").getInt("temp_min"));

                    setImage(day.getJSONArray("weather").getJSONObject(0).getString("main"), iv6);
                    setImage(week.getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main"), iv2);
                    setImage(week.getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main"), iv3);
                    setImage(week.getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main"), iv4);
                    setImage(week.getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main"), iv5);

                    celsius.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                if(check){
                                    convertTemp(week.getJSONObject(1).getJSONObject("main").getInt("temp_max"), week.getJSONObject(1).getJSONObject("main").getInt("temp_min"), high2, low2);
                                    convertTemp(week.getJSONObject(2).getJSONObject("main").getInt("temp_max"), week.getJSONObject(2).getJSONObject("main").getInt("temp_min"), high3, low3);
                                    convertTemp(week.getJSONObject(3).getJSONObject("main").getInt("temp_max"), week.getJSONObject(3).getJSONObject("main").getInt("temp_min"), high4, low4);
                                    convertTemp(week.getJSONObject(4).getJSONObject("main").getInt("temp_max"), week.getJSONObject(4).getJSONObject("main").getInt("temp_min"), high5, low5);
                                    convertTemp(day.getJSONObject("main").getInt("temp_max"), day.getJSONObject("main").getInt("temp_min"), high6, low6);
                                    convertTemp2(todayData.getJSONObject("main").getInt("temp"), temp);
                                    check = false;
                                }
                                else{
                                    temp.setText(""+todayData.getJSONObject("main").getInt("temp"));
                                    high6.setText(""+day.getJSONObject("main").getInt("temp_max"));
                                    high2.setText(""+week.getJSONObject(1).getJSONObject("main").getInt("temp_max"));
                                    high3.setText(""+week.getJSONObject(2).getJSONObject("main").getInt("temp_max"));
                                    high4.setText(""+week.getJSONObject(3).getJSONObject("main").getInt("temp_max"));
                                    high5.setText(""+week.getJSONObject(4).getJSONObject("main").getInt("temp_max"));
                                    low6.setText(""+day.getJSONObject("main").getInt("temp_min"));
                                    low2.setText(""+week.getJSONObject(1).getJSONObject("main").getInt("temp_min"));
                                    low3.setText(""+week.getJSONObject(2).getJSONObject("main").getInt("temp_min"));
                                    low4.setText(""+week.getJSONObject(3).getJSONObject("main").getInt("temp_min"));
                                    low5.setText(""+week.getJSONObject(4).getJSONObject("main").getInt("temp_min"));
                                    check = true;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    switch(getTime(day)){
                        case "7 AM":
                            constraintLayout.setBackgroundResource(R.drawable.sunrise);
                        case "10 AM":
                        case "1 PM":
                        case "4 PM":
                            constraintLayout.setBackgroundResource(R.drawable.daytime);
                            break;
                        case "7 PM":
                            constraintLayout.setBackgroundResource(R.drawable.evening);
                        case "10 PM":
                        case "1 AM":
                        case "4 AM":
                            constraintLayout.setBackgroundResource(R.drawable.night);
                            break;
                    }

                    switch (todayData.getJSONArray("weather").getJSONObject(0).getString("main")){
                        case "Thunderstorm":
                            iv.setImageResource(R.drawable.thunderstorm);
                            if ((int) (Math.random() * 2) == 0) {
                                quote.setText("\"Lightning strikes silently while thunder roars but never strikes. Be lightning!\"");
                            } else {
                                quote.setText("\"Be the lightning in someone's life. Even if it's just for a second!\"");
                            }
                            break;
                        case "Snow":
                            iv.setImageResource(R.drawable.snow);
                            if ((int) (Math.random() * 2) == 0) {
                                quote.setText("\"Kindness is like snow:it beautifies everything it covers - Kahlil Gibran\"");
                            } else {
                                quote.setText("\"Winter is a season of recovery and preparation for the sweltering Summer!\"");
                            }
                        case "Clouds":
                            iv.setImageResource(R.drawable.clouds);
                            if ((int) (Math.random() * 2) == 0) {
                                quote.setText("\"Behind every daunting cloud, there is a silver lining!\"");
                            } else {
                                quote.setText("\"Clouds never stop moving forward. Be resilient like them.\"");
                            }
                            break;
                        case "Rain":
                            if(getTime(day).equals("7 AM")||getTime(day).equals("10 AM")||getTime(day).equals("1 PM")||getTime(day).equals("4 PM"))
                                iv.setImageResource(R.drawable.rainmorning);
                            else
                                iv.setImageResource(R.drawable.rainnight);
                            if ((int) (Math.random() * 2) == 0) {
                                quote.setText("\"If you want to see a rainbow, you must endure the rain!\"");
                            } else {
                                quote.setText("\"Let the rain wash away any lasting pain!\"");
                            }
                            break;
                        case "Clear":
                            if(getTime(day).equals("7 AM")||getTime(day).equals("10 AM")||getTime(day).equals("1 PM")||getTime(day).equals("4 PM"))
                                iv.setImageResource(R.drawable.clearmorning);
                            else
                                iv.setImageResource(R.drawable.clearnight);
                            quote.setText("\"Look at the sunny side of all your problems in life!\"");
                            break;
                        case "Drizzle":
                            if(getTime(day).equals("7 AM")||getTime(day).equals("10 AM")||getTime(day).equals("1 PM")||getTime(day).equals("4 PM"))
                                iv.setImageResource(R.drawable.rainmorning);
                            else
                                iv.setImageResource(R.drawable.rainnight);
                            quote.setText("\"Without rain, nothing grows! Embrace the storms of your life!\"");
                            break;
                        case "Mist":
                            iv.setImageResource(R.drawable.mist);
                            quote.setText("Be courageous when facing the mist. Be excited for the unknown that lays ahead!");
                    }

                    weatherData = null;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            /*if(zipcode.length()==5 && input.isEnabled()) {
                final Toast remind = Toast.makeText(getApplicationContext(), "Please Enter A Valid Zipcode", Toast.LENGTH_SHORT);
                remind.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 700);
                remind.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        remind.cancel();
                    }
                }, 500);
            }

             */
        }

        @Override
        protected Void doInBackground(String... zipcode) {
            try{
                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?zip=" + zipcode[0] + "&appid=76d71f91a7540e0c939bfc007d93bc65&&units=imperial");
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                data = bufferedReader.readLine();
                weatherData = new JSONObject(data);
                URL url2 = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + zipcode[0] + "&appid=76d71f91a7540e0c939bfc007d93bc65&&units=imperial");
                URLConnection urlConnection2 = url2.openConnection();
                InputStream inputStream2 = urlConnection2.getInputStream();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                data2 = bufferedReader2.readLine();
                todayData = new JSONObject(data2);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public String getTime(JSONObject object){
        try {
            Long time = object.getLong("dt");
            Date date = new Date(time * 1000);
            //Log.d("TAG", date.toLocaleString());
            String[] arr = date.toLocaleString().split(" ");
            //Log.d("TAG", arr[3]);
            ArrayList<String> list = new ArrayList<>();
            String[] temp = arr[3].split(":");
            list.add(temp[0]+" "+arr[4]);
            return list.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setImage(String condition, ImageView image){
        switch (condition){
            case "Thunderstorm":
                image.setImageResource(R.drawable.thunderstorm);
                break;
            case "Snow":
                image.setImageResource(R.drawable.snow);
                break;
            case "Clouds":
                image.setImageResource(R.drawable.clouds);
                break;
            case "Rain":
            case "Drizzle":
                if(getTime(day).equals("7 AM")||getTime(day).equals("10 AM")||getTime(day).equals("1 PM")||getTime(day).equals("4 PM"))
                    image.setImageResource(R.drawable.rainmorning);
                else
                    image.setImageResource(R.drawable.rainnight);
                break;
            case "Clear":
                if(getTime(day).equals("7 AM")||getTime(day).equals("10 AM")||getTime(day).equals("1 PM")||getTime(day).equals("4 PM"))
                    image.setImageResource(R.drawable.clearmorning);
                else
                    image.setImageResource(R.drawable.clearnight);
                break;
            case "Mist":
                image.setImageResource(R.drawable.mist);
        }
    }

    public void convertTemp(int high, int low, TextView hi, TextView lo){
        double high2 = (high-32)*5/9;
        double low2 = (low-32)*5/9;
        hi.setText(""+((int)high2));
        lo.setText(""+((int)low2));
    }

    public void convertTemp2(int temp, TextView tv){
        double cel = (temp-32)*5/9;
        tv.setText(""+((int)cel));
    }

}
