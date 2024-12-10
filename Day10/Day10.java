import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Day10 {
	
	private static String[] readInput(String file_path){
		try(Scanner scn = new Scanner(new File(file_path))){
			ArrayList<String> grid = new ArrayList<String>();
			while(scn.hasNextLine()){
				grid.add(scn.nextLine());
			}
			return grid.toArray(String[]::new);
		}
		catch(Exception e){ 
			System.out.println(e); 
			return new String[0];
		}
	}

	static void solve(boolean part1){
		String[] grid = readInput("Day10/input");
		ArrayList<int[]> start_list = new ArrayList<int[]>();
		for(int i=0; i<grid.length; i++) for(int j=0; j<grid[0].length(); j++){
			if(grid[i].charAt(j) == '0') start_list.add(new int[]{i, j});
		}
		
		int count = 0;
		for(var p : start_list){
			var visited = new HashMap<Integer, HashSet<Integer>>();
			count += visit(p, grid, visited, part1);
		}

		System.out.println(count);
	}


	static int visit(int[] p, String[] grid, HashMap<Integer, HashSet<Integer>> visited, boolean part1){
		//visited.get(p[0]).add(p[1]);
		if(grid[p[0]].charAt(p[1]) == '9') return 1;
		//Don't visit new points that were visited already
		var adjacents = getValidAdjacents(p, grid);
		int count = 0;
		for (var adj : adjacents){
			// Don't use the visited map if in part 2
			if(part1){
				if(!visited.containsKey(adj[0])) visited.put(adj[0], new HashSet<Integer>());
				if(visited.get(adj[0]).add(adj[1])) count += visit(adj, grid, visited, true);
			}
			else count += visit(adj, grid, visited, false);
		}
		return count;
	}

	static ArrayList<int[]> getValidAdjacents(int[] p, String[] grid){
		var adjacents = new ArrayList<int[]>();
		int[] l, r, u, d;
		l = new int[]{p[0], p[1]-1};
		r = new int[]{p[0], p[1]+1};
		u = new int[]{p[0]-1, p[1]};
		d = new int[]{p[0]+1, p[1]};
		char c = grid[p[0]].charAt(p[1]);
		if(l[1] >= 0 && grid[l[0]].charAt(l[1]) - c == 1) adjacents.add(l);
		if(r[1] < grid[0].length() && grid[r[0]].charAt(r[1]) - c == 1) adjacents.add(r);
		if(u[0] >= 0 && grid[u[0]].charAt(u[1]) - c == 1) adjacents.add(u);
		if(d[0] < grid.length && grid[d[0]].charAt(d[1]) - c == 1) adjacents.add(d);
		return adjacents;
	}
}
