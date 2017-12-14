package com.example.carica.contratclient_v1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddContactsActivity extends AppCompatActivity {

    private ArrayList<String> revList = new ArrayList<String>();
    private ArrayList<String> typeList = new ArrayList<String>();

    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    public static final int LOADEDTYPE = 3;
    public static final int ADDCONTACTS = 4;
    //默认类型名
    String typename = "undefined";


    EditText moblie;
    EditText name;
    EditText sex;
    EditText bir;
    EditText address;
    EditText telno;
    EditText post;
    EditText qqno;
    EditText email;

    Button send;

    String smoblie;
    String sname;
    String ssex;
    String sbir;
    String saddress;
    String stelno;
    String spost;
    String sqqno;
    String semail;
    String stype;



    String uuuid = "";
    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case LOADEDTYPE:
                    Toast.makeText(getApplicationContext(),"加载类型",Toast.LENGTH_SHORT).show();
                    data_list = new ArrayList<String>();
                    data_list.add(" ");
                    for(int i = 0; i<typeList.size(); i++)
                    {
                        data_list.add(typeList.get(i));
                    }
                    ssetPinner();
                    break;
                case ADDCONTACTS:
                    String logg = revList.get(0);
                    //successed成功  failed已经存在  wrong格式错误
                    if(logg.equals("successed"))
                    {
                        logg =  "新增成功";
                        //finish();
                    }
                    else if(logg.equals("wrong"))
                        logg = "失败：信息要求错误！";
                    else
                        logg = "注册失败：联系人已存在！";
                    Toast.makeText(getApplicationContext(),logg,Toast.LENGTH_SHORT).show();

                    if(logg.equals("successed"))
                    {

                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        findviews();
        Intent pintent = getIntent();
        uuuid = pintent.getStringExtra("id");
        //加载类别
        spinner = (Spinner) findViewById(R.id.spinner_addcon);
        spinner.setOnItemSelectedListener(new OnItemSelectedListenerImpl());
        String command = "9 " +uuuid;
        handleRequest hh = new handleRequest(LoginActivity.ip, LoginActivity.port, command, typeList, mHandler, LOADEDTYPE);
        hh.start();
    }

    private void ssetPinner()
    {

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
    }
    //下拉框选择事件
    private class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String city = parent.getItemAtPosition(position).toString();
            if (!(city.equals(null)) && !(city.equals(" ")) && !(city.equals("null"))) {
                String[] ss = city.split(" ");
                //type remark
                String info = ss[0];

                typename = info;
                Toast.makeText(getApplicationContext(),"select "+typename,Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
            typename = "undefined";
        }
    }

   public void submit(View v)
    {
        //
        getTextInfo();
        //11新增联系人
        String command = "11 "+uuuid+" "+smoblie+" "+sname+" "+ssex
                +" "+sbir+" "+saddress+" "+stelno+" "+spost+" "
                +sqqno+" "+semail+" "+typename;
        //String []ss = command.split(" ");
        handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, command, revList, mHandler, ADDCONTACTS);
        h.start();
    }


    private  void getTextInfo()
    {

         smoblie = this.moblie.getText().toString();
         sname = this.name.getText().toString();
         ssex = this.sex.getText().toString();
         sbir = this.bir.getText().toString();
         saddress = this.address.getText().toString();
         stelno = this.telno.getText().toString();
         spost = this.post.getText().toString();
         sqqno = this.qqno.getText().toString();
         semail = this.email.getText().toString();


    }

    public void findviews()
    {
        moblie = (EditText)findViewById(R.id.ed_ADmoblienumber);
         name= (EditText)findViewById(R.id.ed_ADname);
         sex= (EditText)findViewById(R.id.ed_ADsex);
         bir= (EditText)findViewById(R.id.ed_ADbir);
         address= (EditText)findViewById(R.id.ed_ADaddress);
         telno= (EditText)findViewById(R.id.ed_ADtel);
         post= (EditText)findViewById(R.id.ed_ADpost);
         qqno= (EditText)findViewById(R.id.ed_ADqq);
         email= (EditText)findViewById(R.id.ed_ADemail);
         //type= (EditText)findViewById(R.id.ed_ADtype);

         send= (Button)findViewById(R.id.bt_ADsend);

    }
}
