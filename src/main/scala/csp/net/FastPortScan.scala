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
    // TODO: Change this declaration so that "open" is not null.
    // Ensure that "open" is a synchronized data structure.
    // HINT: look in examples/src/main/scala/csp/threads/Contention.scala
    val open : Set[(String,Int)] = null

    val ts : List[Thread] = for (addr <- args.toList; port <- (0 to 1023).toList) yield {
      new Thread (new Runnable {
        def run () {
          val pair : (String,Int) = (addr, port)
          // TODO: Complete this code.  
          // Attempt to open a connection to "port" on host "addr".
          // If it succeeds, add "pair" to "open".
        }
      })
    }

    // TODO: Complete this code.
    // Start all of the threads, then wait for them all to terminate (join with them).

    // Print the list of address and port pairs.
    println (open)
  }

}
