package com.hjc.herol.util;

public class ServletPathMatcher implements PatternMatcher {

    private final static ServletPathMatcher instance = new ServletPathMatcher();

    public static ServletPathMatcher getInstance() {
        return instance;
    }

    public boolean matches(String pattern, String source) {
        if (pattern == null || source == null) {
            return false;
        }
        pattern = pattern.trim();
        source = source.trim();
        if (pattern.endsWith("*")) {
            int length = pattern.length() - 2;
            //if (source.length() >= length) {
                if (source.contains(pattern.substring(0, length))) {
                    return true;
                }
            //}
        } else if (pattern.startsWith("*")) {
            int length = pattern.length() - 1;
            if (source.length() >= length && source.endsWith(pattern.substring(1))) {
                return true;
            }
        } else {
            if (source.endsWith(pattern)) {
                return true;
            }
        }
        return false;
    }
}
