package com.example.carica.contratclient_v1;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Carica on 2017/12/6.
 */

public class handleRequest extends Thread
{
    private BufferedWriter writer;
    private InetSocketAddress isa = null;
    Socket socket = null;
    String commandString;
    ArrayList<String> returnlist = new ArrayList<String>();
    public boolean flag_finish = false;

    private String ip;
    private int PORT = 6000;

    public boolean isFinished(){
        return flag_finish;
    }
    Handler thandler = new Handler();
    int  resucode ;
    public handleRequest()
    {
        commandString = "ls";
    }
    //ip,port,命令，保存结果的list，更新ui的handler，处理完毕的代码
    public handleRequest(String ip, int port,String command, ArrayList list, Handler handler,int code)
    {
        commandString = command;
        if(!(list.isEmpty()))
            list.clear();
        returnlist =list;

        thandler = handler;
        this.ip = ip;
        this.PORT = port;
        resucode = code;
    }

    public void run()
    {
        String recv = "";
        try
        {
            Socket ConSocket = new Socket();
            //创建套接字地址，其中 IP 地址为通配符地址，端口号为指定值。
            //有效端口值介于 0 和 65535 之间。端口号 zero 允许系统在 bind 操作中挑选暂时的端口。
           try{
               isa = new InetSocketAddress(ip, PORT);
           }catch (Exception e)
           {
               e.printStackTrace();
           }

            //建立一个远程链接
            ConSocket.connect(isa);
            //socket.connect(isa);
            socket=ConSocket;
            //向服务器发送命令
            writer = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream(),"UTF-8"));

            Log.i("msg", commandString.replace("\n", "")+"\n");

            writer.write(commandString.replace("\n", "")+"\n");
            writer.flush();
            //等待，接收来自服务器返回的消息
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(),"UTF-8"));
            String line;
            String txt = "";

            while ((line = reader.readLine()) != null)
            {
                returnlist.add(line);
            }
            flag_finish = true;


            Message msg = new Message();
            msg.what = resucode;
            thandler.sendMessage(msg);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

