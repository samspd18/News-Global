package com.satya.newsglobal.ui.fragment.Global

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.satya.newsglobal.R
import com.satya.newsglobal.databinding.FragmentGlobalBinding
import com.satya.newsglobal.ui.adapter.CategoryNewsListAdapter
import com.satya.newsglobal.ui.adapter.NewsAdapter
import com.satya.newsglobal.ui.constants.CategoryArray
import com.satya.newsglobal.ui.constants.CategoryArray.Companion.navBar
import com.satya.newsglobal.ui.viewModel.NewsListViewModel

class GlobalFragment : Fragment() {

    private var _binding: FragmentGlobalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsListViewModel
    private val adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentGlobalBinding.inflate(inflater, container, false)
        initViewModel()
        setSharedPrefenceValue("World")

        return binding.root
    }

    private fun setSharedPrefenceValue(categoryName: String) {
        val sharedPreference =  activity!!.getSharedPreferences(CategoryArray.sharedPreferenceFileName, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("selectedCategory",categoryName)
        editor.commit()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
        binding.recyclerViewWorld.adapter = adapter

        viewModel.isLoading.observe(viewLifecycleOwner
        ) { isLoading ->

            if (isLoading != null) {
                if (isLoading) {
                    // hide your progress bar
                    binding.progressBarWorld.visibility = View.GONE
                    binding.recyclerViewWorld.visibility = View.VISIBLE
                } else if(!isLoading) {
                    binding.progressBarWorld.visibility = View.VISIBLE
                    binding.recyclerViewWorld.visibility = View.GONE
                }
            }
        }
        activity?.let { it ->
            viewModel.getNewsListServiceObserver().observe(it) {
                if(it != null) {
                    Log.e("chip", it.toString())
                    adapter.setNewsList(it.data)
                    adapter.notifyDataSetChanged()
                    //func for the set data in shared preference
//                    setSharedPrefenceValue(it.category)

                } else {
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //calling the view model function for api call
        viewModel.makeApiCallForCategoryWiseNews("world")
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        navBar= activity!!.findViewById(R.id.nav_view)
        navBar.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        navBar= activity!!.findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}