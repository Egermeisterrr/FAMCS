#include <iostream>
#include <windows.h>
#include <string>
#include <fstream>
#include <ctime>
#include <map>
#include <set>
#include <stack>

using namespace std;

ofstream fout("output.txt");

struct taskDate
{
	int a, b, c;
};

stack<pair<taskDate, pair<double, double>>> twoSol;
stack<pair<taskDate, double>> oneSol;
stack<pair<taskDate, string>> noSol;
stack<pair<taskDate, string>> inf;

typedef struct times
{
	clock_t start;
	clock_t end;
};

map<int, times> arrtime;
map<int, int> statistics;

int min_time = INT_MAX;
int max_time = INT_MIN;

DWORD WINAPI calculate2(LPVOID arg)
{
	if (arrtime.find(GetCurrentThreadId()) == arrtime.end()) {
		pair<clock_t, clock_t> t(clock(), clock());
		times current;
		current.start = clock();
		current.end = clock();
		int i1 = GetCurrentThreadId();
		statistics.insert(make_pair(i1, 0));
		arrtime.insert(make_pair(i1, current));
	}
	else
		statistics[GetCurrentThreadId()] = ++statistics[GetCurrentThreadId()]; // можно сделать иначе

	HANDLE mutex = CreateMutex(NULL, FALSE, L"functThread");

	if (mutex == NULL)
		throw "(functThread) mutex failed\n";

	WaitForSingleObject(mutex, INFINITE);

	stack<taskDate>& stac = *(stack<taskDate>*)arg;

	if (stac.empty()) {
		ReleaseMutex(mutex);
		return 0;
	}

	clock_t st = clock();
	taskDate cur = stac.top();
	stac.pop();

	int a = cur.a;
	int b = cur.b;
	int c = cur.c;

	if (a != 0 || b != 0 || c != 0)
	{
		double d = b * b - 4 * a * c;

		if (d < 0)
		{
			pair<taskDate, string> b(cur, "Nosolutions");
			noSol.push(b);
		}
		else if (d == 0)
		{
			double x = -b / (2 * a);
			pair<taskDate, double> b(cur, x);
			oneSol.push(b);
		}
		else
		{
			double x1 = (-b + sqrt(d)) / (2 * a);
			double x2 = (-b - sqrt(d)) / (2 * a);
			pair<double, double> p(x1, x2);
			pair<taskDate, pair<double, double>> b(cur, p);
			twoSol.push(b);
		}
	}
	else
	{
		pair<taskDate, string> b(cur, "Infinityofsolutions");
		inf.push(b);
	}

	ReleaseMutex(mutex);
	arrtime[GetCurrentThreadId()].end = clock();
	clock_t en = clock();

	if (en - st > max_time)
		max_time = en - st;
	else if (en - st < min_time)
		min_time = en - st;

	calculate2(arg);
}

int main()
{
	setlocale(LC_ALL, "rus");
	srand(time(NULL));

	// 2 ЗАДАНИЕ
	int  TaskCount;
	int  ThreadCount;
	stack<taskDate> stak;

	cout << "ВТОРОЕ ЗАДАНИЕ" << "\n\n";
	cout << "Введите количество задач" << endl;
	cin >> TaskCount;
	cout << "Введите количество потоков" << endl;
	cin >> ThreadCount;
	cout << "\n";

	HANDLE* handler = new HANDLE[ThreadCount];
	DWORD* id = new DWORD[ThreadCount];

	// генерируем данные для задачи
	for (int i = 0; i < TaskCount; i++)
	{
		taskDate tr;

		tr.a = rand() % 200 - 100;
		tr.b = rand() % 200 - 100;
		tr.c = rand() % 200 - 100;

		stak.push(tr);
	}

	//создаём потоки
	for (int i = 0; i < ThreadCount; i++)
	{
		handler[i] = CreateThread(NULL, 0, calculate2, (void*)(&stak), 0, &id[i]);
		if (handler[i] == NULL)
			return GetLastError();
	}

	// ожидание завершения потоков
	WaitForMultipleObjects(ThreadCount, handler, TRUE, INFINITE);

	// закрытие всех потоков
	for (int i = 0; i < ThreadCount; i++) {
		CloseHandle(handler[i]);
	}

	cout << "Решено задач: " << twoSol.size()  + oneSol.size() << "\n";
	cout << "Нет решений: " << noSol.size() << "\n";
	cout << "Бесконечность решений: " << inf.size() << "\n\n";

	// сколько задач решил каждый поток
	for (map <int, int> ::iterator it = statistics.begin(); it != statistics.end(); it++) {
		cout << "Номер потока: " << it->first << " Задач решено:  " << it->second << "\n";
	}
	cout << "\n";

	// время работы каждого потока
	for (map <int, times> ::iterator it = arrtime.begin(); it != arrtime.end(); it++) {
		cout << "Номер потока : " << it->first << " Время длы выполнения задач: " << it->second.end - it->second.start << "ms\n";
	}
	cout << "\n";

	//минимальное и максимальное время на выполнение таски
	cout << "Максимальное время выполнение задачи: " << max_time << "ms\n";
	cout << "Минимальное время выполнения задачи: " << min_time << "ms\n\n";

	int size1 = twoSol.size();
	int size2 = oneSol.size();
	int size3 = noSol.size();
	int size4 = inf.size();

	// запись в файл и расчёт времени на это
	clock_t start_time = clock();
	for (int i = 0; i < size1; i++) {
		pair<taskDate, pair<double, double>>dot = twoSol.top();
		fout << dot.first.a << " " << dot.first.b << " " << dot.first.c << " result = " << dot.second.first << " " << dot.second.second << "\n";
		twoSol.pop();
	}
	for (int i = 0; i < size2; i++) {
		pair<taskDate, double> dot = oneSol.top();
		fout << dot.first.a << " " << dot.first.b << " " << dot.first.c << " result = " << dot.second << "\n";
		oneSol.pop();
	}
	for (int i = 0; i < size3; i++) {
		pair<taskDate, string>dot = noSol.top();
		fout << dot.first.a << " " << dot.first.b << " " << dot.first.c << " result = " << dot.second << "\n";
		noSol.pop();
	}
	for (int i = 0; i < size4; i++) {
		pair<taskDate, string>dot = inf.top();
		fout << dot.first.a << " " << dot.first.b << " " << dot.first.c << " result = " << dot.second << "\n";
		inf.pop();
	}
	clock_t end_time = clock();
	cout << "Время потраченное на запись в файл: " << end_time - start_time << "ms\n";

	return 0;
}