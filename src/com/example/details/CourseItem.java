package com.example.details;

import java.util.ArrayList;


public class CourseItem {
	// Course catalog
    private int courseId;
	private String courseName;
	private String smallIcon;
	
	
	// University catalog
	public class University {		
		private int id;
		private String name;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}		
	}
	private ArrayList<University> universityList;

	
	// Instructor catalog
	public class Instructor {
		private int id;
		private String firstName;
		private String middleName;
		private String lastName;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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
	}	
	private ArrayList<Instructor> instructorList;

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSmallIcon() {
		return smallIcon;
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public ArrayList<Instructor> getInstructorList() {
		return instructorList;
	}

	public void setInstructorList(ArrayList<Instructor> instructorList) {
		this.instructorList = instructorList;
	}

	public ArrayList<University> getUniversityList() {
		return universityList;
	}

	public void setUniversityList(ArrayList<University> universityList) {
		this.universityList = universityList;
	}
}
