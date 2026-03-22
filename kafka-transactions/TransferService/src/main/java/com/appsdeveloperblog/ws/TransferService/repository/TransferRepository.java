package com.appsdeveloperblog.ws.TransferService.repository;

import com.appsdeveloperblog.ws.TransferService.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, String> {
}
