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

public class UpdatePasswordActivity extends AppCompatActivity {

    String uuuuid;


    public static final int UPDATALOG = 1;
    private ArrayList<String> revList = new ArrayList<String>();

    EditText oldp ,newp,newpagain;

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                //wrong密码错误 failed格式错误  successed成功
                case UPDATALOG:
                    String logg = null;
                    if(!(revList.isEmpty()))
                        logg = revList.get(0);
                    if(logg.equals("failed"))
                        logg ="密码格式错误，请查看帮助";
                    else if(logg.equals("wrong"))
                        logg = "密码错误";
                    else
                    {
                        logg = "密码修改成功";
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
        setContentView(R.layout.activity_update_password);
        Intent pintent = getIntent();
        uuuuid = pintent.getStringExtra("id");
        oldp = (EditText)findViewById(R.id.ed_oldPass) ;
        newp = (EditText)findViewById(R.id.ed_newPass);
        newpagain = (EditText)findViewById(R.id.ed_confinNewPass);

    }

    public void updatePassWord(View v)
    {
        String op,np,npa;
        op = oldp.getText().toString();
        np = newp.getText().toString();
        npa = newpagain.getText().toString();
        if(np.equals(npa))
        {
            String command = "7 " + uuuuid + " " + op + " " + np;
            handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, command, revList, mHandler, UPDATALOG);
            h.start();
        }
        else
            Toast.makeText(getApplicationContext(),"两次密码不一样",Toast.LENGTH_SHORT).show();
    }
}
