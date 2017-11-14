package com.example.ad.search;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class SearchActivity extends AppCompatActivity {

    private String TAG = SearchActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String scan = getIntent().getStringExtra("text");
        EditText edtText = (EditText) findViewById(R.id.editText);
        edtText.setText(scan);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);


        final Button szukaj = (Button) findViewById(R.id.button);

        szukaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new GetContacts().execute();

            }
        });
        EditText userinput = (EditText) findViewById(R.id.editText);


        userinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    new GetContacts().execute();
                    return true;
                }
                return false;
            }
        });

        /*userinput.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new GetContacts().execute();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/ //Fragment nie gotowy do obsługi

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selected = ((TextView) view.findViewById(R.id.filename)).getText().toString();
                String encodeString = null;
                try {
                    encodeString = URLEncoder.encode(selected, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse("http://192.168.100.226/imager/pdf/index?filename=" + encodeString), "application/pdf");
                startActivity(browserIntent);
            }
        });
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        final EditText userinput = (EditText) findViewById(R.id.editText);
        String result = userinput.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchActivity.this, "Pobieram listę rysunków...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://192.168.100.226/KotnizRejestratorAPI/api/Rysunki/GetPDF?name=" + result + "";
            //String url2 = "http://192.168.100.226/KotnizRejestratorAPI/api/Rysunki/GetPDF?name=" + scan + "";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr + url);
            if (jsonStr != null) {
                try {
                    JSONArray jsonObj = new JSONArray(jsonStr);
                    // looping through All Contacts
                    contactList.removeAll(contactList);
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject c = jsonObj.getJSONObject(i);
                        String id = c.getString("id");
                        String filename = c.getString("filename");
                        String path = c.getString("path");

                        filename = filename.replace("©","ę");
                        filename = filename.replace("\u0088","ł");
                        filename = filename.replace("˘","ó");
                        filename = filename.replace("Ą","ą");
                        filename = filename.replace("\u0098","ś");
                        filename = filename.replace("ľ","ż");
                        filename = filename.replace("ť","Ł");
                        filename = filename.replace("ŕ","Ó");
                        filename = filename.replace("ä","ń");
                        filename = filename.replace("†","ć");
                        //filename = filename.replace("ä","ń");
                        //filename = filename.replace("ä","ń");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("filename", filename);
                        contact.put("path", path);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "       Nie można pobrać rysunków.\nSprawdź połączenie z lokalną siecią!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ListAdapter adapter = new SimpleAdapter(SearchActivity.this, contactList,
                    R.layout.list_item, new String[]{"filename"},
                    new int[]{R.id.filename});
            lv.setAdapter(adapter);
            if (lv == null) {
                Toast.makeText(SearchActivity.this, "Nie znaleziono rysunków o podanej nazwie", Toast.LENGTH_LONG).show();
            }
        }
    }
}
