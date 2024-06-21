package com.jds.eduTech;

import android.net.Uri;
import android.os.Build;

import org.intellij.lang.annotations.Language;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Utils {

    public static boolean isValid(@Language("RegExp") String regExp, String value) {
        if(value == null) return false;
        Pattern pattern = Pattern.compile(regExp,Pattern.CASE_INSENSITIVE);
        return pattern.matcher(value).find();
    }

    public static String validate(String value, List<ValidationEntry> entries) {
        for(ValidationEntry entry: entries) {
            String errorMessage = entry.validate(value);
            if(errorMessage != null) {
                return errorMessage;
            } else {
                if(entry.getErrorMessage() == null) return null;
            }
        }
        return null;
    }

    public static String validate(String value, ValidationEntry... entries) {
        for(ValidationEntry entry: entries) {
            String errorMessage = entry.validate(value);
            if(errorMessage != null) return errorMessage;
        }
        return null;
    }


    public static String[] name(String fullName){

        String[] names=new String[2];

        String tempFirstName="";
        String tempLastName="";


        if(fullName.contains(" ")){
            String tmp="";
            String []tmp2 = fullName.split(" ");
            for(int i=0;i<tmp2.length;i++){
                if(i==0) {
                    tempFirstName=tmp2[i];
                }else{
                    tmp = tmp.concat(tmp2[i]+" ");
                }
            }
            tempLastName=tmp.trim();
        }else{
            tempFirstName= fullName;
            tempLastName="";
        }


        names[0]=tempFirstName;
        names[1]=tempLastName;

        return names;
    }


    public static String stringSumInsuredFormat(String value){
        if(value == null || value.equals("")) return "";

        DecimalFormat decimalFormat=new DecimalFormat("##,##,##0");
        //System.out.println("decimal:::"+decimalFormat.format(Math.floor(Double.parseDouble(value))));
        return decimalFormat.format(Math.floor(Double.parseDouble(value.replaceAll(",",""))));
    }

    public static String stringSumInsuredFormatNew(String value){
        if(value == null || value.equals("")) return "";

        double val = Double.parseDouble(value.replaceAll(",","").replaceAll("$",""));
        return NumberFormat.getNumberInstance(Locale.US).format(val);
    }


  /*public static Spannable spannableString(String first, String second, Context context){
    Spannable spannable=new SpannableString(first+second);
    Typeface typeface=(ResourcesCompat.getFont(context, R.font.poppins_regular));
    Typeface secondTypeface=(ResourcesCompat.getFont(context, R.font.poppins_regular));

    spannable.setSpan(new CustomTypefaceSpan("regular",secondTypeface), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    spannable.setSpan( new CustomTypefaceSpan("sans-serif",typeface), first.length(), first.length() + second.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    return spannable;
  }*/


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static String numberFormat(String mobile){

        if(mobile == null) return "";

        return mobile.replace("+91","").replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getFileName(Uri uri) {
        try {
            String path = uri.getLastPathSegment();
            return path != null ? path.substring(path.lastIndexOf("/") + 1) : "file";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "file";
    }

}
