# Algorithm on graph

CONDITION:
The street plan for the race is given in the form of points marked with numbers from 0 (start) to N − 1 (finish), and arrows that connect them. The arrows represent one-way streets. The participants of the race move from point to point along the streets in the direction of the arrows. At each point, the participant can select any of the outgoing arrows.
Let's call a street plan good if it has the following properties:

1. each point of the plan can be reached from the start;

2. the finish line can be reached from any point of the plan;

3. there are no outgoing arrows at the finish line.

To reach the finish line, the participant does not necessarily have to go through all the points. However, some points cannot be skipped. Let's call them inevitable.
Let's assume that the race is held for two consecutive days. For this purpose, the plan should be divided into two "good" plans, one for each day. On the first day, the start point is 0, and the finish point is some kind of "split point". On the second day, the start is at this "split point", and the finish is at point N − 1.

The point S is the "split point" for a "good" plan C if:

1. S is different from the start and finish of Plan C;

2. the plan is divided by it into two "good" plans without common arrows (i.e. there are no arrows connecting them between the plans) and with a single common point S.

For a given "good" plan, it is necessary:

1. determine the set of "unavoidable points" that all participants of the race must visit (with the exception of the start and finish);

2. define the set of all possible "partition points".


INPUT FORMAT:
The first line contains the number of N points (3 ≤ N ≤ 50).
The next N lines contain the endpoints of the arrows originating respectively from points numbered from 0 to N − 1. Each of these lines ends with the number -2.


OUTPUT FORMAT:
In the first line, print the number of "unavoidable points" in the given plan, then their numbers in ascending order.
In the second line — the number of "split points" in the given plan, then — their numbers in ascending order.
If there are no points, then the corresponding line must contain the number 0.

Example:
![image](https://user-images.githubusercontent.com/102251036/169083206-cb3b89fa-13a9-41bc-9fc4-206fb06c3849.png)

