package com.aviraxp.messagingnoquickreply;

import android.content.res.XModuleResources;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

public class HookMain implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
    private static String MODULE_PATH = null;

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    @Override
    public void handleInitPackageResources(
            XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.messaging")) {
            return;
        }

        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        resparam.res.setReplacement("com.android.messaging", "array",
                "notification_reply_choices", modRes.getStringArray(
                        R.array.notification_reply_choices));
    }
}