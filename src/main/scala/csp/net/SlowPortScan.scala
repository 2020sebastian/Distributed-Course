// GENERATED

package csp.net

import csp.utils.Utils.{tryfinally,tryIgnore}
import java.io.{InputStream,OutputStream}
import java.net.{ServerSocket,Socket}
import scala.util.Random


object SlowPortScan {
  // For each hostname or IP address on the command line, attempt to establish a TCP connection
  // to that address on each port 0-1023 (inclusive).
  def main (args : Array[String]) {
    for (addr <- args) {
      println ("Scanning %s".format (addr))
      // TODO: Complete this method.  
      // Scan for open ports in the range 0-1023 (inclusive) on host given by "addr : String".
      // Then print out the list of open ports for host "addr".
    }
  }

}
