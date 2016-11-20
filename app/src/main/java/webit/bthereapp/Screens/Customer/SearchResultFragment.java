package webit.bthereapp.Screens.Customer;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustemLightAutoCompleteTextView;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.General.MyLocation;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;

public class SearchResultFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String search = "param1";

    ListView lv;
    Context context;
    private RelativeLayout distance_l, ranking_l;
    private ImageView distance_image, ranking_image;
    private CustomBoldTextView distance_txt, ranking_txt;
    public ArrayList<SearchResulstsObj> resulstsObjs;
    private CustemLightAutoCompleteTextView autoComplete;
    private RelativeLayout searchButton;
    private SearchResultListAdapter searchResultListAdapter;
    private TextView sumResultTV;
    private ArrayList<String> listAutoC;
    private ArrayAdapter<String> adp;
    String[] data;
    private boolean showList = true, b = true;
    private Button changePlaceB;
    private View view = null;
    private String getSearchWord;
    boolean regularSearch = true;
    JSONObject jsonObj;


    private boolean hasLocation = false;
    MyLocation locHelper;
    String nvLatitude, nvLongitude;


    public static SearchResultFragment newInstance(String word) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(search, word);
        fragment.setArguments(args);
        return fragment;
    }


    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment instance;

    public static SearchResultFragment getInstance() {
        if (instance == null)
            instance = new SearchResultFragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            searchWord = getArguments().getString(search);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        if (!ConnectionUtils.return_from_camera&&getActivity() instanceof NavigetionLayout) {
            if (view == null) {
                view = inflater.inflate(R.layout.fragment_search_result, container, false);
                context = view.getContext();
                searchButton = (RelativeLayout) view.findViewById(R.id.search_button);
                if (ConnectionUtils.resulstsObjs != null) {
                    resulstsObjs = new ArrayList<>(ConnectionUtils.resulstsObjs);
                    ConnectionUtils.resulstsObjs = null;
                }
                if (resulstsObjs != null) {
                    lv = (ListView) view.findViewById(R.id.search_result_listView);
                    searchResultListAdapter = new SearchResultListAdapter(context, resulstsObjs);
                    lv.setAdapter(searchResultListAdapter);
                    sumResultTV = ((TextView) view.findViewById(R.id.sumResult));
                    sumResultTV.setText(String.valueOf(resulstsObjs.size()));
                }
                if (getActivity() instanceof MainActivity) {
                    HideFragmentTop();
                    visibleFragmentBottom();
                }
                autoComplete = (CustemLightAutoCompleteTextView) view.findViewById(R.id.search_linek);
                // check if did not come from advance search
                if (((NavigetionLayout) getActivity()).getSearchWord() != null && ((NavigetionLayout) getActivity()).from != 2) {
                    getSearchWord = ((NavigetionLayout) getActivity()).getSearchWord();
                    autoComplete.setText(getSearchWord);
                    //for avoiding showing the autocomplete list for this text
                    b = false;
                    autoComplete.dismissDropDown();
                } else
                    //if came from advance search so get the json
                    if (((NavigetionLayout) getActivity()).from == 2 || ((NavigetionLayout) getActivity()).from == 1) {
                        if (((NavigetionLayout) getActivity()).getAdvanceSearchJson() != null) {
                            try {
                                jsonObj = new JSONObject(((NavigetionLayout) getActivity()).getAdvanceSearchJson());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            regularSearch = false;
                        }
                    }

                listAutoC = new ArrayList<>();

                autoComplete.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(getActivity()!=null) {
                            //check if the text changed was not setting the text
                            if (s.length() > 1 && b)
                                initAutoComplete(s.toString());
                            else if (!b) {
                                b = true;
                                Log.d("ttt", "ttt");
                            }
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                //gets the event of picking a item from the autocomplete list
                autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                        showList = false;
                    }
                });
                distance_l = (RelativeLayout) view.findViewById(R.id.distance_l);
                distance_txt = (CustomBoldTextView) view.findViewById(R.id.distance_txt);
                distance_image = (ImageView) view.findViewById(R.id.distance_image);
                ranking_l = (RelativeLayout) view.findViewById(R.id.ranking_l);
                ranking_image = (ImageView) view.findViewById(R.id.renking_image);
                ranking_txt = (CustomBoldTextView) view.findViewById(R.id.ranking_txt);
                //if the search is by a city so the sort is by ranking(not done yet...) with no option to sort by distance
                if (((NavigetionLayout) getActivity()).searchByCity) {
                    distance_txt.setTextColor(getResources().getColor(R.color.dark_gray_6));
                    distance_txt.setStyle(1);
                    distance_image.setVisibility(View.GONE);
                    ranking_txt.setStyle(0);
                    ranking_image.setVisibility(View.VISIBLE);
                } else
                    //if there is no distance (the location is not enable) so sort by ranking and try to enable location
                    if ((ConnectionUtils.CurrentLatLng == null)) {
                        distance_txt.setTextColor(getResources().getColor(R.color.dark_gray_6));
                        distance_txt.setStyle(1);
                        distance_image.setVisibility(View.GONE);
                        ranking_txt.setStyle(0);
                        ranking_image.setVisibility(View.VISIBLE);
                        checkLocationEnable();
                        mayRequestLocation();
                    } else {
                        // the default is sorting by distance
                        //first check if the location is enabled
                        checkLocationEnable();
                        //check if the permission of location is enabled
                        if (mayRequestLocation()) {
                            distance_txt.setStyle(0);
                            distance_image.setVisibility(View.VISIBLE);
                            ranking_txt.setStyle(1);
                            ranking_image.setVisibility(View.GONE);
                        }
                    }


                distance_l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((NavigetionLayout) getActivity()).searchByCity)
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_sort_by_distance), Toast.LENGTH_SHORT).show();
                        else if (ConnectionUtils.CurrentLatLng == null) {
                            //when the location is not enabled, so try to enable it by the dialog...
                            checkLocationEnable();
                        } else {
                            distance_txt.setStyle(0);
                            distance_image.setVisibility(View.VISIBLE);
                            ranking_txt.setStyle(1);
                            ranking_image.setVisibility(View.GONE);
                            sort(1);
                        }
                    }
                });
                //if the search was by city so the sort is by ranking with no option to change
                ranking_l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if there is no distance so the sort is already on this, so there is no purpose for the  press
                        if (!((NavigetionLayout) getActivity()).searchByCity && ConnectionUtils.CurrentLatLng != null) {
                            distance_txt.setStyle(1);
                            distance_image.setVisibility(View.GONE);
                            ranking_txt.setStyle(0);
                            ranking_image.setVisibility(View.VISIBLE);
                            sort(2);
                        }
                    }
                });
                searchButton = (RelativeLayout) view.findViewById(R.id.search_button);
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                        if (autoComplete.getText().toString().trim().length() > 0) {
                            getSearchWord = autoComplete.getText().toString();
                            regularSearch = true;
                            SearchByKeyWord();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_word_for_search), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                changePlaceB = (Button) view.findViewById(R.id.change_location_btn);
                changePlaceB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangePlaceDialogeFragment changePlaceDialogeFragment = ChangePlaceDialogeFragment.newInstance(true);
                        changePlaceDialogeFragment.setTargetFragment(SearchResultFragment.this, 1);
                        ((NavigetionLayout) getActivity()).initFragmentAll(changePlaceDialogeFragment, true, 0);
                    }
                });
            }
//        }
        return view;
    }

    public void initAutoComplete(String str) {
        Log.d("ttt", "hhh");
        String jsonString = "{\"nvKeyWord\":\"" +/* autoComplete.getText().toString() */ str + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainBL.SearchWordCompletion(getContext(), jsonObject, new IExecutable<ArrayList<String>>() {
            @Override
            public void onExecute(ArrayList<String> strings) {
                if (strings != null) {
                    if (adp == null)
                        //check if did not come back from another page,
                        if (!((NavigetionLayout) getActivity()).leftSearchResult)
                            autoComplete.showDropDown();
                        else
                            ((NavigetionLayout) getActivity()).leftSearchResult = false;
                    //  else
                    if (adp != null)
                        adp = null;
                    //refresh strings for autocomplate
                    data = strings.toArray(new String[strings.size()]);
                    adp = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
                    autoComplete.setAdapter(adp);
                    adp.notifyDataSetChanged();
                    //if a item from the list was picked not to show the list and remove the keyboard
                    if (!showList) {
                        autoComplete.dismissDropDown();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(autoComplete.getWindowToken(), 0);
                        showList = true;
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (autoComplete != null) {
            new Handler().post(new Runnable() {
                public void run() {
                    autoComplete.dismissDropDown();
                }
            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: //came from changePlace
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String searchCity = bundle.getString("citySearch", "");
                    if (regularSearch) {
                        //regular search
                        JSONObject jsonObject = new JSONObject();
                        //put the word of search
                        try {
                            if (getSearchWord != null)
                                jsonObject.put("nvKeyWord", getSearchWord);
                            if (!searchCity.equals(""))
                                jsonObject.put("nvCity", searchCity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MainBL.SearchByKeyWord(getContext(), jsonObject, new IExecutable<ArrayList<SearchResulstsObj>>() {
                            @Override
                            public void onExecute(ArrayList<SearchResulstsObj> searchResulstsObjs) {
                                if (searchResulstsObjs != null) {
                                    resulstsObjs = new ArrayList<>(searchResulstsObjs);
                                    searchResultListAdapter.setSearchResulstsObjs(resulstsObjs);
                                    searchResultListAdapter.notifyDataSetChanged();
                                    sumResultTV.setText(String.valueOf(resulstsObjs.size()));
                                    // make not enable to sort by distance
                                    ((NavigetionLayout) getActivity()).searchByCity = true;
                                    distance_txt.setTextColor(getResources().getColor(R.color.dark_gray_6));
                                    distance_txt.setStyle(1);
                                    distance_image.setVisibility(View.GONE);
                                    ranking_txt.setStyle(0);
                                    ranking_image.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_your_search), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        //advance search
                        try {
                            jsonObj.put("nvCity", searchCity);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MainBL.SearchProviders(getActivity(), ConnectionUtils.SearchProviders, jsonObj,
                                new IExecutable<ArrayList<SearchResulstsObj>>() {
                                    @Override
                                    public void onExecute(ArrayList<SearchResulstsObj> searchResulst) {
                                        if (searchResulst != null) {
                                            resulstsObjs = new ArrayList<>(searchResulst);
                                            searchResultListAdapter.setSearchResulstsObjs(resulstsObjs);
                                            searchResultListAdapter.notifyDataSetChanged();
                                            sumResultTV.setText(String.valueOf(resulstsObjs.size()));
                                            // make not enable to sort by distance
                                            ((NavigetionLayout) getActivity()).searchByCity = true;
                                            distance_txt.setTextColor(getResources().getColor(R.color.dark_gray_6));
                                            distance_txt.setStyle(1);
                                            distance_image.setVisibility(View.GONE);
                                            ranking_txt.setStyle(0);
                                            ranking_image.setVisibility(View.VISIBLE);
                                        } else {
                                            Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_your_search), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new IExecutable<Integer>() {
                                    @Override
                                    public void onExecute(Integer integer) {
                                        if (integer == -3) {
                                            Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_your_search), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                        );

                    }
                }
                break;
            case 4:// came from location definitions
                LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled = false;
                boolean network_enabled = false;
                //check if the location is enabled
                try {
                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch (Exception ex) {
                }

                try {
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex) {
                }

                if (gps_enabled && network_enabled) {
                    locHelper = new MyLocation();
                    locHelper.getLocation(context, MyLocation.saveLocationResult);
                    LocationControl locationControlTask = new LocationControl();
                    locationControlTask.execute(getActivity());
                } else {
                    //if the user does not enable his location, so enable type a city for search
                    ChangePlaceDialogeFragment changePlaceDialogeFragment = ChangePlaceDialogeFragment.newInstance(false);
                    changePlaceDialogeFragment.setTargetFragment(SearchResultFragment.this, 1);
                    ((NavigetionLayout) getActivity()).initFragmentAll(changePlaceDialogeFragment, true, 0);
                }
                break;
        }

    }


    @Override
    public boolean OnBackPress() {
        return true;
    }

    private void SearchByKeyWord() {
        nvLatitude = "";
        nvLongitude = "";
        if (ConnectionUtils.CurrentLatLng != null) {
            nvLatitude = String.valueOf(ConnectionUtils.CurrentLatLng.latitude);
            nvLongitude = String.valueOf(ConnectionUtils.CurrentLatLng.longitude);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nvKeyWord", autoComplete.getText().toString());
            if (!nvLongitude.equals(""))
                jsonObject.put("nvlong", nvLongitude);
            if (!nvLatitude.equals(""))
                jsonObject.put("nvlat", nvLatitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.SearchByKeyWord(getContext(), jsonObject, new IExecutable<ArrayList<SearchResulstsObj>>() {
            @Override
            public void onExecute(ArrayList<SearchResulstsObj> searchResulstsObjs) {
                if (searchResulstsObjs != null) {
                    resulstsObjs = new ArrayList<>(searchResulstsObjs);
//                    searchResultListAdapter= new SearchResultListAdapter(context, resulstsObjs);
                    searchResultListAdapter.setSearchResulstsObjs(resulstsObjs);
                    searchResultListAdapter.notifyDataSetChanged();
                    sumResultTV.setText(String.valueOf(resulstsObjs.size()));
                    if (ConnectionUtils.CurrentLatLng != null) {
                        //if the location is enabled so sort by  distance
                        distance_txt.setTextColor(getResources().getColor(R.color.black));
                        distance_txt.setStyle(0);
                        distance_image.setVisibility(View.VISIBLE);
                        ranking_txt.setStyle(1);
                        ranking_image.setVisibility(View.GONE);
                        // enable to sort also by distance (this option isn't enabled when search by city)
                        ((NavigetionLayout) getActivity()).searchByCity = false;
                    } else {
                        distance_txt.setTextColor(getResources().getColor(R.color.dark_gray_6));
                        ranking_txt.setStyle(0);
                        ranking_image.setVisibility(View.VISIBLE);
                        distance_txt.setStyle(1);
                        distance_image.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_this_word), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void sort(int i) {
        if (i == 1) {
            Collections.sort(resulstsObjs, new ComparatorDistance());

        } else {
            Collections.sort(resulstsObjs, new ComparatorRank());
        }
        searchResultListAdapter = new SearchResultListAdapter(context, resulstsObjs);
        lv.setAdapter(searchResultListAdapter);
        searchResultListAdapter.notifyDataSetChanged();
    }

    private class ComparatorDistance implements Comparator<SearchResulstsObj> {
        @Override
        public int compare(SearchResulstsObj obj1, SearchResulstsObj obj2) {
            float distance = obj1.iDistance;
            float distance1 = obj2.iDistance;
            if (distance == -1)
                return 1;
            if (distance1 == -1)
                return -1;
            if (distance > distance1) {
                return 1;
            } else if (distance < distance1) {
                return -1;
            } else {
                return 0;
            }
        }

    }

    private class ComparatorRank implements Comparator<SearchResulstsObj> {
        // needs to be sort from the big to little
        @Override
        public int compare(SearchResulstsObj obj1, SearchResulstsObj obj2) {
            int rank1 = obj1.iCustomerRank;
            int rank2 = obj2.iCustomerRank;
            if (rank1 > rank2)
                return 1;
            if (rank1 < rank2)
                return -1;
            else
                return 0;
        }
    }


    private static final int REQUEST_ACCESS_FINE_LOCATION = 0;

    // check if the permission of getting location is enabled
    private boolean mayRequestLocation() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            try {
                Snackbar.make(view.findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // get the location
                nvLatitude = "";
                nvLongitude = "";
                if (ConnectionUtils.CurrentLatLng != null) {
                    nvLatitude = String.valueOf(ConnectionUtils.CurrentLatLng.latitude);
                    nvLongitude = String.valueOf(ConnectionUtils.CurrentLatLng.longitude);
                }
            }
        }
    }

    //check if getting the location is enabled, if no so show a dialog to enable changing that
    private void checkLocationEnable() {
        //check if did not get the location (the location is initialize in loading of the search page
        //, if after the user turns off the location before searching  the search function wil still send the location, so there is no need for this checking)
        if (ConnectionUtils.CurrentLatLng == null) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }

            if (!gps_enabled && !network_enabled) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        ChangePlaceDialogeFragment changePlaceDialogeFragment = ChangePlaceDialogeFragment.newInstance(false);
                        changePlaceDialogeFragment.setTargetFragment(SearchResultFragment.this, 1);
                        ((NavigetionLayout) getActivity()).initFragmentAll(changePlaceDialogeFragment, true, 0);
                    }
//                    }
                });

                dialog.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(myIntent, 4);
                    }
                });
                dialog.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
                    //open the option to pick place
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        ChangePlaceDialogeFragment changePlaceDialogeFragment = ChangePlaceDialogeFragment.newInstance(false);
                        changePlaceDialogeFragment.setTargetFragment(SearchResultFragment.this, 1);
                        ((NavigetionLayout) getActivity()).initFragmentAll(changePlaceDialogeFragment, true, 0);
                    }
                });
                dialog.show();
            }
        }
    }

    //makes a progress dialog when looking for location
    private class LocationControl extends AsyncTask<Context, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());

        protected void onPreExecute() {
            this.dialog.setMessage(getResources().getString(R.string.looking_for_location));
            //make no possibility to leave the dialog by clicking on the screen or on back button
            this.dialog.setCanceledOnTouchOutside(true);
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        protected Void doInBackground(Context... params) {
            //Wait a few seconds to see if we can get a location from either network or GPS, otherwise stop
            Long t = Calendar.getInstance().getTimeInMillis();
            while (!hasLocation && Calendar.getInstance().getTimeInMillis() - t < 13000) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(final Void unused) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (ConnectionUtils.CurrentLatLng != null) {
                nvLatitude = "";
                nvLongitude = "";
                nvLatitude = String.valueOf(ConnectionUtils.CurrentLatLng.latitude);
                nvLongitude = String.valueOf(ConnectionUtils.CurrentLatLng.longitude);
                if (regularSearch) {
                    //regular search
                    JSONObject jsonObject = new JSONObject();
                    //put the word of search
                    try {
                        if (getSearchWord != null)
                            jsonObject.put("nvKeyWord", getSearchWord);
                        if (!nvLongitude.equals(""))
                            jsonObject.put("nvlong", nvLongitude);
                        if (!nvLatitude.equals(""))
                            jsonObject.put("nvlat", nvLatitude);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainBL.SearchByKeyWord(getContext(), jsonObject, new IExecutable<ArrayList<SearchResulstsObj>>() {
                        @Override
                        public void onExecute(ArrayList<SearchResulstsObj> searchResulstsObjs) {
                            if (searchResulstsObjs != null) {
                                resulstsObjs = new ArrayList<>(searchResulstsObjs);
                                searchResultListAdapter.setSearchResulstsObjs(resulstsObjs);
                                searchResultListAdapter.notifyDataSetChanged();
                                sumResultTV.setText(String.valueOf(resulstsObjs.size()));
                                distance_txt.setTextColor(getResources().getColor(R.color.black));
                                distance_txt.setStyle(0);
                                distance_image.setVisibility(View.VISIBLE);
                                ranking_txt.setStyle(1);
                                ranking_image.setVisibility(View.GONE);
                                // enable to sort also by distance (this option isn't enabled when search by city)
                                ((NavigetionLayout) getActivity()).searchByCity = false;
                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_this_word), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                } else {
                    //advance search
                    try {
                        if (!nvLongitude.equals(""))
                            jsonObj.put("nvlong", nvLongitude);
                        if (!nvLatitude.equals(""))
                            jsonObj.put("nvlat", nvLatitude);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MainBL.SearchProviders(getActivity(), ConnectionUtils.SearchProviders, jsonObj,
                            new IExecutable<ArrayList<SearchResulstsObj>>() {
                                @Override
                                public void onExecute(ArrayList<SearchResulstsObj> searchResulst) {
                                    if (searchResulst != null) {
                                        resulstsObjs = new ArrayList<>(searchResulst);
                                        searchResultListAdapter.setSearchResulstsObjs(resulstsObjs);
                                        searchResultListAdapter.notifyDataSetChanged();
                                        sumResultTV.setText(String.valueOf(resulstsObjs.size()));
                                        // change to sort by distance
                                        ((NavigetionLayout) getActivity()).searchByCity = false;
                                        distance_txt.setStyle(0);
                                        distance_image.setVisibility(View.VISIBLE);
                                        ranking_txt.setStyle(1);
                                        ranking_image.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_your_search), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new IExecutable<Integer>() {
                                @Override
                                public void onExecute(Integer integer) {
                                    if (integer == -3) {
                                        Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_your_search), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );
                }
            }
        }
    }
}
