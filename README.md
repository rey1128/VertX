# VertX
Gradle to build VertX project

* build with Gradle:
in the project root directory, run command: `bash gradlew tasks`, to see all possible tasks
run command: `bash gradlew fatjar`, to build a fat jar with all required libraries included

* run generated jar:
in the build/libs directorey, see the generated jar, run with `java -jar VertxProject-all-3.3.3.jar`
open the browser, visit `localhost:8080` see "Hello Vertx".
