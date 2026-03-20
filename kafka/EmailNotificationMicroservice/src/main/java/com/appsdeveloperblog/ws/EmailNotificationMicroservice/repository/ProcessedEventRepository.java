package com.appsdeveloperblog.ws.EmailNotificationMicroservice.repository;

import com.appsdeveloperblog.ws.EmailNotificationMicroservice.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, Long> {

//    @Query(
//        value =
//            """
//            SELECT EXISTS (
//                SELECT 1 FROM processed_events
//                WHERE message_id = :messageId
//            )
//            """,
//        nativeQuery = true
//    )
//    boolean isMessagePresent(String messageId);

    boolean existsByMessageId(String messageId);
}
