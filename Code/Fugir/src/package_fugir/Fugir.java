package package_fugir;

import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.concurrent.Semaphore;

import package_comportamento.Comportamento;
import package_comportamento.ComportamentoGUI;
import package_pesos.Pesos;
import robot.RobotLegoEV3;

public class Fugir extends Comportamento {

	private ComportamentoGUI gui;
	private int local = 1490;
	private String msg;

	public Fugir(RobotLegoEV3 robot,Pesos pesos, int prioridade) {
		super(robot, pesos, prioridade);
		gui = new ComportamentoGUI("Fugir", local);
	}

	public ComportamentoGUI getGui() {
		return gui;
	}

	@Override
	public long gerarMensagem() {

		float vel = 30.0f; // cm por s
		int dist = 50;
		int radius = 10;
		int ang = 90;

		robot.Parar(true);

		robot.SetVelocidade(80);
		msg = "Velocidade 80%";
		gui.getTextAreaComportamento().append(msg + "\n");

		robot.Reta(50);
		msg = "Reta 50 cm";
		gui.getTextAreaComportamento().append(msg + "\n");

		robot.SetVelocidade(50);
		msg = "Velocidade 50%";
		gui.getTextAreaComportamento().append(msg + "\n");

		if (esquerdaOuDireita() > 0.5) {
			robot.CurvarEsquerda(10, 90);
			msg = "Curvar Esquerda, 10 cm, 90º";
			gui.getTextAreaComportamento().append(msg + "\n");
		}

		else {
			robot.CurvarDireita(10, 90);
			msg = "Curvar Direita, 10 cm, 90º";
		}

		robot.Parar(false);
		msg = "Parar a false";
		gui.getTextAreaComportamento().append(msg + "\n");

		long tempoEspera = ExecucaoRetas(dist, vel) + ExecucaoCurvas(radius, ang);
		return tempoEspera;
	}

	private int esquerdaOuDireita() {
		Random r = new Random();
		return r.nextInt(2);
	}

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

					if (robot.SensorLuz(robot.S_2) < 500) {
						if (state != ESPERAR) {
							tempoEspera = gerarMensagem();
							pesos.resetPeso();
							Thread.sleep(tempoEspera);
						}
					}
					pesos.resetPeso();

				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					if (state != TERMINAR)
						esperar();
				}

				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					e1.printStackTrace();

				}
				break;

			}
		}
		robot.Parar(true);
	}
}
