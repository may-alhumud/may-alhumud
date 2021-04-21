package com.udacity.asteroidradar.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetailBinding.bind(view)
        val earthObj = args.selectedAsteroid
        val dailyImage = args.dailyImage

        binding.apply {
            Picasso.get().load(dailyImage?.url).into(activityMainImageOfTheDay)
            activityMainImageOfTheDay.contentDescription = dailyImage?.title

            closeApproachDate.text = earthObj.closeApproachDate
            closeApproachDate.contentDescription  = earthObj.closeApproachDate

            absoluteMagnitude.text =
                String.format(
                    requireActivity().getString(R.string.astronomical_unit_format),
                    earthObj.magnitude
                )

            estimatedDiameter.text =
                String.format(
                    requireActivity().getString(R.string.km_unit_format),
                    earthObj.estimatedDiameter
                )

            relativeVelocity.text =
                String.format(
                    requireActivity().getString(R.string.km_s_unit_format),
                    earthObj.kilometersPerSecond
                )

            distanceFromEarth.text =
                String.format(
                    requireActivity().getString(R.string.astronomical_unit_format),
                    earthObj.distanceFromEarth
                )

            helpButton.setOnClickListener {
                displayAstronomicalUnitExplanationDialog()
                helpButton.contentDescription = getString(R.string.astronomica_unit_explanation)
            }


        }

    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
