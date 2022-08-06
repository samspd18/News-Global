package com.satya.newsglobal.ui.fragment.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.satya.newsglobal.databinding.FragmentCategoryNewsListBinding
import com.satya.newsglobal.ui.adapter.CategoryNewsListAdapter
import com.satya.newsglobal.ui.adapter.NewsAdapter
import com.satya.newsglobal.ui.constants.CategoryArray
import com.satya.newsglobal.ui.viewModel.NewsListViewModel

class CategoryNewsListFragment : Fragment() {

    private var _binding: FragmentCategoryNewsListBinding? = null
    private val binding get() = _binding!!
    private var categoryName: String = ""

    private lateinit var viewModel: NewsListViewModel
    private val adapter = CategoryNewsListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryNewsListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        categoryName = arguments?.getString("categoryName").toString().lowercase()
        Log.e("category", categoryName )
        binding.categoryWiseName.text = arguments?.getString("categoryName").toString()

//        sendDataToViewModel
        initViewModel()
        backButtonPress()
        setSharedPrefenceValue(categoryName)
        return root
    }

    private fun backButtonPress() {
        binding.categoryBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
        binding.recyclerViewCategory.adapter = adapter

        viewModel.isLoading.observe(viewLifecycleOwner
        ) { isLoading ->

            if (isLoading != null) {
                if (isLoading) {
                    // hide your progress bar
                    binding.progressBarCategory.visibility = View.GONE
                    binding.recyclerViewCategory.visibility = View.VISIBLE
                } else if(!isLoading) {
                    binding.progressBarCategory.visibility = View.VISIBLE
                    binding.recyclerViewCategory.visibility = View.GONE
                }
            }
        }
        activity?.let { it ->
            viewModel.getNewsListServiceObserver().observe(it) {
                if(it != null) {
                    Log.e("chip", it.toString())
                    adapter.setCategoryWiseNews(it.data)
                    adapter.notifyDataSetChanged()
                    //func for the set data in shared preference
//                    setSharedPrefenceValue(it.category)

                } else {
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //calling the view model function for api call
        viewModel.makeApiCallForCategoryWiseNews(categoryName)
        adapter.notifyDataSetChanged()
    }

    private fun setSharedPrefenceValue(category: String) {
        val sharedPreference =  activity!!.getSharedPreferences(CategoryArray.sharedPreferenceFileName, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("selectedCategory",category)
        editor.commit()
    }
}
//We are using the NewsAdapter for it , cause it same as home