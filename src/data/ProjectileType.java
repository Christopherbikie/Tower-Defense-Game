package data;

import static helpers.Artist.QuickLoad;

import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {
	
	CannonBall(QuickLoad("bullet"), 10, 500),
	IceBall(QuickLoad("projectileIceBall"), 6, 450);
	
	Texture texture;
	int damage;
	float speed;
	
	ProjectileType(Texture texture, int damage, float speed) {
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
	}
}
