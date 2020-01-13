package com.iflytek.voicecloud.lfasr.demo.dto;

public class ResultDto {

    private String bg;
    private String ed;
    private String onebest;
    private String speaker;

    @Override
    public String toString() {
        return "Result{" +
                "bg='" + bg + '\'' +
                ", ed='" + ed + '\'' +
                ", onebest='" + onebest + '\'' +
                ", speaker='" + speaker + '\'' +
                '}';
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public String getOnebest() {
        return onebest;
    }

    public void setOnebest(String onebest) {
        this.onebest = onebest;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}
