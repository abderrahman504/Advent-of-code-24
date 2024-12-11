import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Day11 {

	private static LinkedList<Long> readInput(String file_path){
		try(Scanner scn = new Scanner(new File(file_path))){
			var list = new LinkedList<Long>();
			while(scn.hasNextLong()) list.add(scn.nextLong());
			return list;
		}
		catch(Exception e){
			return new LinkedList<Long>();
		}
	}

	static void solve(boolean part1)
	{
		var input = readInput("Day11/input");
		//var stk = new Stack<NumBlinkPair>();
		long size = 0;
		int blinks = part1 ? 25 : 75;
		HashMap<Long, long[]> memos = new HashMap<Long, long[]>();
		for(var num : input){
			size += expandElement(num, blinks, memos, blinks);
		}
		//for(var num: input) stk.add(new NumBlinkPair(num, blinks));
		//while(!stk.isEmpty()){
		//	var elem = stk.pop();
		//	if(elem.remaining_blinks == 0) size++;
		//	else processElement(elem, stk);
		//}
		System.out.println(size);
	}
	
	private static long expandElement(long elem, int remaining_blinks, HashMap<Long, long[]> memos, int memo_width)
	{
		if(remaining_blinks == 0) return 1;
		if(!memos.containsKey(elem)){
			long[] arr = new long[memo_width];
			for(int i=0; i<arr.length; i++) arr[i] = -1;
			memos.put(elem, arr);
		}
		if(memos.get(elem)[remaining_blinks-1] != -1) return memos.get(elem)[remaining_blinks-1];

		String str = Long.toString(elem);
		long result;
		// If 0 element
		if(elem == 0){
			result = expandElement(1, remaining_blinks-1, memos, memo_width);
		}
		// If even length number
		else if(str.length()%2 == 0){
			long x, y;
			x = Long.parseLong(str.substring(0, str.length()/2));
			y = Long.parseLong(str.substring(str.length()/2));
			result = expandElement(x, remaining_blinks-1, memos, memo_width) + expandElement(y, remaining_blinks-1, memos, memo_width);
		}
		// Otherwise 
		else{
			result = expandElement(elem*2024, remaining_blinks-1, memos, memo_width);
		}
		memos.get(elem)[remaining_blinks-1] = result;
		return result;
	}

	//private static void processElement(NumBlinkPair elem, Stack<NumBlinkPair> stk)
	//{
	//	String str = Long.toString(elem.num);
	//	elem.remaining_blinks--;
	//	// If 0 element
	//	if (elem.num == 0){
	//		elem.num = 1;
	//		stk.add(elem);
	//	}
	//	// If even length number
	//	else if(str.length()%2 == 0){
	//		long x, y;
	//		x = Long.parseLong(str.substring(0, str.length()/2));
	//		y = Long.parseLong(str.substring(str.length()/2));
	//		elem.num = x;
	//		stk.add(elem);
	//		stk.add(new NumBlinkPair(y, elem.remaining_blinks));
	//	}
	//	// Otherwise 
	//	else{
	//		elem.num *= 2024;
	//		stk.add(elem);
	//	}
	//}

	//static class NumBlinkPair{
	//	long num;
	//	int remaining_blinks;

	//	NumBlinkPair(long num, int remaining_blinks){
	//		this.num = num;
	//		this.remaining_blinks = remaining_blinks;
	//	}
	//}
}