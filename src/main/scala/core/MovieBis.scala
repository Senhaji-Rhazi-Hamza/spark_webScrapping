package core

import java.io.PrintWriter

import core.MovieCollector.apiKey
import play.api.libs.json.{JsArray, JsObject, JsUndefined, JsValue}
import utils.http.HttpUtils
import utils.myProducer

/**
  * Created by hamza on 06/07/17.
  */
object MovieBis extends {
  apiKey = ""

   def valideMovie(jsonobject:Option[JsValue]):Boolean = {
    jsonobject match {
      case Some(json) => {
        json.\("adult").isInstanceOf[JsUndefined] match {
          case false => {
            val newJson = ReviewCollector.retrieveReviews(json.as[JsObject], apiKey)
            if ((newJson \ "reviews").as[JsArray].value.size > 0)
              true
            else
              false
          }
            case true =>false
           }
        }
      }
   }
  def getMovieById(id: Int) = {
    val url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + apiKey
    val query = HttpUtils.getContent(url)
    query
  }
  def getAllMovies(topic:String, Key:String):Unit ={
    apiKey = Key
    val producer = myProducer
    for(i <- 1 to 500)
      {
        var a = getMovieById(i)
        if(valideMovie(a))
          producer.sendmsg(a.toString,"key",topic)
      }
  }
}
