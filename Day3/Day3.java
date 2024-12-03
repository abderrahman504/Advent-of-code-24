
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
	
	private static void readInput(String file_path, StringBuilder out_string)
	{
		try(Scanner scn = new Scanner(new File(file_path))){
			while(scn.hasNextLine()){
				out_string.append(scn.nextLine());
				out_string.append("\n");
			}
		}
		catch (Exception e){
			System.out.println("Error : " + e.getMessage());
		}
	}

	static void part1()
	{
		StringBuilder input = new StringBuilder();
		readInput("Day3/input", input);
		Pattern mul_pat = Pattern.compile("mul\\(\\d+,\\d+\\)");
		Matcher matcher = mul_pat.matcher(input);
		int result = 0;
		while(matcher.find()){
			String match = matcher.group();
			String[] nums = match.substring(4, match.length()-1).split(",");
			int x = Integer.parseInt(nums[0]);
			int y = Integer.parseInt(nums[1]);
			result += x * y;
		}

		System.out.println(result);
	}

	static void part2()
	{
		StringBuilder input = new StringBuilder();
		readInput("Day3/input", input);
		Pattern mul_pat = Pattern.compile("mul\\(\\d+,\\d+\\)|don't\\(\\)|do\\(\\)");
		Matcher matcher = mul_pat.matcher(input);
		boolean enabled = true;
		int result = 0;
		while(matcher.find()){
			String match = matcher.group();
			if(match.equals("do()")) enabled = true;
			else if (match.equals("don't()")) enabled = false;
			else if (enabled){
				String[] nums = match.substring(4, match.length()-1).split(",");
				int x = Integer.parseInt(nums[0]);
				int y = Integer.parseInt(nums[1]);
				result += x * y;
			}
		}

		System.out.println(result);
	}
}
