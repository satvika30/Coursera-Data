package com.example.courseradata;

import java.util.ArrayList;

import retrofit.RestAdapter;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.details.CourseAttributes;
import com.example.details.UniversityAttributes;
import com.example.details.InstructorAttributes;
import com.example.details.CourseItem;
import com.example.details.CourseItem.Instructor;
import com.example.details.CourseItem.University;


public class MainActivity extends ListActivity {
	
	private static ArrayList<CourseAttributes> courseList;
	private static ArrayList<InstructorAttributes> instructorList;
	private static ArrayList<UniversityAttributes> universityList;
	
	private static ArrayList<CourseItem> courseItemList;
	
	public static final String COURSE_ID = "COURSE_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (isNetworkAvailable()) {
			courseList = new ArrayList<CourseAttributes>();
			instructorList = new ArrayList<InstructorAttributes>();
			universityList = new ArrayList<UniversityAttributes>();

			courseItemList = new ArrayList<CourseItem>();

			TextView tv = (TextView) findViewById(R.id.progress_text);
			tv.setText("Fetching Courses");

			ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
			pb.setVisibility(View.VISIBLE);
			
			FetchCourseData fcd = new FetchCourseData();
			fcd.execute("fetch started");
		} else {
			Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_LONG).show();
		}
	}

	protected boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}
	
	private class FetchCourseData extends AsyncTask<String, String, Void> {
		
		@Override
		protected Void doInBackground(String... params) {
			
			RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Utils.ENDPOINT)
			.build();
			
			CourseraAPICalls api = adapter.create(CourseraAPICalls.class);
			
			String[] tmpCourseFields = {"name","smallIcon"};
			String[] tmpCourseIncludes = {"instructors","universities"};
			
			//Gather course data
			String idQuery = null;
			String fieldQuery = Utils.getFieldQuery(tmpCourseFields);
			String includeQuery = Utils.getIncludeQuery(tmpCourseIncludes);
			
			ArrayList<CourseAttributes> allCourses = api.getCourses(idQuery, fieldQuery, includeQuery).getElements();
			
			courseItemList = new ArrayList<CourseItem>();			
			
			for (int i = 0; i < allCourses.size() && i < 20; i++) {
				CourseAttributes course_attr = allCourses.get(i);
				
				CourseItem course_item = new CourseItem();
				course_item.setCourseId(course_attr.getId());
				course_item.setCourseName(course_attr.getName());
				course_item.setSmallIcon(course_attr.getSmallIcon());
				
				//Instructor Data
				ArrayList<Integer> instructorIdList = course_attr.getLinks().getInstructors();
				
				if (instructorIdList != null) {						

					String[] tmpInstructorFields = {"id","firstName","middleName","lastName"};

					ArrayList<InstructorAttributes> instr_list = api.getInstructors(Utils.getIdQuery(instructorIdList), 
							Utils.getFieldQuery(tmpInstructorFields), null).getElements();

					ArrayList<Instructor> course_item_instr_list = new ArrayList<Instructor>();
					
					for (int k = 0; k < instructorIdList.size(); k++) {
						InstructorAttributes instructor = instr_list.get(k);

						CourseItem.Instructor course_item_instr = course_item.new Instructor();

						course_item_instr.setFirstName(instructor.getFirstName());
						course_item_instr.setMiddleName(instructor.getMiddleName());
						course_item_instr.setLastName(instructor.getLastName());
						course_item_instr_list.add(course_item_instr);
					}
					course_item.setInstructorList(course_item_instr_list);
				}
				
				// University data
				ArrayList<Integer> universityIdList = course_attr.getLinks().getUniversities();	
				
				if (universityIdList != null) {
			
					String[] tmpUniversityFields = {"id","name"};

					ArrayList<UniversityAttributes> univ_list = api.getUniversities(Utils.getIdQuery(universityIdList), 
							Utils.getFieldQuery(tmpUniversityFields), null).getElements();

					ArrayList<University> course_item_univ_list = new ArrayList<University>();

					for (int k = 0; k < universityIdList.size(); k++) {
						UniversityAttributes university = univ_list.get(k);

						CourseItem.University course_item_univ = course_item.new University();

						course_item_univ.setId(university.getId());
						course_item_univ.setName(university.getName());					
						course_item_univ_list.add(course_item_univ);
					}			
					course_item.setUniversityList(course_item_univ_list );
				}
				
				courseItemList.add(course_item);		
				
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			showCourseList();
		}	
		
	}
	
	public void showCourseList() {
		
		ListView lv = getListView();
		CourseListAdapter adapter = new CourseListAdapter(this, R.layout.list_item_course, courseItemList);
		lv.setAdapter(adapter);

		ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.GONE);
		
		TextView tv = (TextView) findViewById(R.id.progress_text);
		tv.setVisibility(View.GONE);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long row) {
				CourseItem item = courseItemList.get(position);

				int courseId = item.getCourseId();

				Intent intent = new Intent(parent.getContext(), CourseDetailsActivity.class);

				intent.putExtra(COURSE_ID, courseId);

				startActivity(intent);
			}
		});
	}
}
