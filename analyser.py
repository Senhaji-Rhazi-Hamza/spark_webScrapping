import json
import requests
import sys

# The use of this script is limited to 1000 call by ip adress

# Online sentiment analyser
url = "http://text-processing.com/api/sentiment/"

text = sys.argv[1]
lang = "english"


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

print(analyse(text))