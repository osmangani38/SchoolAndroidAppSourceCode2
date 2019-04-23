package com.sap.school;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

public class ApplicationContextProvider extends MultiDexApplication {
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        /*try {
            ProviderInstaller.installIfNeeded(sContext);
        } catch (GooglePlayServicesRepairableException e) {
            // Prompt the user to install/update/enable Google Play services.
            GoogleApiAvailability.getInstance()
                    .showErrorNotification(this, e.getConnectionStatusCode());
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates a non-recoverable error: let the user know.
        }*/
    }
    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }
}