package edu.nyu.libraries.acm

import scala.xml.{Elem, XML}

case class Doc(repository: String, filename: String, unitId: String, physloc: Option[String], title: String, extent1: String, extent2:String, creator: String,
               accessRestriction: Option[String], userRestriction: Option[String], acqinfo: Boolean, appraisal: Boolean, abstrct: Boolean,
               langcode: String, scope: Boolean, bio: Boolean, arrange: Boolean, processInfo: Boolean, langmat: Option[String],
               control: Boolean, modified: String, totalc: Int, totalIds: Int, noNormal: Int, undated: Int, dscDates: Int, noContainer: Int,
               dscTitle: Int, seriesScope: Int, seriesOrSub: Int, index: Boolean, dscIndex: Boolean)



object Main extends App {

  val tamwag = XML.loadFile("tamwag.xml")
  val records = getRecords(tamwag)

  def getRecords(file: Elem): Map[String, Doc] = {
    var records = Map.empty[String, Doc]
    
    for (docs <- tamwag) {
      for (doc <- (docs \ "doc")) {
        val record = Doc(
          "tamwag",
          (doc \ "filename").text,
          (doc \ "unitid").text,
          getOption((doc \ "physloc").text),
          (doc \ "title").text,
          (doc \ "extent1").text,
          (doc \ "extent2").text,
          (doc \ "creator").text,
          getOption((doc \ "accessRestriction").text),
          getOption((doc \ "userRestriction").text),
          getBool((doc \ "acqinfo").text),
          getBool((doc \ "appraisal").text),
          getBool((doc \ "abstract").text),
          (doc \ "langcode" \ "@langcode").text,
          getBool((doc \ "scopecontent").text),
          getBool((doc \ "bioghist").text),
          getBool((doc \ "arrangement").text),
          getBool((doc \ "processinginfo").text),
          getOption((doc \ "langmaterial").text),
          getBool((doc \ "controlaccess").text),
          (doc \ "modified").text,
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
          getBool((doc \ "dscIndex").text)
        )

        records = records + ((doc \ "unitid").text -> record)

      }

    }
    records
  }

  def getOption(text: String): Option[String] = {
    if(text.isEmpty) {
      None
    } else {
      Some(text)
    }
  }

  def getBool(text: String): Boolean = {
    if(text == "true") { true }
    else if(text == "false") { false }
    else false
  }


}