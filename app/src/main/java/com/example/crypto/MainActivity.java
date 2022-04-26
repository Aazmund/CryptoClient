package com.example.crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        String[] cryptoList = new String[]{
                "Шифр Цезаря", "Одиночная перестановка по ключу", "Аффинный шифр", "Цезарь с ключевым словом",
                "Двойная перестановка", "Шифр тритемиуса", "Магический квадрат", "Шифр Вижнера",
                "Шифр Гронсфельда", "Шифр Плейфера", "Шифр Хилла", "Маршрутный шифр",
                "Поворотная решетка Кардано", "Шифр Решелье", "Шифр Фейстеля", "Кастомный шифр"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cryptoList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                switch (((TextView) itemClicked).getText().toString()){
                    case "Шифр Цезаря":
                        Intent intentCaesar = new Intent(MainActivity.this, CaesarActivity.class);
                        intentCaesar.putExtra("cryptName", "Caesar");
                        startActivity(intentCaesar);
                        break;
                    case "Одиночная перестановка по ключу":
                        Intent intentSinPer = new Intent(MainActivity.this, SinglePermutationKey.class);
                        intentSinPer.putExtra("cryptName", "SinglePermutationKey");
                        startActivity(intentSinPer);
                        break;
                    case "Аффинный шифр":
                        Intent intentAffine = new Intent(MainActivity.this, AffineActivity.class);
                        intentAffine.putExtra("cryptName", "Affine");
                        startActivity(intentAffine);
                        break;
                    case "Цезарь с ключевым словом":
                        Intent intentCaesarKey = new Intent(MainActivity.this, CaesarKeyWordActivity.class);
                        intentCaesarKey.putExtra("cryptName", "CaesarKeyWord");
                        startActivity(intentCaesarKey);
                        break;
                    case "Двойная перестановка":
                        break;
                    case "Шифр тритемиуса":
                        break;
                    case "Магический квадрат":
                        break;
                    case "Шифр Вижнера":
                        break;
                    case "Шифр Гронсфельда":
                        break;
                    case "Шифр Плейфера":
                        break;
                    case "Шифр Хилла":
                        break;
                    case "Маршрутный шифр":
                        break;
                    case "Поворотная решетка Кардано":
                        break;
                    case "Шифр Решелье":
                        break;
                    case "Шифр Фейстеля":
                        break;
                    case "Кастомный шифр":
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + ((TextView) itemClicked).getText().toString());
                }
            }
        });

    }
}