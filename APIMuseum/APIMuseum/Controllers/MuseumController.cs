
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
    public class MuseumController : ControllerBase
    {

        MuseumRequestsAPI museumRequestsAPI = new MuseumRequestsAPI();

        //Pegar museu por id
        [HttpGet]
        [ActionName("getMuseum")]
        public Museum Get(int id)
        {
            var museum = museumRequestsAPI.MgetMuseumById(id);
            if (museum == null)
            {
                var callback = new HttpResponseMessage(HttpStatusCode.NotFound);
                throw new System.Web.Http.HttpResponseException(callback);
            }
            return museum;
        }

        [HttpGet]
        [ActionName("getAllMuseums")]
        public IEnumerable getAllMuseums()
        {
            return museumRequestsAPI.MgetMuseum();
        }

        //Adicionar novo museu



        [HttpPost]
        [ActionName("addItens")]
        public void Post([FromBody] Museum museum)
        {
            museumRequestsAPI.InsertMuseum(museum);
        }



        [HttpDelete]
        [ActionName("deleteMuseum")]
        public void DeleteMuseum(int id)
        {
            museumRequestsAPI.DeleteMuseum(id);
        }



        [HttpPut]
        [ActionName("updateItem")]
        public void UpdateMuseum(int id, [FromBody] Museum museum)
        {
            museumRequestsAPI.UpdateMuseum(id, museum);
        }

    }
}
