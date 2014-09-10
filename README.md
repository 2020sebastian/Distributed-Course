# Instructions

This repository contains the assignments for CSC376 Fall 2014.

You should check out your personal repository from BitBucket.  Make your changes to the files in
that repository, commit the changes, and then push back to BitBucket.  The instructor will then be
able to review your submission.

For all assignments, you must commit source code that compiles without error.  Submissions where the
source code does not compile will receive 0 points.  You can confirm that your source code compiles
correctly by running the following command in your repository directory:

    sbt compile

# Software Requirements

You will need to install:

- Java SE 8 http://www.oracle.com/technetwork/java/javase/downloads/index.html

- Scala Build Tool (SBT) http://www.scala-sbt.org/download.html

# Scala Build Tool (SBT)

The Scala Build Tool (SBT http://www.scala-sbt.org/) is used to compile and test the Scala
assignments.  Files must **NOT** be renamed or refactored, and the test code and build instruction
files must **NOT** be altered.  Specifically, you should only edit files under `src/main/`.

SBT will take care of downloading the correct version of Scala for you.  You will need to be
connected to the Internet when you do this.

## Launch SBT

From a shell window (Linux or OS X) or CMD/Powershell prompt (Windows), launch SBT using the command
you set up during SBT installation.  This is usually just `sbt`.  For example:

    $ sbt
    [info] Loading project definition from /csc376/main/project
    [info] Set current project to CSC376 Assignments (in build file:/csc376/main/)
    >

## Launch Scala Console from SBT

To launch the Scala console from the SBT console, run:

    console

To exit the Scala console and return to the SBT console, enter:

    :quit

You will have access to your source code files in the Scala console.

Note: if you paste into the Scala console, the parser looks for the smallest syntactically correct
expression, and this may not be what you want.  If this becomes problematic, you should either put
curly braces around the body of functions when pasting (this has been done for you already in the
homework assignments), or to investigate the `:paste` command in the Scala interpreter, i.e., type
`:paste`, then paste code in.

## Compiling with SBT

To compile the Scala source code with SBT, run:

    compile
    
To continuously compile, run:

    ~compile

## Running Code

You can run your Scala code from the command line as you would a Java program.  However, the
management of library code (in JAR files) becomes tedious and error prone as the number of libraries
increases.  A nonsense example illustrates the point:

    scala -cp lib/lib1.jar:lib/lib2.jar:/some/other/path/lib3.jar org.example.Main
    
In this repository, SBT has been configured to allow generation of a shellscript that manages these
dependencies.

Firstly, start SBT by running `sbt` as usual.

Secondly, run the following command in the SBT console:

    startScript
    
This will generate a shellscript `target/start` that invokes a Scala class with a classpath
including all JAR files for the SBT project.  

From the shell, you can then run, e.g.,

    $ ./target/start csp.net.ServerDemoIO

NOTE: as assignments are added to the repository, we will have more dependencies, so you will need
to re-run `startScript` to regenerate the shellscript file!

## Offline Work with SBT

You need to have Internet connectivity when you initially ask SBT to compile and test in order to download to libraries.

To work offline in SBT once you have the dependencies, run the following command in the SBT console:

    set offline := true
    
Local Variables:
mode: markdown
fill-column: 100
End:
