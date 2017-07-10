package utils




import core.{Review, ReviewCollector}

import scala.collection.JavaConversions._
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import play.api.libs.json._
import utils.myConsumer.props
import utils.myProducer.props

import scala.collection.JavaConverters._
/**
  * Created by Hamza on 04/07/2017.
  */
object myConsumer {
  import java.util.Properties
  val localhost = "localhost" //10.41.175.16
  val broker = localhost+":9092"
  val groupId = "test"

  def createConsumerConfig(brokers: String, groupId: String): Properties = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    props.put("zookeeper.connect", "localhost:2181")
    props.put("session.timeout.ms", "30000")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("partition.assignment.strategy", "range")
    props
  }
  val props = createConsumerConfig(broker, groupId)


  def listenMovies(topic:String):Unit = {
    val msgs=getmsgs(topic)
    msgs.foreach(msg=>println(new String(msg)))
      //consumer.subscribe(TOPIC)
  }
  def getmsgs(topic:String):Iterator[Array[Byte]] = {
    val config = new kafka.consumer.ConsumerConfig(props)
    val consumer =  kafka.consumer.Consumer.create(config)
    val numThread=1
    val topicCounts=Map(topic->numThread)
    val consumerMap= consumer.createMessageStreams(topicCounts)
    val consumerIterator=consumerMap.get(topic).get.head.iterator()
    val msgs=consumerIterator.map(_.message())
    msgs
  }

  def listenReviews(topic:String, key:String):Unit = {
    val apiKey = key
    val config = new kafka.consumer.ConsumerConfig(props)
    val consumer =  kafka.consumer.Consumer.create(config)
    val numThread=1
    val topicCounts=Map(topic->numThread)
    val consumerMap= consumer.createMessageStreams(topicCounts)
    val consumerIterator=consumerMap.get(topic).get.head.iterator()
    val msgs=consumerIterator.map(_.message())
    msgs.foreach(msg=> println(ReviewCollector.retrieveReviews(Json.parse(msg.toString).as[JsObject], key)) )

  }
}
