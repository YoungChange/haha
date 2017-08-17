package com.hailer.news.util;

/**
 * Created by moma on 17-8-17.
 */

public class TextUtil {
    public static String noCRLF(String target){
        return  target.replaceAll("(\r\n|\r|\n|\n\r)", "");
    }


}
