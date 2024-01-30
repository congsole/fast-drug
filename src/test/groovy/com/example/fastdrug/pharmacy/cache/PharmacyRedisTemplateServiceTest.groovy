package com.example.fastdrug.pharmacy.cache

import com.example.fastdrug.AbstractIntegrationContainerBaseTest
import com.example.fastdrug.pharmacy.dto.PharmacyDto
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService;

    def setup() {
        pharmacyRedisTemplateService.findAll()
            .forEach(dto -> {
                pharmacyRedisTemplateService.delete(dto.getId())
            })
    }

    def "save success"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto = PharmacyDto.builder()
                                        .id(1L)
                                        .pharmacyName(pharmacyName)
                                        .pharmacyAddress(pharmacyAddress)
                                        .build()
        System.out.println(dto.getId())
        when:
        pharmacyRedisTemplateService.save(dto)
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1
        result.get(0).pharmacyAddress == pharmacyAddress
        result.get(0).pharmacyName == pharmacyName

     }

}
