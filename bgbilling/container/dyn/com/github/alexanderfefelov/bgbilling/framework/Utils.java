package com.github.alexanderfefelov.bgbilling.framework;

public interface Utils {

    default String removeNewLines(String s) { return s.replaceAll("\n", " "); }

}
