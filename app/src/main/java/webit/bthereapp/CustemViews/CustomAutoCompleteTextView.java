package webit.bthereapp.CustemViews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.PlaceJSONParser;
import webit.bthereapp.R;

/**
 * Created by User on 18/07/2016.
 */
public class CustomAutoCompleteTextView extends LinearLayout {
    private View view;
    private Context mContext;
    private AutoCompleteTextView autoCompleteCity, autoCompleteAdress;
    private ParserTask mParserTask;
    private PlacesTask mPlacesTask;
    int cityId = -1;
    String cityName,AdressName;
    boolean isCity=true;
    String fontType;
    String citySelect = "";
    private String Tag;

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        if (view == null) {
            view = View.inflate(context, R.layout.custom_auto_complete_text_view, this);
            this.mContext = context;
            autoCompleteCity = (AutoCompleteTextView) view.findViewById(R.id.SelectCity);
            autoCompleteAdress = (AutoCompleteTextView) view.findViewById(R.id.SelectAdress);
            autoCompleteCity.setHintTextColor(Color.GRAY);
            autoCompleteAdress.setHintTextColor(Color.GRAY);
            autoCompleteAdress.setEnabled(false);
            autoCompleteCity.setHint(autoCompleteCity.getHint()+"*");
            autoCompleteAdress.setHint(autoCompleteAdress.getHint()+"*");
            fontType = "OpenSansHebrew-Light.ttf";
            autoCompleteCity.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/" + fontType));
            autoCompleteAdress.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/" + fontType));
            initValueToSpinerCity();
        }
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public void setFilter(InputFilter[] inputFilters) {
        autoCompleteCity.setFilters(inputFilters);
    }

    private void initValueToSpinerCity() {
        autoLocation();

        autoCompleteCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView lv = (ListView) adapterView;
                SimpleAdapter adapter = (SimpleAdapter) adapterView.getAdapter();
                HashMap<String, String> hm = (HashMap<String, String>) adapter.getItem(i);
                cityName = hm.get("description");
                cityId = 1;
                autoCompleteCity.setText(cityName);
                autoCompleteAdress.setEnabled(true);
                if(citySelect !=null&&!citySelect.equals("")){
                    if(!citySelect.equals(cityName)) {
                        citySelect = "";
                        autoCompleteAdress.setText("");
                    }
                }
            }
        });

        autoCompleteAdress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SimpleAdapter adapter = (SimpleAdapter) adapterView.getAdapter();
                HashMap<String, String> hm = (HashMap<String, String>) adapter.getItem(i);
                AdressName = hm.get("description"/*"name"*/);
                cityId = 1;
                autoCompleteAdress.setText(AdressName);
            }
        });
    }


    public void autoLocation() {
        autoCompleteCity.setThreshold(1);
        autoCompleteCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isCity=true;
                mPlacesTask = new PlacesTask();
                mPlacesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        autoCompleteAdress.setThreshold(1);
        autoCompleteAdress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(cityName!=null) {
                    citySelect = cityName;
                }
                isCity=false;
                mPlacesTask = new PlacesTask();
                mPlacesTask.execute(citySelect+s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();
        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    public boolean valid() {
        if (autoCompleteCity.getText().toString().trim().length() == 0) {
            autoCompleteCity.setError(mContext.getString(R.string.adrerror));
            return false;
        }
        if (autoCompleteCity.getText() != null && autoCompleteCity.getText().length() < 2 && !autoCompleteCity.getText().subSequence(0, 1).equals(" ")) {
            autoCompleteCity.setError(mContext.getString(R.string.adrerror));
            return false;
        }
        if (autoCompleteAdress.getText().toString().trim().length() == 0) {
            autoCompleteAdress.setError(mContext.getString(R.string.adrerror));
            autoCompleteAdress.setEnabled(true);
            return false;
        }
        if (autoCompleteAdress.getText() != null && autoCompleteAdress.getText().length() < 2 && !autoCompleteAdress.getText().subSequence(0, 1).equals(" ")) {
            autoCompleteAdress.setError(mContext.getString(R.string.adrerror));
            autoCompleteAdress.setEnabled(true);
            return false;
        }
        return true;
    }

    public String getTextCity() {
        return String.valueOf(autoCompleteCity.getText());
    }

    public void setTextCity(String text) {
        autoCompleteCity.setText(text);
        citySelect=text;
    }
    public String getTextAdress() {
        return String.valueOf(autoCompleteAdress.getText());
    }

    public void setTextAdress(String text) {
        autoCompleteAdress.setText(text);
    }

    public void setError(String text) {
        autoCompleteCity.setError(text);
        autoCompleteAdress.setError(text);
    }

    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            String data = "";
            String key = "key=AIzaSyA6QytTUmLiHpFVLQCmgbCFZtCS1y-CmrI";
            String input = "";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            String types = "";
            // place type to be searched
            if (citySelect!=null&&!citySelect.equals("")) {
                types = "geocode";
            } else {
                types = "(cities)"/*geocode*/;

            }
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?"+input+"&types="+types+"&language=Heb&"+key;
            if(!ConnectionUtils.isLive)Log.d("url = ",url);
            try {
                // Fetching the data from we service
                data = downloadUrl(url);
            } catch (Exception e) {
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            mParserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            mParserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(
                String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try {
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

            } catch (Exception e) {
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {
            if (result != null) {
                String[] ListForAdapter = new String[result.size()];
                String[] from = new String[]{"description"};
                int[] to = new int[]{android.R.id.text1};
                for (int i = 0; i < result.size(); i++) {
                    ListForAdapter[i] = result.get(i).get("description");
                }

                SimpleAdapter adapter = new SimpleAdapter(mContext, result, android.R.layout.simple_list_item_1, from, to);
                adapter.getFilter().filter(null);
                // Setting the adapter
                autoCompleteCity.setAdapter(adapter);
                if(isCity) {
                    autoCompleteCity.setAdapter(adapter);
                }else{
                    autoCompleteAdress.setAdapter(adapter);
                }
            }
        }
    }
}
