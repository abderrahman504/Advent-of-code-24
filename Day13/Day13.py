import numpy as np
import regex

is_part1 = False

# Read input file
fh = open("input", "r")
input_problems = []
while True:
	group = [fh.readline() for i in range(4)]
	if not group[0]: break
	# Convert the Button lines to a 2x2 matrix
	button_A = regex.findall(r"X\+(\d+), Y\+(\d+)", group[0])[0]
	button_A = [int(i) for i in button_A]
	button_B = regex.findall(r"X\+(\d+), Y\+(\d+)", group[1])[0]
	button_B = [int(i) for i in button_B]
	mat = np.array([[button_A[0], button_B[0]],[button_A[1], button_B[1]]])
    # Convert the prize line to a 2x1 vector
	prize = regex.findall(r"X=(\d+), Y=(\d+)", group[2])[0]
	prize = [int(i) for i in prize]
	target = np.array(prize) + (0 if is_part1 else 1e13) 
	input_problems.append((mat, target))


# Processing input
tokens = 0
for problem in input_problems:
    mat = problem[0]
    target = problem[1]
    
    sol = np.matmul(np.linalg.inv(mat), target)
    
    skip = False
    for i in sol:
        if i < 0: skip = True
        diff = abs(i-round(i))
        if diff > 1e-3: skip = True
    if skip: continue
    
    tokens += 3*sol[0] + sol[1]

print(tokens)