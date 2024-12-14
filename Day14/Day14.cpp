#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <stdio.h>

using namespace std;


struct Vector{
	int x, y;

	Vector(int x, int y) : x(x), y(y) {}
	Vector operator +(Vector other){ return Vector(x+other.x, y+other.y); }
	Vector operator -(Vector other){ return Vector(x-other.x, y-other.y); }
	Vector operator *(int c){ return Vector(x*c, y*c); }
};

vector<Vector> readInput(string file_path)
{
	vector<Vector> data;
	FILE* fh = fopen(file_path.c_str(), "r");

	int px, py, vx, vy;
	int read_data;
	while(fscanf(fh, "p=%d,%d v=%d,%d\n", &px, &py, &vx, &vy) != EOF){
		data.push_back(Vector(px, py));
		data.push_back(Vector(vx, vy));
	}
	return data;
} 

int mod(int x, int y){
	x %= y;
	return x < 0 ? x+y : x;
}



int main()
{
	vector<Vector> input = readInput("input");
	Vector room_size(101,103);
	//Vector room_size(11,7);
	int time = 100;

	vector<Vector> after_positions;
	for(int i=0; i<input.size()/2; i++){
		Vector new_pos = input[2*i] + input[2*i+1]*time;
		new_pos.x = mod(new_pos.x,room_size.x);
		new_pos.y = mod(new_pos.y,room_size.y);
		after_positions.push_back(new_pos);
	}

	int tl_count, tr_count, bl_count, br_count;
	tl_count = tr_count = bl_count = br_count = 0;
	for(int i=0; i<after_positions.size(); i++){
		Vector vec = after_positions[i];
		if(vec.x == room_size.x/2 || vec.y == room_size.y/2) continue;
		bool top = vec.y < room_size.y/2, left = vec.x < room_size.x/2;
		tl_count += top && left ? 1 : 0;
		tr_count += top && !left ? 1 : 0;
		bl_count += !top && left ? 1 : 0;
		br_count += !top && !left ? 1 : 0;
	}

	cout << tl_count * tr_count * bl_count * br_count << endl;
	return 0;
}




