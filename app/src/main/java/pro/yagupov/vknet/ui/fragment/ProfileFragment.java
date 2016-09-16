package pro.yagupov.vknet.ui.fragment;

import android.app.AlertDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pro.yagupov.vknet.R;
import pro.yagupov.vknet.helper.ImageHelper;
import pro.yagupov.vknet.helper.NetworkHelper;
import pro.yagupov.vknet.helper.ToastHelper;
import pro.yagupov.vknet.provider.DataProvider;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public class ProfileFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int OWNER = 0;

    @BindView(R.id.avatar)
    ImageView ivAvatar;
    @BindView(R.id.name)
    TextView tvName;
    @BindView(R.id.progress)
    View pvProgress;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.error)
    ViewGroup vError;


    @Override
    protected int getLayoutId() {
        return R.layout.profile_fragment;
    }

    @Override
    protected void onAction() {
        getActivity().setTitle(R.string.profile);
        showToolbar();
        initRefreshLayout();
        tryLoadProfile(false);
    }

    private void initRefreshLayout() {
        swipeLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        tryLoadProfile(true);
    }

    private void showToolbar() {
        ButterKnife.findById(getActivity(), R.id.toolbar).setVisibility(View.VISIBLE);
    }


    private void tryLoadProfile(boolean pIsRefresh) {

        if (!pIsRefresh) pvProgress.setVisibility(View.VISIBLE);

        if (!NetworkHelper.isNetworkAvailable()) {

            ToastHelper.showToast(R.string.check_internet_connection);

            if (DataProvider.getInstance().isHasUserInfo()) tryLoadFromCache();
            else showRetryError();

            return;
        }

        loadProfile();
    }

    @SuppressWarnings("unchecked")
    private void loadProfile() {
        VKApi.users()
                .get(VKParameters.from(VKApiConst.FIELDS, VKApiUser.FIELD_PHOTO_400_ORIGIN))
                .executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {

                        hideRefresh();

                        try {
                            VKList<VKApiUserFull> users = (VKList<VKApiUserFull>) response.parsedModel;
                            prepareUser(users.get(OWNER));
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VKError error) {

                        hideRefresh();

                        if (NetworkHelper.isNetworkAvailable()) {
                            new AlertDialog
                                    .Builder(getActivity())
                                    .setMessage(error.errorMessage)
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show();
                        } else {
                            tryLoadFromCache();
                            ToastHelper.showToast(R.string.check_internet_connection);
                        }
                    }
                });
    }

    private void tryLoadFromCache() {
        fillViews();
    }

    @OnClick(R.id.retry)
    public void retry() {
        hideError();
        tryLoadProfile(false);
    }

    private void hideError() {
        vError.setVisibility(View.GONE);
    }

    private void showRetryError() {
        hideRefresh();
        vError.setVisibility(View.VISIBLE);
    }

    private void hideRefresh() {
        swipeLayout.setRefreshing(false);
    }

    private void prepareUser(VKApiUserFull pUser) {
        if (isUserUpdated(pUser)) {
            DataProvider.getInstance()
                    .setUserName(pUser.toString())
                    .setAvatar(pUser.photo_400_orig);
        }

        fillViews();
    }

    private boolean isUserUpdated(VKApiUserFull pUser) {
        String avatar = DataProvider.getInstance().getAvatar();
        String name = DataProvider.getInstance().getUserName();

        return !(avatar.equals(pUser.photo_400_orig) && name.equals(pUser.toString()));
    }

    private void fillViews() {
        ImageHelper.load(DataProvider.getInstance().getAvatar())
                .into(ivAvatar, new Callback() {
                    @Override
                    public void onSuccess() {
                        hideRefresh();
                        tvName.setText(DataProvider.getInstance().getUserName());
                        pvProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        hideRefresh();
                        pvProgress.setVisibility(View.GONE);
                        showRetryError();
                    }
                });
    }
}
