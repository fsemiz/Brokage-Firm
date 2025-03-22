package com.exercise.brokageFirm.repository;

import com.exercise.brokageFirm.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByCustomerId(Long customerId); //retrieve all assets of a customer

    Asset findByCustomerIdAndAssetName(Long customerId, String assetName); // retrieve a specific asset of a specific customer

}
