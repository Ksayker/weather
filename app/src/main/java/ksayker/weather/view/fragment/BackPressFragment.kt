package ksayker.weather.view.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

open class BackPressFragment : Fragment() {

    protected fun initToolbar(toolbar: Toolbar) {
        val activity = activity

        if (activity != null) {
            activity as AppCompatActivity

            activity.setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { activity.onBackPressed() }
        }
    }
}