package com.example.crypto.CryptActivities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crypto.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MagicSquareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_square);

        Bundle bundle = getIntent().getExtras();

        Button btnGetResult = findViewById(R.id.btnMagicSquareGetResult);

        RadioButton radioButtonThree = findViewById(R.id.radioButtonThree);
        RadioButton radioButtonFive = findViewById(R.id.radioButtonFive);
        RadioButton radioButtonSeven = findViewById(R.id.radioButtonSeven);

        btnGetResult.setOnClickListener(view -> {
            if (radioButtonThree.isChecked()){
                requestToServer("magicSquare", "", "", String.valueOf(3));
            }
            if (radioButtonFive.isChecked()){
                requestToServer("magicSquare", "", "", String.valueOf(5));
            }
            if (radioButtonSeven.isChecked()){
                requestToServer("magicSquare", "", "", String.valueOf(7));
            }
        });

    }

    protected void action(int col, String response){
        GridLayout gridLayout = findViewById(R.id.GridMagickSquare);
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(col);
        gridLayout.setRowCount(col);

        String[] numbers = response.split(",");

        for(String number: numbers){
            TextView textView = new TextView(this);
            textView.setTextSize(25);
            textView.setText(number + "   ");
            gridLayout.addView(textView);
        }
    }

    private void requestToServer(String action, String string, String cryptName, String context) {
        String url = "http://10.0.2.2:8080/crypto";
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<>();
        params.put("action", "magicSquare");
        params.put("string", string);
        params.put("cryptName", cryptName);
        params.put("context", context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), response -> {
            try {
                action(Integer.parseInt(context), response.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(MagicSquareActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", action);
                params.put("string", string);
                params.put("cryptName", cryptName);
                params.put("context", context);
                params.put("result", "");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }
}