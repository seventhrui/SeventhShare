package com.seventh.SeventhShare.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.seventh.SeventhShare.DatabaseHandler;
import com.seventh.SeventhShare.HandlerCode;
import com.seventh.SeventhShare.R;
import com.seventh.SeventhShare.UserFunctions;
import com.seventh.SeventhShare.activity.MainActivity;
import com.seventh.SeventhShare.util.CheckNetworkStateUtil;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment -- Login
 * 
 */
public class Fragment_Login extends Fragment {
	private Context context;
	private View rootView = null;
	private EditText et_login_usernum;
	private EditText et_login_userpswd;
	private CheckBox cb_login_rememberpswd;
	private TextView tv_login_error;
	private Button btn_login_submit;
	private Button btn_login_undo;
	private Button btn_login_register;

	private String customername = "";
	private String customerpass = "";
	private Thread threadlogin = null;

	private UserFunctions userFunction=null;
	// JSON Response node names
	private JSONObject json = null;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "id";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_PHONE = "phone";
	private static String KEY_QQ = "qq";
	private static String KEY_UPTIME = "uptime";

	public Fragment_Login(Context c) {
		this.context = c;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		initView(inflater, container);
		initListener();
		return rootView;
	}

	/**
	 * Initialize the View
	 * 
	 * @param i
	 * @param c
	 */
	private void initView(LayoutInflater i, ViewGroup c) {
		rootView = i.inflate(R.layout.fragment_login_page, c, false);

		et_login_usernum = (EditText) rootView
				.findViewById(R.id.et_login_usernum);
		et_login_userpswd = (EditText) rootView
				.findViewById(R.id.et_login_userpswd);

		et_login_usernum
				.setText(MainActivity.sp.getString("Customer_name", ""));
		et_login_userpswd.setText(MainActivity.sp
				.getString("Customer_pass", ""));

		cb_login_rememberpswd = (CheckBox) rootView
				.findViewById(R.id.cb_login_rememberpswd);
		tv_login_error = (TextView) rootView.findViewById(R.id.tv_login_error);
		btn_login_submit = (Button) rootView
				.findViewById(R.id.btn_login_submit);
		btn_login_undo = (Button) rootView.findViewById(R.id.btn_login_undo);
		btn_login_register = (Button) rootView
				.findViewById(R.id.btn_login_register);
	}

	/**
	 * Initialize the Listener
	 */
	private void initListener() {
		LoginOnclickListener loll = new LoginOnclickListener();
		btn_login_submit.setOnClickListener(loll);
		btn_login_undo.setOnClickListener(loll);
		btn_login_register.setOnClickListener(loll);
	}

	/**
	 * OnclickListener
	 * 
	 */
	class LoginOnclickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_login_submit:
				handler_login.sendEmptyMessage(HandlerCode.LOGIN_CUSTOMER_BEGIN);
				// login();
				break;
			case R.id.btn_login_undo:
				handler_login.sendEmptyMessage(HandlerCode.LOGIN_CUSTOMER_UNDO);
				break;
			case R.id.btn_login_register:
				break;
			}
		}
	}
	
	/**
	 * Login Function
	 * @return login result
	 */
	private String loginStart() {
		String loginresult = "";
		customername = et_login_usernum.getText().toString().trim();
		customerpass = et_login_userpswd.getText().toString().trim();
		if (TextUtils.isEmpty(customername) || TextUtils.isEmpty(customerpass)) {
			Toast.makeText(getActivity(), "帐码或密码不能为空", 0).show();
		} else if(!CheckNetworkStateUtil.checkNet(context)){
			Toast.makeText(getActivity(), "请检查网络连接", 0).show();
		} else{
			btn_login_submit.setVisibility(View.GONE);
			btn_login_undo.setVisibility(View.VISIBLE);
			threadlogin=new Thread(loginThread);
			threadlogin.start();
		}
		return loginresult;
	}
	/**
	 * Login Thread
	 */
	private Runnable loginThread = new Runnable() {
		public void run() {
			userFunction = new UserFunctions();
			json = userFunction.loginUser(customername, customerpass);
			// Check Login
			try {
				if (json.getString(KEY_SUCCESS) != null) {
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 1) {
						handler_login.sendEmptyMessage(HandlerCode.LOGIN_CUSTOMER_SUCCESS);
					} else {
						handler_login.sendEmptyMessage(HandlerCode.LOGIN_CUSTOMER_FAILURE);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	private void saveData(){
		new Thread(saveThread).start();
	}
	/**
	 * Save Thread
	 */
	private Runnable saveThread = new Runnable(){
		public void run(){
			try {
				if (cb_login_rememberpswd.isChecked()) {
					Editor editor = MainActivity.sp.edit();
					editor.putString("Customer_name", customername);
					editor.putString("Customer_pass", customerpass);
					editor.commit();
					Toast.makeText(context, "密码已保存", 0).show();
				}
				// Save Customer to SQLite
				DatabaseHandler db = new DatabaseHandler(context);
				JSONObject json_user = json.getJSONObject("customer");
				Log.v("---json_user--->", json_user.toString());

				userFunction.logoutUser(context);
				Log.v("---KEY_NAME--->", json_user.getString(KEY_NAME) + " 1");
				Log.v("---KEY_EMAIL--->", json_user.getString(KEY_EMAIL) + " 2");
				Log.v("---KEY_UID--->", json_user.getString(KEY_UID) + " 3");
				Log.v("---KEY_PHONE--->", json_user.getString(KEY_PHONE) + " 4");
				Log.v("---KEY_QQ--->", json_user.getString(KEY_QQ) + " 5");
				Log.v("---KEY_UPTIME--->", json_user.getString(KEY_UPTIME)
						+ " 6");

				db.addUser(json_user.getString(KEY_NAME),
						json_user.getString(KEY_EMAIL),
						json_user.getString(KEY_UID),
						json_user.getString(KEY_PHONE),
						json_user.getString(KEY_QQ),
						json_user.getString(KEY_UPTIME));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			handler_login.sendEmptyMessage(HandlerCode.LOGIN_CUSTOMER_SAVEDATAED);
		}
	};
	/**
	 * Login Cancel
	 */
	private void loginStop(){
		Thread.interrupted();
		Log.v("---线程状态--->", threadlogin.isInterrupted()+"");
		if(!threadlogin.isInterrupted()){
			btn_login_submit.setVisibility(View.VISIBLE);
			btn_login_undo.setVisibility(View.GONE);
		}
	}
	/**
	 * Login Error
	 */
	private void loginError() {
		tv_login_error.setText("用户名或密码错误");
		btn_login_submit.setVisibility(View.VISIBLE);
		btn_login_undo.setVisibility(View.GONE);
	}
	/**
	 * Goto  Fragment_lv
	 */
	private void gotoPage() {
		Fragment fragment=new Fragment_lv(context);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		String planet = getResources().getStringArray(R.array.planets_array)[0];
		getActivity().setTitle(planet);
	}
	private Handler handler_login = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int whatVal = msg.what;
			switch (whatVal) {
			case HandlerCode.LOGIN_CUSTOMER_BEGIN:
				loginStart();
				break;
			case HandlerCode.LOGIN_CUSTOMER_UNDO:
				loginStop();
				break;
			case HandlerCode.LOGIN_CUSTOMER_SUCCESS:
				saveData();
				break;
			case HandlerCode.LOGIN_CUSTOMER_FAILURE:
				loginError();
				break;
			case HandlerCode.LOGIN_CUSTOMER_SAVEDATAED:
				gotoPage();
				break;
			}
		}
	};
}
