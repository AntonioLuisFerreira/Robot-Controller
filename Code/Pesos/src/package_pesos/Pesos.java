package package_pesos;

public class Pesos {

	private int prioridadeAtual = 0;
	
	public Pesos() {}

	public synchronized void verificarPeso(int peso) throws InterruptedException {
		while (peso < prioridadeAtual) {
			this.wait();
		}
		prioridadeAtual = peso;
		
	}
	
	public synchronized void resetPeso() {
		prioridadeAtual = 0;
		this.notifyAll();
	}
}
