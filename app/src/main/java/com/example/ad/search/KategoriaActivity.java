package com.example.ad.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class KategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategoria);
    }
    public void testKlik(View v){
        Intent intent;
        intent = new Intent(KategoriaActivity.this, SearchActivity.class);
        startActivity(intent);
    }

}
