package com.azhuoinfo.gbf.fragment.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Brand;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class BrandAdapter extends BaseAdapter<Brand> {
    private Context mContext;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;

    public BrandAdapter(Context context) {
        super(context);
        this.mContext = context;
        mImageLoader = ImageLoader.getInstance();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_photo_default)
                .showImageForEmptyUri(R.drawable.ic_photo_default)
                .showImageOnFail(R.drawable.ic_photo_default)
                .build();
    }
    
    public void setImageLayoutParams(ImageView imageView) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout. LayoutParams.MATCH_PARENT);
        layoutParams.width = (int) (displayMetrics.widthPixels-displayMetrics.density*12*4)/3;
        layoutParams.height = layoutParams.width;
        imageView.setLayoutParams(layoutParams);
    }
    
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Brand getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(),
                    R.layout.gridview_item_brand, null);
            vh = new ViewHolder();
            vh.image = (ImageView) convertView.findViewById(R.id.gridview_item_image);
            setImageLayoutParams(vh.image);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Brand item =getItem(position);
        mImageLoader.displayImage(item.getLogo(), vh.image);
        return convertView;
    }

    class ViewHolder {
        ImageView image;
    }
}
