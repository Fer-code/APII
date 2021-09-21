using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIMuseum.Models
{
    public class Museum
    {
        public Museum()
        {

        }
        public Museum(int idM, string nameM, string addressM, string latM, string logM, int userM)
        {
            IdM = idM;
            NameM = nameM;
            AddressM = addressM;
            LatM = latM;
            LogM = logM;
            UserM = userM;

        }

        public int IdM { get; set; }
        public string NameM { get; set; }
        public string AddressM { get; set; }
        public string LatM { get; set; }
        public string LogM { get; set; }
        public int UserM { get; set; }

    }
}
