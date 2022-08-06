package com.satya.newsglobal.ui.fragment.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.satya.newsglobal.R
import com.satya.newsglobal.databinding.FragmentCategoryBinding
import com.satya.newsglobal.ui.adapter.CategoryAdapter
import com.satya.newsglobal.ui.adapter.NewsAdapter
import com.satya.newsglobal.ui.constants.Category
import com.satya.newsglobal.ui.constants.CategoryArray
import kotlin.math.log

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var category: Array<Category>
    private val adapter = CategoryAdapter()
    private lateinit var navBar: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val notificationsViewModel =
//            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.categoryRecyclerView.adapter  = adapter


        category = CategoryArray.categories
        adapter.setCategories(category)
        adapter.notifyDataSetChanged()

        return root
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