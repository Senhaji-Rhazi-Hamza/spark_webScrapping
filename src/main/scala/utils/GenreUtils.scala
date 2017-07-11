package utils

import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

object GenreUtils {

  case class Genre(
                    id: Option[Int],
                    name: Option[String]
                  )

  implicit val genreFormat = Json.format[Genre]

  def StringToGenre(str: String) = Json.parse(str).validate[Genre] match {
    case JsError(e) => println(e); None
    case JsSuccess(t, _) => Some(t)
  }
}
