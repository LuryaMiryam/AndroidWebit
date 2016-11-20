package webit.bthereapp.Screens.Customer;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.ProviderProfileObj;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Utils.Utils;

/**
 * Created by user on 07/09/2016.
 */
public class GivingServiceAdapter extends BaseAdapter  {

    Context context;
    private int p;
    int width;

    ArrayList<SearchResulstsObj> searchResulstsObjs;
    private String string_distance, string_voters, string_ranking;

    public GivingServiceAdapter(Context context, ArrayList<SearchResulstsObj> searchResulstsObjs) {
        this.context = context;
        this.searchResulstsObjs = searchResulstsObjs;
        if (context != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        }
    }

    @Override
    public int getCount() {
        return searchResulstsObjs.size();
    }

    @Override
    public SearchResulstsObj getItem(int position) {
        return searchResulstsObjs.get(position);
    }


    @Override
    public long getItemId(int position) {
        return searchResulstsObjs.get(position).iProviderId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        p = position;
        final Holder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.giving_service_row, null);
            holder = new Holder();
            holder.erase_b = (RelativeLayout) convertView.findViewById(R.id.erase);
            holder.navigate_b = (RelativeLayout) convertView.findViewById(R.id.waze);
            holder.order_b = (RelativeLayout) convertView.findViewById(R.id.order_rl);
            holder.title_1 = (TextView) convertView.findViewById(R.id.item_title_1);
            holder.title_2 = (TextView) convertView.findViewById(R.id.item_title_2);
            holder.all = (LinearLayout) convertView.findViewById(R.id.ww);
            holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
            holder.image = (LinearLayout) convertView.findViewById(R.id.item_imagell);
            holder.et = (EditText) convertView.findViewById(R.id.et);
            holder.scrollX = 0;
            holder.scrollY = 0;
            holder.distance = (TextView) convertView.findViewById(R.id.item_distance);
            holder.ranking = (TextView) convertView.findViewById(R.id.item_ranking);
            holder.voters = (TextView) convertView.findViewById(R.id.voters);
            holder.scroll = (HorizontalScrollView) convertView.findViewById(R.id.scroll);

            //play animtion of slide to the first item
            if (position == 0) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator animator = ObjectAnimator.ofInt(holder.scroll, "scrollX", holder.order_b.getRight());
                        animator.setDuration(1000);
                        animator.start();

                        ObjectAnimator animator1 = ObjectAnimator.ofInt(holder.scroll, "scrollX", holder.erase_b.getLeft());
                        animator1.setDuration(1000);
                        animator1.setStartDelay(1500);
                        animator1.start();
                    }
                });
            }
            else {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        holder.scroll.scrollTo((int) holder.erase_b.getLeft(), 0);
                    }
                });
            }
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) holder.all.getLayoutParams();
        params1.width = width;
        holder.all.setLayoutParams(params1);


        holder.navigate_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationByWaze(position);
            }
        });
        holder.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order(position);
            }
        });
        holder.order_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order(position);
            }
        });

        holder.erase_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getResources().getString(R.string.erase_supplier));

                builder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        eraseProvider(position);
                        searchResulstsObjs.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }
        });
        ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        holder.title_1.setText(getItem(position).nvProviderName);
        holder.title_2.setText(getItem(position).nvProviderSlogan);
        if (getItem(position).nvProviderLogo != null) {
            Bitmap bitmap = ConnectionUtils.convertStringToBitmap(getItem(position).nvProviderLogo);
            holder.icon.setImageBitmap(bitmap);
        } else {
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.user_1));
        }

        if (getItem(position).nvProviderHeader != null) {
            Bitmap bmImg = ConnectionUtils.convertStringToBitmap(getItem(position).nvProviderHeader);
            BitmapDrawable background = new BitmapDrawable(bmImg);
            holder.image.setBackgroundDrawable(background);
        } else {
            holder.image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fingers));
        }
        string_ranking = context.getResources().getString(R.string.ranking_, getItem(position).iCustomerRank);
//        string_voters = context.getResources().getString(R.string.voters,getItem(position).);
        string_distance = context.getResources().getString(R.string.way, getItem(position).nvAdress, getItem(position).iDistance);
        holder.ranking.setText(string_ranking);
        holder.distance.setText(string_distance);
//        holder.voters.setText(string_voters);
        holder.s = holder.scroll.getScrollY();


        return convertView;
    }
    private void LocationByWaze(int position) {
        try {
            String url = "waze://?q=" + searchResulstsObjs.get(position).nvAdress;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            context.startActivity(intent);
        }
    }
    //pass to the fragment of order service
    private void Order(int position) {
        Bundle bundle = new Bundle();
        SearchResulstsObj sr = getItem(position);
        bundle.putString("business", new Gson().toJson(sr));
        ChosenServiceFragment chosenServiceFragment = new ChosenServiceFragment();
        chosenServiceFragment.setArguments(bundle);
        ((NavigetionLayout) context).initFragmentMain(/*ChosenServiceFragment.newInstance(getItemId(position))*/chosenServiceFragment, true);
    }

    //erase provider
    private void eraseProvider(int position) {
        JSONObject jsonObject = new JSONObject();
        try {
            Realm realm = Utils.getRealmInstance(context);
            UserRealm user = realm.where(UserRealm.class).findFirst();
            double id = user.getUserID();
            jsonObject.put("iCustomerUserId", id);
            jsonObject.put("iProviderId", getItem(position).iProviderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.RemoveProviderFromCustomer(context, jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double num) {
       if(num==-1)
      Toast.makeText(context,context.getResources().getString(R.string.not_erase),Toast.LENGTH_SHORT).show();
            }
        });

    }


    public class Holder {
        int s;
        TextView title_1;
        TextView title_2;
        LinearLayout all;
        ImageView icon;
        TextView distance;
        TextView ranking;
        EditText et;
        TextView voters;
        HorizontalScrollView scroll;
        int scrollX, scrollY;
        LinearLayout image;
        RelativeLayout navigate_b, erase_b, order_b;
    }
}

