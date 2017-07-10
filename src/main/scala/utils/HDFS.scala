package utils

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka._
import kafka.serializer.StringDecoder

/**
  * Created by hamza on 09/07/17.
  */
object HDFS {
  def record(topics : String) = {
    val numThreads = "1"
    val sparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")

    val ip = "localhost"
    val kafkaConf = Map(
      "metadata.broker.list" -> "localhost:6667",
      "zookeeper.connect" -> "localhost:2181",
      "group.id" -> "test"
    )

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc, kafkaConf, topicMap, StorageLevel.MEMORY_ONLY_SER).map(_._2)
    lines.foreachRDD
    {
      rdd =>
        if (rdd.count > 0)
          rdd.repartition(1).saveAsTextFile("./test/" + rdd.hashCode())
    }

    ssc.start()
    ssc.awaitTermination()
  }


}
