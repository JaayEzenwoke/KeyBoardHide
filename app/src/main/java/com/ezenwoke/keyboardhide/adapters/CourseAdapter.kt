package com.ezenwoke.keyboardhide.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ezenwoke.keyboardhide.R
import com.ezenwoke.keyboardhide.model.CourseModel

class CourseAdapter(courseModelArrayList: ArrayList<CourseModel>) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    // creating a variable for array list and context.
    lateinit var  courseModelArrayList: ArrayList<CourseModel>

    // method for filtering our recyclerview items.
    fun filterList(filterList: ArrayList<CourseModel>) {
        // below line is to add our filtered
        // list in our course array list.
        courseModelArrayList = filterList
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // below line is to inflate our layout.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // setting data to our views of recycler view.
        val model: CourseModel = courseModelArrayList[position]
        holder.courseNameTV.setText(model.getCourseName())
        holder.courseDescTV.setText(model.getCourseDescription())
    }

    override fun getItemCount(): Int {
        // returning the size of array list.
        return courseModelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our views.
        lateinit var courseNameTV: TextView
        lateinit var courseDescTV: TextView

        init {
            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName)
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription)
        }
    }

    // creating a constructor for our variables.
    init {
        this.courseModelArrayList = courseModelArrayList
    }
}
