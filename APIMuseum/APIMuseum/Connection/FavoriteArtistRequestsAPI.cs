using System;
using System.Collections.Generic;
using System.Linq;
using APIMuseum.Models;
using System.Web;
using MySql.Data.MySqlClient;

namespace APIMuseum.Connection
{
    public class FavoriteArtistRequestsAPI
    {

        ConnectionDB connectionDB = new ConnectionDB();
        ArtistFavorite FavArt = new ArtistFavorite();

        public List<ArtistFavorite> MgetfavArt()
        {
            MySqlCommand command = new MySqlCommand("select * from tbArtistFavorito", connectionDB.ConectarBD());
            var ReturnFav = command.ExecuteReader();
            return MgetfavArt(ReturnFav);
        }

        public ArtistFavorite MgetfavArtById(int id)
        {
            MySqlCommand command = new MySqlCommand($"select * from tbArtistFavorito where id_artista = {id}", connectionDB.ConectarBD());
            var ReturnFav = command.ExecuteReader();
            var listfavArtById = MgetfavArt(ReturnFav).FirstOrDefault();

            return new ArtistFavorite()
            {
                IdArt = listfavArtById.IdArt,
                IdUser = listfavArtById.IdUser

            };
        }

        public List<ArtistFavorite> MgetfavArt(MySqlDataReader dataReader)
        {
            var ListGetfavArt = new List<ArtistFavorite>();
            while (dataReader.Read())
            {
                var newGetFavArt = new ArtistFavorite()
                {
                    IdArt = Convert.ToInt32(dataReader["id_artista"]),
                    IdUser = Convert.ToInt32(dataReader["id_user"]),
                };
                ListGetfavArt.Add(newGetFavArt);
            }
            dataReader.Close();
            return ListGetfavArt;
        }

        public void InsertFavArt(ArtistFavorite ArtFav)
        {
            MySqlCommand insert = new MySqlCommand($"insert into tbArtistFavorito (id_user) values (@id_user);", connectionDB.ConectarBD());
            insert.Parameters.Add(Convert.ToString("@id_user"), MySqlDbType.Int32).Value = ArtFav.IdUser;
            insert.ExecuteNonQuery();
            connectionDB.DesconectarBD();
        }


        public void DeleteArtFav(int id)
        {
            MySqlCommand delete = new MySqlCommand($"Delete from tbArtistFavorito where id_artista = {id}", connectionDB.ConectarBD());
            delete.Parameters.AddWithValue(Convert.ToString(id), FavArt.IdArt);
            delete.ExecuteNonQuery();
            connectionDB.DesconectarBD();

        }
        public void UpdateArtFav(int id, ArtistFavorite ArtFav)
        {
            MySqlCommand update = new MySqlCommand($"update tbArtistFavorito set id_user = @id_user where id_artista = {id}", connectionDB.ConectarBD());
            update.Parameters.Add(Convert.ToString("@id_user"), MySqlDbType.Int32).Value = ArtFav.IdUser;

            update.ExecuteNonQuery();
            connectionDB.DesconectarBD();
        }

    }
}
