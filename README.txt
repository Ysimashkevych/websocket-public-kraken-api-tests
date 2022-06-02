Kraken WebSocketAPI tests
=========================
## Setup
Clone this repo to your desktop
Docker should be installed on your machine

##Usage
After repo is cloned go to its directory and run following to build an image:
docker build -t testsimage .

To execute tests run below, execution results along with received messages from would be displayed in console / terminal:
docker run testsimage mvn clean test
