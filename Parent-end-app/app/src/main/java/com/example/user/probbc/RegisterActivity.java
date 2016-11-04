package com.example.user.probbc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity{

    EditText fname,lname,email,pass,confpass,mobile;
    String firstName,lastName,mail,password,mob;
    Button register;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        fname= (EditText) findViewById(R.id.fname);
        lname= (EditText) findViewById(R.id.lname);
        email= (EditText) findViewById(R.id.email);
        pass= (EditText) findViewById(R.id.pass);
        confpass= (EditText) findViewById(R.id.confpass);
        mobile= (EditText) findViewById(R.id.mobile);


        register=(Button)findViewById(R.id.btnRegister);

        if (!isConnected()) {

            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this)
                    .create();

            // Setting Dialog Title
            alertDialog.setTitle("Error");

            // Setting Dialog Message
            alertDialog
                    .setMessage("OOPS! It seems Internet connection is lost or too slow. Please try again.");

            // Setting Icon to Dialog
            alertDialog.setIcon(android.R.drawable.stat_notify_error);


            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(),
                    // "You clicked on OK", Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName=fname.getText().toString();
                lastName=lname.getText().toString();
                mail=email.getText().toString();
                password=pass.getText().toString();
               String cPass=confpass.getText().toString();
                mob=mobile.getText().toString();
                if(firstName.equalsIgnoreCase("")||lastName.equalsIgnoreCase("")||mail.equalsIgnoreCase("")
                        ||password.equalsIgnoreCase("")||cPass.equalsIgnoreCase("")||mob.equalsIgnoreCase(""))
                {

                    Toast.makeText(RegisterActivity.this,"None of the fields should be empty", Toast.LENGTH_LONG).show();
                }else if(!(password.equals(cPass))){

                      Toast.makeText(RegisterActivity.this,"Passwords are Not Matching",Toast.LENGTH_SHORT).show();
                }else if (!isValidEmail(mail))
                {
                    Toast.makeText(RegisterActivity.this,"Invalid Email ID",Toast.LENGTH_SHORT).show();
                }else if (mob.length()<10){
                    Toast.makeText(RegisterActivity.this,"Enter a 10 Digit Mobile Number",Toast.LENGTH_SHORT).show();
                }
                else if (isConnected()){

                    dialog=new ProgressDialog(RegisterActivity.this,R.style.AppTheme);
                    dialog.setCancelable(false);
                    dialog.setProgressStyle(android.R.style.Widget_ProgressBar);
                    dialog.setMessage("Registering....");
                    dialog.show();

                    new AuthenticateUser().execute();

                /*Toast.makeText(RegisterActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                Intent registerIntent=new Intent();
                registerIntent.setClass(RegisterActivity.this, LoginActivity.class);

               startActivity(registerIntent);*/
            }
            }
        });

        //click-event ends


    }
    public boolean isConnected() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())

            return true;
        else
            return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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

    public class AuthenticateUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {

                URL url = new URL("http://ws.eighty20technologies.com/StudentService/Service1.svc/InsertRegistration/"+
                        "?Email="+mail+"&Password="+password+
                        "&FirstName="+firstName+"&LastName="+lastName+
                        "&PhoneNumber="+mob+"&LoginType=0");
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
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (NullPointerException e) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IllegalStateException e) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again.",

                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                if (dialog.isShowing()) {
                    dialog.dismiss();

                }
                runOnUiThread(new Runnable() {
                    public void run() {

                        /*Toast.makeText(
                                getBaseContext(),
                                "OOPS! It seems Internet connection is lost or too slow. Please try again5.",
                                Toast.LENGTH_SHORT).show();*/
                    }
                });

            }

            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            try {


                if (result.equals("1")) {

                    Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "Now Login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();

                } else if (result.equals("0")) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(
                            RegisterActivity.this,
                            "Registration Failed!",
                            Toast.LENGTH_LONG).show();
                }else if(result.equals("2")){
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(
                            RegisterActivity.this,
                            "User Already Registered",
                            Toast.LENGTH_LONG).show();

                } else {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(
                            RegisterActivity.this,
                            "OOPS! It seems Internet connection is lost or too slow. Please try again.",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                runOnUiThread(new Runnable() {
                    public void run() {


                    }
                });

            }
        }

    }
}
