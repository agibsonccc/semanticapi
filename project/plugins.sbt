// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

//resolvers += "CCC" at "www.clevercloudcomputing.com/nexus/content/repositories/CCC/"

//libraryDependencies += "play" %% "play" % "2.0.4"
// Use the Play sbt plugin for Play projects

resolvers+= "Typesafe artifactory" at "http://typesafe.artifactoryonline.com/typesafe/repo/"

//addSbtPlugin("play" % "sbt-plugin" % "2.0")

addSbtPlugin("play" % "sbt-plugin" % "2.0.4")
//addSbtPlugin("play" % "sbt-plugin" % "2.0")
