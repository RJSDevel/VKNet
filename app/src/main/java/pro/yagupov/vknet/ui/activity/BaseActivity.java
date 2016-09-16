package pro.yagupov.vknet.ui.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pro.yagupov.vknet.R;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        mUnbinder = ButterKnife.bind(this);

        addToolbar();

        onAction();
    }

    protected void addToolbar() {
        if (mToolbar != null) setSupportActionBar(mToolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    protected void showFragment(Fragment pFragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, pFragment)
                .commit();
    }


    protected abstract int getLayoutId();

    protected abstract void onAction();
}
