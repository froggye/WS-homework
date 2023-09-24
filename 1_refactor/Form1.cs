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

        int n;
        double[] x;
        double[] d;

        double sum_x, sum_y, sum_xy, sum_xx;
        double k, b;

        double w1, w2, y;

        double nu = 0.01;
        double delta;

        private void BrowseFile_Click(object sender, EventArgs e)
        {
            OpenFileDialog ofd = new OpenFileDialog();
            ofd.Filter = "Text Files(*.txt)|*.txt|All files (*.*)|*.*";
            if (ofd.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    sum_x = 0;
                    sum_y = 0;
                    sum_xy = 0;
                    sum_xx = 0;
                    w1 = 0.1;
                    w2 = 0;
                    y = 0;
                    delta = 0;

                    k_textbox.Clear();
                    b_textbox.Clear();
                    w1_textbox.Clear();
                    w2_textbox.Clear();

                    //открыть файл
                    StreamReader sr = new StreamReader(ofd.FileName);
                    string line = sr.ReadLine();
                    n = Convert.ToInt32(line);

                    x = new double[n];
                    d = new double[n];

                    bool t = true;

                    for (int i = 0; i < n; i++)
                    {
                        line = sr.ReadLine();
                        foreach (var number in line.Split())
                        {
                            if (t)
                            {
                                x[i] = Convert.ToDouble(number);
                                t = false;
                            }
                            else
                            {
                                d[i] = Convert.ToDouble(number);
                                t = true;
                            }
                        }
                    }

                    //вывод исходных значений
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < n; i++)
                    {
                        sb.Append(x[i].ToString("F2").PadLeft(7));
                        sb.Append(d[i].ToString("F2").PadLeft(10));
                        sb.AppendLine();
                    }
                    richTextBox1.Text = sb.ToString();

                    chart.Series[0].Points.Clear();
                    chart.Series[1].Points.Clear();
                    chart.Series[2].Points.Clear();
                    for (int i = 0; i < n; i++)
                        chart.Series[2].Points.AddXY(x[i], d[i]);

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
            for (int i = 0; i < n; ++i)
            {
                sum_x += x[i];
                sum_y += d[i];
                sum_xy += x[i] * d[i];
                sum_xx += x[i] * x[i];
            }

            k = (n * sum_xy - sum_x * sum_y) / (n * sum_xx - sum_x * sum_x);
            b = (sum_y - k * sum_x) / n;

            k_textbox.Text = k.ToString();
            b_textbox.Text = b.ToString();

            graphic();
        }

        private void NN_button_Click(object sender, EventArgs e)
        {
            for (int j = 0; j < 100; j++) //повторить произвольное количество раз
                                         //чем больше, тем точнее
            {
                for (int i = 0; i < n; i++) //пройтись по всему массиву
                {
                    y = w1 * x[i] + w2;
                    delta = d[i] - y;

                    w1 = w1 + nu * x[i] * delta;
                    w2 = w2 + nu * delta;
                }
            }

            w1_textbox.Text = w1.ToString();
            w2_textbox.Text = w2.ToString();

            graphic();
        }

        void graphic()
        {
            double a0 = x[0], a1 = x[n - 1];
            double h = x[1] - x[0];
            chart.Series[0].Points.Clear();
            chart.Series[1].Points.Clear();

            for (double x = a0; x <= a1; x = x + h)
            {
                chart.Series[0].Points.AddXY(x, k * x + b);
                chart.Series[1].Points.AddXY(x, w1 * x + w2);
            }
        }
    }
}
