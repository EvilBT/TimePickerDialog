package com.jzxiang.pickerview.sample.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.config.PickerConfig;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.data.WheelCalendar;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.util.Date;


public class DuDuTimePickerDialog extends TimePickerDialog implements View.OnClickListener {
    PickerConfig mPickerConfig;
    int minDay, maxDay;
    Date currentDay;
    DuDuOnDateSetListener listener;
    private DuDuTimeWheel mTimeWheel;
    private long mCurrentMillSeconds;

    private static DuDuTimePickerDialog newIntance(PickerConfig pickerConfig, int minDay, int maxDay, Date currentDay,DuDuOnDateSetListener listener) {
        DuDuTimePickerDialog timePickerDialog = new DuDuTimePickerDialog();
        timePickerDialog.initialize(pickerConfig,minDay, maxDay, currentDay, listener);
        return timePickerDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public void onResume() {
        super.onResume();
        int height = getResources().getDimensionPixelSize(com.jzxiang.pickerview.R.dimen.picker_height);

        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);//Here!
        window.setGravity(Gravity.BOTTOM);
    }

    private void initialize(PickerConfig pickerConfig, int minDay, int maxDay, Date currentDay, DuDuOnDateSetListener listener) {
        mPickerConfig = pickerConfig;
        this.minDay = minDay;
        this.maxDay = maxDay;
        this.currentDay = currentDay;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), com.jzxiang.pickerview.R.style.Dialog_NoTitle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(initView());
        return dialog;
    }

    View initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(com.jzxiang.pickerview.R.layout.timepicker_layout, null);
        TextView cancel = (TextView) view.findViewById(com.jzxiang.pickerview.R.id.tv_cancel);
        cancel.setOnClickListener(this);
        TextView sure = (TextView) view.findViewById(com.jzxiang.pickerview.R.id.tv_sure);
        sure.setOnClickListener(this);
        TextView title = (TextView) view.findViewById(com.jzxiang.pickerview.R.id.tv_title);
        View toolbar = view.findViewById(com.jzxiang.pickerview.R.id.toolbar);

        title.setText(mPickerConfig.mTitleString);
        cancel.setText(mPickerConfig.mCancelString);
        sure.setText(mPickerConfig.mSureString);
        toolbar.setBackgroundColor(mPickerConfig.mThemeColor);

        mTimeWheel = new DuDuTimeWheel(view, mPickerConfig, currentDay, minDay, maxDay);
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == com.jzxiang.pickerview.R.id.tv_cancel) {
            dismiss();
        } else if (i == com.jzxiang.pickerview.R.id.tv_sure) {
            sureClicked();
        }
    }
    
    /*
    * @desc This method returns the current milliseconds. If current milliseconds is not set,
    *       this will return the system milliseconds.
    * @param none
    * @return long - the current milliseconds.
    */
    public long getCurrentMillSeconds() {
        if (mCurrentMillSeconds == 0)
            return System.currentTimeMillis();

        return mCurrentMillSeconds;
    }

    /*
    * @desc This method is called when onClick method is invoked by sure button. A Calendar instance is created and 
    *       initialized. 
    * @param none
    * @return none
    */
    void sureClicked() {
        /*Calendar calendar = Calendar.getInstance();
        calendar.clear();

        calendar.set(Calendar.YEAR, mTimeWheel.getCurrentYear());
        calendar.set(Calendar.MONTH, mTimeWheel.getCurrentMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, mTimeWheel.getCurrentDay());
        calendar.set(Calendar.HOUR_OF_DAY, mTimeWheel.getCurrentHour());
        calendar.set(Calendar.MINUTE, mTimeWheel.getCurrentMinute());

        mCurrentMillSeconds = calendar.getTimeInMillis();
        if (mPickerConfig.mCallBack != null) {
            mPickerConfig.mCallBack.onDateSet(this, mCurrentMillSeconds);
        }*/

        long time = mTimeWheel.getDuDuCurrentTime();

        if (listener != null) {
            listener.onDataSet(this, time);
        }

        dismiss();
    }

    public static class Builder {
        PickerConfig mPickerConfig;
        DuDuOnDateSetListener listener;

        int minDay;
        int maxDay;
        Date currentDate;

        public Builder() {
            mPickerConfig = new PickerConfig();
        }

        public Builder setType(Type type) {
            mPickerConfig.mType = type;
            return this;
        }

        public Builder setMinDay(int minDay) {
            this.minDay = minDay;
            return this;
        }

        public Builder setMaxDay(int maxDay) {
            this.maxDay = maxDay;
            return this;
        }

        public Builder setCurrentDate(Date currentDate) {
            this.currentDate = currentDate;
            return this;
        }

        public Builder setListener(DuDuOnDateSetListener listener){
            this.listener = listener;
            return this;
        }

        public Builder setThemeColor(int color) {
            mPickerConfig.mThemeColor = color;
            return this;
        }

        public Builder setCancelStringId(String left) {
            mPickerConfig.mCancelString = left;
            return this;
        }

        public Builder setSureStringId(String right) {
            mPickerConfig.mSureString = right;
            return this;
        }

        public Builder setTitleStringId(String title) {
            mPickerConfig.mTitleString = title;
            return this;
        }

        public Builder setToolBarTextColor(int color) {
            mPickerConfig.mToolBarTVColor = color;
            return this;
        }

        public Builder setWheelItemTextNormalColor(int color) {
            mPickerConfig.mWheelTVNormalColor = color;
            return this;
        }

        public Builder setWheelItemTextSelectorColor(int color) {
            mPickerConfig.mWheelTVSelectorColor = color;
            return this;
        }

        public Builder setWheelItemTextSize(int size) {
            mPickerConfig.mWheelTVSize = size;
            return this;
        }

        public Builder setCyclic(boolean cyclic) {
            mPickerConfig.cyclic = cyclic;
            return this;
        }

        public Builder setMinMillseconds(long millseconds) {
            mPickerConfig.mMinCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setMaxMillseconds(long millseconds) {
            mPickerConfig.mMaxCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setCurrentMillseconds(long millseconds) {
            mPickerConfig.mCurrentCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setYearText(String year){
            mPickerConfig.mYear = year;
            return this;
        }

        public Builder setMonthText(String month){
            mPickerConfig.mMonth = month;
            return this;
        }

        public Builder setDayText(String day){
            mPickerConfig.mDay = day;
            return this;
        }

        public Builder setHourText(String hour){
            mPickerConfig.mHour = hour;
            return this;
        }

        public Builder setMinuteText(String minute){
            mPickerConfig.mMinute = minute;
            return this;
        }

        public Builder setCallBack(OnDateSetListener listener) {
            mPickerConfig.mCallBack = listener;
            return this;
        }

        public DuDuTimePickerDialog build() {
            return newIntance(mPickerConfig, minDay, maxDay, currentDate, listener);
        }

    }


}
