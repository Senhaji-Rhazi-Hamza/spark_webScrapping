package utils

import java.util.{Date, Properties}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


import org.scalatest.prop

import scala.util.Random

/**
  * Created by Hamza on 04/07/2017.
  */
object myProducer {
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  val localhost = "10.41.175.16"
  props.put("zookeeper.connect", localhost+ ":2181")
  props.put("client.id", "ScalaProducerExample")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    def sendmsg(msg:String, ip:String, topic:String):Unit =
    {
      val producer = new KafkaProducer[String, String](props)
      val data = new ProducerRecord[String, String](topic, ip, msg)
      producer.send(data)
      producer.close()
    }
}
