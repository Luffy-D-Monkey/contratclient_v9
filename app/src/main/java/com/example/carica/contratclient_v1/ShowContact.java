package com.example.carica.contratclient_v1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ShowContact extends AppCompatActivity
{


    int location = 0;
    boolean flag_updateListText = false;
    boolean firstloadflag = false;
    String uuuid = null;
    public static final int UPDATALOG = 1;
    public static final int UPDATECONTACTS = 2;
    public static final int LOADEDTYPE = 3;
    public static final int QUERYREAUEST = 5;


    public static final int DELETECONTACT = 4;

    public static final int ADDFRIEND = 6;

    public static final int EAEYADDFRIEND = 7;


    public static final int DROPMYSELF = 8;
    public static final int REJUECT = 9;

    private ArrayList<String> revList = new ArrayList<String>();
    private ArrayList<String> typeList = new  ArrayList<String>();
    private ArrayList<String> requestList = new  ArrayList<String>();
    //private static ArrayList<String> llllllllllsssssttt = new  ArrayList<String>();


    private static final String TAG = "MainActivity";
    //布局对象
    private ListView listview;
    private Button deleteBt;
    private Button cancelBt;
    private CheckBox selectAllCheckbox;
    private RelativeLayout relativeLayout;

    private boolean isSelecting = false;//是否正在选择
    private List<Fruit> fruitList = new ArrayList<>();
    private FruitAdapter adapter;
    private List<Integer> deleteList = new ArrayList<>();//存储将要删除的子项们的位置
    boolean selectAll = false;//选择全部

    int choose = -1;//第一次长按的位置，让它被选定
    boolean isClosing = false;//关闭选择的一瞬间

    //侧滑菜单
    private ListView listView;

    private DrawerLayout drawerLayout;

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATALOG:
                    String logg = null;
                    if (!(revList.isEmpty()))
                        logg = revList.get(0);
                    if (logg.equals("yes"))
                        logg = "yes:用户首次登录，初始化成功";
                    else if (logg.equals("no"))
                        logg = "no:用户非首次登录";
                    else{

                        adapter = new FruitAdapter(getApplicationContext(),R.layout.item_layout,fruitList);

                        //if(llllllllllsssssttt.size() == 0)
                        Toast.makeText(getApplicationContext(),"<magod>-空",Toast.LENGTH_SHORT).show();

                        //for(int i = 0; i<llllllllllsssssttt.size(); i++)
                        //Toast.makeText(getApplicationContext(),"listViewText-<"+i+">-<"+llllllllllsssssttt.get(i)+">",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(), logg, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "现在开始加载联系人", Toast.LENGTH_SHORT).show();

                    updateContacts();


                    //查询好友请求
                    String request = "15 "+uuuid;
                    handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, request, requestList, mHandler, QUERYREAUEST);
                    h.start();



                    break;
                case UPDATECONTACTS://更新联系人列表
                    if(!(fruitList.isEmpty()))//先清空列表
                        fruitList.clear();

                    if(!(revList.isEmpty()))
                        Toast.makeText(getApplicationContext(), "联系人不为为空"+revList.get(0), Toast.LENGTH_LONG).show();

                    initData();

                    ///llllllllllsssssttt = new ArrayList<String>();
                    adapter = new FruitAdapter(getApplicationContext(),R.layout.item_layout,fruitList);

                    //if(llllllllllsssssttt.size() == 0)
                    Toast.makeText(getApplicationContext(),"<magod>-空",Toast.LENGTH_SHORT).show();

                    //for(int i = 0; i<llllllllllsssssttt.size(); i++)
                    //Toast.makeText(getApplicationContext(),"listViewText-<"+i+">-<"+llllllllllsssssttt.get(i)+">",Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(), "update sccess", Toast.LENGTH_SHORT).show();

                    break;
                case LOADEDTYPE:
                    typeList.add("null null");
                    break;

                case DELETECONTACT:
                    //n + contacts
                    if(!(revList.isEmpty()))
                        Toast.makeText(getApplicationContext(), "revList---------------------<<<<<<<<"+revList.get(0)+">>>>>>>>", Toast.LENGTH_LONG).show();
                    if(!(fruitList.isEmpty()))//先清空列表
                        fruitList.clear();
                    int n = Integer.parseInt(revList.get(0));
                    Toast.makeText(getApplicationContext(), "删除"+n+"人", Toast.LENGTH_LONG).show();
                    ArrayList<String> temp = new ArrayList<String>();
                    for(int i = 1; i<revList.size(); i++)
                        temp.add(revList.get(i));
                    revList = temp;
                    initData();

                    ///llllllllllsssssttt = new ArrayList<String>();
                    adapter = new FruitAdapter(getApplicationContext(),R.layout.item_layout,fruitList);

                    //if(llllllllllsssssttt.size() == 0)
                    Toast.makeText(getApplicationContext(),"<magod>-空",Toast.LENGTH_SHORT).show();

                    //for(int i = 0; i<llllllllllsssssttt.size(); i++)
                    //Toast.makeText(getApplicationContext(),"listViewText-<"+i+">-<"+llllllllllsssssttt.get(i)+">",Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(), "update sccess", Toast.LENGTH_SHORT).show();

                    break;

                case QUERYREAUEST:
                    if(requestList.size()>1)
                    {
                        for ( location = 0; location<requestList.size(); location++)
                        {
                            final String  res = requestList.get(location);
                            if(!(requestList.get(location).equals("queryFriendreaqust")))
                                new AlertDialog.Builder(ShowContact.this).setTitle("好友请求").setMessage((res))
                                        .setPositiveButton("确认添加", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which){
                                                dialog.dismiss();
// SysPassDao pass = new SysPassDao(MainActivity.this);这里是自己写的方法，其他的是都需要的
// pass.roleAll();
                                                String request = "17 "+uuuid+" "+res;
                                                handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, request, new ArrayList<String>(), mHandler, EAEYADDFRIEND);
                                                h.start();



                                                //ShowContact.this.finish();
                                            }
                                        })
                                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                dialog.dismiss();

                                                String request = "19 "+uuuid+" "+res;
                                                handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, request, new ArrayList<String>(), mHandler, REJUECT);
                                                h.start();
                                            }
                                        }).show();

                            /*if(!(requestList.get(location).equals("queryFriendreaqust")))
                            new AlertDialog.Builder(ShowContact.this).setTitle("好友请求").setMessage(requestList.get(location))

                                    .setPositiveButton("确认添加", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.dismiss();
// 这里是自己写的方法，其他的是都需要的

                                     //String request = "16 "+uuuid+" "+requestList.get(location);
                                            //handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, request, new ArrayList<String>(), mHandler, NOTDOO);
                                           // h.start();

                                        }
                                    })
                                    .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();*/
                        }
                    }


                    //Toast.makeText(getApplicationContext(), "update sccess", Toast.LENGTH_SHORT).show();
                    break;
                case ADDFRIEND:
                    if(requestList.get(0).equals("successed"))
                        Toast.makeText(getApplicationContext(), "添加中，等待好友同意", Toast.LENGTH_SHORT).show();


                    else
                        Toast.makeText(getApplicationContext(), "添加失败，已经添加或者好友不存在", Toast.LENGTH_SHORT).show();
                    break;


                case EAEYADDFRIEND:
                    if(requestList.get(0).equals("successed"))
                        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();


                    else
                        Toast.makeText(getApplicationContext(), "添加失败，已经添加或者好友不存在", Toast.LENGTH_SHORT).show();

                    break;



                case DROPMYSELF:
                    if(revList.get(0).equals("successed")) {

                        Toast.makeText(getApplicationContext(), "注销成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "注销失败", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        Intent pintent = getIntent();
        uuuid = pintent.getStringExtra("id");
/*


        new AlertDialog.Builder(this).setTitle("好友请求").setMessage("好友1")

                .setPositiveButton("确认添加", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
// SysPassDao pass = new SysPassDao(MainActivity.this);这里是自己写的方法，其他的是都需要的
// pass.roleAll();
                        ShowContact.this.finish();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


*/


        Toast.makeText(getApplicationContext(), uuuid, Toast.LENGTH_SHORT).show();
        String loginfo = "3 " + uuuid;
        initView();

        if (!firstloadflag)
        {
            //检查用户初始化用户
            firstloadflag = true;
            handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, loginfo, revList, mHandler, UPDATALOG);
            h.start();

            //加载类别
            String command = "9 " +uuuid;
            handleRequest hh = new handleRequest(LoginActivity.ip, LoginActivity.port, command, typeList, mHandler, LOADEDTYPE);
            hh.start();

        }


    }


    //单选对话框
    int yourChoice;
    private void showSingleChoiceDialog(){
        if(!(typeList.isEmpty()))
        {
            final String[] items = new String[typeList.size()];

            for(int i = 0; i<items.length; i++)
                items[i] = typeList.get(i);
            final String[] itemss =  new String[typeList.size()+1];
            itemss[0] = "显示所有";
            for(int i = 1; i<itemss.length; i++)
                itemss[i] = items[i-1];
            yourChoice = -1;
            AlertDialog.Builder singleChoiceDialog =
                    new AlertDialog.Builder(ShowContact.this);
            singleChoiceDialog.setTitle("选择类别");
            // 第二个参数是默认选项，此处设置为0
            singleChoiceDialog.setSingleChoiceItems(itemss, 0,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            yourChoice = which;
                        }
                    });
            singleChoiceDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (yourChoice != -1)
                            {
                                String [] ss = itemss[yourChoice].split(" ");
                                groupContacts(ss[0]);
                                Toast.makeText(ShowContact.this,
                                        "你选择了" + itemss[yourChoice],
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            singleChoiceDialog.show();
        }
        else
            Toast.makeText(getApplicationContext(),"类别加载出错",Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_showcontact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String toastMesage = "";
        //查询
        if (item.getItemId() == R.id.item_showqueryContacts)
        {

            Intent intent2=new Intent(ShowContact.this,SearchContactsActivity.class);
            intent2.putExtra("id",uuuid);
            startActivity(intent2);
        }
        //增加联系人
        else if (item.getItemId() == R.id.item_showaddContacts)
        {
            Intent intent=new Intent(ShowContact.this,AddContactsActivity.class);
            intent.putExtra("id",uuuid);
            startActivity(intent);
        }
        //增加朋友
        else if (item.getItemId() == R.id.item_addfriend)
        {
            //String  friendid = "" ;
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowContact.this);

            builder.setTitle("请输入好友号码");	//设置对话框标题
            builder.setIcon(android.R.drawable.btn_star);	//设置对话框标题前的图标
            final EditText edit = new EditText(ShowContact.this);
            builder.setView(edit);

            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                @Override

                public void onClick(DialogInterface dialog, int which)
                {

                    dialog.dismiss();
                    String friendid = edit.getText().toString();
                    String request = "16 "+uuuid+" "+friendid;
                    handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, request, requestList, mHandler, ADDFRIEND);
                    h.start();
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override

                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    //Toast.makeText(ShowContact.this, "你点了取消", Toast.LENGTH_SHORT).show();

                }

            });
            builder.show();

        }
        return true;
    }

    private void initView()
    {
        //获取布局控件对象
        deleteBt = (Button) findViewById(R.id.delete_bt);
        cancelBt = (Button) findViewById(R.id.cancel_bt);
        selectAllCheckbox = (CheckBox) findViewById(R.id.select_all_checkbox);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
//listView相关逻辑代码：
        listview = (ListView) findViewById(R.id.listview);
        flag_updateListText = true;
        //llllllllllsssssttt = new ArrayList<String>();
        adapter = new FruitAdapter(getApplicationContext(),R.layout.item_layout,fruitList);
        //for(int i = 0; i<llllllllllsssssttt.size(); i++)
        //Toast.makeText(getApplicationContext(),"listViewText-<"+i+">-<"+llllllllllsssssttt.get(i)+">",Toast.LENGTH_SHORT).show();

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

                }
                else
                {

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

        //按钮：编辑联系人
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editContact();
            }
        });

//按钮：删除选择项
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Collections.reverse(deleteList);
                for (int i = 0; i < deleteList.size(); i++) {
                    Log.d(TAG, "delete: " + deleteList.get(i));
                }
                String scontact = "13 "+uuuid;
                for (int i = 0; i < deleteList.size(); i++)
                {
                    //获取到要删除的联系人：
                    String ss[] = fruitList.get(deleteList.get(i).intValue()).getName().split(" ");
                    scontact +=" "+ss[2];
                    //Toast.makeText(getApplicationContext(),"刪除的試試："+name,Toast.LENGTH_SHORT).show();

                    //fruitList.remove(deleteList.get(i).intValue());
                }

                for (int i = 0; i < deleteList.size(); i++)
                {
                    //Toast.makeText(getApplicationContext(),"刪除的試試："+name,Toast.LENGTH_SHORT).show();

                    fruitList.remove(deleteList.get(i).intValue());
                }
                //删除联系人   "13 "+uuuid+" "+conid+...
                handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, scontact, revList, mHandler, DELETECONTACT);
                h.start();
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

        //左滑菜单初始化

        listView=(ListView) findViewById(R.id.v4_listview);
        drawerLayout=(DrawerLayout) findViewById(R.id.v4_drawerlayout);

        initScolMenu();
    }

    private void editContact()
    {
        Collections.reverse(deleteList);
        if(deleteList.size() == 0 )
            Toast.makeText(getApplicationContext(),"请选择",Toast.LENGTH_SHORT).show();
        else if(deleteList.size() > 1)
            Toast.makeText(getApplicationContext(),"只能选择一个联系人",Toast.LENGTH_SHORT).show();
        else
        {
            //name 两个空格 phone
            String s = fruitList.get(deleteList.get(0).intValue()).getName();
            String []sss = s.split(" ");
            String preinfo = "";
            for(int i = 0; i<revList.size(); i++)
            {
                String []ss = revList.get(i).split(" ");
                if(sss[2].equals(ss[6]))
                {
                    preinfo =revList.get(i);
                }
            }


            Intent it = new Intent(ShowContact.this,UpdateContactsActivity.class);
            it.putExtra("id",uuuid);
            it.putExtra("contact",preinfo);


            startActivity(it);
        }
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
            ViewHolder viewHolder;
            if (isClosing || convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
                //引入ViewHolder提升ListView的效率
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.fruit_image);
                viewHolder.textView = (TextView) view.findViewById(R.id.fruit_name);
                viewHolder.checkBox = (CheckBox) view.findViewById(R.id.item_checkbox);
                view.setTag(viewHolder);
                if (position == fruitList.size()) {
                    isClosing = false;//最后一项加载完成后变为false
                }
            } else {
                view = convertView;
                viewHolder = (ViewHolder)view.getTag();
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

    //初始化
    private void initData()
    {
        if(!(fruitList.isEmpty()))
            fruitList.clear();
        for (int i = 0; i < revList.size(); i++)
        {
            String []ss = revList.get(i).split(" ");

            //name phone
            fruitList.add(new Fruit(ss[0]+" "+ss[6], R.drawable.apple));
        }
    }


    //分类显示:
    private void groupContacts(String Typename)
    {

        if(!(fruitList.isEmpty()))
            fruitList.clear();

        if(Typename.equals("显示所有"))
        {
            for (int i = 0; i < revList.size(); i++)
            {
                String []ss = revList.get(i).split(" ");

                fruitList.add(new Fruit(ss[0] + "  " + ss[6], R.drawable.apple));
            }
        }
        else
        {
            for (int i = 0; i < revList.size(); i++)
            {
                String []ss = revList.get(i).split(" ");
                Toast.makeText(getApplicationContext(),"分类吸纳时："+Typename,Toast.LENGTH_SHORT).show();
                if(ss[9].equals(Typename)) {
                    String[] sss = revList.get(i).split(" ");

                    fruitList.add(new Fruit(sss[0] + "  " + sss[6], R.drawable.apple));
                }
            }
        }

        //llllllllllsssssttt = new ArrayList<String>();
        adapter = new FruitAdapter(getApplicationContext(),R.layout.item_layout,fruitList);

        //if(llllllllllsssssttt.size() == 0)
        // Toast.makeText(getApplicationContext(),"<magod>-空",Toast.LENGTH_SHORT).show();

        // f/or(int i = 0; i<llllllllllsssssttt.size(); i++)
        // Toast.makeText(getApplicationContext(),"listViewText-<"+i+">-<"+llllllllllsssssttt.get(i)+">",Toast.LENGTH_SHORT).show();

        listview.setAdapter(adapter);

    }
    private void updateContacts()
    {

        //4 是请求联系人命令
        String getContacts = "4 " + uuuid;
        //请求所有联系人
        handleRequest s = new handleRequest(LoginActivity.ip, LoginActivity.port, getContacts, revList, this.mHandler, UPDATECONTACTS);
        s.start();
    }

    //左滑菜单初始化
    private void initScolMenu()
    {
        final List<String> list = new ArrayList<String>();
        list.add("个人中心");
        list.add("修改密码");
        list.add("分类显示");
        list.add("增加类别");
        list.add("类别管理");
        list.add("注销技几");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //左滑菜单选择响应
                //Toast.makeText(getApplicationContext(),"选择的是："+list.get(position),Toast.LENGTH_SHORT).show();
                showDrawerLayout();
                switch (position)
                {
                    case 0:Toast.makeText(getApplicationContext(),"左滑选择的是："+list.get(0),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ShowContact.this,UserInfomationActivity.class);
                        intent.putExtra("id",uuuid);
                        startActivity(intent);
                        break;
                    case 1:Toast.makeText(getApplicationContext(),"左滑选择的是："+list.get(1),Toast.LENGTH_SHORT).show();
                        Intent intent2=new Intent(ShowContact.this,UpdatePasswordActivity.class);
                        intent2.putExtra("id",uuuid);
                        startActivity(intent2);
                        break;
                    //分类显示
                    case 2:Toast.makeText(getApplicationContext(),"左滑选择的是："+list.get(2),Toast.LENGTH_SHORT).show();
                        showSingleChoiceDialog();
                        break;
                    //增加类别
                    case 3:Toast.makeText(getApplicationContext(),"左滑选择的是："+list.get(3),Toast.LENGTH_SHORT).show();
                        Intent intent3=new Intent(ShowContact.this,NewContactsTypeActivity.class);
                        intent3.putExtra("id",uuuid);
                        startActivity(intent3);
                        break;
                    //类别管理
                    case 4:Toast.makeText(getApplicationContext(),"左滑选择的是："+list.get(4),Toast.LENGTH_SHORT).show();
                        Intent intent4=new Intent(ShowContact.this,TypeModifyActivity.class);
                        intent4.putExtra("id",uuuid);
                        startActivity(intent4);
                        break;

                    //注销技几
                    case 5:Toast.makeText(getApplicationContext(),"左滑选择的是："+list.get(5),Toast.LENGTH_SHORT).show();

                        new AlertDialog.Builder(ShowContact.this).setTitle("确认注销自己？")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        dialog.dismiss();
// SysPassDao pass = new SysPassDao(MainActivity.this);这里是自己写的方法，其他的是都需要的
// pass.roleAll();
                                        new AlertDialog.Builder(ShowContact.this).setTitle("真滴注销自己？")
                                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which){
                                                        dialog.dismiss();
// SysPassDao pass = new SysPassDao(MainActivity.this);这里是自己写的方法，其他的是都需要的
// pass.roleAll();
                                                        String request = "18 "+uuuid+" ";
                                                        handleRequest h = new handleRequest(LoginActivity.ip, LoginActivity.port, request, revList, mHandler, DROPMYSELF);
                                                        h.start();


                                                        //ShowContact.this.finish();
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).show();


                                        //ShowContact.this.finish();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        break;


                }
            }
        });
        drawerLayout.openDrawer(Gravity.LEFT);//侧滑打开  不设置则不会默认打开
    }
    private void showDrawerLayout() {
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }






}
