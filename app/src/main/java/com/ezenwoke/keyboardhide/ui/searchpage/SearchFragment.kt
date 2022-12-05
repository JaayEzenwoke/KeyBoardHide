package com.ezenwoke.keyboardhide.ui.searchpage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezenwoke.keyboardhide.MainActivity
import com.ezenwoke.keyboardhide.adapters.CourseAdapter
import com.ezenwoke.keyboardhide.databinding.SearchFragmentBinding
import com.ezenwoke.keyboardhide.model.CourseModel
import java.util.*

class SearchFragment : Fragment() {
    private lateinit var courseRV: RecyclerView

    // variable for our adapter
    // class and array list
    private lateinit var adapter: CourseAdapter
    private lateinit var courseModelArrayList: ArrayList<CourseModel>


    var mAppHeight = 0
    var currentOrientation: Int? = -1

    companion object {
        fun newInstance() = SearchFragment()
    }


    private lateinit var viewModel: SearchViewModel
    var binding: SearchFragmentBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchFragmentBinding.inflate(layoutInflater)
        setKeyboardVisibilityListener()
        buildRecyclerView()
        if(this@SearchFragment.isVisible || this@SearchFragment.isAdded || this@SearchFragment.userVisibleHint || this@SearchFragment.isResumed){
            //showSoftKeyboard(binding?.searchBar?)
            binding?.searchBar?.requestFocus()
        }
        binding?.searchBar?.setOnQueryTextFocusChangeListener { _ , hasFocus ->
            if (hasFocus) {
                val view:View = binding!!.searchBar
                binding?.searchBar?.requestFocus()
                showSoftKeyboard(view)
                //NavHostFragment.findNavController(this).navigate(R.id.action_exploreFragment_to_SearchFragment)
            } else {
                // searchView not expanded
            }
        }

        binding?.searchBar?.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
    }


    fun setKeyboardVisibilityListener(){
        var contentView: View? = activity?.findViewById(android.R.id.content)
        contentView?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            var previousHeight: Int? = null
            override fun onGlobalLayout() {
                var newHeight: Int = contentView.height
                if (newHeight == previousHeight)
                    return
                previousHeight = newHeight
                if (activity?.resources?.configuration?.orientation != currentOrientation)
                    currentOrientation = activity?.resources?.configuration?.orientation

                if (newHeight >= mAppHeight)
                    mAppHeight = newHeight
                if (newHeight != 0) {
                    if (this@SearchFragment.isVisible) {
                        if (mAppHeight > newHeight) {
                            (activity as MainActivity).bottomNavigationView.visibility = View.INVISIBLE
                        }
                        else{
                            (activity as MainActivity).bottomNavigationView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }


    private fun buildRecyclerView() {

        // below line we are creating a new array list
        courseModelArrayList = ArrayList<CourseModel>()


        // below line is to add data to our array list.
        courseModelArrayList.add(CourseModel("DSA", "DSA Self Paced Course"))
        courseModelArrayList.add(CourseModel("JAVA", "JAVA Self Paced Course"))
        courseModelArrayList.add(CourseModel("C++", "C++ Self Paced Course"))
        courseModelArrayList.add(CourseModel("Python", "Python Self Paced Course"))
        courseModelArrayList.add(CourseModel("Fork CPP", "Fork CPP Self Paced Course"))
        courseModelArrayList.add(CourseModel("C#", "Self Paced Course"))
        courseModelArrayList.add(CourseModel("Kotlin", "Self Paced Course"))
        courseModelArrayList.add(CourseModel("React Native", "Self Paced Course"))
        courseModelArrayList.add(CourseModel("React JS", "Self Paced Course"))
        courseModelArrayList.add(CourseModel("Flutter", "Self Paced Course"))
        courseModelArrayList.add(CourseModel("Bash Script", "Self Paced Course"))


        // initializing our adapter class.
        adapter = CourseAdapter(courseModelArrayList)

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(context)
        binding?.idRVCourses?.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        binding?.idRVCourses?.layoutManager = manager

        // setting adapter to
        // our recycler view.
        binding?.idRVCourses?.adapter = adapter
    }


    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<CourseModel>()

        // running a for loop to compare elements.
        for (item in courseModelArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCourseName().toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }


    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return binding?.root//inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}