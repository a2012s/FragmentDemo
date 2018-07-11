package com.example.lx.fragmentdemo;
/*************************************************
 *
 *
 * 类似这种界面逻辑用ViewPager+fragment处理较好，此demo只供理解fragment
 *
 *
 * **********************************************************/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment = new Fragment();

    private int iNow = 0;

    private Fragment1 first = new Fragment1();
    private Fragment2 second = new Fragment2();
    private Fragment3 third = new Fragment3();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.v("logcat", "item.getItemId()=" + item.getItemId());
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(first, 1).commit();
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(second, 2).commit();
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(third, 3).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        switchFragment(first).setCustomAnimations(
//                R.animator.fragment_slide_right_in, R.animator.fragment_slide_left_out,
//                R.animator.fragment_slide_left_in, R.animator.fragment_slide_right_out).commit();

        switchFragment(first, 0).commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Fragment优化
    private FragmentTransaction switchFragment(Fragment targetFragment, int i) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (i == iNow) {
            //doNothing
            Log.e("logcat", "i==i==" + i);
//            transaction.setCustomAnimations(0, 0, 0, 0);
        } else if (i > iNow) {
            Log.e("logcat", "i > ii;i==" + i);

            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out
            );
        } else {
            Log.e("logcat", "i < ii;i==" + i);
            transaction.setCustomAnimations(
                    R.anim.slide_left_in,
                    R.anim.slide_right_out,
                    R.anim.slide_right_in,
                    R.anim.slide_left_out

            );
        }

        iNow = i;


        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragment, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction.hide(currentFragment).show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }
}
