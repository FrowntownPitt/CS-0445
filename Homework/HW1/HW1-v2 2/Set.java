import java.io.Serializable;

public class Set<T> implements SetInterface<T>, Serializable{

	private T[] set;
	private int numberOfEntries;
	private static final int DEFAULT_CAPACITY = 1;
	private int capacity;

	private boolean initialized = false;

	public Set(){
		this(DEFAULT_CAPACITY);
	}

	public Set(int capacity){
		this.capacity = capacity;
		numberOfEntries = 0;

		@SuppressWarnings("unchecked")
		T[] tempSet = (T[]) new Object[capacity];
		set = tempSet;
		initialized = true;
	}

	private void resize(){
		if(capacity == 0){
			capacity = 1;
		} else {
			capacity *= 2;
		}
		@SuppressWarnings("unchecked")
		T[] newSet = (T[]) new Object[capacity];

		if(capacity != 1){
			for(int i=0; i<numberOfEntries; i++){
				newSet[i] = set[i];
			}
		}
		set = newSet;
	}

	@Override
	public int getCurrentSize(){
		return numberOfEntries;
	}

	@Override
	public boolean isEmpty(){
		return numberOfEntries==0;
	}

	@Override
	public boolean add(T newEntry) throws SetFullException, java.lang.IllegalArgumentException{
		boolean status = false;

		if(newEntry == null)
			throw new java.lang.IllegalArgumentException("Null Object Entry");
		
		numberOfEntries++;

		if(numberOfEntries+1 > capacity)
			resize();
			//throw new SetFullException();
		
		if(!contains(newEntry)){
			set[numberOfEntries-1] = newEntry;
			status = true;
		} else {
			status = false;
			numberOfEntries--;
		}

		return status;
	}

	@Override
	public boolean remove(T entry) throws java.lang.IllegalArgumentException{
		boolean status = false;

		if(entry==null){
			throw new java.lang.IllegalArgumentException("Null Object Entry");
		}

		if(!contains(entry))
			return false;

		for(int i=0; i<numberOfEntries; i++){
			if(entry == set[i]){
				status = true;
				set[i] = set[numberOfEntries];
				set[numberOfEntries] = null;
				numberOfEntries--;
				return true;
			}
		}
		return status;
	}

	@Override
	public T remove(){
		if(isEmpty())
			return null;
		T entry = set[numberOfEntries-1];
		set[numberOfEntries-1] = null;
		numberOfEntries--;
		return entry;
	}

	@Override
	public void clear(){
		if(!isEmpty())
			for(int i=0; i<numberOfEntries; i++){
				set[i] = null;
			}
		numberOfEntries = 0;
		return;
	}

	@Override
	public boolean contains(T entry) throws java.lang.IllegalArgumentException{
		boolean status = false;
		if(isEmpty())
			return false;

		if(entry == null)
			throw new java.lang.IllegalArgumentException("Null Object Entry");
		
		for(T i:set)
			if(entry == i){
				status = true;
				break;
			}

		return status;
	}

	@Override
	public T[] toArray(){
		@SuppressWarnings("unchecked")
		T[] tempSet = (T[]) new  Object[numberOfEntries];
		for(int i=0; i<numberOfEntries; i++){
			tempSet[i] = set[i];
		}
		return tempSet;
	}

	public String toString(){
		String result = "Set{Size:" + numberOfEntries + " ";

		for(T i: toArray()){
			result += "[" + i + "] ";
		}

		result += "}";

		return result;
	}
}