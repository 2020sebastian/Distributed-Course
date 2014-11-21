package csp.akka

import akka.actor.{Actor,ActorContext,ActorLogging,ActorRef,ActorSystem,Inbox,Props,Terminated}
import akka.io.{IO,Tcp}
import akka.io.Tcp.{Bind,Bound,Close,Closed,CommandFailed,Connected,PeerClosed,Received,Register,Write}
import akka.util.{ByteString}
import java.net.{InetSocketAddress}
import scala.concurrent.duration._
import scala.util.Random


object TriviaServer {
  case class CreateClient (remote : InetSocketAddress, out : ActorRef)
  case class ClientWrite (data : ByteString)
  case object NewQuestion
  
  val pattern = "^!(.*)$".r
  var expectedAnswer = ""
  var currentQuestion = ""
  var playerID = 0
  var totalPlayers = 0
  var startGame = false
  var pause = false

  val questions = Map("james gosling" -> "Name the primary creator of the Java programming language.",
                       "1970" -> "What is the year of the Unix epoch?",
                       "1998" -> "What year was Google founded?",
                       "quicksort"->"If you need to sort a very large list of integers (billions), what efficient sorting algorithm would be your best bet?",
                       "cisco" -> "Which company is the largest manufacturer of network equipment?",
                       "ibm" -> "Which company invented the floppy disk?",
                       "tux" -> "What is the name of Linux’s Mascot",
                       "nibble" -> "What name is given to half of a Byte (4 bits)?",
                       "modulation and demodulation" -> "From the computer world: What does the word 'modem' abbreviate?",
                       "1969" -> "In what year did ARPANET became operational?",
                       "philips" -> "Which company first manufactured CDs")
                      

  def mkHandlerName () : String =
    "Player" + playerID
  
  def printServerWelcome = {
    println("\n**********************************************")
    println("          Trivia Server v0.1")
    println("**********************************************")
    println ("\nWaiting for Players to connect ... \n")
  }


  class Server extends Actor {
    val forwarder : ActorRef = context.actorOf (Props[Forwarder], "forwarder")
    val manager : ActorRef = IO (Tcp) (context.system)
    manager ! Bind (self, new InetSocketAddress (7000))
    
    context.system.scheduler.schedule (0.milliseconds, 5000.milliseconds, self, NewQuestion) (context.system.dispatcher)
    
    printServerWelcome
    
    def receive = {
      
      case m @ Connected (remote, local) =>
        playerID = remote.getPort
        totalPlayers += 1 
        val text = "Player "+ playerID +" has joined.\n"
        print (text)
        forwarder ! CreateClient (remote, sender)
        if (totalPlayers != 1)
            forwarder ! ClientWrite (ByteString (text))
      
      case m @ NewQuestion =>
        if (startGame == true && pause == false){
            expectedAnswer = Random.shuffle(questions.keys.toList).head
            currentQuestion = questions.getOrElse(expectedAnswer, "")
            forwarder ! ClientWrite (ByteString (currentQuestion + "\n"))
            pause = true
      }
    }
  }

  class Forwarder extends Actor {
    def receive = {
      case CreateClient (remote, out) =>
        context.actorOf (Props (classOf[Handler], remote, out), mkHandlerName )
      case m @ ClientWrite (data) =>
        for (h <- context.children) {
          h ! m
        }
    }
  }

  class Handler (remote : InetSocketAddress, out : ActorRef) extends Actor {
    out ! Register (self)

    self ! ClientWrite (ByteString ("\n*************************************************************"))
    self ! ClientWrite (ByteString ("\nWelcome to TriviaServer\n"))
    self ! ClientWrite (ByteString ("You are player "+ playerID +"\n"))
    self ! ClientWrite (ByteString ("For a list of available commands type: !help\n"))
    self ! ClientWrite (ByteString ("\n*************************************************************\n"))

    def receive = {

      case m @ ClientWrite (data) => 
        out ! Write (data)

      case m @ Received (data) => 
        println ("received %d bytes from %s".format (data.length, self.path.name))
        val text : String = data.decodeString ("utf-8").trim
        text match {
          case "!bye" =>
           totalPlayers -= 1
            out ! Close
          case "!players" => 
            self ! ClientWrite (ByteString ("\nCurrent players: "+ totalPlayers +"\n"))
          case "!whoami" => 
            self ! ClientWrite (ByteString ("You are player: %s\n".format (remote.getPort)))
          case "!help" => 
            self ! ClientWrite (ByteString ("\n***************  List of available commands  *******************\n"))
            self ! ClientWrite (ByteString ("<!start>           - start a new Trivia Game\n"))
            self ! ClientWrite (ByteString ("<!stop>            - stops the current game\n"))
            self ! ClientWrite (ByteString ("<!players>         - displays the number of players connected\n"))
            self ! ClientWrite (ByteString ("<!bye>             - exit the trivia game\n"))
            self ! ClientWrite (ByteString ("<!whoami>          - lists information about current player\n"))
            

          case "!start" => 
            if (startGame == false) {
            context.parent ! ClientWrite (ByteString("New Game Starting\n"))
            Thread.sleep(2000)
            context.parent ! ClientWrite (ByteString("Remember to type your answer in lowercase letters.\n"))
            Thread.sleep(1000)
            context.parent ! ClientWrite (ByteString("Go !!!\n"))
            startGame = true
          } else {
            self ! ClientWrite (ByteString ("Game is already running\n"))
          }
            
          case "!stop" => 
            startGame = false
            pause = false
            context.parent ! ClientWrite (ByteString("Game stopped by player "+remote.getPort+".\n"))
          case _ => 
            
            val d : String = data.decodeString ("utf-8").trim

            if(startGame == false){
                self ! ClientWrite (ByteString("Be patient.. the game hasn't started yet\n"))
            } else {

              if (d.equals(expectedAnswer)){
                 self ! ClientWrite (ByteString ("Correct !!!\n"))
                 context.parent ! ClientWrite (ByteString("Player "+ remote.getPort + " answerred correctly: "+expectedAnswer+ "\n"))
                 pause = false
                 context.parent ! ClientWrite (ByteString("\n**************\n"))
                 context.parent ! ClientWrite (ByteString("Next Question: \n"))
                 context.parent ! ClientWrite (ByteString("**************\n\n"))
              } else {
                self ! ClientWrite (ByteString ("Try again !\n"))
              
              }
            

            }
        }
      case r @ NewQuestion => 
         context.parent ! ClientWrite (ByteString ("New Question"))
      case p @ Closed => 
        println (p)
        context.stop (self)
      case p @ PeerClosed => 
        println (p)
        context.stop (self)
    }
  }

  def main (args : Array[String]) : Unit = {
    val system : ActorSystem = ActorSystem ("Chat")
    system.actorOf (Props[Server], "server")
  }
}
