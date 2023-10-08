package package_vaguear;

import java.util.Random;
import java.util.concurrent.Semaphore;

import package_comportamento.Comportamento;
import package_comportamento.ComportamentoGUI;
import package_pesos.Pesos;
import robot.RobotLegoEV3;

public class Vaguear extends Comportamento {

	private ComportamentoGUI gui;

	public Vaguear(RobotLegoEV3 robot,Pesos pesos, int prioridade) {
		super(robot, pesos, prioridade);
		gui = new ComportamentoGUI("Vaguear",600);
	}
	
	public ComportamentoGUI getGui() {
		return gui;
	}

	@Override
	public long gerarMensagem() {

		Random rnd = new Random();

		int[] types = new int[] { 1, 2, 3, 4, 5 };
		int type = 0;
		int lastType = 0;
		float vel = 30.0f; // cm/s

		long tempoEspera = 0;

		while (type == lastType) {
			type = types[rnd.nextInt(5)];
		}

		lastType = type;
		int dist = 50 + rnd.nextInt(10);
		int angulo = 45 + rnd.nextInt(45);
		String msg = null;	
		switch (type) {

		case 1:
			msg = "Reta" + " dist " + dist;
			gui.getTextAreaComportamento().append(msg + "\n");
			robot.Reta(dist);
			tempoEspera = ExecucaoRetas(dist, vel);
			break;

		case 2:
			msg = "Curva para a Esquerda" + " dist " + dist + " ang " + angulo;
			gui.getTextAreaComportamento().append(msg + "\n");
			robot.CurvarEsquerda(dist, angulo);
			tempoEspera = ExecucaoCurvas(dist, angulo);
			break;

		case 3:
			msg = "Curva para a Direita" + " dist " + dist + " ang " + angulo;
			gui.getTextAreaComportamento().append(msg + "\n");
			robot.CurvarDireita(dist, angulo);
			tempoEspera = ExecucaoCurvas(dist, angulo);
			break;

		case 4:
			msg = "Retaguarda" + " dist " + dist;
			gui.getTextAreaComportamento().append(msg + "\n");
			robot.Reta(-dist);
			tempoEspera = ExecucaoRetas(dist, vel);
			break;

		case 5:
			msg = "Parar a true";
			gui.getTextAreaComportamento().append(msg + "\n");
			robot.Parar(true);
			break;
		}
		return tempoEspera;
	}

	public void run() {
		long tempoEspera = 0;

		while (state != TERMINAR) {

			switch (this.state) {

			case ESPERAR:
				try {
					pesos.verificarPeso(prioridade);
					robot.Parar(false);
					pesos.resetPeso();

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
					tempoEspera = gerarMensagem();
					pesos.resetPeso();
					Thread.sleep(tempoEspera);

				} catch (InterruptedException e) {
					e.printStackTrace();

					if (state != TERMINAR)
						esperar();
				}
				break;
			}
		}

		robot.Parar(true);
	}

}
