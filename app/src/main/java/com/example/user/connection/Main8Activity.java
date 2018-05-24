package com.example.user.connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main8Activity extends AppCompatActivity {


    private ArrayList<ClassList> itemsArrayList;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        //custom tool bar
        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //calling user for logout function
        user=new User(this);

        //for  back button on action bar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        listView=(ListView)findViewById(R.id.listView);
        final Button button=(Button)findViewById(R.id.button1);
        //creating onclick to button
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       move();
    }
});

        itemsArrayList=new ArrayList<ClassList>();
        //class for retriving all the details about the product from orders
        SyncData orderData=new SyncData();
        orderData.execute("");
        resolve();
        }
        //method to retrive status of a product into a button
        public void resolve(){
        //class to retrive status
        Send objsend=new Send();
        objsend.execute("");

        }
        //method to send request is approved to admin
public void move(){
        //class to check whether  request is present  or not if present it will update organisation name once click

        Update  objupdate=new Update();
        objupdate.execute("");
}
//
private class Update extends  AsyncTask<String,String,String>{
Button button;
String msg="internet/db";
    @Override
    protected String doInBackground(String... strings) {
        Button button=(Button)findViewById(R.id.button1);
        if( button.getText().toString().equals("request")){
            Intent k=getIntent();
            final String product1=k.getStringExtra("product");

            final Integer orderid1=k.getIntExtra("orderid",0);
            final Integer organizationid=k.getIntExtra("organizationid",0);
            String organization=k.getStringExtra("organization");


                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                        if (conn == null) {

                        } else {
                            String query = "UPDATE ordertable  SET status='"+organizationid+"' where orderid='"+orderid1+"'  ";
                            Log.e("n","UPDATE ordertable  SET status='"+organizationid+"' where orderid='"+orderid1+"'");
                            Statement stmt = conn.createStatement();
                            Integer i= stmt.executeUpdate(query);
                            button.setText(organization);


                                Toast.makeText(Main8Activity.this, "your order is under taken", Toast.LENGTH_SHORT).show();


                        }
                        conn.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


        return null;
    }
}

        private class Send extends AsyncTask<String,String,String>{
        Button button;
        String msg="internet/DB";

            @Override
            protected String doInBackground(String... strings) {
                Button button = (Button) findViewById(R.id.button1);
                Intent j = getIntent();
                String product = j.getStringExtra("product");

                final Integer orderid = j.getIntExtra("orderid", 0);
                Integer organisationid = j.getIntExtra("organizationid", 0);
                try {


                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    if (conn == null) {
                        success = false;
                    } else {
                        String query = "SELECT  status,organization,organizationid from ordertable where orderid='" + orderid + "' ";
                        Log.e("n", "SELECT status,organization,organizationid from ordertable where orderid='" + orderid + "' ");
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                            if (rs != null) {
                                while (rs.next()) {
                                    try {

                                        String h = rs.getString("status");
                                        String l = rs.getString("organization");
                                        Integer v = rs.getInt("organizationid");
                                        if (h.equals("pending")) {
                                            button.setText(h);
                                        } else if (h.equals("request")) {
                                            button.setText(h);
                                        } else {
                                            button.setText((l));
                                        }

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }

                                msg = "Found";
                                success = true;
                            } else {
                                msg = "No Data found!";
                                success = false;
                            }
                        }

                    }catch(Exception ex){
                        ex.printStackTrace();
                        Writer writer = new StringWriter();
                        ex.printStackTrace(new PrintWriter(writer));
                        msg = writer.toString();
                        success = false;
                    }


                    return null;
                }

        }



    private  class SyncData extends AsyncTask<String,String,String>{
        TextView text;

        String msg="internet/Db";
        ProgressDialog progress;
        @Override
        protected void onPreExecute() {



        }
        protected String doInBackground(String... strings) {
            text=(TextView)findViewById(R.id.Texttvv);
            ImageView  img = (ImageView) findViewById(R.id.logo);

            Intent j=getIntent();
            String product=j.getStringExtra("product");

            final Integer orderid=j.getIntExtra("orderid",0);
            Integer organisationid=j.getIntExtra("organizationid",0);

            if (getIntent().hasExtra("byteArray")) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(
                        getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
                img.setImageBitmap(bitmap);
            }


            Log.e("n",product);
            text.setText(product);
            Log.e("n",product);


            try{ Class.forName("com.mysql.jdbc.Driver");
                Connection  conn= DriverManager.getConnection(DB_URL,USER,PASS);
                if(conn==null){
                    success=false;
                }else{
                    String query="SELECT description,quantity,quantityin,price,quality from ordertable where orderid='"+orderid+"' ";
                    Log.e("n","SELECT description,quantity,quantityin,price,quality from ordertable where orderid='"+orderid+"' ");
                    Statement stmt=conn.createStatement();
                    ResultSet rs=stmt.executeQuery(query);
                    if(rs!=null){
                        while (rs.next()){
                            try{
                                itemsArrayList.add(new ClassList(rs.getString("description"),rs.getString("quantity"),rs.getString("quantityin"),rs.getString("price"),rs.getString("quality")));

                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        msg="Found";
                        success=true;
                    }else{
                        msg="No Data found!";
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

            Toast.makeText(Main8Activity.this,msg+"",Toast.LENGTH_LONG).show();
            if(success==false){

            }else{
                try{
                    myAppAdapter =new MyAppAdapter(itemsArrayList,Main8Activity.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                    }catch(Exception ex){
                }

            } }
        }

    public class MyAppAdapter extends BaseAdapter {
        public class ViewHolder {


            TextView textName,text1,text2,text3,text4;
            }

        public List<ClassList> parkingList;
        public Context context;
        ArrayList<ClassList> arrayList;

        private MyAppAdapter(List<ClassList> apps, Context context) {
            this.parkingList = apps;
            this.context = context;

            arrayList = new ArrayList<ClassList>();
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

        public View getView(final int position, final View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder viewHolder = null;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.text_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textName=(TextView)rowView.findViewById(R.id.textview1);
                viewHolder.text1=(TextView)rowView.findViewById(R.id.textview2);
                viewHolder.text2=(TextView)rowView.findViewById(R.id.textview3);
                viewHolder.text3=(TextView)rowView.findViewById(R.id.textview4);
                viewHolder.text4=(TextView)rowView.findViewById(R.id.textview5);

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();



                }


            ClassList food=parkingList.get(position);
            viewHolder.textName.setText(food.getDescription() + "");
            viewHolder.text1.setText(food.getQuantity());
            viewHolder.text2.setText(food.getQuantityIn());
            viewHolder.text3.setText(food.getPrice());
            viewHolder.text4.setText(food.getQuality());



            return rowView;
            }



    }
}