package com.example.fastdrug.api.service;

import com.example.fastdrug.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import org.springframework.http.HttpHeaders;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;
    private final KakaoUriBuilderService kakaoUriBuilderService;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    @Retryable(
            value={RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    public KakaoApiResponseDto requestAddressSearch(String address) {

        if(ObjectUtils.isEmpty(address)) { return null; }

        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

//        System.out.println("♥" + httpEntity.getHeaders());

        // 카카오 api 호출
        KakaoApiResponseDto dto = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();

//        System.out.println(dto.getMetaDto().getTotalCount());
//        System.out.println(dto.getDocumentDtoList().get(0).getLatitude());
//        System.out.println(dto.getDocumentDtoList().get(0).getLongitude());
//        System.out.println(dto.getDocumentDtoList().get(0).getDistance());

        return dto;
    }
    @Recover
    public KakaoApiResponseDto recover(RuntimeException e, String address) {
        log.error("All the retried failed. address: {}, error: {}", address, e.getMessage());
        return null;
    }
}
