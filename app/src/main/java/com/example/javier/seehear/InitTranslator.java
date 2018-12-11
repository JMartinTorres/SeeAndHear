/*
package com.example.javier.seehear;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class InitTranslator extends AsyncTask<String, String, String> {

    private ProgressDialog pDialog;

    private Translation translation;
    private boolean translate = false;

    @Override
    protected String doInBackground(String... paths) {

        if (paths.length == 0)
            MainActivity.setTranslator(TranslateOptions.getDefaultInstance().getService());
        else if (paths.length == 1) {
            translation =
                    MainActivity.getTranslator().translate(
                            paths[0].toString(),
                            Translate.TranslateOption.sourceLanguage("es"),
                            Translate.TranslateOption.targetLanguage("en"));
        }

        return new String();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!translate) {
            translate = true;
        } else {
            MainActivity.setTranslation(translation.getTranslatedText());
        }
    }

}*/
