package com.example.ad.search;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private String TAG = SearchActivity.class.getSimpleName();
    private ListView lv;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    //private List<String> listDataHeader;
    //private HashMap<String, List<String>> listHash;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    ArrayList<HashMap<String, String>> contactList;
    static final List<String> historia = new ArrayList<>();
    static final List<String> listDataHeader = new ArrayList<>();
    static final HashMap<String, List<String>> listHash = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (listDataHeader.isEmpty()) {
            listDataHeader.add("Historia wyszukiwania");
        }
        String scan = getIntent().getStringExtra("text");
        EditText edtText = (EditText) findViewById(R.id.editText);
        edtText.setText(scan);

        listView = (ExpandableListView) findViewById(R.id.lvExp);
        //Collections.reverse(historia);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);


        final Button szukaj = (Button) findViewById(R.id.button);

        szukaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new GetContacts().execute();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            }
        });

        EditText userinput = (EditText) findViewById(R.id.editText);
        userinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    new GetContacts().execute();
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

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
                bindData(selected);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse("http://192.168.100.226/imager/pdf/index?filename=" + encodeString), "application/pdf");
                startActivity(browserIntent);

            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                String selected = ((TextView) view.findViewById(R.id.lblListItem)).getText().toString();
                String encodeString = null;
                try {
                    encodeString = URLEncoder.encode(selected, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //bindData(selected);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse("http://192.168.100.226/imager/pdf/index?filename=" + encodeString), "application/pdf");
                startActivity(browserIntent);

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.emergency_open:
                EditText edtText = findViewById(R.id.editText);
                String encodeString = edtText.getText().toString() + ".pdf";
                try {
                    encodeString = URLEncoder.encode(encodeString, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse("http://192.168.100.226/imager/pdf/index?filename=" + encodeString), "application/pdf");
                startActivity(browserIntent);
                return super.onOptionsItemSelected(item);
            case R.id.relese_the_history:
                historia.removeAll(historia);
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindData(String selected) {

        Collections.reverse(historia);
        historia.add(selected);
        listHash.put(listDataHeader.get(0), historia);
        Collections.reverse(historia);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);
//        listView.onSaveInstanceState();
    }

    protected void onResume() {
        super.onResume();
        listHash.put(listDataHeader.get(0), historia);
        //  Collections.reverse(historia);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);
//        listView.onSaveInstanceState();
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

                        filename = filename.replace("©", "ę");
                        filename = filename.replace("\u0088", "ł");
                        filename = filename.replace("˘", "ó");
                        filename = filename.replace("Ą", "ą");
                        filename = filename.replace("\u0098", "ś");
                        filename = filename.replace("ľ", "ż");
                        filename = filename.replace("ť", "Ł");
                        filename = filename.replace("ŕ", "Ó");
                        filename = filename.replace("ä", "ń");
                        filename = filename.replace("†", "ć");
                        filename = filename.replace("¤", "ą");
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

    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "SCAN_MODE");
            //intent.putExtra("SCAN_CAMERA_ID", 1); when you want to use front camera, but it's hard to focus on barcode...
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if ("SCAN_RESULT" != null) {
                    String contents = intent.getStringExtra("SCAN_RESULT");
                    intent = new Intent(this, SearchActivity.class);
                    intent.putExtra("text", contents);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Nie zeskanowano kodu!", Toast.LENGTH_LONG).show();
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
