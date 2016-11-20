package webit.bthereapp.Utils;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import webit.bthereapp.CustemViews.CustomTimePicker;
import webit.bthereapp.Entities.WorkingHours;
import webit.bthereapp.R;

/**
 * Created by User on 04/04/2016.
 */
public class WorkingHoursUtil {

    public static List<WorkingHours> getMainWorkingHoursList(List<WorkingHours> workingHoursList, List<WorkingHours> mRaceHoursList) {
        List<WorkingHours> mainWorkingHoursList = new ArrayList<>();
        for (WorkingHours workingHours : workingHoursList) {
            for (WorkingHours raceHours : mRaceHoursList) {
                if (workingHours.getiDayInWeekType() == raceHours.getiDayInWeekType()) {
                    if (raceHours.getNvFromHour().equals(raceHours.getNvToHour()))
                        mainWorkingHoursList.add(workingHours);
                    else {
                        mainWorkingHoursList.add(new WorkingHours(workingHours.getiDayInWeekType(), workingHours.getNvFromHour()+":00", raceHours.getNvFromHour()+":00"));
                        mainWorkingHoursList.add(new WorkingHours(workingHours.getiDayInWeekType(), raceHours.getNvToHour()+":00",workingHours.getNvToHour()+":00"));
                    }
                }
            }
        }
        return mainWorkingHoursList;
    }

    public static List<WorkingHours> saveHours(List<WorkingHours> hoursList, CustomTimePicker timePicker, int selectDay) {
        boolean flag = false;
        String fromHour = timePicker.getFromHour();
        String toHour = timePicker.getToHour();
        //change the hours of exsits day
        for (WorkingHours workingHours : hoursList) {
            if (workingHours.getiDayInWeekType() == selectDay) {
                flag = true;
                workingHours.setNvFromHour(fromHour);
                workingHours.setNvToHour(toHour);
            }
        }
        if (!flag)
        hoursList.add(new WorkingHours(selectDay, fromHour, toHour));
        return hoursList;
    }

    public static  boolean hoursValidation(Context context,int selectDay,CustomTimePicker timePicker) {

        String[] strings=context.getResources().getStringArray(R.array.days);
        int fHour = Integer.valueOf(timePicker.getFromHour().replace(":", ""));
        int tHour = Integer.valueOf(timePicker.getToHour().replace(":", ""));
        if (tHour < fHour) {
            String dayName = strings[selectDay - 1];
            Toast.makeText(context, R.string.in + dayName + " " + R.string.invalid_hours, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
