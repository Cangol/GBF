package com.azhuoinfo.gbf.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Category;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;


public class CategoryAdapter extends BaseAdapter<Category> {
    private Context mContext;

    public CategoryAdapter(Context context) {
        super(context);
        this.mContext = context;
    }
    
    
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Category getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(),
                    R.layout.gridview_item_category, null);
            vh = new ViewHolder();
            vh.name = (TextView) convertView.findViewById(R.id.gridview_item_category_name);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Category item =getItem(position);
        vh.name.setText(item.getName());
        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
