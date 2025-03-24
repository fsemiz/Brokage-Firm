package com.exercise.brokageFirm.controller;

import com.exercise.brokageFirm.model.Asset;
import com.exercise.brokageFirm.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        Asset createdAsset = assetService.updateAsset(asset);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAsset);
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAssets(@RequestParam Long customerId) {
        return ResponseEntity.ok(assetService.getAssetsByCustomerId(customerId));
    }
}