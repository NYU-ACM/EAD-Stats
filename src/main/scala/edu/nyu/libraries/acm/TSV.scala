package edu.nyu.libraries.acm

import java.io.{File, FileWriter}

object TSV {
  trait TSVSupport {
    val outputFile = new File("tamwag.tsv")
    val writer = new FileWriter(outputFile)
    writer.write(s"unitid\trepository\ttitle\tdate\textent\tcreator\taccess points" +
      s"\tabstract\tarrangement\tbioghist\taccess\tuse\tsource\tlangmat\tcitation" +
      s"\tprocess\tscope\n")
    val none = "None"

    def writeEntry(doc: Doc): Unit = {

      writer.write(s"${doc.unitId}\t${doc.repository}\t${doc.title}\t${doc.unitdate}" +
        s"\t${doc.extent1.getOrElse()},${doc.extent2.getOrElse()}\t${doc.creator.getOrElse("NA")}" +
        s"\t${doc.control}\t${doc.abstrct}\t${doc.arrange}\t${doc.bio}\t${doc.accessRestriction.getOrElse(none)}" +
        s"\t${doc.useRestriction.getOrElse(none)}\t${doc.acqinfo}\t${doc.langmat.getOrElse(none)}\t${doc.prefCite}" +
        s"\t${doc.processInfo}\t${doc.scope}")
      writer.write("\n")
      writer.flush()
    }

    def closeWriter: Unit = {
      writer.flush
      writer.close
    }

  }
}
