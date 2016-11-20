package webit.bthereapp.Screens.Register.BusinessRegister;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.R;

/**
 * Created by user on 29/06/2016.
 */
public class ContactListAdapter extends ArrayAdapter<ContactListFragment.Contact> {
    private Context mContext;
    private ArrayList<ContactListFragment.Contact> contactList;
    private  ContactListFragment contactListFragment;



    private ArrayList<ContactListFragment.Contact> saveContactList;
    private boolean chooseAll=true;

    public void setChange(boolean change) {
        this.change = change;
    }

    private boolean change=false;
    int i=0;
    public ContactListAdapter(Context context, ArrayList<ContactListFragment.Contact> contacts,ContactListFragment contactListFragment) {
         super(context, 0, contacts);
         mContext = context;
        contactList = contacts;
        this.contactListFragment=contactListFragment;
        saveContactList = new ArrayList<ContactListFragment.Contact>(contactList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView =  View.inflate(mContext,R.layout.contact_list_row, null);
            holder = new ViewHolder();
            holder.name=(TextView) convertView.findViewById(R.id.contact_name);
            holder. mChoseIV= (ImageView) convertView.findViewById(R.id.image_choose);
            holder.mAllRL= (RelativeLayout) convertView.findViewById(R.id.contact_row_all);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ContactListFragment.Contact contact=contactList.get(position);
        String str = contact.getName();
         holder.name.setText(str);

       if(!contact.isChanged()){
            if(!chooseAll)
        {
            contact.setChecked(false);
            holder. mChoseIV.setVisibility(View.GONE);
            showList();
        }
        else
        {
            contact.setChecked(true);
            holder. mChoseIV.setVisibility(View.VISIBLE);
            showList();
        }
        }
        if(contact.isChecked())
            holder.mChoseIV.setVisibility(View.VISIBLE);
        else
            holder.mChoseIV.setVisibility(View.GONE);

            holder.mAllRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 change=true;
               contact.setChanged(true);
            if (contact.isChecked())
            {
                holder.mChoseIV.setVisibility(View.GONE);
                saveContactList.remove(contact);
                contact.setChecked(false);
                showList();
                if(chooseAll) {
                   contactListFragment. mChooseAllIV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_empty));
                  contactListFragment.chosePressed = false;
                }

           }
                else
            {
                holder.mChoseIV.setVisibility(View.VISIBLE);
                saveContactList.add(contact);
                contact.setChecked(true);
                showList();
            }
             notifyDataSetChanged();
            }
        });

        return convertView;
    }


    public void setChose(boolean b)
    {
        chooseAll=b;
    }
    private  void showList(){
     i++;
    }

    public   void setAllSaveList(boolean b){
        if(b)
            saveContactList = new ArrayList<ContactListFragment.Contact>(contactList);
            else
            saveContactList.clear();
    }

    public ArrayList<ContactListFragment.Contact> getSaveContactList() {
        return saveContactList;
    }

    public void setSaveContactList(ArrayList<ContactListFragment.Contact> saveContactList) {
        this.saveContactList = saveContactList;
    }
    class  ViewHolder{
       TextView name;
      ImageView mChoseIV;
       RelativeLayout mAllRL;
    }
}
