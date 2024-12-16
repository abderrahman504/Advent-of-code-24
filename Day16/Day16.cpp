#include "../includes.h"
#include <queue>

using namespace std;


enum Dirs{W, S, E, N};

struct Vector3{
	int x, y, z;

	Vector3(int x, int y, int z) : x(x), y(y), z(z) {}
	Vector3() = default;

	bool operator==(const Vector3 other) const { return x == other.x && y == other.y && z == other.z; }
};
struct Vector2{
	int x, y;

	Vector2(int x, int y) : x(x), y(y) {}
	bool operator==(const Vector2 other) const { return x == other.x && y == other.y; }
};

struct Vec3Hasher{
	size_t operator()(const Vector3 &vec) const {
		return hash<int>()(vec.x) ^ hash<int>()(vec.y) ^ hash<int>()(vec.z);
	}
};

struct PQWrapper{
	int cost;
	int heuristic;
	Vector3 vec;
	PQWrapper(int cost, int heuristic, Vector3 vec) : vec(vec), cost(cost), heuristic(heuristic) {}

	bool operator <(const PQWrapper other) const { return (cost + heuristic) < (other.cost + other.heuristic); }
	bool operator <=(const PQWrapper other) const { return (cost + heuristic) <= (other.cost + other.heuristic); }
	bool operator >(const PQWrapper other) const { return (cost + heuristic) > (other.cost + other.heuristic); }
	bool operator >=(const PQWrapper other) const { return (cost + heuristic) >= (other.cost + other.heuristic); }
	bool operator ==(const PQWrapper other) const { return (cost + heuristic) == (other.cost + other.heuristic); }
};


void readInput(string file_path, vector<string> &out_map, Vector2 &out_start, Vector2 &out_end)
{
	ifstream fh(file_path);
	if(fh.is_open()){
		string line;
		while(getline(fh, line)){
			if(line.find('S') != string::npos) out_start = Vector2(line.find('S'), out_map.size());
			if(line.find('E') != string::npos) out_end = Vector2(line.find('E'), out_map.size());
			out_map.push_back(line);
		}
	}
	else cout << "Couldn't open " << file_path << endl;
}

int heuristic(Vector3 location, Vector2 end){
	int distance = abs(location.x - end.x) + abs(location.y - end.y);
	int rotation = 0;
	int h_dir = location.x < end.x ? Dirs::E : Dirs::W;
	int v_dir = location.y < end.y ? Dirs::S : Dirs::N;
	
	// If facing horizonally
	if(location.z == Dirs::E || location.z == Dirs::W){
		// If we'll have to make h movement and facing the opposite direction then we need to rotate twice.
		if(location.x != end.x && h_dir != location.z) rotation += 2000;
		// Otherwise if we'll have to make v movement then we'll need to rotate once.
		else if(location.y != end.y) rotation += 1000;
	}
	// If facing vertically
	else if(location.z == Dirs::N || location.z == Dirs::S){
		// If we'll have to make v movement and facing the opposite direction then we need to rotate twice.
		if(location.y != end.y && v_dir != location.z) rotation += 2000;
		// Otherwise if we'll have to make h movement then we'll need to rotate once.
		else if(location.x != end.x) rotation += 1000;
	}
	return distance + rotation;
}

vector<Vector3> exploreLocation(Vector3 location, int cost, vector<string>& map){
	vector<Vector3> neighbours;
	vector<Vector3> candidates;
	candidates.push_back(Vector3(location.x-1, location.y, Dirs::W));
	candidates.push_back(Vector3(location.x+1, location.y, Dirs::E));
	candidates.push_back(Vector3(location.x, location.y-1, Dirs::N));
	candidates.push_back(Vector3(location.x, location.y+1, Dirs::S));
	for(auto cand : candidates){
		if(cand.x < 0 || cand.y < 0 || cand.x >= map[0].size() || cand.y >= map.size()) continue;
		if(map[cand.y][cand.x] == '#') continue;
		neighbours.push_back(cand);
	}
	return neighbours;
}


int main()
{
	//// Testing heuristic
	//Vector2 test_end(5,5);
	//Vector3 test1(5,5,Dirs::S); // h1 = 0
	//Vector3 test2(4,5,Dirs::E); // h2 = 1
	//Vector3 test3(4,5,Dirs::W); // h3 = 2001
	//Vector3 test4(6,5,Dirs::E); // h4 = 2001
	//Vector3 test5(6,5,Dirs::W); // h5 = 1
	//Vector3 test6(4,5,Dirs::S); // h6 = 1001
	//Vector3 test7(4,5,Dirs::N); // h7 = 1001
	//Vector3 test8(6,5,Dirs::S); // h8 = 1001
	//Vector3 test9(6,5,Dirs::N); // h9 = 1001
	//Vector3 test10(5,4,Dirs::E); // h10 = 1001
	//Vector3 test11(5,4,Dirs::W); // h11 = 1001
	//Vector3 test12(5,6,Dirs::E); // h12 = 1001
	//Vector3 test13(5,6,Dirs::W); // h13 = 1001
	//Vector3 test14(5,4,Dirs::S); // h14 = 1
	//Vector3 test15(5,4,Dirs::N); // h15 = 2001
	//Vector3 test16(5,6,Dirs::S); // h16 = 2001
	//Vector3 test17(5,6,Dirs::N); // h17 = 1
	//int h1 = heuristic(test1, test_end);
	//int h2 = heuristic(test2, test_end);
	//int h3 = heuristic(test3, test_end);
	//int h4 = heuristic(test4, test_end);
	//int h5 = heuristic(test5, test_end);
	//int h6 = heuristic(test6, test_end);
	//int h7 = heuristic(test7, test_end);
	//int h8 = heuristic(test8, test_end);
	//int h9 = heuristic(test9, test_end);
	//int h10 = heuristic(test10, test_end);
	//int h11 = heuristic(test11, test_end);
	//int h12 = heuristic(test12, test_end);
	//int h13 = heuristic(test13, test_end);
	//int h14 = heuristic(test14, test_end);
	//int h15 = heuristic(test15, test_end);
	//int h16 = heuristic(test16, test_end);
	//int h17 = heuristic(test17, test_end);


	vector<string> map;
	Vector2 start(0,0);
	Vector2 end(0,0);
	readInput("input", map, start, end);

	priority_queue<PQWrapper, vector<PQWrapper>, greater<PQWrapper>> frontier;
	unordered_set<Vector3, Vec3Hasher> visited;
	Vector3 vec = Vector3(start.x, start.y, Dirs::E);
	frontier.push(PQWrapper(0, heuristic(vec, end), vec));
	int final_cost = 0;
	while(true){
		PQWrapper elem = frontier.top();
		frontier.pop();
		// If found the end then stop and store the cost
		if(elem.vec.x == end.x && elem.vec.y == end.y){
			final_cost = elem.cost;
			break;
		}
		vector<Vector3> neighbours = exploreLocation(elem.vec, elem.cost, map);
		for(auto neigh : neighbours){
			if(visited.count(neigh)) continue;
			PQWrapper wrapper = PQWrapper(elem.cost + heuristic(elem.vec, Vector2(neigh.x, neigh.y)), heuristic(neigh, end), neigh);
			frontier.push(wrapper);
		}
		visited.insert(elem.vec);
	}

	cout << final_cost << endl;

}