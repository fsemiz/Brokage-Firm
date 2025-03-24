package com.exercise.brokageFirm.repository;

import com.exercise.brokageFirm.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByCustomerId(Long customerId); //retrieve all assets of a customer

    Asset findByCustomerIdAndAssetName(Long customerId, String assetName); // retrieve a specific asset of a specific customer

}
