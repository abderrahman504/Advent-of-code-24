#include "../includes.h"

using namespace std;
enum Dirs{left, down, right, up};

struct Vector{
	int x, y;

	Vector(int x, int y) : x(x), y(y) {}
	Vector operator +(Vector other){ return Vector(x+other.x, y+other.y); }
	Vector operator -(Vector other){ return Vector(x-other.x, y-other.y); }
};

void readInput(string file_name, vector<string>& out_map, vector<char>& out_moves)
{
	ifstream fh(file_name);
	if(fh.is_open()){
		string line;
		bool reading_map = true;
		while(getline(fh, line)){
			if(line.size() == 0) reading_map = false;
			if(reading_map){
				out_map.push_back(line);
			}
			else{
				for(auto c : line) out_moves.push_back(c);
			}
		}
	}
	else cout << "Couldn't open " << file_name << endl;
}

vector<string> getPart2Map(vector<string>& map){
	vector<string> new_map(map.size());
	for(int i=0; i<map.size(); i++){
		for(int j=0; j<map[0].size(); j++){
			char c = map[i][j];
			string expanded; 
			switch(c){
				case '.':
					expanded = "..";
				break;
				case '#':
					expanded = "##";
				break;
				case '@':
					expanded = "@.";
				break;
				case 'O':
					expanded = "[]";
				break;
			}
			new_map[i] += expanded;
		}
	}
	return new_map;
}


bool canMoveObj(Vector obj_pos, vector<string>& map, char dir)
{
	char obj = map[obj_pos.y][obj_pos.x];
	if(obj == '#' || obj == '.'){
		cerr << "Error: Tried to move object at (" << obj_pos.x << ", " << obj_pos.y << ") but found " << obj << endl;
		return false;
	}
	Vector move(0,0);
	move.x = dir=='<' ? -1 : dir=='>' ? 1 : 0;
	move.y = dir=='^' ? -1 : dir=='v' ? 1 : 0;
	Vector next = move + obj_pos;
	char next_obj = map[next.y][next.x];
	// If next contains a block then call canMoveObj on it.
	if(next_obj == 'O' || ((next_obj == '[' || next_obj == ']') && (dir == '<' || dir == '>'))){
		return canMoveObj(next, map, dir);
	}
	// If next contains a big block and we're moving vertically then call canMoveObj on both halves
	else if(next_obj == '[' || next_obj == ']'){
		Vector other_next = next;
		other_next.x = next_obj == '[' ? next.x+1 : next.x-1;
		return canMoveObj(next, map, dir) && canMoveObj(other_next, map, dir);
	}
	// If next contains a wall then object can't move
	else if(next_obj == '#') return false;
	// If next is empty then object can move
	else return true;
}

// assume this will only be called on an object whose canMoveObj() is true.
Vector moveObj(Vector obj_pos, vector<string>& map, char dir){
	char obj = map[obj_pos.y][obj_pos.x];
	Vector move(0,0);
	move.x = dir=='<' ? -1 : dir=='>' ? 1 : 0;
	move.y = dir=='^' ? -1 : dir=='v' ? 1 : 0;
	Vector next = move + obj_pos;

	char next_obj = map[next.y][next.x];
	// If next contains a block then call canMoveObj on it.
	if(next_obj == 'O' || ((next_obj == '[' || next_obj == ']') && (dir == '<' || dir == '>'))){
		moveObj(next, map, dir);
		map[obj_pos.y][obj_pos.x] = '.';
		map[next.y][next.x] = obj;
		return next;
	}
	// If next contains a big block and we're moving vertically
	else if(next_obj == '[' || next_obj == ']'){
		Vector other_next = next;
		other_next.x = next_obj == '[' ? next.x+1 : next.x-1;
		moveObj(other_next, map, dir);
		moveObj(next, map, dir);
		map[obj_pos.y][obj_pos.x] = '.';
		map[next.y][next.x] = obj;
		return next;
	}
	// If next is empty
	else{
		map[obj_pos.y][obj_pos.x] = '.';
		map[next.y][next.x] = obj;
		return next;
	}
}

void print_map(vector<string> &map){
	for(string line: map){
		cout << line << endl;
	}
	cout << endl;
}

int main()
{
	bool is_part1 = false;
	vector<string> map;
	vector<char> moves;
	readInput("input", map, moves);
	if(!is_part1) map = getPart2Map(map);

	Vector bot_pos(0,0);
	// Find the bot position
	for(int i=0; i<map.size(); i++){
		for(int j=0; j<map[0].size(); j++){
			if(map[i][j] == '@'){
				bot_pos = Vector(j, i);
				goto BotFound;
			}
		}
	}
	BotFound:

	// Apply bot moves
	cout << "Initial state\n";
	print_map(map);
	int moves_count = 0;
	for(auto move: moves){
		if(canMoveObj(bot_pos, map, move))
			bot_pos = moveObj(bot_pos, map, move);
		//cout << "Move " << move << endl;
		//print_map(map);
		cout << "Move " << ++moves_count << " / " << moves.size() << endl;
	}

	// Calculate GPS
	int sum = 0;
	for(int i=0; i<map.size(); i++){
		for(int j=0; j<map[0].size(); j++){
			if(map[i][j] == 'O' || map[i][j] == '[') sum += 100 * i + j;
		}
	}

	cout << sum << endl;

}