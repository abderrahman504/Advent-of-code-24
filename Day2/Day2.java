import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day2 {
	
	private static void readInput(String file_path, ArrayList<ArrayList<Integer>> out_list)
	{
		try (Scanner scn = new Scanner(new File(file_path))){
			while(scn.hasNextLine())
			{
				ArrayList<Integer> list = new ArrayList<Integer>();
				String[] line = scn.nextLine().split(" ");
				for(String s : line)
					list.add(Integer.parseInt(s));
				out_list.add(list);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	static void part1()
	{
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		readInput("Day2/input", list);
		int safe = 0;
		
		for(int i=0; i<list.size(); i++)
			safe += isSafe(list.get(i)) ? 1 : 0;
		System.out.println(safe);
	}

	static void part2()
	{
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		readInput("Day2/input", list);
		int safe = 0;
		for(int i=0; i<list.size(); i++)
		{
			ArrayList<Integer> report = list.get(i);
			boolean is_safe = true;
			if(!isSafe(report)){
				is_safe = false;
				ArrayList<Integer> cpy = new ArrayList<Integer>(report.size()-1);
				for(int j=0; j<report.size(); j++){
					cpy.clear();
					cpy.addAll(report);
					cpy.remove(j);
					if(isSafe(cpy)){
						is_safe = true;
						break;
					}
				}
			}
			
			
			safe += is_safe ? 1 : 0;
		}
		System.out.println(safe);
	}

	static boolean isSafe(ArrayList<Integer> report)
	{
		boolean is_safe = true;
		int max = 3, min = 1;
		// Get direction of change from first 2 entries. Negative is decreasing, positive is increasing.
		int sign = report.get(0) - report.get(1) > 0 ? -1 : 1;

		for(int j=0; j<report.size()-1; j++){
			int diff = report.get(j+1) - report.get(j);
			// Check if sign of change is different
			if (diff * sign > 0){
				// Check if the difference is within the range
				if (diff * sign <= max && diff * sign >= min) continue;
			}
			is_safe = false;
		}
		return is_safe;
	}
}
