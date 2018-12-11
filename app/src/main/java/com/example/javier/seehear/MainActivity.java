package com.example.javier.seehear;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
//import com.google.cloud.translate.*;
//import com.google.cloud.translate.Translate.TranslateOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    private static final String HTTP_GIMAGES_QUERY = "https://www.google.com/search?tbm=isch&q=";
    private static final ArrayList<String> LANGUAGES = new ArrayList<>(Arrays.asList("Afrikaans","Albanian","Amharic","Arabic","Armenian","Azerbaijan","Bashkir","Basque","Belarusian","Bengali","Bosnian","Bulgarian","Burmese","Catalan","Cebuano","Chinese","Croatian","Czech","Danish","Dutch","English","Esperanto","Estonian","Finnish","French","Galician","Georgian","German","Greek","Gujarati","Haitian (Creole)","Hebrew","Hill Mari","Hindi","Hungarian","Icelandic","Indonesian","Irish","Italian","Japanese","Javanese","Kannada","Kazakh","Khmer","Korean","Kyrgyz","Laotian","Latin","Latvian","Lithuanian","Luxembourgish","Macedonian","Malagasy","Malay","Malayalam","Maltese","Maori","Marathi","Mari","Mongolian","Nepali","Norwegian","Papiamento","Persian","Polish","Portuguese","Punjabi","Romanian","Russian","Scottish","Serbian","Sinhala","Slovakian","Slovenian","Spanish","Sundanese","Swahili","Swedish","Tagalog","Tajik","Tamil","Tatar","Telugu","Thai","Turkish","Udmurt","Ukrainian","Urdu","Uzbek","Vietnamese","Welsh","Xhosa","Yiddish"));
    private static final ArrayList<String> CODES = new ArrayList<>(Arrays.asList("af","sq","am","ar","hy","az","ba","eu","be","bn","bs","bg","my","ca","ceb","zh","hr","cs","da","nl","en","eo","et","fi","fr","gl","ka","de","el","gu","ht","he","mrj","hi","hu","is","id","ga","it","ja","jv","kn","kk","km","ko","ky","lo","la","lv","lt","lb","mk","mg","ms","ml","mt","mi","mr","mhr","mn","ne","no","pap","fa","pl","pt","pa","ro","ru","gd","sr","si","sk","sl","es","su","sw","sv","tl","tg","ta","tt","te","th","tr","udm","uk","ur","uz","vi","cy","xh","yi"));
    private EditText searchTerm;
    private static TextView translatedTerm;
    private static ImageView searchImage;
    private Spinner spnSource, spnTarget;
    private Button btnSearch;
    private FloatingActionButton btnTTS;

    private TextToSpeech tts;

    //private static Translate translator;
    private String sourceLang = "en";
    private String targetLang = "es";

    private String lastSearchedTerm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        translatedTerm = findViewById(R.id.txtTranslated);
        searchTerm = findViewById(R.id.txtSearch);
        searchImage = findViewById(R.id.imgSearch);
        btnSearch = findViewById(R.id.btnSearch);
        spnSource = findViewById(R.id.spnSource);
        spnTarget = findViewById(R.id.spnTarget);
        btnTTS = findViewById(R.id.btnTTS);

        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });

        // Spinners

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        LANGUAGES); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnSource.setAdapter(spinnerArrayAdapter);
        spnTarget.setAdapter(spinnerArrayAdapter);


        spnSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnSource.setSelection(position);
                sourceLang = CODES.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spnTarget.setSelection(position);
                targetLang = CODES.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnTarget.setSelection(74);
        spnSource.setSelection(20);

        // Buttons

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!searchTerm.getText().toString().equals(lastSearchedTerm)) {
                    searchImage(searchTerm.getText().toString());
                    lastSearchedTerm = searchTerm.getText().toString();
                    }

                    new YandexTranslator(context).execute(searchTerm.getText().toString(), sourceLang + "-" + targetLang);
                    btnTTS.setVisibility(View.VISIBLE);

            }
        });

        btnTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(translatedTerm.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        // Imagen inicial
        Picasso.get().load("https://www.recetasderechupete.com/wp-content/uploads/2018/01/Pan-casero-f%C3%A1cil-525x360.jpg").into(searchImage);

    }

   /* public static Translate getTranslator() {
        return translator;
    }

    public static void setTranslator(Translate translator) {
        MainActivity.translator = translator;
    }
*/
    public static void setTranslation(String translation) {
        MainActivity.translatedTerm.setText(translation);
    }

    private static void searchImage(String httpQuery) {
        httpQuery.replace(" ", "%20");
        new GetImage(context).execute(HTTP_GIMAGES_QUERY + httpQuery);
    }

    public static void loadImage(String imageUrl) {
        Picasso.get().load(imageUrl).into(searchImage);
    }

}
