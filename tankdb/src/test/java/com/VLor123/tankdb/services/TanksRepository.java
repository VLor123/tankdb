package com.VLor123.tankdb.services;

import com.VLor123.tankdb.models.Tank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TanksRepository extends JpaRepository<Tank, Integer> {
}
