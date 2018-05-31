package com.github.alexanderfefelov.bgbilling.dyn.graalvm;

public enum PolyglotLanguage {

    JS("js"),
    PYTHON("python"),
    RUBY("ruby"),
    R("R");

    PolyglotLanguage(String language) {
        this.language = language;
    }

    public String language() {
        return language;
    }

    private String language;

}
