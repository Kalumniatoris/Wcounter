package ka2.zda;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WordsManager {
	public static final String RAWFILE = "outraw.txt";
	public static final String REGEX = "[^A-Za-zęóąśłżźćń \u00A0]";
	private int bin = 0;
	private int forced = 0;
	private int semi = 0;
	private int semia = 0;
	private static Comparator<Word> bycountDec = new Comparator<Word>() {

		@Override
		public int compare(Word arg0, Word arg1) {
			return arg1.getCount() - arg0.getCount();
		}
	};

	public static void sortByCount(List<Word> words) {
		Collections.sort(words, bycountDec);
	}

	// TODO save sorted by count. Extract regex.

	static Scanner sc = null;
	private static List<Word> slowa = new LinkedList<Word>();

	public void initScanner(String filename) throws FileNotFoundException {
		// Scanner in = null;
		File f = rf(filename);
		if (f == null) {

			System.exit(4);
		}
		sc = new Scanner(f);

	}

	private File rf(String name) {
		File f = new File(name);
		if (f.isFile()) {
			return f;

		} else {
			System.err.println("FILE NOT FOUND");
			return null;
		}
	}

	public boolean readWords() {
		loadFromRa();
		System.out.println("Started importing");
		int i;
		int call = 0;
		Word tmp;
		String sl;
		System.out.println(sc.hasNext());
		if (!sc.hasNext()) {
			System.err.println("ERROR NOTHING FOUND");
		}
		while (sc.hasNext()) {
			call += 1;
			if (call % 1000 == 0) {
				System.out.println(call);
				saveToFileRa();
			}
			if (call % 100 == 0) {
				System.out.println(".");
			}

			if (getSlowa().isEmpty()) {

				sl = sc.next();
				sl = sl.toLowerCase();
				sl = sl.replaceAll(REGEX, "");
				getSlowa().add(new Word(sl));
			} else {
				sl = sc.next();
				sl = sl.toLowerCase();
				sl = sl.replaceAll(REGEX, "");
				tmp = new Word(sl);
				if (getSlowa().size() > 2) {
					i = findIBin(tmp);
				} else {
					i = findI(tmp);
				}
				int il = -999, im = -999, ie = -999;
				if (i > 0) {
					il = tmp.compareTo(getSlowa().get(i - 1));
				}
				ie = tmp.compareTo(getSlowa().get(i));
				if (i < getSlowa().size() - 1) {
					im = tmp.compareTo(getSlowa().get(i + 1));
				}
				if (il == 0 || ie == 0 || im == 0) {
					if (tmp.compareTo(getSlowa().get(i)) == 0) {
						// System.out.println("F");
						getSlowa().get(i).increase();
					} else if (i > 0) {
						if (tmp.compareTo(getSlowa().get(i - 1)) == 0) {
							getSlowa().get(i - 1).increase();
						}
					} else if (i < getSlowa().size() + 1) {
						if (tmp.compareTo(getSlowa().get(i + 1)) == 0) {
							getSlowa().get(i + 1).increase();
						}
					}
				}

				else {
					getSlowa().add(i, tmp);
				}

			}
		}

		saveToFileRa();
		return true;
	}

	private int findI(Word tmp) {
		// System.out.println("Forced");
		forced += 1;
		int i = 0;
		while (tmp.compareTo(getSlowa().get(i)) > 0
				&& i < getSlowa().size() - 1) {

			i += 1;
		}
		return i;
	}

	private int findIBin(Word tmp) {
		// ///Collections.sort(getSlowa());
		// System.out.println("\n" + tmp.getWord() + " ");

		int max = getSlowa().size() - 1;
		// System.out.print(" max=" + max);
		int min = 0;
		int i = 0;
		int p, l;
		while (min <= max) {
			i = (min + max) / 2;
			p = tmp.compareTo(getSlowa().get(i));
			if (i + 1 <= getSlowa().size() - 1) {
				l = tmp.compareTo(getSlowa().get(i + 1));
				if (p < l) {
					setBin(getBin() + 1);
					return i + 1;
				}
			}

			if (p == 0) {
				setBin(getBin() + 1);
				return i;
			}

			if (p <= 0) {
				max = i - 1;
			} else {
				min = i + 1;
			}

		}

		return findI(tmp);

	}

	public boolean saveToFileRa() {
		try {
			File f = new File(RAWFILE);
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println("Saving");

			SAV: for (Word word : getSlowa()) {
				if (word.getWord().equals("")) {
					continue SAV;
				}
				// fout.println(word.getWord() + " " + word.getCount());
				bw.write(word.getWord() + " " + word.getCount());
				bw.newLine();
			}
			bw.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Saving done");

		return true;
	}

	public void loadFromRa() {
		try {
			File f = new File(RAWFILE);
			if (f.isFile()) {
				System.out.println("Found " + RAWFILE + " updating");
				Scanner scr = new Scanner(f);
				System.out.println("AAA");

				String word;
				int count;
				SCN: while (scr.hasNextLine()) {
					if (!scr.hasNext()) {
						break SCN;
					}
					word = scr.next();
					if (!scr.hasNextInt()) {
						break SCN;
					}

					count = scr.nextInt();
					System.out.println("r: " + word + ":" + count);
					slowa.add(new Word(word, count));
					Collections.sort(slowa);
				}
				scr.close();

			} else {
				System.out.println(RAWFILE + " not found generating new one");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveSorted(String file) {
		File f = new File(file);
		List<Word> tmp = new LinkedList<Word>();
		tmp.addAll(slowa);
		sortByCount(tmp);
		try {
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println("Saving sorted");

			SAV: for (Word word : tmp) {
				if (word.getWord().equals("")) {
					continue SAV;
				}
				bw.write(word.getWord() + " " + word.getCount());
				bw.newLine();
			}
			bw.close();

			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return the slowa
	 */
	public List<Word> getSlowa() {
		return slowa;
	}

	/**
	 * @param slowa
	 *            the slowa to set
	 */
	public void setSlowa(List<Word> slowa) {
		WordsManager.slowa = slowa;
	}

	public void stats() {
		System.out.println("Bin: " + bin + "Forced: " + forced + "semi: "
				+ semi + "semia: " + semia);
	}

	/**
	 * @return the bin
	 */
	public int getBin() {
		return bin;
	}

	/**
	 * @param bin
	 *            the bin to set
	 */
	public void setBin(int bin) {
		this.bin = bin;
	}

}
