package data;

import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Artist.TILE_SIZE;

public class TowerCannonBlue extends Tower{
	public TowerCannonBlue(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}

	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileCannonball(super.type.projectileType, super.target,
				super.getX() + TILE_SIZE / 2 - 16,
				super.getY() + TILE_SIZE / 2 - 16, 32, 32));
		
	}
}
