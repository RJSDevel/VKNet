package pro.yagupov.vknet.helper;

import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public class ToastHelper extends BaseHelper {

    public static void showToast(@StringRes int pResId) {
        Toast.makeText(getContext(), pResId, Toast.LENGTH_SHORT).show();
    }
}
