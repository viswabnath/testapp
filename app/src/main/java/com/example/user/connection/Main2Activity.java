package com.example.user.connection;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {
    AutoCompleteTextView text1;
    AutoCompleteTextView text2;
    AutoCompleteTextView text3;
    AutoCompleteTextView text4;
    TextView text5;
    Button submit;

//    private static final String DB_URL = "jdbc:mysql://192.168.1.12/android";
//    private static final String USER = "zzz";
//    private static final String PASS = "root";

    private static final String DB_URL="jdbc:mysql://192.168.1.14/android";
    private static final String USER="viswabnath";
    private static final String PASS="root";
//oncreate method for execution


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        text1= findViewById(R.id.username);
        text2= findViewById(R.id.password);
        text3= findViewById(R.id.Mobilenumber);
        text4= findViewById(R.id.scpassword);
        submit= findViewById(R.id.signupbutton);
        text5= findViewById(R.id.loginlink);
        //providing link for login page
                text5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
                    Intent move=new Intent(Main2Activity.this,MainActivity.class);
                     startActivity(move);
                        }
                            });
//providing onclick for submit button and validating all fields

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

                 }
                public void register(){
                   // Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-A-Za-z]");

                    if(text1.getText().length()<6){
                    Toast.makeText(Main2Activity.this,"  Username must be minimum six characters", Toast.LENGTH_SHORT).show();

                    }


                    else{
                    if(text2.getText().length()<6){
                        Toast.makeText(Main2Activity.this, "Password is too short", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        if(!text2.getText().toString().equals(text4.getText().toString())){
                            Toast.makeText(Main2Activity.this,"Password mismatch",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            if(text3.getText().length()>10){
                                Toast.makeText(Main2Activity.this, "Mobile number length exceeded", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                if (text3.getText().length() < 10) {
                                    Toast.makeText(Main2Activity.this, "Mobile Number should be minimum 10 characters ", Toast.LENGTH_SHORT).show();
                                } /*else {
                                    if (regex.matcher(text3.getText().toString()).find()) {
                                        Toast.makeText(Main2Activity.this, "special", Toast.LENGTH_SHORT).show();


                                    }*/ else {

                                        DoRegister doRegister = new DoRegister();
                                        doRegister.execute();
                                    }
                                }



                              //  }



                            }
                            }



                    }





            }

//databse code to store user details in database


    class DoRegister extends AsyncTask<String, String, String> {
                    String username = text1.getText().toString();
                    String password= text2.getText().toString();
                    String number = text3.getText().toString();
                    String z = "";

                    @Override
                    protected void onPreExecute() { }


                    @Override
                    protected String doInBackground(String... strings) {


                        try {
                            String number = text3.getText().toString();


                            Class.forName("com.mysql.jdbc.Driver");
                            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            if (conn == null) {

                            } else {
                                String query = " select * from userlogin where phone='" + number + "'";
                                Log.e("n", " select * from userlogin where phone='" + number + "'");
                                Statement stmt = conn.createStatement();
                                ResultSet result = stmt.executeQuery(query);


                                if (result.next()) {
                                    Intent j = new Intent(Main2Activity.this, Main2Activity.class);

                                    startActivity(j);
                                }
                                else{

                                    try {
                                        String username = text1.getText().toString();
                                        String password = text2.getText().toString();
                                        String number1 = text3.getText().toString();
                                        Class.forName("com.mysql.jdbc.Driver");
                                        Connection conn1 = DriverManager.getConnection(DB_URL, USER, PASS);
                                        if (conn1 == null) {
                                            z = "something went wrong";
                                        } else {
                                            String query1 = " insert into userlogin (username,password,phone)values('" + username + "','" + password + "','" + number1 + "')";
                                            Log.e("n", " insert into userlogin (username,password,phone)values('" + username + "','" + password + "','" + number1 + "' );");
                                            Statement stmt1 = conn1.createStatement();
                                           Integer i= stmt1.executeUpdate(query1);
                                            if(i>0) {
                                                Intent j = new Intent(Main2Activity.this, MainActivity.class);

                                                startActivity(j);
                                            }
                                            }

                                            } catch (Exception e) { e.printStackTrace(); }
                                            }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    return z;
                        }


                    public void onPostExecute(String z) {
                        Toast.makeText(Main2Activity.this, z + "", Toast.LENGTH_LONG).show();

                    }
                }


            });


        }
    }