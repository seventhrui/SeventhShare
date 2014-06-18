package com.seventh.SeventhShare.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;

import com.seventh.SeventhShare.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Fragment_gv extends Fragment {
	private View rootView = null;
	private GridView gv_funs;
	private ArrayList<HashMap<String, Object>> lstImageItem = null;

	public Fragment_gv() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initView(inflater, container);
		return rootView;
	}

	private void initView(LayoutInflater i, ViewGroup c) {
		rootView = i.inflate(R.layout.fragment_gridview_page, c, false);
		gv_funs = (GridView) rootView.findViewById(R.id.gv_funs);
		SimpleAdapter sladapter = (SimpleAdapter) getGvAdapter();
		gv_funs.setAdapter(sladapter);
		gv_funs.setOnItemClickListener(new GridViewItemClickListener());
	}

	/**
	 * Get GridView Adapter
	 * 
	 * @return SimpleAdapter
	 */
	private SimpleAdapter getGvAdapter() {
		SimpleAdapter sladapter = null;
		sladapter = getGetSimpleAdapter(getActivity().getApplicationContext());
		return sladapter;
	}

	private SimpleAdapter getGetSimpleAdapter(Context context) {
		lstImageItem = new ArrayList<HashMap<String, Object>>();
		Integer[] mIds = { R.drawable.icon_share_qqzone,
				R.drawable.icon_share_qqweibo,
				R.drawable.icon_share_weichatquan, R.drawable.icon_share_sina };
		String[] mNames = { "QQ空间", "腾讯微博", "朋友圈", "新浪微博" };
		for (int i = 0; i < mIds.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", mIds[i]);// 添加图像资源的ID
			map.put("ItemText", mNames[i]);// 按序号做ItemText
			map.put("Itemid", i);// id
			lstImageItem.add(map);
		}
		SimpleAdapter saImageItems = new SimpleAdapter(context, lstImageItem,
				R.layout.item_gridview,
				new String[] { "ItemImage", "ItemText" }, new int[] {
						R.id.ItemImage, R.id.ItemText });
		return saImageItems;
	}

	class GridViewItemClickListener implements GridView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			String imgName = (String) item.get("ItemText");
			Log.v("----aaa--->>", imgName);
			int itemid = Integer.parseInt(item.get("Itemid").toString());
			getWeibo(itemid);
		}
	}

	/**
	 * 获得授权
	 */
	private AbstractWeibo getWeibo(int vid) {
		String name = null;
		switch (vid) {
		// 进入QQ空间的授权页面
		case 0:
			name = QZone.NAME;
			break;
		// 进入腾讯微博的授权页面
		case 1:
			name = TencentWeibo.NAME;
			break;
		// 进入微信朋友圈的授权页面
		case 2:
			name = QZone.NAME;
			break;
		// 进入新浪微博的授权页面
		case 3:
			name = SinaWeibo.NAME;
			break;
		}

		if (name != null) {
			return AbstractWeibo.getWeibo(this, name);
		}
		return null;
	}
}
