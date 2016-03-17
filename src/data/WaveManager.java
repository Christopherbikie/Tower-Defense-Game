package data;

public class WaveManager {
	
	private float timeBetweenEnemys;
	private int waveNumber, enemysPerWave;
	private Enemy enemyType;
	private Wave currentWave;
	
	public WaveManager(Enemy enemyType, int timeBetweenEnemys, int enemysPerWave) {
		this.enemyType = enemyType;
		this.timeBetweenEnemys = timeBetweenEnemys;
		this.enemysPerWave = enemysPerWave;
		this.waveNumber = 0;
		this.currentWave = null;
		newWave();
	}
	
	public void update() {
		if (!currentWave.isCompleted())
			currentWave.Update();
		else
			newWave();
	}
	
	private void newWave() {
		currentWave = new Wave(enemyType, timeBetweenEnemys, enemysPerWave);
		waveNumber++;
		System.out.println("Beginning Wave " + waveNumber);
	}
	
	public Wave getCurrentWave() {
		return currentWave;
	}
}
