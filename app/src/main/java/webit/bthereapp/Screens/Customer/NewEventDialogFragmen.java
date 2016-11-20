package webit.bthereapp.Screens.Customer;

import android.app.Dialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import webit.bthereapp.CustemViews.CustomViewTimePicker;
import webit.bthereapp.R;
import webit.bthereapp.General.DateTimePicker.DatePicker;

/**

 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewEventDialogFragmen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewEventDialogFragmen extends DialogFragment implements View.OnClickListener {


    // TODO: Rename and change types of parameters

    private ImageButton close;
    private TextView hourtv,comment,datetv;
    private LinearLayout commentll,datell,hourll;
    private EditText comment_et,event_name;
    private Dialog vDialod = null;
    private DatePicker datePicker;
    private CustomViewTimePicker timePicker;
    private Button btn_ok;

    public NewEventDialogFragmen() {
        // Required empty public constructor
    }

    public static NewEventDialogFragmen instance;

    public static NewEventDialogFragmen getInstance() {
        if (instance == null)
            instance = new NewEventDialogFragmen();
        return instance;
    }

    // TODO: Rename and change types and number of parameters
    public static NewEventDialogFragmen newInstance(String param1, String param2) {
        NewEventDialogFragmen fragment = new NewEventDialogFragmen();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.dialog_fragment_new_event, container, false);
        close = (ImageButton) view.findViewById(R.id.close);
        commentll = (LinearLayout)view.findViewById(R.id.comments_ll);
        comment_et= (EditText) view.findViewById(R.id.text_comment);
        comment= (TextView) view.findViewById(R.id.comments_tv);
        datell= (LinearLayout) view.findViewById(R.id.date_ll);
        datetv= (TextView) view.findViewById(R.id.date_tv);
        hourll= (LinearLayout) view.findViewById(R.id.hour_ll);
        hourtv= (TextView) view.findViewById(R.id.hour_tv);
        timePicker = (CustomViewTimePicker) view.findViewById(R.id.cvTimePicker);
        event_name= (EditText) view.findViewById(R.id.event_name);
        btn_ok= (Button) view.findViewById(R.id.btn_ok);

        close.setOnClickListener(this);
        commentll.setOnClickListener(this);
        comment.setOnClickListener(this);
        hourtv.setOnClickListener(this);
        hourll.setOnClickListener(this);
        datetv.setOnClickListener(this);
        datell.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                dismiss();
                break;
            case R.id.comments_ll:
            case R.id.comments_tv:
                comment_et.setVisibility(comment_et.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
                if(comment_et.getVisibility()==View.GONE&&comment_et.getText().length()>0){
                    comment.setText(comment_et.getText());
                }
                break;
            case R.id.date_ll:
            case R.id.date_tv:
                openDialogDate();
                break;
            case R.id.hour_ll:
            case R.id.hour_tv:
                timePicker.setVisibility(timePicker.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
                if(timePicker.getVisibility()==View.GONE){
                    if(toBigerFrom(timePicker.from,timePicker.to)) {
                        hourtv.setText(timePicker.from + " - " + timePicker.to);
                    }else{
                        Toast.makeText(getActivity(),getResources().getString(R.string.hours_not_correct2),Toast.LENGTH_LONG).show();
                        hourtv.setText("");
                    }
                }
                break;
            case R.id.btn_ok:
                if(isValid()){
                    setAppointmentInCalendar();
                    dismiss();
                }else{
                    Toast.makeText(getActivity(),getString(R.string.error_register),Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private boolean isValid() {
        if(toBigerFrom(timePicker.from,timePicker.to)){
            hourtv.setText(timePicker.from+" - "+timePicker.to);
        }else{
            hourtv.setText("");
            return false;
        }
        if(event_name.getText()==null||event_name.getText().length()==0)
            return false;
        if(datetv.getText().toString().equals(getString(R.string.date)))
            return false;
        if(hourtv.getText().toString().equals(getString(R.string.hour)))
            return false;
        return true;
    }


    private boolean toBigerFrom(String nvFromHour, String nvToHour) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date from = sdf.parse(nvFromHour);
            Date to = sdf.parse(nvToHour);
            return from.compareTo(to) != 1;
        } catch (Exception e) {
            return false;
        }
    }

    private void openDialogDate() {
        if(getActivity()!=null) {
            vDialod = new Dialog(getActivity());
            vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
            vDialod.setCanceledOnTouchOutside(false);
            vDialod.setContentView(R.layout.date_picker_dialog);
            datePicker = (DatePicker) vDialod.findViewById(R.id.datePicker);
            Button btnOk = (Button) vDialod.findViewById(R.id.ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, datePicker.getYear());
                    c.set(Calendar.MONTH, datePicker.getMonth());
                    c.set(Calendar.DAY_OF_MONTH, datePicker.getDay());
                    datetv.setText(datePicker.getDay() + "." + (datePicker.getMonth()+1) + "." + datePicker.getYear());
                    vDialod.dismiss();
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


    private long setAppointmentInCalendar() {
        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        eventValues.put("title", "BT "+event_name.getText());
        eventValues.put("description", comment_et.getText().toString());


        try {
            eventValues.put("dtstart", String.valueOf(parseDate(timePicker.from + " " + datetv.getText())));
            eventValues.put("dtend", String.valueOf(parseDate(timePicker.to + " " + datetv.getText())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimeZone timeZone = TimeZone.getDefault();
        eventValues.put("eventTimezone",/*timeZone.getID()*/"UTC/GMT +2:00");
        eventValues.put("hasAlarm", 1);
        Uri eventUri = getActivity().getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());
        return eventID;
    }

    private static long parseDate(String text) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd.MM.yyyy", Locale.US);
        return dateFormat.parse(text+"+0200").getTime();
    }
}
