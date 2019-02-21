package edu.nyu.libraries.acm

import java.io.{File, FileWriter}

import edu.nyu.libraries.acm.Mapper.MappingSupport
import edu.nyu.libraries.acm.TSV.TSVSupport

import scala.io.Source
import scala.xml.{Elem, XML}

case class Doc(repository: String, filename: String, unitId: String, physloc: Option[String], title: String, extent1: Option[String], extent2:Option[String], creator: Option[String],
               accessRestriction: Option[String], useRestriction: Option[String], acqinfo: Boolean, appraisal: Boolean, abstrct: Boolean,
               langcode: String, scope: Boolean, bio: Boolean, arrange: Boolean, processInfo: Boolean, langmat: Option[String],
               control: Boolean, modified: String, totalc: Int, totalIds: Int, noNormal: Int, undated: Int, dscDates: Int, noContainer: Int,
               dscTitle: Int, seriesScope: Int, seriesOrSub: Int, index: Boolean, dscIndex: Boolean, prefCite: Boolean, procInfo: Boolean, unitdate: Boolean)



object Main extends App with MappingSupport {

  val input = new File("tamwag.xml")
  addTags(input)


  def addTags(input: File): Unit = {
    val output = new File("output.xml")
    val writer = new FileWriter(output)

    iterate(Source.fromFile(output).getLines(), true)
    writer.close()


    def iterate(i: Iterator[String], firstLine: Boolean): Unit = {
      i.hasNext match {
        case true => {
          if(firstLine == true) {
            writer.write(i.next())
            writer.write("<docs>\n")
            writer.flush()
            iterate(i, false)
          } else {
            writer.write(i.next())
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