package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.lang.Math.abs;

/**
 * Created by Tyhj on 2017/5/18.
 */

public class TimeUtil {

    //获取今天时间
    public static String getNowTime(){
        String time;
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日-HH:mm");
        time=df.format(now);
        return time;
    }

    //获取明天时间
    public static String getNextTime(){
        String time;
        Date next = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(next);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        next=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
        time=df.format(next);
        return time;
    }


    //获取倒计时
    public String countDown(String startTime) {
        if (startTime == null)
            return null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        long l, mouth, day, hour, min, s, year;
        try {
            java.util.Date date = df.parse(df.format(Long.parseLong(startTime)));
            l = now.getTime() - date.getTime();
            year = l / (365 * 24 * 60 * 60 * 1000);
            mouth = l / (30 * 24 * 60 * 60 * 1000);
            day = l / (24 * 60 * 60 * 1000);
            hour = (l / (60 * 60 * 1000) - day * 24);
            min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if(l>0){
            return null;
        }

        return getTime4Int(new int[]{(int) abs(year), (int) abs(mouth),(int) abs(day), (int) abs(hour), (int) abs(min), (int) abs(s)});

    }


    public static String getTime4Int(int[] a){
        String time="";
        for(int i=0;i<a.length;i++){
            switch (i){
                case 0:
                    if(a[i]!=0){
                        time=time+a[i]+"年 ";
                    }
                    break;
                case 1:
                    time=time+a[i]+"月 ";
                    break;
                case 2:
                    time=time+a[i]+"日 ";
                    break;
                case 3:
                case 4:
                case 5:
                    if(a[i]<10){
                        time=time+"0"+a[i]+":";
                    }else {
                        time=time+a[i]+":";
                    }
                    break;
                default:
                    break;
            }
        }
        return time;
    }


}
