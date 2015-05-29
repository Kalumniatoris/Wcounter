package ka2.zda;


public class Word implements Comparable<Word>{
private String word;
private int count;
public Word(String word){
	this.setWord(word);
	this.setCount(1);
}
public Word(String word, int count){
	this.word = word;
	this.setCount(count);
}
public void increase(){
	this.setCount(this.getCount() + 1);
}
public int getCount(){
	return this.count;
}
public String getWord() {
	return word;
}
public void setWord(String word) {
	this.word = word;
}
@Override
public int compareTo(Word arg0) {
	int i= word.compareToIgnoreCase(arg0.getWord());
	//if(true)
	//return i;
	i=(int) Math.signum(i);
	return i;
}
/**
 * @param count the count to set
 */
public void setCount(int count) {
	this.count = count;
}





}
