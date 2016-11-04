package com.example.user.probbc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class  SelectChildActivity extends Activity {

    ListView  selectionList;
    SCAdapter adapter;
    SwipeRefreshLayout swipe;
    String bbcLogin;
    ProgressDialog dialog;
    ArrayList<StudentStatusModel> studentListJSON;

    String[] studentIDS=new String[10];
    String[] names=new String[10]; //{"Harshad Shinde","Ankit Verma","Ashwath Kumar","Akkhilaysh Shetty"};
    String[] sts=new String[10]; //{"Onboard","Reached","At Home","Onboard"};
    int[] pics=new int[]{R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_child);
        selectionList=(ListView)findViewById(R.id.listView);
        swipe=(SwipeRefreshLayout)findViewById(R.id.swipeChildList);
        SharedPreferences sharedPreferences=getSharedPreferences(LoginActivity.MyPREFERENCES, 0);
        bbcLogin=sharedPreferences.getString(LoginActivity.BBCLoginID,"2");
        studentListJSON=new ArrayList<StudentStatusModel>();


        new SelectChildTask().execute();
        selectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent SelectChildListIntent = new Intent(SelectChildActivity.this, ChildStatusActivity.class);

                SelectChildListIntent.putExtra("Picture", pics[position]);
                SelectChildListIntent.putExtra("Name", names[position]);
                SelectChildListIntent.putExtra("Status", sts[position]);
                SelectChildListIntent.putExtra("StudentID",studentIDS[position]);
                startActivity(SelectChildListIntent);


            }

        });
        swipe.setColorSchemeColors(Color.BLUE,Color.RED,Color.LTGRAY,Color.CYAN);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            myOperation();
                swipe.setRefreshing(false);
            }
        });

    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
    public class SelectChildTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {


            String result = "";

            try {
                URL url = new URL("http://ws.eighty20technologies.com/StudentService/Service1.svc/GetStatusByBBCLoginID/"+
                        "?BBCLoginID="+bbcLogin);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());



                result = convertStreamToString(stream);

            } catch (UnknownHostException e) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (SocketTimeoutException e) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (NullPointerException e) {

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IllegalStateException e) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONArray jsonArray = new JSONArray(result);
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject c = jsonArray.getJSONObject(i);


                    names[i]=c.getString("StudentName");
                    sts[i]=c.getString("StudentStatusDetail");
                    studentIDS[i]=c.getString("StudentID");
                    StudentStatusModel ssm = new StudentStatusModel(c.getString("SchoolName"), c.getString("StudentID"),
                            c.getString("StudentName"), c.getString("StudentNumber"),c.getString("StudentStatusDetail"));
                    Log.d("St",c.getString("StudentStatusDetail"));
                    studentListJSON.add(ssm);
                }
                    adapter=new SCAdapter(SelectChildActivity.this,studentListJSON);
                    selectionList.setAdapter(adapter);


            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            }
    }

    public void myOperation(){
        studentListJSON.clear();
        new SelectChildTask().execute();
    }
}
