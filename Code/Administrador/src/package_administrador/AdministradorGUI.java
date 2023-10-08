package package_administrador;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import robot.RobotLegoEV3;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdministradorGUI extends JFrame{

	private JFrame frame;
	private JTextField textNome;
	private JTextField textDist;
	private JTextField textAng;
	private JTextField textRaio;
	private JTextArea textArea;
	
	protected JCheckBox chckbxVaguear;
	protected JCheckBox chckbxEvitar;
	protected JCheckBox chckbxFugir;
	
	private String nome;
	private int dist;
	private int raio;
	private int angulo;
	
	protected boolean onOff;
	private Administrador admin;

	private RobotLegoEV3 robot;

	//construtor da GUI
	public AdministradorGUI() {
		initialize();
	}

	public boolean isOnOff() {
	    return onOff;
	}

	public void setOnOff(boolean onOff) {
	    this.onOff = onOff;
	}
	
	public void setAdministrador(Administrador admin) {
		this.admin = admin;
	}

	public AdministradorGUI(Administrador admin) {
	    this();
	    this.setAdministrador(admin);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					admin.acabar();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Robot desligado...");
				
				if(robot != null) {
					robot.Parar(true);
					try {
						Thread.sleep(250);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					robot.CloseEV3();
				}
				else {
					System.exit(0);
				}
				
			}
		});
		frame.setBounds(100, 100, 507, 395);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 211, 432, 120);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		
		JLabel lblName = new JLabel("Nome");
		lblName.setBounds(26, 61, 46, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblDist = new JLabel("Distância");
		lblDist.setBounds(26, 86, 46, 14);
		frame.getContentPane().add(lblDist);
		
		JLabel lblAng = new JLabel("Ângulo");
		lblAng.setBounds(26, 111, 46, 14);
		frame.getContentPane().add(lblAng);
		
		JLabel lblRaio = new JLabel("Raio");
		lblRaio.setBounds(26, 136, 46, 14);
		frame.getContentPane().add(lblRaio);
		
		
		
		textNome = new JTextField();
		textNome.addActionListener(new ActionListener() {
			//definir nome
			public void actionPerformed(ActionEvent e) {
				nome = textNome.getText();
				textArea.append("Nome: " + nome + "\n");
			}
		});
		textNome.setBounds(93, 58, 86, 20);
		frame.getContentPane().add(textNome);
		textNome.setColumns(10);
		
		textDist = new JTextField();
		textDist.addActionListener(new ActionListener() {
			//definir dist
			public void actionPerformed(ActionEvent e) {
			dist = Integer.parseInt(textDist.getText());
			textArea.append("Distância: " + dist + " cm\n");
			}
		});
		textDist.setBounds(93, 83, 86, 20);
		textDist.setColumns(10);
		frame.getContentPane().add(textDist);
		
		textAng = new JTextField();
		textAng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				angulo = Integer.parseInt(textAng.getText());
				textArea.append("Angulo: " + angulo + " graus\n");
			}
		});
		textAng.setBounds(93, 108, 86, 20);
		textAng.setColumns(10);
		frame.getContentPane().add(textAng);
		
		textRaio = new JTextField();
		textRaio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				raio = Integer.parseInt(textRaio.getText());
				textArea.append("Raio: " + raio + " cm\n");
			}
		});
		textRaio.setBounds(93, 133, 86, 20);
		textRaio.setColumns(10);
		frame.getContentPane().add(textRaio);
		
		JButton btnFrente = new JButton("↑");
		btnFrente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.append("Reta de: " + dist + " cm\n");
				robot.Reta(dist);
				robot.Parar(false);
			}
		});
		btnFrente.setBounds(313, 37, 68, 44);
		frame.getContentPane().add(btnFrente);
		
		JButton btnStop = new JButton("P");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textArea.append("O Robot parou\n");
				robot.Parar(true);
				
			}
		});
		btnStop.setBounds(313, 87, 68, 44);
		frame.getContentPane().add(btnStop);
		
		JButton btnRetaguarda = new JButton("↓");
		btnRetaguarda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append("Retaguarda de: " + (-dist) + " cm\n");
				robot.Reta(-dist);
				robot.Parar(false);
			}
		});
		btnRetaguarda.setBounds(313, 142, 68, 44);
		frame.getContentPane().add(btnRetaguarda);
		
		JButton btnDir = new JButton("→");
		btnDir.addActionListener(new ActionListener() {
			//curvar direita
			public void actionPerformed(ActionEvent e) {
				textArea.append("Curva à direita com raio de " + raio + " cm e " + angulo + " graus\n");
				robot.CurvarDireita(raio, angulo);
				robot.Parar(false);
			}
		});
		btnDir.setBounds(389, 87, 68, 44);
		frame.getContentPane().add(btnDir);
		
		JButton btnEsq = new JButton("←");
		btnEsq.addActionListener(new ActionListener() {
			//curvar esquerda
			public void actionPerformed(ActionEvent e) {

				textArea.append("Curva à esquerda com raio de " + raio + " cm e " + angulo + " graus\n");
				robot.CurvarEsquerda(raio, angulo);
				robot.Parar(false);
			}
		});
		btnEsq.setBounds(235, 87, 68, 44);
		frame.getContentPane().add(btnEsq);
		
		JRadioButton rdbtnOnOff = new JRadioButton("On/Off");
		rdbtnOnOff.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				onOff =! onOff;
			    if(onOff) {
			    	textArea.append(nome + " ligado\n");
			    	robot = admin.getRobot();
			    	System.out.println(robot);
				    robot.OpenEV3(nome);
				    onOff= true;
			    }
			    else {
			    	textArea.append(nome + " desligado\n");
			        robot.CloseEV3();
			        onOff = false;	
			    }
			}
		});
		rdbtnOnOff.setBounds(24, 23, 68, 23);
		frame.getContentPane().add(rdbtnOnOff);
		
		//Vaguear
		chckbxVaguear = new JCheckBox("Vaguear");
		chckbxVaguear.setBounds(93, 23, 68, 23);
		frame.getContentPane().add(chckbxVaguear);
		
		//Evitar
		chckbxEvitar = new JCheckBox("Evitar");
		chckbxEvitar.setBounds(163, 23, 59, 23);
		frame.getContentPane().add(chckbxEvitar);
		
		//Fugir
		chckbxFugir = new JCheckBox("Fugir");
		chckbxFugir.setBounds(224, 23, 59, 23);
		frame.getContentPane().add(chckbxFugir);
				
		frame.setVisible(true);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JCheckBox getChckbxVaguear() {
		return chckbxVaguear;
	}

	public JCheckBox getChckbxEvitar() {
		return chckbxEvitar;
	}
	
	public JCheckBox getChckbxFugir() {
		return chckbxFugir;
	}	
}
