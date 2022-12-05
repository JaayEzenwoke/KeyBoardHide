package com.ezenwoke.keyboardhide.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.ezenwoke.keyboardhide.MainActivity
import com.ezenwoke.keyboardhide.R
import com.ezenwoke.keyboardhide.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment(), View.OnClickListener{

    private var binding: FragmentHomeBinding? = null



    var mAppHeight = 0
    var currentOrientation: Int? = -1

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setKeyboardVisibilityListener()
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding?.searchBar?.setOnClickListener(this)
        //binding?.searchBar?.setOnTouchListener(this)
        binding?.searchBar?.setOnQueryTextFocusChangeListener { _ , hasFocus ->
            if (hasFocus) {
                NavHostFragment.findNavController(this).navigate(R.id.action_HomeFragment_to_SearchFragment)
            } else {
                // searchView not expanded
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        val root: View = binding!!.root

        val textView: TextView = binding!!.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
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
                    if (this@HomeFragment.isVisible) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.searchBar -> {
                    NavHostFragment.findNavController(this).navigate(R.id.action_HomeFragment_to_SearchFragment)
                }
            }
        }
    }
}