package com.studia.BazaDanych;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RuchRepository extends JpaRepository<RuchEntity, Long> {
    List<RuchEntity> findByGraId(Long graId);
}