package com.seventh.SeventhShare.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.AbstractWeibo;
import cn.sharesdk.framework.WeiboActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;

import com.seventh.SeventhShare.R;
import com.seventh.SeventhShare.adapter.GridViewItemAdapter;
import com.seventh.SeventhShare.bean.GridViewItemBean;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_gv extends Fragment {
	private Context context;
	private View rootView = null;
	private GridView gv_funs;
	private static Integer[] mIds = { R.drawable.icon_share_qqzone,R.drawable.icon_share_qqweibo,
			R.drawable.icon_share_weichatquan, R.drawable.icon_share_sina };
	private String[] mNames = { "QQ�ռ�", "��Ѷ΢��", "����Ȧ", "����΢��" };
	private static Boolean[] mSelect = {false,false,false,false};
	
	private static List<GridViewItemBean> gviblist;
	private GridViewItemAdapter gviadapter;
	
	public Fragment_gv(Context c) {
		this.context=c;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initData();
		initView(inflater, container);
		return rootView;
	}

	private void initView(LayoutInflater i, ViewGroup c) {
		rootView = i.inflate(R.layout.fragment_gridview_page, c, false);
		gv_funs = (GridView) rootView.findViewById(R.id.gv_funs);
		gviadapter=getGridViewAdapter(context);
		Log.v("---context--->", context+"");
		gv_funs.setAdapter(gviadapter);
		gv_funs.setOnItemClickListener(new GridViewItemClickListener());
	}

	private void initData() {
		gviblist=new ArrayList<GridViewItemBean>();
		// ��ȡƽ̨�б�
		AbstractWeibo.initSDK(context);
		AbstractWeibo[] weibos = AbstractWeibo.getWeiboList(context);
		
		for(AbstractWeibo aw : weibos)
			getWeiboName(aw);
		for(Boolean b:mSelect){
			Log.v("----selected1--->", b+"");
		}
		
		for (int i = 0; i < weibos.length; i++) {
			if (!weibos[i].isValid()) {
				continue;
			}

			// �õ���Ȩ�û����û�����
			String userName = weibos[i].getDb().get("nickname");
			if (userName == null || userName.length() <= 0 || "null".equals(userName)) {
				// ���ƽ̨�Ѿ���Ȩȴû���õ��ʺ����ƣ����Զ���ȡ�û����ϣ��Ի�ȡ����
				userName = getWeiboName(weibos[i]);
				// ���ƽ̨�¼�����
				weibos[i].setWeiboActionListener(new weiboListener());
				// ��ʾ�û����ϣ�null��ʾ��ʾ�Լ�������
				weibos[i].showUser(null);
				Log.v("---name--->", userName);
			}
			
		}
		
		for(int i=0;i<mIds.length;i++){
			GridViewItemBean gvib=new GridViewItemBean();
			gvib.setGvitemid(i);
			gvib.setImageid(mIds[i]);
			gvib.setItemname(mNames[i]);
			gvib.setSelected(mSelect[i]);
			gviblist.add(gvib);
		}
	}

	/**
	 * Get GridView Adapter
	 * 
	 * @return SimpleAdapter
	 */
	private GridViewItemAdapter getGridViewAdapter(Context context) {
		GridViewItemAdapter gvia=new GridViewItemAdapter(context,gviblist);
		return gvia;
	}

	class GridViewItemClickListener implements GridView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_id=(TextView) arg1.findViewById(R.id.ItemText);
			String messageid = tv_id.getText().toString();
			Toast.makeText(context, messageid+","+arg2, Toast.LENGTH_SHORT).show();
			
			AbstractWeibo weibo = getWeibo(arg2);
			if (weibo == null) {
			}
			if (weibo.isValid()) {
				weibo.removeAccount();
			}
			weibo.setWeiboActionListener(new weiboListener());
			weibo.showUser(null);
		}
	}

	private String getWeiboName(AbstractWeibo weibo) {
		if (weibo == null) {
			return null;
		}

		String name = weibo.getName();
		if (name == null) {
			return null;
		}

		int res = 0;
		if (QZone.NAME.equals(name)) {
			res = R.string.share_qqzone;
			//gviblist.get(0).setSelected(true);
		}
		else if (TencentWeibo.NAME.equals(name)) {
			res = R.string.share_tcweibo;
			//gviblist.get(1).setSelected(true);
		} 
		else if (SinaWeibo.NAME.equals(name)) {
			res = R.string.share_sina;
			//gviblist.get(3).setSelected(true);
		} 

		if (res == 0) {
			return name;
		}
		return this.getResources().getString(res);
	}

	/**
	 * �����Ȩ
	 */
	private AbstractWeibo getWeibo(int vid) {
		String name = null;
		switch (vid) {
		// ����QQ�ռ����Ȩҳ��
		case 0:
			name = QZone.NAME;
			break;
		// ������Ѷ΢������Ȩҳ��
		case 1:
			name = TencentWeibo.NAME;
			break;
		// ����΢������Ȧ����Ȩҳ��
		case 2:
			name = QZone.NAME;
			break;
		// ��������΢������Ȩҳ��
		case 3:
			name = SinaWeibo.NAME;
			break;
		}

		if (name != null) {
			Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
			return AbstractWeibo.getWeibo(context, name);
		}
		return null;
	}
	class weiboListener implements WeiboActionListener {
		/**
		 * ��Ȩ�ɹ��Ļص� weibo - �ص���ƽ̨ action - ���������� res - ���������ͨ��res����
		 */
		@Override
		public void onComplete(AbstractWeibo weibo, int action,
				HashMap<String, Object> arg2) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.arg1 = 1;
			msg.arg2 = action;
			msg.obj = weibo;
			handler.sendMessage(msg);
		}

		/**
		 * ��Ȩʧ�ܵĻص�
		 */
		@Override
		public void onError(AbstractWeibo weibo, int action, Throwable t) {
			// TODO Auto-generated method stub
			t.printStackTrace();
			Message msg = new Message();
			msg.arg1 = 2;
			msg.arg2 = action;
			msg.obj = weibo;
			handler.sendMessage(msg);
		}

		/**
		 * ȡ����Ȩ�Ļص�
		 */
		@Override
		public void onCancel(AbstractWeibo weibo, int action) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.arg1 = 3;
			msg.arg2 = action;
			msg.obj = weibo;
			handler.sendMessage(msg);
		}
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			AbstractWeibo weibo = (AbstractWeibo) msg.obj;
			String text = "";
			switch (msg.arg1) {
			case 1: // �ɹ�
				text = weibo.getName() + " completed at " + text;
				Toast.makeText(context, text + "", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2: // ʧ��
				text = weibo.getName() + " caught error at " + text;
				Toast.makeText(context, text + "", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3: // ȡ��
				text = weibo.getName() + " canceled at " + text;
				Toast.makeText(context, text + "", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			
			AbstractWeibo[] weibos = AbstractWeibo.getWeiboList(context);
			
			for(AbstractWeibo aw : weibos)
				getWeiboName(aw);
			for(Boolean b:mSelect){
				Log.v("----selected2--->", b+"");
			}
			
			gviadapter.notifyDataSetChanged();
			
		}
	};
}
