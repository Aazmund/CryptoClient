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

public class AffineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affine);

        Bundle bundle = getIntent().getExtras();
        String cryptName = bundle.getString("cryptName");

        Button btnGetResult = findViewById(R.id.btnAffineGetResult);

        EditText editTextAffineForEncDec = findViewById(R.id.editTextAffineForEncDec);
        EditText editTextAffineKey1 = findViewById(R.id.editTextAffineKey1);
        EditText editTextAffineKey2 = findViewById(R.id.editTextAffineKey2);

        RadioButton radioButtonEnc = findViewById(R.id.radioButtonAffineEnc);
        RadioButton radioButtonDec = findViewById(R.id.radioButtonAffineDec);

        btnGetResult.setOnClickListener(view -> {
            if (editTextAffineForEncDec.getText().toString().equals("") ||
                    editTextAffineKey1.getText().toString().equals("") || editTextAffineKey2.getText().toString().equals("") ||
                    (!radioButtonEnc.isChecked() && !radioButtonDec.isChecked())){
                Toast.makeText(this, "Есть незаполненые поля", Toast.LENGTH_SHORT).show();
            }else{
                String action;
                if(radioButtonEnc.isChecked()){
                    action = "encrypt";
                }else{
                    action = "decrypt";
                }
                String context = editTextAffineKey1.getText().toString() + "@" + editTextAffineKey2.getText().toString();
                requestToServer(action, editTextAffineForEncDec.getText().toString(), cryptName, context);
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
                EditText editTextAffineResult = findViewById(R.id.editTextAffineResult);
                editTextAffineResult.setText(response.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(AffineActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()){
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