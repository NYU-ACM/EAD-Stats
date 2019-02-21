package edu.nyu.libraries.acm
import java.io.{File, FileWriter}

import edu.nyu.libraries.acm.Mapper.MappingSupport
import edu.nyu.libraries.acm.TSV.TSVSupport

import scala.io.Source
import scala.xml.XML

object Main extends App with MappingSupport {

  //val input = new File("tamwag.xml")
  //addTags(input)
  getRecords(XML.loadFile("tamwag.xml"))


  def addTags(input: File): Unit = {
    val output = new File("output.xml")
    output.createNewFile()
    val writer = new FileWriter(output)

    iterate(Source.fromFile(output).getLines(), true)
    writer.flush
    writer.close()

    def iterate(i: Iterator[String], firstLine: Boolean): Unit = {
      i.hasNext match {
        case true => {
          if(firstLine == true) {
            writer.write(i.next() + "\n")
            writer.write("<docs>\n")
            writer.flush()
            iterate(i, false)
          } else {
            writer.write(i.next() + "\n")
            iterate(i, false)
          }
        }
        case false => {
          writer.write("</docs>")
          writer.flush()
        }
      }
    }
  }
}