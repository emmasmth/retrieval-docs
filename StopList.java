/**
 * This is my code! It's gial is to create a StopList 'wrapper' class to iterate through the
 * stopwords and add them to a Set
 * CS 312 - Assignment 9
 * @author Emma Smith
 */


import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.FileReader;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
public class StopList implements Iterable<String>
{
    /** Set<String> stopWords, the set of words to be ignored */
    protected Set<String> stopWords;

    /** String filename, the name of the file */
    protected String filename;

    /** String asRead, the contents of the file in original format */
    protected String asRead;


    /** 
     * PURPOSE: initialize the StopList;
     * INPUT: String pathString, the path to the file;
     * RESULT: the initialized StopList object;
     * EXPECTED TIME COMPLEXITY: O(n), where n is the number of words in the file;
     */
    public StopList(String pathString)
    {
	Path p = Paths.get(pathString);
	this.filename = p.getFileName().toString();
	
	try
	{
	    this.asRead = new String(Files.readAllBytes(Paths.get(pathString)));
	    this.stopWords = new HashSet<String>();
	    Iterator<String> ii = iterator();
	    while(ii.hasNext())
	    {
		stopWords.add(ii.next());
	    }
	    
	}
	catch(Exception ex)
	{
	    ex.printStackTrace();
	}
    }    


    /** 
     * PURPOSE: iterate through the contents of the document;
     * INPUT: none;
     * RESULT: a String iterator is created;
     * EXPECTED TIME COMPLEXITY: O(n), where n is the number of words in the file;
     */
    public Iterator<String> iterator()
    {
	return new Scanner(asRead).useDelimiter("[^a-zA-Z]+");
    }


    /** 
     * PURPOSE: Check whether a word is a stopword;
     * INPUT: String wordToCheck, the word that we are checking;
     * RESULT: true if the word is a stopword, false if the word is not;
     * EXPECTED TIME COMPLEXITY: O(1), HashSet 'contains()' is constant;
     */
    public Boolean isAStopWord(String wordToCheck)
    {
	return stopWords.contains(wordToCheck);
    }


    /**
     * PURPOSE: display the words in the stoplist;
     * INPUT: none;
     * RESULT: the stoplist's contents are written to stdout;
     * EXPECTED TIME COMPLEXITY: O(n), where n is the number of words in stopWords;
     */
    public void display()
    {
	for(String s : stopWords)
	{
	    System.out.println(s);
	}
    }

}
