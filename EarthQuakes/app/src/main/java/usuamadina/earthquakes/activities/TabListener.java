package usuamadina.earthquakes.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by cursomovil on 10/04/15.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {

    private Fragment fragment;
    private Activity activity;
    private Class fragmentClass;
    private int fragmentContainer;

    public TabListener(MainActivity mainActivity, int fLayout, Class<T> fragmentClass) {
        this.activity = mainActivity;
        this.fragmentContainer = fLayout;
        this.fragmentClass = fragmentClass;


    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        if (fragment == null) {
            String fragmentName = fragmentClass.getName();
            fragment = Fragment.instantiate(activity, fragmentName);
            ft.add(fragmentContainer, fragment, fragmentName);

        } else {
            ft.attach(fragment);
        }


    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment != null) {
            ft.detach(fragment);
        }

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {


        if (fragment!= null){

            ft.attach(fragment);

        }



    }
}
