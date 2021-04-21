package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.retrofit.dbUtil.Status
import com.udacity.asteroidradar.retrofit.dbUtil.ViewModelFactory
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapters.MainAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.retrofit.ApiHelperImpl
import com.udacity.asteroidradar.retrofit.RetrofitBuilder
import com.udacity.asteroidradar.room.AppDatabase
import com.udacity.asteroidradar.room.DatabaseHelperImpl
import com.udacity.asteroidradar.room.entity.Asteroid
import com.udacity.asteroidradar.room.entity.DailyImage


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel
    var imgUrl: String? = null
    var dailyImage: DailyImage? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        setHasOptionsMenu(true)
        initViewModel()
        viewModel.fetchMainResponse()
        viewModel.fetchDailyImage()
        getSearchResult()
        setImage()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(AppDatabase.getInstance(requireContext()))
            )
        ).get(MainViewModel::class.java)
    }

    private fun initRecycleView(data: List<Asteroid>) {

        val mainAdapter = MainAdapter(data)
        binding.asteroidRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
            setHasFixedSize(true)
        }

        mainAdapter.onItemClick = {

            val action = MainFragmentDirections.actionShowDetail(it, dailyImage)
            findNavController().navigate(action)
        }


    }

    private fun getSearchResult() {
        viewModel.getMainList().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data?.let { list ->
                        Log.e("TAG", "getSearchResult: ${list.toString()}")

                        initRecycleView(list)
                    }
                    binding.progressBar.visibility = View.GONE
                }
                Status.EMPTY -> {

                    binding.progressBar.visibility = View.GONE


                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    // Handle Error
                }
            }
        })
    }

    private fun setImage() {
        viewModel.getDailyImage().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data?.let { img ->
                        dailyImage = img
                        imgUrl = img.url
                        Picasso.get().load(imgUrl).into(binding.activityMainImageOfTheDay)
                        binding.textView.text = img.title
                        binding.activityMainImageOfTheDay.contentDescription = img.title

                    }
                }
                Status.EMPTY -> {

                }
                Status.LOADING -> {
                }
                Status.ERROR -> {

                }
            }
        })
    }


}
