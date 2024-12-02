import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Day1{

	private static void readInput(String input_path, ArrayList<Integer> out_list1, ArrayList<Integer> out_list2)
	{
		try (Scanner scn = new Scanner(new File(input_path))){
			while(scn.hasNextInt())
			{
				out_list1.add(scn.nextInt());
				out_list2.add(scn.nextInt());
			}
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void part1(){
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		readInput("Day1/input", list1, list2);
		Collections.sort(list1);
		Collections.sort(list2);
		int diffs = 0;
		for (int i=0; i<list1.size(); i++){
			diffs += Math.abs(list2.get(i) - list1.get(i));
		}
		System.out.println(diffs);
	}
	
	static void part2()
	{
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		readInput("Day1/input", list1, list2);
		HashMap<Integer, Integer> occurance1 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> occurance2 = new HashMap<Integer, Integer>();
		for (int i=0; i<list1.size(); i++){
			if(occurance1.containsKey(list1.get(i))){
				int x = occurance1.get(list1.get(i)) + 1;
				occurance1.replace(list1.get(i), x);
			}
			else{
				occurance1.put(list1.get(i), 1);
			}
			if(occurance2.containsKey(list2.get(i))){
				int x = occurance2.get(list2.get(i)) + 1;
				occurance2.replace(list2.get(i), x);
			}
			else{
				occurance2.put(list2.get(i), 1);
			}
		}
		int similarity = 0;
		for (var entry : occurance1.entrySet()){
			if (occurance2.containsKey(entry.getKey())){
				int x = entry.getKey();
				similarity += x * entry.getValue() * occurance2.get(x);
			}
		}
		System.out.println(similarity);
	}

}