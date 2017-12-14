package com.example.carica.contratclient_v1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SingupActivity extends AppCompatActivity
{

    EditText id;
    EditText password;
    EditText name;
    EditText sex;
    EditText bir;
    EditText address;
    EditText telno;
    EditText post;
    EditText qqno;
    EditText email;
    TextView log;
    Button send;

    String sid;
    String spassword;
    String sname;
    String ssex;
    String sbir;
    String saddress;
    String stelno;
    String spost;
    String sqqno;
    String semail;

    String exsql = "";
    private String content = "";
    private StringBuffer sb = null;
    private BufferedReader in = null;
    public static final int CONNENTED = 0;
    public static final int UPDATALOG = 1;
    private String logMsg;
    private Socket socket;
    private BufferedWriter writer;
    private InetSocketAddress isa = null;


    private ArrayList<String> revList = new ArrayList<String>();
    private ArrayList<String> typeList = new  ArrayList<String>();

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case CONNENTED:
                    logMsg += "Server Connented\n";
                    log.setText(logMsg);
                    break;

                case UPDATALOG:
                    String logg = revList.get(0);
                    if(logg.equals("successed"))
                    {
                        logg = sid + "注册成功！";
                        finish();
                    }
                    else if(logg.equals("wrong"))
                        logg = sid+"注册失败：信息要求错误！";
                    else
                        logg = sid+"注册失败：用户已存在！";
                    Toast.makeText(getApplicationContext(),logg,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findviews();
        setonclick();

    }

    private void setonclick()
    {
        send.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

   /*             char num[] = sid.toCharArray();
                int k = 0;
                for ( ; k< num.length; k++) {
                    if (Character.isDigit(num[k]))
                        break;
                }
                if(k !=10)
                    Toast.makeText(getApplicationContext(),"请输入11位手机号",Toast.LENGTH_SHORT).show();
                else
                    {*/
                    getTextInfo();
                    if(sid.equals("") || spassword.equals("") || sname.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"数据不完整",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        exsql = "2" + " " + sid + " " + spassword + " " + sname + " " + ssex + " " + sbir + " " + saddress + " " + stelno + " " + spost
                                + " " + sqqno + " " + semail;
                    }
                    //(String ip, int port,String command, ArrayList list, Handler handler,int code)

                    handleRequest h = new handleRequest(LoginActivity.ip,LoginActivity.port,exsql,revList,mHandler,UPDATALOG);
                    h.start();
                    // final tcpClient tcp = new tcpClient(exsql,revList,mHandler);
                    //tcp.start();

                }


           /* }*/



        });


    }

    public void findviews()
    {
        id = (EditText)findViewById(R.id.edx_id);
        password = (EditText)findViewById(R.id.edx_pwd);
        name        = (EditText)findViewById(R.id.edx_name);
        sex = (EditText)findViewById(R.id.edx_sex);
        bir = (EditText)findViewById(R.id.edx_birthday);
        address = (EditText)findViewById(R.id.edx_address);
        telno = (EditText)findViewById(R.id.edx_homePhone);
        post = (EditText)findViewById(R.id.edx_post);
        qqno = (EditText)findViewById(R.id.edx_qq);
        email = (EditText)findViewById(R.id.edx_email);
        log = (TextView)findViewById(R.id.log);
        send = (Button)findViewById(R.id.send);

    }

    private  void getTextInfo()
    {
        sid = id.getText().toString();
        spassword = password.getText().toString();
        sname = name.getText().toString();
        ssex = sex.getText().toString();
        sbir = bir.getText().toString();
        saddress = address.getText().toString();
        stelno = telno.getText().toString();
        spost = post.getText().toString();
        sqqno = qqno.getText().toString();
        semail = email.getText().toString();
    }




}
