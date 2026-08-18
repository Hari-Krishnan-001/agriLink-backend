package com.agriconnect.repository;

import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.Crop;
import com.agriconnect.entity.CropOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropOrderRepository
        extends JpaRepository<CropOrder, Long> {

    List<CropOrder> findByBusinessFirm(
            BusinessFirm businessFirm);

    List<CropOrder> findByCrop(
            Crop crop);
}