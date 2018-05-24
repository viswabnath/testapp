package com.example.user.connection;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Main6Activity extends AppCompatActivity {

    private User user;

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


                Intent l=new Intent(Main6Activity.this,Main7Activity.class);
                l.putExtra("name",username);
                l.putExtra("id",userid);
                startActivity(l);
// settings
                break;
            case R.id.action_profile:
                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                //profile
                break;
            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "Loggedout Succesfully", Toast.LENGTH_SHORT).show();

                user.setLoggedin(false);
                finish();
                startActivity(new Intent(Main6Activity.this,MainActivity.class));


                if(!user.loggedin()){
                    user.setLoggedin(false);
                    finish();
                    startActivity(new Intent(Main6Activity.this,MainActivity.class));
                }

                //logout
                break;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    ///


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        //custom tool bar
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //calling user for logout function
        user=new User(this);

        //for  back button on action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        //
        ImageView img=(ImageView)findViewById(R.id.imageView);
        //code to say sucessfully order placed
       /* Button btn=(Button)findViewById(R.id.btnsubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent R = getIntent();
                String username = R.getStringExtra("name");
                Integer userid = R.getIntExtra("id", 0);

                Intent intent=new Intent(Main6Activity.this,Main7Activity.class);
                intent.putExtra("name",username);
                intent.putExtra("id",userid);
                Log.e("name",username);
                Log.d("n",Integer.toString(userid));

                startActivity(intent);
            }
        });
*/
    }
}
