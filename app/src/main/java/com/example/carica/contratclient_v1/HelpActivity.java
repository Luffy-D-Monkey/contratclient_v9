package com.example.carica.contratclient_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView view = (TextView)findViewById(R.id.tx_help);
        view.setText("1.\t用户注册\n" +
                "您需要11位手机号码作为登录账号，密码最多为8位，必填字段为账号，密码与姓名。\n" +
                "2.\t联系人类别管理\n" +
                "1）\t新增\n" +
                "联系人类别编号为自动排序，类别名称至多20个字母，可添加适当的备注\n" +
                "2）\t修改\n" +
                "通过联系人类别名称进行修改，可修改其名称，备注，对应联系人的类别也会被修改\n" +
                "3）\t删除\n" +
                "通过联系人类别名称进行删除，该类型的联系人类别将被置空\n" +
                "3.\t联系人管理\n" +
                "1）\t新增\n" +
                "您可以新增联系人，联系人信息有编号，类别，名字，性别，生日，家庭地址，家庭电话，邮政编码，手机号码，QQ号码，电子邮箱，必填字段为名字，手机号码编号为自动编号。\n" +
                "2）\t查询\n" +
                "可以通过联系人编号查询，姓名查询，类别查询，如果没有输入任何条件则显示全部联系人信息。\n" +
                "3）\t修改\n" +
                "通过联系人编号查找到联系人，进行信息的修改。联系人编号可由查询板块获得。\n" +
                "4）\t删除\n" +
                "通过联系人编号删除联系人信息。\n" +
                "4.\t添加好友\n" +
                "您可以通过账户查找使用该系统的其他用户互相添加好友，并将对方信息录入联系人表中。\n");
    }
}
