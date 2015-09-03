package com.example.courseradata;

import com.example.courseradata.CourseraAPIResponses.CourseObject;
import com.example.courseradata.CourseraAPIResponses.InstructorObject;
import com.example.courseradata.CourseraAPIResponses.UniversityObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface CourseraAPICalls {

	// Course calls
	// fields=id,shortName,name,largeIcon,photo,shortDescription,smallIcon and so on
	// includes=sessions,instructors,universities,categories
	@GET("/api/catalog.v1/courses")
	public void getCourses(@Query("ids") String ids, @Query("fields") String fields, @Query("includes") String includes, Callback<CourseObject> response);
		
	@GET("/api/catalog.v1/courses")
	public CourseObject getCourses(@Query("ids") String ids, @Query("fields") String fields, @Query("includes") String includes);
	
	// Instructor calls	
	// fields=id,photo,photo150,firstName,middleName,lastName and so on
	// includes=universities,courses,sessions
	@GET("/api/catalog.v1/instructors")
	public void getInstructors(@Query("ids") String ids, @Query("fields") String fields, @Query("includes") String includes, Callback<InstructorObject> response);

	@GET("/api/catalog.v1/instructors")
	public InstructorObject getInstructors(@Query("ids") String ids, @Query("fields") String fields, @Query("includes") String includes);
		
	// University calls
	// fields=id,name and so on
	// includes=courses,universities
	@GET("/api/catalog.v1/universities")
	public void getUniversities(@Query("ids") String ids, @Query("fields") String fields, @Query("includes") String includes, Callback<UniversityObject> response);
	
	@GET("/api/catalog.v1/universities")
	public UniversityObject getUniversities(@Query("ids") String ids, @Query("fields") String fields, @Query("includes") String includes);
}