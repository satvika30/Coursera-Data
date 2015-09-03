package com.example.courseradata;

import java.util.ArrayList;

import com.example.details.CourseAttributes;
import com.example.details.InstructorAttributes;
import com.example.details.UniversityAttributes;


public class CourseraAPIResponses {		
		
	// Objects to be passed and called by CourseraAPI
	public class CourseObject {

		ArrayList<CourseAttributes> elements;
		public ArrayList<CourseAttributes> getElements() {
			return elements;
		}

		public void setElements(ArrayList<CourseAttributes> elements) {
			this.elements = elements;
		}		
	}

		
	public class InstructorObject {

		ArrayList<InstructorAttributes> elements;

		public ArrayList<InstructorAttributes> getElements() {
			return elements;
		}

		public void setElements(ArrayList<InstructorAttributes> elements) {
			this.elements = elements;
		}		
	}
		
	public class UniversityObject {

		ArrayList<UniversityAttributes> elements;

		public ArrayList<UniversityAttributes> getElements() {
			return elements;
		}

		public void setElements(ArrayList<UniversityAttributes> elements) {
			this.elements = elements;
		}		
	}		
		
}