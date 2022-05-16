CONDITION: There is a terrain plan divided into squares, given by a matrix of size n × m. Each square with coordinates (i, j) has a height relative to sea level, the value of which is determined by the natural number aij (numbering of rows and columns of matrix A begins with 1, the upper left square of the matrix has coordinates (1, 1)). The robot can move only on the terrain and only parallel to the Ox and Oy axes between the centers of the squares. When moving to an adjacent square, the length of the ascent (descent) is equal to the modulus of the difference in the heights of the squares, and the length of the movement from square to square is equal to the value k.
It is necessary to find among the robot routes from position (x1, y1) to position (x2, y2) a route of minimum length. The length of the route is defined as the total length of ascents and descents plus the total length of movements from square to square.

INPUT FORMAT: The first line contains the numbers n and m (1 ≤ n, m ≤ 1000) — the dimensions of the field through which the robot moves.
The next n lines contain information about the height matrix of each square (the input line corresponds to the row of the height matrix; heights are integers from 1 to 109).
The next line is the number k (0 ≤ k ≤ 109).
In the last line, the coordinates of the starting and ending points of the robot's movement are set: x1, y1, x2, y2.

OUTPUT FORMAT: Print the length of the shortest work route.

Example:
![image](https://user-images.githubusercontent.com/102251036/168595267-c479e5cc-97a3-4b5a-a1c9-92700f49baa6.png)
![image](https://user-images.githubusercontent.com/102251036/168595327-6549d81b-cb34-4151-9840-177fd8d1e031.png)
