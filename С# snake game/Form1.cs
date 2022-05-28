using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Fokin_Lab18
{
    public partial class Form1 : Form
    {
        private PictureBox fruit;
        private PictureBox[] sn = new PictureBox[400];
        private Label labelScore;
        private int _width = 700;
        private int _height = 600;
        private int size = 40;
        private int score = 0;
        private int dirX;
        private int dirY;
        private int rI, rJ;
        
        public Form1()
        {
            InitializeComponent();

            this.Width = _width;
            this.Height = _height;

            dirX = 0;
            dirY = 0;

            labelScore = new Label();
            labelScore.Text = "Score: 0";
            labelScore.Location = new Point(610,10);
            this.Controls.Add(labelScore);

            sn[0] = new PictureBox();
            sn[0].Location = new Point(201, 201);
            sn[0].Size = new Size(size-1, size-1);
            sn[0].BackColor = Color.Blue;
            this.Controls.Add(sn[0]);

            fruit = new PictureBox();
            fruit.BackColor = Color.Red;
            fruit.Size = new Size(size, size);

            timer1.Tick += new EventHandler(Update);
            timer1.Interval = 300;
            timer1.Start();

            Generate_map();
            GenerateFruit();
            

            this.KeyDown += new KeyEventHandler(OKP);
        }

        private void Generate_map()
        {
            for (int i = 0; i <= _width / size; i++)
            {
                PictureBox pic = new PictureBox();
                pic.BackColor = Color.Black;
                pic.Location = new Point(0, size * i);
                pic.Size = new Size(_width - 100, 1);
                this.Controls.Add(pic);
            }

            for (int i = 0; i <= _height / size; i++)
            {
                PictureBox pic = new PictureBox();
                pic.BackColor = Color.Black;
                pic.Location = new Point(size * i, 0);
                pic.Size = new Size(1, _width - 100);
                this.Controls.Add(pic);
            }
        }

        private void OKP(object sender, KeyEventArgs e)
        {
            switch (e.KeyCode.ToString())
            {
                case "Right":
                    dirX = 1;
                    dirY = 0;
                    break;
                case "Left":
                    dirX = -1;
                    dirY = 0;
                    break;
                case "Up":
                    dirX = 0;
                    dirY = -1;
                    break;
                case "Down":
                    dirX = 0;
                    dirY = 1;
                    break;
            }

        }

        private void movement()
        {
            for (int i = score; i >= 1; i--)
            {
                sn[i].Location = sn[i - 1].Location;
            }
            sn[0].Location = new Point(sn[0].Location.X + dirX * size, sn[0].Location.Y + dirY * size);
            eatItSelf();
        }

        private void Update(Object sender, EventArgs e)
        {
            EatFruit();
            movement();
            borders();
        }

        private void GenerateFruit()
        {
            Random r = new Random();
            rI = r.Next(0, _height - size);
            int tempI = rI % size;
            rI -= tempI;

            rJ = r.Next(0, _height - size);
            int tempJ = rJ % size;
            rJ -= tempJ;
            rI++;
            rJ++;
            fruit.Location = new Point(rI, rJ);
            this.Controls.Add(fruit);
        }

        private void EatFruit()
        {
            if (sn[0].Location.X == fruit.Location.X && sn[0].Location.Y == fruit.Location.Y)
            {
                labelScore.Text = "Score " + ++score;
                sn[score] = new PictureBox();
                sn[score].Location = new Point(sn[score - 1].Location.X + 40 * dirX, sn[score - 1].Location.Y + 40 * dirY);
                sn[score].Size = new Size(size - 1, size - 1);
                sn[score].BackColor = Color.Blue;
                this.Controls.Add(sn[score]);
                GenerateFruit();
            }
        }

        private void eatItSelf()
        {
            for (int i = 1; i < score; i++)
            {
                if (sn[0].Location == sn[i].Location)
                {
                    for (int j = i; j <= score; j++)
                    {
                        this.Controls.Remove(sn[j]);
                    }
                    score = score - (score - i + 1);
                }
            }
            labelScore.Text = "Score " + score;
        }

        private void borders()
        {
            if(sn[0].Location.X < 0)
            {
                for(int i = 1; i <= score; i++)
                {
                    this.Controls.Remove(sn[i]);
                }
                score = 0;
                labelScore.Text = "Score " + score;
                dirX = 1;
            }

            if (sn[0].Location.X > _width - 150)
            {
                for (int i = 1; i <= score; i++)
                {
                    this.Controls.Remove(sn[i]);
                }
                score = 0;
                labelScore.Text = "Score " + score;
                dirX = -1;
            }

            if (sn[0].Location.Y > _height)
            {
                for (int i = 1; i <= score; i++)
                {
                    this.Controls.Remove(sn[i]);
                }
                score = 0;
                labelScore.Text = "Score " + score;
                dirY = -1;
            }

            if (sn[0].Location.Y < 0)
            {
                for (int i = 1; i <= score; i++)
                {
                    this.Controls.Remove(sn[i]);
                }
                score = 0;
                labelScore.Text = "Score " + score;
                dirY = 1;
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
    }
}