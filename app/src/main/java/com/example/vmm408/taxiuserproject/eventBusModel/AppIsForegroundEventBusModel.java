package com.example.vmm408.taxiuserproject.eventBusModel;

import com.example.vmm408.taxiuserproject.models.AppForeground;

public class AppIsForegroundEventBusModel {
    private AppForeground appForeground;

    public AppIsForegroundEventBusModel(boolean isBackground) {
        this.appForeground = new AppForeground(isBackground);
    }

    public void setAppForeground(AppForeground appForeground) {
        this.appForeground = appForeground;
    }

    public AppForeground getAppForeground() {
        return appForeground;
    }


//    private Boolean appForeground;
//
//    public Boolean getAppForeground() {
//        return appForeground;
//    }
//
//    public void setAppForeground(Boolean appForeground) {
//        this.appForeground = appForeground;
//    }
}
