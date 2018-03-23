package com.example.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	public Card(Context context) {
		super(context);
		label=new TextView(getContext());
		label.setTextSize(32);
		label.setGravity(Gravity.CENTER);
		label.setBackgroundColor(0x33ffffff);
		LayoutParams lp=new LayoutParams(-1,-1);
		lp.setMargins(10,10,0,0);//设置间隔 lp即每个卡片
		addView(label,lp);
		
		setNum(0);
	}
	private int num=0;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num=num;
		if (num<=0) {
			label.setText("");//必须变成字符串//是零呈现空字符串
		}else {
			label.setText(num+"");//不是零的话呈现正常值
		}
		
	}
	public boolean equals(Card o) {//判断是否折叠
		return getNum()==o.getNum();
	}
	private TextView label;
}
