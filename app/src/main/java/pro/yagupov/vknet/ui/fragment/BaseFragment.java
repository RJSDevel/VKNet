package pro.yagupov.vknet.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pro.yagupov.vknet.R;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onAction();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
