// GENERATED

package csp.net

import csp.utils.Utils.{chooseServerPort,printThread,runnable,trytrace,trytraceIgnore}
import java.net.{InetSocketAddress,ServerSocket,Socket,SocketOption,StandardSocketOptions}
import java.nio.{ByteBuffer}
import java.nio.channels.{Channels,SelectionKey,Selector,ServerSocketChannel,SocketChannel,WritableByteChannel}
import java.util.{Set}
import scala.collection.JavaConversions._


object NIOCensor {
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
  }

  class Handler (selector : Selector, sc : SocketChannel) {
    val rb = ByteBuffer.allocate (16384)
    def read () {
      assert (rb.position == 0)
      // TODO: Complete this method.
      // Read from "sc", then write the same number of bytes back to the client.
      // You should assume an ASCII encoding for the data, i.e., each byte is one character.
      // For each character read from the client that is neither '\r' nor '\n', write 'x' back to the client.
      // For each character '\r' or '\n' read from the client, write the same character '\r' or '\n' back to the client.
    }
  }
}
