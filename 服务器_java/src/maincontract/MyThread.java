package maincontract;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyThread extends Thread
{
	 Socket ssocket;
	 private BufferedReader br;  
	 private PrintWriter pw;  
	 public  String msg; 
	 public MyThread(Socket s)
	 {
		 ssocket=s;
	 }
	public void run()
	{
		System.out.print("silasila");	
		try{
			
			br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));  
				
		
              while ((msg = br.readLine()) != null)
              {  
                  
                    msg = "¡¾" + ssocket.getInetAddress() + "¡¿Ëµ£º" + msg;  
                    System.out.print(msg);
                  
              } 
				}catch(Exception ex)
				{
					
				}				
   }

      

} 
 

