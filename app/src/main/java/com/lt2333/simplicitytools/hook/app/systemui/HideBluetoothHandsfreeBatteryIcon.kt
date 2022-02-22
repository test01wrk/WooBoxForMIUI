package com.lt2333.simplicitytools.hook.app.systemui

import android.content.Intent
import com.lt2333.simplicitytools.util.XSPUtils
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HideBluetoothHandsfreeBatteryIcon : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val classIfExists = XposedHelpers.findClassIfExists(
            "com.android.systemui.statusbar.phone.MiuiPhoneStatusBarPolicy",
            lpparam.classLoader
        )
        XposedHelpers.findAndHookMethod(
            classIfExists,
            "updateBluetoothHandsfreeBattery",
            Intent::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    if (XSPUtils.getBoolean("hide_bluetooth_battery_icon", false)) {
                        param.result = null
                    }
                }
            })
    }
}