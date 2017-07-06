#gnome-terminal -e "bin/zookeeper-server-start.sh config/zookeeper.properties &"
bin/zookeeper-server-start.sh config/zookeeper.properties&
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test&
bin/kafka-server-start.sh config/server.properties&
xterm -e "bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test"&
gnome-terminal -e "bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning"&
echo tatt

