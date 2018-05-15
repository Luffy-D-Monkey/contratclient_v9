package DButil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DeleteContact
{
	
	Connection conn = null;
	public ArrayList<String> deleteContact(String turecommand)
	{
		 ArrayList<String> resultList = new ArrayList<String>();
		int changen = 0;
		//command   "13 "+uuuid+" "+conid+...
		String command = turecommand + " =";
		String []ss = command.split(" ");
		int turesize = ss.length-1;
		System.out.println("delete command"+turecommand);
		conn = new MysqlConnect().connect("u"+ss[1]);
		Statement stmt = null;
		try
		{
			stmt = conn.createStatement();
			
			for(int i = 2; i<turesize; i++)
			{
				String desql ="delete from contacts where Cmobile_no = '"+ss[i]+"'";
				changen += stmt.executeUpdate(desql);
			}
			
			System.out.println("changchangchangchangchangd"+changen);
			resultList.add(""+changen);
			ArrayList<String>temp = new ArrayList<String>();
			temp = new GetContacts().getContacts("u"+ss[1]);
			for(int i = 0; i<temp.size(); i++)
				resultList.add(temp.get(i));	
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//n + contacts
		return resultList;
	}
	
}
