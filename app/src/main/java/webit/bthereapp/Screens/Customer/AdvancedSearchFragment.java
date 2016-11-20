package webit.bthereapp.Screens.Customer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.CustemViews.TimeText;
import webit.bthereapp.DM.FieldAndCategoryDM;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.General.DateTimePicker.DatePicker;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.SearchResultFragment;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Utils.Utils;

public class AdvancedSearchFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_STREET = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout mDomenLinearLayout;
    private LinearLayout mAreaLinearLayout;
    private LinearLayout mDateLinearLayout;
    private LinearLayout mTimeLinearLayout;
    private LinearLayout mTimeAndSaveL;
    private LinearLayout mSecondLinearLayout;
    private LinearLayout mexpandedLinearLayout;
    private LinearLayout mAutoCopleteLinearLayout;
    private CustomLightTextView mTextView;
    private ArrayAdapter<String> adapter;
    private TextView mTextViewFirst, mChosenTV;
    private TextView domainChoose, mAreaChose, mDateChose, mTimeChoose;
    private ImageButton MCancelImageMutton;
    private LinearLayout mcustomTimePicker;
    private Button mSearchB;
    private View view;
    private TimeText from, to;
    private View mView;
    private boolean isOpen = false;
    private boolean domainP = false, TimeP = false;
    private HashMap<String, Integer> list2;
    private AutoCompleteTextView textView;
    private  int openNow;
    Dialog vDialod = null;
    private DatePicker datePicker;
    private Calendar c = Calendar.getInstance();



    private int iSupplierFieldType = 0;


    public static AdvancedSearchFragment instance;

    public static AdvancedSearchFragment getInstance() {
        if (instance == null)
            instance = new AdvancedSearchFragment();
        return instance;
    }
    public static void removeInstance() {
        instance = null;
    }
    @Override
    public boolean OnBackPress() {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
            String weekdays[] = new DateFormatSymbols(Locale.ITALIAN).getWeekdays();
            mSecondLinearLayout = (LinearLayout) view.findViewById(R.id.secondLinearLayout);
            mSecondLinearLayout.setOnClickListener(this);
            mexpandedLinearLayout = (LinearLayout) view.findViewById(R.id.expandedLinearLayout);
            mDomenLinearLayout = (LinearLayout) view.findViewById(R.id.domainLinearLayout);
            mDomenLinearLayout.setOnClickListener(this);
            mAreaLinearLayout = (LinearLayout) view.findViewById(R.id.areaLinearLayout);
            mAreaLinearLayout.setOnClickListener(this);
            mDateLinearLayout = (LinearLayout) view.findViewById(R.id.dateLinearLayout);
            mDateLinearLayout.setOnClickListener(this);
            mTimeLinearLayout = (LinearLayout) view.findViewById(R.id.timeLinearLayout);
            mTimeLinearLayout.setOnClickListener(this);
            mTimeAndSaveL = (LinearLayout) view.findViewById(R.id.time_and_save);
            MCancelImageMutton = (ImageButton) view.findViewById(R.id.imageCancel);
            MCancelImageMutton.setOnClickListener(this);
            mTimeLinearLayout.setOnClickListener(this);
            mAutoCopleteLinearLayout = (LinearLayout) view.findViewById(R.id.LinearLayoutAboutAutoComplete);
            textView = (AutoCompleteTextView) view.findViewById(R.id.autoComplete);
            textView.setHint(getResources().getString(R.string.enter_subject));
            mView = (View) view.findViewById(R.id.view);
            mTextViewFirst = (TextView) view.findViewById(R.id.domainTV);
            domainChoose = (TextView) view.findViewById(R.id.domainChoose);
            mAreaChose = (TextView) view.findViewById(R.id.areaChoose);
            mDateChose = (TextView) view.findViewById(R.id.dateChoose);
            mcustomTimePicker = (/*CustomTimePicker*/LinearLayout) view.findViewById(R.id.customTimePicker);
            mTimeChoose = (TextView) view.findViewById(R.id.TimeChoose);
            mSearchB = (Button) view.findViewById(R.id.searchButton);
            mSearchB.setOnClickListener(this);
            from = (TimeText) view.findViewById(R.id.fromTime);
            to = (TimeText) view.findViewById(R.id.toTime);
            view.findViewById(R.id.btn_save).setOnClickListener(this);
        return view;
    }

    public void setLinear() {
        mTextViewFirst.setTextColor(getResources().getColor(R.color.black));
        LinearLayout.LayoutParams layoutSecond = (LinearLayout.LayoutParams) mSecondLinearLayout.getLayoutParams();
        layoutSecond.weight = 1;
        mSecondLinearLayout.setLayoutParams(layoutSecond);
        mexpandedLinearLayout.setVisibility(View.VISIBLE);
        mView.setVisibility(View.VISIBLE);
        mTextView.setTextColor(getResources().getColor(R.color.color3));
        mTextView.init(1);
    }

    public void setSpinner() {
        mAutoCopleteLinearLayout.setVisibility(View.VISIBLE);
        mcustomTimePicker.setVisibility(View.GONE);

        Realm realm = Utils.getRealmInstance(getActivity());
        final RealmResults<FieldAndCategoryDM> results = realm.where(FieldAndCategoryDM.class).findAll();
        list2 = new HashMap<>();
        for (int j = 0; j < results.size(); j++)
            if (!(list2.containsKey(results.get(j).getNvCategoryName())))
                list2.put(results.get(j).getNvCategoryName(), results.get(j).getiCategoryRowId());

        ArrayList<String> Categories = new ArrayList<>();
        for (String key : list2.keySet()) {
            Categories.add(key);
        }


        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Categories);
        textView.setAdapter(adapter);
        textView.setThreshold(1);

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (list2.containsKey(textView.getText().toString())) {
                    mChosenTV.setText(textView.getText());
                    iSupplierFieldType = list2.get(textView.getText().toString());
                    domainP=false;
                    setBack();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void setBack() {
        mTextView.setTextColor(getResources().getColor(R.color.black));
        mTextView.init(0);
        //  mTextView.setTypeface(null, Typeface.NORMAL);
        hideKeyBoard(mChosenTV);
        mexpandedLinearLayout.setVisibility(View.GONE);
        mView.setVisibility(View.GONE);
        mChosenTV.setVisibility(View.VISIBLE);
        mAutoCopleteLinearLayout.setVisibility(View.GONE);
        textView.clearFocus();
        mcustomTimePicker.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutSecond = (LinearLayout.LayoutParams) mSecondLinearLayout.getLayoutParams();
        layoutSecond.weight = 3;
        mSecondLinearLayout.setLayoutParams(layoutSecond);
        isOpen = false;
    }

    public void setTime() {
        mAutoCopleteLinearLayout.setVisibility(View.GONE);
        mcustomTimePicker.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_STREET) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                mChosenTV.setText(place.getAddress().toString());
                mChosenTV.setVisibility(View.VISIBLE);
                mTextView.setTextColor(getResources().getColor(R.color.black));
                mTextView.init(0);
                setBack();
                isOpen = false;
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                setBack();
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.domainLinearLayout:
                //open domain autocomplate
                if (domainP) {
                    //if open so close
                    setBack();
                    domainP = false;
                    isOpen = false;
                } else{
                    if (!isOpen) {
                    //if close so open
                 openDomain();
                    openNow=1;
                }
                    else
                        closeAndOpenNext(1);
                }
                break;
            case R.id.areaLinearLayout:
                //open address
                if (!isOpen) {
                   openAddress();
                    openNow=2;
                }
                else
                    closeAndOpenNext(2);
                break;
            case R.id.dateLinearLayout:
                //open dialog DateTimePicker
                if (!isOpen) {
                   openDate();
                    openNow=3;
                }
                else
                closeAndOpenNext(3);
                break;
            case R.id.btn_save:
                //test hour and save
                if (!checkHour()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_hours), Toast.LENGTH_SHORT).show();
                    from.getTextView().setError("");
                    to.getTextView().setError("");
                } else {
                    from.getTextView().setError(null);
                    to.getTextView().setError(null);
                    LinearLayout.LayoutParams layoutSecond = (LinearLayout.LayoutParams) mTimeAndSaveL.getLayoutParams();
                    layoutSecond.weight = 3;
                    mTimeAndSaveL.setLayoutParams(layoutSecond);
                    String s=from.getText() + " - " + to.getText();
                    mTimeChoose.setText(s.toString());
                    mTextView.setTextColor(getResources().getColor(R.color.black));
                    mTextView.init(0);
                    isOpen = false;
                    TimeP = false;
                }
                break;
            case R.id.timeLinearLayout:
                //open hours from/to
                if (TimeP) {
                    LinearLayout.LayoutParams layoutSecond = (LinearLayout.LayoutParams) mTimeAndSaveL.getLayoutParams();
                    layoutSecond.weight = 3;
                    mTimeAndSaveL.setLayoutParams(layoutSecond);
                    TimeP = false;
                    isOpen = false;
                    mTextView.setTextColor(getResources().getColor(R.color.black));
                    mTextView.init(0);
                } else {
                    if (!isOpen) {
                       openHours();
                        openNow=4;
                    }
                    else {
                        closeAndOpenNext(4);
                    }
                }
                break;
            case R.id.imageCancel:
                //cancel button
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                //Find the currently focused view, so we can grab the correct window token from it.
                View view = getActivity().getCurrentFocus();
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = new View(getActivity());
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                getActivity().onBackPressed();
                //initFragmentMain(SearchFragment.getInstance(), false, true);
                break;
            case R.id.searchButton:
                if(isOpen)
                    closeAndOpenNext(0);
                if (isValid()) {
                    Search();
                } else {
                    Toast.makeText(getActivity(), R.string.error_all_empty, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void closeAndOpenNext(int wantToOpen) {
        //close what is open now
        switch (openNow){
            case 1:
                setBack();
                domainP = false;
                isOpen = false;
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                LinearLayout.LayoutParams layoutSecond = (LinearLayout.LayoutParams) mTimeAndSaveL.getLayoutParams();
                layoutSecond.weight = 3;
                mTimeAndSaveL.setLayoutParams(layoutSecond);
                TimeP = false;
                isOpen = false;
                mTextView.setTextColor(getResources().getColor(R.color.black));
                mTextView.init(0);
                break;
        }
        //open what was pressed
        openNow=wantToOpen;
        switch (wantToOpen){
            case 0:
                // do nothing, (when want to search, so i don't want to open anything)
                break;
            case 1:
              openDomain();
                break;
            case 2:
                openAddress();
                break;
            case 3:
               openDate();
                break;
            case 4:
               openHours();
                break;
        }

    }

    private void openDomain(){
        domainP = true;
        isOpen = true;
        mTextView = (CustomLightTextView) view.findViewById(R.id.domainTV);
        // if do this so the word goes away if open again and did not pick anything,if no, so it stays there always
        domainChoose.setText("");
        mChosenTV = domainChoose;
        setLinear();
        setSpinner();
        mTextViewFirst = mTextView;
        //open the keyboard immediately when open
        textView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT);
    }

    private void openAddress(){
        mTextView = (CustomLightTextView) view.findViewById(R.id.areaTV);
        mTextView.setTextColor(getResources().getColor(R.color.color3));
        mTextView.init(1);
        mAreaChose.setText("");
        mChosenTV = mAreaChose;
        mTextViewFirst = mTextView;
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(typeFilter).build(AdvancedSearchFragment.this.getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_STREET);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        mTextViewFirst.requestFocus();
    }

    private void openDate(){
        isOpen = true;
        mTextView = (CustomLightTextView) view.findViewById(R.id.dateTV);
//        new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
//                .setListener(listener)
//                .setInitialDate(new Date())
//                .setMinDate(new Date())
//                .build()
//                .show();
        openDialogDate();
        hideKeyBoard(textView);
        mTextViewFirst = mTextView;
    }


    private void openDialogDate() {
        if (getActivity() != null) {
            vDialod = new Dialog(getActivity());
            vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
            vDialod.setCanceledOnTouchOutside(false);
            vDialod.setContentView(R.layout.date_picker_dialog);
            datePicker = (DatePicker) vDialod.findViewById(R.id.datePicker);
//            final Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.YEAR, 1950);
//            calendar.set(Calendar.MONTH, 0);
//            calendar.set(Calendar.DAY_OF_MONTH, 1);
//            datePicker.setCalendar(calendar);
            Button btnOk = (Button) vDialod.findViewById(R.id.ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = Calendar.getInstance();
                    c.set(Calendar.YEAR, datePicker.getYear());
                    c.set(Calendar.MONTH, datePicker.getMonth());
                    c.set(Calendar.DAY_OF_MONTH, datePicker.getDay());
                    if (c.before(Calendar.getInstance())) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.enter_valid_date), Toast.LENGTH_LONG).show();
                    } else {
                        mDateChose.setText(datePicker.getDay() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                        isOpen = false;
                        View view = getActivity().getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        hideKeyBoard(textView);
                        hideKeyBoard(mTextViewFirst);
                        vDialod.dismiss();
                    }
                }
            });
            Button btnBack = (Button) vDialod.findViewById(R.id.back);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vDialod.dismiss();
                }
            });
            vDialod.show();
        }
    }

    private void openHours(){
        domainP = false;
        TimeP = true;
        isOpen = true;
        LinearLayout.LayoutParams layoutSecond = (LinearLayout.LayoutParams) mTimeAndSaveL.getLayoutParams();
        layoutSecond.weight = 1;
        mTimeAndSaveL.setLayoutParams(layoutSecond);
        mTextView = (CustomLightTextView) view.findViewById(R.id.timeTV);
        mTextView.setTextColor(getResources().getColor(R.color.color3));
        mTextView.init(1);
        mTextViewFirst.requestFocus();
    }

    private void Search() {
        //Location for Search
        String nvLatitude = "", nvLongitude = "";
        if (ConnectionUtils.CurrentLatLng != null) {
            nvLatitude = String.valueOf(ConnectionUtils.CurrentLatLng.latitude);
            nvLongitude = String.valueOf(ConnectionUtils.CurrentLatLng.longitude);
        }
        //create jsonObject
        final JSONObject jsonObject = new JSONObject();
        try {
            if (domainChoose.getText() != null && !domainChoose.getText().equals("")) {
                if (list2 != null)
                    //id for string
                    iSupplierFieldType = list2.get(domainChoose.getText().toString());
                jsonObject.put("iSupplierFieldType", iSupplierFieldType);
            }
            if (mAreaChose.getText() != "")
                jsonObject.put("nvCity", mAreaChose.getText());
            if (mDateChose.getText().toString() != "")
                jsonObject.put("dtDateDesirable", ConnectionUtils.convertStringToDate(mDateChose.getText().toString()));
            if (from.getText() != "")
                jsonObject.put("tFromHour", from.getText());
            if (to.getText() != "")
                jsonObject.put("tToHour", to.getText());
            if (nvLongitude != "")
                jsonObject.put("nvlong", nvLongitude);
            if (nvLatitude != "")
                jsonObject.put("nvlat", nvLatitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainBL.SearchProviders(getActivity(), ConnectionUtils.SearchProviders, jsonObject,
                new IExecutable<ArrayList<SearchResulstsObj>>() {
                    @Override
                    public void onExecute(ArrayList<SearchResulstsObj> searchResulst) {
                        if (searchResulst != null) {
                            ConnectionUtils.resulstsObjs = searchResulst;
                            SearchResultFragment.removeInstance();
                            if (getActivity() instanceof MainActivity) {
                                Intent intent = new Intent(getActivity(), NavigetionLayout.class);
                                intent.putExtra("ShowDifferentView", 1);
                                intent.putExtra("sentFrom", 1);
                                if((mAreaChose.getText() != ""))
                                 intent.putExtra("searchByCity",true);
                                else
                                    intent.putExtra("searchByCity",false);
//                                intent.putExtra("cameFromRegularSearch",false);
                                intent.putExtra("advanceSearchObject",jsonObject.toString());
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                ((NavigetionLayout) getActivity()).from = 2;
                                ((NavigetionLayout) getActivity()).setAdvanceSearchJson(jsonObject.toString());
                                if((mAreaChose.getText() != ""))
                                ((NavigetionLayout) getActivity()).searchByCity=true;
                                else
                                ((NavigetionLayout) getActivity()).searchByCity=false;
                                ((NavigetionLayout) getActivity()).initFragmentMain(SearchResultFragment.getInstance(), true);
                            }
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_your_search), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                        if(integer==-3){
                            Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_your_search), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private boolean isValid() {
        //if At least one full field
        if (!domainChoose.getText().toString().equals(""))
            return true;
        if (!mAreaChose.getText().toString().equals(""))
            return true;
        if (!mDateChose.getText().toString().equals(""))
            return true;
        if (!mTimeChoose.getText().toString().equals(""))
            return true;
        return false;
    }

    //Returns true if normal hours
    public boolean checkHour() {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date fromHour = sdf.parse(from.getText());
            Date toHour = sdf.parse(to.getText());
            //fromHour.compareTo(toHour) return 1 if to biger from
            return fromHour.compareTo(toHour) != 1;
        } catch (Exception e) {
            return false;
        }
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        private SimpleDateFormat mFormatter = new SimpleDateFormat("dd.MM.yy");

        @Override
        public void onDateTimeSet(Date date) {
            mDateChose.setText(mFormatter.format(date));
            isOpen = false;
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            hideKeyBoard(textView);
            hideKeyBoard(mTextViewFirst);
        }

        @Override
        public void onDateTimeCancel() {
            isOpen = false;
            hideKeyBoard(textView);
            hideKeyBoard(mTextViewFirst);
        }
    };

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
