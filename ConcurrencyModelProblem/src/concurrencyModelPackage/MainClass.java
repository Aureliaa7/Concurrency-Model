package concurrencyModelPackage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class MainClass {
	
	public static void main(String[] args) {
		int choice;
		Scanner scanner = new Scanner(System.in);
		Integer upperBound, noThreads;
	
		System.out.println("1. Use solution one to determine the prime numbers");
		System.out.println("2. Use solution two to determine the prime numbers");
		System.out.println("3. Exit");
		System.out.println("\nYour choice: ");
		choice = scanner.nextInt();
		switch(choice) {
			case 1:
				int choice2;
				PrintWriter writer1 = null;
				Thread threads[];
				
				System.out.println("******* Solution one *******");
				System.out.println("\n1. Enter the upper bound of the interval and the number of threads");
				System.out.println("2. Generate random numbers for the upper bound of the interval and the number of threads");
				System.out.println("\nYour choice: ");
				choice2 = scanner.nextInt();
				try {
					writer1 = new PrintWriter("outputSolution1.txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				switch(choice2) {
					case 1:
						System.out.println("Enter the end of the interval: ");
						upperBound = scanner.nextInt();
					
						System.out.println("Enter the number of threads: ");
						noThreads = scanner.nextInt();
						threads = new Thread[noThreads];
	
						long startTime = System.nanoTime();
						solution1(upperBound, noThreads, threads, writer1); 
						//estimatedTime represents the needed time in nano seconds for computing the prime numbers using the first solution
						long estimatedTime = System.nanoTime() - startTime;
						System.out.println("\n\nTime elapsed: " + estimatedTime + " nano seconds");
						break;
					case 2:
						upperBound = generateRandomNumber();
						noThreads = generateRandomNumber();
				
						if(noThreads > upperBound) {
							while(noThreads > upperBound) {
								upperBound = generateRandomNumber();
								noThreads = generateRandomNumber();
							}
						}
						System.out.println("** upper bound: " + upperBound);
						System.out.println("** number of threads: " + noThreads);
						
						threads = new Thread[noThreads];
						long startTime1 = System.nanoTime();
						solution1(upperBound, noThreads, threads, writer1); 
						//estimatedTime represents the needed time in nano seconds for computing the prime numbers using the first solution
						long estimatedTime1 = System.nanoTime() - startTime1;
						System.out.println("\n\nTime elapsed: " + estimatedTime1 + " nano seconds");
						break;
					}
					break;
					
			case 2:
				PrintWriter writer2 = null;
				int choice3;
				
				System.out.println("******* Solution two *******");
				System.out.println("\n1. Enter the upper bound of the interval and the number of threads");
				System.out.println("2. Generate random numbers for the upper bound of the interval and the number of threads");
				System.out.println("\nYour choice: ");
					
				choice3 = scanner.nextInt();
				try {
					writer2 = new PrintWriter("outputSolution2.txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				switch(choice3) {
					case 1:
						System.out.println("Enter the end of the interval: ");
						upperBound = scanner.nextInt();
						System.out.println("Enter the number of threads: ");
						noThreads = scanner.nextInt();
						threads = new Thread[noThreads];
						
						long startTime = System.nanoTime();
						solution2(upperBound, noThreads, threads, writer2);
						long estimatedTime = System.nanoTime() - startTime;
						System.out.println("\n\nTime elapsed: " + estimatedTime + " nano seconds");
						break;
					case 2:
						upperBound = generateRandomNumber();
						noThreads = generateRandomNumber();
								
						if(noThreads > upperBound) {
							while(noThreads > upperBound) {
								upperBound = generateRandomNumber();
								noThreads = generateRandomNumber();
							}
						}
						System.out.println("** upper bound: " + upperBound);
						System.out.println("** number of threads: " + noThreads);
						
						threads = new Thread[noThreads];
						long startTime2 = System.nanoTime();
						solution2(upperBound, noThreads, threads, writer2); 
						//estimatedTime represents the needed time for computing the prime numbers using the second solution
						long estimatedTime2 = System.nanoTime() - startTime2;
						System.out.println("\n\nTime elapsed: " + estimatedTime2 + " nano seconds");
						break;
				}
				break;
					
			case 3:
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid input!");
		}
	}
	
	/**
	 * This function creates a list of integer numbers. 
	 * @param min represents the start of the list
	 * @param max represents the end of the list
	 * @return the created list of integers
	 */
	static List<Integer> createList(Integer min, Integer max) {
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i = min; i <= max; i++) {
			list.add(i);
		}
		return list;
	}
	
	/**
	 * This function creates a list by inserting all the numbers starting from min to max, except
	 * for those numbers which are multiples of nThreads+1 strictly bigger than nThreads+1.
	 * @param min represents the lower bound of the interval
	 * @param max represents the upper bound of the interval
	 * @param nThreads represents the number of threads
	 * @return the list without the multiples of nThreads+1 strictly bigger than nThreads+1
	 */
	static List<Integer> removeMultiples(int min, int max, int nThreads) {
		List<Integer> initialList = new ArrayList<Integer>();
		int temp = nThreads + 1;
	
		for(int iterator = min; iterator <= max; iterator++) {
			if(iterator > temp && iterator % temp == 0) {
				continue;
			}
			else {
				initialList.add(iterator);
			}
		}
		return initialList;
	}
	
	/**
	 * This function generates a random number between 10^3 and 10^8.
	 * @return the generated number
	 */
	static Integer generateRandomNumber() {
		Random random = new Random();
		Integer low = (int) Math.pow(10, 3);
		Integer high = (int) Math.pow(10, 8);
		Integer result = random.nextInt(high - low) + low;
		return result;
	}
	
	/** 
	 * This function partitions the interval [1..maxLimit] in noThreads intervals as follows:
	 * I1=[1, quotient+1], I2=[quotient+2, 2*quotient+2], ...,
	 * Ik=[(k-1)*quotient+remainder+1, k*quotient+remainder], where k=noThreads. Each thread j(j is between 1 and noThreads)
	 * will determine the prime numbers in the interval Ij.
	 * @param maxLimit represents the upper bound of the interval
	 * @param noThreads represents the number of threads
	 * @param threads represents the list of threads
	 * @param pw represents the PrintWriter used to write the output to outputSolution1.txt file
	 */
	static void solution1(Integer maxLimit, Integer noThreads, Thread[] threads, PrintWriter pw) {
		System.out.println("\n\n*** Solution one ***");
		Integer remainder = maxLimit % noThreads;
		Integer quotient = (maxLimit - remainder) / noThreads;
		Integer auxLimit1 = 0, auxLimit2 = 0;
		// in this loop, the bounds of the interval corresponding to each thread will be computed
		// and then a thread will be created for its corresponding interval
		for(int iterator = 1; iterator <= noThreads; iterator++) {
			/*auxLimit1 represents the beginning of the interval and 
				auxLimit2 represents the end of the interval. */
			if(iterator == 1) {
				auxLimit1 = 1;
				auxLimit2 = quotient + 1;
			}
			else {
			auxLimit1 = auxLimit2 + 1;
			auxLimit2 = iterator * quotient + remainder;
			}
			//auxList is the interval corresponding to each thread
			List<Integer>auxList = new ArrayList<Integer>();
			auxList = createList(auxLimit1, auxLimit2);
		
			pw.print("\nInterval " + iterator + ": [");
			pw.print(auxList.get(0) + ", " + auxList.get(auxList.size() - 1) +"]");
			
			/*To create a thread we need an interval and a PrintWriter for writing 
			the prime numbers computed by each thread to a text file. */ 
			threads[iterator - 1] = new Thread(new ComputeDivisorsThread(auxList, pw));
		}
		pw.println();
		pw.println("\nThe prime numbers corresponding to each thread: ");
		// here the threads are starting
		for(int iterator = 0; iterator < noThreads; iterator++) {
			threads[iterator].start();
		}
		// join() method allows one thread to wait until another thread completes its execution
		for(int iterator = 0; iterator < noThreads; iterator++) {
			try {
				threads[iterator].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/* If the stream has saved any characters from the various write() methods 
		in a buffer, write them immediately to their intended destination. */
		pw.flush();
		//close the PrintWriter
		pw.close();
	}
	
	/**
	 * This function creates the initial list after removing the multiples of noThreads+1 strictly bigger than noThreads+1
	 * from the interval [1, maxLimit]. The resulted set(let us call it M) is then divided into noThreads sets. Each set Mj 
	 * contains all those elements from M which divided by noThreads+1 give remainder j.
	 * It is considered that noThreads+1 belongs to M1. Each thread j will determine the prime numbers from set j.
	 * @param maxLimit the end of the interval
	 * @param noThreads represents the number of threads
	 * @param threads represents the list which will store the noThreads
	 * @param pw represents the PrintWriter used to write the output to outputSolution2.txt file
	 */
	static void solution2(Integer maxLimit, Integer noThreads, Thread[] threads, PrintWriter pw) {
		System.out.println("\n\n*** Solution two ***");
		List<Integer> listOfNumbers = new ArrayList<Integer>();
		Map<Integer, List<Integer>> hashMap = new HashMap<Integer, List<Integer>>();
		
		// our interval starts always from 1
		listOfNumbers = removeMultiples(1, maxLimit, noThreads);
			
		if(listOfNumbers.size() > 0) {
			pw.println("\nYour list after removing the multiples of " + (noThreads + 1) + ": ");
			for(int iterator = 0; iterator < listOfNumbers.size(); iterator++) {	
				pw.print(listOfNumbers.get(iterator) + "  ");		
			}
		}
				
		for(int iterator1 = 1; iterator1 <= noThreads; iterator1++) {
			List<Integer> auxList = new ArrayList<Integer>();
			
			for(int iterator2 = 0; iterator2 < listOfNumbers.size(); iterator2++) {
				if((listOfNumbers.get(iterator2)) % (noThreads + 1) == iterator1) { 
					auxList.add(listOfNumbers.get(iterator2));
				}
				// inserting noThreads+1 into M1
				if(iterator1 == 1 && listOfNumbers.get(iterator2) == noThreads + 1) {
					auxList.add(listOfNumbers.get(iterator2));
				}
			}
			hashMap.put(iterator1 - 1, auxList);
		}
				
		//write Mj to text file
		pw.println();
		pw.println("\nThe resulted sets are: ");
		for(int iterator1 = 0; iterator1 < noThreads; iterator1++) {
			List<Integer> a = new ArrayList<Integer>();
			a = hashMap.get(iterator1);
			pw.print("\nM"+ (iterator1 + 1) + ":  ");
			for(int iterator2 = 0; iterator2 < a.size(); iterator2++) {
				pw.print(a.get(iterator2) + "  ");
			}
		}
		pw.println();	
		
		for(int iterator = 1; iterator <= noThreads; iterator++) {
			List<Integer> tempList = new ArrayList<Integer>();
			tempList = hashMap.get(iterator - 1);
			threads[iterator - 1] = new Thread(new ComputeDivisorsThread(tempList, pw));
		}
		//start the threads
		pw.println("\nThe prime numbers are: ");	
		for(int iterator = 0; iterator < noThreads; iterator++) {
			threads[iterator].start();
		}
		
		for(int iterator = 0; iterator < noThreads; iterator++) {
			try {
				threads[iterator].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		pw.flush();
		pw.close();
	}
}
