package com.example.json;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> name;
    ArrayList<String> email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(JsonDataFromAssect("users.json"));
            JSONArray jsonArray=jsonObject.getJSONArray("users");

                for(int i=0;i< jsonArray.length();i++){
                    JSONObject userData=jsonArray.getJSONObject(i);
                    name.add(userData.getString("name"));
                    email.add(userData.getString("email"));
                }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Helper helper = new Helper(name,email,MainActivity.this);
        recyclerView.setAdapter(helper);
    }

    private String JsonDataFromAssect(String fileName) {
        String json = null;
        try {
            InputStream inputStream=getAssets().open(fileName);
            int sizeOfFile=inputStream.available();
            byte[] bufferData= new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json=new String(bufferData,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;

    }
}

