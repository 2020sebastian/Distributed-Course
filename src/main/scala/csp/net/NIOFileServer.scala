// GENERATED

package csp.net

import csp.utils.Utils.{chooseServerPort,printThread,runnable,tryfinally,trytrace,trytraceIgnore}
import java.io.{File,FileInputStream}
import java.net.{InetSocketAddress,ServerSocket,Socket,SocketOption,StandardSocketOptions}
import java.nio.{ByteBuffer}
import java.nio.channels.{Channels,SelectionKey,Selector,ServerSocketChannel,SocketChannel,WritableByteChannel}
import java.util.{HashMap,Set}
import java.util.regex.{Matcher,Pattern}
import scala.collection.JavaConversions._


object NIOFileServer {
  def main (args : Array[String]) {
    trytrace {
      val selector = createSelectorWithServerSocketChannel (7000)
      while (true) {
        selector.select
        handleKeys (selector.selectedKeys)
      }
    }
  }

  def createSelectorWithServerSocketChannel (serverPort : Int) : Selector = {
    val ssc = ServerSocketChannel.open
    ssc.setOption (StandardSocketOptions.SO_REUSEADDR, true.asInstanceOf[java.lang.Boolean])
    ssc.bind (new InetSocketAddress (serverPort))
    ssc.configureBlocking (false)
    val selector = Selector.open
    ssc.register (selector, SelectionKey.OP_ACCEPT)
    selector
  }

  def handleKeys (keys : Set[SelectionKey]) {
    for (key <- keys.toList) {
      trytraceIgnore ( () ) {
        if (key.isAcceptable) {
          acceptKey (key)
        } else if (key.isReadable) {
          key.attachment.asInstanceOf[Handler].read
        } else {
          ???
        }
      }
      keys.remove (key)
    }
  }

  def acceptKey (key : SelectionKey) {
    val ssc : ServerSocketChannel = key.channel.asInstanceOf[ServerSocketChannel]
    val sc : SocketChannel = ssc.accept
    println ("accepted connection from %s".format (sc.getRemoteAddress))
    sc.configureBlocking (false)
    val handler = new Handler (key.selector, sc)
    sc.register (key.selector, SelectionKey.OP_READ).attach (handler)
    writeString (sc, "> ")
  }

  // Writes String "s" to SocketChannel "sc".
  // Not thread safe, because it uses the same ByteBuffer "wb".
  val wb = ByteBuffer.allocate (16384)
  def writeString (sc : SocketChannel, s : String) {
    wb.clear
    wb.put (s.getBytes ("us-ascii"))
    wb.flip
    sc.write (wb)
    wb.clear
  }

  // Writes "length" bytes starting from "offset" in array "b" to SocketChannel "sc".
  def writeArray (sc : SocketChannel, b : Array[Byte], offset : Int, length : Int) {
    val bb = ByteBuffer.wrap (b, offset, length)
    sc.write (bb)
  }

  // Splits a string around "\n" characters.  Returns a pair.
  // The first part of the pair is the list of substrings from "s"
  // that had a "\n" at the end.  The second part is any remaining
  // characters that are not ended by "\n" in "s".
  // For example, 
  // 
  // scala> splitString ("the\nrain\nin\nspain")
  // res0: (List[String], String) = (List(the, rain, in),spain)
  //
  // scala> splitString ("the\nrain\nin\nspain\n")
  // res1: (List[String], String) = (List(the, rain, in, spain),"")
  def splitString (s : String) : (List[String], String) = {
    var start : Int = 0
    var result : List[String] = Nil
    var i = -1
    while ( { i = s.indexOf ('\n', start); i != -1 } ) {
      result = result ::: List (s.substring (start, i))
      start = i + 1
    }
    (result, if (start < s.length) s.substring (start) else "")
  }


  class Handler (selector : Selector, sc : SocketChannel) {
    // TODO:
    // You can add more field or method declarations here if you like.
    val rb = ByteBuffer.allocate (16384)
    def read () {
      assert (rb.position == 0)
      // TODO: Complete this method.
      // Read from "sc".  
      // Split that input into lines (delimited by '\n') (you can use the splitString method above).
      // Each line should be either an "ls" or "get" command as in the previous ConcurrentFileServer 
      // assignment (you need not implement the "quit" command).
      // Execute each command and send the results to the client over "sc".
      // You should send a new prompt "> " after each command is executed.
     }
  }
}
