package com.exercise.brokageFirm.service;

import com.exercise.brokageFirm.model.Asset;
import com.exercise.brokageFirm.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository repository;

    public List<Asset> getAssetsByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);

    }

    public Asset findByCustomerIdAndAssetName(Long customerId, String assetName) {
        return repository.findByCustomerIdAndAssetName(customerId, assetName);

    }

    public Asset updateAsset(Asset asset) {
        return repository.save(asset);
    }

}
