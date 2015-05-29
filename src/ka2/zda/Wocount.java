package ka2.zda;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Wocount {
	public static final String SORTED = "sorted.txt";
	
	static Scanner scn = new Scanner(System.in);
	public static void main(String[] args) throws FileNotFoundException {
		List<String> names = new ArrayList<String>();
		WordsManager wr=new WordsManager();
		List<Word> wyrazy = new LinkedList<Word>();
		System.out.println("Word counter");
		System.out.println("Podaj plik wej�ciowy: ");
NIN: while(scn.hasNext()){
String f=scn.next();
names.add(f);
if(f=="")break NIN;
System.out.println(f);
}
		
		System.out.println("begining ");
		for (String n : names) {
			wr.initScanner(n);
			wr.readWords();
			wyrazy=wr.getSlowa();
			wr.saveToFileRa();
			wr.stats();
			wr.saveSorted(SORTED);
		}
		
		
		/*for (Word word : wyrazy) {
			System.out.println(word.getWord()+": "+word.getCount());
		}
		System.out.println("_____");
		*/
		
		
		
	}

}
