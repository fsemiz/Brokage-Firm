package com.exercise.brokageFirm.service;

import com.exercise.brokageFirm.model.Asset;
import com.exercise.brokageFirm.repository.AssetRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository repository;

    List<Asset> getAssetsByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);

    }

    Asset findByCustomerIdAndAssetName(Long customerId, String assetName) {
        return repository.findByCustomerIdAndAssetName(customerId, assetName);

    }

    public void updateAsset(Asset asset) {
        repository.save(asset);
    }

}
