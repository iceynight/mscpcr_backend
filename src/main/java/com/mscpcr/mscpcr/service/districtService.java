package com.mscpcr.mscpcr.service;


import com.mscpcr.mscpcr.entity.District;
import com.mscpcr.mscpcr.repository.DistrictRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DistrictService {
    private final DistrictRepository districtRepository;

    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public District createDistrict(District district) {
        return districtRepository.save(district);
    }

    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    public Optional<District> getDistrictById(Long id) {
        return districtRepository.findById(id);
    }

    public Optional<District> getDistrictByCode(String code) {
        return districtRepository.findByCode(code);
    }

    public District updateDistrict(District district) {
        return districtRepository.save(district);
    }

    public void deleteDistrict(Long id) {
        districtRepository.deleteById(id);
    }
}
