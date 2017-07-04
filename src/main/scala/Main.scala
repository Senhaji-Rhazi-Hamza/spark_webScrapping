import core.{MovieCollector, ReviewCollector}
import utils.ReviewUtils.Review


object Main {

  def main(args : Array[String]) : Unit = {
    val apiKey = args(0)
    MovieCollector.retrieveMovies(apiKey)

  }

}
