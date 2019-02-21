package edu.nyu.libraries.acm

import java.io.{File, FileWriter}

import org.apache.commons.io.FilenameUtils

import scala.xml.XML

case class Doc(repository: String, filename: String, unitId: String, physloc: Option[String], title: String, extent1: Option[String], extent2:Option[String], creator: Option[String],
               accessRestriction: Option[String], useRestriction: Option[String], acqinfo: Boolean, appraisal: Boolean, abstrct: Boolean,
               langcode: String, scope: Boolean, bio: Boolean, arrange: Boolean, processInfo: Boolean, langmat: Option[String],
               control: Boolean, modified: String, totalc: Int, totalIds: Int, noNormal: Int, undated: Int, dscDates: Int, noContainer: Int,
               dscTitle: Int, seriesScope: Int, seriesOrSub: Int, index: Boolean, dscIndex: Boolean, prefCite: Boolean, procInfo: Boolean, unitdate: Boolean)


class Mapper {

  val none = "None"

  def getRecords(filename: String, input: File): Unit = {

    val source = XML.loadFile(input)
    val repo = FilenameUtils.getBaseName(filename)
    val tsv = getFileWriter(repo)

    for (docs <- source) {
      for (doc <- docs \ "doc") {

        val record = Doc(
          repo,
          (doc \ "filename").text.replace("\t", "").replace("\n", ""),
          (doc \ "unitid").text.replace("\t", "").replace("\n", ""),
          getOption((doc \ "physloc").text.replace("\t", "").replace("\n", "")),
          (doc \ "title").text.replace("\t", "").replace("\n", ""),
          getOption((doc \ "extent1").text.replace("\t", "").replace("\n", "")),
          getOption((doc \ "extent2").text.replace("\t", "").replace("\n", "")),
          getOption((doc \ "creator").text.replace("\t", "").replace("\n", "")),
          getOption((doc \ "accessrestrict").text.replace("\t", "").replace("\n", "")),
          getOption((doc \ "userestrict").text.replace("\t", "").replace("\n", "")),
          getBool((doc \ "acqinfo").text.replace("\t", "").replace("\n", "")),
          getBool((doc \ "appraisal").text.replace("\t", "").replace("\n", "")),
          getBool((doc \ "abstract").text.replace("\t", "").replace("\n", "")),
          (doc \ "langcode" \ "@langcode").text.replace("\t", "").replace("\n", ""),
          getBool((doc \ "scopecontent").text.replace("\t", "").replace("\n", "")),
          getBool((doc \ "bioghist").text.replace("\t", "").replace("\n", "")),
          getBool((doc \ "arrangement").text.replace("\t", "").replace("\n", "")),
          getBool((doc \ "processinginfo").text.replace("\t", "").replace("\n", "")),
          getOption((doc \ "langmaterial").text.replace("\t", "").replace("\n", "")),
          getBool((doc \ "controlaccess").text.replace("\t", "").replace("\n", "")),
          (doc \ "modified").text.replace("\t", "").replace("\n", ""),
          (doc \ "totalc").text.toInt,
          (doc \ "totalids").text.toInt,
          (doc \ "nonormal").text.toInt,
          (doc \ "undated").text.toInt,
          (doc \ "dscdates").text.toInt,
          (doc \ "nocontainer").text.toInt,
          (doc \ "dsctitle").text.toInt,
          (doc \ "seriesscopecontent").text.toInt,
          (doc \ "seriesorsub").text.toInt,
          getBool((doc \ "index").text),
          getBool((doc \ "dscIndex").text.replace("\t", "")),
          getBool((doc \ "prefercite").text.replace("\t", "")),
          getBool((doc \ "processinfo").text.replace("\t", "")),
          getBool((doc \ "unitdate").text.replace("\t", ""))
        )

        writeEntry(record)

      }

      def getOption(text: String): Option[String] = {
        if (text.isEmpty) {
          None
        } else {
          Some(text)
        }
      }

      def getBool(text: String): Boolean = {
        if (text == "true") {
          true
        }
        else if (text == "false") {
          false
        }
        else false
      }
    }

    def writeEntry(doc: Doc): Unit = {

      tsv.write(s"${doc.unitId}\t${doc.repository}\t${doc.title}\t${doc.unitdate}" +
        s"\t${doc.extent1.getOrElse()},${doc.extent2.getOrElse()}\t${doc.creator.getOrElse(none)}" +
        s"\t${doc.control}\t${doc.abstrct}\t${doc.arrange}\t${doc.bio}\t${doc.accessRestriction.getOrElse(none)}" +
        s"\t${doc.useRestriction.getOrElse(none)}\t${doc.acqinfo}\t${doc.langmat.getOrElse(none)}\t${doc.prefCite}" +
        s"\t${doc.processInfo}\t${doc.scope}")
      tsv.write("\n")
      tsv.flush()
    }
  }

  def getFileWriter(filename: String): FileWriter = {
    val writer = new FileWriter(s"${filename}.tsv")

    writer.write(s"unitid\trepository\ttitle\tdate\textent\tcreator\taccess" +
      s"\tabstract\tarrangement\tbioghist\taccess\tuse\tsource\tlangmat\tcitation" +
      s"\tprocess\tscope\n")

    writer
  }



}
