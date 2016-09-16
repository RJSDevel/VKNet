package pro.yagupov.vknet.ui.fragment;

import android.content.Intent;
import android.util.Log;

import com.vk.sdk.BuildConfig;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

import butterknife.OnClick;
import pro.yagupov.vknet.R;
import pro.yagupov.vknet.helper.NetworkHelper;
import pro.yagupov.vknet.helper.ToastHelper;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public class AuthFragment extends BaseFragment {

    private static final String TAG = "FP";

    @Override
    protected int getLayoutId() {
        return R.layout.auth_fragment;
    }

    @Override
    protected void onAction() {
        if (BuildConfig.DEBUG) printFingerprints();
    }

    private void printFingerprints() {
        String[] fingerprints = VKUtil.getCertificateFingerprint(getActivity(), getActivity().getPackageName());
        for (String fp : fingerprints) Log.d(TAG, fp);
    }

    @OnClick(R.id.login)
    public void login() {
        if (NetworkHelper.isNetworkAvailable()) VKSdk.login(this);
        else ToastHelper.showToast(R.string.check_internet_connection);
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        getActivity().onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                res.saveTokenToSharedPreferences(getActivity(), res.accessToken);

                showFragment(new ProfileFragment());
            }

            @Override
            public void onError(VKError error) {
                switch (error.errorCode) {
                    case VKError.VK_CANCELED:
                        if (NetworkHelper.isNetworkAvailable()) {
                            ToastHelper.showToast(R.string.user_has_canceled);
                        } else ToastHelper.showToast(R.string.check_internet_connection);
                        break;
                    case VKError.VK_REQUEST_HTTP_FAILED:
                        ToastHelper.showToast(R.string.connection_error);
                        break;
                    default:
                        ToastHelper.showToast(R.string.unknown_error);
                }
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
