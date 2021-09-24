
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIMuseum.Connection;
using APIMuseum.Models;
using MySql.Data.MySqlClient;


namespace APIMuseum.Connection
{
    public class MuseumRequestsAPI
    {

        ConnectionDB connection = new ConnectionDB();
        Museum museum = new Museum();



        public List<Museum> MgetMuseumByIdUser(int id_User)
        {
            MySqlCommand command = new MySqlCommand($"select * from tbVisitedMuseum where user_museum = {id_User}", connection.ConectarBD());
            var ReturnMuseum = command.ExecuteReader();
            return MgetMuseum(ReturnMuseum);

           
        }


        public List<Museum> MgetMuseum()
        {
            MySqlCommand command = new MySqlCommand("select * from tbVisitedMuseum", connection.ConectarBD());
            var ReturnMuseum = command.ExecuteReader();
            return MgetMuseum(ReturnMuseum);
        }

        public Museum MgetMuseumById(int id)
        {
            MySqlCommand command = new MySqlCommand($"select * from tbVisitedMuseum where id_museum = {id}", connection.ConectarBD());
            var ReturnMuseum = command.ExecuteReader();
            var listMuseumById = MgetMuseum(ReturnMuseum).FirstOrDefault();

            return new Museum()
            {
                IdM = listMuseumById.IdM,
                NameM = listMuseumById.NameM,
                AddressM = listMuseumById.AddressM,
                UserM = listMuseumById.UserM

            };
        }

        public List<Museum> MgetMuseum(MySqlDataReader dataReader)
        {
            var ListGetMuseum = new List<Museum>();
            while (dataReader.Read())
            {
                var newGetMuseum = new Museum()
                {
                    IdM = Convert.ToInt32(dataReader["id_museum"]),
                    NameM = dataReader["nome_museum"].ToString(),
                    AddressM = dataReader["address_museum"].ToString(),
                    UserM = Convert.ToInt32(dataReader["user_museum"])
                };
                ListGetMuseum.Add(newGetMuseum);
            }
            dataReader.Close();
            return ListGetMuseum;
        }

        public void InsertMuseum(Museum museum)
        {
            MySqlCommand insert = new MySqlCommand($"insert into tbVisitedMuseum (nome_museum, address_museum, user_museum) values (@name, @address, @user);", connection.ConectarBD());
            insert.Parameters.Add("@name", MySqlDbType.VarChar).Value = museum.NameM;
            insert.Parameters.Add("@address", MySqlDbType.Text).Value = museum.AddressM;
            insert.Parameters.Add(Convert.ToString("@user"), MySqlDbType.Int32).Value = museum.UserM;
            insert.ExecuteNonQuery();
            connection.DesconectarBD();
        }

        public void DeleteMuseum(int id)
        {
            MySqlCommand delete = new MySqlCommand($"Delete from tbVisitedMuseum where id_museum = {id}", connection.ConectarBD());
            delete.Parameters.AddWithValue("id_user", museum.IdM);
            delete.ExecuteNonQuery();
            connection.DesconectarBD();

        }
        public void UpdateMuseum(int id, Museum museum)
        {
            MySqlCommand update = new MySqlCommand($"update tbVisitedMuseum set nome_museum = @name, address_museum = @address, user_museum = @user  where id_museum = {id}", connection.ConectarBD());

            update.Parameters.AddWithValue("@name", museum.NameM);
            update.Parameters.AddWithValue("@address", museum.AddressM);
            update.Parameters.AddWithValue(Convert.ToString("@user"), museum.UserM);
            update.ExecuteNonQuery();
            connection.DesconectarBD();
        }


    }
}
