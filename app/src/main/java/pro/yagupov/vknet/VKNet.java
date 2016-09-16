package pro.yagupov.vknet;

import android.app.Application;
import android.content.Context;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import pro.yagupov.vknet.helper.BaseHelper;
import pro.yagupov.vknet.provider.DataProvider;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public class VKNet extends Application {

    VKAccessTokenTracker mAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        DataProvider.onCreate(getApplicationContext());
        BaseHelper.onInitialize(getApplicationContext());

        initVkSdk();
    }


    private void initVkSdk() {
        VKSdk.initialize(getApplicationContext());
        mAccessTokenTracker.startTracking();
    }
}
