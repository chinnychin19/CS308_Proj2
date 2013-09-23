package jboxGlue;

import jgame.platform.JGEngine;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import ourObjects.Constants;

public class WorldManager {
	public static OurWorld ourWorld;

	static {
		ourWorld = null;
	}

	public static OurWorld getWorld() {
		// make sure we have a world, just in case...
		if (ourWorld == null) {
			throw new Error("call initWorld() before you call getWorld()!");
		}

		return ourWorld;
	}

	public static void initWorld(JGEngine engine) {
		AABB worldBounds = new AABB(new Vec2(0, 0), new Vec2(
				engine.displayWidth(), engine.displayHeight()));
		Vec2 gravity = Constants.DEFAULT_GRAVITY;
		ourWorld = new OurWorld(worldBounds, gravity, true);
	}
}
