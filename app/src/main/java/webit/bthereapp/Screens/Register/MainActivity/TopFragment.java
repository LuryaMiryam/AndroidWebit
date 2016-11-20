package webit.bthereapp.Screens.Register.MainActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.AlertsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessDetailsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessPaymentFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;
import webit.bthereapp.Screens.Supplier.ExistsSuplierDefinitionsActivity;
import webit.bthereapp.Utils.Utils;


public class TopFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String POSITION = "position";
    private static final String TITLE = "title";
    private static final String PLACE = "place";

    // TODO: Rename and change types of parameters

    private int mPosition;
    private static int SPLASH_TIME = 1000;
    private String mTitle;
    private int CirclePlace;

    public static TopFragment instance;
    public LinearLayout myLinearLayout, mBackCv;
    private static TextView mTitleTv;
    LinearLayout.LayoutParams params1;
    LinearLayout.LayoutParams params2;

    public TopFragment() {
        // Required empty public constructor
    }
    // public TopFragment(String title) {

    // }
    public static TopFragment newInstance(int position, String title) {
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putString(TITLE, title);
        args.putInt(PLACE,-1);
        fragment.setArguments(args);
        return fragment;
    }

    public static TopFragment newInstance(int position, String title,int place) {
        TopFragment fragment = new TopFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putString(TITLE, title);
        args.putInt(PLACE,place);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mPosition = getArguments().getInt(POSITION);
            mTitle = getArguments().getString(TITLE);
            CirclePlace=getArguments().getInt(PLACE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up, container, false);
        mTitleTv = (TextView) view.findViewById(R.id.title);
        mTitleTv.setText(mTitle);
        mBackCv = (LinearLayout) view.findViewById(R.id.back);
        mBackCv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity)
                    if ((getFragmentManager().getBackStackEntryCount() - 1) != 0)
                        getActivity().onBackPressed();
                    else
                        exitToCustomer(getContext());

            }
        });
        myLinearLayout = (LinearLayout) view.findViewById(R.id.topL);
        setTopSquare(6);
        if (getActivity() instanceof ExistsSuplierDefinitionsActivity) {
            mBackCv.setVisibility(View.GONE);
            myLinearLayout.setVisibility(View.GONE);
        }
        return view;
    }
    public void exitToCustomer(final Context context/* ,final Fragment fragment*/) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.exit);
        alertDialogBuilder
                .setMessage(R.string.exit_to_customer_question)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConnectionUtils.which_calendar = false;
                        Intent intent = new Intent(context, NavigetionLayout.class);
                        startActivity(intent);

                        Realm realm = Utils.getRealmInstance(getContext());
                        realm.beginTransaction();
                        realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
                        realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
                        realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
                        realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
                        realm.where(SyncContactsRealm.class).findAll().deleteAllFromRealm();
                        realm.commitTransaction();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public boolean OnBackPress() {
        return false;
    }

    public static TopFragment getInstance(int p, String s) {

        if (instance == null)
            instance = new TopFragment();
        return instance;
    }

    public void getPlace(final int place) {
        int  i;
        for (i = 0; i < place * 2 - 1; i++) {
            myLinearLayout.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.light_blue));
            if (myLinearLayout.getChildAt(i) instanceof CustomLightTextView) {
                CustomLightTextView textView = (CustomLightTextView) myLinearLayout.getChildAt(i);
                textView.setTextColor(getResources().getColor(R.color.light_gray_2));
                textView.setBackgroundResource(R.color.light_blue);
                textView.init(1);
               // int num = Integer.valueOf(textView.getText().toString());
                final int finalI = i;
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       initFragmentMain(getFragment(finalI),true,true,place);
                    }
                });
            }
        }
        if(CirclePlace==-1)
        putCircle(i);
        else{
            putCircle(CirclePlace*2-1);
        }

    }

    public void setTopSquare(int i) {
        double len = i + i * 0.75;
        myLinearLayout.setWeightSum((int) len);
        params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.weight = 0.75f;
        params1.gravity = Gravity.CENTER;
        params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params2.weight = 1;

        params2.gravity = Gravity.CENTER;
        for (int j = 1; j <= i; j++) {
            if (j != 1) {
                TextView view = new TextView(getActivity());
                view.setBackgroundColor(getResources().getColor(R.color.light_gray_8));
                view.setHeight(2);
                //view.setTextScaleX(2);
                myLinearLayout.addView(view, params1);
            }
            CustomLightTextView textView = new CustomLightTextView(getActivity(), null);
            textView.setText(j + "");
            textView.setTextSize(20);
            textView.setBackground(getResources().getDrawable(R.drawable.rectangle));
            textView.setGravity(Gravity.CENTER);
            myLinearLayout.addView(textView, params2);
        }
        getPlace(mPosition);
    }
    private Fragment getFragment(int i){
        Fragment fragment=null;
        switch (i) {
            case  0:
                fragment= BusinessDetailsFragment.getInstance();
                break;
            case 2:
                fragment= BusinessGeneralData.getInstance();
                break;
            case 4:
                fragment= AlertsFragment.getInstance();
                break;
            case 6:
                fragment= BusinessProfileFragment.getInstance();
                break;
            case 8:
                fragment= ContactListFragment.getInstance();
                break;
            case 10:
                fragment= BusinessPaymentFragment.getInstance();
                break;
        }
       return fragment;
    }
    public void putCircle(int i){
        if (myLinearLayout.getChildAt(i - 1) instanceof CustomLightTextView) {
            CustomLightTextView textView = (CustomLightTextView) myLinearLayout.getChildAt(i - 1);
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_top));
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params3.weight = 0.5f;
            params3.height = myLinearLayout.getLayoutParams().width;
            params3.gravity = Gravity.CENTER;
            textView.setLayoutParams(params3);
        }
    }
}