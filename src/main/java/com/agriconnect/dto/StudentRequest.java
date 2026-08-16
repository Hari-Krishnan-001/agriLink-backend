package com.agriconnect.dto;

public class StudentRequest {

    private String college;

    private String department;

    private String year;

    private String skills;

    private String researchInterests;

    private String about;

    public StudentRequest() {
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getResearchInterests() {
        return researchInterests;
    }

    public void setResearchInterests(String researchInterests) {
        this.researchInterests = researchInterests;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}