package com.cursomovil.earthquakes.listeners;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by cursomovil on 10/04/15.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {

    private Fragment mFragment;
    private final Activity mActivity;
    private final int mFrameContainer;
    private final Class<T> mFragmentClass;


    public TabListener(Activity activity, int frameContainer, Class<T> fragmentClass) {
        mActivity = activity;
        mFrameContainer = frameContainer;
        mFragmentClass = fragmentClass;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            String	fragmentName	=	mFragmentClass.getName();
            mFragment = Fragment.instantiate(mActivity, fragmentName);

            // This should be ft.add(), but causes fragments overlapping;
            ft.replace(mFrameContainer, mFragment, fragmentName);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if	(mFragment	!=	null)
            ft.attach(mFragment);
    }

}
