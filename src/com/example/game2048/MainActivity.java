package com.example.game2048;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private TextView tvScore;
	private static MainActivity mainActivity=null;
	private int score=0;
	public MainActivity(){
		mainActivity=this;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvScore =(TextView) findViewById(R.id.tvScore);
	}
	
	public void clearScore() {//
		score=0;
		showScore();
	}
	public void showScore() {
		tvScore.setText(score+"");//必须加空字符串
	}
	public void addScore(int s) {//添加
		score+=s;
		showScore();
	}
	public static MainActivity getMainActivity() {
		return mainActivity;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}
