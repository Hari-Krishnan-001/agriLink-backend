package com.agriconnect.service;

import com.agriconnect.dto.DashboardResponse;
import com.agriconnect.entity.BusinessFirm;
import com.agriconnect.entity.CropOrder;
import com.agriconnect.entity.Farmer;
import com.agriconnect.entity.Student;
import com.agriconnect.entity.User;
import com.agriconnect.repository.BusinessFirmRepository;
import com.agriconnect.repository.CropOrderRepository;
import com.agriconnect.repository.CropRepository;
import com.agriconnect.repository.FarmerRepository;
import com.agriconnect.repository.ProjectRepository;
import com.agriconnect.repository.StudentRepository;
import com.agriconnect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final FarmerRepository farmerRepository;
    private final BusinessFirmRepository businessFirmRepository;
    private final ProjectRepository projectRepository;
    private final CropRepository cropRepository;
    private final CropOrderRepository cropOrderRepository;

    public DashboardService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            FarmerRepository farmerRepository,
            BusinessFirmRepository businessFirmRepository,
            ProjectRepository projectRepository,
            CropRepository cropRepository,
            CropOrderRepository cropOrderRepository) {

        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.farmerRepository = farmerRepository;
        this.businessFirmRepository = businessFirmRepository;
        this.projectRepository = projectRepository;
        this.cropRepository = cropRepository;
        this.cropOrderRepository = cropOrderRepository;
    }

    public DashboardResponse getStudentDashboard(
            Authentication authentication) {

        User user = userRepository.findByEmail(
                        authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found"));

        DashboardResponse response =
                new DashboardResponse();

        response.setTotalProjects(
                projectRepository.countByStudent(student));

        return response;
    }

    public DashboardResponse getFarmerDashboard(
            Authentication authentication) {

        User user = userRepository.findByEmail(
                        authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Farmer farmer = farmerRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Farmer profile not found"));

        DashboardResponse response =
                new DashboardResponse();

        response.setTotalCrops(
                cropRepository.countByFarmer(farmer));

        response.setTotalOrders(
                cropOrderRepository.countByCropFarmer(farmer));

        response.setApprovedOrders(
                cropOrderRepository
                        .countByCropFarmerAndStatus(
                                farmer,
                                "APPROVED"));

        response.setRejectedOrders(
                cropOrderRepository
                        .countByCropFarmerAndStatus(
                                farmer,
                                "REJECTED"));

        double revenue = cropOrderRepository
                .findByCropFarmer(farmer)
                .stream()
                .filter(order ->
                        "APPROVED".equals(
                                order.getStatus()))
                .mapToDouble(CropOrder::getTotalPrice)
                .sum();

        response.setTotalRevenue(revenue);

        return response;
    }

    public DashboardResponse getBusinessDashboard(
            Authentication authentication) {

        User user = userRepository.findByEmail(
                        authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        BusinessFirm businessFirm =
                businessFirmRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business profile not found"));

        DashboardResponse response =
                new DashboardResponse();

        response.setTotalOrders(
                cropOrderRepository
                        .countByBusinessFirm(
                                businessFirm));

        double spending = cropOrderRepository
                .findByBusinessFirm(businessFirm)
                .stream()
                .mapToDouble(CropOrder::getTotalPrice)
                .sum();

        response.setTotalSpending(spending);

        return response;
    }
}