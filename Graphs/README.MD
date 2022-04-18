# Algorithm on graph

Condition
The group consists of N people. There are exactly N/2 friends in it every time. The relationship of friendship is symmetrical (if A is a friend of B, then B is a friend of A ). One person in the group has a book (his number is X) that everyone would like to hear and then happen to some of the others. A necessary way to determine the transfer of a book, in which it would visit everyone in the highest degree once, passing only from one to another, and finally returned to its own owner.

Input file format
The first line of the file contains three numbers N, K, X , where N is the number of people in the group, K = N / 2 is the number of friends each person has, X is the number of book owners (2 ≤ N ≤ 1000, 1 ≤ X ≤ N). The next N lines occur by K different numbers — the numbers of friends for the 1st, 2nd, ..., nth person, respectively. It is guaranteed that in the list of friends of the I-th person he himself (the number I) not mentioned.

Output file format
You enter a sequence of people's numbers in the order in which books can be distributed (the first and last elements of this line must correspond to the owner of the book).
