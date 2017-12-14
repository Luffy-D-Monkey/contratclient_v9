package com.example.carica.contratclient_v1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NewContactsTypeActivity extends AppCompatActivity {

    String uuuid = "";


    public static final int UPDATALOG = 1;
    private ArrayList<String> revList = new ArrayList<String>();

    EditText typeno ,typename,typeinfo;

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                //failed格式错误  typenoHaveExit    typenameHaveExit  successed
                case UPDATALOG:
                    String logg = null;
                    if(!(revList.isEmpty()))
                        logg = revList.get(0);
                    if(logg.equals("failed"))
                        logg ="格式错误";
                    else if(logg.equals("typenoHaveExit"))
                        logg = "类型编号已经存在";
                    else if(logg.equals("typenameHaveExit"))
                        logg = "类型名已经存在";
                    else
                    {
                        logg = "新增成功";
                    }
                    Toast.makeText(getApplicationContext(),logg,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contacts_type);
        Intent pintent = getIntent();
        uuuid = pintent.getStringExtra("id");


        typename = (EditText)findViewById(R.id.ed_typename);
        typeinfo = (EditText)findViewById(R.id.ed_typeinfo);

    }


    public void newType(View v)
    {
        String tno,tname,tinfo;

        tname = typename.getText().toString();
        tinfo = typeinfo.getText().toString();
        String command = "8 " +uuuid + " " + tname + " " + tinfo;
        handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, command, revList, mHandler, UPDATALOG);
        h.start();
    }
}
