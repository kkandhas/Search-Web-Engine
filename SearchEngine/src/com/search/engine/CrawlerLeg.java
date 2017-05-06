	/**********************************************************************************
	 * FILE NAME 	 :	CrawlerLeg.java
	 * DETAILS		 :	This File contains all the functions relating processing 
	 * 					the given points bellow
						1. Crawl the page (make an HTTP request and parse the page) 
						2. Search for a word 
						3. Return all the links on the page.
	 * AUTHOR		 :	Karan Prabu Kandhaswamy
	
	 **********************************************************************************/
package com.search.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerLeg
{
    // USER_AGENT is defined to access the web page.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;

	/**********************************************************************************
	 * FUNCTION NAME :	crawl
	 * DETAILS		 :	This performs all the work. It makes an HTTP request, checks 
	 * 					the response, and then gathers up all the links on the page. 
	 * 					Perform a searchForWord after the successful crawl	
	 * INPUT TYPE	 :	String type (URL)
	 * OUTPUT TYPE	 :	boolean type (whether or not the crawl was successful)
	 **********************************************************************************/

    public boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
                System.out.println("\n ****Visiting**** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("*****Failure***** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl("href"));
            }
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
    }

	/**********************************************************************************
	 * FUNCTION NAME :	searchForWord
	 * DETAILS		 :	Performs a search on the body of on the HTML document that is 
	 * 					retrieved. This method should only be called after a successful crawl.
	 * INPUT TYPE	 :	ArrayList<String> (searchWord)
	 * OUTPUT TYPE	 :	boolean type (whether or not the keywords were found)
	 **********************************************************************************/

    public boolean searchForWord(ArrayList<String> searchWord)
    {
        // This method should only be used after a successful crawl.
        if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        int i=0;
        int flag=0;
        for (String str : searchWord)
        {
        	i++;
        	if (bodyText.toLowerCase().contains(str.toLowerCase()))
        	{
        		flag++;
        	}
        }
        if (i == flag)
        	return true;
        else 
        	return false;
    }

	/**********************************************************************************
	 * FUNCTION NAME :	getLinks
	 * DETAILS		 :	used to fetch the connected links of the URL .
	 * INPUT TYPE	 :	NA
	 * OUTPUT TYPE	 :	List<String> type (the links connected to the URL)
	 **********************************************************************************/

    public List<String> getLinks()
    {
        return this.links;
    }

}
