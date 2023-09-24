/*=============================
 * 
 * Дано: файл, который содержит число N - количество точек на плоскости - 
 *  и их координаты в виде:
 * x1 d1
 * x2 d2
 * ...
 * xN dN
 * 
 * Задача: найти коэффициенты прмой y = kx + b (аппроксимирующей функции), 
 *  так что y ~= d - двумя способами: 
 *  - методом наименьших квадратов
 *  - методом обратного распространения ошибки
 * 
 * 
 * === Что делать:
 * 
 * 1) Считать исходный файл
 *  1a) Дать пользователю возможность выбрать свой текстовый файл
 *  1b) Нужно хранить значения массивов x и d
 *  1c) Нужно сбросить все пердыдущие вычисления и очистить график
 *  
 * 2) Найти коэффициенты прямой первым способом
 *  2a) Нужо хранить значения собственных k и b
 *      и нужен метод для их вычисления
 *  2b) Алгоритм метода:
 *      - вычислить суммы всех x, d, x*d, x^2
 *      - вычислить k и b по формулам (будут в коде)
 *  
 * 3) Найти коэффициенты прямой вторым способом
 *  3a) Нужно хранить значения собственных k и b
 *      и нужен метод для их вычисления
 *  3b) Алгоритм метода:
 *      - задать случайные значения k и b
 *      - вычислить занение y[i] = kx[i] + b
 *      - вычислить ошибку d[i] - y[i]
 *      - изменить k и b по формулам (будут в коде)
 *      - повторить эти шаги много раз
 * 
 * 4) Вывести всё на график 
 *  4a) Вывести исходные точки
 *  4b) Вывести полученные прямые
 * 
 * =============================*/



using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace Back_Propagation
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private Input input = new Input();
        private LeastSq mnk = new LeastSq();
        private Propagation prop = new Propagation();        

        private void BrowseFile_Click(object sender, EventArgs e)
        {
            // Открыть диалоговое окно для выбора файла
            
            OpenFileDialog ofd = new OpenFileDialog();
            ofd.Filter = "Text Files(*.txt)|*.txt|All files (*.*)|*.*";
            if (ofd.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    // Сбросить все что можно
                    
                    input.Clear();
                    mnk.Clear();
                    prop.Clear();

                    k_textbox.Clear();
                    b_textbox.Clear();
                    w1_textbox.Clear();
                    w2_textbox.Clear();

                    chart.Series[0].Points.Clear();
                    chart.Series[1].Points.Clear();
                    chart.Series[2].Points.Clear();

                    // Открыть файл и считать два вектора

                    StreamReader sr = new StreamReader(ofd.FileName);
                    string line = sr.ReadLine();

                    input.Size = Convert.ToInt32(line);

                    bool t = true;

                    for (int i = 0; i < input.Size; i++)
                    {
                        line = sr.ReadLine();
                        foreach (var number in line.Split())
                        {
                            if (t)
                            {
                                input.X.Add(Convert.ToDouble(number));
                                t = false;
                            }
                            else
                            {
                                input.D.Add(Convert.ToDouble(number));
                                t = true;
                            }
                        }
                    }

                    // Вывод исходных значений - в виде текста и графика

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < input.Size; i++)
                    {
                        sb.Append(input.X[i].ToString("F2").PadLeft(7));
                        sb.Append(input.D[i].ToString("F2").PadLeft(10));
                        sb.AppendLine();
                    }
                    richTextBox1.Text = sb.ToString();

                    for (int i = 0; i < input.Size; i++)
                        chart.Series[2].Points.AddXY(input.X[i], input.D[i]);

                    MNK_button.Enabled = true;
                    NN_button.Enabled = true;
                }
                catch
                {
                    MessageBox.Show("Can't open file", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }

        private void MNK_button_Click(object sender, EventArgs e)
        {
            // Метод наименьших квадратов

            mnk.Compute(input);

            k_textbox.Text = mnk.K.ToString();
            b_textbox.Text = mnk.B.ToString();

            graphic();
        }

        private void NN_button_Click(object sender, EventArgs e)
        {
            // Обратное распространение ошибки
            
            prop.Compute(input);

            w1_textbox.Text = prop.K.ToString();
            w2_textbox.Text = prop.B.ToString();

            graphic();
        }


        // Чтобы обновить график, его нужно заново перерисовать
        void graphic()
        {
            double a0 = input.X[0], a1 = input.X[input.Size - 1];
            double h = input.X[1] - input.X[0];
            chart.Series[0].Points.Clear();
            chart.Series[1].Points.Clear();

            for (double x = a0; x <= a1; x = x + h)
            {
                chart.Series[0].Points.AddXY(x, mnk.K * x + mnk.B);
                chart.Series[1].Points.AddXY(x, prop.K * x + prop.B);
            }
        }
    }
}
