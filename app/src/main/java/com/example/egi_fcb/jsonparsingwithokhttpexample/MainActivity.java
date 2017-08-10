package com.example.egi_fcb.jsonparsingwithokhttpexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.egi_fcb.jsonparsingwithokhttpexample.Adapter.MyArrayAdapter;
import com.example.egi_fcb.jsonparsingwithokhttpexample.Model.MyDataModel;
import com.example.egi_fcb.jsonparsingwithokhttpexample.Parser.JSONParser;
import com.example.egi_fcb.jsonparsingwithokhttpexample.Utils.InternetConnection;
import com.example.egi_fcb.jsonparsingwithokhttpexample.Utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Sumber Pembelajaran : http://www.pratikbutani.com/2016/01/android-json-parsing-using-okhttp-example-with-custom-view-imageview-textview/

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("JSON Parsing");

        list = new ArrayList<>();

        adapter = new MyArrayAdapter(this, list);

        //Getting List and Setting List Adapter
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getName() + " => " + list.get(position).getPhone(), Snackbar.LENGTH_LONG).show();
            }
        });


        Snackbar.make(findViewById(R.id.parentLayout), "Click on Rotate to Load JSON", Snackbar.LENGTH_LONG).show();
        //Toast toast = Toast.makeText(getApplicationContext(), "Click on Rotate to Load JSON", Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.CENTER, 0, 0);
        //toast.show();
    }

    //Creating Get Data
    class GetDataTask extends AsyncTask<Void, Void, Void>{
        ProgressDialog dialog;

        @Override
        protected void onPreExecute(){
            //Progress
            list.clear();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("I am getting your JSON");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            //Getting JSON Object
            JSONObject jsonObject = JSONParser.getDataFromWeb();
            try {
                if (jsonObject != null){
                    if (jsonObject.length() > 0){
                        //Getting Array Contact
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);

                        //Check Length of Array
                        int lenArray = array.length();
                        if(lenArray > 0){
                            for (int jIndex = 0; jIndex < lenArray; jIndex++){
                                //Creating Every Time New Object
                                MyDataModel model = new MyDataModel();

                                //Getting Object
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString(Keys.KEY_NAME);
                                String email = innerObject.getString(Keys.KEY_EMAIL);
                                String image = innerObject.getString(Keys.KEY_PROFILE_PIC);

                                //Getting Object Drom Object Phone
                                JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setName(name);
                                model.setEmail(email);
                                model.setPhone(phone);
                                model.setImage(image);

                                list.add(model);
                            }
                        }
                    }else {
                        Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                Log.i(JSONParser.TAG, "" + e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            dialog.dismiss();

            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // menambahkan icon menu pada toolbar
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        int id = item.getItemId();

        //memberi aksi menu
        if (id == R.id.action_about) {
            if (InternetConnection.checkConnection(getApplicationContext())) {
                new GetDataTask().execute();
            } else {
                Snackbar.make(findViewById(R.id.parentLayout), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}