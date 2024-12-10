
public class Main{

	// Used to run the problem code for each day.
	
	public static void main(String[] args) {
		var start = System.nanoTime();
		Day10.solve(false);
		var finish = System.nanoTime();
		System.out.printf("Time : %.4f ms", (finish-start) * 1e-6);
	}
}