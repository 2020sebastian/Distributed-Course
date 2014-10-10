// GENERATED

package csp.net

import csp.utils.Utils.{runnable,tryfinally}
import java.io.{BufferedReader,BufferedWriter,InputStream,InputStreamReader,OutputStream,OutputStreamWriter,PrintWriter}
import java.net.{ServerSocket,Socket}
import java.util.{Collections,HashSet,Set}


object ConcurrentHelloServer {
  def main (args : Array[String]) {
    // TODO: Change this declaration so that "open" is not null.
    // Ensure that "open" is a synchronized data structure.
    // HINT: look in examples/src/main/scala/csp/threads/Contention.scala
    val names : Set[String] = null

    val serverPort : Int = 7000
    val ss : ServerSocket = new ServerSocket (serverPort)
    while (true) {
      val s : Socket = ss.accept
      new Thread (() => handleRequest (s, names)).start
    }
  }

  def handleRequest (s : Socket, names : Set[String]) {
    tryfinally (s) { (s : Socket) =>
      println ("Connection from: " + s.getRemoteSocketAddress)
      tryfinally (new BufferedReader (new InputStreamReader (s.getInputStream))) { (br : BufferedReader) => 
        tryfinally (new PrintWriter (new BufferedWriter (new OutputStreamWriter (s.getOutputStream)))) { (pw : PrintWriter) => 
          // TODO: Complete this method.  
          // Use "br : BufferedReader" and "pw : PrintWriter" to read and write from the socket s.
          // This will use characters rather than bytes, and is the correct approach for working with character data.
          // (Using InputStream/OutputStream works with ASCII but this is the more robust approach).
          // Please look in the Java Doc API for java.io.BufferedReader to see available methods.  One of them is very useful 
          // for this part of the assignment.

          // Read one line from the client (via "br).
          // If the line is anything other than "backdoor", then assume the line is a name.
          // Add the name to the set "names", and print ("hello " + name) to the client (via "pw").
          // If the line is equal to "backdoor", then print "names" to the client (via "pw").
        }
      }
    }
  }
}
