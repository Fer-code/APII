using APIMuseum.Models;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using APIMuseum.Connection;
using Microsoft.AspNetCore.Mvc;

namespace APIMuseum.Controllers.V1
{   
    [ApiController]
    [Route("api/V1/[controller]/[action]")]
    public class UserController : ControllerBase
    {
        UserRequestsAPI userRequestsAPI = new UserRequestsAPI();


        [HttpGet]
        [ActionName("getUsers")]
        public User Get(int id)
        {
            var users = userRequestsAPI.MgetUserById(id);
            if (users == null)
            {
                var callback = new HttpResponseMessage(HttpStatusCode.NotFound);
                throw new System.Web.Http.HttpResponseException(callback);
            }
            return users;
        }


        [HttpGet]
        [ActionName("getAllUsers")]
        public IEnumerable GetAllUsers()
        {
            return userRequestsAPI.MgetUser();
        }

        [HttpPost]
        [ActionName("addItens")]
        public bool Post([FromBody] User user)
        {
            if (user == null)
            {
                return false;
            }

            userRequestsAPI.InsertUser(user);
            return true;
        }



        [HttpDelete]
        [ActionName("deleteUser")]
        public void DeleteUser(int id)
        {
            userRequestsAPI.DeleteUser(id);
        }



        [HttpPut]
        [ActionName("updateItem")]
        public void UpdateUser(int id, [FromBody] User user)
        {
            userRequestsAPI.UpdateUser(id, user);
        }
    }
}
