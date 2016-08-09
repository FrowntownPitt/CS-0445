import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class SocialClient {
    
    static String userFile = "clients.bin";
	static UserData userData = null;
    static SocialClient user = new SocialClient();

	public static boolean restore(String filename) {
		try {
			ObjectInputStream restoreStream = new ObjectInputStream(new FileInputStream(filename));
			userData = (UserData)restoreStream.readObject();
		}
		catch(FileNotFoundException e) {
			System.err.println(filename + " does not exist.");
			return false;
		}
		catch(IOException e) {
			System.err.println("Error restoring from " + filename);
			return false;
		}
		catch(ClassNotFoundException e) {
			System.err.println("Error restoring from " + filename);
			return false;
		}
        return true;
    }

    public static boolean save(String filename){
        try {
            ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(filename));
            
            System.out.println("Saving data....");

        	saveStream.writeObject(userData);

        	System.out.println("Finished.");
        }
        catch(IOException e) {
            System.err.println("Something went wrong saving to " + filename);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static void init(){
    	userData = new UserData();
    }

    public String toString(){
    	return userData.toString();
    }

    public static void main(String[] args){

    	if(user.restore(userFile)){
    		//System.out.println("Restored from " + userFile + ", found:");
    		//System.out.println(user);
    	} else {
            System.out.println("Initializing the file");
            user.init();
            System.out.println(user);
            System.out.println("Saving data...");
            user.save(userFile);
        }

        prompt();

    }

    public static void prompt(){
    	while(true){
	    	System.out.print("Welcome to the Profile Viewer.  Make a selection:\n\n"+
	        	"\t1. List the profiles that currently exist\n"+
	        	"\t2. Create a new profile\n" +
	        	"\t3. Show profile information\n" + 
	        	"\t4. Edit Profile\n" +
	        	"\t5. Follow\n" +
	        	"\t6. Unfollow\n" +
	        	"\t7. Receive a suggestion to follow\n" +
	        	"\t8. Quit\n\n"+
	        	"Selection: ");

	        Scanner input = new Scanner(System.in);
	        int selection = input.nextInt();

	        switch(selection){
	        	case 1:
	        		listProfiles();
	        		break;
	        	case 2:
	        		createProfile();
	        		break;
	        	case 3:
	        		showProfile();
	        		break;
	        	case 4:
	        		editProfile();
	        		break;
	        	case 5:
	        		followProfile();
	        		break;
	        	case 6:
	        		unfollowProfile();
	        		break;
	        	case 7:
	        		suggest();
	        		break;
	        	case 8:
	        		quit();
	        		return;
	        	default:
	        		System.out.println("You made an invalid entry.  Please try one of the options specified");
	        		break;
	        }
	        System.out.println("\n-----\n");
        }
    }

    public static void listProfiles(){
    	Set<Profile> temp = new Set<Profile>();
    	Profile t;
    	int size = userData.getCurrentSize();
    	System.out.println("\nProfiles:");
    	for(int i=0; i<size; i++){
    		t = userData.remove();
    		try{temp.add(t);} catch(SetFullException e){} catch(java.lang.IllegalArgumentException e){}
    		System.out.println("\t"+(i+1)+". "+t.getName());
    	}
    	for(int i=0; i<size; i++){
    		try{userData.add(temp.remove());} catch(SetFullException e){} catch(java.lang.IllegalArgumentException e){}
    	}
    	return;
    }

    public static void createProfile(){
    	System.out.print("Enter the name for this profile: ");
    	Scanner input = new Scanner(System.in);
    	String name = input.nextLine();

    	System.out.print("Enter the bio for this profile: ");
    	String aboutMe = input.nextLine();

    	try{userData.add(new Profile(name, aboutMe));}
    	catch (SetFullException e){}
    	catch (java.lang.IllegalArgumentException e){
    		System.out.println("You entered an illegal value.  Please reconsider");}
    	return;
    }

    public static void showProfile(){
    	System.out.print("Choose which profile to view: ");
    	Scanner input = new Scanner(System.in);
    	int choice = input.nextInt();
    	Set<Profile> temp = new Set<Profile>();
    	Profile t = new Profile();
    	if(choice > 0 && choice <= userData.getCurrentSize()){
    		for(int i=0; i<choice; i++){
    			t = userData.remove();
    			try{temp.add(t);} catch(SetFullException e){} catch(java.lang.IllegalArgumentException e){}
    		}
    		//System.out.println(t.getName() + " : " + t.getAbout());
    		for(int i=0; i<choice; i++){
    			try{userData.add(temp.remove());} catch(SetFullException e){} catch(java.lang.IllegalArgumentException e){}
    		}
    	} else {
    		System.out.println("That was an invalid selection.");
    		return;
    	}

    	System.out.println("Name: " + t.getName() + "\n" +
    						"Bio: " + t.getAbout() + "\n" + 
    						"Following:");
    	ProfileInterface[] f = t.following(4);
    	for(ProfileInterface p:f){
    		System.out.println("\tName: "+p.getName()+
    							"\n\t Bio: " + p.getAbout());
    	}
    	return;
    }

    public static Profile getProfile(int choice){
    	Set<Profile> temp = new Set<Profile>();
    	Profile t = new Profile();
    	if(choice > 0 && choice <= userData.getCurrentSize()){
    		for(int i=0; i<choice; i++){
    			t = userData.remove();
    			try{temp.add(t);} catch(SetFullException e){} catch(java.lang.IllegalArgumentException e){}
    		}
    		//System.out.println(t.getName() + " : " + t.getAbout());
    		for(int i=0; i<choice; i++){
    			try{userData.add(temp.remove());} catch(SetFullException e){} catch(java.lang.IllegalArgumentException e){}
    		}
    		return t;
    	}
    	return null;
    }

    public static void editProfile(){
    	System.out.print("Choose the profile to edit: ");
    	Scanner input = new Scanner(System.in);
    	int choice = input.nextInt();

    	if(choice < 1 || choice > userData.getCurrentSize()){
    		System.out.println("That was an invalid selection.");
    		return;
    	}

    	Profile selection = getProfile(choice);

    	input.nextLine();

    	System.out.print("Enter the new name: ");
    	String name = input.nextLine();
    	System.out.print("Enter the new bio: ");
    	String about = input.nextLine();
    	selection.setName(name);
    	selection.setAbout(about);
    	return;
    }

    /** 
      * param option  Verbose input.  0 will prompt user, other int will select 
      */
    public static void followProfile(){
    	System.out.print("Select which profile would you like to make follow another: ");
    	Scanner input = new Scanner(System.in);

    	int first = input.nextInt();

    	System.out.print("Select which profile to be followed: ");

    	int second = input.nextInt();

    	Profile t = getProfile(first);
    	t.follow(getProfile(second));

    	return;
    }

    public static void unfollowProfile(){
    	System.out.print("Select which profile would you like to make unfollow another: ");
    	Scanner input = new Scanner(System.in);

    	int first = input.nextInt();

    	System.out.print("Select which profile to be unfollowed: ");

    	int second = input.nextInt();

    	Profile t = getProfile(first);
    	t.unfollow(getProfile(second));
    	return;
    }

    public static void suggest(){
    	System.out.print("Select which profile would you like to receive a selection: ");
    	Scanner input = new Scanner(System.in);

    	int first = input.nextInt();

    	Profile t = getProfile(first);
    	Profile r = (Profile) t.recommend();

    	if(r != null){
	    	System.out.println("Name: " + r.getName() + "\n Bio: " + r.getAbout());

	    	input.nextLine();

	    	System.out.println("Follow? [Y/N]");

	    	String choice = input.nextLine();
	    	if(choice.equals("Y")){
	    		t.follow(r);
	    	}
	    } else {
	    	System.out.println("No suggestions were found");
	    }
    	return;
    }

    public static void quit(){
    	user.save(userFile);
    	return;
    }

    static class UserData implements Serializable {
    	Set<Profile> users = new Set<Profile>();

    	UserData() {
    	}

    	public int getCurrentSize(){
    		return users.getCurrentSize();
    	}

    	public boolean isEmpty(){
    		return users.isEmpty();
    	}

    	public boolean add(Profile newEntry) throws java.lang.IllegalArgumentException, SetFullException{
    		try{
    			return users.add(newEntry);
    		}
    		catch (SetFullException e){throw new java.lang.IllegalArgumentException(e);}
    		catch (java.lang.IllegalArgumentException e){throw new java.lang.IllegalArgumentException(e);}
    	}

    	public boolean remove(Profile entry) throws java.lang.IllegalArgumentException{
    		try{
    			return users.remove(entry);
    		}
    		catch (java.lang.IllegalArgumentException e){throw new java.lang.IllegalArgumentException(e);}
    	}

    	public Profile remove(){
    		return users.remove();
    	}

    	public String toString() {
    		return users.toString();
    	}

    	public void clear(){
    		users.clear();
    	}

    	public boolean contains(Profile entry) throws java.lang.IllegalArgumentException{
    		try{
    			return users.contains(entry);
    		} catch(java.lang.IllegalArgumentException e){throw new java.lang.IllegalArgumentException(e);}
    	}

    	public Profile[] toArray(){
    		return users.toArray();
    	}
    }

}