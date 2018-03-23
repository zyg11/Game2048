package com.example.game2048;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}
	private void initGameView() {
		setColumnCount(4);//设置4列
		setBackgroundColor(0xffbbada0);
		setOnTouchListener(new OnTouchListener() {
			private float startX,startY,offsetX,offsetY;
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX=event.getX();
					startY=event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX=event.getX()-startX;
					offsetY=event.getY()-startY;
					if (Math.abs(offsetX)>Math.abs(offsetY)) {
						if (offsetX<-5) {
							swipeLeft();
							System.out.println("Left");
						}else if (offsetX>5) {
							swipeRight();
							System.out.println("Right");
						}
					}else {
						if (offsetY<-5) {
							swipeUp();
							System.out.println("Up");
						}else if (offsetY>5) {
							swipeDown();
							System.out.println("Down");
						}
					}
					
					break;
				default:
					break;
				}
				return true;//必须为TRUE，否则只能监听到Touchdown
			}
		});
	}
	//设置宽高
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth=(Math.min(w, h)-10)/4;//求最小值
		addCards(cardWidth,cardWidth);//初始为方形
		startGame();
	}
	private void addCards(int cardWidth,int cardHeight) {
		Card c;//创建卡片
		for (int y = 0; y <4; y++) {
			for (int x = 0; x <4; x++) {
				c=new Card(getContext());
				c.setNum(0);
				addView(c, cardWidth, cardHeight);
				cardsMap[x][y]=c;
			}
		}
	}
	//进行初始化的清理，都为零
	private void startGame() {
		MainActivity.getMainActivity().clearScore();
		for (int y = 0; y <4; y++) {
			for (int x = 0; x <4; x++) {
				cardsMap[x][y].setNum(0);
			}
		}
		addRandonNum();//添加随机数
		addRandonNum();//有两个数要添加两次

	}
	private void addRandonNum() {
		emptyPoints.clear();
		for (int y = 0; y <4; y++) {
			for (int x = 0; x <4; x++) {
				if (cardsMap[x][y].getNum()<=0) {
					emptyPoints.add(new Point(x,y));
				}
			}
		}
		Point p=emptyPoints.remove((int)(Math.random()*emptyPoints.size()));//把从0到1之间的数值做了变换
		cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
	}
	//四个动作执行特定的代码
	private void swipeLeft() {
		boolean merge=false;
		for (int y = 0; y <4; y++) {
			for (int x = 0; x <4; x++) {
				
				for (int x1 = x+1; x1 <4; x1++) {
					if (cardsMap[x1][y].getNum()>0) {
						if (cardsMap[x][y].getNum()<=0) {//如果为空，将右边位置的移到当前位置
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0); 
							x--;
							merge=true;
						}else if (cardsMap[x][y].equals(cardsMap[x1][y])) {//两个值相同可以合并
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);//合并后乘以2
							cardsMap[x1][y].setNum(0);//把右边的清掉
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
	
		}	
		if (merge) {
			addRandonNum();
			checkComplete();
		}
	}
	private void swipeRight() {
		boolean merge=false;
		for (int y = 0; y <4; y++) {
			for (int x = 3; x>=0; x--) {				
				for (int x1 = x-1; x1>0; x1--) {
					if (cardsMap[x1][y].getNum()>0) {
						if (cardsMap[x][y].getNum()<=0) {
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0); 
							x++;
							merge=true;
						}else if (cardsMap[x][y].equals(cardsMap[x1][y])) {//两个值相同可以合并
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);//合并后乘以2
							cardsMap[x1][y].setNum(0);//把右边的清掉
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		
		}
		if (merge) {
			addRandonNum();
			checkComplete();
		}
	}
	private void swipeUp() {
		boolean merge=false;
		for (int x= 0; x<4; x++) {
			for (int y = 0; y<4; y++) {				
				for (int y1 = y+1; y1<4; y1++) {
					if (cardsMap[x][y1].getNum()>0) {
						if (cardsMap[x][y].getNum()<=0) {
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0); 
							y--;
							merge=true;
						}else if (cardsMap[x][y].equals(cardsMap[x][y1])) {//两个值相同可以合并
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);//合并后乘以2
							cardsMap[x][y1].setNum(0);//把右边的清掉
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
	
		}
		if (merge) {
			addRandonNum();
			checkComplete();
		}
	}
	private void swipeDown() {
		boolean merge=false;
		for (int x= 0; x<4; x++) {
			for (int y = 3; y>=0; y--) {				
				for (int y1 = y-1; y1>=0; y1--) {
					if (cardsMap[x][y1].getNum()>0) {
						if (cardsMap[x][y].getNum()<=0) {
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0); 
							y++;
							merge=true;
						}else if (cardsMap[x][y].equals(cardsMap[x][y1])) {//两个值相同可以合并
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);//合并后乘以2
							cardsMap[x][y1].setNum(0);//把右边的清掉
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge=true;
						}
						break;
					}
				}
			}
		
		}
		if (merge) {
			addRandonNum();
			checkComplete();
		}
	}
	private void checkComplete() {
		boolean complete=true;
		ALL://标签
		for (int y = 0; y <4; y++) {
			for (int x = 0; x <4; x++) {
				if (cardsMap[x][y].getNum()==0||
						(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
						(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
						(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
						(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {
					complete=false;
					break ALL; 				
				}
			}
		}
		if (complete) {
			new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束")
			.setPositiveButton("重来", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
					
				}
			}).show();
		}
	}
	private Card[][] cardsMap=new Card[4][4];//添加的卡片记录到二维数组里
	private List<Point>emptyPoints=new ArrayList<>();
}
