# Recurrence Relations
CONDITION: In connection with the influenza epidemic, a patients with influenza A and b patients with influenza C are sent to the hospital. Patients with influenza A cannot be placed in the same ward with patients with influenza B. There is information on the total number p of wards in the hospital, numbered from 1 to p, and on the distribution patients already there. It is necessary to determine the maximum number m of patients that the hospital can accept. When placing new patients, it is not allowed to move existing patients from ward to ward.

INPUT FORMAT:
The first line contains the integer a (0 ≤ a ≤ 20,000). The second line contains an integer b (0 ≤ b ≤ 20,000). The third line contains an integer p (1 ≤ p ≤ 1000). In each of the following p lines there are 3 numbers ni, ai and bi, separated by a space, where ni is the capacity of the ward, ai is the number of flu A patients already in the ward, bi is the number of flu B patients already in the ward. Information about the capacity of wards is entered sequentially for wards numbered 1, 2, ..., p. The numbers ni, ai, bi are non—negative integers not exceeding 20,000.

OUTPUT FORMAT:
In the first line, print the number m. If all the admitted patients are placed, then in the second line, in ascending order, print the ward numbers separated by a space (there should be no space after the last number!), where the patients with influenza A are placed.

Example:
![image](https://user-images.githubusercontent.com/102251036/169134725-ab55d1f4-98ef-4d9e-8ae5-9adbe00a3aee.png)
