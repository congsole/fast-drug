package com.example.fastdrug.pharmacy.cache;

import com.example.fastdrug.pharmacy.dto.PharmacyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRedisTemplateService {
    private static final String CACHE_KEY = "PHARMACY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private HashOperations<String, String, String> hashOperations;
    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    /* redis에 저장하기 */
    public void save(PharmacyDto pharmacyDto) {
        if(Objects.isNull(pharmacyDto) || Objects.isNull(pharmacyDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }
        System.out.println("★ " + pharmacyDto.getId());
        try {
            hashOperations.put(CACHE_KEY,
                    pharmacyDto.getId().toString(),
                    serializePharmacyDto(pharmacyDto));
            log.info("[PharmacyRedisTemplateService save success] id: {}", pharmacyDto.getId());
        } catch(Exception e) {
            log.error("[PharmacyRedisTemplateService save fail] {}", e.getMessage());
        }
    }

    /* redis에서 조회하기 */
    public List<PharmacyDto> findAll() {
        try {
            List<PharmacyDto> list = new ArrayList<>();
            for(String value : hashOperations.entries(CACHE_KEY).values()) {
                PharmacyDto pharmacyDto = deserializePharmacyDto(value);
                list.add(pharmacyDto);
            }
            return list;
        } catch(Exception e) {
            return Collections.emptyList();
        }
    }

    /* redis에서 삭제하기 */
    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[PharmacyRedisTemplateService delete]: {}", id);
    }

    /* dto → Json */
    private String serializePharmacyDto(PharmacyDto pharmacyDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pharmacyDto); //json형태로 변경해줌.
    }

    /* Json → dto */
    private PharmacyDto deserializePharmacyDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, PharmacyDto.class);
    }
}
