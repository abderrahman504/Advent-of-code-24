import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Day4 {
	private static char[][] readInput(String file_path)
	{
		ArrayList<ArrayList<Character>> input = new ArrayList<ArrayList<Character>>();
		try (Scanner scn = new Scanner(new File(file_path))){
			while (scn.hasNextLine()){
				char[] str = scn.nextLine().toCharArray();
				ArrayList<Character> line = new ArrayList<Character>(str.length);
				for (var c : str) line.add(c);
				input.add(line);
			}
		}
		catch (Exception e){
			System.out.println(e);
		}
		char[][] ret = new char[input.size()][input.get(0).size()];
		for(int i=0; i<ret.length; i++){
			for(int j=0; j<ret[0].length; j++){
				ret[i][j] = input.get(i).get(j);
			}
		}
		return ret;
	}

	static void part1()
	{
		char[][] input = readInput("Day4/input");
		int count = 0;
		int width = input[0].length;
		int height = input.length;

		for (int i=0; i<height; i++){
			for (int j=0; j<width; j++){
				if (input[i][j] == 'X') count += findXMASAt(i, j, input);
			}
		}
		System.out.println(count);
	}

	private static int findXMASAt(int x, int y, char[][] input){
		
		int count = 0;
		//Search each of the cardinal and diagonal directions
		if(y >= 3 && input[x][y-1] == 'M' && input[x][y-2] == 'A' && input[x][y-3] == 'S') count++;
		if(y < input.length-3 && input[x][y+1] == 'M' && input[x][y+2] == 'A' && input[x][y+3] == 'S') count++;
		if(x >= 3 && input[x-1][y] == 'M' && input[x-2][y] == 'A' && input[x-3][y] == 'S') count++;
		if(x < input[0].length-3 && input[x+1][y] == 'M' && input[x+2][y] == 'A' && input[x+3][y] == 'S') count++;
		
		if(y >= 3 && x >= 3 && input[x-1][y-1] == 'M' && input[x-2][y-2] == 'A' && input[x-3][y-3] == 'S') count++;
		if(y < input.length-3 && x >= 3 && input[x-1][y+1] == 'M' && input[x-2][y+2] == 'A' && input[x-3][y+3] == 'S') count++;
		if(y >= 3 && x < input[0].length-3 && input[x+1][y-1] == 'M' && input[x+2][y-2] == 'A' && input[x+3][y-3] == 'S') count++;
		if(y < input.length-3 && x < input[0].length-3 && input[x+1][y+1] == 'M' && input[x+2][y+2] == 'A' && input[x+3][y+3] == 'S') count++;

		return count;
	}

	static void part2()
	{
		char[][] input = readInput("Day4/input");
		int count = 0;
		int width = input[0].length;
		int height = input.length;

		for (int i=1; i<height-1; i++){
			for (int j=1; j<width-1; j++){
				if (input[i][j] == 'A') count += findX_MASAt(i, j, input);
			}
		}
		System.out.println(count);
	}

	private static int findX_MASAt(int x, int y, char[][] input)
	{
		int count = 0;

		if((input[x-1][y-1] == 'M' && input[x+1][y+1] == 'S') || (input[x-1][y-1] == 'S' && input[x+1][y+1] == 'M')) count++;
		if((input[x-1][y+1] == 'M' && input[x+1][y-1] == 'S') || (input[x-1][y+1] == 'S' && input[x+1][y-1] == 'M')) count++;
		

		return count/2;
	}
}
