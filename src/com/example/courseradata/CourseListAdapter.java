package com.example.courseradata;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.details.CourseItem;
import com.example.details.CourseItem.Instructor;
import com.example.details.CourseItem.University;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CourseListAdapter extends ArrayAdapter<CourseItem> {
	private Context context;
	private List<CourseItem> courseItemList;
	private int resource;
	private int width;
	private int height;
	private int imageWidth;


	@SuppressWarnings("deprecation")
	public CourseListAdapter(Context context, int resource, List<CourseItem> objects) {
		super(context, resource, objects);
		this.context = context;
		this.courseItemList = objects;
		this.resource = resource;

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		
		width = display.getWidth();
		height = display.getHeight();
		imageWidth = width/4;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_course, null);
		} else {
			// Reset views
			ImageView iv = ((ImageView) convertView.findViewById(R.id.imageView1));	
			iv.setImageBitmap(null);
			
			TextView tv = (TextView) convertView.findViewById(R.id.university_field);
			tv.setText(null);
			tv = (TextView) convertView.findViewById(R.id.course_field);
			tv.setText(null);
			
			tv = (TextView) convertView.findViewById(R.id.instructor_field);
			tv.setText(null);		
		}
		CourseItem courseItem = courseItemList.get(position);
				
		CourseItemAndView container = new CourseItemAndView();
		container.courseItem = courseItem;
		container.view = convertView;
			
		ImageLoader loader = new ImageLoader();
		loader.execute(container);					
		
		// Course Title
		TextView tv = (TextView) convertView.findViewById(R.id.course_field);
		tv.setText(courseItem.getCourseName());
		
		// Universities
		tv = (TextView) convertView.findViewById(R.id.university_field);

		ArrayList<University> universities = courseItem.getUniversityList();
		ArrayList<String> universityNameList = new ArrayList<String>();

		if (universities != null) {
			for (int i = 0; i < universities.size(); i++) {
				University univ = universities.get(i);
				universityNameList.add(univ.getName());				
			}
			String names = Utils.nameListToString(universityNameList);
			tv.setText(names);
		}
	

		// Instructors
		tv = (TextView) convertView.findViewById(R.id.instructor_field);

		ArrayList<Instructor> instructors = courseItem.getInstructorList();
		ArrayList<String> instructorNameList = new ArrayList<String>();
		
		if (instructors != null) {
			for (int i = 0; i < instructors.size(); i++) {
				Instructor instr = instructors.get(i);
				String instructorName = instr.getFirstName() + " " + instr.getMiddleName() + " " + instr.getLastName();
				instructorNameList.add(instructorName);				
			}			
			String names = Utils.nameListToString(instructorNameList);
			tv.setText(names);
		}

		return convertView;
	}
		
	private class CourseItemAndView {
		public CourseItem courseItem;
		public Bitmap bitmap;
		public View view;
	}
	
	private class ImageLoader extends AsyncTask<CourseItemAndView, Void, CourseItemAndView> {

		@Override
		protected CourseItemAndView doInBackground(CourseItemAndView... params) {

			CourseItemAndView container = params[0];
			CourseItem course_item = container.courseItem;

			try {
				String imageUrl = course_item.getSmallIcon();
				InputStream in = (InputStream) new URL(imageUrl).getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				container.bitmap = bitmap;
				return container;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(CourseItemAndView result) {					
			ImageView iv = (ImageView) result.view.findViewById(R.id.imageView1);
			iv.setAdjustViewBounds(true);
			iv.setMaxHeight(imageWidth);
			iv.setMaxWidth(imageWidth);		
			iv.setImageBitmap(result.bitmap);			
		}
	}	
}
