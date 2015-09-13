package com.azhuoinfo.gbf.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azhuoinfo.gbf.R;
import com.azhuoinfo.gbf.model.Tag;
import com.azhuoinfo.gbf.view.listview.BaseAdapter;

public class TagAdapter extends BaseAdapter<Tag> {
	public TagAdapter(Context context) {
		super(context);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		final Tag item=getItem(position);
		if(null!=convertView){
			holder=(ViewHolder)convertView.getTag();
		}else{
			convertView = this.mInflater.inflate(R.layout.gridview_item_tag, parent, false);
			holder=new ViewHolder();
			holder.name=(TextView) convertView.findViewById(R.id.gridview_item_tag_name);
			convertView.setTag(holder);
		}
		holder.name.setText(item.getName());
		
		return convertView;
	}
	class ViewHolder {
		TextView name;
	}
}
