import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Day8 {
	
	private static String[] readInput(String file_path){
		try(Scanner scn = new Scanner(new File(file_path))){
			var arr = new ArrayList<String>();
			while(scn.hasNextLine()){
				arr.add(scn.nextLine());
			}
			return arr.toArray(String[]::new);
		}
		catch(Exception e){
			return null;
		}
	}

	static void part1(){
		String[] input = readInput("Day8/input");
		HashMap<Character, ArrayList<int[]>> antennas_map = new HashMap<Character, ArrayList<int[]>>();
		for(int i=0; i<input.length; i++){
			for(int j=0; j<input[0].length(); j++){
				char c = input[i].charAt(j);
				if((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9')) continue;
				if(!antennas_map.containsKey(c)) antennas_map.put(c, new ArrayList<int[]>());
				antennas_map.get(c).add(new int[]{i, j});
			}
		}

		
		int[] input_size = new int[]{input.length, input[0].length()};
		HashMap<Integer, HashSet<Integer>> found_antinodes = new HashMap<Integer, HashSet<Integer>>();
		// For each different frequency (character)
		for(var entry : antennas_map.entrySet()){
			var points = entry.getValue();
			// Take each pair of locations and get their antinodes
			for(int i=0; i<points.size()-1; i++){
				for(int j=i+1; j<points.size(); j++){
					int[] p1 = getAntinodePT1(points.get(i), points.get(j));
					int[] p2 = getAntinodePT1(points.get(j), points.get(i));
					if(withinBounds(p1, input_size)) addAntinode(p1, found_antinodes);
					if(withinBounds(p2, input_size)) addAntinode(p2, found_antinodes);
				}
			}
		}

		System.out.println(countAntinodes(found_antinodes));
	}

	static void part2(){
		String[] input = readInput("Day8/input");
		HashMap<Character, ArrayList<int[]>> antennas_map = new HashMap<Character, ArrayList<int[]>>();
		for(int i=0; i<input.length; i++){
			for(int j=0; j<input[0].length(); j++){
				char c = input[i].charAt(j);
				if((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9')) continue;
				if(!antennas_map.containsKey(c)) antennas_map.put(c, new ArrayList<int[]>());
				antennas_map.get(c).add(new int[]{i, j});
			}
		}

		
		int[] input_size = new int[]{input.length, input[0].length()};
		HashMap<Integer, HashSet<Integer>> found_antinodes = new HashMap<Integer, HashSet<Integer>>();
		// For each different frequency (character)
		for(var entry : antennas_map.entrySet()){
			var points = entry.getValue();
			// Take each pair of locations and get their antinodes
			for(int i=0; i<points.size()-1; i++){
				for(int j=i+1; j<points.size(); j++){
					ArrayList<int[]> arr = getAntinodesPT2(points.get(i), points.get(j), input_size);
					for(var p : arr){
						addAntinode(p, found_antinodes);
					}
				}
			}
		}

		System.out.println(countAntinodes(found_antinodes));
	}


	private static boolean withinBounds(int[] loc, int[] size){ return loc[0] < size[0] && loc[0] >= 0 && loc[1] < size[1] && loc[1] >= 0; }


	private static int[] getAntinodePT1(int[] loc1, int[] loc2){
		int[] v = new int[]{loc2[0] - loc1[0], loc2[1] - loc1[1]};
		return new int[]{loc2[0] + v[0], loc2[1] + v[1]};
	}


	private static ArrayList<int[]> getAntinodesPT2(int[] loc1, int[] loc2, int[] grid_size){
		var arr = new ArrayList<int[]>();
		int[] v = new int[]{loc2[0] - loc1[0], loc2[1] - loc1[1]};
		// divide v by the gcd of v[0] and v[1]
		int gcd = gcd(v[0], v[1]);
		v[0] /= gcd;
		v[1] /= gcd;
		int i = 0;
		while(true){
			int[] p = new int[]{loc2[0] + i*v[0], loc2[1] + i*v[1]};
			if(!withinBounds(p, grid_size)) break;
			arr.add(p);
			i++;
		}
		i = -1;
		while(true){
			int[] p = new int[]{loc2[0] + i*v[0], loc2[1] + i*v[1]};
			if(!withinBounds(p, grid_size)) break;
			arr.add(p);
			i--;
		}
		return arr;
	}


	private static int gcd(int a, int b) {
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}


	private static void addAntinode(int[] loc, HashMap<Integer, HashSet<Integer>> anitnodes){
		if(!anitnodes.containsKey(loc[0])) anitnodes.put(loc[0], new HashSet<Integer>());
		anitnodes.get(loc[0]).add(loc[1]);
	}


	private static int countAntinodes(HashMap<Integer, HashSet<Integer>> anitnodes){
		int sum = 0;
		for(var entry : anitnodes.entrySet()){
			sum += entry.getValue().size();
		}
		return sum;
	}
}
