package com.example.carica.contratclient_v1;

import android.app.Application;

import java.net.Socket;

/**
 * Created by Carica on 2017/12/5.
 */

public class MySocket extends Application
{
    private Socket socket = null;

    public Socket getSocket()
    {
        return this.socket;
    }

    public boolean setSocket(Socket s)
    {
        this.socket = s ;
        return true;
    }

}
