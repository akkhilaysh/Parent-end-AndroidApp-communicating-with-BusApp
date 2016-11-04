package com.example.user.probbc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ChildStatusActivity extends Activity {



    ImageView studentImage_statusActivity;

    TextView battery_statusActivity;
    TextView studentName_statusActivity;
    TextView studentStatus_statusActivity;

    SwipeRefreshLayout swipeStatus;
    public int i;
    String studentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_status);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(R.drawable.bbc);
        studentImage_statusActivity=(ImageView)findViewById(R.id.studentPic_statusActivity);

        studentName_statusActivity=(TextView)findViewById(R.id.studentName_statusActivity);
        studentStatus_statusActivity=(TextView)findViewById(R.id.status_statusActivity);
        swipeStatus=(SwipeRefreshLayout)findViewById(R.id.swipe_status);
        i=0;

        Bundle bundle=getIntent().getExtras();
        int studentPic_statusActivity=bundle.getInt("Picture");
        String studentName=bundle.getString("Name");
        String status=bundle.getString("Status");
        studentID=bundle.getString("StudentID");
        studentImage_statusActivity.setImageResource(studentPic_statusActivity);
        studentName_statusActivity.setText(studentName);
        studentStatus_statusActivity.setText(status);

        swipeStatus.setColorSchemeColors(Color.CYAN, Color.BLUE, Color.LTGRAY, Color.RED);

        swipeStatus.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Refreshing...!", Toast.LENGTH_SHORT).show();

                //myOperation();
                new ChildStatusTask().execute();

                swipeStatus.setRefreshing(false);
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
    public class ChildStatusTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    studentStatus_statusActivity.setText(c.getString("StudentStatusDetail"));
                    Log.d("Status",c.getString("StudentStatusDetail"));
                }
                Log.d("Test",studentID);

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


        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {
                URL url = new URL("http://ws.eighty20technologies.com/StudentService/Service1.svc/" +
                        "GetStatusByStudentID/?StudentID="+studentID);
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
    }
    /*public void myOperation(){
        String[] temp={"Home","Onboard","Reached","Onboard"};
        studentStatus_statusActivity.setText(temp[i]);
        if(i<3) {
            i++;
        }else{
            i=0;
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_child) {
            startActivity(new Intent(ChildStatusActivity.this,SelectChildActivity.class));
            return true;
        }else if(id==R.id.action_logout)
        {
            startActivity(new Intent(ChildStatusActivity.this,LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}