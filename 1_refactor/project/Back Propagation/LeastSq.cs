using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Back_Propagation
{
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
}
