// GENERATED

package csp.akka

import akka.actor.{Actor,ActorContext,ActorLogging,ActorRef,ActorSystem,Inbox,Props,Terminated}
import akka.io.{IO,Tcp}
import akka.io.Tcp.{Bind,Bound,Close,Closed,CommandFailed,Connected,PeerClosed,Received,Register,Write}
import akka.util.{ByteString}
import java.net.{InetSocketAddress}
import java.util.{TreeSet}
import scala.collection.JavaConversions._
import scala.concurrent.duration._


object Monster {
  case class HandlerAdded (handler : ActorRef)
  case class HandlerWrite (data : ByteString)
  case class HandlerReceived (data : ByteString, handler : ActorRef)
  case object MonsterMove


  def mkHandlerName (addr : InetSocketAddress) : String =
    "h-" + addr.getAddress.getHostAddress.replaceAll (":", "-") + "-" + addr.getPort

  class Server extends Actor {
    val monster = context.actorOf (Props (classOf[Monster]), "monster")
    val manager : ActorRef = IO (Tcp) (context.system)
    manager ! Bind (self, new InetSocketAddress (7000))
    def receive = {
      case m @ Connected (remote, local) => 
        println ("client connected from %s".format (remote))
        val handler = context.actorOf (Props (classOf[Handler], monster, sender), mkHandlerName (remote))
        monster ! HandlerAdded (handler)
    }
  }

  val vt100_clear_screen : Array[Byte] = Array (0x1B, 0x5B, 0x32, 0x4A)
  val vt100_move_cursor_to_home : Array[Byte] = Array (0x1B, 0x5B, 0x48)

  class Monster extends Actor {
    private val handlers : TreeSet[ActorRef] = new TreeSet
    var current : Option[ActorRef] = None
    context.system.scheduler.schedule (0.milliseconds, 2000.milliseconds, self, MonsterMove) (context.system.dispatcher)
    def chooseNew : Option[ActorRef] = {
      val len = handlers.size
      (len, current) match {
        case (0, _)        => None
        case (1, None)     => Some (handlers.first)
        case (1, Some (_)) => None
        case _             => Some (handlers.toList ((len * Math.random).asInstanceOf[Int]))
      }
    }
    def clearScreen (handler : ActorRef) {
      handler ! HandlerWrite (ByteString (vt100_clear_screen))
      handler ! HandlerWrite (ByteString (vt100_move_cursor_to_home))
    }
    def receive = {
      case m @ HandlerAdded (handler) =>
        handlers.add (handler)
        context.watch (handler)
        clearScreen (handler)
      case m @ HandlerReceived (data, handler) =>
        // TODO:
        // If the data received by the handler is "pew pew" and the monster is at the current client/handler, 
        // then send "You got me, the pain, the agony, urgh...\n" to the client's terminal (via "handler"), 
        // and print "Hit the monster" to the server terminal.
        // Otherwise, print "Nyah, nyah, nyah, nyah, nyah...\n" to the client's terminal (if the monster is not hiding),
        // and print "Missed the monster" to the server terminal.
      case m @ MonsterMove =>
        // TODO:
        // Time to move the monster.
      case m @ Terminated (terminatedHandler) =>
        println (m + " / " + sender)
        handlers.remove (terminatedHandler)
        current match {
          case Some (currentHandler) if (terminatedHandler == currentHandler) => 
            // TODO:
            // Client died, move the monster.
          case _ => 
            ()
        }
    }
  }

  class Handler (monster : ActorRef, out : ActorRef) extends Actor {
    out ! Register (self)
    def receive = {
      case m @ Received (data) => 
        monster ! HandlerReceived (data, self)
      case p @ HandlerWrite (data) => 
        out ! Write (data)
      case p @ Closed => 
        println (p)
        context.stop (self)
      case p @ PeerClosed => 
        println (p)
        context.stop (self)
    }
  }

  def main (args : Array[String]) : Unit = {
    val system : ActorSystem = ActorSystem ("MONSTER")
    system.actorOf (Props[Server], "server")
  }
}
