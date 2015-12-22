/*
Emilio Esposito
Creation Date:	10/27/2015
Modified Date:	10/29/2015
Description:	Project 3
				Reads in log and gives stats on file with most authors & revisions
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class P3A4_ESPOSITO_EMILIO_File
{
	private String fileName;
	private ArrayList<String> thisFileAuthors =  new ArrayList<String>();
	private ArrayList<Integer> thisFileRev =  new ArrayList<Integer>();

	//Create a map that stores numeric index (sortable) with respective string date
	private HashMap<Double, String> dateMap = new HashMap<Double, String>();

	public P3A4_ESPOSITO_EMILIO_File(String fn)
	{
		fileName = new String(fn); //clone the temporary filename

	}

	public String getFileName()
	{
		return fileName;
	}

	public int getNumRev()
	{
		return dateMap.size();
	}

	public ArrayList<String> getListAuthors()
	{
		return thisFileAuthors;
	}

	//record a revision
	public void addRev(String an, String dateStr)
	{

		int authIndex;
		//create a numeric version of date for min/max
		double dateNum = Double.parseDouble(dateStr.replaceAll("[\\D]", ""));

		//if author is new to the file
		if (!thisFileAuthors.contains(an))
		{
			thisFileAuthors.add(an);	//add author to list of authors of file
			thisFileRev.add(1);;		//record first revision (index will be aligned with thisFileAuthors)
		}
		//else the author already edited this file
		else
		{
			authIndex = thisFileAuthors.indexOf(an);	//get index of the author in the list
			thisFileRev.set(authIndex, thisFileRev.get(authIndex)+1);	//increment the number of revisions at appropriate index

		}

		//record date
		dateMap.put(dateNum, dateStr);
	}
	//return the string date of earliest commit
	public String getFirstCommit()
	{
		TreeMap<Double, String> dateMapSorted = new TreeMap<Double, String>(dateMap);
		return dateMapSorted.firstEntry().getValue().toString();
	}

	//return the string date of last commit
	public String getLastCommit()
	{
		TreeMap<Double, String> dateMapSorted = new TreeMap<Double, String>(dateMap);
		return dateMapSorted.lastEntry().getValue().toString();
	}

	//return string of users with most commits
	public String getUsersMostCommit()
	{
		int maxRev=getNumMaxCommit();
		String commonUsers="";

		for(int i=0; i<thisFileRev.size(); i++)
		{
			if(maxRev==thisFileRev.get(i))
			{
				commonUsers = thisFileAuthors.get(i)+" "+commonUsers;
			}
		}

		return commonUsers;
	}

	//get the max # commits/rev
	public int getNumMaxCommit()
	{
		int maxRev=0;

		for(int i=0; i<thisFileRev.size(); i++)
		{
			if (i==0)
			{
				maxRev=thisFileRev.get(i);
			}
			else
			{
				maxRev=Math.max(maxRev, thisFileRev.get(i));
			}
		}
		return maxRev;
	}
}