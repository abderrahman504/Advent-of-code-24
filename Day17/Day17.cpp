#include "../includes.h"

using namespace std;

/*
    Combo operands 0 through 3 represent literal values 0 through 3.
    Combo operand 4 represents the value of register A.
    Combo operand 5 represents the value of register B.
    Combo operand 6 represents the value of register C.
    Combo operand 7 is reserved and will not appear in valid programs.


The adv instruction does reg_a / (2^combo) and puts result in reg_a.

The bxl instruction calculates the bitwise XOR reg_b ^ literal and stores the result in reg_b.

The bst instruction calculates combo % 8 (keeping only its lowest 3 bits) and puts result in reg_b.

The jnz instruction does nothing if the reg_a is 0. Otherwise, it jumps by setting the instruction pointer to the value of its literal operand. I
f this instruction jumps, the instruction pointer is not increased by 2 after this instruction.

The bxc instruction calculates the bitwise XOR reg_b ^ reg_c and stores the result in register B.
(For legacy reasons, this instruction reads an operand but ignores it.)

The out instruction calculates combo % 8, then outputs that value. (If a program outputs multiple values, they are separated by commas.)

The bdv instruction does reg_a / (2^combo) and puts result in reg_b.

The cdv instruction does reg_a / (2^combo) and puts result in reg_c.
*/

enum {adv, bxl, bst, jnz, bxc, out, bdv, cdv};

typedef char int8;

vector<int> readInput(string file_path, int& out_reg_a, int& out_reg_b, int& out_reg_c)
{
	FILE* fh = fopen(file_path.c_str(), "r");
	int res, x;
	res = fscanf(fh, "Register A: %d\n", &x);
	out_reg_a = x;
	res = fscanf(fh, "Register B: %d\n", &x);
	out_reg_b = x;
	res = fscanf(fh, "Register C: %d\n", &x);
	out_reg_c = x;
	char prog_str[50];
	vector<int> program;
	fscanf(fh, "Program: %s", prog_str);
	int i = 0;
	while(i < 50){
		program.push_back(prog_str[i]-'0');
		if(prog_str[i+1] == 0) break;
		i += 2;
	}
	return program;
}


int reg_a, reg_b, reg_c;

int evalCombo(int8 combo){
	switch(combo){
		case 0:
		case 1:
		case 2:
		case 3:
			return combo;
		case 4:
			return reg_a;
		case 5:
			return reg_b;
		case 6:
			return reg_c;
		default:
			return -1;
	}
}

int main()
{
	bool is_part1 = true;
	int pc = 0;
	vector<int> program = readInput("input", reg_a, reg_b, reg_c);
	vector<int> output;
	
	while(pc < program.size()){
		bool jump = false;
		int8 opcode = program[pc];
		int8 operand = program[pc+1];
		switch(opcode){
			case adv:
				reg_a = reg_a / (1 << evalCombo(operand));
			break;
			case bxl:
				reg_b ^= operand;
			break;
			case bst:
				reg_b = evalCombo(operand) & 0b111;
			break;
			case jnz:
				jump = reg_a != 0;
				pc = jump ? operand : pc;
			break;
			case bxc:
				reg_b = reg_b ^ reg_c;
			break;
			case out:
				output.push_back(evalCombo(operand) & 0b111);
			break;
			case bdv:
				reg_b = reg_a / (1 << evalCombo(operand));
			break;
			case cdv:
				reg_c = reg_a / (1 << evalCombo(operand));
			break;
			default:
				cout << "Invalid opcode " << opcode << " at " << pc << endl;
		}

		if(!jump) pc += 2;
	}

	for(int out : output){
		cout << out << ",";
	}

	return 0;
}