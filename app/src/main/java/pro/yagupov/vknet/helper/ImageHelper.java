package pro.yagupov.vknet.helper;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by Yagupov Ruslan on 16.09.2016.
 */
public class ImageHelper extends BaseHelper {

    private static final int CACHE_SIZE = 10 * 1024 * 1024;

    private static Picasso sPicasso;


    public static RequestCreator load(String pUrl) {

        if (sPicasso == null) sPicasso = buildPicasso();

        return sPicasso.load(pUrl);
    }

    private static Picasso buildPicasso() {
        Picasso.Builder builder = new Picasso.Builder(getContext());
        builder.downloader(new OkHttpDownloader(getContext(), CACHE_SIZE));
        return builder.build();
    }
}
