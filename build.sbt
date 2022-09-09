resolvers += "netlogo" at "https://dl.cloudsmith.io/public/netlogo/netlogo/maven/"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.13" % Test,
  "org.nlogo" % "netlogo" % "6.2.2" % Test,
  "org.jogamp.jogl" % "jogl-all" % "2.4.0" from "https://jogamp.org/deployment/archive/rc/v2.4.0-rc-20210111/jar/jogl-all.jar",
  "org.jogamp.gluegen" % "gluegen-rt" % "2.4.0" from "https://jogamp.org/deployment/archive/rc/v2.4.0-rc-20210111/jar/gluegen-rt.jar"
)

scalaVersion := "2.12.8"

lazy val downloadFromZip = taskKey[Unit]("Download zipped extensions and extract them to ./extensions")

downloadFromZip := {
  val baseURL = "https://raw.githubusercontent.com/NetLogo/NetLogo-Libraries/6.1/extensions/"
  val extensions = List(
    "table" -> "table-1.3.1.zip",
    "nw" -> "nw-3.7.9.zip",
    "rnd" -> "rnd-3.0.1.zip",
    "csv" -> "csv-1.1.1.zip"
  )
  for {
    (extension, file) <- extensions
    path = new File("extensions/" + extension)
    if java.nio.file.Files.notExists(path.toPath)
    url = new URL(baseURL + file)
  } {
    println("Downloading " + url)
    IO.unzipURL(url, path)
  }
}

compile in Test := (compile in Test).dependsOn(downloadFromZip).value

fork in Test := true
