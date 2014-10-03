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
      System.out.println("Waiting for connections ...")
      handleRequest (ss.accept)
    }
  }


  def handleRequest (s : Socket) {
    System.out.println("Connection established.")
    val is : InputStream = s.getInputStream
    val os : OutputStream = s.getOutputStream  
    var status : Integer = 0
    while (true){
    var line : String = System.console.readLine ("Your turn> ")
	if (line == null){
	status = 1
	return
	}
	if (line.length <= 1){
		os.write("?\n".getBytes("us-ascii"))
		os.write("Your turn> ".getBytes("us-ascii"))
		os.flush
	} else {
		line += "\n"
		os.write(line.getBytes("us-ascii"))
		os.write("Your turn> ".getBytes("us-ascii"))
		os.flush
    }
    var ch : Int = 0
    var bytesRead : Int = 0
  
 	while ( { ch = is.read; ch } != 10 ){
		if (status == 1) return	        
		System.out.write(ch)
        	System.out.flush
		bytesRead += 1
        }
	if (bytesRead == 0 && status != 1){
		System.out.write("?\n".getBytes("us-ascii"))
		System.out.flush
	} else {
	System.out.write("\n".getBytes("us-ascii"))
	System.out.flush
	}
   
  }
   is.close
   os.close
}
}





