using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIMuseum.Connection;
using APIMuseum.Models;
using MySql.Data.MySqlClient;
namespace APIMuseum.Connection
{
    public class UserRequestsAPI
    {
        ConnectionDB connection = new ConnectionDB();
        User user = new User();
        public List<User> MgetUser()
        {
            MySqlCommand command = new MySqlCommand("select * from tbUser", connection.ConectarBD());
            var ReturnUser = command.ExecuteReader();
            return MgetUser(ReturnUser);
        }

        public User MgetUserById(int id)
        {
            MySqlCommand command = new MySqlCommand($"select * from tbUser where id_user = {id}", connection.ConectarBD());
            var ReturnUser = command.ExecuteReader();
            var listUserById = MgetUser(ReturnUser).FirstOrDefault();

            return new User()
            {
                Id = listUserById.Id,
                Name = listUserById.Name,
                Email = listUserById.Email,
                Password = listUserById.Password
            };
        }

        public List<User> MgetUser(MySqlDataReader dataReader)
        {
            var ListGetUser = new List<User>();
            while (dataReader.Read())
            {
                var newGetUser = new User()
                {
                    Id = Convert.ToInt32(dataReader["id_user"]),
                    Name = dataReader["nome_user"].ToString(),
                    Email = dataReader["email"].ToString(),
                    Password = dataReader["password_user"].ToString()
                };
                ListGetUser.Add(newGetUser);
            }
            dataReader.Close();
            return ListGetUser;
        }

        public void InsertUser(User user)
        {
            MySqlCommand insert = new MySqlCommand($"insert into tbUser (nome_user, email, password_user) values (@name, @email, @password);", connection.ConectarBD());

            insert.Parameters.Add("@name", MySqlDbType.VarChar).Value = user.Name;
            insert.Parameters.Add("@email", MySqlDbType.VarChar).Value = user.Email;
            insert.Parameters.Add("@password", MySqlDbType.VarChar).Value = user.Password;

            insert.ExecuteNonQuery();
            connection.DesconectarBD();
        }

        public void DeleteUser(int id)
        {
            MySqlCommand delete = new MySqlCommand($"Delete from tbUser where id_user = {id}", connection.ConectarBD());
            delete.Parameters.AddWithValue("id_user", user.Id);
            delete.ExecuteNonQuery();
            connection.DesconectarBD();

        }
        public void UpdateUser(int id, User user)
        {
            MySqlCommand update = new MySqlCommand($"update tbUser set nome_user = @name, email = @email, password_user = @password  where id_user = {id}", connection.ConectarBD());
            update.Parameters.AddWithValue("@name", user.Name);
            update.Parameters.AddWithValue("@email", user.Email);
            update.Parameters.AddWithValue("@password", user.Password);
            update.ExecuteNonQuery();
            connection.DesconectarBD();
        }
    }
}
