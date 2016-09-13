Start a java project out of this (or just run it from the terminal wihtout all the eclipse mess).

More information found in the java project :)
Good luck!

Running without eclipse in OSX/Linux:

* change line in `/Java/src/implementation/Main.java:39` to
`String quotes = currentRelativePath.toAbsolutePath().toString() + "/data";` (only for non-windows-users)
* put json-simple.jar in Java-folder. (get in here: http://www.java2s.com/Code/Jar/j/Downloadjsonsimplejar.htm)
* cd to Java-folder
* compile with `javac -classpath json-simple.jar **/*.java`
* run with `java -cp src:json-simple.jar implementation.Main`

Now Main assumes its run from the Java-folder, hardcode the path and you can run it from whereever.
