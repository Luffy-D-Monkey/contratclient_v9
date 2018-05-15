package DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryFriendreaqust
{
	Connection conn = null;
	public ArrayList<String> queryFriendreaqust(String turecommand)
	{
		 ArrayList<String> resultList = new ArrayList<String>();
		 resultList.add("queryFriendreaqust");
		String command = turecommand + " =";
		
		int changen = 0;
		
		String []ss = command.split(" ");
		int turesize = ss.length-1;
		System.out.println("delete command"+turecommand);
		conn = new MysqlConnect().connect("adminroot");
		//15 uuuid
	    String sql = "SELECT Requester ,Receiver FROM friend where Receiver = '"+ss[1]+"' OR Requester = '"+ss[1]+"'";
	    Statement stmt = null;
	    try
		{
			stmt = conn.createStatement();
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ResultSet rs;
		try
		{
			System.out.println("delete command 1"+turecommand);

			rs = stmt.executeQuery(sql);
			
			ArrayList<String> temp = new ArrayList<String>();
			
			while(rs.next())
	        {
				temp.add(rs.getString("Requester")+" "+rs.getString("Receiver"));
				
				//  					0								1
	        }
			System.out.println("delete command2"+turecommand);

			ArrayList<String>rrrrr = new ArrayList<String>();
			
			String location = "";
			
			for(int i = 0; i<temp.size()-1; i++)
			{
				System.out.println("size "+temp.size()+"index "+i);
				boolean flag = false;
				String [] st = temp.get(i).split(" ");
				//   0res   1rec
				int k = 0;
				for(k  = i+1; k<temp.size(); k++)
				{
					String [] reques = temp.get(k).split(" ");
					if(st[1].equals(reques[0]) && st[0].equals(reques[1]))
					{
						flag = true;
						break;
					}		
				}
				if(flag)
				{
					location += i+" "+k+" ";
				}
			}
			
			
			for(int i = 0; i<temp.size(); i++)
			{
				String s = ""+i;
				if(!location.contains(s))
					rrrrr.add(temp.get(i));
			
			}
			for(int i = 0; i<rrrrr.size(); i++)
			{
				String [] tt = rrrrr.get(i).split(" ");
				if(tt[0].equals(ss[1]))
				{
					rrrrr.remove(i);
				}
			}
			
			
			for(int i = 0; i<rrrrr.size(); i++)
			{
				String [] tt = rrrrr.get(i).split(" ");
				if(!(tt[0].equals(ss[1])))
				{
					resultList.add(tt[0]);
				}
			}						
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i<resultList.size(); i++)
			System.out.println("query result->"+resultList.get(i));
        return resultList;	
	}
}
