package com.example.details;

import java.util.ArrayList;

public class InstructorAttributes {
	// Links (includes)
	private Links links;
    public class Links {
		private ArrayList<Integer> courses;
		private ArrayList<Integer> universities;
				
		public ArrayList<Integer> getCourses() {
			return courses;
		}
		public void setCourses(ArrayList<Integer> courses) {
			this.courses = courses;
		}
		public ArrayList<Integer> getUniversities() {
			return universities;
		}
		public void setUniversities(ArrayList<Integer> universities) {
			this.universities = universities;
		}
	}
	public Links getLinks() {
		return links;
	}
	public void setLinks(Links links) {
		this.links = links;
	}
				
	// Elements (fields)
	private int id;						// Instructor ID
	private String photo150; 			//Option[String] - photo URL
	private String firstName;			// Instructor first name
	private String middleName;			// Instructor middle name
	private String lastName;			// Instructor last name
	private String department;			// Instructor department
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoto150() {
		return photo150;
	}
	public void setPhoto150(String photo150) {
		this.photo150 = photo150;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
}
