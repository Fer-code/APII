
using APIMuseum.Connection;
using APIMuseum.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
namespace APIMuseum.Controllers.V1
{
    [ApiController]
    [Route("api/V1/[controller]/[action]")]
    public class FavoriteExpoController : ControllerBase
    {
        FavoriteExpoRequestsAPI FavAPI = new FavoriteExpoRequestsAPI();

        [HttpGet]
        [ActionName("getExpoFavorite")]
        public ExpoFavorite Get(int id)
        {
            var ex = FavAPI.MgetfavExpoById(id);
            if (ex == null)
            {
                var callback = new HttpResponseMessage(HttpStatusCode.NotFound);
                throw new System.Web.Http.HttpResponseException(callback);
            }
            return ex;
        }


        [HttpGet]
        [ActionName("getAllExpoFavorite")]
        public IEnumerable GetAllUsers()
        {
            return FavAPI.MgetfavExpo();
        }


        [HttpPost]
        [ActionName("addExpoFavorite")]
        public void Post([FromBody] ExpoFavorite ExpoFav)
        {
            FavAPI.InsertFavExpo(ExpoFav);
        }

        [HttpDelete]
        [ActionName("deleteExpoFavorite")]
        public void DeleteExpoFavorite(int id)
        {
            FavAPI.DeleteExpoFav(id);
        }



        [HttpPut]
        [ActionName("updateItem")]
        public void UpdateExpoFavorite(int id, [FromBody] ExpoFavorite FavExpo)
        {
            FavAPI.UpdateExpoFav(id, FavExpo);
        }
    }
}
