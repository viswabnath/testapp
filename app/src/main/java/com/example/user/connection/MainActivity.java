package com.example.user.connection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.SharedPreferences;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

     AutoCompleteTextView username;
    AutoCompleteTextView password;
    TextView textView;

    private Button Submit ;
    private User user;


//private static final String DB_URL="jdbc:mysql://192.168.1.12/android";
//private static final String USER="zzz";
//private static final String PASS="root";

    private static final String DB_URL="jdbc:mysql://192.168.1.14/android";
    private static final String USER="viswabnath";
    private static final String PASS="root";

//oncreate method method for front end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//calling user class
       //user=new User(this);

//declaring all the text views and button views in th avtivity-main.xml
        username= (AutoCompleteTextView) findViewById(R.id.lmn);
        password=(AutoCompleteTextView)findViewById(R.id.userPassword);
        textView=(TextView)findViewById(R.id.signuplink);
            Submit = (Button) findViewById(R.id.loginbutton);
//make a click move to sign up page
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i);
                }
            });
            //checking whether user is loged in or not

//if(user.loggedin()){
 //   startActivity(new Intent(MainActivity.this,Main3Activity.class));
  //  finish();
//}

//providing click listener for submit button
            Submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Send objSend = new Send();
                    objSend.execute("");

                    }

//class to check  whether user exits in db or not and move to next page


                class Send extends AsyncTask<String, String, String> {
                    String msg = "";
                    String Text = username.getText().toString();
                    String Text1= password.getText().toString();
                    private int k;

                    @Override
                    protected void onPreExecute() {
                        msg="please wait";
                    }

                    @Override
                    protected String doInBackground(String... strings) {

                        String Text = username.getText().toString();
                        String Text1=password.getText().toString();
//database code to check user details
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            if (conn == null) {
                                msg = "something went wrong";
                            } else {
                                String query = "select* from userlogin where username='"+Text+"' AND  password='"+Text1+"' ";
                                Log.e("n","select * from userlogin where username='"+Text+"'AND password='"+Text1+"'");
                                Statement stmt = conn.createStatement();
                             ResultSet result= stmt.executeQuery(query);
                             if(result.next()) {
                                 //fetching id of user  and number of user
                                    int k = result.getInt(1);
                                    String number=result.getString(4);
                                    Log.d("n", Integer.toString(k));
                                    /*user.setLoggedin(true);*/
                                    //code to move from one page to another
                                       Intent j = new Intent(MainActivity.this, Main3Activity.class);
                                        j.putExtra("name", Text);
                                        j.putExtra("id", k);
                                        j.putExtra("phone",number);
                                        Log.e("n", Text);
                                        Log.d("n", Integer.toString(k));
                                        startActivity(j);
                                        finish();
                                        }

                            }
                            conn.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return msg;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                    }
                }


            });


        }




}