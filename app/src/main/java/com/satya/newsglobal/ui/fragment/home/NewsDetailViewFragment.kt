package com.satya.newsglobal.ui.fragment.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.satya.newsglobal.R
import com.satya.newsglobal.databinding.FragmentNewsDetailViewBinding
import com.squareup.picasso.Picasso
import java.util.*


class NewsDetailViewFragment : Fragment() {
    private var _binding: FragmentNewsDetailViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var navBar: BottomNavigationView
    private var title: String = ""
    private var author:  String = ""
    var content:  String = ""
    private var readMoreUrl:  String = ""
    private var shareUrl:  String = ""
    private var imageUrl:  String = ""
    private var time: String = ""
    private var date: String = ""

    private var sharedPreferenceFileName = "NewsGlobalData"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailViewBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val root: View = binding.root

        //hiding the bottom navigation
        navBar= activity!!.findViewById(R.id.nav_view)
        navBar.visibility = View.GONE

        title = arguments?.getString("title").toString()
        imageUrl = arguments?.getString("imageUrl").toString()
        author = arguments?.getString("author").toString()
        content = arguments?.getString("content").toString()
        readMoreUrl = arguments?.getString("readMoreUrl").toString()
        shareUrl = arguments?.getString("shareUrl").toString()
        time = arguments?.getString("time").toString()
        date = arguments?.getString("date").toString()


        binding.detailTitle.text = title
        binding.authorName.text = author
        binding.content.text = content
        binding.authorName.text = author
        binding.date.text = date
        binding.time.text = time
        Picasso.get()
            .load(imageUrl)
            .noFade()
            .into(binding.newsDetailsImage)

        binding.share.setOnClickListener {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, shareUrl)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        binding.readMore.setOnClickListener {
            val uri: Uri = Uri.parse(readMoreUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences(sharedPreferenceFileName, Context.MODE_PRIVATE)
        val category = sharedPreferences.getString("selectedCategory","category")
        binding.categoryName.text = category?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        backButton()

        return root
    }

    //custom back button code
    private fun backButton() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
            //showing bottom navigation
            navBar.visibility = View.VISIBLE

        }
    }
}