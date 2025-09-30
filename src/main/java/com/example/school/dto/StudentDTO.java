package com.example.school.dto;

import java.util.List;

public class StudentDTO {
    private String firstName;
    private String lastName;
    private String email;
    private AddressDTO address;
    private List<Long> courses;

    // Getters e setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public AddressDTO getAddress() { return address; }
    public void setAddress(AddressDTO address) { this.address = address; }
    public List<Long> getCourses() { return courses; }
    public void setCourses(List<Long> courses) { this.courses = courses; }

    public static class AddressDTO {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        // Getters e setters
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    }
}
