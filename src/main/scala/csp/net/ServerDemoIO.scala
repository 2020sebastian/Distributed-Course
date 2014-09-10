// GENERATED

package csp.net

import java.io.{InputStream,OutputStream}
import java.net.{ServerSocket,Socket}
import scala.util.Random


object ServerDemoIO {
  def main (args : Array[String]) {
    val serverPort : Int = 7000 + ((Random.nextInt % 10).abs)
    print ("serverPort = %d\n".format (serverPort))
    val ss : ServerSocket = new ServerSocket (serverPort)
    while (true) {
      handleRequest (ss.accept)
    }
  }

  def handleRequest (s : Socket) {
    val is : InputStream = s.getInputStream
    val os : OutputStream = s.getOutputStream
    var ch : Int = 0
    var count : Int = 0
    while ( { ch = is.read; ch } != -1 ) {
      count = count + 1
    }
    val msg : String = "Read %d bytes\n".format (count)
    os.write (msg.getBytes ("us-ascii"))
    os.close
    is.close
    s.close
  }
}
