package com.example.details;

import java.util.ArrayList;

public class CourseAttributes {

	// Links
	private Links links;
	public class Links {		
		private ArrayList<Integer> instructors;
		private ArrayList<Integer> universities;

		public ArrayList<Integer> getInstructors() {
			return instructors;
		}
		public void setInstructors(ArrayList<Integer> instructors) {
			this.instructors = instructors;
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
		
	// Elements
	private int id;											// The Course Id.
	private String shortName;								// The short name associated with the course.
	private String name;									// The course name or title.
	private String shortDescription;						// Option[String] - A short description of the course.
	private String smallIcon;								// Option[String] - A small icon.
	private String largeIcon;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getSmallIcon() {
		return smallIcon;
	}
	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}
	public String getlargeIcon() {
		return largeIcon;
	}
	public void setlargeIcon(String largeIcon) {
		this.largeIcon = largeIcon;
	}	
}
