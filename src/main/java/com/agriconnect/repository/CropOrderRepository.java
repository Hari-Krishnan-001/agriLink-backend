package com.agriconnect.repository;

import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.Crop;
import com.agriconnect.entity.CropOrder;
import com.agriconnect.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropOrderRepository
        extends JpaRepository<CropOrder, Long> {

    List<CropOrder> findByBusinessFirm(
            BusinessFirm businessFirm);

    long countByBusinessFirm(
            BusinessFirm businessFirm);

    List<CropOrder> findByCrop(
            Crop crop);

    List<CropOrder> findByCropFarmer(
            Farmer farmer);

    long countByCropFarmer(
            Farmer farmer);

    long countByCropFarmerAndStatus(
            Farmer farmer,
            String status);
}