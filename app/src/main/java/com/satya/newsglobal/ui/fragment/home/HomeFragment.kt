package com.satya.newsglobal.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.satya.newsglobal.databinding.FragmentHomeBinding
import com.satya.newsglobal.ui.adapter.NewsAdapter
import com.satya.newsglobal.ui.viewModel.NewsListViewModel
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var sharedPreferenceFileName = "NewsGlobalData"

    private lateinit var viewModel: NewsListViewModel
    private val adapter = NewsAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        var selectedChipText: String
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.chipGroupFilter.children.toList().filter { (it as Chip).isCheckable }.forEach{ it ->
            it.setOnClickListener {
                selectedChipText = (it as Chip).text as String
                sendDataToViewModel(selectedChipText.lowercase())
            }
        }
        initViewModel()
        initLoader()


        return root
    }

    private fun initLoader() {

    }

    private fun sendDataToViewModel(selectedChipText: String) {
        viewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
        viewModel.sendSelectedChipData(selectedChipText)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
        binding.recyclerView.adapter = adapter

        viewModel.isLoading.observe(viewLifecycleOwner
        ) { isLoading ->

            if (isLoading != null) {
                if (isLoading) {
                    // hide your progress bar
                    binding.progressBarDashboard.visibility = View.GONE
                    binding.horizontalScrollView.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                } else if(!isLoading) {
                    binding.progressBarDashboard.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
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
                    setSharedPrefenceValue(it.category)

                } else {
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //calling the view model function for api call
        viewModel.makeApiCallForListOfData("all")
        adapter.notifyDataSetChanged()
    }

    private fun setSharedPrefenceValue(category: String) {
        val sharedPreference =  activity!!.getSharedPreferences(sharedPreferenceFileName, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("selectedCategory",category)
        editor.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}