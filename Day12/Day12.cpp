#include <vector>
#include <unordered_map>
#include <string>
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

struct Region{
	int id;
	int area = 0;
	int parimeter = 0;
	int sides_count = 0;
	
	Region(int id) : id(id){}
	Region() = default;

};

vector<string> readInput(string file_path){
	// Open the file and read lines from it
	vector<string> lines;
	ifstream fh(file_path);
	if(fh.is_open()){
		string line;
		while(getline(fh, line)){
			lines.push_back(line);
		}
	}
	else cout << "Couldn't open " << file_path << endl;
	return lines;
}

char getChar(vector<string>& grid, int i, int j){
	if(i < 0 || i >= grid.size() || j < 0 || j >= grid[0].size()) return -1;
	else return grid[i][j];
}

void addToRegion(int i, int j, vector<string>& input, vector<vector<int>> &markers, Region& region)
{
	if(markers[i][j] != -1) return;
	char c = input[i][j];
	markers[i][j] = region.id;
	region.area++;

	if(c != getChar(input, i-1, j)) region.parimeter++;
	else addToRegion(i-1, j, input, markers, region);
	if(c != getChar(input, i+1, j)) region.parimeter++;
	else addToRegion(i+1, j, input, markers, region);
	if(c != getChar(input, i, j-1)) region.parimeter++;
	else addToRegion(i, j-1, input, markers, region);
	if(c != getChar(input, i, j+1)) region.parimeter++;
	else addToRegion(i, j+1, input, markers, region);
}

enum Dirs{left, down, right, up};

short nextDir(short dir){ return (dir+1)%4; }
short prevDir(short dir){ return (dir+3)%4; }

void getAdjacent(int i, int j, Dirs dir, int& out_i, int& out_j){
	switch(dir){
		case Dirs::left:
			out_i = i-1;
			out_j = j;
		break;
		case Dirs::right:
			out_i = i+1;
			out_j = j;
		break;
		case Dirs::down:
			out_i = i;
			out_j = j+1;
		break;
		case Dirs::up:
			out_i = i;
			out_j = j-1;
		break;
	}
}

int main(int argc, char** argv)
{
	bool part1 = false;
	vector<string> input = readInput("input");
	int height = input.size();
	int width = input[0].size();
	vector<vector<int>> markers(height, vector<int>(width, -1));
	
	vector<Region> regions;

	int region_count = 0;
	for(int i=0; i<height; i++){
		for(int j=0; j<width; j++){
			if(markers[i][j] != -1) continue;
			char c = input[i][j];
			regions.push_back(Region(region_count++));
			addToRegion(i, j, input, markers, regions.back());

		}
	}

	if(part1){
		int result = 0;
		for(auto region : regions){
			result += region.area * region.parimeter;
		}
		cout << result << endl;
		return 0;
	}

	// Part 2
	bool checked_regions[region_count] = {};

	for(int i=0; i<height; i++){
		for(int j=0; j<width; j++){
			if(checked_regions[markers[i][j]]) continue;
			// At this point this position is in an unchecked region
			int start_i = i, start_j = j; // Remember the first position in the region so that we stop when we reach it again.
			int cur_i = i, cur_j = j;
			bool start = true;
			Dirs dir = Dirs::left;
			while(start || cur_i != start_i || cur_j != start_j){

			}

		}
	}

	return 0;
}