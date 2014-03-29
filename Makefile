runs:
	java -classpath .:protobuf-java-2.5.0.jar Server --host localhost --port 8004
runc: 
	java -classpath .:protobuf-java-2.5.0.jar Client --host localhost --port 8004


all: client server

client:
	javac -cp protobuf-java-2.5.0.jar Client.java cs/Communication.java

server:
	javac -cp protobuf-java-2.5.0.jar Server.java cs/Communication.java

test:
	echo "Compiling test cases"
	javac -cp .:junit-4.8.2.jar TestClient.java 
	echo "Running test cases"
	java -cp .:junit-4.8.2.jar org.junit.runner.JUnitCore TestClient
