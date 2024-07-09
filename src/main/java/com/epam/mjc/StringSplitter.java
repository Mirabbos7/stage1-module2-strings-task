package com.epam.mjc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public static void main(String[] args) {
        StringSplitter stringSplitter = new StringSplitter();
        System.out.println(stringSplitter.split("private void log(String logString, LogLevel level, Context context)", List.of(" ", ")", "(")));
    }
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        List<String> result = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(source, String.join("", delimiters));
        while (st.hasMoreTokens()){
            result.add(st.nextToken());
        }
        return result;
    }
    public List<String> split(String source, Collection<String> delimiters) {
        List<String> result = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(source, List.of(" ", "(", ")").toString());
        while (st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result;
    }
}
