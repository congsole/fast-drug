package com.example.fastdrug.pharmacy.repository;

import com.example.fastdrug.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
