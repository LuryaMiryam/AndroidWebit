package webit.bthereapp.Screens.Register.BusinessRegister;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.YesOrNo_White;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.Entities.objInContactList;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.SyncContacts;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ContactListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ContactListFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mContinueBtn, mOkBtn;
    private String name, phone;
    ArrayList<String> nvPhoneList,getSaveList;
    private int phone_idx, name_idx;
    private CustomLightButton ok;
    public static boolean is_back=false;
    private View view;
    public ArrayList<String> nameList;
    public ArrayList<Contact> contactsList,contactsSave,setContactSave;
    private ListView mContactListLV;
    private ContactListAdapter contactListAdapter;
    public ImageView mChooseAllIV;
    public boolean chosePressed = true, find;
    private Contact PersonContact;



    public ContactListFragment() {
        // Required empty public constructor
    }

    public static ContactListFragment instance;

    public static ContactListFragment getInstance() {
        if (instance == null)
            instance = new ContactListFragment();
        return instance;
    }
    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {
        if (getActivity() instanceof ExistsSuplierActivity) {
            ((ExistsSuplierActivity) getActivity()).visibleFragmentMain();
            ((ExistsSuplierActivity) getActivity()).hideFragmentTop();
            ((ExistsSuplierActivity) getActivity()).hideContainerMain();
        }
        return true;
    }

    public static ContactListFragment newInstance(String param1, String param2) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_contact_list, container, false);
            mChooseAllIV = (ImageView) view.findViewById(R.id.choose_all);
            mChooseAllIV.setOnClickListener(this);
            mContinueBtn = (Button) view.findViewById(R.id.contacts_continue);
            mContinueBtn.setOnClickListener(this);
            ok = (CustomLightButton) view.findViewById(R.id.ok);
            ok.setOnClickListener(this);
            mContactListLV = (ListView) view.findViewById(R.id.list_of_names);


            nameList = new ArrayList<>();
            contactsList = new ArrayList<>();
            nvPhoneList = new ArrayList<>();

            if (mayRequestContacts()) {
                getAddressBook();
            }



            //in the first open the fragment, open dialog to say the user about it
            Realm realm = Utils.getRealmInstance(getContext());
            SyncContactsRealm syncContactsRealm = realm.where(SyncContactsRealm.class).findFirst();
            if (syncContactsRealm == null) {
                if (getActivity() instanceof MainActivity){
                    if (getActivity() instanceof MainActivity){
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showDialogOpen();
                            }
                        },600);

                    }
                }
            } else
            {
                setContactSave = new ArrayList<Contact>();
                getSaveList = syncContactsRealm.getNvPhoneList_();
                boolean checkedAll=true;
                for (Contact contact : contactsList) {
                    contact.setChanged(true);
                    find = false;
                    for (String phone : getSaveList) {
                        if (contact.getPhoneNumber().equals(phone))
                            if (phone.equals(contact.getPhoneNumber()))
                                find = true;
                    }
                    if (find) {
                        contact.setChecked(true);
                        setContactSave.add(contact);
                    } else {
                        contact.setChecked(false);
                        checkedAll=false;
                    }
                }
                //if not all the contacts are choose so the circle should be empty
                if(!checkedAll)
                    mChooseAllIV.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
                contactListAdapter.setSaveContactList(setContactSave);
                contactListAdapter.notifyDataSetChanged();
            }

        }
        if (getActivity() instanceof MainActivity)
        if(is_back)
            super.initFragmentTop(MainActivity.Num, getString(R.string.bus_contact_synchrun), 5,true);
        else
            super.initFragmentTop(MainActivity.Num, getString(R.string.bus_contact_synchrun), 5,false);

        else {
            super.initFragmentTop3(5, getString(R.string.bus_contact_synchrun),is_back);
            ok.setVisibility(View.VISIBLE);
            mContinueBtn.setVisibility(View.GONE);
        }
        is_back=false;
        return view;
    }
//get adress book of the user from the device
    public void getAddressBook() {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
       contactsList.clear();
        objInContactList contact;
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        int i = 0;
        while (cursor.moveToNext()) {
            phone_idx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phone = cursor.getString(phone_idx);
            phone = phone.replaceAll("[-]", "");
            name_idx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            name = cursor.getString(name_idx);
            PersonContact = new Contact(name, phone);
            contactsList.add(PersonContact);
            i++;
        }
        progressDialog.dismiss();
        contactListAdapter = new ContactListAdapter(getActivity(), contactsList,this);
        mContactListLV.setAdapter(contactListAdapter);

    }

    private static final int REQUEST_READ_CONTACTS = 0;

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            try {
                Snackbar.make(view.findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted , Access contacts here or do whatever you need.
                getAddressBook();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
            getActivity().onBackPressed();
            break;
            case R.id.choose_all:
             //   contactListAdapter.setChange(false);
                setAllList();
                if (chosePressed) {
                    mChooseAllIV.setImageDrawable(getResources().getDrawable(R.drawable.circle_empty));
                    chosePressed = false;
                    contactListAdapter.setChose(chosePressed);
                    contactListAdapter.notifyDataSetChanged();
                } else {
                    mChooseAllIV.setImageDrawable(getResources().getDrawable(R.drawable.ok_select_strock_black));
                    chosePressed = true;
                    contactListAdapter.setChose(chosePressed);
                    contactListAdapter.notifyDataSetChanged();
                }
                contactListAdapter.setAllSaveList(chosePressed);
                break;
            case R.id.contacts_continue:
                //save the contacts list and pass to payment fragment
                mContinueBtn.setEnabled(false);
                saveContactList();

                Realm realm = Utils.getRealmInstance(getContext());

//                ProviderDetailsObjRealm providerDetailsObjRealm = realm.where(ProviderDetailsObjRealm.class).findFirst();

                realm.beginTransaction();
//                if(providerDetailsObjRealm!=null) {
//                    providerDetailsObjRealm.setNvPhoneList(SyncContacts.getInstance().getPhone_list());
//                    providerDetailsObjRealm.setbAutoApproval(SyncContacts.getInstance().getbAutoApproval());
//                }
                SyncContactsRealm syncContactsRealm = new SyncContactsRealm(SyncContacts.getInstance());
                realm.where(SyncContactsRealm.class).findAll().deleteAllFromRealm();
                realm.copyToRealm(syncContactsRealm);


                realm.commitTransaction();
                super.initFragmentMain(BusinessPaymentFragment.getInstance(), true, true,6);
                break;
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mContinueBtn.setEnabled(true);
    }

    //open dialog of the contacts list
    private void showDialogOpen() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_contact_list);
        mOkBtn = (CustomLightButton) dialog.findViewById(R.id.dialog_continue);
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.light_gray_9);
    }

    private void setAllList() {
        for(Contact contact:contactsList)
            contact.setChanged(false);
    }

    private void saveContactList() {
        nvPhoneList.clear();
        contactsSave=contactListAdapter.getSaveContactList();
        for(Contact contact:contactsSave){
           nvPhoneList.add(contact.getPhoneNumber());
        }
        SyncContacts.getInstance().setPhone_list(nvPhoneList);
    }


    public class Contact {
        public String name;
        public String phoneNumber;
        private boolean isChecked = true;
        private boolean changed=false;

        public boolean isChanged() {
            return changed;
        }

        public void setChanged(boolean changed) {
            this.changed = changed;
        }

        private boolean AllChecked=true;

        public Contact(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Contact{" +
                    "name='" + name + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }



}
