
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class P3A4_ESPOSITO_EMILIO_Author
{

	private String authorName;
	private int authRev;
	private ArrayList<String> listFiles =  new ArrayList<String>();

	//Create a map that stores numeric index (sortable) with respective string date
	private HashMap<Double, String> dateMap = new HashMap<Double, String>();


	public P3A4_ESPOSITO_EMILIO_Author(String an, String fn, String dt)
	{
		authorName = new String(an);
		authRev=0; //revisions are incremented when file is recorded
		addRev(fn, dt);
	}

	public String getAuthorName()
	{
		return authorName.toString();
	}

	public void addRev(String fn, String dateStr)
	{
		//if it's a new file, record the filename
		if(!listFiles.contains(fn))
		{
			listFiles.add(fn);
		}
		double dateNum = Double.parseDouble(dateStr.replaceAll("[\\D]", ""));
		dateMap.put(dateNum, dateStr);
		authRev++;
	}

	public ArrayList<String> getListFiles()
	{
		return listFiles;
	}

	public int getAuthRev() {
		return authRev;
	}

	public String getFirstCommit()
	{
		//create sorted tree map
		TreeMap<Double, String> dateMapSorted = new TreeMap<Double, String>(dateMap);
		return dateMapSorted.firstEntry().getValue().toString();
	}
	public String getLastCommit()
	{
		TreeMap<Double, String> dateMapSorted = new TreeMap<Double, String>(dateMap);
		return dateMapSorted.lastEntry().getValue().toString();
	}
}