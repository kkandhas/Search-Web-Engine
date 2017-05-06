	/**********************************************************************************
	 * FILE NAME 	 :	CrawlerWeb.java
	 * DETAILS		 :	This File contains all the functions relating processing 
	 * 					the given points bellow
	  					1.	Retrieve a web page from a website
						2.	Collect all the links on that website
						3.	Collect all the words on that website
						4.	See if the word we're looking for is contained in the list of 
							words
						5.	Visit the next link 
						6.	Keep track of pages that we've already visited
						7.	Put a limit on the number of pages to search so this doesn't 
							run for eternity.
		AUTHOR		 :	Karan Prabu Kandhaswamy
	
	 **********************************************************************************/
package com.search.engine;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CrawlerWeb
{
  private static final int MAX_PAGES_TO_SEARCH = 20; //MAX no. of pages to search
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();

	/**********************************************************************************
	 * FUNCTION NAME :	search
	 * DETAILS		 :	Our main launching point for the crwalerweb's functionality. 
	 * 					Internally it creates crawler legs that make an HTTP request 
	 * 					and parse the response (the web page).
	 * INPUT TYPE	 :	String type (URL) , ArrayList<String> (searchWord's Keywords)
	 * OUTPUT TYPE	 :	void type 
	 **********************************************************************************/

  public void search(String url, ArrayList<String> searchWord)
  {
      while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
      {
          String currentUrl;
          CrawlerLeg leg = new CrawlerLeg();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
              
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          leg.crawl(currentUrl); // Gathers up all the links on the page. 
          				 
          boolean success = leg.searchForWord(searchWord);// searches the keywords 
          											//in the body of the Current URL
          if(success)
          {
              System.out.println(String.format("===> Success : Word %s found at %s", searchWord, currentUrl));

          }
          this.pagesToVisit.addAll(leg.getLinks());
      }
      System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");

  }

	/**********************************************************************************
	 * FUNCTION NAME :	nextUrl
	 * DETAILS		 :	Returns the next URL to visit (in the order that they were found).
	 * 					We also do a check to make sure this method doesn't return a URL 
	 * 					that has already been visited.
	 * INPUT TYPE	 :	NA
	 * OUTPUT TYPE	 :	String (nextUrl)
	 **********************************************************************************/

  private String nextUrl()
  {
      String nextUrl;
      do
      {
          nextUrl = this.pagesToVisit.remove(0);
      } while(this.pagesVisited.contains(nextUrl));
      this.pagesVisited.add(nextUrl);
      return nextUrl;
  }
}