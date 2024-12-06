import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntUnaryOperator;

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


}
