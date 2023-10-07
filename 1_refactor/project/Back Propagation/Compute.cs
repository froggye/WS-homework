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

}
