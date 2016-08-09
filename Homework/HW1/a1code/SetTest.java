public class SetTest {

	public static Set<String> testSet1 = new Set<String>(0);
	public static Set<String> testSet2 = new Set<String>(1);
	public static Set<String> testSet3 = new Set<String>(2);
	public static Set<String> testSet4 = new Set<String>(2);
	public static Set<String> testSet5 = new Set<String>(10);
	public static Set<String> testSet6 = new Set<String>(4);
	public static Set<String> testSet7 = new Set<String>(4);


	public static void main(String args[]){
		checkAdd();
		checkRemove();
	}

	public static void checkAdd() {
		System.out.println("####    Testing Add Methods\n");

		System.out.println("Testing Add item to list size 0");
		try {
			testSet1.add("A");
			System.out.println("\tPassed");
			System.out.println(testSet1.toString());
		} catch(SetFullException e) {
			System.out.println("****    Failed Add");
		}

		System.out.println("Testing Add 2 items to list size 1");
		try {
			testSet2.add("A");
			testSet2.add("B");
			System.out.println("\tPassed");
		} catch(SetFullException e) {
			System.out.println("****    Failed Add -- Overflow");
		}

		System.out.println("Testing Add 5 items to list size 2");
		try {
			testSet3.add("A");
			testSet3.add("B");
			testSet3.add("C");
			testSet3.add("D");
			testSet3.add("E");
			System.out.println("\tPassed");
		} catch(SetFullException e) {
			System.out.println("****    Failed Add -- Overflow");
		}


		System.out.println("Testing Add Duplicate items to list size 2");
		try {
			testSet4.add("A");
			if(!testSet4.add("A")){
				System.out.println("\tPassed");
			} else {
				System.out.println("****    Failed -- Wrong Boolean");
			}
		} catch(SetFullException e) {
			System.out.println("****    Failed Add -- Overflow");
		}

		System.out.println("Testing Add null items to list");
		try {
			testSet4.add(null);
			System.out.println("****    Failed -- Added null item");
		} catch(java.lang.IllegalArgumentException e) {
			System.out.println("\tPassed");
		} catch(SetFullException e){

		}

	}

	public static void checkRemove(){
		System.out.println("\n\n####    Testing Remove Methods\n");

		try{
			System.out.println("Testing Remove Specific on existing entry");
			testSet5.add("A");
			testSet5.add("B");
			testSet5.add("C");
			testSet5.add("D");
			testSet5.add("E");

			if(testSet5.remove("E")){
				System.out.println("\tPassed Remove");
			} else {
				System.out.println("****    Failed Remove -- Wrong Boolean");
			}
			if(!testSet5.contains("E")){
				System.out.println("\tPassed Removed Check");
			} else {
				System.out.println("****    Failed Remove -- Item still in set");
			}
		} catch(java.lang.IllegalArgumentException e){
		} catch(SetFullException e){
		}

		try{
			System.out.println("Testing Remove Specific on non-existing entry");
			testSet6.add("A");
			testSet6.add("B");
			testSet6.add("C");
			testSet6.add("D");
			testSet6.add("E");

			if(!testSet5.remove("F")){
				System.out.println("\tPassed Remove");
			} else {
				System.out.println("****    Failed Remove -- Wrong Boolean");
			}
			if(!testSet5.contains("F")){
				System.out.println("\tPassed Removed Check");
			} else {
				System.out.println("****    Failed Remove -- Item still in set");
			}
		} catch(java.lang.IllegalArgumentException e){
		} catch(SetFullException e){
		}

		try{
			System.out.println("Testing Remove any on nonempty list");
			testSet6.add("A");
			testSet6.add("B");
			testSet6.add("C");
			testSet6.add("D");
			testSet6.add("E");

			String removed = testSet6.remove();

			if(removed != null){
				System.out.println("\tPassed Remove");
			} else {
				System.out.println("****    Failed Remove -- null value");
			}
			if(!testSet5.contains(removed)){
				System.out.println("\tPassed Removed Check");
			} else {
				System.out.println("****    Failed Remove -- Item still in set");
			}
		} catch(java.lang.IllegalArgumentException e){
		} catch(SetFullException e){
		}

		try{
			System.out.println("Testing Remove any on empty list");
			testSet6.add("A");
			testSet6.add("B");
			testSet6.add("C");
			testSet6.add("D");
			testSet6.add("E");

			String removed = testSet6.remove();

			if(removed == null){
				System.out.println("\tPassed Remove");
			} else {
				System.out.println("****    Failed Remove -- not a null value");
			}
			if(!testSet5.contains(removed)){
				System.out.println("\tPassed Removed Check");
			} else {
				System.out.println("****    Failed Remove -- Item still in set");
			}
		} catch(java.lang.IllegalArgumentException e){
		} catch(SetFullException e){
		}
	}

}