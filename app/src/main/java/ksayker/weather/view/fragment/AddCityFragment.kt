package ksayker.weather.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ksayker.weather.R
import ksayker.weather.databinding.FragmentAddCityBinding
import ksayker.weather.model.entity.City
import ksayker.weather.view.dialog.MessageDialogFragment

class AddCityFragment : BackPressFragment(), AddCityViewModel.AddCityViewModelCallback {
    private lateinit var viewModel: AddCityViewModel
    private lateinit var binding: FragmentAddCityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_city, container, false)

        initToolbar(binding.toolbar)
        setupBindings(binding, savedInstanceState)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun setupBindings(binding: FragmentAddCityBinding, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(AddCityViewModel::class.java)

        viewModel.init()
        viewModel.callback = this

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

        binding.viewModel = viewModel
    }

    override fun addCity(city: City) {
        activity?.onBackPressed()
        (activity as? OnAddCityListener)?.addCity(city)
    }

    override fun cancel() {
        activity?.onBackPressed()
    }

    interface OnAddCityListener {
        fun addCity(city: City)
    }

    companion object {
        fun newInstance() : AddCityFragment {
            val bundle = Bundle()
            val fragment = AddCityFragment()

            fragment.arguments = bundle

            return fragment
        }
    }
}