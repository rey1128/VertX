clear
rm -r audit-db.tmp
rm audit*
bash gradlew clean
bash gradlew fatjar
java -jar build/libs/VertxProject-all-3.3.3.jar