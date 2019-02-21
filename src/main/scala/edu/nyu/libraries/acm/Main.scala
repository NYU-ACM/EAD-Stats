package edu.nyu.libraries.acm

import java.io.{File, FileWriter}
import scala.io.Source

object Main extends App {




  val inputFile = new File(args(0))
  addTags(inputFile)

  val mapper = new Mapper

  mapper.getRecords(inputFile)

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
            println(i.next())
            println("<docs>\n")
            iterate(i, false)
          } else {
            println(i.next() + "\n")
            iterate(i, false)
          }
        }
        case false => {
          println("</docs>")
        }
      }
    }
  }
}