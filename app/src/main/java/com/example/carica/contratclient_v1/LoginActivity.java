package com.example.carica.contratclient_v1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity
{



    private TextView show;
    private EditText eTx_user;
    public static final int port = 6000;
    public static final String ip = "192.168.199.192";
    //private TextView iptv;
    private EditText eTx_pwd;
    //private TextView porttv;
    private EditText portet;

    private String uuuid = null;

    private Socket socket = null;
    public static final int UPDATALOG = 1;
    private ArrayList<String> revList = new ArrayList<String>();

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case UPDATALOG:
                    String logg = null;
                    if(!(revList.isEmpty()))
                         logg = revList.get(0);
                    if(logg.equals("noexit"))
                        logg ="用户（"+ eTx_user.getText().toString()+"）不存在";
                    else if(logg.equals("wrong"))
                        logg = "密码错误";
                    else
                    {
                        logg = "登录成功";
                        uuuid  = eTx_user.getText().toString();

                        Intent intent=new Intent(LoginActivity.this,ShowContact.class);
                        intent.putExtra("id",uuuid);
                        startActivity(intent);
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
        setContentView(R.layout.activity_login);
        eTx_user = (EditText)findViewById(R.id.eTx_user);
        eTx_pwd = (EditText)findViewById(R.id.eTx_pwd);
    }
    /**
     * 注册
     */
    public void signUp(View v)
    {
        Intent intent=new Intent(LoginActivity.this,SingupActivity.class);
        startActivity(intent);
    }

    /**
     * 登录
     */
    public void login(View v)
    {
        String loginfo = "";
        if(!(eTx_user.getText().toString().equals("")) &&!(eTx_user.getText().toString().equals(null))&&!(eTx_user.getText().toString().equals("null"))
        && !(eTx_user.getText().toString().equals("")) &&!(eTx_user.getText().toString().equals(null))&&!(eTx_user.getText().toString().equals("null")))
        {
            loginfo = "1"+" "+eTx_user.getText().toString()+" "+eTx_pwd.getText().toString();
            System.out.print(loginfo);
            handleRequest h = new handleRequest(LoginActivity.ip,LoginActivity.port,loginfo,revList,mHandler,UPDATALOG);
            h.start();
        }

    }

    public void help(View v)
    {
        Intent intent=new Intent(LoginActivity.this,HelpActivity.class);
        startActivity(intent);

    }


}
