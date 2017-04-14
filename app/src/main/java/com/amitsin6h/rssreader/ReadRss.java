package com.amitsin6h.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by amitsin6h on 2/22/2017.
 */

public class ReadRss extends AsyncTask<Void,Void,Void>{
    Context context;
    ProgressDialog progressDialog;
    ArrayList<FeedItem> feeds;
    RecyclerView recyclerView;
    String address="http://www.sciencemag.org/rss/news_current.xml";
    URL url;

    public ReadRss(Context context, RecyclerView recyclerView){
        this.recyclerView=recyclerView;
        this.context=context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
    }


    @Override
    protected void onPreExecute() {

        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

        MyAdapter adapter=new MyAdapter(context,feeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(20));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(Getdata());
        return null;
    }

    private void ProcessXml(Document data) {
        if(data != null){
            feeds = new ArrayList<>();
            Element rootElement = data.getDocumentElement();
            Node channel = rootElement.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i=0; i<items.getLength(); i++){
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")){
                    FeedItem feedItem = new FeedItem();
                    NodeList itemChilds = currentChild.getChildNodes();
                    for (int j=0; j<itemChilds.getLength(); j++){
                        Node current = itemChilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")){
                            feedItem.setTitle(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            feedItem.setDescription(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("pubDate")){
                            feedItem.setPubDate(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("link")){
                            feedItem.setLink(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")){
                            //this will return us thumbnail url
                            String url=current.getAttributes().item(0).getTextContent();
                            feedItem.setThumbnailUrl(url);
                        }
                    }

                    feeds.add(feedItem);

                }
            }

        }
    }

    public  Document Getdata(){
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDom = builder.parse(inputStream);
            return xmlDom;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
