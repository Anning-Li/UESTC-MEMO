package com.starstudio.projectdemo.utils;

import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.CLOUDY;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.HEAVY_RAIN;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.HEAVY_RAIN_TO_STORM;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.HEAVY_SNOW;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.HEAVY_SNOW_TO_SNOWSTORM;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.LIGHT_RAIN;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.LIGHT_SNOW;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.LIGHT_TO_MODERATE_RAIN;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.LIGHT_TO_MODERATE_SNOW;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.MODERATE_RAIN;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.MODERATE_SNOW;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.MODERATE_TO_HEAVY_RAIN;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.MODERATE_TO_HEAVY_SNOW;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.OVERCAST;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.RAINFALL;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.SNOW;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.SNOWFALL;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.SNOWSTORM;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.SNOW_FLURRY;
import static com.huawei.hms.kit.awareness.status.weather.constant.CNWeatherId.SUNNY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;


import com.google.gson.Gson;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.videoeditor.sdk.p.S;
import com.starstudio.projectdemo.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;


public class OtherUtil {
    public static final HashMap<Integer, Integer> weatherId2Mipmap = new HashMap() {{
        put(SUNNY, R.mipmap.weather2_sun);
        put(CLOUDY, R.mipmap.weather2_cloud);
        put(OVERCAST, R.mipmap.weather2_overcast);
        put(SNOW, R.mipmap.weather2_snow);
        put(SNOW_FLURRY, R.mipmap.weather2_snow);
        put(SNOWFALL, R.mipmap.weather2_snow);
        put(SNOWSTORM, R.mipmap.weather2_snow);
        put(HEAVY_SNOW, R.mipmap.weather2_snow);
        put(LIGHT_SNOW, R.mipmap.weather2_snow);
        put(LIGHT_TO_MODERATE_SNOW, R.mipmap.weather2_snow);
        put(MODERATE_SNOW, R.mipmap.weather2_snow);
        put(MODERATE_TO_HEAVY_SNOW, R.mipmap.weather2_snow);
        put(HEAVY_SNOW_TO_SNOWSTORM, R.mipmap.weather2_snow);
        put(RAINFALL, R.mipmap.weather2_rain);
        put(HEAVY_RAIN, R.mipmap.weather2_rain);
        put(LIGHT_RAIN, R.mipmap.weather2_rain);
        put(MODERATE_RAIN, R.mipmap.weather2_rain);
        put(LIGHT_TO_MODERATE_RAIN, R.mipmap.weather2_rain);
        put(MODERATE_TO_HEAVY_RAIN, R.mipmap.weather2_rain);
        put(HEAVY_RAIN_TO_STORM, R.mipmap.weather2_rain);
    }};
    private static final HashMap<String, String> weekToEng = new HashMap(){{
        put("周一", "Mon.");
        put("周二", "Tue.");
        put("周三", "Wen.");
        put("周四", "Thu.");
        put("周五", "Fri.");
        put("周六", "Sta.");
        put("周日", "Sun.");
    }};
    private static final HashMap<Integer, String> monthToEng = new HashMap(){{
       put(1, "January");
       put(2, "February");
       put(3, "March");
       put(4, "April");
       put(5, "May");
       put(6, "June");
       put(7, "July");
       put(8, "August");
       put(9, "September");
       put(10, "October");
       put(11, "November");
       put(12, "December");
    }};
    private static final Gson gson = new Gson();
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-E");

    public static Bitmap decodeBitmapSafe(String path) {
        Bitmap res = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inDither = true;
        opts.inSampleSize = 8;
        res = BitmapFactory.decodeFile(path, opts);

        return res;
    }
    public static Bitmap scaleSquare(Bitmap originBitmap) {
        Bitmap res;
        int originWidth = originBitmap.getWidth();
        int originHeight = originBitmap.getHeight();
        if (originWidth > originHeight) {
            int x = (originWidth - originHeight) / 2;
            int y = 0;
            res = Bitmap.createBitmap(originBitmap, x, y, originHeight, originHeight);
        } else {
            int y = (originHeight - originWidth) / 2;
            int x = 0;
            res = Bitmap.createBitmap(originBitmap, x, y, originWidth, originWidth);
        }
        return res;
    }

    public static String decodeObject(Object o) {
        return gson.toJson(o);
    }

    public static Object encodeString(String json) {
        return gson.toJson(json);
    }

    public static String getSystemYear() {
        String[] ss = dateFormat.format(new Date(System.currentTimeMillis())).split("-");
        return ss[0];
    }

    public static String getSystemMonthToNumber() {
        String[] ss = dateFormat.format(new Date(System.currentTimeMillis())).split("-");
        if(ss[1].charAt(0) == '0'){
            return ss[1].charAt(1) + "";
        }else{
            return ss[1];
        }
    }

    public static String getSystemMonth() {
        String[] ss = dateFormat.format(new Date(System.currentTimeMillis())).split("-");
        return ss[1];
    }

    public static String getSystemDay() {
        String[] ss = dateFormat.format(new Date(System.currentTimeMillis())).split("-");
        return ss[2];
    }

    public static String getSystemWeek() {
        String[] ss = dateFormat.format(new Date(System.currentTimeMillis())).split("-");
        return ss[3];
    }

    public static String getSystemMonthEng() {
        String[] ss = dateFormat.format(new Date(System.currentTimeMillis())).split("-");
        return monthToEng.get(Integer.valueOf(ss[1]));
    }

    public static String getSystemWeekEng() {
        String[] ss = dateFormat.format(new Date(System.currentTimeMillis())).split("-");
        return weekToEng.get(ss[3]);
    }
    /**
     * 将传递的毫秒时长解析为“时：分：秒”的格式
     * @param time 为时间长度，单位为毫秒
     * @return
     */
    public static String formatLongToTime(int time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        return format.format(time);
    }


    /**
     * 检查两个时间是否位于同一天
     * @param time1
     * @param time2
     * @return 0:代表长度小于一天；1：代表长度大于一天，小于两天；2：代表长度大于等于两天
     */
    public static int checkTimeLength(long time1, long time2) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String t1 = sdf.format(new Date(time1));
        String t2 = sdf.format(new Date(time2));
        String[] time1s = t1.split("-");
        String[] time2s = t2.split("-");
        if (!time1s[0].equals(time2s[0]) || !time1s[1].equals(time2s[1]))
            return 2;
        if (time1s[2].equals(time2s[2]))
            return 0;
        return Math.min(Math.abs(Integer.parseInt(time1s[2]) - Integer.parseInt(time2s[2])), 2);
    }

    /**
     * 根据时间戳得到标准钟表时间 HH:mm
     * @param timestamp 时间戳
     * @return 钟表时间
     */
    public static String getClockTime(long timestamp) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(timestamp);
        return time;
    }

    /**
     * 根据时间戳得到年月日
     * @param timestamp 时间戳
     * @return yyyy-MM-dd
     */
    public static String getYearMonth(long timestamp) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date(timestamp));
        return time;
    }


    /**
     * 根据选择的时间生成时间戳
     * @param hhmm 用户使用TimePicker选择出的时间
     * @return 时间戳
     */
     public static long generateTimestamp(String hhmm) {
         @SuppressLint("SimpleDateFormat")
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         StringBuilder builder = new StringBuilder(getYearMonth(System.currentTimeMillis()));
         builder.append(" ");
         builder.append(hhmm);
         long res = System.currentTimeMillis();
         try {
             res = sdf.parse(builder.toString()).getTime();
         } catch (ParseException e) {
             e.printStackTrace();
         }
         return res;
     }


    /**
     * 将用字符串表示的两个数字相加减进行整合为一个方法便于调用
     * (该方法默认字符串中正数前面不带符号，负数第一位用"-"代表)
     */
    public static String bigNumberOperation(String f, String s){
        String ans = "";

        if(f == null || s == null){
            return ans;
        }

        String[] first = f.split("\\.");
        String[] second = s.split("\\.");
        int num_first = first.length;
        int num_second = second.length;
        //分开整数和小数部分
        String first_integer = first[0];
        String second_integer = second[0];
        //判断是否有小数部分
        String first_decimal = "";
        String second_decimal = "";
        if(num_first == 2){
            first_decimal = first[1];
        }
        if(num_second == 2){
            second_decimal = second[1];
        }
        //两数均为正数
        if(first_integer.charAt(0) != '-' && second_integer.charAt(0) != '-'){
            String ans_decimal = decimalAdd(first_decimal, second_decimal);
            String[] arr = ans_decimal.split("\\.");
            String ans_integer = bigNumberAdd(first_integer, second_integer);
            //若存在进位
            if(arr.length == 2){
                ans_integer = bigNumberAdd(ans_integer, "1");
                ans = ans_integer + "." + arr[0];
            }
            else{
                ans = ans_integer + "." + arr[0];
            }
        }
        //第一个数为负，第二个数为正
        else if(first_integer.charAt(0) == '-' && second_integer.charAt(0) != '-'){
            ans = subOperation(second_integer, first_integer, second_decimal, first_decimal);
        }
        //第一个数为正，第二个为负
        else if(first_integer.charAt(0) != '-' && second_integer.charAt(0) == '-'){
            ans = subOperation(first_integer, second_integer, first_decimal, second_decimal);
        }
        //若两数均为负
        else if(first_integer.charAt(0) == '-' && second_integer.charAt(0) == '-'){
            String ans_decimal = decimalAdd(first_decimal, second_decimal);
            String[] arr = ans_decimal.split("\\.");
            String ans_integer = bigNumberAdd(first_integer.substring(1), second_integer.substring(1));
            if(arr.length == 2){
                ans_integer = bigNumberAdd(ans_integer, "1");
                ans = "-" + ans_integer + "." + arr[0];
            }
            else{
                ans = "-" + ans_integer + "." + arr[0];
            }
        }
        return ans;
    }

    /**
     * 将字符串表示的两数字做减法运算
     */
    public  static  String subOperation(String first_integer, String second_integer, String first_decimal, String second_decimal){
        String ans;
        //比较两数大小
        int compareBigRes = compareBigNum(first_integer, second_integer.substring(1));
        //若正数整数部分大于负数整数部分
        if(compareBigRes == 1){
            String ans_decimal = decimalSub(first_decimal, second_decimal);
            String[] arr = ans_decimal.split("\\.");
            String ans_integer = bigNumberSub(first_integer, second_integer.substring(1));
            if(arr.length == 2){
                ans_integer = bigNumberSub(ans_integer, "1");
                ans = ans_integer + "." + arr[0];
            }
            else{
                ans = ans_integer + "." + arr[0];
            }
        }
        //若正数整数部分小于负数整数部分
        else if (compareBigRes == -1){
            String ans_decimal = decimalSub(second_decimal, first_decimal);
            String[] arr = ans_decimal.split("\\.");
            String ans_integer = bigNumberSub(second_integer.substring(1), first_integer);
            if(arr.length == 2){
                ans_integer = bigNumberSub(ans_integer, "1");
                ans = "-" + ans_integer + "." + arr[0];
            }else{
                ans = "-" + ans_integer + "." + arr[0];
            }
        }
        //若正数整数部分等于负数
        else{
            int compareDecimalRes = compareDecimalNum(first_decimal, second_decimal);
            if(compareDecimalRes == 1){
                String ans_decimal = decimalSub(first_decimal, second_decimal);
                String[] arr = ans_decimal.split("\\.");
                String ans_integer = bigNumberSub(first_integer, second_integer.substring(1));
                if(arr.length == 2){
                    ans_integer = bigNumberSub(ans_integer, "1");
                    ans = ans_integer + "." + arr[0];
                }
                else{
                    ans = ans_integer + "." + arr[0];
                }
            }
            else if(compareDecimalRes == -1){
                String ans_decimal = decimalSub(second_decimal, first_decimal);
                String[] arr = ans_decimal.split("\\.");
                String ans_integer = bigNumberSub(second_integer.substring(1), first_integer);
                if(arr.length == 2){
                    ans_integer = bigNumberSub(ans_integer, "1");
                    ans = "-" + ans_integer + "." + arr[0];
                }else{
                    ans = "-" + ans_integer + "." + arr[0];
                }
            }
            else{
                ans = "0";
            }
        }
        return  ans;
    }

    /**
     * 将用字符串表示的整数部分进行相加 (该方法默认字符串中不包含 "+" 或 ”-“ 号)
     * @param f 第一个数整数部分
     * @param s 第一个数整数部分
     * @return  整数加法结果
     */
    public static String bigNumberAdd(String f, String s){
        // 翻转两个字符串，并转换成数组
        char[] a = new StringBuffer(f).reverse().toString().toCharArray();
        char[] b = new StringBuffer(s).reverse().toString().toCharArray();
        int lenA = a.length;
        int lenB = b.length;

        // f 和 s 均为 0 时的特殊情况
        if(lenA == 1 && lenB == 1 && a[0] == '0' && b[0] == '0'){
            return "0";
        }

        // 计算两个长字符串中的较长字符串的长度
        int len = lenA > lenB ? lenA : lenB;
        int[] result = new int[len + 1];
        for (int i = 0; i < len + 1; i++) {
            // 如果当前的i超过了其中的一个，就用0代替，和另一个字符数组中的数字相加
            int aint = i < lenA ? (a[i] - '0') : 0;
            int bint = i < lenB ? (b[i] - '0') : 0;
            result[i] = aint + bint;
        }
        // 处理结果集合，如果大于10的就向前一位进位，本身进行除10取余
        for (int i = 0; i < result.length; i++) {
            if (result[i] >= 10) {
                result[i + 1] += result[i] / 10;
                result[i] %= 10;
            }
        }
        StringBuffer sb = new StringBuffer();
        // 该字段用于标识是否有前置0（即判断最高位是否有进位），如果有0(代表没有进位)就不要存储
        boolean flag = true;
        // 注意从后往前
        for (int i = len; i >= 0; i--) {
            if (result[i] == 0 && flag) {
                continue;
            } else {
                flag = false;
            }
            sb.append(result[i]);
        }
        // 结果
        return sb.toString();
    }


    /**
     * 将用字符串表示的整数部分进行相减 (该方法默认字符串中不包含 "+" 或 ”-“ 号)
     * @param f 第一个数整数部分
     * @param s 第一个数整数部分
     * @return  整数减法结果
     */
    public static String bigNumberSub(String f, String s) {
        // 将字符串翻转并转换成字符数组
        char[] a = new StringBuffer(f).reverse().toString().toCharArray();
        char[] b = new StringBuffer(s).reverse().toString().toCharArray();
        int lenA = a.length;
        int lenB = b.length;
        // 找到最大长度
        int len = lenA > lenB ? lenA : lenB;
        int[] result = new int[len];

        // 计算结果集，如果最终结果为正，那么就a-b否则的话就b-a
        for (int i = 0; i < len; i++) {
            int aint = i < lenA ? (a[i] - '0') : 0;
            int bint = i < lenB ? (b[i] - '0') : 0;
            result[i] = aint - bint;

        }
        // 如果结果集合中的某一位小于零，那么就向前一位借一，然后将本位加上10。其实就相当于借位做减法
        for (int i = 0; i < result.length - 1; i++) {
            if (result[i] < 0) {
                result[i + 1] -= 1;
                result[i] += 10;
            }
        }

        StringBuffer sb = new StringBuffer();

        // 判断是否有前置0,并去掉前置0
        boolean flag = true;
        for (int i = len - 1; i >= 0; i--) {
            if (result[i] == 0 && flag) {
                continue;
            } else {
                flag = false;
            }
            sb.append(result[i]);
        }

        // 如果最终结果集合中没有值，就说明是两值相等，最终返回0
        if (sb.toString().equals("")) {
            sb.append("0");
        }
        // 返回值
        return sb.toString();
    }

    /**
     * 将用字符串表示的小数部分进行相加 (该方法默认字符串中不包含 "+" 或 ”-“ 号)
     * @param f 第一个数小数部分
     * @param s 第二个数小数部分
     * @return 返回运算结果，若有向整数的进位则在结果尾加上.1
     */
    public static String decimalAdd(String f, String s){
        // 两个字符串转换成数组,不翻转
        char[] a = new StringBuffer(f).toString().toCharArray();
        char[] b = new StringBuffer(s).toString().toCharArray();
        int lenA = a.length;
        int lenB = b.length;
        //找到最大长度
        int len = lenA > lenB ? lenA : lenB;
        //若相加两数均为空则返回结果“0”
        if(len==0)
            return  "0";
        //设置进位标志，若有进位置为1
        int flg=0;
        int[] result = new int[len];
        for (int i = len-1; i >=0; i--) {
            // 如果当前i所指向数为空，就用0代替，和另一个字符数组中的数字相加
            int aint = i < lenA ? (a[i] - '0') : 0;
            int bint = i < lenB ? (b[i] - '0') : 0;
            result[i] = aint + bint;
        }
        // 处理结果集合，如果大于10的就向前一位进位，本身进行除10取余
        for (int i = len-1; i >=1; i--) {
            if (result[i] >= 10) {
                result[i-1] += 1;
                result[i] %= 10;
            }
        }
        if(result[0]>=10){
            flg=1;
            result[0]-=10;
        }
        StringBuffer sb=new StringBuffer();
        int j=-1;
        // 判断是否有后置0，若有则抛弃
        boolean flag = true;
        for (int i = len - 1; i >= 0; i--) {
            if (result[i] == 0 && flag) {
                continue;
            } else {
                flag = false;
                j=i;
                break;
            }
        }

        for(int i = 0; i <= j; i++)
            sb.append(result[i]);
        if (sb.toString().equals("")) {
            sb.append("0");
        }

        //如果有进位，将进位放在结果的最后一位，与数值用.分隔
        if(flg==1){
            sb.append(".1");
        }
        // 结果
        return sb.toString();
    }

    /**
     * 将用字符串表示的小数部分进行相减 (该方法默认字符串中不包含 "+" 或 ”-“ 号)
     * @param f 第一个数小数部分
     * @param s 第二个数小数部分
     * @return 返回运算结果，若有向整数的借位则在结果尾加上.1
     */
    public static String decimalSub(String f, String s) {
        // 将字符串转换成字符数组,不反转
        if(f.equals("") && s.equals(""))
            return  "0";
        char[] a = new StringBuffer(f).toString().toCharArray();
        char[] b = new StringBuffer(s).toString().toCharArray();
        int lenA = a.length;
        int lenB = b.length;
        // 找到最大长度
        int len = lenA > lenB ? lenA : lenB;
        int[] result = new int[len];

        for (int i = len-1; i >=0; i--) {
            int aint = i < lenA ? (a[i] - '0') : 0;
            int bint = i < lenB ? (b[i] - '0') : 0;
            result[i]=aint-bint;
        }
        // 如果结果集合中的某一位小于零，那么就向前一位借一，然后将本位加上10。其实就相当于借位做减法
        for (int i = len-1; i >0; i--) {
            if (result[i] < 0) {
                result[i - 1] -= 1;
                result[i] += 10;
            }
        }

        //设置借位标志
        int flg = 0;
        if(result[0] < 0){
            flg=1;
            result[0] += 10;
        }
        StringBuffer sb = new StringBuffer();

        int j =- 1;
        boolean flag = true;
        for (int i = len - 1; i >= 0; i--) {
            if (result[i] == 0 && flag) {
                continue;
            } else {
                flag = false;
                j = i;
                break;
            }
        }
        for(int i = 0; i <= j; i++){
            sb.append(result[i]);
        }
        // 如果最终结果集合中没有值，就说明是两值相等，最终返回0
        if (sb.toString().equals("")) {
            sb.append("0");
        }
        //以.分割借位
        if(flg == 1)
            sb.append(".1");

        // 返回值
        return sb.toString();
    }


    /**
     * 比较整数部分大小
     * @param f 第一个数整数部分
     * @param s 第二个数整数部分
     * @return 1代表f>s,0代表f=s，-1代表f<s
     */
    public static int compareBigNum(String f,String s){
        char[] a = new StringBuffer(f).toString().toCharArray();
        char[] b = new StringBuffer(s).toString().toCharArray();
        int lenA = f.length();
        int lenB = s.length();
        // 判断最终结果的正负
        if(lenA > lenB)
            return  1;
        if (lenA < lenB) {
            return  -1;
        } else if (lenA == lenB) {
            int i = 0;
            while (i < lenA && a[i] == b[i]) {
                i++;
            }
            if(i == lenA)
                return  0;
            if (a[i] < b[i])
                return  -1;
            if(a[i] > b[i])
                return  1;
            if(i == lenA-1)
                return 0;
        }
        return 1;
    }

    /**
     * 比较小数部分大小
     * @param f 第一个数小数部分
     * @param s 第二个数小数部分
     * @return 1代表f>s,0代表f=s，-1代表f<s
     */
    public static int compareDecimalNum(String f,String s){
        char[] a = new StringBuffer(f).toString().toCharArray();
        char[] b = new StringBuffer(s).toString().toCharArray();
        int lenA = f.length();
        int lenB = s.length();
        //判断结果正负
        int i = 0;
        while(i<lenA && i<lenB){
            if(a[i] < b[i])
                return -1;
            if(a[i] > b[i])
                return 1;
            i++;
        }
        if(i == lenA && lenA < lenB)
            return  -1;
        if(i == lenA && lenA == lenB)
            return  0;
        return 1;
    }


    public  static  boolean isNUm(String s){
        char[] a=new StringBuffer(s).toString().toCharArray();
        int len=a.length;
        int num=0;
        for(int i=0;i<len;i++){
            if(a[i]=='.'){
                num++;
                if(num>1)
                    return  false;
            }
        }
        return  true;
    }

    public static Boolean isStringToNum(String s){
        char[] a=new StringBuffer(s).toString().toCharArray();
        int len=a.length;
        int num=0;
        for(int i=0;i<len;i++){
            if(a[i]=='.'){
                num++;
                if(num>1)
                    return  false;
            }
        }
        return  true;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int p2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px 转换为 sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 sp 转换为 px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }



}
