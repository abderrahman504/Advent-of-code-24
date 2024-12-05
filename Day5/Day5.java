import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class Day5 {
	private static void readInput(String file_path, HashMap<Integer, HashSet<Integer>> out_prerequisites, ArrayList<ArrayList<Integer>> out_updates)
	{
		try(Scanner scn = new Scanner(new File(file_path))){
			while(scn.hasNextLine()){
				String line = scn.nextLine();
				if(line.split("\\|").length == 2){
					int x = Integer.parseInt(line.split("\\|")[0]);
					int y = Integer.parseInt(line.split("\\|")[1]);
					if(!out_prerequisites.containsKey(x)) out_prerequisites.put(x, new HashSet<Integer>());
					if(!out_prerequisites.containsKey(y)) out_prerequisites.put(y, new HashSet<Integer>()); 
					out_prerequisites.get(y).add(x);
				}
				else if(line.split(",").length > 1){
					String[] nums = line.split(",");
					ArrayList<Integer> update = new ArrayList<Integer>();
					for(var num : nums){
						update.add(Integer.parseInt(num));
					}
					out_updates.add(update);
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	static void part1()
	{
		HashMap<Integer, HashSet<Integer>> prerequisites = new HashMap<Integer, HashSet<Integer>>();
		ArrayList<ArrayList<Integer>> updates = new ArrayList<ArrayList<Integer>>();
		readInput("Day5/input", prerequisites, updates);
		ArrayList<Integer> correct = new ArrayList<Integer>();
		UpdatesLoop:
		for(int i=0; i<updates.size(); i++){
			var update = updates.get(i);
			HashSet<Integer> processed = new HashSet<Integer>();
			
			for(var num : update){
				for(var required : prerequisites.get(num)){
					// If a required value is in the update but not processed yet then reject the update
					if(update.contains(required) && !processed.contains(required)){
						continue UpdatesLoop;
					}
				}
				processed.add(num);
			}
			correct.add(i);
		}

		int sum = 0;
		for (var x : correct){
			sum += updates.get(x).get(updates.get(x).size()/2);
		}
		System.out.println(sum);
	}


	static void part2()
	{
		HashMap<Integer, HashSet<Integer>> prerequisites = new HashMap<Integer, HashSet<Integer>>();
		ArrayList<ArrayList<Integer>> updates = new ArrayList<ArrayList<Integer>>();
		readInput("Day5/input", prerequisites, updates);
		ArrayList<ArrayList<Integer>> incorrect = new ArrayList<ArrayList<Integer>>();
		UpdatesLoop:
		for(int i=0; i<updates.size(); i++){
			var update = updates.get(i);
			HashSet<Integer> processed = new HashSet<Integer>();
			
			for(var num : update){
				for(var required : prerequisites.get(num)){
					// If a required value is in the update but not processed yet then reject the update
					if(update.contains(required) && !processed.contains(required)){
						incorrect.add(update);
						continue UpdatesLoop;
					}
				}
				processed.add(num);
			}
		}

		var corrected = fixUpdates(incorrect, prerequisites);

		int sum = 0;
		for (var x : corrected){
			sum += x.get(x.size()/2);
		}
		System.out.println(sum);
	}

	private static ArrayList<ArrayList<Integer>> fixUpdates(ArrayList<ArrayList<Integer>> updates, HashMap<Integer, HashSet<Integer>> prerequisites)
	{
		var corrected = new ArrayList<ArrayList<Integer>>();
		for(var update : updates){
			corrected.add(topologicalSort(update, prerequisites));
		}
		return corrected;
	}

	private static ArrayList<Integer> topologicalSort(ArrayList<Integer> update, HashMap<Integer, HashSet<Integer>> prerequisites)
	{
		var sorted = new ArrayList<Integer>();
		var visited = new HashSet<Integer>();
		var finished = new HashSet<Integer>();
		var stack = new Stack<Integer>();
		
		for(var num : update){
			stack.push(num);
			while(!stack.isEmpty()){
				int node = stack.peek();
				if(finished.contains(node)){
					stack.pop();
					continue;
				}
				if(visited.contains(node)){
					sorted.add(node);
					finished.add(node);
					stack.pop();
				}
				else{
					visited.add(node);
					for(var preq : prerequisites.get(node)){
						if(update.contains(preq) && !finished.contains(preq)) stack.add(preq);
					}
				}
			}

		} 

		return sorted;
	}

}
