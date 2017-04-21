# FROM isuper/java-oracle
FROM java:8
EXPOSE 8080
ADD /build/libs/VertxWebSocket-3.3.3-all.jar vtx-websocket.jar
# RUN echo 'hosts: files mdns4_minimal [NOTFOUND=return] dns mdns4' >> /etc/nsswitch.conf
ENTRYPOINT ["java","-jar","vtx-websocket.jar"]