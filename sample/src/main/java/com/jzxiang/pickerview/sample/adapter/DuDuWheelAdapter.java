package com.jzxiang.pickerview.sample.adapter;

import android.content.Context;

import com.jzxiang.pickerview.adapters.AbstractWheelTextAdapter;
import com.jzxiang.pickerview.sample.util.TimeConstants;
import com.jzxiang.pickerview.sample.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 创建人： zp
 * 创建时间：2017/12/14
 */

public class DuDuWheelAdapter extends AbstractWheelTextAdapter {

    private Date currentDate;

    /**
     * 从今天往前几天
     */
    private int minDay;

    /**
     * 从今天往后几天
     */
    private int maxDay;

    private DateFormat dateFormat = new SimpleDateFormat("MMMd日EEE", Locale.getDefault());

    public DuDuWheelAdapter(Context context) {
        super(context);
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public void setMinDay(int minDay) {
        this.minDay = minDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    @Override
    public int getItemsCount() {
        return minDay + 1 + maxDay;
    }

    @Override
    protected CharSequence getItemText(int index) {
        if (index == minDay) {
            // 是今天
            return "今天";
        }
        Date date = TimeUtils.getDate(currentDate,index - minDay, TimeConstants.DAY);
        return TimeUtils.date2String(date, dateFormat);
    }
}
