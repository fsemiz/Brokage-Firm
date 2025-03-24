package com.exercise.brokageFirm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.exercise.brokageFirm.model.Asset;
import com.exercise.brokageFirm.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCustomerIdAndAssetName_Success() {
        // Arrange
        Asset asset = new Asset(1L,1L, "BTC", 5, 500);
        when(assetRepository.findByCustomerIdAndAssetName(1L, "BTC"))
                .thenReturn(asset);

        // Act
        Asset result = assetService.findByCustomerIdAndAssetName(1L, "BTC");

        // Assert
        assertNotNull(result);
        assertEquals("BTC", result.getAssetName());
        assertEquals(500, result.getUsableSize());
    }

    @Test
    void testAddAsset_Success() {
        // Given
        Asset asset = new Asset(1L,1L, "TRY", 5, 1000);
        when(assetRepository.save(any(Asset.class))).thenReturn(asset);

        // When
        Asset createdAsset = assetService.updateAsset(asset);

        // Then
        assertNotNull(createdAsset);
        assertEquals("TRY", createdAsset.getAssetName());
        assertEquals(1000, createdAsset.getUsableSize());
        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    void testUpdateAssetUsableSize_Success() {
        // Given
        Asset asset = new Asset(1L, 1L, "TRY", 5, 1000);
        Asset assetToUpdate = new Asset(1L, 1L, "TRY", 3, 1500);
        when(assetRepository.findByCustomerIdAndAssetName(1L, "TRY")).thenReturn(asset);
        when(assetRepository.save(any(Asset.class))).thenReturn(assetToUpdate);

        // When
        Asset updatedAsset = assetService.updateAsset(assetToUpdate);

        // Then
        assertNotNull(updatedAsset);
        assertEquals(1500, updatedAsset.getUsableSize());
        verify(assetRepository, times(1)).save(updatedAsset);
    }
}