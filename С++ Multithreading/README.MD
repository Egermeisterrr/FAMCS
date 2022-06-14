# C++ Multithreading

Implement a program that works according to the following scenario:
1) Two arguments TaskCount and ThreadCount are accepted from the console.
2) The main thread generates a TaskCount of parameter sets (for a task from task A), and stores them in some container (a container with parameters is the same for all threads). When generating parameters, use a random number sensor. The generation algorithm should be such that the solutions to all problems are not the same (for example, 0 or Nosolution).
3) A ThreadCount of identical auxiliary threads is created that take tasks from the container and execute them asynchronously (i.e. as soon as the thread has completed its current task, it takes the next one and executes it. And so on until all the tasks are completed. ). All threads put the results of their calculations into one common container.
4) After all tasks are calculated, the main thread saves all the results in pairs (task parameters, answer) to the output text file.
5) After the main stream, statistics are displayed on the screen: how many tasks have been solved in total, how many have not been solved due to an error in the calculation process, how many have no solutions, how many tasks have been solved by each of the threads, how much time he spent on it, the minimum and maximum time for solving the problem, the time spent writing data to a file. Statistics should be displayed on the screen in a readable form.
