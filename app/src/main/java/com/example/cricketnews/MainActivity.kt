package com.example.cricketnews

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry{
    var docTitle:String=""
    var title:String=""
    var description:String=""
    var coverImage:String=""
    var link:String=""
    var pubDate:String=""

    override fun toString(): String {
        return """
            docTitle=$docTitle
            title=$title
            description=$description
            pubDate=$pubDate
            link=$link
            """.trimIndent()
    }
}

class MainActivity : AppCompatActivity() {
    val TAG="mainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloadData=DownloadData(this,xmlListView)
        downloadData.execute("https://www.espncricinfo.com/rss/content/story/feeds/0.xml")

    }


    companion object {
         class DownloadData(context: Context,listView: ListView) : AsyncTask<String, Void, String>(){
            private val TAG="downloadData"

             var propContext: Context by Delegates.notNull()
             var propListView: ListView by Delegates.notNull()

             init {
                 propContext=context
                 propListView=listView
             }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                var parseApplication=ParseApplications()
                parseApplication.parse(result)

                var feedAdapter=FeedAdapter(propContext,R.layout.list_record,parseApplication.applications)
                propListView.adapter=feedAdapter
            }

            override fun doInBackground(vararg url: String?): String {
                val rssFeed=downloadXml(url[0])
                if(rssFeed.isEmpty()){
                    Log.e(TAG,"Error in downloading")
                }
                return rssFeed
            }

             private fun downloadXml(url:String?): String {
                 return URL(url).readText()
             }
         }
    }
}