package com.tuwien.snowwhite.celebrityData;

import android.graphics.drawable.Drawable;

public class Celebrity implements ICelebrity{
	
	private String name;
	private String sex;
	private float score;
	private Drawable picture;
	
	public Celebrity(String name, String sex, float score, Drawable picture){
		this.name = name;
		this.sex = sex;
		this.score = score;
		this.picture = picture;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSex() {
		return sex;
	}

	@Override
	public float getScore() {
		return score;
	}

	@Override
	public Drawable getPicture() {
		return picture;
	}
	

}
