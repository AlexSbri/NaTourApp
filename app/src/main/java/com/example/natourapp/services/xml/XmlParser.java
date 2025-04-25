package com.example.natourapp.services.xml;

import android.util.Log;
import android.util.Xml;

import com.google.android.gms.maps.model.LatLng;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class XmlParser {
    private final InputStream inputStream;
    private final List<LatLng> listLatLng;
    private XmlPullParser parser;

    public XmlParser(InputStream in){
        this.inputStream=in;
        listLatLng = new LinkedList<>();

    }

    public List<LatLng> getListLatLng() {
        try {
            return initGpxParse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<LatLng> initGpxParse() throws IOException {
        parser = Xml.newPullParser();
        try {

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            readGPXTag();

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
        return listLatLng;
    }

    private void readGPXTag() throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "gpx");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                Log.d("EVENT TYPE", parser.getEventType() + " ");
                continue;
            }
            if(parser.getName().equals("trk")) {
                readTRKTag();
            }
            else {
                skip();
            }
        }
    }

    private void readTRKTag() throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "trk");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                Log.d("EVENT TYPE", parser.getEventType() + " ");
                continue;
            }
            if(parser.getName().equals("trkseg")){
                readTRKSEGTag();
            }else {
                skip();
            }
        }
    }

    private void readTRKSEGTag() throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "trkseg");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                Log.d("EVENT TYPE", parser.getEventType() + " ");
                continue;
            }
            if(parser.getName().equals("trkpt")){
                readTRKPTTag();
            }else {
                skip();
            }
        }
    }

    private void readTRKPTTag() throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "trkpt");

        String lat = parser.getAttributeValue(null, "lat");
        String lon = parser.getAttributeValue(null, "lon");
        listLatLng.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)));
        setParser();
        parser.require(XmlPullParser.END_TAG,null,"trkpt");
    }

    private void setParser() throws XmlPullParserException, IOException {
        parser.nextTag();
        parser.nextText();
        parser.nextTag();
        parser.nextText();
        parser.nextTag();

    }


    private void skip() throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
