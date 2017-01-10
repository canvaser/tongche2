package com.siwei.tongche.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.siwei.tongche.R;
import com.siwei.tongche.utils.DensityUtil;


/**
 * 选择照片
 */
public class PopWindowSelectCamera extends Activity implements OnClickListener{

	private Button btn_take;
	private Button btn_pick;
	private Button btn_cancle;
	private Intent intent;
	public static final int UPLOAD_OPEN_CAMERA=0X11;//打开相机
	public static final int UPLOAD_OPEN_PHOTO=0X12;//打开相册

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.popwindow_select_caremer);
    	init();
		initView();
		setListener();
    }

	private void initView() {

	}

	private void setListener() {
		btn_take.setOnClickListener(this);
		btn_pick.setOnClickListener(this);
		btn_cancle.setOnClickListener(this);
		
	}
	private void init() {
		btn_take=(Button) findViewById(R.id.btn_take);
		btn_pick=(Button) findViewById(R.id.btn_pick);
		btn_cancle=(Button) findViewById(R.id.btn_cancle);
		intent=new Intent();

		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
		WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
		p.height = LinearLayout.LayoutParams.MATCH_PARENT;
		p.width = DensityUtil.getScreenWidth();
		p.alpha = 1.0f;      //设置本身透明度
		p.dimAmount = 0.5f;      //设置黑暗度
		getWindow().setAttributes(p);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.btn_take:
				setResult(RESULT_OK, intent.putExtra("type", UPLOAD_OPEN_CAMERA));//拍照
				break;
			case R.id.btn_pick:
				setResult(RESULT_OK, intent.putExtra("type", UPLOAD_OPEN_PHOTO));//相册选择
				break;
			case R.id.btn_cancle:
				break;
			default:
				break;
		}
		finish();
	}

	// 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
}
