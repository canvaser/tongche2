package com.siwei.tongche.dialog;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.siwei.tongche.R;


/**
 * Created by lg on 2015/10/22.
 */
public class NormalAlertDialog extends Dialog {
	private Context context;
	//private String title;
	private ClickListenerInterface clickListenerInterface;
	private String message;
	public NormalAlertDialog(Context context, String message) {
		super(context, R.style.dialogBase);
		this.context = context;
		this.message=message;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_normal_alert, null);
		setContentView(view);
		TextView btComfirm = (TextView) view.findViewById(R.id.bt_comfirm);
		TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
		TextView btMiss = (TextView) view.findViewById(R.id.bt_miss);
		tv_content.setText(message);
		clickListener clickListener=new clickListener();
		btComfirm.setOnClickListener(clickListener);
		btMiss.setOnClickListener(clickListener);
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
		dialogWindow.setAttributes(lp);
	}

	public interface ClickListenerInterface {
		public void doConfirm();
		public void doCancel();
	}

	public void setClicklistener(ClickListenerInterface clickListenerInterface) {
		this.clickListenerInterface = clickListenerInterface;
	}

	private class clickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.bt_comfirm://确认
				clickListenerInterface.doConfirm();
				dismiss();
				break;
			case R.id.bt_miss:	//取消
				clickListenerInterface.doCancel();
				dismiss();
				break;

			}
		}
	};
}
