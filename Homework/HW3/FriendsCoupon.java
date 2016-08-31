import java.io.*;
import java.util.Scanner;

public class FriendsCoupon {

	static int[][] friends;
	static int numberFriends = 0;
	static int numCoupons = 0;

	public static void main(String[] args) throws InvalidInputs, IOException{
		if(args.length >= 1 && args[0].equals("test")){
			String file = "testMatrix.txt";
			testFullSolution(file);
			testReject(file);
			testExtend(file);
			testNext(file);
			// run test methods

		} else {
			if(args.length < 2)
				throw new InvalidInputs("ERROR: Not enough arguments");

			try{
				numCoupons = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e) {
				throw new InvalidInputs("ERROR: Invalid number of coupons (argument not an integer)");
			}

			numberFriends = findFriendCount(args[0]);
			if(numberFriends == 0)
				throw new InvalidInputs("ERROR: Invalid file input (improperly sized matrix)");
			if(numberFriends == -1)
				throw new InvalidInputs("ERROR: Invalid file input (one or more arguments are invalid)");
			if(numberFriends == -2)
				throw new InvalidInputs("ERROR: Invalid file input (file not found)");

			//System.out.println("Number of Friends: " + numberFriends);
			//System.out.println("Number of coupons: " + numCoupons);

			friends = new int[numberFriends][numberFriends];

			parseFriends(args[0]);

			int[] c = new int[numberFriends];

			long endTime = 0;
			long startTime = System.currentTimeMillis();

			try{
				backtrack(c);
				System.out.println("No assignment possible");
			} catch (ProgramFinished e){
				//System.out.println("End of program");
				endTime = System.currentTimeMillis();
				System.out.println("Took a total time of: " + (double)(endTime-startTime)/1000 + " seconds");
			}
		}

	}

	public static void testFullSolution(String file){
		System.out.println("TESTING FUllSOLUTION METHOD");
		try{
			numberFriends = findFriendCount(file);
		} catch (IOException e){
			System.out.println("Test Matrix file missing.");
		}
		numberFriends = 6;
		friends = new int[numberFriends][numberFriends];
		try{
			parseFriends(file);
		} catch (FileNotFoundException e){
			System.out.println("Test Matrix file missing.");
		} 

		System.out.println("Testing reject call");
		int[] test8 = {1,1,1,1,1,1};
		if(!fullSolution(test8)){
			System.out.println("\tPASSED: Rejected");
		} else {
			System.out.println("  ****  FAILED: did not reject");
		}

		System.out.println("Testing with 1 friend having no coupon, otherwise a full solution");
		int[] test10 = {1,2,0,1,3,1};
		if(!fullSolution(test10)){
			System.out.println("\tPASSED: caught the missing coupon");
		} else {
			System.out.println("  ****  FAILED: Did not catch the missing coupon.");
		}

		System.out.println("Testing with full solution");
		int[] test11 = {1,2,3,1,3,1};
		if(fullSolution(test11)){
			System.out.println("\tPASSED: accepted as full solution");
		} else {
			System.out.println("  ****  FAILED: did not recognize as full solution");
		}

	}

	public static void testReject(String file) {
		System.out.println("\n");
		System.out.println("TESTING REJECT METHOD");
		try{
			numberFriends = findFriendCount(file);
		} catch (IOException e){
			System.out.println("Test Matrix file missing.");
		}
		numberFriends = 6;
		friends = new int[numberFriends][numberFriends];
		try{
			parseFriends(file);
		} catch (FileNotFoundException e){
			System.out.println("Test Matrix file missing.");
		}



		System.out.println("Testing with no coupons applied");
		int[] test3 = {0,0,0,0,0,0};
		if(!reject(test3)){
			System.out.println("\tPASSED: did not reject");
		} else {
			System.out.println("  ****  FAILED: Rejected");
		}

		System.out.println("Testing 2 adjacent friends with same coupon");
		int[] test1 = {1,1,0,0,0,0};
		if(reject(test1)){
			System.out.println("\tPASSED: Rejected (adjacent friends with same coupon)");
		} else {
			System.out.println("  ****  FAILED: Did not reject.");
		}

		System.out.println("Testing 2 separate friends with same coupon");
		int[] test2 = {1,0,0,0,1,0};
		if(reject(test2)){
			System.out.println("\tPASSED: Rejected (separate friends with same coupon)");
		} else {
			System.out.println("  ****  FAILED: Did not reject.");
		}

		System.out.println("Testing 2 sets of 2 friends with same coupons");
		int[] test4 = {1,2,2,0,1,0};
		if(reject(test4)){
			System.out.println("\tPASSED: Rejected");
		} else {
			System.out.println("  ****  FAILED: Did not reject.");
		}

		System.out.println("Testing actual, complete solution");
		int[] test5 = {1,2,3,1,3,1};
		if(!reject(test5)){
			System.out.println("\tPASSED: did not reject");
		} else {
			System.out.println("  ****  FAILED: Rejected");
		}

		System.out.println("Testing actual, partial solution");
		int[] test6 = {1,2,3,1,0,0};
		if(!reject(test6)){
			System.out.println("\tPASSED: did not reject");
		} else {
			System.out.println("  ****  FAILED: Rejected");
		}

		System.out.println("Testing run of all differing coupons");
		int[] test7 = {1,2,3,4,5,6};
		if(!reject(test7)){
			System.out.println("\tPASSED: did not reject");
		} else {
			System.out.println("  ****  FAILED: Rejected");
		}
		
		System.out.println("Testing run of all same coupons");
		int[] test8 = {1,1,1,1,1,1};
		if(reject(test8)){
			System.out.println("\tPASSED: Rejected");
		} else {
			System.out.println("  ****  FAILED: did not reject");
		}
		
		System.out.println("Testing 2 non-friends with same coupon");
		int[] test9 = {1,0,0,1,0,0};
		if(!reject(test9)){
			System.out.println("\tPASSED: did not reject");
		} else {
			System.out.println("  ****  FAILED: Rejected");
		}
	}

	public static void testExtend(String file){
		System.out.println("\n");
		System.out.println("TESTING EXTEND METHOD");
		try{
			numberFriends = findFriendCount(file);
		} catch (IOException e){
			System.out.println("Test Matrix file missing.");
		}
		numberFriends = 6;
		friends = new int[numberFriends][numberFriends];
		try{
			parseFriends(file);
		} catch (FileNotFoundException e){
			System.out.println("Test Matrix file missing.");
		}

		System.out.println("Testing with initial coupon assignments (all nothing)");
		int[] test1 = {0,0,0,0,0,0};
		int[] check1 = {1,0,0,0,0,0};
		if(equals(extend(test1),check1)){
			System.out.println("\tPASSED: incremented the first");
		} else {
			System.out.println("  ****  FAILED: unexpected result");
		}

		System.out.println("Testing with complete coupon assignments");
		int[] test2 = {1,2,3,1,3,1};
		int[] check2 = {1,2,3,1,3,1};
		if(extend(test2) == null){
			System.out.println("\tPASSED: returned null since all positions have some coupon");
		} else {
			System.out.println("  ****  FAILED: not null");
		}

		System.out.println("Testing with 1 coupon assignment left");
		int[] test3 = {1,2,3,1,3,0};
		int[] check3 = {1,2,3,1,3,1};
		if(equals(extend(test3),check3)){
			System.out.println("\tPASSED: incremented the last");
		} else {
			System.out.println("  ****  FAILED: unexpected result");
		}

	}

	public static void testNext(String file){
		System.out.println("\n");
		System.out.println("TESTING NEXT METHOD");
		try{
			numberFriends = findFriendCount(file);
		} catch (IOException e){
			System.out.println("Test Matrix file missing.");
		}
		numberFriends = 6;
		numCoupons = 6;
		friends = new int[numberFriends][numberFriends];
		try{
			parseFriends(file);
		} catch (FileNotFoundException e){
			System.out.println("Test Matrix file missing.");
		}

		System.out.println("Testing on initial coupons (1st is A, rest are unassigned)");
		int[] test1 = {1,0,0,0,0,0};
		int[] check1 = {2,0,0,0,0,0};
		if(equals(next(test1), check1)){
			System.out.println("\tPASSED: first incremented");
		} else {
			System.out.println("  ****  FAILED: unexpected result");
		}

		System.out.println("Testing on initial coupons (1st is maximum, rest are unassigned)");
		int[] test2 = {6,0,0,0,0,0};
		if(next(test2) == null){
			System.out.println("\tPASSED: incrementing beyond the max coupon value returns null");
		} else {
			System.out.println("  ****  FAILED: non-null return from next");
		}

		System.out.println("Testing on initial coupons (1st is maximum-1, rest are unassigned)");
		int[] test3 = {5,0,0,0,0,0};
		int[] check3 = {6,0,0,0,0,0};
		if(equals(next(test3), check3)){
			System.out.println("\tPASSED: increments up to the max coupon amount");
		} else {
			System.out.println("  ****  FAILED: unexpected result");
		}

		System.out.println("Testing on all coupons at maximum-1");
		int[] test4 = {5,5,5,5,5,5};
		int[] check4 = {5,5,5,5,5,6};
		if(equals(next(test4), check4)){
			System.out.println("\tPASSED: increments the last up to the max coupon amount");
		} else {
			System.out.println("  ****  FAILED: unexpected result");
		}

		System.out.println("Testing on all coupons at maximum, last at maximum-1");
		int[] test5 = {6,6,6,6,6,5};
		int[] check5 = {6,6,6,6,6,6};
		if(equals(next(test5), check5)){
			System.out.println("\tPASSED: increments the last up to the max coupon amount");
		} else {
			System.out.println("  ****  FAILED: unexpected result");
		}

		System.out.println("Testing on all coupons at maximum");
		int[] test6 = {6,6,6,6,6,6};
		if(next(test6) == null){
			System.out.println("\tPASSED: returned null");
		} else {
			System.out.println("  ****  FAILED: unexpected result");
		}

	}

	public static boolean equals(int[] x, int[] y) {
		for(int i=0; i<x.length; i++){
			if(x[i] != y[i])
				return false;
		}
		return true;
	}

	public static void backtrack(int[] partial) throws ProgramFinished{
		//printCurrent(partial);
		if(reject(partial)) return;
		if(fullSolution(partial)){
			printSolution(partial);
			throw new ProgramFinished();
		}
		int[] attempt = extend(partial);
		while(attempt != null){
			backtrack(attempt);
			attempt = next(attempt);
		}
	}

	public static void printSolution(int[] solution) {
		System.out.print("Solution: ");
		for(int i=0; i<numberFriends-1; i++){
			System.out.print((char)(solution[i]+64) + ", ");
		}
		System.out.println((char)(solution[numberFriends-1]+64));
	}

	public static void printCurrent(int[] solution) {
		for(int i=0; i<numberFriends-1; i++){
			System.out.print((char)(solution[i]+64) + ", ");
		}
		System.out.println((char)(solution[numberFriends-1]+64));
	}

	public static int[] next(int[] partial) {
		int[] temp = new int[numberFriends];
		for(int i=0; i<numberFriends; i++){
			if(i == numberFriends-1 || partial[i+1] == 0){
				if(partial[i] >= numCoupons){
					return null;
				} else {
					temp[i] = partial[i]+1;
					break;
				}
			} else {
				temp[i] = partial[i];
			}
		}
		return temp;
	}

	public static int[] extend(int[] partial) {
		int[] temp = new int[numberFriends];
		for(int i=0; i<numberFriends; i++){
			if(partial[i] != 0){
				temp[i] = partial[i];
			} else {
				temp[i] = 1;
				return temp;
			}
		}
		return null;
	}

	public static boolean fullSolution(int[] partial) {
		for(int i=0; i<numberFriends; i++){
			if(partial[i] == 0){
				return false;
			}
		}

		if(reject(partial)){
			return false;
		}

		return true;
	}

	public static boolean reject(int[] partial) {
		for(int i=0; i<numberFriends; i++){
			if(partial[i] != 0){
				for(int j=0; j<numberFriends; j++){
					/*if(partial[i] == 0){

					} else if(friends[i][j] == 1 && partial[i] == partial[j]){
						System.out.print("Rejecting: ");
						printCurrent(partial);
						return true;
					}*/
					if(friends[i][j] == 1){
						if(partial[i] == partial[j]){
							//System.out.print("Rejecting: ");
							//printCurrent(partial);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static int findFriendCount(String file) throws IOException {
		Scanner s = null;
		int count = 0;

		try{
			s = new Scanner(new BufferedReader(new FileReader(file)));

			while(s.hasNext()){
				
				int x = Integer.parseInt(s.next());
				if(x != 0 && x != 1)
					return -1;
				count++;
			}
		} catch (FileNotFoundException e) {
			return -2;
		} catch (NumberFormatException e) {
			return -1;
		} finally {
			if(s != null){
				s.close();
			}
		}

		if(((int) Math.sqrt(count)) != Math.sqrt(count))
			count = 0;
		else
			count = (int)Math.sqrt(count);

		return count;
	}

	public static void parseFriends(String file) throws FileNotFoundException {
		Scanner s = null;

		try{
			s = new Scanner(new BufferedReader(new FileReader(file)));

			for(int i=0; i<numberFriends; i++){
				for(int j=0; j<numberFriends; j++){
					friends[i][j] = Integer.parseInt(s.next());
				}
			}
		} finally {
			if(s != null){
				s.close();
			}
		}

		return;
	} 

	static class ProgramFinished extends Exception {
		public ProgramFinished(){}
	}

	static class InvalidInputs extends Exception {
		public InvalidInputs(String e){
			System.out.println(e);
		}
	}
}