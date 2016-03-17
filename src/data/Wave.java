package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {
	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemysPerWave, enemiesSpawned;
	private boolean waveCompleted;

	public Wave(Enemy enemyType, float spawnTime, int enemysPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		this.enemysPerWave = enemysPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;

		spawn();
	}

	public void Update() {
		
		//Assume all enemies are dead, until for loop proves otherwise
		boolean allEnemysDead = true;
		if (enemiesSpawned < enemysPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}

		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemysDead = false;
				e.update();
				e.draw();
			} else
				enemyList.remove(e);
		}
		
		if (allEnemysDead)
			waveCompleted = true;
	}

	private void spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(), TILE_SIZE, TILE_SIZE,
				enemyType.getSpeed(), enemyType.getHealth()));
		enemiesSpawned++;
	}

	public boolean isCompleted() {
		return waveCompleted;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
}
