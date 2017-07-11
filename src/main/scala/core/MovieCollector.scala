package core

import play.api.libs.json._
import utils.http.HttpUtils

object MovieCollector {

  var apiKey = ""

  def getFirstResultMovieId(name : String) : Option[Int] = {
    val url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + name.replaceAll("\\s+", "+") + "&language=en-US&page=1&include_adult=false"
    HttpUtils.getContent(url) match {
      case Some(json) => {
        val results = (json \ "results").as[JsArray].value
        results.size match {
          case 0 => None
          case _ => Some((results.head \ "id").as[Int])
        }
      }
      case None => None
    }
  }
}