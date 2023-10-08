package package_evitar;

import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import package_comportamento.Comportamento;
import package_comportamento.ComportamentoGUI;
import package_pesos.Pesos;
import robot.RobotLegoEV3;

public class Evitar extends Comportamento{

	private ComportamentoGUI gui;
	private int local = 1050;
	private String msg;

	public Evitar(RobotLegoEV3 robot, Pesos pesos, int prioridade) {
		super(robot,pesos, prioridade);
		gui = new ComportamentoGUI("Evitar",local);
	}

	public ComportamentoGUI getGui() {
		return gui;
	}

	@Override
	public long gerarMensagem() {

		float vel = 30.0f; // cm/s
		int dist = 15;
		int execCurva = 2500; // tempo que demora a executar a instrução

		robot.Parar(true);
		msg = "Parar a true";
		gui.getTextAreaComportamento().append(msg + "\n");

		robot.Reta(-15);
		msg = "Retaguarda" + " dist " + dist;;
		gui.getTextAreaComportamento().append(msg + "\n");

		robot.CurvarEsquerda(0, 90);
		msg = "Curva para a Esquerda" + " dist " + dist + " ang 90";
		gui.getTextAreaComportamento().append(msg + "\n");

		robot.Parar(false);
		msg = "Parar a true";
		gui.getTextAreaComportamento().append(msg + "\n");

		long tempoEspera = ExecucaoRetas(dist, vel) + execCurva;
		return tempoEspera;
	}

	/**
	 *
	 */
	public void run() {
		long tempoEspera = 0;
		while (state != TERMINAR) {

			switch (this.state) {

			case ESPERAR:
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case TRABALHAR:
				try {
					pesos.verificarPeso(prioridade);

					if (robot.SensorToque(robot.S_1) == 1) {
						if (state != ESPERAR) {
							tempoEspera = gerarMensagem();
							pesos.resetPeso();
							Thread.sleep(tempoEspera);
						}
					}
					pesos.resetPeso();

				} catch (InterruptedException e1) {
					e1.printStackTrace();
					if (state != TERMINAR)
						esperar();
				}

				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}

		}
		robot.Parar(true);
	}

	

}
