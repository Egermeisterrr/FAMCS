#include <iostream>
#include <string>
#include <fstream>

using namespace std;

ifstream fin("in.txt");
ofstream fout("out.txt");

struct Node
{
	int Data;
	int lvl;
	int sum;
	Node* left;
	Node* right;   

	Node()
	{
		Data = 0;
		lvl = 0;
		sum = 0;
		this->left = nullptr;
		this->right = nullptr;
	}

	Node(int x)
	{
		Data = x;
		lvl = 0;
		sum = 0;
		this->left = nullptr;
		this->right = nullptr;
	}
};

class Tree
{
private:
	Node* root;

public:
	Tree()
	{
		root = nullptr;
	}

	~Tree()
	{
		delete_tree(root);
	}

	void insert(int x)
	{
		if (root == nullptr)
		{
			root = new Node(x);
			return;
		}
		Node* cur = root;
		while (true)
		{
			if (x < cur->Data)
			{
				if (cur->left != nullptr)
					cur = cur->left;
				else
				{
					cur->left = new Node(x);
					return;
				}
			}
			else if (x > cur->Data)
			{
				if (cur->right != nullptr)
					cur = cur->right;
				else
				{
					cur->right = new Node(x);
					return;
				}
			}
			else return;
		}
	}

	Node* returnRoot()
	{
		return this->root;
	}

	void delete_tree(Node* node)
	{
		if (node != NULL)
		{
			if (node->left != NULL)
			{
				delete_tree(node->left);
			}
			if (node->right != NULL)
			{
				delete_tree(node->right);
			}

			delete node;
		}
	}

	void rightDelete(int toDelete)
	{
		Node* parent = nullptr;
		Node* cur = this->root;
		while (true)
		{
			if (cur == nullptr)
				return;

			if (toDelete < cur->Data)
			{
				parent = cur;
				cur = cur->left;
			}
			else if (toDelete > cur->Data)
			{
				parent = cur;
				cur = cur->right;
			}
			else
				break;
		}

		if (cur->left == nullptr)
			replaceChild(parent, cur, cur->right);
		else if (cur->right == nullptr)
			replaceChild(parent, cur, cur->left);
		else
		{
			Node* minKeyNodeParent = cur;
			Node* minKeyNode = cur->right;
			while (minKeyNode->left != nullptr)
			{
				minKeyNodeParent = minKeyNode;
				minKeyNode = minKeyNode->left;
			}
			cur->Data = minKeyNode->Data;
			replaceChild(minKeyNodeParent, minKeyNode, minKeyNode->right);
		}
	}

	void replaceChild(Node* parent, Node* old, Node* recent)
	{
		if (parent == nullptr)
			root = recent;
		else if (parent->left == old)
			parent->left = recent;
		else if (parent->right == old)
			parent->right = recent;
	}

	string preOrder(Node* root)
	{
		if (root != nullptr)
		{
			fout << root->Data << endl;
			preOrder(root->left);
			preOrder(root->right);
		}
		return "";
	}

	void calculateLvl(Node* root)
	{
		if (root != nullptr)
		{
			calculateLvl(root->left);
			calculateLvl(root->right);
			if (root->left == nullptr && root->right == nullptr)
			{
				root->lvl = 0;
				root->sum = root->Data;
			}
			else if (root->left == nullptr && root->right != nullptr)
			{
				root->lvl = root->right->lvl + 1;
				root->sum = root->right->sum + root->Data;
			}
			else if (root->left != nullptr && root->right == nullptr)
			{
				root->lvl = root->left->lvl + 1;
				root->sum = root->left->sum + root->Data;
			}
			else if (root->left != nullptr && root->right != nullptr)
			{
				if (root->left->lvl > root->right->lvl)
				{
					root->lvl = root->left->lvl + 1;
					root->sum = root->left->sum + root->Data;
				}
				else if (root->left->lvl < root->right->lvl)
				{
					root->lvl = root->right->lvl + 1;
					root->sum = root->right->sum + root->Data;

				}
				else if (root->left->lvl == root->right->lvl)
				{
					root->lvl = root->right->lvl + 1;
					if (root->right->sum > root->left->sum)
					{
						root->sum = root->right->sum + root->Data;
					}
					else
					{
						root->sum = root->left->sum + root->Data;
					}
				}

			}
		}
	}

	void rootSearch(Node* root, Node*& rs, int max, int sum)
	{
		if (root != nullptr)
		{
			if (root->left == nullptr && root->right == nullptr && rs == nullptr)
			{
				rs = root;
				return;
			}
			if (root->left != nullptr && root->right != nullptr)
			{
				if ((root->left->lvl + root->right->lvl + 2) > max)
				{
					max = root->left->lvl + root->right->lvl + 2;
					sum = root->left->sum + root->right->sum + root->Data;
					rs = root;
				}
				if ((root->left->lvl + root->right->lvl + 2) == max)
				{
					if (sum < root->left->sum + root->right->sum + root->Data)
					{
						sum = root->left->sum + root->right->sum + root->Data;
						rs = root;
					}
				}
			}
			if (root->left != nullptr && root->right == nullptr)
			{
				if ((root->left->lvl + 1) > max)
				{
					max = root->left->lvl + 1;
					sum = root->left->sum + root->Data;
					rs = root;

				}
				if ((root->left->lvl + 1) == max)
				{
					if (sum < root->left->sum + root->Data)
					{
						sum = root->left->sum + root->Data;
						rs = root;
					}
				}
			}
			if (root->left == nullptr && root->right != nullptr)
			{
				if ((root->right->lvl + 1) > max)
				{
					max = root->right->lvl + 1;
					sum = root->right->sum + root->Data;
					rs = root;
				}
				if ((root->right->lvl + 1) == max)
				{
					if (sum < root->right->sum + root->Data)
					{
						sum = root->right->sum + root->Data;
						rs = root;
					}
				}
			}
			rootSearch(root->left, rs, max, sum);
			rootSearch(root->right, rs, max, sum);
		}
	}

	int searchCenterLvl(Node* rs)
	{
		if (rs != nullptr)
		{
			if (rs->left != nullptr && rs->right != nullptr)
			{
				if (rs->left->lvl == rs->right->lvl)
				{
					return -1;
				}
				if ((rs->left->lvl + rs->right->lvl) % 2 != 0)
				{
					return -1;
				}
			}

			if (rs->lvl % 2 == 0)
			{
				if (rs->left == nullptr || rs->right == nullptr)
				{
					return (rs->lvl / 2);
				}
				else
				{
					if (rs->left->lvl < rs->right->lvl)
					{
						int j = -1;
						for (int i = -1; i <= rs->left->lvl; i += 2)
						{
							j++;
						}
						return ((rs->lvl / 2) + j);
					}
					else
					{
						int j = -1;
						for (int i = -1; i <= rs->right->lvl; i += 2)
						{
							j++;
						}
						return ((rs->lvl / 2) + j);
					}
				}
			}
			else
			{
				if ((rs->left == nullptr || rs->right == nullptr))
				{
					return -1;
				}
				else
				{
					if (rs->left->lvl < rs->right->lvl)
					{
						int j = 0;
						for (int i = 0; i <= rs->left->lvl; i += 2)
						{
							j++;
						}
						return ((rs->lvl / 2) + j);
					}
					else
					{
						int j = 0;
						for (int i = 0; i <= rs->right->lvl; i += 2)
						{
							j++;
						}
						return ((rs->lvl / 2) + j);
					}
				}
			}
		}
		else
			return -1;
	}

	void searchForLvl(Node* root, Node*& rl, int lvl)
	{
		if (root != nullptr)
		{
			if (root->lvl == lvl)
			{
				rl = root;
				return;
			}
			searchForLvl(root->left, rl, lvl);
			searchForLvl(root->right, rl, lvl);
		}
	}
};

void inFile(Tree* a)
{
	fout << a->preOrder(a->returnRoot());
}

int main()
{
	Tree tree;
	Node* nod = nullptr, * nod2 = nullptr;
	int node, max = 0, sum = 0;

	while (fin >> node)
	{
		tree.insert(node);
	}

	tree.calculateLvl(tree.returnRoot());
	tree.rootSearch(tree.returnRoot(), nod, max, sum);
	int lvl = tree.searchCenterLvl(nod);

	if (lvl > 0)
	{
		tree.searchForLvl(tree.returnRoot(), nod2, lvl);
		tree.rightDelete(nod2->Data);
	}

	tree.rightDelete(nod->Data);

	inFile(&tree);

	fin.close();
	fout.close();
}