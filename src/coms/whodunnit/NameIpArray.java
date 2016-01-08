package coms.whodunnit;

import java.io.Serializable;
import java.util.ArrayList;

public class NameIpArray implements Serializable{

	public int players;
	public ArrayList<String> name;
	public ArrayList<String> ip;
	public ArrayList<ArrayList<String>> alloted;
	public String selectedperson;
	public String selectedroom;
	public String selectedweapon;
	
	public NameIpArray()
	{}
	
	
	public NameIpArray(int x,String k,String l,String m,ArrayList<String> a,ArrayList<String> b,ArrayList<ArrayList<String>> c)
	{
		
		name=new ArrayList<String>();
		ip=new ArrayList<String>();
		alloted=new ArrayList<ArrayList<String>>();
		
		players=x;
		selectedperson=k;
		selectedroom=l;
		selectedweapon=m;
		name.addAll(a);
		ip.addAll(b);
		alloted.addAll(c);
	}
}
