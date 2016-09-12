Start a java project out of this (or just run it from the terminal wihtout all the eclipse mess).

More information found in the java project :)
Good luck!

Running without eclipse in OSX/Linux:

* put json-simple.jar in src-folder. (get in here: http://www.java2s.com/Code/Jar/j/Downloadjsonsimplejar.htm)
* cd to src
* compile with `javac -classpath json-simple.jar **/*.java`
* run with `java -cp .:json-simple.jar implementation.Main`

BTW remember to fix filepath in Main.java (right now it uses backslashes and points to the wrong dir. I recommend hardcoding the path to the data-folder.)
