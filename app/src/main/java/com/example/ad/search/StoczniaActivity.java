package com.example.ad.search;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StoczniaActivity extends AppCompatActivity {
    private TextView te_smugi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocznia);
        Intent i = getIntent();
        String name = i.getExtras().getString("nazwa_stoczni");
        String opis = i.getExtras().getString("opis");
        //Toast.makeText(this, "Wybrałeś stocznie: \n" + name, Toast.LENGTH_LONG).show();
        TextView nStoczni = (TextView) findViewById(R.id.tv_nazwa_stoczni);
        TextView oStoczni = (TextView) findViewById(R.id.tv_opis_stoczni);
        te_smugi = (TextView) findViewById(R.id.tv_smugi);

        nStoczni.setText(name);
        oStoczni.setText(opis);

        if(name.equals("FAIRLINE")){
            te_smugi.setVisibility(View.VISIBLE);
        }
    }


    public void smugi(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setDataAndType(Uri.parse("http://192.168.100.226/imager/pdf/index?filename=" + getText(R.string.pdf_smugi)), "application/pdf");
        startActivity(browserIntent);

    }


}

