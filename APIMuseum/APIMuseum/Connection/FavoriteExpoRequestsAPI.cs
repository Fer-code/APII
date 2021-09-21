
using APIMuseum.Models;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;


namespace APIMuseum.Connection
{
    public class FavoriteExpoRequestsAPI
    {


        ConnectionDB connectionDB = new ConnectionDB();
        ExpoFavorite FavExpo = new ExpoFavorite();

        public List<ExpoFavorite> MgetfavExpo()
        {
            MySqlCommand command = new MySqlCommand("select * from tbExpoFavorito", connectionDB.ConectarBD());
            var ReturnExpo = command.ExecuteReader();
            return MgetfavExpo(ReturnExpo);
        }

        public ExpoFavorite MgetfavExpoById(int id)
        {
            MySqlCommand command = new MySqlCommand($"select * from tbExpoFavorito where id_expo = {id}", connectionDB.ConectarBD());
            var ReturnExpo = command.ExecuteReader();
            var listfavExpoById = MgetfavExpo(ReturnExpo).FirstOrDefault();

            return new ExpoFavorite()
            {
                IdExpo = listfavExpoById.IdExpo,
                IdUser = listfavExpoById.IdUser

            };
        }

        public List<ExpoFavorite> MgetfavExpo(MySqlDataReader dataReader)
        {
            var ListGetfavExpo = new List<ExpoFavorite>();
            while (dataReader.Read())
            {
                var newGetFavExpo = new ExpoFavorite()
                {
                    IdExpo = Convert.ToInt32(dataReader["id_expo"]),
                    IdUser = Convert.ToInt32(dataReader["id_user"]),
                };
                ListGetfavExpo.Add(newGetFavExpo);
            }
            dataReader.Close();
            return ListGetfavExpo;
        }

        public void InsertFavExpo(ExpoFavorite ExpoFav)
        {
            MySqlCommand insert = new MySqlCommand($"insert into tbExpoFavorito (id_user) values (@id_user);", connectionDB.ConectarBD());
            insert.Parameters.Add(Convert.ToString("@id_user"), MySqlDbType.Int32).Value = ExpoFav.IdUser;
            insert.ExecuteNonQuery();
            connectionDB.DesconectarBD();
        }


        public void DeleteExpoFav(int id)
        {
            MySqlCommand delete = new MySqlCommand($"Delete from tbExpoFavorito where id_expo = {id}", connectionDB.ConectarBD());
            delete.Parameters.AddWithValue(Convert.ToString(id), FavExpo.IdExpo);
            delete.ExecuteNonQuery();
            connectionDB.DesconectarBD();

        }
        public void UpdateExpoFav(int id, ExpoFavorite ExpoFav)
        {
            MySqlCommand update = new MySqlCommand($"update tbExpoFavorito set id_user = @id_user where id_expo = {id}", connectionDB.ConectarBD());
            update.Parameters.Add(Convert.ToString("@id_user"), MySqlDbType.Int32).Value = ExpoFav.IdUser;

            update.ExecuteNonQuery();
            connectionDB.DesconectarBD();
        }


    }
}
