import json
import requests
import sys
import codecs
from kafka import KafkaProducer
from kafka import KafkaConsumer

# Usage: $ python analyser.py host_consume topicToConsume host_producer topiceToProduce


# The use of this script is limited to 1000 call by ip adress

# Online sentiment analyser
url = "http://text-processing.com/api/sentiment/"
consumeTopic = sys.argv[2]
consumeIp = sys.argv[1]

produceTopic = sys.argv[4]
produceIp = sys.argv[3]
lang = "english"

def process(consume, produce, topic1, topic2):
	consume_topic = topic1
	consume_host = consume

	produce_topic = topic2
	produce_host = produce

	consumer = KafkaConsumer(group_id='my-group',
                         bootstrap_servers=consume_host)

	consumer.subscribe([consume_topic])

	producer = KafkaProducer(bootstrap_servers=[produce_host],
			value_serializer=lambda v: json.dumps(v).encode('utf-8'))

	for message in consumer:
		movie = json.loads(message.value.decode('utf-8'))
		title = movie["title"]
		review = movie["reviews"][0]["content"]
		rate = analyse(review)
		data = {
		"title": title,
		"rate": rate
		}
		json_data = json.dumps(data)
		producer.send(produce_topic, json_data)
	producer.flush()
	producer.close()


def analyse(review):
	rate = 0
	data = [
	('text', review),
	]
	r = requests.post(url, data=data)
	json_response = r.json()
	label = json_response["label"]
	if (label == "pos"):
	  rate = 10 * json_response["probability"]["pos"]
	elif (label == "neg"):
	  rate = -10 * json_response["probability"]["neg"]
	elif (label == "neutral"):
	  rate = 10 * json_response["probability"]["neutral"]
	return "{0:.2f}".format(rate)


process(consumeIp, produceIp, consumeTopic, produceTopic)


