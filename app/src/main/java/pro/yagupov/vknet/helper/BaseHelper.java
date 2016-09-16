package pro.yagupov.vknet.helper;

import android.content.Context;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public class BaseHelper {

    private static Context sContext;

    public static void onInitialize(Context pContext) {
        sContext = pContext;
    }

    protected static Context getContext() {
        return sContext;
    }
}
