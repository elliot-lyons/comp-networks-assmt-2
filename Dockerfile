FROM ubuntu

COPY . /comp-networks-assmt-2
WORKDIR /comp-networks-assmt-2

RUN apt-get update
RUN apt-get install -y net-tools netcat tcpdump inetutils-ping openjdk-18-jdk
RUN javac *.java

CMD ["/bin/bash"]