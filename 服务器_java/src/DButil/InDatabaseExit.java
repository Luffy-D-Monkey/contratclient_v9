package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InDatabaseExit
{
	//��ѯ���ݿ�ʵ�������򷵻�true���������򴴽����ݿ�ʵ�����ҷ���false
     public boolean isDatabaseExit(String database) 
     {
    	 Connection conn = null; 
    	 boolean returnf = false;
    	 try 
    	 {
    		
             Class.forName("com.mysql.jdbc.Driver");
    		 
    	
    	 
    	 
         // ������
         System.out.println("�������ݿ�...");
         System.out.println(" ʵ����Statement��...");
         //����ϵͳ���ݿ�
         conn = new MysqlConnect().connect("information_schema");
         Statement stmt = conn.createStatement();
         
         String sql = "select * from SCHEMATA where  SCHEMA_NAME='"+database+"'";
         ResultSet rs = stmt.executeQuery(sql);
    	 
         if(rs.next())
         {
        	 	rs.close();
	           
	            stmt.close();
	            conn.close();
	            returnf =  true;
         }
         else 
         {
        	 //�������򴴽����ݿ�ʵ��
        	 rs.close();
        	 String createbasesql = "create database "+database;
	         stmt.executeUpdate(createbasesql);
	         System.out.println("inDatabaseexit��ǣ�"+createbasesql);
             stmt.close();
             conn.close();
             returnf =  false;
         }
    	 
    	 }catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 } 
         	return returnf;
        
     }
     
     
}
