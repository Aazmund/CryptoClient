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
import com.android.volley.toolbox.Volley;
import com.example.crypto.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class CaesarKeyWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar_key_word);
        Bundle bundle = getIntent().getExtras();
        String cryptName = bundle.getString("cryptName");
        Button btnGetResult = findViewById(R.id.btnCaesarKeyWordGetResult);
        EditText editTextCaesarKeyWordForEncDec = findViewById(R.id.editTextCaesarKeyWordForEncDec);
        EditText editTextCaesarKeyWordKey1 = findViewById(R.id.editTextCaesarKeyWordKey1);
        EditText editTextCaesarKeyWordKey2 = findViewById(R.id.editTextCaesarKeyWordKey2);
        RadioButton radioButtonEnc = findViewById(R.id.radioButtonCaesarKeyWordEnc);
        RadioButton radioButtonDec = findViewById(R.id.radioButtonCaesarKeyWordDec);

        btnGetResult.setOnClickListener(view -> {
            if (editTextCaesarKeyWordForEncDec.getText().toString().equals("") ||
                    editTextCaesarKeyWordKey1.getText().toString().equals("") || editTextCaesarKeyWordKey2.getText().toString().equals("") ||
                    (!radioButtonEnc.isChecked() && !radioButtonDec.isChecked())){
                Toast.makeText(this, "Есть незаполненые поля", Toast.LENGTH_SHORT).show();
            }else{
                String action;
                if(radioButtonEnc.isChecked()){
                    action = "encrypt";
                }else{
                    action = "decrypt";
                }
                String context = editTextCaesarKeyWordKey2.getText().toString() + "@" + editTextCaesarKeyWordKey1.getText().toString();
                requestToServer(action, editTextCaesarKeyWordForEncDec.getText().toString(), cryptName, context);
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
                EditText editTextCaesarKeyWordResult = findViewById(R.id.editTextCaesarKeyWordResult);
                editTextCaesarKeyWordResult.setText(response.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(CaesarKeyWordActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()){
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