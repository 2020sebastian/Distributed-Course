// GENERATED

package csp.net

import csp.utils.Utils.{tryfinally}
import io.netty.bootstrap.{ServerBootstrap}
import io.netty.buffer.{ByteBuf,ByteBufUtil,Unpooled}
import io.netty.channel.{ChannelFuture,ChannelFutureListener,ChannelHandlerContext,ChannelInboundHandlerAdapter,ChannelInitializer}
import io.netty.channel.ChannelHandler.{Sharable}
import io.netty.channel.nio.{NioEventLoopGroup}
import io.netty.channel.socket.{SocketChannel}
import io.netty.channel.socket.nio.{NioServerSocketChannel}
import io.netty.handler.codec.{DelimiterBasedFrameDecoder,FixedLengthFrameDecoder,LengthFieldBasedFrameDecoder,LineBasedFrameDecoder}
import io.netty.handler.codec.string.{StringDecoder,StringEncoder}
import io.netty.util.{CharsetUtil}
import java.io.{File,FileReader}
import java.net.{InetSocketAddress}
import java.util.{Date}
import java.util.regex.{Matcher,Pattern}
import scala.collection.JavaConversions._
import sys.process._

object NettyFileServer {
  def initServer (port : Int, childHandler : ChannelInitializer[SocketChannel]) {
    val group = new NioEventLoopGroup
    try {
      val b : ServerBootstrap = new ServerBootstrap
      b.group (group)
      b.channel (classOf[NioServerSocketChannel])
      b.localAddress (new InetSocketAddress (port))
      b.childHandler (childHandler)
      val f : ChannelFuture = b.bind.sync
      println ("listening on port %s".format (f.channel.localAddress))
      f.channel.closeFuture.sync
    } finally {
      group.shutdownGracefully ().sync
    }
  }

  def main (args:Array[String]) {
    initServer (7000, new ChannelInitializer[SocketChannel] {
      override def initChannel (sc : SocketChannel)  {
	println ("initChannel %s".format (sc.toString))
        // TODO:
        // This method is called whenever a new client connects.
        // The connection is represented by "sc : SocketChannel".
        // Write a prompt "> " over "sc".
	sc.pipeline.addLast ("frameDecoder", new LineBasedFrameDecoder (1024))
        sc.pipeline.addLast ("stringDecoder", new StringDecoder (CharsetUtil.UTF_8))
	sc.pipeline.addLast ("stringEncoder", new StringEncoder (CharsetUtil.UTF_8))
        sc.pipeline.addLast ("myHandler", new MyHandler)
        sc.write ("> ")
        sc.flush
	}
    })
  }


  class MyHandler extends ChannelInboundHandlerAdapter {
    
    // Execute each command and send the results to the client over "ctx".
    // You should send a new prompt "> " after each command is executed.

      override def channelRead (ctx : ChannelHandlerContext, msg : Object) {
      val in : String = msg.asInstanceOf[String]
	
      	val pGet = Pattern.compile ("^get (.*)$")
  	val pLs = Pattern.compile ("^ls (.*)$")
	val pQuit = Pattern.compile ("^quit$")	

	val mGet : Matcher = pGet.matcher (in)
        val mLs : Matcher = pLs.matcher (in)
        val mQuit : Matcher = pQuit.matcher (in)

	if (mGet.matches) {
            val key : String = mGet.group (1)
	    ctx.write(("cat " + key)!!)
	    ctx.flush
          
	   } else if (mLs.matches) {
            val key : String = mLs.group (1)
	    ctx.write(("ls " + key)!!)
	    ctx.flush
           
          } else if (mQuit.matches) {
		val f : ChannelFuture = ctx.write ("\n\nHasta La Vista Baby !\n\n\n")
                f.addListener (ChannelFutureListener.CLOSE)
	  }
	ctx.write("\n> ")
	ctx.flush
   	 }
    	override def channelInactive (ctx : ChannelHandlerContext) {
      	println ("channelInactive")
   	 }
    	override def exceptionCaught (ctx : ChannelHandlerContext, cause : Throwable) {
      	cause.printStackTrace
      	ctx.close
    }
  }
}





















