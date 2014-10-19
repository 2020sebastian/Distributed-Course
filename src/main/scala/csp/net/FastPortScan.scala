// GENERATED

package csp.net

import csp.utils.Utils.{tryfinally,tryIgnore}
import java.io.{InputStream,OutputStream}
import java.net.{ServerSocket,Socket}
import java.util.{Collections,HashSet,Set}


object FastPortScan {
  // For each hostname or IP address on the command line, attempt to establish a TCP connection
  // to that address on each port 0-1023 (inclusive).
  def main (args : Array[String]) {
    val open : Set[(String,Int)] = Collections.synchronizedSet(new HashSet())

    val ts : List[Thread] = for (addr <- args.toList; port <- (0 to 1023).toList) yield {
      new Thread (new Runnable {
        def run () {
          val pair : (String,Int) = (addr, port)
    	try{	
		val s = new Socket(addr, port)
		open.add(pair)
		s.close
		
	} catch {
		case (e: Exception) =>
		}
	
	    }
      })
    }

     for (t <- ts) { t.start }
     for (t <- ts) { t.join }

    // Print the list of address and port pairs.
    println (open)
  }

}
