package com.example.carica.contratclient_v1;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchContactsActivity extends AppCompatActivity
{
    boolean firstloadflag = false;

    private ArrayList<String> revList = new ArrayList<String>();
    private ArrayList<String> typeList = new ArrayList<String>();
    public static final int LOADEDTYPE = 3;
    public static final int SEARCHCONTACTS = 4,GETCONTACTS = 5;


    //布局对象
    private ListView listview;
    private Button deleteBt;
    private Button cancelBt;
    private CheckBox selectAllCheckbox;
    private RelativeLayout relativeLayout;
    private boolean isSelecting = false;//是否正在选择
    private List<Fruit> fruitList = new ArrayList<>();
    private SearchContactsActivity.FruitAdapter adapter;
    private List<Integer> deleteList = new ArrayList<>();//存储将要删除的子项们的位置
    boolean selectAll = false;//选择全部

    EditText searchname,searchsex;
    int choose = -1;//第一次长按的位置，让它被选定
    boolean isClosing = false;//关闭选择的一瞬间

    String uuuid = "";
    String typename = "";


    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

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
                case SEARCHCONTACTS:
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

                case GETCONTACTS:
                    if(!(fruitList.isEmpty()))//先清空列表
                        fruitList.clear();
                    if(!(fruitList.isEmpty()))
                        Toast.makeText(getApplicationContext(), "fruitList$$$$$$$$$$$$$$$$$$not null"+revList.get(1), Toast.LENGTH_SHORT).show();

                    if(!(revList.isEmpty()))
                        Toast.makeText(getApplicationContext(), "revList---------------------<<<<<<<<"+revList.get(0)+">>>>>>>>", Toast.LENGTH_LONG).show();

                    initData();
                    adapter = new SearchContactsActivity.FruitAdapter(getApplicationContext(),R.layout.item_layout,fruitList);
                    listview.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "update sccess", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);
        Intent pintent = getIntent();
        uuuid = pintent.getStringExtra("id");
        initView();
        //加载类别
        spinner = (Spinner) findViewById(R.id.spinner_search);
        spinner.setOnItemSelectedListener(new OnItemSelectedListenerImpl());

            String command = "9 " + uuuid;
            handleRequest hh = new handleRequest(LoginActivity.ip, LoginActivity.port, command, typeList, mHandler, LOADEDTYPE);
            hh.start();

        updateContacts();


    }

    public void submitSearch(View v)
    {
        String name = searchname.getText().toString();
        String sex = searchsex.getText().toString();

        //EditText没有输入的时候，getText().toString() return "";
    /*    if(sssss.equals(""))
            Toast.makeText(getApplicationContext(),"sssssssssssssss_is_空",Toast.LENGTH_SHORT).show();
        else if(sssss.equals(" "))
            Toast.makeText(getApplicationContext(),"sssssssssssssss_is_空格",Toast.LENGTH_SHORT).show();
        else if(sssss.equals("null"))
            Toast.makeText(getApplicationContext(),"sssssssssssssss_is_null",Toast.LENGTH_SHORT).show();
/**//*
        String coloumm = "";
             /*  coloumm += rs.getString("Cname")+" "; 0
            coloumm += rs.getString("Csex")+" ";        1
            coloumm += rs.getString("Cbirthday")+" ";   2
            coloumm += rs.getString("Caddress")+" ";    3
            coloumm += rs.getString("Ctel_no")+" ";     4
            coloumm += rs.getString("Cpost")+" ";       5
            coloumm += rs.getString("Cmobile_no")+" ";  6
            coloumm += rs.getInt("CQQ_no")+" ";         7
            coloumm += rs.getString("Cemall")+" ";      8
            coloumm += rs.getString("Type")+" ";	    9*/

        if(!(fruitList.isEmpty()))
            fruitList.clear();
        for (int i = 0; i < revList.size(); i++)
        {
           if(name.equals("") && sex.equals("") && typename.equals(""))//默认
               fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
           else if(name.equals("") && sex.equals("") && !(typename.equals("")))//类型
           {
               String []ss = revList.get(i).split(" ");
               if(ss[9].equals(typename))
                   fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
           }
           else if(name.equals("") && !(sex.equals(""))&& typename.equals(""))//性别
           {
               String []ss = revList.get(i).split(" ");
               if(ss[1].equals(sex))
                   fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
           }
           else if(!(name.equals("")) && sex.equals("") && typename.equals(""))//姓名
           {
               String []ss = revList.get(i).split(" ");
               if(ss[0].equals(name))
                   fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
           }
           else if(name.equals("") && !(sex.equals("")) && !(typename.equals("")))//类型 性别
           {
               String []ss = revList.get(i).split(" ");
               if(ss[9].equals(typename) && ss[1].equals(sex))
                   fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
           }
           else if(!(name.equals("")) && !(sex.equals("")) && typename.equals(""))//姓名 性别
           {
               String []ss = revList.get(i).split(" ");
               if(ss[0].equals(name) && ss[1].equals(sex))
                   fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
           }
           else if(!(name.equals("")) && sex.equals("") && !(typename.equals("")))//姓名 类型
           {
               String []ss = revList.get(i).split(" ");
               if(ss[0].equals(name) && ss[9].equals(typename))
                   fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
           }
           else if(!(name.equals("")) && !(sex.equals("")) && !(typename.equals("")))//姓名 性别 类型
           {
               String []ss = revList.get(i).split(" ");
               if(ss[0].equals(name) && ss[1].equals(sex) && ss[9].equals(typename))
                   fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
           }

        }
        adapter = new SearchContactsActivity.FruitAdapter(getApplicationContext(),R.layout.item_layout,fruitList);
        listview.setAdapter(adapter);
    }
    //初始化
    private void initData()
    {

        for (int i = 0; i < revList.size(); i++)
        {
            fruitList.add(new Fruit(revList.get(i), R.drawable.apple));
        }
    }

    private void updateContacts()
    {

        /*Handler updatehandler = new Handler()
        {
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case UPDATALOG:
                            initData();
                            adapter = new FruitAdapter(getApplicationContext(),R.layout.item_layout,fruitList);
                            listview.setAdapter(adapter);
                        Toast.makeText(getApplicationContext(), "update sccess", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };*/
        //4 是请求联系人命令
        String getContacts = "4 " + uuuid;
        //请求所有联系人
        handleRequest s = new handleRequest(LoginActivity.ip, LoginActivity.port, getContacts, revList, this.mHandler, GETCONTACTS);
        s.start();
    }

    //listView的适配器
    class FruitAdapter extends ArrayAdapter<Fruit>
    {
        private int resourceId;

        public FruitAdapter(Context context, int textViewResourceId,
                            List<Fruit> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Fruit fruit = getItem(position); // 获取当前项的Fruit实例
            View view;
            SearchContactsActivity.ViewHolder viewHolder;
            if (isClosing || convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
                //引入ViewHolder提升ListView的效率
                viewHolder = new SearchContactsActivity.ViewHolder();
                viewHolder.textView = (TextView) view.findViewById(R.id.fruit_name);
                viewHolder.checkBox = (CheckBox) view.findViewById(R.id.item_checkbox);
                view.setTag(viewHolder);
                if (position == fruitList.size()) {
                    isClosing = false;//最后一项加载完成后变为false
                }
            } else {
                view = convertView;
                viewHolder = (SearchContactsActivity.ViewHolder)view.getTag();
            }


            viewHolder.imageView.setImageResource(fruit.getImageId());
            viewHolder.textView .setText(fruit.getName());

            if (isSelecting) {
                viewHolder.checkBox.setVisibility(View.VISIBLE);
            } else {
                viewHolder.checkBox.setVisibility(View.GONE);

            }
            if (selectAll)
                viewHolder.checkBox.setChecked(true);
            if (choose > -1 && position == choose) {
                viewHolder.checkBox.setChecked(true);
                choose = -1;
            }
            return view;
        }
    }

    class ViewHolder
    {
        TextView textView;
        ImageView imageView;
        CheckBox checkBox;
    }

    private void initView()
    {

        searchname = (EditText)findViewById(R.id.ed_SEname);
        searchsex = (EditText)findViewById(R.id.ed_SEsex);

        //获取布局控件对象
        deleteBt = (Button) findViewById(R.id.delete_bt_search);
        cancelBt = (Button) findViewById(R.id.cancel_bt_search);
        selectAllCheckbox = (CheckBox) findViewById(R.id.select_all_checkbox_search);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_seach);
//listView相关逻辑代码：
        listview = (ListView) findViewById(R.id.listview_search);
        adapter = new SearchContactsActivity.FruitAdapter(this, R.layout.item_layout, fruitList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (isSelecting) {
                    CheckBox checkBox = view.findViewById(R.id.item_checkbox);
                    checkBox.setChecked(!checkBox.isChecked());
                    if (checkBox.isChecked() && !deleteList.contains(new Integer(position))) {
                        deleteList.add(new Integer(position));
                    } else if (!checkBox.isChecked() && deleteList.contains(new Integer(position))) {
                        for (int i = 0; i < deleteList.size(); i++) {
                            if (deleteList.get(i).equals(new Integer(position))) {
                                deleteList.remove(i);
                            }
                        }
                    }

                } else {
                    //没有选择删除时的代码逻辑
                }
            }
        });


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (isSelecting) {
                    CheckBox checkBox = view.findViewById(R.id.item_checkbox);
                    checkBox.setChecked(!checkBox.isChecked());
                    if (checkBox.isChecked() && !deleteList.contains(new Integer(position))) {
                        deleteList.add(new Integer(position));
                    } else if (!checkBox.isChecked() && deleteList.contains(new Integer(position))) {
                        for (int i = 0; i < deleteList.size(); i++) {
                            if (deleteList.get(i).equals(new Integer(position))) {
                                deleteList.remove(i);
                            }
                        }
                    }
                } else {
                    choose = position;
                    deleteList.add(new Integer(position));
                    isSelecting = true;
                    adapter.notifyDataSetInvalidated();
                    relativeLayout.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        //按钮：取消选择
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

//按钮：删除选择项
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.reverse(deleteList);
                for (int i = 0; i < deleteList.size(); i++) {
                    Log.d("SearchContactsActivity", "delete: " + deleteList.get(i));
                }
                for (int i = 0; i < deleteList.size(); i++) {
                    fruitList.remove(deleteList.get(i).intValue());
                }
                isClosing = true;
                adapter.notifyDataSetInvalidated();
                relativeLayout.setVisibility(View.GONE);
                isSelecting = false;
                deleteList.clear();
            }
        });

        //checkBox：全选
        selectAllCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    deleteList.clear();
                    for (int i = 0; i < fruitList.size(); i++) {
                        deleteList.add(new Integer(i));
                    }
                    selectAll = true;
                    adapter.notifyDataSetInvalidated();
                } else {
                    deleteList.clear();
                    selectAll = false;
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0&&isSelecting) {
                cancel();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    //点击取消或返回键的处理逻辑
    private void cancel(){
        selectAll = false;
        selectAllCheckbox.setChecked(false);
        relativeLayout.setVisibility(View.GONE);
        deleteList.clear();
        isClosing = true;
        adapter.notifyDataSetInvalidated();
        isSelecting = false;
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
                //tno type
                String info = ss[0];
                typename = info;
                Toast.makeText(getApplicationContext(),"select "+typename,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
            typename = "";
        }
    }

}
