FROM ubuntu

RUN apt-get update
RUN apt-get -y install python
RUN apt-get clean

ADD ./files/Posts.xml /web/Posts.xml

EXPOSE 8080

CMD [ "python", "-m", "SimpleHTTPServer", "8080" ]