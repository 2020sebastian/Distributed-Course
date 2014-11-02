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
        // TODO:
        // This method is called whenever a new client connects.
        // The connection is represented by "sc : SocketChannel".
        // Write a prompt "> " over "sc".
      }
    })
  }


  class MyHandler extends ChannelInboundHandlerAdapter {
    // TODO:
    // Override methods from ChannelHandlerContext to provide file server functionality.
    // You should implement "ls", "get", and "quit" commands as in the previous ConcurrentFileServer
    // assignment.
    // Execute each command and send the results to the client over "ctx".
    // You should send a new prompt "> " after each command is executed.
  }
}

