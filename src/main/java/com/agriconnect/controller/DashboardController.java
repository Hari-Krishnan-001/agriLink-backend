package com.agriconnect.controller;

import com.agriconnect.dto.DashboardResponse;
import com.agriconnect.service.DashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping("/student")

    public DashboardResponse getStudentDashboard(
            Authentication authentication) {

        return dashboardService
                .getStudentDashboard(
                        authentication);
    }

    @GetMapping("/farmer")

    public DashboardResponse getFarmerDashboard(
            Authentication authentication) {

        return dashboardService
                .getFarmerDashboard(
                        authentication);
    }

    @GetMapping("/business")

    public DashboardResponse getBusinessDashboard(
            Authentication authentication) {

        return dashboardService
                .getBusinessDashboard(
                        authentication);
    }
}
