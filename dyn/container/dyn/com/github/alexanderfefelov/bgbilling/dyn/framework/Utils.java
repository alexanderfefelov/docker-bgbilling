package com.github.alexanderfefelov.bgbilling.dyn.framework;

public interface Utils {

    default String removeNewLines(String s) { return s.replaceAll("\n", " "); }

}
