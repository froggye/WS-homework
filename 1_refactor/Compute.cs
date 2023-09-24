using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Back_Propagation
{
    // Т.к. оба метода вычисляют и хранят значения k и b
    // можно сделать им один интерфейс

    internal interface Compute
    {
        double K { get; set; }
        double B { get; set; }

        void Compute(Input input);

        void Clear();
    }


    // Метод наименьших квадратов
    internal class LeastSq : Compute
    {
        public double K { get; set; }
        public double B { get; set; }

        public LeastSq()
        {
            Clear();
        }

        public void Clear()
        {
            K = 0;
            B = 0;
        }

        public void Compute(Input input)
        {
            double sum_x = 0,
                sum_y = 0,
                sum_xy = 0,
                sum_xx = 0;

            for (int i = 0; i < input.Size; ++i)
            {
                sum_x += input.X[i];
                sum_y += input.D[i];
                sum_xy += input.X[i] * input.D[i];
                sum_xx += input.X[i] * input.X[i];
            }

            K = (input.Size * sum_xy - sum_x * sum_y) / (input.Size * sum_xx - sum_x * sum_x);
            B = (sum_y - K * sum_x) / input.Size;
        }
    }


    // Метод обратного распространения ошибки
    internal class Propagation : Compute
    {
        public double K { get; set; }
        public double B { get; set; }

        // для вычислений
        private const double NU = 0.01;

        public Propagation()
        {
            Clear();
        }

        public void Clear()
        {
            K = 0.1;
            B = 0;
            // получается исходная функция y = 0.1x
        }

        public void Compute(Input input)
        {
            for (int j = 0; j < 100; j++) //повторить произвольное количество раз
                                          //чем больше, тем точнее
            {
                for (int i = 0; i < input.Size; i++) //пройтись по всему массиву
                {
                    double y = K * input.X[i] + B;
                    double delta = input.D[i] - y;

                    K += NU * input.X[i] * delta;
                    B += NU * delta;
                }
            }
        }
    }
}
