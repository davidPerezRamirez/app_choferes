package com.example.app_choferes.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.app_choferes.R;
import com.example.app_choferes.contracts.MainActivityContract;
import com.example.app_choferes.listeners.OnBackPressedListener;
import com.example.app_choferes.presenter.MainPresenterImp;
import com.example.app_choferes.ui.fragments.LoginFragment;
import com.example.app_choferes.utils.DialogFactory;
import com.example.app_choferes.utils.FeedbackMessageDisplay;
import com.example.app_choferes.utils.SwitcherFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter presenter;
    private SwitcherFragment switcherFragment;
    private FeedbackMessageDisplay feedbackMessageDisplay;
    private OnBackPressedListener onBackPressedListener;
    private Unbinder binder;

    @BindView(R.id.coordinatorLayout)
    View coordinatorLayoutView;
    @BindView(R.id.progress_bar_container)
    FrameLayout progressBarContainer;

    public SwitcherFragment getSwitcherFragment() {
        return switcherFragment;
    }

    public FeedbackMessageDisplay getFeedbackMessageDisplay() {
        return feedbackMessageDisplay;
    }

    private MainActivityContract.Presenter createPresenter() {
        return new MainPresenterImp(this);
    }

    public MainActivityContract.Presenter getPresenter() {
        return presenter;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void navigateToLogin() {
        getSwitcherFragment().switchFragment(LoginFragment.newInstance(), false, LoginFragment.class.getSimpleName());
    }

    public void reloadActualFragment(List<Fragment> fragments) {
        Fragment fragmentActive = getFragmentActive(fragments);
        if (fragmentActive != null) {
            feedbackMessageDisplay.dimissDisplay();
            switcherFragment.reloadFragment(fragmentActive);
        }
    }

    private Fragment getFragmentActive(List<Fragment> fragments) {
        Fragment fragmentActive = null;

        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible()) {
                    fragmentActive = fragment;
                }
            }
        }

        return fragmentActive;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binder = ButterKnife.bind(this);
        this.presenter = this.createPresenter();
        this.switcherFragment = new SwitcherFragment(getSupportFragmentManager());
        this.feedbackMessageDisplay = new FeedbackMessageDisplay(this);

        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
        };
        int requestCode = 200;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            requestPermissions(permissions, requestCode);
        }

        navigateToLogin();
    }

    @Override
    public void onBackPressed() {

        Log.i("MainActivity", "Se presionó back");

        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        Boolean isLastActivity = (backStackEntryCount == 0);

        if (isLastActivity) {
            DialogFactory.showExitDialog(MainActivity.this);
        } else {
            boolean result = true;
            if (onBackPressedListener != null) {
                result = onBackPressedListener.doBack();
            }

            if (result && backStackEntryCount > 0) {
                super.onBackPressed();
            }
        }
    }

    public void showTemporalMessage(String msg) {
        this.feedbackMessageDisplay.showTemporalMessage(msg);
    }

    @Override
    public View getCoordinatorLayoutView() {
        return coordinatorLayoutView;
    }

    @Override
    public FrameLayout getProgressBarContainer() {
        return progressBarContainer;
    }

    @Override
    protected void onDestroy() {
        if (binder != null)
            binder.unbind();
        super.onDestroy();
    }
}
