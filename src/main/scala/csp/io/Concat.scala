// GENERATED

package csp.io

import csp.utils.Utils.{tryfinally}
import java.io.{BufferedInputStream,BufferedOutputStream,FileInputStream,FileOutputStream,InputStream,OutputStream}


object Concat {
  def main (args : Array[String]) {
    // Convert the "args" Array to a List.
    val argsList : List[String] = args.toList
    // Get all but the last element as a new List "sources".
    val sources : List[String] = argsList.init
    // Get the last element as a String "target".
    val target : String = argsList.last
    concatFiles (sources, target)
  }

  // Concatenates contents of all files from "sources" and writes it to file "target".
  def concatFiles (sources : List[String], target : String) = {
    concatStreams (sources.map (filename => getBuffInStream (filename)), getBuffOutStream (target))
  }

  // Gets a BufferedInputStream for file "filename".
  def getBuffInStream (filename : String) : InputStream = {
    new BufferedInputStream (new FileInputStream (filename))
  }

  // Gets a BufferedOutputStream for file "filename".
  def getBuffOutStream (filename : String) : OutputStream = {
    new BufferedOutputStream (new FileOutputStream (filename))
  }

  // Concatenates contents of all InputStreams from "sources" and writes it to OutputStream "target".
  // MUST close all of the InputStreams and the OutputStream.
  def concatStreams (sources : List[InputStream], target : OutputStream) {
    // TODO: Complete this method.
	
	for (i <- 0 until sources.length){
		var numRead : Int = -1
		val buffer : Array[Byte] = new Array[Byte] (1024 * 1024)	
	
		while( { numRead = sources(i).read (buffer); numRead } != -1 ){
	        target.write(buffer, 0, numRead)
		target.flush
       	        }
	sources(i).close
	}	
	target.close
    }	
}
