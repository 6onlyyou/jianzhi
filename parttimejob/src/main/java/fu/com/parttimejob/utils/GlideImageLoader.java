package fu.com.parttimejob.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import fu.com.parttimejob.App;

/**
 * Created by Administrator on 2017/7/12.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(App.getInstance()).load(path).into(imageView);
    }

}
