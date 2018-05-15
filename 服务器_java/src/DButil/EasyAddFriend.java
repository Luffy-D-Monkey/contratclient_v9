package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EasyAddFriend
{

	Connection conn = null;
	public String easyAddFriend(String turecommand)
	{
		 String result = "failed";
         //String turecommand = "17 "+uuuid+" "+res;
		String []ss = turecommand.split(" ");
		conn = new MysqlConnect().connect("adminroot");
		String id1 = ss[1], id2 = ss[2];	
		String lname1,lsex1,lbir1,laddress1,ltel1,lpost1,lqq1,lemail1,Lphone1;
		String lname2,lsex2,lbir2,laddress2,ltel2,lpost2,lqq2,lemail2,Lphone2;	
		Statement stmt1 = null;
		 String sql1 = "SELECT ID,"
		 		+ "Uname,\r\n" + 
			 		"Usex,\r\n" + 
			 		"Ubirthday,\r\n" + 
			 		"Uaddress ,\r\n" + 
			 		"Utel_no ,\r\n" + 
			 		"Upost  ,\r\n" + 
			 		"UQQ_no,\r\n" + 
			 		"Uemall \r\n" + 
			 		"from user where id = '"+id1+"'"; 
	 
		 String sql2 = "SELECT Uname,ID,"
		 		+ "\r\n" + 
			 		"Usex,\r\n" + 
			 		"Ubirthday,\r\n" + 
			 		"Uaddress ,\r\n" + 
			 		"Utel_no ,\r\n" + 
			 		"Upost  ,\r\n" + 
			 		"UQQ_no,\r\n" + 
			 		"Uemall \r\n" + 
			 		"from user where id = '"+id2+"'"; 
	
	         ResultSet rs1,rs2;

	        try
			{
	        	stmt1 = conn.createStatement();		
	        	 rs1 = stmt1.executeQuery(sql1);
				if(rs1.next())
				 {
					 //System.out.println("getUserInfo>>>>>>>>ResultSet<<<<<<<");
					 lname1 = "'"+rs1.getString("Uname")+"'";
					 System.out.println("好友姓名1-》"+lname1);
					 Lphone1 =  "'"+rs1.getString("ID")+"'";
					 System.out.println("好友id1-》"+Lphone1);
					 lsex1= rs1.getString("Usex")+"";
					 lbir1= rs1.getString("Ubirthday")+"";
					 laddress1 = rs1.getString("Uaddress")+"";
					 ltel1 = rs1.getString("Utel_no")+"";
					 lpost1= rs1.getString("Upost")+"";			 
					 lqq1= rs1.getString("UQQ_no")+"";
					 lemail1= rs1.getString("Uemall");
					 System.out.println("好友email-》"+lemail1);
					 if(lsex1.equals("null") || lsex1.equals("") ||lsex1.equals(null) )
						 lsex1 = "null";
						else
							lsex1 = "'"+lsex1+"'";			
					 if(laddress1.equals("null") || laddress1.equals("") ||laddress1.equals(null) )
						 laddress1 = "null";
						else
							laddress1 = "'"+laddress1+"'";
					 
					 if(ltel1.equals("null") || ltel1.equals("") ||ltel1.equals(null) )
						 	ltel1 = "null";
						else
							ltel1 = "'"+ltel1+"'";
					 if(lpost1.equals("null") || lpost1.equals("") ||lpost1.equals(null) )
						 lpost1 = "null";
						else
							lpost1 = "'"+lpost1+"'";
					 if(lqq1.equals("null") || lqq1.equals("") ||lqq1.equals(null) )
						 lqq1 = "null";
						else
							lqq1 = "'"+lqq1+"'";
					 System.out.println("好友id88-》"+Lphone1);
					
					 if(lemail1 == null)
						 lemail1 = "null";
					 else if(lemail1.equals("null") || lemail1.equals("") ||lemail1.equals(null) )
						 lemail1 = "null";
						else
							lemail1 = "'"+lemail1+"'";
					 System.out.println("好友id88-》"+Lphone1);
					 if(lbir1.equals("null") || lbir1.equals("") ||lbir1.equals(null) )
							lbir1 = "null";
						else
							lbir1 = "'"+lbir1+"'";

					 rs1.close();
					 
					 Connection conn2 = new MysqlConnect().connect("u"+id2);
					 //插入联系人
					 Statement insertstmt1 = null;
				   
					 insertstmt1 = conn2.createStatement();
						String insertsql1 = "INSERT INTO CONTACTS SET"
								+ " Cmobile_no = "+Lphone1
								+",Cname  = "+lname1
								+",Csex  = "+lsex1;
								
								insertsql1 += 
								",Cbirthday  = "+lbir1
								+",Caddress  = "+laddress1
								+",Ctel_no = "+ltel1
								+",Cpost = "+lpost1
								+",CQQ_no = "+lqq1
								+",Cemall = "+lemail1
								+",Type = null";
								System.out.println("求供求2"+insertsql1);
						insertstmt1.executeUpdate(insertsql1);
						
						insertstmt1.close();
						 conn2.close();
						 rs2 = stmt1.executeQuery(sql2);
							if(rs2.next())
							 {
								 //System.out.println("getUserInfo>>>>>>>>ResultSet<<<<<<<");
								 lname1 = "'"+rs2.getString("Uname")+"'";
								 Lphone1 = "'"+rs2.getString("ID")+"'";
								 System.out.println("好友姓名2-》"+lname1);
						 System.out.println("好友id2-》"+Lphone1);
								 
								 lsex1= rs2.getString("Usex")+"";
								 lbir1= rs2.getString("Ubirthday")+"";
								 laddress1 = rs2.getString("Uaddress")+"";
								 ltel1 = rs2.getString("Utel_no")+"";
								 lpost1= rs2.getString("Upost")+"";
								 
								 lqq1= rs2.getString("UQQ_no")+"";
								 lemail1= rs2.getString("Uemall");
								 rs2.close();
								 if(lsex1.equals("null") || lsex1.equals("") ||lsex1.equals(null) )
									 lsex1 = "null";
									else
										lsex1 = "'"+lsex1+"'";
								 
								 if(laddress1.equals("null") || laddress1.equals("") ||laddress1.equals(null) )
									 laddress1 = "null";
									else
										laddress1 = "'"+laddress1+"'";
								 
								 if(ltel1.equals("null") || ltel1.equals("") ||ltel1.equals(null) )
									 	ltel1 = "null";
									else
										ltel1 = "'"+ltel1+"'";
								 if(lpost1.equals("null") || lpost1.equals("") ||lpost1.equals(null) )
									 lpost1 = "null";
									else
										lpost1 = "'"+lpost1+"'";
								 if(lqq1.equals("null") || lqq1.equals("") ||lqq1.equals(null) )
									 lqq1 = "null";
									else
										lqq1 = "'"+lqq1+"'";
								 if(lemail1 == null)
									 lemail1 = "null";
								 else if(lemail1.equals("null") || lemail1.equals("") ||lemail1.equals(null) )
									 lemail1 = "null";
									else
										lemail1 = "'"+lemail1+"'";
								 if(lbir1.equals("null") || lbir1.equals("") ||lbir1.equals(null) )
										lbir1 = "null";
									else
										lbir1 = "'"+lbir1+"'";
							 Connection conn3 = new MysqlConnect().connect("u"+id1);
								 //插入联系人
								 Statement insertstmt2 = conn3.createStatement();
									String insertsql2 = "INSERT INTO CONTACTS SET"
											+ " Cmobile_no = "+Lphone1
											+",Cname  = "+lname1
											+",Csex  = "+lsex1;
											
											insertsql1 += 
											",Cbirthday  = "+lbir1
											+",Caddress  = "+laddress1
											+",Ctel_no = "+ltel1
											+",Cpost = "+lpost1
											+",CQQ_no = "+lqq1
											+",Cemall = "+lemail1
											+",Type = null";											
											
										System.out.println("求供求1"+insertsql2);
									insertstmt2.executeUpdate(insertsql2);
									insertstmt2.close();
									conn3.close();	
							
									String desql ="delete from friend where Requester = '"+id1+"' and Receiver = '"+id2+"'";
									String desq2 ="delete from friend where Requester = '"+id2+"' and Receiver = '"+id1+"'";

									stmt1.executeUpdate(desql);
									stmt1.executeUpdate(desq2);
							 stmt1.close();
									 conn.close();
									 return "successed";
							 }		 
					
					 }
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        return "failed";
	}
}
