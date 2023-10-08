package package_comportamento;

import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import package_pesos.Pesos;
import robot.RobotLegoEV3;

public abstract class Comportamento extends Thread {

	protected RobotLegoEV3 robot;
	private ComportamentoGUI gui;

	protected static final int ESPERAR = 0;
	protected static final int TRABALHAR = 1;
	protected static final int TERMINAR = 2;
	
	protected int state;
	protected Pesos pesos;
	protected int prioridade;

	public Comportamento(RobotLegoEV3 robot,Pesos pesos, int prioridade) {
		this.state = ESPERAR;
		this.robot = robot;
		this.prioridade = prioridade;
		this.pesos = pesos;
		robot.SetVelocidade(50);
	}

	public synchronized void esperar() {
		this.state = ESPERAR;
	}

	public synchronized void trabalha() {
		this.state = TRABALHAR;
		this.notify();
	}

	public synchronized void terminar() {
		this.state = TERMINAR;
		this.interrupt();
		
	}
	
	// (Vrobot = 30cm/s)
	// Tempo de espera retas
	public long ExecucaoRetas(int distancia, float vel) {
		return (long) ((distancia / vel) * 1500.0f);
	}
	
	// Tempo de espera curvas
	public long ExecucaoCurvas(int raio, int ang) {
		return (long) ((((ang / 360.0f) * 2 * Math.PI * raio) / 30.0f) * 1500.0f);
	}
	
	abstract public long gerarMensagem();
}
