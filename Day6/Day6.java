import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day6 {
	
	final static int up = 0, right = 1, down = 2, left = 3;
	
	private static char[][] readInput(String file_path)
	{
		try{
			List<String> lines = Files.readAllLines(Paths.get(file_path));
			return lines.stream().map(String::toCharArray).toArray(char[][]::new);
		}
		catch(Exception e){
			System.out.println(e);
			return new char[0][0];
		}
	}

	private static int turnGuard(int dir) { return (dir+1)%4; }

	private static int[] nextPos(int dir, int x, int y){
		switch(dir){
			case up:
			return new int[] {x, y-1};
			case left:
			return new int[] {x-1, y};
			case down:
			return new int[] {x, y+1};
			default:
			return new int[] {x+1, y};	
		}
	}

	static void part1()
	{
		char[][] map = readInput("Day6/input");
		// find guard position
		int x, y=0;
		int dir = 0;
		LocatingGuard:
		for(x = 0; x < map[0].length; x++){
			for(y = 0; y < map.length; y++){
				if(map[y][x] != '.' && map[y][x] != '#'){
					char c = map[y][x];
					dir = c == '^' ? up : c == 'v' ? down : c == '<' ? left : right;
					break LocatingGuard;
				}
			}
		}
		int count = 1;
		map[y][x] = 'X';

		// Trace steps
		int[] next = nextPos(dir, x, y);
		while(next[0] < map[0].length && next[0] >= 0 && next[1] < map.length && next[1] >= 0){
			if(map[next[1]][next[0]] == '#') dir = turnGuard(dir);
			else{
				x = next[0]; y = next[1];
				count += map[y][x] == 'X' ? 0 : 1;
				map[y][x] = 'X';
			}
			next = nextPos(dir, x, y);
		}

		System.out.println(count);
	}

	static void part2()
	{
		char[][] map = readInput("Day6/input");
		// find guard position
		int x, y=0;
		int dir = 0;
		LocatingGuard:
		for(x = 0; x < map[0].length; x++){
			for(y = 0; y < map.length; y++){
				if(map[y][x] != '.' && map[y][x] != '#'){
					char c = map[y][x];
					dir = c == '^' ? up : c == 'v' ? down : c == '<' ? left : right;
					break LocatingGuard;
				}
			}
		}
		ArrayList<int[]> original_trace = new ArrayList<int[]>();
		tracePath(map, x, y, dir, original_trace);
		int count = 0;
		HashSet<Integer> examined = new HashSet<Integer>();
		for(int i=1; i<original_trace.size(); i++){
			int test_x = original_trace.get(i)[0], test_y = original_trace.get(i)[1];
			if(!examined.add(test_y*map.length+test_x)) continue;
			char[][] cpy = new char[map.length][map[0].length];
			// copy map contents
			for(int j=0; j<map.length; j++){
				System.arraycopy(map[j], 0, cpy[j], 0, map[0].length);
			}
			cpy[test_y][test_x] = '#';
			var trace = new ArrayList<int[]>();
			count += tracePath(cpy, x, y, dir, trace) ? 1 : 0;
		}
		System.out.println(count);
	}

	// The return value is used to check if the path loops
	private static boolean tracePath(char[][] map, int x, int y, int dir, ArrayList<int[]> out_trace){
		HashMap<Integer, HashSet<Integer>> visited = new HashMap<Integer, HashSet<Integer>>();
		visited.put(y*map.length+x, new HashSet<Integer>());
		visited.get(y*map.length+x).add(dir);

		// Trace steps
		out_trace.add(new int[]{x,y});
		int[] next = nextPos(dir, x, y);
		while(next[0] < map[0].length && next[0] >= 0 && next[1] < map.length && next[1] >= 0){
			if(map[next[1]][next[0]] == '#') dir = turnGuard(dir);
			else{
				x = next[0]; y = next[1];
				out_trace.add(new int[]{x,y});
				if(!visited.containsKey(y*map.length+x)) visited.put(y*map.length+x, new HashSet<Integer>());
				if(!visited.get(y*map.length+x).add(dir)) return true;
			}
			next = nextPos(dir, x, y);
		}

		return false;
	}


}
