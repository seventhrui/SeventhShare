package com.seventh.SeventhShare.fragment;

import java.util.ArrayList;
import java.util.List;

import com.seventh.SeventhShare.R;
import com.seventh.SeventhShare.adapter.ListViewItemAdapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.SwipeRefreshLayout;

public class Fragment_lv extends Fragment implements OnClickListener{
	private View rootView = null;
	private ListView lv_funs;
	private SwipeRefreshLayout swipeLayout;
	private EditText txtNew;
	
	private String content="";
	
	private Button mBtnSend;//
	private TextView mBtnRcd;//
	private RelativeLayout mBottom;//
	private ImageView chatting_mode_btn;//
	private boolean btn_vocie = false;//
	
	private List<String> lsitem;
	public Fragment_lv() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initView(inflater, container);
		return rootView;
	}
	private void initView(LayoutInflater i, ViewGroup c){
		rootView = i.inflate(R.layout.fragment_listview_page, c, false);
		lv_funs = (ListView) rootView.findViewById(R.id.lv_funs);
		ListViewItemAdapter lvadapter = getListViewAdapter(getActivity().getApplicationContext());
		lv_funs.setAdapter(lvadapter);
		lv_funs.setOnItemClickListener(new ListViewItemClickListener());
		
		// 下拉刷新初始化设置
		swipeLayout = (SwipeRefreshLayout) rootView
				.findViewById(R.id.swipe_container);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		swipeLayout_setOnRefreshListener(); // 下拉刷新
		
		txtNew = (EditText) rootView.findViewById(R.id.txtNew);
		txtNew_setOnKeyListener(); // 提交新subject
		
		mBtnSend = (Button) rootView.findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		
		mBtnRcd = (TextView) rootView.findViewById(R.id.btn_rcd);
		mBtnRcd.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				//按下语音录制按钮时返回false执行父类OnTouch
				Toast.makeText(getActivity(), "按住了", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		mBottom = (RelativeLayout) rootView.findViewById(R.id.btn_bottom);
		chatting_mode_btn = (ImageView) rootView.findViewById(R.id.ivPopUp);
		chatting_mode_btn.setOnClickListener(this);
	}
	
	/**
	 * get ListView Adapter
	 * @param context
	 * @return ListViewItemAdapter
	 */
	private ListViewItemAdapter getListViewAdapter(Context context){
		lsitem=new ArrayList<String>();
		String[] strs={"二一", "二二", "二三", "二四", "二五"};
		for(int i=0;i<strs.length;i++){
			lsitem.add(strs[i]);
		}
		ListViewItemAdapter lvia=new ListViewItemAdapter(context,lsitem);
		return lvia;
		
	}
	/**
	 * ListView OnItemClickListener
	 *
	 */
	class ListViewItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			TextView tv=(TextView) arg1.findViewById(R.id.tv_lv_ItemText);
			String s = tv.getText().toString();
			Log.v("----a---->>", s);
			Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * ListView Set Pull-refresh Listener
	 */
	private void swipeLayout_setOnRefreshListener() {
		swipeLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					// 下拉刷新
					@Override
					public void onRefresh() {
						new Handler().postDelayed(new Runnable() {
							public void run() {
								
								swipeLayout.setRefreshing(false);
							}
						}, 1000);
					}
				});
	}
	/**
	 * EditText set OnKeyListener
	 */
	private void txtNew_setOnKeyListener() {
		txtNew.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager) v
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					if (imm.isActive()) {
						content = txtNew.getText().toString().trim();
						if (content.length() > 1) {
							lsitem.add(content);
							txtNew.setText("");
						}
					}
					return true;
				}
				return false;
			}
		});
	}
	/**
	 * Button Set Onclick
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			content = txtNew.getText().toString().trim();
			lsitem.add(content);
			txtNew.setText("");
			break;
		case R.id.ivPopUp:
			if (btn_vocie) {
				mBtnRcd.setVisibility(View.GONE);
				mBottom.setVisibility(View.VISIBLE);
				btn_vocie = false;
				chatting_mode_btn.setImageResource(R.drawable.chatting_setmode_msg_btn);

			} else {
				mBtnRcd.setVisibility(View.VISIBLE);
				mBottom.setVisibility(View.GONE);
				chatting_mode_btn.setImageResource(R.drawable.chatting_setmode_voice_btn);
				btn_vocie = true;
			}
			break;
		}
	}
}
