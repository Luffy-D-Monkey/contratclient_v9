package com.example.carica.contratclient_v1;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateContactsActivity extends AppCompatActivity
{

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
    String styyyy = "";

    String uuuid;
    String preinfo ;
    private Spinner spinner;
    boolean flag_editable = false;


    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    public static final int LOADEDTYPE = 3;
    public static final int  UPDATECONTACT = 4;
    private ArrayList<String> revList = new ArrayList<String>();
    private ArrayList<String> typeList = new ArrayList<String>();
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
                        //Toast.makeText(getApplicationContext(),typeList.get(0),Toast.LENGTH_SHORT).show();
                        data_list.add(typeList.get(i));
                    }
                    //();
                    ssetPinner();
                    break;
                case UPDATECONTACT:
                    String logg = null;
                    if (!(revList.isEmpty()))
                    {
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
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contacts);
        Intent pintent = getIntent();
        uuuid = pintent.getStringExtra("id");
        preinfo =  pintent.getStringExtra("contact");
        Toast.makeText(getApplicationContext(),preinfo,Toast.LENGTH_SHORT).show();
        findviews();
        setTexvlue();
        spinner = (Spinner) findViewById(R.id.spinner_UC);
        spinner.setOnItemSelectedListener(new UpdateContactsActivity.OnItemSelectedListenerImpl());
        String command = "9 " +uuuid;
        handleRequest hh = new handleRequest(LoginActivity.ip, LoginActivity.port, command, typeList, mHandler, LOADEDTYPE);
        hh.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editcontact, menu);
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


public void submit(View v)
{
    getTextInfo();
    String ucommand = "14 ";
    ucommand += uuuid+" "+smoblie+" "+sname+" "+ssex+" "+sbir+" "+saddress+" "+stelno+" "+spost+" "+sqqno+" "+semail+" "+styyyy;
    handleRequest hh = new handleRequest(LoginActivity.ip, LoginActivity.port, ucommand, revList, mHandler, UPDATECONTACT);
    hh.start();

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
        send.setVisibility(View.INVISIBLE);
        moblie.setEnabled(false);
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
        moblie.setEnabled(true);
        send.setVisibility(View.VISIBLE);
    }




    private void ssetPinner()
    {
        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
       // spinner.setSelection();
    }






    //下拉框选择事件
    private class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String city = parent.getItemAtPosition(position).toString();
            if (!(city.equals(null)) && !(city.equals(" ")) && !(city.equals("null"))) {
                String[] ss = city.split(" ");
                //type mark
                String info = ss[0];

                styyyy = info;
                Toast.makeText(getApplicationContext(),"select "+styyyy,Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
            styyyy = "undefined";
        }
    }

    public void setTexvlue()
    {

        //name phone
        String s = preinfo+" =";
   /*   coloumm += rs.getString("Cname")+" ";           0
            coloumm += rs.getString("Csex")+" ";        1
            coloumm += rs.getString("Cbirthday")+" ";   2
            coloumm += rs.getString("Caddress")+" ";    3
            coloumm += rs.getString("Ctel_no")+" ";     4
            coloumm += rs.getString("Cpost")+" ";       5
            coloumm += rs.getString("Cmobile_no")+" ";  6
            coloumm += rs.getInt("CQQ_no")+" ";         7
            coloumm += rs.getString("Cemall")+" ";      8
            coloumm += rs.getString("Type")+" ";      9*/
        String []ss = s.split(" ");
        sname = ss[0];
        ssex = ss[1];
        smoblie = ss[6];
        sbir = ss[2];
        saddress = ss[3];
        stelno = ss[4];
        spost =ss[5];

        sqqno = ss[7];
        semail = ss[8];
        styyyy = ss[9];

        name.setText(sname);
        sex.setText(ssex);
        bir.setText(sbir);
        address.setText(saddress);
        telno.setText(stelno);
        post.setText(spost);
        moblie.setText(smoblie);
        qqno.setText(sqqno);
        email.setText(semail);
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
        moblie = (EditText)findViewById(R.id.ed_UCmoblienumber);
        name= (EditText)findViewById(R.id.ed_UCname);
        sex= (EditText)findViewById(R.id.ed_UCsex);
        bir= (EditText)findViewById(R.id.ed_UCbir);
        address= (EditText)findViewById(R.id.ed_UCaddress);
        telno= (EditText)findViewById(R.id.ed_UCtel);
        post= (EditText)findViewById(R.id.ed_UCpost);
        qqno= (EditText)findViewById(R.id.ed_UCqq);
        email= (EditText)findViewById(R.id.ed_UCemail);
        //type= (EditText)findViewById(R.id.ed_ADtype);

        send= (Button)findViewById(R.id.bt_UCsend);
        setUnEdit();

    }
}
