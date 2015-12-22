/*
Emilio Esposito
Creation Date:	10/27/2015
Modified Date:	10/29/2015
Description:	Project 3
				Reads in log and gives stats on file with most authors & revisions
*/


import java.util.Scanner;
import java.util.Iterator;
import java.util.Set;
import java.io.*;
import java.util.ArrayList;
import com.opencsv.CSVWriter;
import java.util.HashMap;
import java.util.Map;

public class P3A4_ESPOSITO_EMILIO {

	public static void main(String[] args) {
		//create scanner used to read keyboard
		Scanner scanKB = new Scanner(System.in);
		String currentLine;	//stores current line string
		String currentFileName="";	//stores current filename
		String authorName;	//stores current authorname
		String dateStr; //date in string format
		ArrayList<Integer> queryIndex = new ArrayList<Integer> ();
		File logFile;	//stores address to log file object

		//use hash map to store author object lookups
		HashMap<String, P3A4_ESPOSITO_EMILIO_Author> authMap = new HashMap<String, P3A4_ESPOSITO_EMILIO_Author>();


		while(true) //this is NOT an infinite loop, it's for try/catch exception handling
		{
			try
			{

				//ask user to enter log file name
				System.out.println("Enter log name to analyze (e.g. \"emacs.log\" without quotes): ");

				logFile = new File(scanKB.nextLine());	//import logfile

				//Open CSVWriter
				CSVWriter write_out_3 = new CSVWriter(new FileWriter("activity3_out.csv"));
				CSVWriter write_out_4 = new CSVWriter(new FileWriter("activity4_out.csv"));

				//create ArrayList to store all file objects
				ArrayList<P3A4_ESPOSITO_EMILIO_File> allFiles = new ArrayList<P3A4_ESPOSITO_EMILIO_File>();

				//create scanner used to read log
				Scanner scanLog = new Scanner(logFile);

				//read each line of the log with a loop
				while(scanLog.hasNext())
				{
					//read in current line
					currentLine = scanLog.nextLine();

					//check if the line indicates a file
					if(currentLine.contains("Working file: "))
					{
						//get the filename
						currentFileName = currentLine.substring(14, currentLine.length());	//cut down string to everything after "RCS file: " and before ","

						//create file object
						allFiles.add(new P3A4_ESPOSITO_EMILIO_File(currentFileName));
					}

					//check if the line indicates an author
					if(currentLine.contains("author: "))
					{

						//Author
						//cut down string to everything after "author: "
						authorName = currentLine.substring(currentLine.indexOf("author: ") + 8, currentLine.length());

						//cut down string to everything before ","
						authorName = authorName.substring(0, authorName.indexOf(";"));

						//Date
						//cut down string to "date: " portion
						dateStr = currentLine.substring(currentLine.indexOf("date: ") + 6, currentLine.indexOf(";")-6);

						//check for Author existence
						if(authMap.containsKey(authorName))
						{
							//record the author revision
							authMap.get(authorName).addRev(currentFileName, dateStr);
						}
						else
						{
							//add relationship to map and create new author
							authMap.put(authorName,new P3A4_ESPOSITO_EMILIO_Author(authorName, currentFileName, dateStr));
						}

						//record the revision
						allFiles.get(allFiles.size()-1).addRev(authorName, dateStr);
					}
				}


//ACTIVTY 1
				System.out.println("ACTIVTY 1");
				//Output Number of files in logging data
				System.out.println("# of files: " + allFiles.size());

				//Find index of file object with the most authors
				int maxAuthorIndex=0;
				for(int i = 1; i < allFiles.size(); i++)
				{
					if(allFiles.get(maxAuthorIndex).getListAuthors().size() < allFiles.get(i).getListAuthors().size())
					{
						maxAuthorIndex = i;
					}
				}
				//check for ties
				for(int i = 1; i < allFiles.size(); i++)
				{
					if(allFiles.get(maxAuthorIndex).getListAuthors().size() == allFiles.get(i).getListAuthors().size())
					{
						queryIndex.add(i);
					}
				}

				//Print Summary of file(s) with most authors
				System.out.println("\n\nFILE WITH THE MOST NUMBER OF USERS (AUTHORS) COMMITTING THINGS TO IT");
				for(int i=0; i < queryIndex.size(); i++)
				{
					System.out.println("Result # " + (i+1) + " of " + queryIndex.size());
					System.out.println("\tFile Name: " + allFiles.get(queryIndex.get(i)).getFileName());
					System.out.println("\tAuthor Count: " + allFiles.get(queryIndex.get(i)).getListAuthors().size());
					System.out.println("\tNumber of Revisions: " + allFiles.get(queryIndex.get(i)).getNumRev());
					System.out.println("");
				}

				//reset query indices
				queryIndex.clear();


				//Find index of file object with the most revisions
				int maxNumRevIndex=0;
				for(int i = 1; i < allFiles.size(); i++)
				{
					if(allFiles.get(maxNumRevIndex).getNumRev() < allFiles.get(i).getNumRev())
					{
						maxNumRevIndex = i;
					}
				}
				//check for ties
				for(int i = 1; i < allFiles.size(); i++)
				{
					if(allFiles.get(maxNumRevIndex).getNumRev() == allFiles.get(i).getNumRev())
					{
						queryIndex.add(i);
					}
				}

				//Print Summary of file with most revisions
				System.out.println("\n\nFILE WITH THE MOST NUMBER OF REVISIONS");
				for(int i=0; i < queryIndex.size(); i++)
				{
					System.out.println("Result # " + (i+1) + " of " + queryIndex.size());
					System.out.println("\tFile Name: " + allFiles.get(queryIndex.get(i)).getFileName());
					System.out.println("\tAuthor Count: " + allFiles.get(queryIndex.get(i)).getListAuthors().size());
					System.out.println("\tNumber of Revisions: " + allFiles.get(queryIndex.get(i)).getNumRev());
					System.out.println("");
				}

				System.out.println("ACTIVTY 2 output suppressed");
//ACTIVITY 2
				/*
				for(int i = 0; i < allFiles.size(); i++)
				{
					System.out.print(allFiles.get(i).getFileName());
					System.out.print("\t"+ allFiles.get(i).getFirstCommit());
					System.out.print("\t"+ allFiles.get(i).getUsersMostCommit());
					System.out.println("\t"+ allFiles.get(i).getNumMaxCommit());

				}

*/

//ACTIVITY 3
//				First column: the name of every file changed
//				Second column: Number of commits
//				Third column: First date of commit
//				Fourth column: Last date of commit

				String[] header3 = {"filename","num_commits","earliest_commit_date","last_commit_date"};
				write_out_3.writeNext(header3);

				//write rows of output to activity3_out.csv
				for(int i = 0; i < allFiles.size(); i++)
				{
					String[] row =
						{
							allFiles.get(i).getFileName(),
							Integer.toString(allFiles.get(i).getNumRev()),
							allFiles.get(i).getFirstCommit(),
							allFiles.get(i).getLastCommit()
						};
					write_out_3.writeNext(row);
				}

				System.out.println("ACTIVTY 3 output is a csv file: \"activity3_out.csv\"");

//ACTIVITY 4
//				First column: Users ID
//				Second column: The number of files the user has interacted with
//				Third column: Total number of commits
//				Fourth column: The users first commit
//				Fifth column: The users last commit

				String[] header4 = {"user","num_files","num_commits","first_commit_date", "last_commit_date"};
				write_out_4.writeNext(header4);

			    Set set = authMap.entrySet();
			    Iterator iterateAuth = set.iterator();
				P3A4_ESPOSITO_EMILIO_Author auth;

				//write rows of output to activity4_out.csv
				while(iterateAuth.hasNext())
				{
					Map.Entry pair = (Map.Entry)iterateAuth.next();
					auth = (P3A4_ESPOSITO_EMILIO_Author) pair.getValue();
					String[] row =
						{
								auth.getAuthorName(),
								Integer.toString(auth.getListFiles().size()),
								Integer.toString(auth.getAuthRev()),
								auth.getFirstCommit(),
								auth.getLastCommit()
						};
					write_out_4.writeNext(row);
				}


				System.out.println("ACTIVTY 4 output is a csv file: \"activity4_out.csv\"");

				//tidy up
				write_out_3.close();
				write_out_4.close();
				scanLog.close();

				//if exception wasn't thrown, break the loop
				break;
			}
			//catch the exception
			catch (FileNotFoundException e)
			{
				System.out.println("Couldn't find that file or the activityX_out.csv file is open... try again.\n");
			}
			catch (IOException e)
			{
				System.out.println("IOException... try again.\n");
			}

		}

		scanKB.close();

	}

}
