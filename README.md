This API is based on play framework for the web view and spring for the service/repository layer.
It uses maven for dependecy management. The following will go through the steps I did to set it up.

Assumptions:
1. You have your own nexus deployment to handle the play dependencies.
2.Running a Red Hat based distro. Setup on other linux machines should be similar. Replace
yum with apt-get, brew, pacman,..




Setup:
1. Download play from here http://www.playframework.org/download
2. You will need SBT. Install as follows: yum install http://scalasbt.artifactoryonline.com/scalasbt/sbt-native-packages/org/scala-sbt/sbt//0.12.1/sbt.rpm
   or install for your respective distribution here: http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html
3. Upload the play jars to your maven repository under: $PLAY_HOME/repository/local/play/
   Depending on which maven repo manager you use, instructions will be different. It is advisable you upload the pom under $VERSION/poms
   followed by the jar file under $VERSION/jars
4. Next install https://github.com/wsargent/play-2.0-spring-module:
	1. cd play-2.0-spring-module
	2. Modify project/Build.scala and remove the 2.0 due to it having an invalid identifier
	3. sbt package
	4. Upload target/scala-verssion/play-2-spring-module_*.jar to your maven repository, and enter it in to your pom
