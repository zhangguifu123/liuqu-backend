package com.omate.liuqu.dto;

import java.util.Date;

public class EmotionRecordDTO {

    private int eid;
    private String emotion;
    private Date emotionRecordDate;
    private int uid;

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public Date getEmotionRecordDate() {
        return emotionRecordDate;
    }

    public void setEmotionRecordDate(Date emotionRecordDate) {
        this.emotionRecordDate = emotionRecordDate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}
