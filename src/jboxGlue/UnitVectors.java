package jboxGlue;

import org.jbox2d.common.Vec2;

public class UnitVectors {
	public static Vec2 unitVector(Vec2 v1, Vec2 v2) {
		float x = v2.x - v1.x;
		float y = v2.y - v1.y;
		float d = dist(v1, v2);
		return new Vec2(x/d, y/d);
	}
	
	private static float dist(Vec2 v1, Vec2 v2) {
		return (float) Math.sqrt(Math.pow(v2.x-v1.x,2) + Math.pow(v2.y-v1.y, 2));
	}
}
