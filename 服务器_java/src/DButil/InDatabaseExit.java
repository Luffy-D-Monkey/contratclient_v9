package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InDatabaseExit
{
	//查询数据库实例存在则返回true，不存在则创建数据库实例并且返回false
     public boolean isDatabaseExit(String database) 
     {
    	 Connection conn = null; 
    	 boolean returnf = false;
    	 try 
    	 {
    		
             Class.forName("com.mysql.jdbc.Driver");
    		 
    	
    	 
    	 
         // 打开链接
         System.out.println("连接数据库...");
         System.out.println(" 实例化Statement对...");
         //连接系统数据库
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
        	 //不存在则创建数据库实例
        	 rs.close();
        	 String createbasesql = "create database "+database;
	         stmt.executeUpdate(createbasesql);
	         System.out.println("inDatabaseexit标记："+createbasesql);
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
