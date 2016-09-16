package pro.yagupov.vknet.ui.activity;

import com.vk.sdk.VKSdk;

import pro.yagupov.vknet.R;
import pro.yagupov.vknet.ui.fragment.AuthFragment;
import pro.yagupov.vknet.ui.fragment.ProfileFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onAction() {
        if (VKSdk.isLoggedIn()) showFragment(new ProfileFragment());
        else showFragment(new AuthFragment());
    }
}
