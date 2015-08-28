package com.azhuoinfo.gbf.fragment.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Banner;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class BannerAdapter extends BaseAdapter<Banner> {
    private Context mContext;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;

    public BannerAdapter(Context context) {
        super(context);
        this.mContext = context;
        mImageLoader = ImageLoader.getInstance();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_photo_default)
                .showImageForEmptyUri(R.drawable.ic_photo_default)
                .showImageOnFail(R.drawable.ic_photo_default)
                .build();
    }
    public void setImageLayoutParams(ImageView imageView, String imagesUrl) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        Gallery.LayoutParams layoutParams = new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery. LayoutParams.MATCH_PARENT);
        layoutParams.width = displayMetrics.widthPixels;
        layoutParams.height = (int) (layoutParams.width*3.0f/8);
        imageView.setLayoutParams(layoutParams);
        mImageLoader.displayImage(imagesUrl, imageView);
    }
    @Override
    public int getCount() {
        int maxValue = Integer.MAX_VALUE;
        return maxValue;
    }

    @Override
    public Banner getItem(int position) {
        return mItems.get(position%mItems.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(),
                    R.layout.gallery_item_banner, null);
            vh = new ViewHolder();
            vh.image = (ImageView) convertView.findViewById(R.id.gallery_item_banner_image);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Banner item =getItem(position);
        setImageLayoutParams(vh.image, item.getImage());
        return convertView;
    }

    class ViewHolder {
        ImageView image;
    }
}
