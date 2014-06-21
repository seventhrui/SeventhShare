package com.seventh.SeventhShare.fragment;

import com.seventh.SeventhShare.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
	private Button btn_login_submit;
	private Button btn_login_undo;
	private Button btn_login_register;

	public Fragment_Login(Context c) {
		this.context=c;
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
		cb_login_rememberpswd = (CheckBox) rootView
				.findViewById(R.id.cb_login_rememberpswd);
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
				login();
				break;
			case R.id.btn_login_undo:
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
	private String login(){
		String loginresult = "";
		String usernum = et_login_usernum.getText().toString().trim();
		String userpswd = et_login_userpswd.getText().toString().trim();
		Toast.makeText(context, usernum+","+userpswd, Toast.LENGTH_SHORT).show();
		return loginresult;
	}
}
