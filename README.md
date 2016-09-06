# Chess for Java

In the Spring of 2014, I created a two-player Chess game, with checkmate detection and a chess clock as a part of a Programming course at Penn. Our objective was to develop and test a bug-free standalone game in Java, complete with a GUI and game logic components.

I developed a bug-free, fast and well-designed product with a clean user interface and received the highest possible score in the assignment. The source code is in this repository.

## Technology

This game is built using core Java, Java Swing GUI libraries and the jUnit test suite. It uses custom drawing for game components and self-programmed logic for checkmate detection. The code is modular, standalone and object oriented, which was a grading criteria for the assignment.

## Running

Compile the project into an executable .jar file by running the following ANT build script on the command line. Make sure jar-in-jar-loader.zip in this repository is in the folder.

```
ant -f build.xml
```

Then, run the executable .jar file, named _chess-java.jar_ to play.