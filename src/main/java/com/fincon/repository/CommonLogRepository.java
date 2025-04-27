package com.fincon.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fincon.model.CommonLog;

@Repository
public interface CommonLogRepository extends JpaRepository<CommonLog, UUID> {
}