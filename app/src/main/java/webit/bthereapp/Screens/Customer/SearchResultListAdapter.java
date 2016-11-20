package webit.bthereapp.Screens.Customer;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.ProviderProfileObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.General.MyLocation;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;

import webit.bthereapp.Screens.Register.BusinessRegister.BusinessPaymentFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;


public class SearchResultListAdapter extends BaseAdapter {

    Context context;
    int width;
    public ProgressDialog progressDialog;

    public void setSearchResulstsObjs(ArrayList<SearchResulstsObj> searchResulstsObjs) {
        this.searchResulstsObjs = searchResulstsObjs;
    }

    ArrayList<SearchResulstsObj> searchResulstsObjs;
    private String string_distance, string_ranking;

    public SearchResultListAdapter(Context context, ArrayList<SearchResulstsObj> searchResulstsObjs) {
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
        final Holder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.search_result_list_new, null);
            holder = new Holder();
            holder.bussiness_page_b = (RelativeLayout) convertView.findViewById(R.id.bussiness_page_rl);
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
            holder.allDistance = (LinearLayout) convertView.findViewById(R.id.all_distance);

            //scrool with animation to first item in list
            if (position == 0) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator animator = ObjectAnimator.ofInt(holder.scroll, "scrollX", holder.order_b.getRight());
                        animator.setDuration(1000);
                        animator.start();

                        ObjectAnimator animator1 = ObjectAnimator.ofInt(holder.scroll, "scrollX", holder.navigate_b.getLeft());
                        animator1.setDuration(1000);
                        animator1.setStartDelay(1000);
                        animator1.start();
                    }
                });
            } else {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        holder.scroll.scrollTo(holder.navigate_b.getLeft(), 0);
                    }
                });
            }
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.bussiness_page_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetProviderProfile(position);
            }
        });

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
        holder.title_1.setText(getItem(position).nvProviderName);
        holder.title_2.setText(getItem(position).nvProviderSlogan);
        if (getItem(position).nvProviderLogo != null) {
            Bitmap bitmap = ConnectionUtils.convertStringToBitmap(getItem(position).nvProviderLogo);
            BitmapDrawable background = new BitmapDrawable(bitmap);
            holder.icon.setImageDrawable(background);
//            holder.icon.setImageBitmap(bitmap);
        } else {
            holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.client_galaxy_icons1_12));
        }

        if (getItem(position).nvProviderHeader != null) {
            Bitmap bmImg = ConnectionUtils.convertStringToBitmap(getItem(position).nvProviderHeader);
            BitmapDrawable background = new BitmapDrawable(bmImg);
            holder.image.setBackgroundDrawable(background);
        } else {
            holder.image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fingers));
        }
        string_ranking = context.getResources().getString(R.string.ranking_, getItem(position).iCustomerRank);
        if (getItem(position).iDistance == -1) {
            //if the server did not sucseed in finding the distance so do not show it
            string_distance = getItem(position).nvAdress;
        } else {
            //get the distance with 2 digits after the point
            String dis=String.format(Locale.US,"%.02f", getItem(position).iDistance);
            string_distance = context.getResources().getString(R.string.way, getItem(position).nvAdress, dis);
        }
        holder.ranking.setText(string_ranking);
        holder.distance.setText(string_distance);
//        holder.voters.setText(string_voters);
        holder.s = holder.scroll.getScrollY();
        return convertView;
    }

    private void GetProviderProfile(final int position) {
        put_progressDialog();
        BusinessProfileFragment.removeInstance();
        String jsonString = "{\"iProviderId\":" + getItem(position).iProviderId + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetProviderProfile(context, jsonObject, new IExecutable<ProviderProfileObj>() {
                    @Override
                    public void onExecute(ProviderProfileObj providerProfileObj) {
                        if (providerProfileObj != null) {
                            ProviderProfileObj.setInstance(providerProfileObj);
                            BusinessProfileFragment.removeInstance();
                            SearchResulstsObj.setInstance((getItem(position)));
                            BusinessProfileFragment.getInstance().put_providerid(getItem(position).iProviderId, getItem(position).nvProviderName, getItem(position).nvAdress);
                            BusinessProfileFragment.getInstance().set_progressDialog(progressDialog);

                            ((NavigetionLayout) context).initFragmentMain(BusinessProfileFragment.getInstance(), true);
                        }
                    }
                }
        );
    }

    public void put_progressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
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

    private void Order(int position) {
        Bundle bundle = new Bundle();
        SearchResulstsObj sr = getItem(position);
        bundle.putString("business", new Gson().toJson(sr));
        ChosenServiceFragment chosenServiceFragment = new ChosenServiceFragment();
        chosenServiceFragment.setArguments(bundle);
        ((NavigetionLayout) context).initFragmentMain(/*ChosenServiceFragment.newInstance(getItemId(position))*/chosenServiceFragment, true);
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
        RelativeLayout navigate_b, bussiness_page_b, order_b;
        LinearLayout allDistance;
    }
}


