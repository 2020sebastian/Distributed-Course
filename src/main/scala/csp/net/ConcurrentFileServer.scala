// GENERATED

package csp.net

import csp.utils.Utils.{runnable,tryfinally}
import java.io.{BufferedReader,BufferedWriter,File,FileReader,InputStream,InputStreamReader,OutputStream,OutputStreamWriter,PrintWriter}
import java.net.{ServerSocket,Socket}
import java.util.{Collections,HashSet,Set}
import java.util.regex.{Matcher,Pattern}
import scala.sys.process._

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
        var done : Boolean = false
        var line : String = null
        
	  while ( !done && { prompt; line = br.readLine; line != null } ) {
          val mGet : Matcher = pGet.matcher (line)
          val mLs : Matcher = pLs.matcher (line)
          val mQuit: Matcher = pQuit.matcher (line)
          
	  if (mGet.matches) {
            val key : String = mGet.group (1)
	    println("Sending "+ key + " to: " + s.getRemoteSocketAddress)
	    pw.write(("cat " + key)!!)
	    pw.flush
          
	   } else if (mLs.matches) {
            val key : String = mLs.group (1)
	    pw.write(("ls "+ key)!!)
	    pw.flush
           
          } else if (mQuit.matches) {
		done = true
	  }
        }
      }
    }
}
}
}

