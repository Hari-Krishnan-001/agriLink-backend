package com.agriconnect.dto;

public class StudentResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String college;

    private String department;

    private String year;

    private String skills;

    private String researchInterests;

    private String about;

    public StudentResponse(
            Long id,
            String fullName,
            String email,
            String phone,
            String college,
            String department,
            String year,
            String skills,
            String researchInterests,
            String about) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.college = college;
        this.department = department;
        this.year = year;
        this.skills = skills;
        this.researchInterests = researchInterests;
        this.about = about;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCollege() {
        return college;
    }

    public String getDepartment() {
        return department;
    }

    public String getYear() {
        return year;
    }

    public String getSkills() {
        return skills;
    }

    public String getResearchInterests() {
        return researchInterests;
    }

    public String getAbout() {
        return about;
    }
}