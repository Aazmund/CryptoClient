package com.example.crypto;

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

        RadioButton radioButtonEnc = findViewById(R.id.radioButtonEnc);
        RadioButton radioButtonDec = findViewById(R.id.radioButtonDec);

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
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                System.out.println(jsonObject);
                EditText editTextAffineResult = findViewById(R.id.editTextAffineResult);
                editTextAffineResult.setText(jsonObject.getString("string"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(AffineActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show()){
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