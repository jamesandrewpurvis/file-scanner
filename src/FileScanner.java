import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileScanner 
{
	// fields
	private Map<String, Integer> mWordDictionary = null;
	private BufferedReader mReader = null;
	private boolean mPoemStart = false;
	private StringBuilder mPoem = null;
	
	//constructor
	public FileScanner(String path)
	{
		try
		{
			//intialize fields
			mReader = new BufferedReader(new FileReader(path));
			mPoem = new StringBuilder();
			String mLine = mReader.readLine();
			mWordDictionary = new LinkedHashMap<String, Integer>();
			
			//while each line in our poem isn't null
			while(mLine != null)
			{
				//filter the html tags, trim excessive spaces and remove dashes.
				mLine = mLine.replaceAll("\\<.*?>","");
				mLine = mLine.replaceAll("[?,!;'.]", "");
				mLine = mLine.replaceAll("\"", "");
				mLine = mLine.replace("&mdash", " ");
				mLine = mLine.trim();
				
				//if the line equal the raven
				if (mLine.equals("The Raven"))
				{
					//our poem has started, set boolean to true
					mPoemStart = true;
					
				}
				
				//if our line contains END the poem is over.
				if (mLine.contains("*** END"))
				{
					//set our boolean to false;
					mPoemStart = false;
				}
				
				//call our storepoem method, take in our line
				
				   StorePoem(mLine);
				
				//set our line to the next line
					mLine = mReader.readLine();
			}
		}
		//error handling
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		//we finished reading the poem, call our save and print methods.
		
		SaveWords();
		PrintWords();
		
	}
	
	private void StorePoem(String line)
	{
		//check if we are in the poem, if we are and it isn't blank, save to a stringbuilder.
		if (mPoemStart == true && line.isBlank() != true)
		{
			mPoem.append(line);
			mPoem.append(System.lineSeparator());
		}
	}
	
	//method helps us to count words
	private void SaveWords()
	{
		//create a new stringtokenizer with our stringbuilder mPoem
		StringTokenizer mTokenizer = new StringTokenizer(mPoem.toString());
		
		String mWord; 
		
		//while our tokenizer has more tokens (there are more words)
		while(mTokenizer.hasMoreTokens())
		{
			//set the word to lowercase
			mWord = mTokenizer.nextToken().toLowerCase();
			
			//if our dictionary contains the word already
			if (mWordDictionary.containsKey(mWord) == true)
			{
				//update our count and the dictionary
				int currentCount = mWordDictionary.get(mWord);
				mWordDictionary.replace(mWord, currentCount = currentCount + 1);
			}
			else //else
			{
				//add to the dictinary with a count of 1
				mWordDictionary.put(mWord, 1);
			}
		}
		
	}
	
	private void PrintWords()
	{
		//create a new set objet so we can iterate through each object in our dictionary.
		Set<String> mKeys = mWordDictionary.keySet();
		
		//print the header for user output
		System.out.println("\n");
		System.out.println("\n");
		
		System.out.println("#########################");
		System.out.println(" Top 20 Most Used Words ");
		System.out.println("#########################");
		
		//convert our set to a stream, and print the 20 most occuring words.
		mWordDictionary.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
        .limit(20)
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
        )).forEach((s, integer) -> System.out.println(String.format("%s : %s", s, integer)));
		
		
		
	}
	
	
	
	
	
}

