package com.mscpcr.mscpcr.service;


import com.mscpcr.mscpcr.entity.district;
import com.mscpcr.mscpcr.repository.districtRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class districtService {
    private final districtRepository districtRepository;

    public districtService(districtRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public district createDistrict(district district) {
        return districtRepository.save(district);
    }

    public List<district> getAllDistricts() {
        return districtRepository.findAll();
    }

    public Optional<district> getDistrictById(Long id) {
        return districtRepository.findById(id);
    }

    public Optional<district> getDistrictByCode(String code) {
        return districtRepository.findByCode(code);
    }

    public district updateDistrict(district district) {
        return districtRepository.save(district);
    }

    public void deleteDistrict(Long id) {
        districtRepository.deleteById(id);
    }
}
