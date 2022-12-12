/**
 * This is my code! It's goal is to provide a command-line interface.
 * CS 312 - Assignment 9
 * @author Emma Smith
 */

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class CLI
{
    /** String entry, the query that is entered through stdin */
    protected String entry = null;


    /** SearchEngine se, the SearchEngine created */
    protected SearchEngine se;


    /** Boolean running, the flag to make loop continue processing queries */
    protected Boolean running;


    /** long startTimeQ, the time at the start of the processing of the queries */
    protected long startTimeQ;

 
    /** long stopTimeQ, the time at the end of the processing of the queries */
    protected long stopTimeQ;
    

    /**
     * <pre>
     * PURPOSE: use a Buffered Reader to read inputs from stdin;
     * INPUT: none;
     * RESULT: the attribute 'entry' is set to equal the input from the command line;
     * EXPECTED TIME COMPLEXITY: O(1)
     * </pre>
     */
    public void BuffRead()
    {
	try
	{
	    BufferedReader br;
	    br = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("Enter your query: ");
	    this.entry = br.readLine();
	    if(entry == null)
	    {
		this.stopTimeQ = System.currentTimeMillis();
		long elapsedTimeQ = stopTimeQ - startTimeQ;
		System.out.println("@@ processing the queries took " + elapsedTimeQ + "ms");
		System.exit(0);
		br.close();
	    }
	}
	catch(Exception ex)
	{
	    ex.printStackTrace();
	}
    }


    public static void main(String [] args)
    {
	Boolean running = true;
	long startTime1 = System.currentTimeMillis();

	CLI cli = new CLI();
	cli.processCommand(args);

	long stopTime1 = System.currentTimeMillis();
	long elapsedTime1 = stopTime1 - startTime1;
	System.out.println("@@ indexing the collection took " + elapsedTime1 + "ms");

	cli.processQueries(args);
    }


    /**
     * PURPOSE: print an error message and the program's command line options;
     * INPUT: none;
     * RESULT: the instructions are printed to stdout;
     * EXPECTED TIME COMPLEXITY: O(1);
     */
    private void usage()
    {
	System.err.println("\nUsage: java CLI [-d] <StopList file> <Document file(s)> \n"
			   + "Ctrl-d to terminate.");
    }
    

    /**
     * PURPOSE: process the user's command;
     * INPUT: the command arguments;
     * RESULT: create the relevant SearchEngine;
     * EXPECTED TIME COMPLEXITY: O(n^2);
     */
    private void processCommand(String [] args)
    {
	int size = args.length;
	
        if (size == 0)
	{
	    usage();
	    return;
	}
	
	if ("-d".equals(args[0]))
	{
	    se = new SearchEngine(true, args[1]);
	    size -= 2;
	}
	else 
	{
	    se = new SearchEngine(false, args[0]);
	    size -= 1;
	}
	
	for(int i = args.length - size; i < args.length; i++) 
	{
	    se.addDocument(new Document(args[i]));
	}
    }


    /** 
     * PURPOSE: process entered queries from stdin;
     * INPUT: the command arguments;
     * RESULT: the relevant Set<Document> is returned and displayed to stdout;
     * EXPECTED TIME COMPLEXITY: O(n) for each query, O(n^2) for entire method where n is the number of queries;
     */ 
    public void processQueries(String [] args)
    {
	this.startTimeQ = System.currentTimeMillis();

	this.running = true;

	while(running)
	{

	    BuffRead();

	    Set<String> query = new HashSet<String>();

	    String [] subStrings = entry.split(" ");
	
	    Set<Document> answer = null;

	    if (subStrings.length == 1)
	    {

	        if (subStrings[0].equals("@@debug"))
	        {
		    se.displayAll();
    	        }
	        else
	        {
	            answer = se.processSingleWordQuery(se.makeSingleWordClean(subStrings[0]));
	        }
	    }
	    else
	    {
	        for (String subString : subStrings)
	        {

	            query.add(subString);
	        }
	        answer = se.processMultiWordQuery(query);
	    }

	    if (answer != null)
	    {
	        se.display(answer);
	    }
	    System.out.print("\n");

	    processCommand(args);

	}

    }

}
    
