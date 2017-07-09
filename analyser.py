import json
import requests
import sys
from kafka import KafkaProducer
from kafka import KafkaConsumer

# Usage: $ python analyser.py "blablabla"


# The use of this script is limited to 1000 call by ip adress

# Online sentiment analyser
url = "http://text-processing.com/api/sentiment/"

lang = "english"

def consume():
	consume_topic = "lolol"
	consume_host = "localhost:9092"

	consumer = KafkaConsumer(group_id='my-group',
                         bootstrap_servers=consume_host)
                         #value_deserializer=lambda m: json.loads(m.decode('ascii')))
	consumer.subscribe([consume_topic])
	for message in consumer:
		rate = analyse(message.value)
		send(rate)


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

def send(rate):
	kafka_topic = "rates"
	kafka_host = "localhost:9092"
	try:
		producer = KafkaProducer(bootstrap_servers=[kafka_host],
			value_serializer=lambda v: json.dumps(v).encode('utf-8'))
		producer.send(kafka_topic, rate)
	except Exception as ex:
		print(ex)
	producer.flush()
	producer.close()

consume()


