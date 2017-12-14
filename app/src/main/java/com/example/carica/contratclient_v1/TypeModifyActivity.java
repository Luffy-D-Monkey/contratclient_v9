package com.example.carica.contratclient_v1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TypeModifyActivity extends AppCompatActivity {

    String uuuid = "";

    String pretypename = "";

    public static final int LOADED = 1;
    public static final int MODIFY = 2;
    public static final int DELETE = 3;
    String selectTypeno = "";
    private ArrayList<String> revList = new ArrayList<String>();
    private ArrayList<String> modifrrevList = new ArrayList<String>();


    String operationType = "";
    EditText typename,typeinfo;

    boolean loaded = false;
    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                //failed格式错误  typenoHaveExit    typenameHaveExit  successed
                case LOADED:
                    loaded = true;
                    data_list = new ArrayList<String>();
                    data_list.add(" ");
                    for(int i = 0; i<revList.size(); i++)
                    {
                        //tno type remark
                        String []sss = revList.get(i).split(" ");
                        data_list.add(sss[0]+" "+sss[1]);

                    }

                    ssetPinner();
                    break;
                case DELETE:
                    loaded = true;
                    if(!(revList.isEmpty()))
                    {
/*
                        Toast.makeText(getApplicationContext(),"删除中---------",Toast.LENGTH_SHORT).show();
*/

                        //successed/Types    failed
                        if(revList.get(0).equals("failed"))
                            Toast.makeText(getApplicationContext(),"删除失败：不存在",Toast.LENGTH_SHORT).show();
                        else
                        {
                            Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                            data_list = new ArrayList<String>();
                            data_list.add(" ");
                            for(int i = 1; i<revList.size(); i++)
                                data_list.add(revList.get(i));
                            ssetPinner();
                        }

                    }


                    else
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    break;


                case MODIFY:
                    if(!(modifrrevList.isEmpty()))
                    {
                        if(revList.get(0).equals("failed"))
                            Toast.makeText(getApplicationContext(),"修改失敗：請檢查格式",Toast.LENGTH_SHORT).show();
                        else
                        {
                            Toast.makeText(getApplicationContext(),"修改成功成功",Toast.LENGTH_SHORT).show();
                            data_list = new ArrayList<String>();
                            data_list.add(" ");
                            for(int i = 1; i<revList.size(); i++)
                                data_list.add(revList.get(i));
                            ssetPinner();//更新下拉列表
                        }
                    }
                    break;
            }
        }
    };

    private void ssetPinner()
    {

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
    }

    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_modify);
        Intent pintent = getIntent();
        uuuid = pintent.getStringExtra("id");

        typename = (EditText)findViewById(R.id.ed_TYmodifyTypeName);
        typeinfo = (EditText)findViewById(R.id.ed_TYmodifyTypeInfo);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new OnItemSelectedListenerImpl());
        //9请求类别
        String command = "9 " +uuuid;
        handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, command, revList, mHandler, LOADED);
        h.start();


        //数据
        data_list = new ArrayList<String>();
        data_list.add(" ");
        data_list.add("上海");
        data_list.add("广州");
        data_list.add("深圳");

    }

    //下拉框选择事件
    private class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String city = parent.getItemAtPosition(position).toString();
            if(!(city.equals(null)) && !(city.equals(" ")) &&!(city.equals("null")))
            {
                city += " =";
                String []ss = city.split(" ");

                String info = "" ;
                Toast.makeText(getApplicationContext(),"select "+selectTypeno,Toast.LENGTH_SHORT).show();
                for(int i = 0; i<revList.size();i++)
                {
                    String sssrevList = revList.get(i)+" =";
                    String []temp = sssrevList.split(" ");
                    //type remark

                    if(temp[0].equals(ss[0]))
                    {
                        info = revList.get(i);
                        break;
                    }
                }
                String []ssinfo = info.split(" ");
                pretypename =ssinfo[0];
                typename.setText(ssinfo[0]);
                typeinfo.setText(ssinfo[1]);
            }
            Toast.makeText(getApplicationContext(), "选择的是：" + city,
                    Toast.LENGTH_LONG).show();
            operationType = city;

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
            operationType = "";
        }
    }

    public void modifyType(View v)
    {
        if(operationType.equals(" "))
            Toast.makeText(getApplicationContext(), "请选择类别" ,
                    Toast.LENGTH_LONG).show();
        else{
            if(!(selectTypeno.equals(" =")))
            {

               // String command = "12 "+uuuid+" "+ pretypename+" " +typename.getText()+" "+typeinfo.getText();

                //                    0      1              2                 3

                String command = "12 "+uuuid+" "+pretypename+" "+typename.getText()+" "+typeinfo.getText();

                handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, command, revList, mHandler, MODIFY);
                h.start();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"请选择类别",Toast.LENGTH_SHORT).show();
            }

        }
    }



    public void delete(View v)
    {
        if(operationType.equals(" "))
        Toast.makeText(getApplicationContext(), "请选择类别" ,
                Toast.LENGTH_LONG).show();
        else
        {
            String []ss = operationType.split(" ");
            String command = "10 "+uuuid+" "+ss[0];
            loaded = false;
            handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, command, revList, mHandler, DELETE);
            h.start();
        }

    }
}
