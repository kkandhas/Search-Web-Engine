	/**********************************************************************************
	 * FILE NAME 	 :	Main.java
	 * DETAILS		 :	This File contains all the functions relating processing 
	 * 					the given points bellow
	  					1. Get the webpage URL and search word/phrase as Input from 
	  						the user
	  					2.  Separated the keywords from the phrase given 
	  					3.	Process the Keywords and check whether its is found in any of 
	  						URLS pages body, related to the webpage given as input
	  					4.	Display the Result 
	 * AUTHOR		 :	Karan Prabu Kandhaswamy
		
	 **********************************************************************************/

package com.search.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	
	/**********************************************************************************
	 * FUNCTION NAME :	main
	 * DETAILS		 :	main function for the project	
	 * @param args
	 * @throws IOException
	 **********************************************************************************/

	public static void main(String[] args) throws IOException {
	
	CrawlerWeb url = new CrawlerWeb();
	
	BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
	
	System.out.println("Enter the FULL URL of the webpage to be searched: ");
	String URL = bfr.readLine();
	
	System.out.println("Enter the search word/phrase: ");
	String sword = bfr.readLine();
	
	ArrayList<String> keywords = new ArrayList<String>();
	
	keywords = stopWordSeparation(sword);

		url.search(URL,keywords);
	
	}

	/**********************************************************************************
	 * FUNCTION NAME :	stopWordSeparation
	 * DETAILS		 :	This function is used to separate the stop words mentioned 
	 * 					in the stopwordslist.txt, for the given search phrase.	
	 * INPUT TYPE	 :	String type 
	 * OUTPUT TYPE	 :	ArrayList<String>
	 **********************************************************************************/

public static ArrayList<String> stopWordSeparation (String sword){

	int k=0;
	ArrayList<String> wordsList = new ArrayList<String>();
	String sCurrentLine;
	String[] stopwords = new String[2000];
	try{
	        FileReader fr=new FileReader("F://stevens/java/eclipse/Workspace/SearchEngine/src/com/search/engine/stopwordslist.txt");
	        BufferedReader br= new BufferedReader(fr);
	        while ((sCurrentLine = br.readLine()) != null){
	            stopwords[k]=sCurrentLine;
	            k++;
	        }

	        StringBuilder builder = new StringBuilder(sword);
	        String[] words = builder.toString().split("\\s");
	        for (String word : words){
	            wordsList.add(word);
	        }
	        for(int i = 0; i < wordsList.size(); i++){
	            for(int j = 0; j < k; j++){
	                if(stopwords[j].contains(wordsList.get(i).toLowerCase())){
	                    wordsList.remove(i);
	                }
	             }
	        }
	        System.out.print("User's input after removing the stopwords : ");
	        for (String str : wordsList){
	            System.out.print(str+" ");
	        } 
	        br.close();
	    }catch(Exception ex){
	        System.out.println(ex);
	    }
	return wordsList;
}
}
