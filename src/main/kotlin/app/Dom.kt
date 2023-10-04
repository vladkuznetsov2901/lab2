package app

import java.io.FileInputStream
import java.io.FileNotFoundException
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException



fun main() {
    val fileName = "users.xml"
    try {
        val xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileName, FileInputStream(fileName))
        while (xmlr.hasNext()) {
            xmlr.next()
            if (xmlr.isStartElement) {
                println(xmlr.localName)
            } else if (xmlr.isEndElement) {
                println("/" + xmlr.localName)
            } else if (xmlr.hasText() && xmlr.text.trim { it <= ' ' }.isNotEmpty()) {
                println("   " + xmlr.text)
            }
        }
    } catch (ex: FileNotFoundException) {
        ex.printStackTrace()
    } catch (ex: XMLStreamException) {
        ex.printStackTrace()
    }
}