package com.example.ad.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class KategoriaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategoria);

    }

    public void doSpecyfikacji(View v) {

        TextView textView = (TextView) findViewById(v.getId());
        String text = textView.getText().toString();
        Toast.makeText(this, "Wybrałeś stocznie: \n" + text, Toast.LENGTH_LONG).show();


        switch (v.getId()) {
            case R.id.tv_st1:
                //Toast.makeText(this, "Wybrałeś stocznie: " + text, Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_st2:
                //Toast.makeText(this, "Wybrałeś stocznie: " + text, Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_st3:

                break;
            case R.id.tv_st4:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st5:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st6:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st7:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st8:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st9:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st10:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st11:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st12:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st13:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st14:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st15:
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_st16:
//                startActivity(new Intent(this, MainActivity.class));
                break;
        }


    }
}
