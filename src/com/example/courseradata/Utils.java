package com.example.courseradata;

import java.util.ArrayList;

public class Utils {
	
	public static final String ENDPOINT = "https://api.coursera.org";
	
	public static final String[] courseFields = {"id",
		"shortName",
		"name",
		"largeIcon",
		"shortDescription",
		"smallIcon",
		"instructor"};
    
	public static final String[] instructorFields = {"id",
		"photo150",
		"firstName",
		"middleName",
		"lastName",
		"department"};
	public static final String[] universityFields = {"id",
		"name",};

	public static final String[] courseIncludes = {"instructors", "universities"};
	public static final String[] sessionIncludes = {"instructors","courses"};
	public static final String[] instructorIncludes = {"universities", "courses"};
	public static final String[] universityIncludes = {"courses", "instructors"};
	
	public static String nameListToString(ArrayList<String> names) {
		/* Returns names in format "Name 1, Name 2, and Name 3". Parameter must be non-null.*/				
		if (names != null) {
			int n = names.size();

			StringBuilder sb = new StringBuilder();		
			for (int i = 0; i < n; i++) {
				sb.append(names.get(i));				
				if(i == n - 2) {
					sb.append(", and ");
				} else if (i < n - 2) {
					sb.append(", ");
				}				
			}
			return sb.toString();
		}
		return null;
	}

	public static String getIdQuery(int[] ids) {
		StringBuilder sb = new StringBuilder();
				
		for (int i = 0; i < ids.length; i++) {
			sb.append(Integer.toString(ids[i]) + ",");		
		}		
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public static String getIdQuery(ArrayList<Integer> ids) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < ids.size(); i++) {
			sb.append(Integer.toString(ids.get(i)) + ",");		
		}		
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();	
	}
	
	public static String getFieldQuery(String[] fields) {
		StringBuilder sb = new StringBuilder();
			
		for (int i = 0; i < fields.length; i++) {
			sb.append(fields[i] + ",");		
		}		
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public static String getIncludeQuery(String[] includes) {
		StringBuilder sb = new StringBuilder();
				
		for (int i = 0; i < includes.length; i++) {
			sb.append(includes[i] + ",");		
		}		
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

}
