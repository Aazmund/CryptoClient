package com.example.crypto.CryptActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crypto.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TrithemiusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trithemius);

        Bundle bundle = getIntent().getExtras();
        String cryptName = bundle.getString("cryptName");

        Button btnGetResult = findViewById(R.id.btnTrithemiusGetResult);

        EditText editTextTrithemiusForEncDec = findViewById(R.id.editTextTrithemiusForEncDec);

        RadioButton radioButtonEnc = findViewById(R.id.radioButtonEnc);
        RadioButton radioButtonDec = findViewById(R.id.radioButtonDec);

        EditText editTextTrithemiusKey1 = findViewById(R.id.editTextTrithemiusKey1);
        EditText editTextTrithemiusKey2 = findViewById(R.id.editTextTrithemiusKey2);
        EditText editTextTrithemiusKey3 = findViewById(R.id.editTextTrithemiusKey3);

        btnGetResult.setOnClickListener(view -> {
            if (editTextTrithemiusForEncDec.getText().toString().equals("") ||
                    editTextTrithemiusKey1.toString().isEmpty() ||
                    editTextTrithemiusKey2.toString().isEmpty() ||
                    editTextTrithemiusKey3.toString().isEmpty() ||
                    (!radioButtonEnc.isChecked() && !radioButtonDec.isChecked())){
                Toast.makeText(this, "Есть незаполненые поля", Toast.LENGTH_SHORT).show();
            }else{
                String action;
                if(radioButtonEnc.isChecked()){
                    action = "encrypt";
                }else{
                    action = "decrypt";
                }
                requestToServer(action, editTextTrithemiusForEncDec.getText().toString(), cryptName,
                        editTextTrithemiusKey1.getText().toString() + "@"
                                + editTextTrithemiusKey2.getText().toString() + "@"
                                + editTextTrithemiusKey3.getText().toString());
            }
        });
    }

    private void requestToServer(String action, String string, String cryptName, String context) {
        String url = "http://10.0.2.2:8080/crypto";
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<>();
        params.put("action", action);
        params.put("string", string);
        params.put("cryptName", cryptName);
        params.put("context", context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), response -> {
            try {
                EditText editTextTrithemiusResult = findViewById(R.id.editTextTrithemiusResult);
                editTextTrithemiusResult.setText(response.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(TrithemiusActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()){
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