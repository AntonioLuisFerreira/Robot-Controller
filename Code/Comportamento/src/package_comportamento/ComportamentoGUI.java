package package_comportamento;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ComportamentoGUI {

	private JFrame frmComportamento;

	private JTextArea textAreaComportamento;

	public ComportamentoGUI(String titulo, int local) {
		initialize(titulo, local);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String titulo, int local) {

		frmComportamento = new JFrame();
		frmComportamento.setTitle(titulo);
		frmComportamento.setBounds(local, 100, 450, 300);
		frmComportamento.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmComportamento.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 239);
		frmComportamento.getContentPane().add(scrollPane);

		textAreaComportamento = new JTextArea();
		scrollPane.setViewportView(textAreaComportamento);
		frmComportamento.setVisible(true);
	}

	public JTextArea getTextAreaComportamento() {
		return textAreaComportamento;
	}

	public JFrame getFrmComportamento() {
		return frmComportamento;
	}

	public void dispose() {
		frmComportamento.setVisible(false);
		frmComportamento.dispose();
	}

}
