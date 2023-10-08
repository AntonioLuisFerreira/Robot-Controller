package package_administrador;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Semaphore;

import package_evitar.Evitar;
import package_fugir.Fugir;
import package_pesos.Pesos;
import package_vaguear.Vaguear;
import robot.RobotLegoEV3;

public class Administrador {
	private AdministradorGUI gui;

	private final int ESPERAR = 0, TRABALHAR = 1;
	private int ESTADO;
	
	private RobotLegoEV3 robot;
	private Pesos pesos;
	private Vaguear vag;
	private Evitar evi;
	private Fugir fug;

	private boolean vagFlag = false;
	private boolean eviFlag = false;
	private boolean fugFlag = false;
	
	private final int PRIORIDADE_VAGUEAR = 1;
	private final int PRIORIDADE_EVITAR = 2;
	private final int PRIORIDADE_FUGIR = 3;

	public Administrador() {
		robot = new RobotLegoEV3();
		pesos = new Pesos();
		gui = new AdministradorGUI(this);
		ESTADO = ESPERAR;
		init();
	}

	public void init() {
		vag = new Vaguear(getRobot(), pesos, PRIORIDADE_VAGUEAR);
		evi = new Evitar(getRobot(), pesos, PRIORIDADE_FUGIR);
		fug = new Fugir(getRobot(), pesos, PRIORIDADE_EVITAR);

		vag.start();
		evi.start();
		fug.start();
	}
	public void run() throws InterruptedException, InvocationTargetException {

		for (;;) {
			switch (ESTADO) {
			case ESPERAR:
				if (gui.onOff) {
					ESTADO = TRABALHAR;
				}
				break;

			case TRABALHAR:
				if (!gui.onOff) {
					ESTADO = ESPERAR;
				}

				// Vaguear
				if (gui.getChckbxVaguear().isSelected() && !vagFlag) {
					vag.trabalha();
					vagFlag = true;
				}
				if (!gui.getChckbxVaguear().isSelected() && vagFlag) {
					vag.esperar();
					vagFlag = false;
				}

				// Evitar
				if (gui.getChckbxEvitar().isSelected() && !eviFlag) {
					evi.trabalha();
					eviFlag = true;
				}

				if (!gui.getChckbxEvitar().isSelected() && eviFlag) {
					evi.esperar();
					eviFlag = false;
				}

				// Fugir
				if (gui.getChckbxFugir().isSelected() && !fugFlag) {
					fug.trabalha();
					fugFlag = true;

				}

				if (!gui.getChckbxFugir().isSelected() && fugFlag) {
					fug.esperar();
					fugFlag = false;
				}

				Thread.sleep(1);
				break;

			}
		}
	}

	public void acabar() throws InterruptedException {

		vag.terminar();
		vag.getGui().dispose();
		vag.join();
		
		evi.terminar();
		evi.getGui().dispose();
		evi.join();
		
		fug.terminar();
		fug.getGui().dispose();
		fug.join();
	}

	public RobotLegoEV3 getRobot() {
		return robot;
	}

	// main da aplicação
	public static void main(String[] args) throws InterruptedException, InvocationTargetException {
		Administrador re = new Administrador();
		re.run();
	}

}