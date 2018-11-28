package com.example.ad.search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class KategoriaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategoria);

    }

    @SuppressLint("ResourceType")
    public void doSpecyfikacji(View v) {

        TextView textView = (TextView) findViewById(v.getId());
        String text = textView.getText().toString();
        Intent i = new Intent(this, StoczniaActivity.class);
        i.putExtra("nazwa_stoczni", text);

        switch (v.getId()) {
            case R.id.tv_st1:
                i.putExtra("opis", getText(R.string.st1_opis));
                startActivity(i);
                break;
            case R.id.tv_st2:
                i.putExtra("opis", getText(R.string.st2_opis));
                startActivity(i);
                break;
            case R.id.tv_st3:
                i.putExtra("opis", getText(R.string.st3_opis));
                startActivity(i);
                break;
            case R.id.tv_st4:
                i.putExtra("opis", getText(R.string.st4_opis));
                startActivity(i);
                break;
            case R.id.tv_st5:
                i.putExtra("opis", getText(R.string.st5_opis));
                startActivity(i);
                break;
            case R.id.tv_st6:
                i.putExtra("opis", getText(R.string.st6_opis));
                startActivity(i);
                break;
            case R.id.tv_st7:
                i.putExtra("opis", getText(R.string.st7_opis));
                startActivity(i);
                break;
            case R.id.tv_st8:
                i.putExtra("opis", getText(R.string.st8_opis));
                startActivity(i);
                break;
            case R.id.tv_st9:
                i.putExtra("opis", getText(R.string.st9_opis));
                startActivity(i);
                break;
            case R.id.tv_st10:
                i.putExtra("opis", getText(R.string.st10_opis));
                startActivity(i);
                break;
            case R.id.tv_st11:
                i.putExtra("opis", getText(R.string.st11_opis));
                startActivity(i);
                break;
            case R.id.tv_st12:
                i.putExtra("opis", getText(R.string.st12_opis));
                startActivity(i);
                break;
            case R.id.tv_st13:
                i.putExtra("opis", getText(R.string.st13_opis));
                startActivity(i);
                break;
            case R.id.tv_st14:
                i.putExtra("opis", getText(R.string.st14_opis));
                startActivity(i);
                break;
            case R.id.tv_st15:
                i.putExtra("opis", getText(R.string.st15_opis));
                startActivity(i);
                break;
            case R.id.tv_st16:
                i.putExtra("opis", getText(R.string.st16_opis));
                startActivity(i);
                break;
        }


    }
}
