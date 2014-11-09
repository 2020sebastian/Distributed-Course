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

object TriviaServer {

  var players = 0

  def initServer (port : Int, childHandler : ChannelInitializer[SocketChannel]) {
    val group = new NioEventLoopGroup
    try {
      val b : ServerBootstrap = new ServerBootstrap
      b.group (group)
      b.channel (classOf[NioServerSocketChannel])
      b.localAddress (new InetSocketAddress (port))
      b.childHandler (childHandler)
      val f : ChannelFuture = b.bind.sync
      println("**********************************************")
      println("\nTrivia Server v0.5\n")
      println("**********************************************")

      println ("\nListening for Players on port %s".format (f.channel.localAddress))
      f.channel.closeFuture.sync
    } finally {
      group.shutdownGracefully ().sync
    }
  }

  def main (args:Array[String]) {
    initServer (7000, new ChannelInitializer[SocketChannel] {
      override def initChannel (sc : SocketChannel)  {
	println ("Player Connected... %s".format (sc.toString))
  players = players + 1

      
	      sc.pipeline.addLast ("frameDecoder", new LineBasedFrameDecoder (1024))
        sc.pipeline.addLast ("stringDecoder", new StringDecoder (CharsetUtil.UTF_8))
	      sc.pipeline.addLast ("stringEncoder", new StringEncoder (CharsetUtil.UTF_8))
        sc.pipeline.addLast ("myHandler", new MyHandler)
        sc.write("\n*************************************************************")
        sc.write ("\nWelcome to TriviaServer\n\n")
        sc.write ("For a list of available commands type: !help\n")
        sc.write("\n> ")
        sc.flush
	   }
    })
  }


  class MyHandler extends ChannelInboundHandlerAdapter {
    
      override def channelRead (ctx : ChannelHandlerContext, msg : Object) {
      val in : String = msg.asInstanceOf[String]
	
      	val pHelp = Pattern.compile ("^!help")
	      val pQuit = Pattern.compile ("^quit$")	
        val pNumber = Pattern.compile ("^!players")
        val pNext = Pattern.compile ("^!next")



	      val mHelp : Matcher = pHelp.matcher (in)        
        val mQuit : Matcher = pQuit.matcher (in)
        val mNumber : Matcher = pNumber.matcher (in)
        val mNext : Matcher = pNext.matcher (in)





	      if (mHelp.matches) {
             ctx.write("\n***************  List of available commands  *******************\n")
             ctx.write("<!players>         - displays the number of players connected\n")
	           ctx.write("<!next>            - check when the next Trivia game will start\n")
             ctx.write("<!submit 'answer'> - submits answer to the current question\n")
	           ctx.flush
           }
        if (mNumber.matches){
              ctx.write("\nThere are currently " + players + " players connected.\n")
            }

        if (mNext.matches){
              ctx.write("\nNext game is starting in: <Timer> Not yet implemented\n")
            }



	     else if (mQuit.matches) {
		            val f : ChannelFuture = ctx.write ("\n\nSee you next time !\n\n\n")
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





















