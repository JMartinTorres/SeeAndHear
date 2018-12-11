package com.example.javier.seehear;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class GetImage extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog pDialog;

    public GetImage(Context contexto) {
        this.context = contexto;
    }

    @Override
    protected String doInBackground(String... paths) {

        String url = "";
        try {
            url = getGoogleImage(paths[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;


    }

    private String getGoogleImage(String ruta) throws IOException {

        final StringBuilder builder = new StringBuilder();
        String url = "";

        try {
            Document doc = Jsoup.connect(ruta).get();
            Element element = doc.getElementsByAttributeValue("data-ri", "0").first();
            Element UrlDiv = element.children().last();
            url = UrlDiv.html();
            url = url.substring(url.indexOf(",\"ou\":")+7);
            url = url.substring(0, url.indexOf(",\"ow\":")-1);
        } catch (IOException e) {
            builder.append("Error : ").append(e.getMessage()).append("\n");
        }

        return url;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity.loadImage(s);
    }
}