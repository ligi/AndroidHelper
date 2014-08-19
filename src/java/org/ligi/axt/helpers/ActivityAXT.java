package org.ligi.axt.helpers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.view.View;

public class ActivityAXT extends ContextAXT {

    private final Activity activity;

    public ActivityAXT(Activity activity) {
        super (activity);
        this.activity =activity;
    }

    public <T extends View> T findById(int id) {
        return (T) activity.findViewById(id);
    }

    /**
     * dynamically disable rotation
     * to be used in onCreate
     * slightly modified from http://stackoverflow.com/a/8765901/322642
     */
    public void disableRotation() {
        lockOrientation((activity.getResources().getConfiguration().orientation));
    }

    public void lockOrientation(final int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO){
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                    if(rotation == android.view.Surface.ROTATION_90|| rotation == android.view.Surface.ROTATION_180){
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    } else {
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
                break;

            case Configuration.ORIENTATION_LANDSCAPE:
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO){
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                    if(rotation == android.view.Surface.ROTATION_0 || rotation == android.view.Surface.ROTATION_90){
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    }
                }
                break;
        }//switch
    }

    /**
     * dynamically enable rotation
     * counterpart to enableRotation
     * slightly modified from http://stackoverflow.com/a/8765901/322642
     */
    public void enableRotation() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    public void rethrowIntentExcludingSelf() {
        final ComponentName component = new ComponentName(activity, activity.getClass());
        activity.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        activity.startActivity(activity.getIntent());
        activity.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

}
