package com.example.www.rssreader.Services;

import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.example.www.rssreader.Models.Item;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GetCbcTopStoriesAsync extends AsyncTask<String, Integer, Void> {

    List<Item> _items;
    BaseAdapter _adapter;

    public GetCbcTopStoriesAsync(List<Item> items, BaseAdapter adapter) {
        _items = items;
        _adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            URL url = new URL("http://www.cbc.ca/cmlink/rss-topstories");
            URLConnection conn = url.openConnection();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            NodeList nodes = doc.getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                _items.add(new Item(
                        GetText("title", element),
                        GetText("pubDate", element),
                        GetText("author", element),
                        GetImageUrl(element),
                        GetText("link", element)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        _adapter.notifyDataSetChanged();
    }

    String GetText(String node, Element element) {
        NodeList nodes = element.getElementsByTagName(node);
        String result = "";
        if (nodes.getLength() > 0) {
            Element line = (Element) nodes.item(0);
            result = line.getTextContent();
        }
        return result;
    }

    String GetImageUrl(Element element) {
        String description = GetText("description", element);
        return ExtractImageUrl(description);
    }

    String ExtractImageUrl(String input) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(input));
            int eventType = 0;
            eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("img")) {
                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            if (xpp.getAttributeName(i).equals("src")) {
                                return xpp.getAttributeValue(i);
                            }
                        }
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
