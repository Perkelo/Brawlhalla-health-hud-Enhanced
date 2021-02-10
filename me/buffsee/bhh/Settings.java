package me.buffsee.bhh;

import org.json.JSONObject;

public class Settings {
	public float imageSize;
	public int textSize;
	public float transparency;
	public float xPosition;
	public float yPosition;

	public Settings(float imageSize, int textSize, float transparency, float xPosition, float yPosition){
		this.imageSize = imageSize;
		this.textSize = textSize;
		this.transparency = transparency;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public static Settings fromJSON(String json){
		if(json == null || json.equals("")){
			return new Settings(0, 0, 0, 0, 0);
		}
		JSONObject obj = new JSONObject(json);
		return new Settings(
				obj.getInt("imageSize"),
				obj.getInt("textSize"),
				obj.getFloat("transparency"),
				obj.getFloat("xPosition"),
				obj.getFloat("yPosition")
		);
	}

	public String toJSON(){
		JSONObject obj = new JSONObject();
		obj.put("imageSize", imageSize);
		obj.put("textSize", textSize);
		obj.put("transparency", transparency);
		obj.put("xPosition", xPosition);
		obj.put("yPosition", yPosition);
		return obj.toString();
	}
}
