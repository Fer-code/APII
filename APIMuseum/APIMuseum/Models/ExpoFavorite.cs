using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace APIMuseum.Models
{
    public class ExpoFavorite
    {
        public ExpoFavorite()
        {

        }

        public ExpoFavorite(int idExpo, int idUser)
        {
            IdExpo = idExpo;
            IdUser = idUser;
        }

        public int IdExpo { get; set; }
        public int IdUser { get; set; }
    }
}
