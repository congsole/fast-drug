package com.example.fastdrug.pharmacy.repository;

import com.example.fastdrug.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
