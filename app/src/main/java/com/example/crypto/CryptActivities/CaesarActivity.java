package com.example.crypto.CryptActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crypto.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CaesarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar);
        Bundle bundle = getIntent().getExtras();
        String cryptName = bundle.getString("cryptName");

        Button btnGetResult = findViewById(R.id.btnCaesarGetResult);

        EditText editTextCaesarForEncDec = findViewById(R.id.editTextCaesarForEncDec);
        EditText editTextCaesarKey = findViewById(R.id.editTextCaesarKey);

        RadioButton radioButtonEnc = findViewById(R.id.radioButtonEnc);
        RadioButton radioButtonDec = findViewById(R.id.radioButtonDec);

        btnGetResult.setOnClickListener(view -> {
            if (editTextCaesarForEncDec.getText().toString().equals("") ||
                    editTextCaesarKey.getText().toString().equals("") ||
                    (!radioButtonEnc.isChecked() && !radioButtonDec.isChecked())){
                Toast.makeText(this, "Есть незаполненые поля", Toast.LENGTH_SHORT).show();
            }else{
                String action;
                if(radioButtonEnc.isChecked()){
                    action = "encrypt";
                }else{
                    action = "decrypt";
                }
                requestToServer(action, editTextCaesarForEncDec.getText().toString(), cryptName, editTextCaesarKey.getText().toString());
            }
        });
    }

    private void requestToServer(String action, String string, String cryptName, String context) {
        String url = "http://10.0.2.2:8080/crypto";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                System.out.println(jsonObject);
                EditText editTextCaesarResult = findViewById(R.id.editTextCaesarResult);
                editTextCaesarResult.setText(jsonObject.getString("string"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(CaesarActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", action);
                params.put("string", string);
                params.put("cryptName", cryptName);
                params.put("context", context);

                return params;
            }
        };
        queue.add(request);
    }
}