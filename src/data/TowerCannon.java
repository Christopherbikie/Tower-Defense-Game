package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public class TowerCannon {
	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, range;
	private Texture baseTexture, cannonTexture;
	private ArrayList<Projectile> projectiles;
	private CopyOnWriteArrayList<Enemy> enemys;
	private Enemy target;
	private boolean targeted;
	
	public TowerCannon(Texture baseTexture, Tile startTile, int damage, int range, CopyOnWriteArrayList<Enemy> enemys) {
		this.baseTexture = baseTexture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.range = range;
		this.firingSpeed = 3f;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemys = enemys;
		this.targeted = false;
	}
	
	private Enemy acquireTarget() {
		Enemy closest = null;
		float closestDistance = 10000;
		for (Enemy e : enemys) {
			if (isInRange(e) && findDistance(e) < closestDistance) {
				closestDistance = findDistance(e);
				closest = e;
			}
		}
		if (closest != null)
			targeted = true;
		return closest;
	}
	
	private boolean isInRange(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if (xDistance < range && yDistance < range)
			return true;
		return false;
	}
	
	private float findDistance(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		return xDistance + yDistance;
	}
	
	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}
	
	private void shoot() {
		timeSinceLastShot = 0;
//		projectiles.add(new ProjectileIceball(QuickLoad("bullet"), target, x + TILE_SIZE / 2 - TILE_SIZE / 4, y + TILE_SIZE / 2 - TILE_SIZE / 4, 32, 32, 900, 10));
	}
	
	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		enemys = newList;
	}
	
	public void update() {
		if (!targeted)
			target = acquireTarget();
		else if (timeSinceLastShot >= firingSpeed)
			shoot();
		
		if (target == null || !target.isAlive())
			targeted = false;
		
		timeSinceLastShot += Delta();
		
		for (Projectile p : projectiles)
			p.update();
		
		angle = calculateAngle();
		draw();
	}
	
	public void draw() {
		DrawQuadTex(baseTexture, x, y, width, height);
		DrawQuadTexRot(cannonTexture, x, y, width, height, angle);
	}
}
