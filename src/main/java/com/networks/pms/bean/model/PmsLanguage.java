package com.networks.pms.bean.model;

public class PmsLanguage {
    private int langId;//0:中文   1：非中文
    private String langLabel;
    private String langDesc;
    private String guestLanguage;

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getLangLabel() {
        return langLabel;
    }

    public void setLangLabel(String langLabel) {
        this.langLabel = langLabel;
    }

    public String getLangDesc() {
        return langDesc;
    }

    public void setLangDesc(String langDesc) {
        this.langDesc = langDesc;
    }

    public String getGuestLanguage() {
        return guestLanguage;
    }

    public void setGuestLanguage(String guestLanguage) {
        this.guestLanguage = guestLanguage;
    }
}
