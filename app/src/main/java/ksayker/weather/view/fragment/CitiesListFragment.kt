package ksayker.weather.view.fragment

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ksayker.weather.LocationManager
import ksayker.weather.PermissionManager
import ksayker.weather.R
import ksayker.weather.databinding.FragmentCitiesListBinding
import ksayker.weather.model.entity.City
import ksayker.weather.view.NavigationView
import ksayker.weather.view.dialog.MessageDialogFragment
import ksayker.weather.view.helper.MessageFactory

class CitiesListFragment : Fragment() {
    private lateinit var viewModel: CitiesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCitiesListBinding>(inflater, R.layout.fragment_cities_list, container, false)

        initView(binding)
        setupBindings(binding)
        setupListClick()
        requestLocationPermissions()

        return binding.root
    }

    private fun requestLocationPermissions() {
        Handler().post {
            PermissionManager.requestLocationPermission(
                activity,
                {
                    viewModel.showCurrentLocation(LocationManager.instance?.lastLocation)
                },
                {
                    val context = context
                    if (context != null) {
                        viewModel.showMessage(context.getString(R.string.listCities_enableLocationMessage))
                    }
                })
        }
    }

    private fun initView(binding: FragmentCitiesListBinding) {
        val activity = activity
        if (activity != null) {
            activity as AppCompatActivity

            activity.setSupportActionBar(binding.toolbar)
            binding.toolbar.setNavigationOnClickListener { activity.onBackPressed() }
        }
    }

    private fun setupListClick() {
        viewModel.getClickCityObserver().observe(this, Observer {
            (activity as? NavigationView)?.navigateToCity(it)
        })
        viewModel.getClickAddCityObserver().observe(this, Observer {
            (activity as? NavigationView)?.navigateToAddCity()
        })
        viewModel.getMessageObserver().observe(this, Observer {
            val fm = activity?.supportFragmentManager
            if (!TextUtils.isEmpty(it) && fm != null) {
                MessageDialogFragment.newInstance(it, object : MessageDialogFragment.ConfirmationListener {
                    override fun confirmButtonClicked() {
                        viewModel.restoreMessage()
                    }

                    override fun onDismiss() {
                        viewModel.restoreMessage()
                    }
                })
                    .show(fm, null)
            }
        })
    }

    private fun setupBindings(binding: FragmentCitiesListBinding) {
        context?.let {
            viewModel = ViewModelProviders.of(this).get(CitiesListViewModel::class.java)

            viewModel.init(MessageFactory(it))

            binding.viewModel = viewModel
        }
    }

    fun addCity(city: City) {
        viewModel.addCity(city)
    }

    companion object {
        const val TAG = "CitiesListFragment"

        fun newInstance(): Fragment {
            val bundle = Bundle()
            val fragment = CitiesListFragment()

            fragment.arguments = bundle

            return fragment
        }
    }
}