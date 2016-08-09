import java.io.Serializable;

public class Profile implements ProfileInterface, Serializable {

	private String name;
	private String aboutMe;

	private Set<ProfileInterface> following;

	public Profile(){
		this("","");
	}

	public Profile(String name, String aboutMe){
		this.name = (name == null) ? "":name;
		this.aboutMe = (aboutMe == null) ? "":aboutMe;
		following = new Set<ProfileInterface>();
	}

	public void setName(String newName) throws java.lang.IllegalArgumentException{
		if(newName == null)
			throw new java.lang.IllegalArgumentException("Null Object Entry");

		name = newName;
		return;
	}

	public String getName(){
		return name;
	}

	public void setAbout(String newAbout) throws java.lang.IllegalArgumentException{
		if(newAbout == null)
			throw new java.lang.IllegalArgumentException("Null Object Entry");

		aboutMe = newAbout;
		return;
	}

	public String getAbout(){
		return aboutMe;
	}

	public boolean follow(ProfileInterface other){
		try{
			if(following.add(other)){
				return true;
			} else {
				return false;
			}
		} catch(java.lang.IllegalArgumentException e) {
			return false;
		} catch(SetFullException e){
			return false;
		}
	}

	public boolean unfollow(ProfileInterface other){
		try{
			if(following.remove(other)){
				return true;
			} else {
				return false;
			}
		} catch (java.lang.IllegalArgumentException e){
			return false;
		}
	}

	public ProfileInterface[] following(int howMany){
		int amt = (howMany < following.getCurrentSize()) ? howMany : following.getCurrentSize();
		ProfileInterface[] f = new ProfileInterface[amt];
		Object[] c = following.toArray();
		for(int i=0; i<amt; i++){
			f[i] = following.remove();
		}
		for(int i=0; i<amt; i++){
			try{following.add(f[i]);}
			catch (java.lang.IllegalArgumentException e) {System.err.println("Failed: Null, " + e);}
			catch (SetFullException e) {System.err.println("Failed: Full" + e);}
		}
		return f;
	}

	public ProfileInterface recommend(){
		boolean state = false;
		ProfileInterface found = null;
		ProfileInterface[] f = following(following.getCurrentSize());
		int fSize = following.getCurrentSize();
		for(int i=0; i<fSize; i++){
			Set<ProfileInterface> s = ((Profile)f[i]).following;
			ProfileInterface[] temp = new ProfileInterface[s.getCurrentSize()];
			int size = s.getCurrentSize();
			for(int j=0; j<size; j++){
				temp[j] = s.remove();
				if(!following.contains(temp[j]) && !state && !(following == temp[j])){
					found = temp[j];
					state = true;
				}

			}

			for(int j=0; j<size; j++){
				try{s.add(temp[j]);} catch(SetFullException e){}
			}
		}
		return found;
	}
}