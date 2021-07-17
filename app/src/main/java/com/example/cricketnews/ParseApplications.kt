package com.example.cricketnews

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
    val applications=ArrayList<FeedEntry>()
    fun parse(xmlData:String):Boolean{
        var status=true
        var textValue=""
        var inChannel=false
        var inItem=false

        try {
            val factory=XmlPullParserFactory.newInstance()
            factory.isNamespaceAware=true
            val xpp=factory.newPullParser()
            xpp.setInput(xmlData.reader())

            var eventType=xpp.eventType
            var currentRecord=FeedEntry()

            while (eventType!= XmlPullParser.END_DOCUMENT){
                val tagName=xpp.name?.toLowerCase()
                when(eventType){
                    XmlPullParser.START_TAG->{
                        if(tagName=="item"){
                            inItem=true
                        }
                    }

                    XmlPullParser.TEXT->{
                        textValue=xpp.text
                    }

                    XmlPullParser.END_TAG->{
                        if(inItem){
                            when(tagName){
                                "item"->{
                                    applications.add(currentRecord)
                                    inItem=false
                                    currentRecord= FeedEntry()
                                }

                                "title"->currentRecord.title=textValue
                                "description"->currentRecord.description=textValue
                                "coverimages"->currentRecord.coverImage=textValue
                                "pubdate"->currentRecord.pubDate=textValue
                                "link"->currentRecord.link=textValue
                            }
                        }
                    }
                }
                eventType=xpp.next()
            }

        }catch (e:Exception){
            status=false
        }
        return status
    }
}