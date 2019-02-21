package edu.nyu.libraries.acm

import edu.nyu.libraries.acm.TSV.TSVSupport

import scala.xml.Elem

object Mapper extends TSVSupport {

  trait MappingSupport {
    def getRecords(tamwag: Elem): Unit = {

      for (docs <- tamwag) {
        for (doc <- docs \ "doc") {

          val record = Doc(
            "tamwag",
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
  }
}
