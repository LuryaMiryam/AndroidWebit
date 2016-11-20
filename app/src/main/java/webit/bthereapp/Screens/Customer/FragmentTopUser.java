package webit.bthereapp.Screens.Customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustemLightAutoCompleteTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.FieldAndCategoryDM;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.General.MyLocation;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Calendar.NextFragment;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Utils.CalendarUtil;
import webit.bthereapp.Utils.Utils;

import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentTopUser extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int otherView = 0;
    private CustomLightTextView mAdvanceSearchB;
    private ImageView search_button;
    private CustemLightAutoCompleteTextView autoComplete;
    private ArrayList<String> listAutoC;
    private ArrayAdapter<String> adp;
    String[] data;
    private PlacesAutoCompleteAdapter adapter;
    private Fragment fragment = null;
    private View view;
    private boolean showList = true;

    public FragmentTopUser() {
    }

    public static FragmentTopUser newInstance(int changeView) {
        FragmentTopUser fragment = new FragmentTopUser();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, changeView);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentTopUser instance;

    public static FragmentTopUser getInstance() {
        if (instance == null)
            instance = new FragmentTopUser();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            otherView = getArguments().getInt(ARG_PARAM1);
        }
        Log.d("ppppp","oncreate_in_top");
    }

    private void GetCustomerOrders() {

        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        double d;
        if (userRealm != null)
            d = userRealm.getUserID();
        else
            d = 0;
        String jsonString = "{\"iUserId\":" + d + "}";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetCustomerOrders(getContext(), jsonObject, new IExecutable<ArrayList<OrderDetailsObj>>() {
            @Override
            public void onExecute(ArrayList<OrderDetailsObj> OrderDetailsObj_) {

                if (OrderDetailsObj_ != null) {
                    if (OrderDetailsObj_.size() > 0) {
                        CalendarUtil.OrderDetailsObj_ = OrderDetailsObj_;
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.no_found_orders_to_this_customer), Toast.LENGTH_LONG).show();
                    CalendarUtil.OrderDetailsObj_ = new ArrayList<OrderDetailsObj>();
                }
                NextFragment.removeInstance();
                fragment = NextFragment.getInstance();
                changeView(fragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onstop","oncreateview_in_top");
        if (view == null) {

                view = inflater.inflate(R.layout.fragment_top_user, container, false);
                mAdvanceSearchB = (CustomLightTextView) view.findViewById(R.id.advance_search);
                mAdvanceSearchB.setOnClickListener(this);

                //get te current location
                MyLocation myLocation = new MyLocation();
                myLocation.getLocation(getActivity().getApplicationContext(), MyLocation.saveLocationResult);

                switch (otherView) {
                    case 0:
                        CalendarUtil.is_calendar_of_provider = false;
                        CalendarUtil.taskCalanderListFree = null;
                        GetCustomerOrders();
                        break;
                    case 1:
                        //open dialog to new appointment
                        NewAppointmentFromCustomer.removeInstance();
                        fragment = NewAppointmentFromCustomer.getInstance();
                        break;
                    case 2:
                        //open dialog to update appointment
                        fragment = new UpdateAppointmentFromCustomer();
                        break;
                    case 3:
                        //open dialog of sales the 90 minute
                        fragment = new LastMinuteSaleFragment();
                        break;
                    case 4:
                        //open dialog of the waiting list
                        fragment = new WaitingListFragment();
                        break;
                    case 5:
                        //open dialog of yhe order details
                        fragment = OrderDetailsFragment.getInstance();
                        break;
                    case 6:
                        //open dialog of new event
                        fragment = new NewEventDialogFragmen();
                        break;

                }
                if (otherView != 0)
                    changeView(fragment);


                Realm realm = Utils.getRealmInstance(getActivity());
                RealmResults<FieldAndCategoryDM> results = realm.where(FieldAndCategoryDM.class).findAll();
                String[] strings = new String[results.size()];


                int i = 0;
                for (FieldAndCategoryDM fieldAndCategoryDM : results) {
                    strings[i++] = fieldAndCategoryDM.getNvFieldName().toString();
                }
                i = 0;

                ArrayList list2 = new ArrayList();

                for (String str : strings)
                    if (!(list2.contains(str)))
                        list2.add(str);

                autoComplete = (CustemLightAutoCompleteTextView) view.findViewById(R.id.search);
                autoComplete.setFilters(setInputFilter(120, getResources().getString(R.string.too_long_slogen)));

                listAutoC = new ArrayList<>();

                autoComplete.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 1)
                            initAutoComplete(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                search_button = (ImageView) view.findViewById(R.id.search_button);
                search_button.setOnClickListener(this);
                autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                        showList = false;
                    }
                });
            }
        return view;
    }
    private InputFilter[] setInputFilter(int max, final String message) {
        InputFilter[] inputFilter = new InputFilter[]{
                new InputFilter.LengthFilter(max) {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        CharSequence res = super.filter(source, start, end, dest, dstart, dend);
                        if (res != null) { // Overflow
                            final Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 600);
                        }
                        return res;
                    }
                }
        };
        return inputFilter;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }

    public void changeView(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.display_next, fragment)/*.addToBackStack(fragment.getClass().toString())*/;
        beginTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button: {
                InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                if (autoComplete.getText().toString().trim().length() > 0) {
                    SearchByKeyWord();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_word_for_search), Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.advance_search: {

                initFragmentMain(new AdvancedSearchFragment(), true);
                break;
            }
        }
    }

    public void initAutoComplete(String str) {
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
                    if(adp==null)
                        autoComplete.showDropDown();
                   else  {
                        adp=null;
                    }
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
//                    }
                }
            }
        });
    }

    public void initFragmentMain(Fragment fragment, boolean isAddToBackStack) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (isAddToBackStack) {
            if (fragmentManager.findFragmentByTag(fragment.getClass().toString()) == null) {
                beginTransaction.replace(R.id.content_frame, fragment, fragment.getClass().toString()).addToBackStack(fragment.getClass().toString());
            } else
                beginTransaction.replace(R.id.content_frame, fragment);
        }
        beginTransaction.commitAllowingStateLoss();

    }

    private ArrayList<String> getAutoCompleteList(String str) {
        String jsonString = "{\"nvKeyWord\":" +/* autoComplete.getText().toString() */ str + "}";
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
                    listAutoC = new ArrayList<>(strings);
                }
            }
        });
        return listAutoC;
    }

    private void SearchByKeyWord() {
        String nvLatitude = "", nvLongitude = "";
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
                    ConnectionUtils.resulstsObjs = searchResulstsObjs;
                    if (getActivity() instanceof NavigetionLayout) {
                        // enable to sort also by distance (this option isn't enabled when search by city)
                        ((NavigetionLayout) getActivity()).searchByCity = false;

                        ((NavigetionLayout) getActivity()).from = 4;
                        ((NavigetionLayout) getActivity()).setSearchWord(autoComplete.getText().toString());
                        SearchResultFragment.removeInstance();
                        ((NavigetionLayout) getActivity()).initFragmentMain(SearchResultFragment.getInstance(), true);
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_this_word), Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = getAutoCompleteList(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        adapter.notifyDataSetChanged();
                    } else {
                        // notifyDataSetInvalidated();
                        adapter.notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

}