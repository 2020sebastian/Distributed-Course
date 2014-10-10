// GENERATED

package csp.net

import csp.utils.Utils.{runnable,tryfinally}
import java.io.{BufferedReader,BufferedWriter,File,FileReader,InputStream,InputStreamReader,OutputStream,OutputStreamWriter,PrintWriter}
import java.net.{ServerSocket,Socket}
import java.util.{Collections,HashSet,Set}
import java.util.regex.{Matcher,Pattern}


object ConcurrentFileServer {
  def main (args : Array[String]) {
    val serverPort : Int = 7000
    val ss : ServerSocket = new ServerSocket (serverPort)
    while (true) {
      val s : Socket = ss.accept
      new Thread (() => handleRequest (s)).start
    }
  }

  val pGet = Pattern.compile ("^get (.*)$")
  val pLs = Pattern.compile ("^ls (.*)$")
  val pQuit = Pattern.compile ("^quit$")

  def handleRequest (s : Socket) {
    val buffer = new Array[Char] (16384)
    tryfinally (s) { (s : Socket) =>
      println ("Connection from: " + s.getRemoteSocketAddress)
      tryfinally (new BufferedReader (new InputStreamReader (s.getInputStream))) { (br : BufferedReader) => 
        tryfinally (new PrintWriter (new BufferedWriter (new OutputStreamWriter (s.getOutputStream)))) { (pw : PrintWriter) => 
          def prompt = { 
            pw.print ("> ")
            pw.flush 
          }
          // TODO: Complete this method.  
          
          // Read a line from the client.  Process it as described in homework.org.  Repeat until 
          // end of file from the client, or the client sends the command "quit".
          
          // HINT: Look at examples/src/main/scala/csp/net/ConcurrentHashMapServer.scala for how to 
          // read a sequence of commands (one per line) and then use regular expression pattern-matching
          // to get data from the command line.  The regular expressions are already defined above for you.

        }
      }
    }
  }
}
