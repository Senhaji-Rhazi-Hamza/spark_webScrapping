import core.{MovieBis, MovieCollector, ReviewCollector}
import utils.ReviewUtils.Review
import utils.{HDFS, myConsumer, myProducer}


object Main {

  def main(args : Array[String]) : Unit = {
    val apiKey = "8a53cb0191b3c24481dac36ce245bd11"
    val topic = "test"

    /*
    myConsumer.listenMovies(topic)
    val thread  = new Thread {
      MovieBis.getAllMovies(topic, apiKey)
    }
    thread.start()*/
    val prod = myProducer
    MovieBis.getAllMovies(topic, apiKey)
    val hdfs = HDFS
    hdfs.record(topic)
    // myConsumer.listenMovies(topic)
    //myConsumer.listenReviews(topic, apiKey)
  }
}
