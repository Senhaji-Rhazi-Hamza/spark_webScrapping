name := "spark_movie"

version := "1.0"

//scalaVersion := "2.10.2"

/*resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.2.1"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.0.0"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.3"

resolvers += "Apache Staging" at "https://repository.apache.org/content/groups/staging/"


libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "0.8.2.1",
  "org.apache.kafka" % "kafka-clients" % "0.8.2.1"
)
*/
version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=  "com.typesafe.play" %% "play-json" % "2.3.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.0.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6"

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.1"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % "2.1.1"

libraryDependencies ++= Seq(
  "org.apache.hbase" % "hbase-server" % "1.2.6",
  "org.apache.hbase" % "hbase-client" % "1.2.6",
  "org.apache.hbase" % "hbase-common" % "1.2.6",
  "org.apache.hadoop" % "hadoop-common" % "2.7.3"
)