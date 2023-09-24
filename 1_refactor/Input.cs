using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Back_Propagation
{

    // Исходные данные

    internal class Input
    {
        public int Size { get; set; }
        public List<double> X { get; set; }
        public List<double> D { get; set; }

        public Input()
        {
            X = new List<double>();
            D = new List<double>();
        }

        public Input(int n)
        {
            this.Size = n;
            X = new List<double>();
            D = new List<double>();
        }

        public void Clear()
        {
            X.Clear();
            D.Clear();
            Size = 0;
        }
    }
}
