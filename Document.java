/**
 * This is my code! It's goal is to create a Document object and store the file's name and contents
 * CS 312 - Assignment 9
 * @author Emma Smith
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
public class Document implements Iterable<String>
{
    /** String filename, the name of the file */ 
    protected String filename;

    /** String contents, the contents of the file in its original format */
    protected String contents;

    /** Set<String> setContents, the set of individual words in the document */
    protected Set<String> setContents;


    /**
     * PURPOSE: initialize a Document object;
     * INPUT: String pathString, the path to the document;
     * RESULT: the initialized Document;
     * EXPECTED TIME COMPLEXITY: O(n), where n is the number of words in the file;
     */
    public Document(String pathString)
    {
	setContents = new HashSet<String>();
	
	Path p = Paths.get(pathString);
	this.filename = p.getFileName().toString();
	
        try
	{
	    BufferedReader br;
	    br = new BufferedReader(new FileReader(pathString));
	    this.contents = new Scanner(br).useDelimiter("\\A").next();
	    br.close();
	}
	catch (Exception ex)
	{
	    ex.printStackTrace();
	}
    }


    /**
     * PURPOSE: iterate through the contents of the document and 
     * create a set of strings that contains the individual words in the document; 
     * INPUT: none;
     * RESULT: a set of strings is returned;
     * EXPECTED TIME COMPLEXITY: O(n), where n is the number of words in the document;
     */
    public Set<String> createSet()
    {
	Iterator<String> ii = iterator();
	while(ii.hasNext())
	{
	    setContents.add(ii.next());
	}
	return setContents;
    }


    /** 
     * PURPOSE: iterate through the contents of the document;
     * INPUT: none;
     * RESULT: a String iterator is created;
     * EXPECTED TIME COMPLEXITY: O(n), where n is the number of words in the document;
     */
    public Iterator<String> iterator()
    {
	return new Scanner(contents).useDelimiter("[^a-zA-Z]+");
    }


    /**
     * PURPOSE: display the document's file name;
     * INPUT: none;
     * RESULT: the document's file name is written to stdout;
     * EXPECTED TIME COMPLEXITY: O(1);
     */
    public void displayFileName()
    {
	System.out.println(filename);
    }


    /**
     * PURPOSE: display the document's contents;
     * INPUT: none;
     * RESULT: the document's contents in original format written to stdout;
     * EXPECTED TIME COMPLEXITY: O(1);
     */
    public void displayContents()
    {
	System.out.println(contents);
    }

}
