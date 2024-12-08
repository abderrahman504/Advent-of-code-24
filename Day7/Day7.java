import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day7 {

	private static void readInput(String file_path, ArrayList<Long> out_targets, ArrayList<ArrayList<Long>> out_arrays)
	{
		try(Scanner scn = new Scanner(new File(file_path))){
			while(scn.hasNextLine()){
				String line = scn.nextLine();
				String[] split = line.split(": ");
				out_targets.add(Long.parseLong(split[0]));
				ArrayList<Long> arr = new ArrayList<Long>();
				String[] nums = split[1].split(" ");
				for(var num : nums) arr.add(Long.parseLong(num));
				out_arrays.add(arr);
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	static void part1(){
		ArrayList<Long> targets = new ArrayList<Long>();
		ArrayList<ArrayList<Long>> arrays = new ArrayList<ArrayList<Long>>();
		readInput("Day7/input", targets, arrays);

		int sum = 0;
		ArrayList<Integer> accepted = new ArrayList<Integer>();
		for(int i=0; i<targets.size(); i++){
			HashMap<Long, Boolean[]> memos = new HashMap<Long, Boolean[]>();
			if(solve(0, targets.get(i), 0, arrays.get(i), memos)){
				sum += targets.get(i);
				accepted.add(i);
			}
		}
		System.out.println(sum);
	}

	private static boolean solve(long accum, long target, int i, ArrayList<Long> array, HashMap<Long, Boolean[]> memos)
	{
		if(!memos.containsKey(accum)) memos.put(accum, new Boolean[array.size()+1]);
		// If already solved then return solution
		if(memos.get(accum)[i] != null) return memos.get(accum)[i];
		// Base case
		if(i == array.size()){
			return accum == target;
		}

		memos.get(accum)[i] = solve(accum+array.get(i), target, i+1, array, memos) || solve(accum*array.get(i), target, i+1, array, memos);
		return memos.get(accum)[i];
	}
}
