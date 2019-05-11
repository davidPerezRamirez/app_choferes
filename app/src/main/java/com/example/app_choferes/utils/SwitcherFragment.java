package com.example.app_choferes.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.app_choferes.R;

public class SwitcherFragment {
    private static final String CONTEXT_TAG = SwitcherFragment.class.getSimpleName() + " ";
    private FragmentManager fragmentManager;

    public SwitcherFragment(FragmentManager currentFragmentManager) {
        this.fragmentManager = currentFragmentManager;
    }

    public void switchFragment(Fragment fragment, Boolean addToBackStack, String tag) {

        if (fragment != null) {

            Log.i("App_Choferes", CONTEXT_TAG + "Pantalla: " + fragment.getClass().getSimpleName());

            //Google Analytics
            //RestoApplication.getInstance().trackScreenView(screenName);
        }
        switchFragment(fragment, R.id.container, addToBackStack, tag);
    }

    public void switchFragment(Fragment fragment, int containerViewId, Boolean addToBackStack,
                               String tag) {
        if (fragmentManager != null && fragment != null) {

            if (addToBackStack) {
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(containerViewId, fragment, tag)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(containerViewId, fragment, tag)
                        .commit();
            }
        }
    }

    public void switchFragmentBack() {
        this.fragmentManager.popBackStack();
    }

    public void removeCurrentAndSwitchFragment(Fragment fragment) {
        removeFragment(fragment);
        switchFragmentBack();
    }

    public void removeFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .remove(fragment)
                .commit();
    }

    public void reloadFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }

}
