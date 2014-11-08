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
    val numRead = sc.read(rb)
    
	if (numRead == -1) {
      	sc.close
    	
	} else if (numRead == 0) {
      	// do nothing
    	
	} else {
		if (rb.get == 10 || rb.get == 13){
      		sc.write (rb)
		} else {
		val temp = "x" * (numRead-1) + "\n"
		rb.clear
		rb.put(temp.getBytes)
		rb.flip
		sc.write(rb)
		}
    	} 
   	rb.clear
    }
  }
}











