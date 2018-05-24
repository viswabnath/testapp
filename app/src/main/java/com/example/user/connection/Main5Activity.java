package com.example.user.connection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main5Activity extends AppCompatActivity {
    ImageView img;
    EditText text, text1, text2, text3;
    private User user;

    Button b;

//    private static final String DB_URL = "jdbc:mysql://192.168.1.12/android";
//    private static final String USER = "zzz";
//    private static final String PASS = "root";

    private static final String DB_URL="jdbc:mysql://192.168.1.14/android";
    private static final String USER="viswabnath";
    private static final String PASS="root";

    // code for the dots on action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_myorders:
//                Toast.makeText(getApplicationContext(), "Orders", Toast.LENGTH_SHORT).show();
                Intent K=getIntent();
                String username=K.getStringExtra("name");
                Integer  userid=K.getIntExtra("id",0);


                Intent l=new Intent(Main5Activity.this,Main7Activity.class);
                l.putExtra("name",username);
                l.putExtra("id",userid);
                startActivity(l);
                //orders
                break;
            case R.id.action_profile:
                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                //profile
                break;
            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "Loggedout Succesfully", Toast.LENGTH_SHORT).show();

                user.setLoggedin(false);
                finish();
                startActivity(new Intent(Main5Activity.this,MainActivity.class));


                if(!user.loggedin()){
                    user.setLoggedin(false);
                    finish();
                    startActivity(new Intent(Main5Activity.this,MainActivity.class));
                }

                //logout
                break;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                //settings
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    ///



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        //custom tool bar
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //calling user for logout function
        user=new User(this);

        //for  back button on action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        img = (ImageView) findViewById(R.id.imageView);
        text = (EditText) findViewById(R.id.description);
        text1 = (EditText) findViewById(R.id.quantity);
        text2 = (EditText) findViewById(R.id.price);

        text3 = (EditText) findViewById(R.id.quality);
        b = (Button) findViewById(R.id.button);

        Intent k = getIntent();
        String view = k.getStringExtra("product");
        String name = k.getStringExtra("name");
        Integer id = k.getIntExtra("id", 0);
        if (getIntent().hasExtra("byteArray")) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            img.setImageBitmap(bitmap);
        }

        final Spinner mySpinner = (Spinner) findViewById(R.id.dropdown);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Main5Activity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantity));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        b.setOnClickListener(new View.OnClickListener() {


                public void onClick(View v) {
                    if(text.getText().length()!=0&&text1.getText().length()!=0&&text2.getText().length()!=0&&text3.getText().length()!=0) {
                        DoRegister doRegister = new DoRegister();
                        doRegister.execute();
                        Intent R = getIntent();
                        String username = R.getStringExtra("name");
                        Integer userid = R.getIntExtra("id", 0);


                        Intent j = new Intent(Main5Activity.this, Main6Activity.class);
                        j.putExtra("name",username);
                        j.putExtra("id",userid);
                        Log.e("name",username);
                        Log.d("n",Integer.toString(userid));

                        startActivity(j);



                    }
                }
                //code to store all the details of user order

                class DoRegister extends AsyncTask<String, String, String> {
                String description = text.getText().toString();
                String quantity= text1.getText().toString();
                String price = text2.getText().toString();
                String quality = text3.getText().toString();
                String quantityin=mySpinner.getSelectedItem().toString();
                String z = "";

                @Override
                protected void onPreExecute() { }

                @Override
                protected String doInBackground(String... strings) {
                    try {
                        Intent R = getIntent();
                        String product = R.getStringExtra("view");
                        String username = R.getStringExtra("name");
                        Integer userid = R.getIntExtra("id", 0);
                        String  number=R.getStringExtra("phone");

                        String description = text.getText().toString();
                        String quantity = text1.getText().toString();
                        String price = text2.getText().toString();
                        String quality = text3.getText().toString();
                        String producttype = R.getStringExtra("product");
                        String quantityin=mySpinner.getSelectedItem().toString();
                        String pending="pending";

                        Class.forName("com.mysql.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        if (conn == null) {
                            z = "something went wrong";
                        } else {
                            String query = " insert into ordertable (userid,username,product,producttype,description,quantity,quantityin,quality,price,phone,status)values('" + userid + "','" + username + "','" + product + "','" + producttype + "','" + description + "','" + quantity + "','"+quantityin+"','" + quality + "','" + price + "','" + number + "','"+pending+"');";
                            Log.e("n", " insert into ordertable (userid,username,product,producttype,description,quantity,quality,price,phone,status)values('" + userid + "','" + username + "','" + product + "','" + producttype + "','" + description + "','" + quantity + "','" + quality + "','" + price + "','"+number+"','"+pending+"');");
                            Statement stmt = conn.createStatement();
                            stmt.executeUpdate(query);





                        }
                        conn.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return z;
                }

                public void onPostExecute(String z) {
                    Toast.makeText(Main5Activity.this, z + "", Toast.LENGTH_LONG).show();

                }
            }


        });


    }
}