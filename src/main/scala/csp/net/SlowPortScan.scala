// GENERATED

package csp.net

import csp.utils.Utils.{tryfinally,tryIgnore}
import java.io.{InputStream,OutputStream}
import java.net.{ServerSocket,Socket}
import scala.util.Random
import scala.collection.mutable.ListBuffer

object SlowPortScan {
  // For each hostname or IP address on the command line, attempt to establish a TCP connection
  // to that address on each port 0-1023 (inclusive).
  def main (args : Array[String]) {
    for (addr <- args) {
      println ("Scanning %s".format (addr))
	var result = new ListBuffer[Integer]()
	var i = 0;
	for (i <- 0 to 1023){
	
	try{	
		val s = new Socket(addr, i)
		result += i
		s.close
	} catch {
		case (e: Exception) =>
		}

	}	
	System.out.println("Open ports on " + addr + ": " + result.toList )
    }
  }

}
