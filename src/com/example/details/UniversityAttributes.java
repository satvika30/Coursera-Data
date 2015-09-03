package com.example.details;

import java.util.ArrayList;

public class UniversityAttributes {
	// Links
	private Links links;
	private class Links {
		private ArrayList<Integer> courses;
		private ArrayList<Integer> instructors;
		
		public ArrayList<Integer> getCourses() {
			return courses;
		}
		public void setCourses(ArrayList<Integer> courses) {
			this.courses = courses;
		}
		public ArrayList<Integer> getInstructors() {
			return instructors;
		}
		public void setInstructors(ArrayList<Integer> instructors) {
			this.instructors = instructors;
		}
	}
	public Links getLinks() {
		return links;
	}
	public void setLinks(Links links) {
		this.links = links;
	}
	
	//Elements
	private int id;								//The University Id
	private String name; 						//The university’s name
		
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
