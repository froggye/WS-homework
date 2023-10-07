using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Back_Propagation
{
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
