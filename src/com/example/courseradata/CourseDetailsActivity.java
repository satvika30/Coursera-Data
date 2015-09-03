package com.example.courseradata;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

import com.example.details.CourseAttributes;
import com.example.details.InstructorAttributes;
import com.example.details.UniversityAttributes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CourseDetailsActivity extends Activity {
	private CourseAttributes Course;
	private InstructorAttributes Instructor;


	private ArrayList<UniversityAttributes> UniversityList;
	private ArrayList<InstructorAttributes> InstructorList;
	
	private boolean instructor_flag = false;
	private boolean university_flag = false;
	private ProgressBar pb;
	private float displayWidth, displayHeight;
	private int logoMaxWidth;
	private int logoMaxHeight;
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_details);
		
		pb = (ProgressBar) findViewById(R.id.progressBar2);
		
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		float scaleH = 0.5f;
		displayWidth = display.getWidth();
		displayHeight = display.getHeight();
		
		logoMaxWidth = (int)(displayWidth);
		logoMaxHeight = (int)(displayHeight*(scaleH));
		
		int courseId = getIntent().getExtras().getInt("COURSE_ID");
		
		LoadCourseDetails task = new LoadCourseDetails();
		task.execute(courseId);		
	}
	
	
	private class ListAndView {
		private List list;
		private View view;
		private String name;
	}

	private class ImageAndView {	
		private String imageUrl;
		private Bitmap bitmap;
		private ImageView iv;		
	}
	
	private void DisplayInformation() {

		ImageView iv = (ImageView) findViewById(R.id.logo);
		iv.setAdjustViewBounds(true);
		iv.setMaxWidth(logoMaxWidth);	
		iv.setMaxHeight(logoMaxHeight);
		
		ImageAndView iav = new ImageAndView();	
		iav.imageUrl = Course.getlargeIcon();
		iav.iv = iv;
		
		LoadImage li = new LoadImage();
		li.execute(iav);

		tv = (TextView) findViewById(R.id.c_name);
		if (Course.getName() != null) {
			tv.setText(Course.getName());
			tv.setVisibility(View.VISIBLE);
		} else {
			tv.setPadding(0, 0, 0, 0);
			tv.setVisibility(View.GONE);
		}
		
		tv = (TextView) findViewById(R.id.c_shortDescription);
		if (Course.getShortDescription() != null) {
			tv.setText(Course.getShortDescription());
			tv.setVisibility(View.VISIBLE);
		} else {
			tv.setPadding(0, 0, 0, 0);
			tv.setVisibility(View.GONE);
		}
	
		
		// Instructors
		if (InstructorList != null) {

			tv = (TextView) findViewById(R.id.Instructors);
			tv.setText("Instructors");
			tv.setVisibility(View.VISIBLE);
			int instructorIconSide = (int) (displayWidth*(1f/3f));
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.instructor_layout);
			
			for (int i = 0; i < InstructorList.size(); i++) {
				Instructor = InstructorList.get(i);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View instructorView = inflater.inflate(R.layout.list_instructors, null);
				
				// Show Image 
				ImageView instructor_icon = (ImageView) instructorView.findViewById(R.id.instructor_item_icon);
				instructor_icon.setAdjustViewBounds(true);
				instructor_icon.setMaxWidth(instructorIconSide);	
				instructor_icon.setMaxHeight(instructorIconSide);

				iav = new ImageAndView();
				iav.imageUrl = InstructorList.get(i).getPhoto150();
				iav.iv = instructor_icon;
				
				li = new LoadImage();
				li.execute(iav);			

				// Description
				// Name
				if (Instructor.getLastName() != null) {
					tv = (TextView) instructorView.findViewById(R.id.instructor_item_name);
					tv.setText(Instructor.getFirstName() + " " + Instructor.getLastName());
					tv.setVisibility(View.VISIBLE);
				}
				
				// Department
				if (Instructor.getDepartment() != null) {
					tv = (TextView) instructorView.findViewById(R.id.instructor_item_department);
					tv.setText(Instructor.getDepartment());
					tv.setVisibility(View.VISIBLE);
				}
	
				// Universities
				ArrayList<Integer> iUniversities = Instructor.getLinks().getUniversities();
				ListAndView lav = new ListAndView();
				lav.list = iUniversities;
     			lav.view = instructorView.findViewById(R.id.instructor_item_university);

				LoadInstructorUniversities liu = new LoadInstructorUniversities();
				liu.execute(lav);

				linearLayout.addView(instructorView);

			}
		}
		
		pb.setVisibility(View.GONE);
	}
	
	
	private class LoadImage extends AsyncTask<ImageAndView, String, ImageAndView> {

		@Override
		protected ImageAndView doInBackground(ImageAndView... params) {
			InputStream is;
			try {
				is = (InputStream) new URL(params[0].imageUrl).getContent(); 	
				Bitmap bm = BitmapFactory.decodeStream(is);
				params[0].bitmap = bm;
				return params[0];
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;			
		}

		@Override
		protected void onPostExecute(ImageAndView result) {
			if (result != null) {
				result.iv.setImageBitmap(result.bitmap);
			}
		}
	}
	
	
	private class LoadCourseDetails extends AsyncTask<Integer, String, CourseAttributes> {
		@Override
		protected CourseAttributes doInBackground(Integer... params) {
			
			RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Utils.ENDPOINT)
			.build();
			
			CourseraAPICalls api = adapter.create(CourseraAPICalls.class);
			
			int[] ids = {params[0]};
			String[] fields = {"name","largeIcon", "shortDescription"};
			String[] includes = {"instructors","universities"};
			
			//Gather course data
			String idQuery = Utils.getIdQuery(ids);
			String fieldQuery = Utils.getFieldQuery(fields);
			String includeQuery = Utils.getIncludeQuery(includes);
					
			
			CourseAttributes course = api.getCourses(idQuery, fieldQuery, includeQuery).getElements().get(0);

			// Get Instructor, University lists and load them		
			ArrayList<Integer> instructorIds = course.getLinks().getInstructors();
			ArrayList<Integer> universityIds = course.getLinks().getUniversities();


			if (instructorIds != null) {
				LoadInstructorDetails instructorTask = new LoadInstructorDetails();
				instructorTask.execute(instructorIds);
			} else {
				InstructorList = null;
				instructor_flag = true;
			}

			if (universityIds != null) {
				LoadUniversityDetails universityTask = new LoadUniversityDetails();
				universityTask.execute(universityIds);
			} else {
				UniversityList = null;
				university_flag = true;
			}

			return course;
		}	

		@Override
		protected void onPostExecute(CourseAttributes result) {
			Course = result;	
		}
	}
	
	private class LoadInstructorDetails extends AsyncTask<ArrayList<Integer>, String, ArrayList<InstructorAttributes>> {

		@Override
		protected ArrayList<InstructorAttributes> doInBackground(ArrayList<Integer>... params) {
			String idQuery = Utils.getIdQuery(params[0]);
			String fieldQuery = Utils.getFieldQuery(Utils.instructorFields);
			String includeQuery = "universities";

			RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Utils.ENDPOINT)
			.build();
			
			CourseraAPICalls api = adapter.create(CourseraAPICalls.class);
			
			ArrayList<InstructorAttributes> instructors = api.getInstructors(idQuery, fieldQuery, includeQuery).getElements();

			return instructors;
		}	

		@Override
		protected void onPostExecute(ArrayList<InstructorAttributes> result) {
			InstructorList = result;	
			instructor_flag = true;
			if (instructor_flag == true && university_flag == true) {	
				instructor_flag = false;        // reset flags
				university_flag = false;        // reset flags
				pb.setVisibility(View.GONE);	
				DisplayInformation();	
			}			
		}
	}
	
	private class LoadUniversityDetails extends AsyncTask<ArrayList<Integer>, String, ArrayList<UniversityAttributes>> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected ArrayList<UniversityAttributes> doInBackground(ArrayList<Integer>... params) {
			String idQuery = Utils.getIdQuery(params[0]);;
			String fieldQuery = Utils.getFieldQuery(Utils.universityFields);
			String includeQuery = null;

			RestAdapter adapter = new RestAdapter.Builder()
			.setEndpoint(Utils.ENDPOINT)
			.build();
			
			CourseraAPICalls api = adapter.create(CourseraAPICalls.class);
			
			ArrayList<UniversityAttributes> universities = api.getUniversities(idQuery, fieldQuery, includeQuery).getElements();

			return universities;
		}	

		@Override
		protected void onPostExecute(ArrayList<UniversityAttributes> result) {
			UniversityList= result;
			university_flag = true;
			if (instructor_flag == true && university_flag == true) {	
				instructor_flag = false;        // reset flags
				university_flag = false;        // reset flags
				pb.setVisibility(View.GONE);	
				DisplayInformation();				
			}		
		}
	}	
	

	private class LoadInstructorUniversities extends AsyncTask<ListAndView, String, ListAndView> {

		@Override
		protected ListAndView doInBackground(ListAndView... params) {

			ArrayList<Integer> ids = (ArrayList<Integer>) params[0].list;

			if (ids != null && ids.size() > 0) {
				ArrayList<String> uNames = new ArrayList<String>();

				RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(Utils.ENDPOINT)
				.build();

				CourseraAPICalls api = adapter.create(CourseraAPICalls.class);


				for (int i = 0; i < ids.size(); i++) {
					uNames.add(api.getUniversities(ids.get(i).toString(), "name", null).getElements().get(0).getName());
				}

				params[0].name = uNames.get(0);
				return params[0];	
			} else {
				return null;
			}


		}

		@Override
		protected void onPostExecute(ListAndView result) {
			TextView tv = (TextView) result.view;
			if (result != null) {
				tv.setText(result.name);
			} 
		}
	}	
}
