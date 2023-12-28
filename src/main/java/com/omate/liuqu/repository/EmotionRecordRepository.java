package com.omate.liuqu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.omate.liuqu.model.EmotionRecord;
import java.util.List;

@Repository
public interface EmotionRecordRepository extends JpaRepository<EmotionRecord, Integer> {

    List<EmotionRecord> findByUid(int uid);

    EmotionRecord findByEidAndUid(int eid, int uid);

}
