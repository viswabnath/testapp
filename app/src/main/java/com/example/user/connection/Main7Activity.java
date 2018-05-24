package com.example.user.connection;

import android.app.Presentation;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main7Activity extends AppCompatActivity {


    private ArrayList<OnlyOneList> itemsArrayList;
    private MyAppAdapter  myAppAdapter;
    private ListView listView;
    private User user;
    private  boolean success=false;
//    private static final String DB_URL="jdbc:mysql://192.168.1.12/android";
//    private static final String USER="zzz";
//    private static final String PASS="root";

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
                Toast.makeText(getApplicationContext(), "Orders", Toast.LENGTH_SHORT).show();
                //settings
                break;
            case R.id.action_profile:
                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                //profile
                break;
            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "Loggedout Succesfully", Toast.LENGTH_SHORT).show();

                user.setLoggedin(false);
                finish();
                startActivity(new Intent(Main7Activity.this,MainActivity.class));


                if(!user.loggedin()){
                    user.setLoggedin(false);
                    finish();
                    startActivity(new Intent(Main7Activity.this,MainActivity.class));
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
        setContentView(R.layout.activity_main7);
        //custom tool bar
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //calling user for logout function
        user=new User(this);

        //for  back button on action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

/*user=new User(this);
if(!user.loggedin()){
    logout();
}*/

     //   Button btnlogout=(Button)findViewById(R.id.btnlogout);

        listView=(ListView)findViewById(R.id.listView);
        itemsArrayList=new ArrayList<OnlyOneList>();

        SyncData orderData=new SyncData();
        orderData.execute("");
       /* btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
*/
    }
  /*      public void  logout(){
        user.setLoggedin(false);
        finish();
        startActivity(new Intent(Main3Activity.this,MainActivity.class));
        }
*/
//code to read all the orders placed in order table by the user id
    private  class SyncData extends AsyncTask<String,String,String>{
        String msg="internet/Db";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress=ProgressDialog.show(Main7Activity.this,
                    "Synchronising","Listview Loading please wait...",true);

        }


        @Override
        protected String doInBackground(String... strings) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection  conn= DriverManager.getConnection(DB_URL,USER,PASS);
                if(conn==null){
                    success=false;
                }else{
                    Intent R = getIntent();
                    String username = R.getStringExtra("name");
                    Integer userid = R.getIntExtra("id", 0);


                    String query="SELECT imagetable.image,ordertable.producttype,ordertable.orderid,ordertable.organizationid,  ordertable.organization from imagetable INNER JOIN ordertable ON imagetable.producttype=ordertable.producttype WHERE ordertable.userid='"+userid+"'";
                   Log.e("n","SELECT imagetable.image,ordertable.producttype ordertable.orderid,ordertable.organizationnid ,ordertable.organization from imagetable INNER JOIN ordertable ON imagetable.producttype=ordertable.producttype WHERE ordertable.userid='"+userid+"'");

                    Statement stmt=conn.createStatement();
                    ResultSet rs=stmt.executeQuery(query);
                    if(rs!=null){
                        while (rs.next()){
                            try{
                                itemsArrayList.add(new OnlyOneList(rs.getString("producttype"),rs.getBytes("image"),rs.getInt("orderid"),rs.getInt("organizationid"),rs.getString("organization")));


                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        msg="Found";
                        success=true;
                    }else{
                        msg=" u dont have any orders!";
                        success=false;
                    }

                    }

            }catch (Exception ex){
                ex.printStackTrace();
                Writer writer=new StringWriter();
                ex.printStackTrace(new PrintWriter(writer));
                msg=writer.toString();
                success=false;
            }
            return msg;
        }


        public void onPostExecute(String msg){
            progress.dismiss();
            Toast.makeText(Main7Activity.this,msg+"",Toast.LENGTH_LONG).show();
            if(success==false){

            }else{
                try{
                    myAppAdapter =new MyAppAdapter(itemsArrayList,Main7Activity.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);

                }catch(Exception ex){
                }
            }

        }



    }

    public class MyAppAdapter extends BaseAdapter {

        public class ViewHolder {


            TextView textName;
            ImageView imageView;
        }

        public List<OnlyOneList> parkingList;
        public Context context;
        ArrayList<OnlyOneList> arrayList;






        private MyAppAdapter(List<OnlyOneList> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arrayList = new ArrayList<OnlyOneList>();
            arrayList.addAll(parkingList);
        }

        public int getCount() {
            return parkingList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder = null;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_content, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.textName = (TextView) rowView.findViewById(R.id.textName);
                viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            final OnlyOneList food=parkingList.get(position);
            //get organization and organization id and send to next page

            viewHolder.textName.setText(food.getName() + "");
            byte[] foodimage= food.getImg();
          final  Bitmap bitmap= BitmapFactory.decodeByteArray(foodimage,0,foodimage.length);
            viewHolder.imageView.setImageBitmap(bitmap);
            final Integer orderId=food.getOrderid();
            final Integer organizationid=food.getOrganizationid();
            final String organization=food.getOrganization();

            /*Picasso.with(context).load(parkingList.get(position).getImg()).into(viewHolder.imageView);

             */       rowView.setOnClickListener(new AdapterView.OnClickListener(){


                @Override
                public void onClick(View v) {



                    Intent i=new Intent(Main7Activity.this,Main8Activity.class);
                    ByteArrayOutputStream bs=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,50,bs);
                    i.putExtra("product",parkingList.get(position).getName());
                    i.putExtra("byteArray",bs.toByteArray());
                    i.putExtra("orderid",orderId);
                    i.putExtra("organizationid",organizationid);
                    i.putExtra("organization",organization);
                    Log.e("product",parkingList.get(position).getName());

                    startActivity(i);
                }
            });
            return rowView;


        }


    }
}