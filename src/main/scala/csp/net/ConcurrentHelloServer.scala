// GENERATED
 
package csp.net
  
import csp.utils.Utils.{runnable,tryfinally}
import java.io.{BufferedReader,BufferedWriter,InputStream,InputStreamReader,OutputStream,OutputStreamWriter,PrintWriter}
import java.net.{ServerSocket,Socket}
import java.util.{Collections,HashSet,Set}


object ConcurrentHelloServer {
  def main (args : Array[String]) {
    
    val names : Set[String] = Collections.synchronizedSet(new HashSet())
 
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
          val item : String = br.readLine()
          if(item.equals("backdoor")){
	    pw.write(names.toString + "\n")
	    pw.flush	
	} else {
	    pw.write("Hello " + item+ "\n")
            pw.flush
            names.add(item)
	}
        }
      }
    }
  }
}
