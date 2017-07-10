package core

import java.io.PrintWriter

import core.MovieCollector.{apiKey, getFirstResultMovieId}
import org.apache.htrace.fasterxml.jackson.annotation.JsonValue
import play.api.libs
import play.api.libs.json
import play.api.libs.json.{JsArray, JsObject, JsUndefined, JsValue}
import utils.http.HttpUtils
import utils.myProducer

/**
  * Created by hamza on 06/07/17.
  */
object MovieBis extends {
  apiKey = ""

   def addReview(jsonobject:Option[JsValue]):Option[JsValue] = {
    jsonobject match {
      case Some(json) => {
        json.\("adult").isInstanceOf[JsUndefined] match {
          case false => {
            val newJson = ReviewCollector.retrieveReviews(json.as[JsObject], apiKey)
            if ((newJson \ "reviews").as[JsArray].value.size > 0)
              Some(newJson)
            else
              None
          }
            case true =>None
           }
        }
      }
   }
  def getMovieById(id: Int) : Option[JsValue] = {
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
        val json = addReview(a)
        json match {
          case Some(json) => producer.sendmsg(json.toString(),"key",topic)
          case None => ???
        }
      }
  }

}
