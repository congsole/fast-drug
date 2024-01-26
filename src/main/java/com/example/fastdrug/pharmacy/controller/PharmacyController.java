package com.example.fastdrug.pharmacy.controller;

import com.example.fastdrug.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.fastdrug.pharmacy.dto.PharmacyDto;
import com.example.fastdrug.pharmacy.entity.Pharmacy;
import com.example.fastdrug.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    /* 데이터 초기 셋팅을 위한 임시 메서드 */
    /* 데이터베이스에 있는 모든 데이터를 레디스에 동기화 하기 */
    @GetMapping("/redis/save")
    public String save() {
        List<Pharmacy> pharmacyList = pharmacyRepositoryService.findAll();

        System.out.println("★ is pharmacyList empty? "+pharmacyList.isEmpty());

        List<PharmacyDto> pharmacyDtoList = pharmacyRepositoryService.findAll()
                .stream().map(pharmacy -> PharmacyDto.builder()
                        .id(pharmacy.getId())
                        .pharmacyName(pharmacy.getPharmacyName())
                        .pharmacyAddress(pharmacy.getPharmacyAddress())
                        .latitude(pharmacy.getLatitude())
                        .longitude(pharmacy.getLongitude())
                        .build())
                .collect(Collectors.toList());
        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);
        return "success";
    }








}
