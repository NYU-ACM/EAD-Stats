package edu.nyu.libraries.acm

import java.io.{File, FileReader, FileWriter}

import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource

import scala.io.Source

object Main extends App {

  val inputFile = new File(args(0))
  addTags(inputFile)

  def addTags(input: File): Unit = {
    val output = new File("output.xml")
    val writer = new FileWriter(output)
    iterate(Source.fromFile(input).getLines(), true)
    writer.flush()
    writer.close()

    checkWellFormed(output) match {
      case true => {
        val mapper = new Mapper
        mapper.getRecords(input.getName(), output)
      }
      case false => throw new Exception("BLARGH")
    }

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

  def checkWellFormed(file: File): Boolean = {
    try {
      val dbf = DocumentBuilderFactory.newInstance()
      val builder = dbf.newDocumentBuilder()
      val is = new InputSource(new FileReader(file))
      val d = builder.parse(is)
      true
    } catch {
      case (e: Exception) => false
    }
  }

}