package com.siwei.tongche.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.siwei.tongche.R;
import com.siwei.tongche.utils.CacheUtils;
import com.siwei.tongche.utils.DensityUtil;
import com.siwei.tongche.utils.MyFormatUtils;
import com.siwei.tongche.utils.MyToastUtils;
import com.siwei.tongche.views.MyWheelView;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * 签收快递
 * Created by lg on 2015/10/22.
 */
public class SignExpressDialog extends Dialog {
	private Context context;
	private double  mDefaultSize;
	private ClickListenerInterface clickListenerInterface;


	private LinearLayout wheelView_layout;
	private LinearLayout layout_title;
	private LinearLayout layout_edit;
	private ImageButton edit_type;

	private MyWheelView mWheelViewNum;//方数
	private MyWheelView mWheelViewLeft;//剩余
	private MyWheelView mWheelViewLose;//损失


	DecimalFormat mDecimalFormat = new DecimalFormat("#0.0");
	private double mSignedSize;
	private double mLeftSize;
	private double mLostSize;

	//输入框
	private EditText edit_sign;
	private EditText edit_left;
	private EditText edit_lost;

	//编辑的输入框值
	private double mEditSignedSize;
	private double mEditLeftSize;
	private double mEditLostSize;

	private double mSignUnit=0.5;
	public SignExpressDialog(Context context, double defaultSize) {
		super(context, R.style.dialogBase);
		this.context = context;
		this.mDefaultSize=defaultSize;
		mSignedSize=defaultSize;
		mLeftSize=0;
		mLostSize=0;
		//初始化签收方量
		try {
//			mSignUnit= MyFormatUtils.toDouble(CacheUtils.getSystemConfig().getSIGN_NUM_TEP());
			mSignUnit=0.5;
		}catch (NumberFormatException e){
			e.printStackTrace();
			mSignUnit=0.5;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_sign_express, null);
		setContentView(view);

		wheelView_layout=(LinearLayout) view.findViewById(R.id.wheelView_layout);
		layout_title= (LinearLayout) view.findViewById(R.id.layout_title);
		layout_edit= (LinearLayout) view.findViewById(R.id.layout_edit);
		edit_type= (ImageButton) view.findViewById(R.id.edit_type);

		edit_sign= (EditText) view.findViewById(R.id.edit_sign);
		edit_left= (EditText) view.findViewById(R.id.edit_left);
		edit_lost= (EditText) view.findViewById(R.id.edit_lost);

		mWheelViewNum=new MyWheelView(context);
		mWheelViewNum.setLayoutParams(new ViewGroup.LayoutParams((int) (DensityUtil.getScreenWidth()*0.26),ViewGroup.LayoutParams.WRAP_CONTENT));
		wheelView_layout.addView(mWheelViewNum);

		mWheelViewLeft=new MyWheelView(context);
		mWheelViewLeft.setLayoutParams(new ViewGroup.LayoutParams((int) (DensityUtil.getScreenWidth()*0.26),ViewGroup.LayoutParams.WRAP_CONTENT));
		mWheelViewLeft.setType(MyWheelView.TYPE_SHENGYU);
		wheelView_layout.addView(mWheelViewLeft);

		mWheelViewLose=new MyWheelView(context);
		mWheelViewLose.setLayoutParams(new ViewGroup.LayoutParams((int) (DensityUtil.getScreenWidth()*0.27),ViewGroup.LayoutParams.WRAP_CONTENT));
		mWheelViewLose.setType(MyWheelView.TYPE_SHENGYU);
		wheelView_layout.addView(mWheelViewLose);


		initView();
		TextView btComfirm = (TextView) view.findViewById(R.id.bt_comfirm);
		clickListener clickListener=new clickListener();
		btComfirm.setOnClickListener(clickListener);
		view.findViewById(R.id.bt_miss).setOnClickListener(clickListener);

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.6
		dialogWindow.setAttributes(lp);
		view.findViewById(R.id.edit_type);

		edit_type.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(layout_title.getVisibility()==View.GONE){//切换到选择模式
					edit_type.setImageResource(R.drawable.sign_select);

					wheelView_layout.setVisibility(View.VISIBLE);
					layout_title.setVisibility(View.VISIBLE);
					layout_edit.setVisibility(View.GONE);

				}else{//编辑模式
					edit_type.setImageResource(R.drawable.sign_edit);

					wheelView_layout.setVisibility(View.GONE);
					layout_title.setVisibility(View.GONE);
					layout_edit.setVisibility(View.VISIBLE);

					//显示默认值
					edit_sign.setText(mDecimalFormat.format(mSignedSize));
					edit_left.setText(mDecimalFormat.format(mLeftSize));
					String lostSizeStr= mLostSize>0 ? "+"+mDecimalFormat.format(mLostSize) : ""+mDecimalFormat.format(mLostSize);
					edit_lost.setText(lostSizeStr);
				}
			}
		});

		edit_sign.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				mEditSignedSize=MyFormatUtils.toDouble(s.toString());
				if(mEditSignedSize>mDefaultSize){
					MyToastUtils.showToast("签收方量不能大于发货方量");
					//默认最大方量
					mEditSignedSize=mDefaultSize;
					edit_sign.setText(mDecimalFormat.format(mDefaultSize));
				}
				refreshEditLostNum();
			}
		});

		edit_left.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				mEditLeftSize=MyFormatUtils.toDouble(s.toString());
				if(mEditLeftSize>mDefaultSize){
					MyToastUtils.showToast("剩余方量不能大于发货方量");
					//默认位0
					mEditLeftSize=0;
					edit_left.setText(mDecimalFormat.format(0));
				}
				refreshEditLostNum();
			}
		});

	}
	/**
	 * 刷新编辑模式下损失数量
	 */
	private void refreshEditLostNum(){
		//计算剩余方量
		mEditLostSize=mDefaultSize-mEditSignedSize-mEditLeftSize;
		//正负数互转
		if(mEditLostSize>0){
			mEditLostSize=-mEditLostSize;
		}else{
			mEditLostSize=Math.abs(mEditLostSize);
		}
		String lostSizeStr= mEditLostSize>0 ? "+"+mDecimalFormat.format(mEditLostSize) : ""+mDecimalFormat.format(mEditLostSize);
		edit_lost.setText(lostSizeStr);
	}


	/**
	 * 刷新损失数量
	 */
	private void refreshLostNum(){
		//计算剩余方量
		mLostSize=mDefaultSize-mSignedSize-mLeftSize;
		//正负数互转
		if(mLostSize>0){
			mLostSize=-mLostSize;
		}else{
			mLostSize=Math.abs(mLostSize);
		}
		String lostSizeStr= mLostSize>0 ? "+"+mDecimalFormat.format(mLostSize) : ""+mDecimalFormat.format(mLostSize);
		ArrayList<String>  lostList=new ArrayList<String>();
		lostList.add(lostSizeStr);
		mWheelViewLose.resetItems(lostList);
		mWheelViewLose.invalidate();//刷新数据
	}

	private void initView() {
		//数据
		ArrayList<String>  fangshuList=new ArrayList<String>();
		ArrayList<String>  shengyuList=new ArrayList<String>();
		//设置损失
		refreshLostNum();
		if(mDefaultSize%mSignUnit!=0){//不是整数
			//默认方量
			fangshuList.add(mDecimalFormat.format(mDefaultSize)+"方");
			shengyuList.add(mDecimalFormat.format(0)+"");
		}
		//整数
		double intNumber=mDefaultSize-mDefaultSize%mSignUnit;
		for(double i=intNumber;i>0;i-=mSignUnit){
			fangshuList.add(mDecimalFormat.format(i)+"方");
			shengyuList.add(mDecimalFormat.format(Math.abs(i-mDefaultSize))+"");
		}
		mWheelViewNum.setItems(fangshuList);
		mWheelViewLeft.setItems(shengyuList);
		mWheelViewNum.setOnWheelViewListener(new MyWheelView.OnWheelViewListener(){
			@Override
			public void onSelected(int selectedIndex, String item,boolean isByUser) {
				//选择签收方量
				mSignedSize=MyFormatUtils.toDouble(item.substring(0,item.length()-1));
				if(!leftIsEdited){//剩余方量已经选择过了  无须在联动
					mWheelViewLeft.setSeletion(selectedIndex-1);
				}else{//刷新数据
					refreshLostNum();
				}

			}
		});

		mWheelViewLeft.setOnWheelViewListener(new MyWheelView.OnWheelViewListener(){
			@Override
			public void onSelected(int selectedIndex, String item,boolean isByUser) {
				if(isByUser){
					leftIsEdited=true;
				}
				//选择剩余方量
				mLeftSize=MyFormatUtils.toDouble(item);
				refreshLostNum();
			}
		});


	}

	boolean  leftIsEdited=false;

	public interface ClickListenerInterface {
		public void doConfirm(String signedSize, String leftSize, String loseSize);
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
					if(clickListenerInterface!=null){
						if(layout_title.getVisibility()==View.GONE){ //编辑模式
							String editLostSizeStr= mEditLostSize>0 ? "+"+mDecimalFormat.format(mEditLostSize) : ""+mDecimalFormat.format(mEditLostSize);
							clickListenerInterface.doConfirm(mDecimalFormat.format(mEditSignedSize),mDecimalFormat.format(mEditLeftSize),editLostSizeStr);
						}else{//选择模式
							String lostSizeStr= mLostSize>0 ? "+"+mDecimalFormat.format(mLostSize) : ""+mDecimalFormat.format(mLostSize);
							clickListenerInterface.doConfirm(mDecimalFormat.format(mSignedSize),mDecimalFormat.format(mLeftSize),lostSizeStr);
						}
					}
					dismiss();
					break;
				case R.id.bt_miss:	//取消
					dismiss();
					break;

			}
		}
	};
}
