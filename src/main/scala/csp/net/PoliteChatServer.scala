// GENERATED

package csp.net

import java.io.{InputStream,OutputStream}
import java.net.{ServerSocket,Socket}
import scala.util.Random


object PoliteChatServer {
  def main (args : Array[String]) {
    val serverPort : Int = 7000 + ((Random.nextInt % 10).abs)
    print ("serverPort = %d\n".format (serverPort))
    val ss : ServerSocket = new ServerSocket (serverPort)
    while (true) {
      handleRequest (ss.accept)
    }
  }

  // Alternately:
  // 1. Read one line from standard input and write it to Socket.
  // 2. Read one line of ASCII-encoded text (up to first \n byte) from Socket and write it to standard output.
  // The reading/writing in (2) MUST input/output each character as soon as it arrives on the Socket,
  // so each character will appear immediately on the server (if a socat client in raw mode is used).
  // It is not possible to put Java/Scala into a raw input mode, so you should use readLine as shown in the
  // example below to read lines from the server's standard input.
  def handleRequest (s : Socket) {
    // TODO: Complete this method.  
    // Here is a sample use of readLine:
    val line : String = System.console.readLine ("Your turn> ")
  }
}
