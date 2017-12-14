package com.example.carica.contratclient_v1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserInfomationActivity extends AppCompatActivity {


    private final static int SUBMIT = 1;
    private final static int GETINFO = 2;

    private ArrayList<String> revList = new ArrayList<String>();
    private ArrayList<String> previousInfo = new ArrayList<String>();
    String uuuid = "";
    boolean getUserInfo = false;

    EditText id;

    EditText name;
    EditText sex;
    EditText bir;
    EditText address;
    EditText telno;
    EditText post;
    EditText qqno;
    EditText email;

    boolean flag_editable = false;
    Button bt_submit;
    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUBMIT:
                    String logg = null;
                    if (!(revList.isEmpty())) {
                        String [] ss = (revList.get(0)).split(" ");
                        logg = ss[0];
                    }
                    if (logg.equals("successed"))
                    {
                        logg = "修改成功";
                        //更新本页面的用户信息
//"successed "+upname+" "+upsex+" "+upbir+" "+upaddress+" "+uptelno+" "+uppost+" "+upqqno+" "+upemail;

                    }
                    else if (logg.equals("failed"))
                        logg = "修改失败";
                    Toast.makeText(getApplicationContext(),logg,Toast.LENGTH_SHORT).show();
                    break;

                case GETINFO:
                    String loggg = null;
                    if (!(revList.isEmpty()))

                    /*    result = "successed ";
                    result += uuuserid+" ";
                    result += rs.getString("Uname")+" ";

                    result += rs.getString("Usex")+" ";
                    result += rs.getString("Ubirthday")+" ";
                    result += rs.getString("Uaddress")+" ";
                    result += rs.getString("Utel_no")+" ";
                    result += rs.getString("Upost")+" ";

                    result += rs.getInt("UQQ_no")+" ";
                    result += rs.getString("Uemall");*/
                        loggg = (revList.get(0).split(" "))[0];
                    if (loggg.equals("successed"))
                    {
                        loggg = "获取成功";
                        getUserInfo  = true;
                        setEditTextinfo(revList.get(0));
                    }
                    else if (loggg.equals("failed"))
                        loggg = "获取失败";

                    Toast.makeText(getApplicationContext(),loggg,Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infomation);
        Intent pintent = getIntent();
        uuuid = pintent.getStringExtra("id");
        String query = "5 "+ uuuid;
        handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, query, revList, mHandler, GETINFO);
        h.start();
        initView();
        //setEnEdit();
    }

    public void bt_submitClick(View v)
    {
        //获取到用户信息才处理
        if(getUserInfo)
        {
            String submitCommand = getUpdateUserInfo();
//        String command = "6 "+uuuid+" "+upname+" "+upsex+" "+upbir+" "+upaddress+" "+uptelno+" "+uppost+" "+upqqno+" "+upemail;

            handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, submitCommand, revList, mHandler, SUBMIT);
            h.start();
        }
        else
            Toast.makeText(getApplicationContext(),"提交失败:获取失败引起的(getUserInfo==false)",Toast.LENGTH_SHORT).show();
    }

    private void initView()
    {
        id = (EditText)findViewById(R.id.Edit_uid);
        id.setEnabled(false);
        name        = (EditText)findViewById(R.id.Edit_Uname);
        sex = (EditText)findViewById(R.id.Edit_usex);
        bir = (EditText)findViewById(R.id.Edit_ubird);
        address = (EditText)findViewById(R.id.Edit_uaddress);
        telno = (EditText)findViewById(R.id.Edit_uTel);
        post = (EditText)findViewById(R.id.Edit_upost);
        qqno = (EditText)findViewById(R.id.Edit_uqq);
        email = (EditText)findViewById(R.id.Edit_uemial);
        bt_submit = (Button)findViewById(R.id.bt_usubmit);
        setUnEdit();

        /*

         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edituserinfo, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.EnUserEditable:

                Toast.makeText(this, "Enable Edit", Toast.LENGTH_SHORT).show();
                if(flag_editable)
                    setUnEdit();
                else
                    setEnEdit();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void setUnEdit()
    {
        flag_editable = false;

        name.setEnabled(false);
        sex.setEnabled(false);
        bir.setEnabled(false);
        address.setEnabled(false);
        telno.setEnabled(false);
        post.setEnabled(false);
        qqno.setEnabled(false);
        email.setEnabled(false);
        bt_submit.setVisibility(View.INVISIBLE);
    }

    private  void setEnEdit()
    {
        flag_editable = true;

        name.setEnabled(true);
        sex.setEnabled(true);
        bir.setEnabled(true);
        address.setEnabled(true);
        telno.setEnabled(true);
        post.setEnabled(true);
        qqno.setEnabled(true);
        email.setEnabled(true);

        bt_submit.setVisibility(View.VISIBLE);
    }

    //根据文本框返回修改用户的命令（6 name sex bir ...）
    private String getUpdateUserInfo()
    {
        String upname = name.getText().toString();

        String upsex = sex.getText().toString();
        String upbir =  bir.getText().toString();
        String upaddress = address.getText().toString();
        String uptelno = telno.getText().toString();
        String  uppost = post.getText().toString();
        String upqqno = qqno.getText().toString();
        String  upemail = email.getText().toString();

        String command = "6 "+uuuid+" "+upname+" "+upsex+" "+upbir+" "+upaddress+" "+uptelno+" "+uppost+" "+upqqno+" "+upemail;
        return command;
    }

    private void setEditTextinfo(String info)
    {
        String s = info+" =";
        String []ss = s.split(" ");
    /*    result = "successed ";
        result += uuuserid+" ";
        result += rs.getString("Uname")+" ";  2

        result += rs.getString("Usex")+" ";
        result += rs.getString("Ubirthday")+" ";
        result += rs.getString("Uaddress")+" ";
        result += rs.getString("Utel_no")+" ";
        result += rs.getString("Upost")+" ";

        result += rs.getInt("UQQ_no")+" ";
        result += rs.getString("Uemall");*/

        id.setText(uuuid);
        name.setText(ss[2]);
        sex.setText(ss[3]);
        bir.setText(ss[4]);
        address.setText(ss[5]);
        telno.setText(ss[6]);
        post.setText(ss[7]);
        qqno.setText(ss[8]);
        email.setText(ss[9]);
    }

}
