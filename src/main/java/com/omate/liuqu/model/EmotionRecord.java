package com.omate.liuqu.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "emotion_record")
public class EmotionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eid")
    private int eid;

    @Column(name = "emotion", nullable = false)
    private String emotion;

    @Column(name = "emotion_record_date", nullable = false)
    private Date emotionRecordDate;

    @Column(name = "uid", nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmotionRecord that = (EmotionRecord) o;
        return eid == that.eid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eid);
    }

    @Override
    public String toString() {
        return "EmotionRecord{" +
                "eid=" + eid +
                ", emotion='" + emotion + '\'' +
                ", emotionRecordDate=" + emotionRecordDate +
                ", uid=" + uid +
                '}';
    }
}
