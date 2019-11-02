package concurrencyModelPackage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *This class implements the Runnable interface and has as members a list which is actually the interval
 *from which it will get the prime numbers and a PrintWriter used to write the determined prime numbers to a specified text file.
 */
public class ComputeDivisorsThread implements Runnable {

	private List<Integer> interval;
	private PrintWriter writer;
	
	/**
	 * This is the constructor of the thread.
	 * @param list represents the interval from which we will get the prime numbers
	 * @param pw represents the PrintWriter for writing the output to a text file
	 */
	public ComputeDivisorsThread(List<Integer> list, PrintWriter pw) {
		interval = new ArrayList<Integer>();
		interval = list;
		writer = pw;
	}

	/**
	 * This function calls the getPrimeNumbers method to get the list of prime numbers and then writes 
	 * to a specific text file the elements of the resulted list.
	 */
	@Override
	public void run() {
		List<Integer> primeNumbers = getPrimeNumbers(interval);
		
		for(int iterator = 0; iterator < primeNumbers.size(); iterator++) {
			writer.print("  " + primeNumbers.get(iterator) + "  ");
		}
		writer.println();
	}
	
	/**
	 * This function inserts into primeNumbers only the prime numbers from myList.
	 * @param myList represents the interval from which the prime numbers will be selected 
	 * @return the list of prime numbers from the given interval
	 */
	
	private List<Integer> getPrimeNumbers(List<Integer> myList) {
		List<Integer> primeNumbers = new ArrayList<Integer>();
		int noDivisors = 0;
		
		for(int iterator1 = 0; iterator1 < myList.size(); iterator1++) {
			for(int iterator2 = 2; iterator2 <= myList.get(iterator1)/2; iterator2++) {
				if(myList.get(iterator1) % iterator2 == 0) {
					noDivisors++;
				}
			}
			if(noDivisors == 0) {
				primeNumbers.add(myList.get(iterator1));
			}
			else {
				noDivisors = 0;
			}
		}
		return primeNumbers;	
	}
}
