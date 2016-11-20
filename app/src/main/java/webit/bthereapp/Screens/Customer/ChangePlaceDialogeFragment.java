package webit.bthereapp.Screens.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.PlaceJSONParser;
import webit.bthereapp.R;


public class ChangePlaceDialogeFragment extends DialogFragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    AutoCompleteTextView autoCompleteCities;
    String citySelect = "",cityName;
    PlacesTask mPlacesTask;
    private ParserTask mParserTask;
    View itemView;
    private  ImageButton closeIB;
    private RelativeLayout searchB;
    private boolean isChangePlace;
    private TextView TitleChangePlace;

    public ChangePlaceDialogeFragment() {
        // Required empty public constructor
    }
    public static ChangePlaceDialogeFragment newInstance(boolean changePlace) {
        ChangePlaceDialogeFragment fragment = new ChangePlaceDialogeFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, changePlace);
        fragment.setArguments(args);
        return fragment;
    }
    public static ChangePlaceDialogeFragment instance;

    public static ChangePlaceDialogeFragment getInstance() {
        if (instance == null)
            instance = new ChangePlaceDialogeFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isChangePlace = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change_place_dialoge, container, false);
        itemView=inflater.inflate(R.layout.row_cities, container, false);
        closeIB= (ImageButton) view.findViewById(R.id.close_change_place);
        closeIB.setOnClickListener(this);
        searchB= (RelativeLayout) view.findViewById(R.id.search_button);
        searchB.setOnClickListener(this);
        TitleChangePlace= (TextView) view.findViewById(R.id.title_change_place);
        if(!isChangePlace)
            TitleChangePlace.setText(getResources().getString(R.string.pick_place));
        autoCompleteCities= (AutoCompleteTextView) view.findViewById(R.id.auto_complete_cities);
        autoCompleteCities.setThreshold(1);
        autoLocation();
        autoCompleteCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView lv = (ListView) adapterView;
                SimpleAdapter adapter = (SimpleAdapter) adapterView.getAdapter();
                HashMap<String, String> hm = (HashMap<String, String>) adapter.getItem(i);
                cityName = hm.get("description");
                autoCompleteCities.setText(cityName);
                autoCompleteCities.setEnabled(true);
                //close the key board
                if(getActivity()!=null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(autoCompleteCities.getWindowToken(), 0);
                }
            }
        });


        return view;
    }
    public void autoLocation() {
        autoCompleteCities.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlacesTask = new PlacesTask();
                mPlacesTask.execute(s.toString());
                autoCompleteCities.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_change_place:
                getActivity().onBackPressed();
                break;
            case R.id.search_button:
                sendNewLocation();
                break;
        }
    }

    private void sendNewLocation() {
if(autoCompleteCities.getText().toString().equals(""))
    Toast.makeText(getActivity(), getResources().getString(R.string.no_word_for_search), Toast.LENGTH_SHORT).show();
        else {
    Intent i = new Intent();
    i.putExtra("citySearch", autoCompleteCities.getText().toString());
    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
     getActivity().onBackPressed();
}
    }

    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";
            // Obtain browser key from
//            String key = "key=AIzaSyA6QytTUmLiHpFVLQCmgbCFZtCS1y-CmrI";
            String key = "key=AIzaSyA6QytTUmLiHpFVLQCmgbCFZtCS1y-CmrI";
            String input = "";
            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            String types = "";
            // place type to be searched
            if (citySelect != null && !citySelect.equals("")) {
                types = "geocode";
            } else {
                types = "(cities)"/*geocode*/;

            }
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?" + input + "&types=" + types + "&language=Heb&" + key;
            if(!ConnectionUtils.isLive) Log.d("url = ", url);
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
                int[] to = new int[]{R.id.text};
                for (int i = 0; i < result.size(); i++) {
                    ListForAdapter[i] = result.get(i).get("description");
                }

                //close
                SimpleAdapter adapter = new SimpleAdapter(getActivity(), result,R.layout.row_cities , from, to);
                adapter.getFilter().filter(null);
                // Setting the adapter
                autoCompleteCities.setAdapter(adapter);
            }
        }
    }
}
