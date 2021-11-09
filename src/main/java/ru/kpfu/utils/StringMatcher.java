package ru.kpfu.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMatcher {

    public StringMatcher(){}

    public boolean matchString(String str, String regex){
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches() || matcher.hitEnd() ||
             str.contains(regex) || regex.contains(str);
    }
}
