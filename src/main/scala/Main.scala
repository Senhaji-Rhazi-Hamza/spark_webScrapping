import core.{MovieBis}
import utils.{HDFS, myProducer}


object Main {

  def main(args : Array[String]) : Unit = {
    val apiKey = "8a53cb0191b3c24481dac36ce245bd11"
    val topic = "movies"

    val prod = myProducer
    MovieBis.getAllMovies(topic, apiKey)
    val hdfs = HDFS
    hdfs.record(topic)
  }
}
