import json
import requests
import sys
from kafka import KafkaProducer
from kafka import KafkaConsumer

# Usage: $ python analyser.py "blablabla"


# The use of this script is limited to 1000 call by ip adress

# Online sentiment analyser
url = "http://text-processing.com/api/sentiment/"
topic = sys.argv[2]
host = sys.argv[1]
lang = "english"

def consume(topic, host):
	consume_topic = topic
	consume_host = host

	consumer = KafkaConsumer(group_id='my-group',
                         bootstrap_servers=consume_host)
                         #value_deserializer=lambda m: json.loads(m.decode('ascii')))
	consumer.subscribe([consume_topic])
	for message in consumer
		print(message.value)
		rate = analyse(message.value)
		send(rate, topic, host)


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

def send(rate, topic, host):
	kafka_topic = topic
	kafka_host = host
	try:
		producer = KafkaProducer(bootstrap_servers=[kafka_host],
			value_serializer=lambda v: json.dumps(v).encode('utf-8'))
		producer.send(kafka_topic, rate)
	except Exception as ex:
		print(ex)
	producer.flush()
	producer.close()

consume(topic, host)


