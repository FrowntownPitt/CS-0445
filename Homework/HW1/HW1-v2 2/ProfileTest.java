public class ProfileTest {

	static ProfileInterface userA = (ProfileInterface) new Profile("A","About Me");

	public static void main(String[] args){
		ProfileInterface userB = (ProfileInterface) new Profile("B", "B: About Me");
		ProfileInterface userC = (ProfileInterface) new Profile("C", "C: About Me");
		ProfileInterface userD = (ProfileInterface) new Profile("D", "D: About Me");
		userA.follow(userB);
		userA.follow(userC);
		userB.follow(userD);

	}
}