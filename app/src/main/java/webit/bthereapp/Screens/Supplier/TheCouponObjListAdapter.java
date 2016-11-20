package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.R;

/**
 * Created by User on 03/05/2016.
 */
public class TheCouponObjListAdapter extends ArrayAdapter<CouponObj> {
    private Context mcontext;


    ArrayList<CouponObj> sale90_list = new ArrayList<>();
    //    private ImageButton button;
    private EditText mNCet;
    private CustomRegularTextView mDateTv, mDescriptionTv;
    private View convertView;
    private int state = 0;
    private LinearLayout ll;

    public TheCouponObjListAdapter(Context context, int resource, ArrayList<CouponObj> objects) {
        super(context, resource, objects);
        this.mcontext=context;
        this.sale90_list=objects;
    }

    public static int getA() {
        return a;
    }

    public static void setA(int a) {
        TheCouponObjListAdapter.a = a;
    }

    private static int a=0;



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.the_90_minute_item, parent, false);

        mDateTv = (CustomRegularTextView) convertView.findViewById(R.id.date);
        mDescriptionTv = (CustomRegularTextView) convertView.findViewById(R.id.description);

        ll= (LinearLayout) convertView.findViewById(R.id.ll);

        ViewGroup.LayoutParams params = ll.getLayoutParams();
        a=params.height;

        mDateTv.setText(sale90_list.get(position).getdDate());
        mDescriptionTv.setText(sale90_list.get(position).getNvCouponName());

        return convertView;
    }

}
