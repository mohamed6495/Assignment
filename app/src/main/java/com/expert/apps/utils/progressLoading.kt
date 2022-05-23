package com.expert.apps.utils

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD


object progressLoading {
    var kProgressHUD: KProgressHUD? = null
    fun CreateProgress(context: Context?) {

            kProgressHUD = KProgressHUD(context)

        kProgressHUD!!
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show()
    }

    fun HideProgress() {
        if (kProgressHUD == null) {
        } else {
            kProgressHUD!!.dismiss()
        }
    }


}