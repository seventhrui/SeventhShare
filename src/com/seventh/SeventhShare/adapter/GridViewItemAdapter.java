package com.seventh.SeventhShare.adapter;

import java.io.InputStream;
import java.util.List;

import com.seventh.SeventhShare.R;
import com.seventh.SeventhShare.bean.GridViewItemBean;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewItemAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<GridViewItemBean> list;
	public GridViewItemAdapter(Context context, List<GridViewItemBean> list) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
	}
	@Override
	public int getCount() {
		return this.list != null ? this.list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_gridview, null);
			holder.iv1=(ImageView) convertView.findViewById(R.id.ItemImage);
			holder.iv2=(ImageView) convertView.findViewById(R.id.ItemSelected);
			holder.tv=(TextView) convertView.findViewById(R.id.ItemText);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		GridViewItemBean gvib=list.get(position);
		
		holder.iv1.setImageDrawable(context.getResources().getDrawable(gvib.getImageid()));
		if(gvib.getSelected())
			holder.iv2.setVisibility(View.VISIBLE);
		else
			holder.iv2.setVisibility(View.GONE);
		holder.tv.setText(gvib.getItemname());
		return convertView;
	}
	static class ViewHolder {
		ImageView iv1;
		ImageView iv2;
		TextView tv;
	}
}
