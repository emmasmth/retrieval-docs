/**
 * This is my code! Its goal is to process queries and return a relevant set of document that
 * contain a given word or words
 * CS 312 - Assignment 9
 * @author Emma Smith
 */


import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
public class SearchEngine
{
    /** Map<String, Set<Document>> invertedIndex, the map of key, value pairs */
    protected Map<String, Set<Document>> invertedIndex;

    /** StopList stoplist, the Stoplist object for this search engine */
    protected StopList stoplist;

    /** Set<String> cleanContents, the set of words without the stopwords */
    protected Set<String> cleanContents;

    /** Boolean contentsFlag, the flag to turn on/off displaying file contents */
    protected Boolean contentsFlag;   


    /**
     * PURPOSE: initialize the SearchEngine;
     * INPUT: Boolean contents flag, the flag to turn on/off displaying contents;
     * INPUT: String stoplistPath, the path to the stoplist file;
     * RESULT: the initialized SearchEngine;
     * EXPECTED TIME COMPLEXITY: O(1);
     */
    public SearchEngine(Boolean contentsFlag, String stoplistPath)
    {
	invertedIndex = new HashMap<String, Set<Document>>();
	stoplist = new StopList(stoplistPath);
	this.contentsFlag = contentsFlag;
    }


    /**
     * PURPOSE: process a query made up of a single word;
     * INPUT: String query, the word to be processed;
     * RESULT: the Set<Document> value corresponding to the query is returned;
     * EXPECTED TIME COMPLEXITY: O(1), HashMap 'get()' is usually constant;
     */
    public Set<Document> processSingleWordQuery(String query)
    {
	if(invertedIndex.get(query) != null)
	{
	    query = new Scanner(query).useDelimiter("[^a-zA-Z]+").next();
	    
	    Set<Document> ans = invertedIndex.get(query);
	    return ans;
	}
	else
	{
	    return new HashSet<Document>();
	}
    }


    /**
     * PURPOSE: process a query made up of multiple words;
     * INPUT: Set<String> query, the set of words in the query;
     * RESULT: the Set<Document> value corresponding to the intersection
     * of the query words is returned;
     * EXPECTED TIME COMPLEXITY: O(n);
     */
    public Set<Document> processMultiWordQuery(Set<String> query)
    {
	Set<String> queryWords = new HashSet<String>();
	queryWords = makeClean(query);
	Iterator<String> ii = queryWords.iterator();
	
	String next = ii.next();
	Set<Document> ansSet = processSingleWordQuery(next);
	while (ii.hasNext())
	{
	    ansSet.retainAll(processSingleWordQuery(ii.next()));
	}
	return ansSet;
    }


    /**
     * PURPOSE: add a document and its words to the inverted index;
     * INPUT: Document doc, the document to be added;
     * RESULT: the inverted index is updated;
     * EXPECTED TIME COMPLEXITY: O(n);
     */
    public void addDocument(Document doc)
    {
	Set<String> cleanWords = new HashSet<String>();
	cleanWords = makeClean(doc.createSet());
	for(String s : cleanWords)
	{
	    String placeHolder = s;
	    if (invertedIndex.keySet().contains(placeHolder))
	    {
		invertedIndex.get(placeHolder).add(doc);
	    }
	    else
	    {
		Set<Document> newSet = new HashSet<Document>();
	 	newSet.add(doc);
		invertedIndex.put(placeHolder, newSet);
	    }
	}
    }


    /** 
     * PURPOSE: make a set of words not include any stopwords;
     * INPUT: Set<String> documentWords, the set of words that needs to be cleaned;
     * RESULT: a set that contains no stopwords;
     * EXPECTED TIME COMPLEXITY: O(n);
     */
    public Set<String> makeClean(Set<String> documentWords)
    {
	cleanContents = new HashSet<String>();
	Iterator<String> ii = documentWords.iterator();
	while(ii.hasNext())
	{
	    String placeHolder = ii.next();
	    if (stoplist.isAStopWord(placeHolder) == false)
	    {
		cleanContents.add(placeHolder);
	    }
	}
	return cleanContents;
    } 


    /**
     * PURPOSE: make a single word clean, mostly used for words with apostrophes or other punctuation;
     * INPUT: String word, the word that needs to be cleaned;
     * RESULT: a word that does not include punctuation or stopwords;
     * EXPECTED TIME COMPLEXITY: O(n);
     */
    public String makeSingleWordClean(String word)
    {
	String cleanWord = null;
	Scanner scan = new Scanner(word).useDelimiter("[^a-zA-Z]+");
	
	while(scan.hasNext())
	{
	    String nextWord = scan.next();
	    if(stoplist.isAStopWord(nextWord) == false)
	    {
		cleanWord = nextWord;
	    }
	}
	return cleanWord;
    }


    /**
     * PURPOSE: display a set of documents' filenames,
     * and if flag is true, the contents;
     * INPUT: Set<Documet> docSet, the set of documents to parse through;
     * RESULT: the documents' filenames (and contents) are displayed to stdout;
     * EXPECTED TIME COMPLEXITY: O(n);
     */
    public void display(Set<Document> docSet)
    {

	System.out.println("--- found in " + ((docSet == null) ? 0 : docSet.size()) + " documents");

	for (Document d : docSet)
	{
	    d.displayFileName();
	    if (contentsFlag)
	    {
		System.out.println("\n>>> CONTENTS: ");
		d.displayContents();
	    }
	    System.out.println(" - - - - - - - ");
	}
    }


    /** 
     * PURPOSE: display all pairs in the inverted index;
     * INPUT: none;
     * RESULT: the HashMap's key, value pairs are displayed to stdout;
     * EXPECTED TIME COMPLEXITY: O(n^2);
     */
    public void displayAll()
    {
	for(String key : invertedIndex.keySet())
	{

	    for(Document d : invertedIndex.get(key))
	    {
		System.out.println("\n>>> KEY: " + key);
	        d.displayFileName();
		System.out.println("\n>>> CONTENTS: ");
		d.displayContents();
		System.out.println(" - - - - - - - ");
	    }
	}
    }

}


