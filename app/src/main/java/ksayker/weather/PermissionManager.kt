package ksayker.weather

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions

object PermissionManager {
    fun requestLocationPermission(activity: FragmentActivity?, onPermissionGranted: (() -> Unit), onPermissionDeny: (() -> Unit)) {
        activity?.let {
            RxPermissions(activity).request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).subscribe {
                if (it) {
                    onPermissionGranted.invoke()
                } else {
                    RxPermissions(activity).shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ).subscribe {
                        if (!it) {
                            onPermissionGranted.invoke()
                        } else {
                            onPermissionDeny.invoke()
                        }
                    }
                }
            }
        }

    }
}