package com.studia.BazaDanych;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GraRepository extends JpaRepository<GraEntity, Long> {
    GraEntity findByIdGry(int idGry);
}