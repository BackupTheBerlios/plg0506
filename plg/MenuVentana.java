
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import procesador.Procesador;
import maquinaP.MaquinaP;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * La clase <B>MenuVentana</B> contiene todos los elementos gráficos del compilador. Extiende de la clase JFrame, contiene JPanel,
 * JTextField, JTextPane y JButton. 
 * <P>La clase Lexico cuenta con los siguientes tipos de atributos:
 * <UL><LI><CODE>JPanel:</CODE> Para poder luego poner mas cosas dentro con diferentes Layaout.</LI>
 * <LI><CODE>JButton:</CODE> Botones para que el usuario pueda realizar las acciones que esten definidas en el evento.</LI>
 * <LI><CODE>JSplitPane:</CODE> Paneles que muestran una division por la mitad horizontal y vertical.</LI>
 * <LI><CODE>JTextField:</CODE> Campo para introducir una linea de texto, en este caso los ficheros del compilador.</LI>
 * <LI><CODE>JScrollPane:</CODE> Panel que contiene barras cuando son necesarias.</LI>
 * <LI><CODE>JTextPane:</CODE> Panel de texto, usado para mostrar texto.</LI>
 * <LI><CODE>JLabel:</CODE> Etiquetas para mostrar texto en los paneles.</LI>
 * <LI><CODE>File:</CODE> Ficheros usados para el analisis y para la ejecución.</LI>
 * </UL></P>
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín
 *
 */

public class MenuVentana extends JFrame{
	
	/*
	 * Atributos de la clase MenuVentana
	 */
	static final long serialVersionUID=1;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel21 = null;
	private JPanel jPanel22 = null;
	private JPanel jPanel23 = null;
	private JButton jButton2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel31 = null;
	private JPanel jPanel33 = null;
	private JPanel jPanel32 = null;
	private JPanel jPanel11 = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel11a = null;
	private JPanel jPanel11b = null;
	private JTextField jTextFieldC = null;
	private JButton jButtonEC = null;
	private File compilar;
	private JTextField jTextFieldE = null;
	private JButton jButtonEE = null;
	private File ejecutar;
	private JSplitPane jSplitPane1 = null;
	private JScrollPane jScrollPaneC = null;
	private JScrollPane jScrollPaneE = null;
	private JTextPane jTextPaneC = null;
	private JTextPane jTextPaneE = null;
	private JLabel jLabelC = null;
	private JLabel jLabelE = null;
	private JLabel jLabelEC = null;
	private JLabel jLabelEE = null;
	private JPanel jPanel4 = null;
	
	/**
	 * Constructor de la clase, que llama al constructor de JFrame
	 */
	public MenuVentana(){
		super();
	}
	
	/**
	 * Este método inicializa el JFrame que extiende. Le cambia el titulo, el tamaño, la visibilidad y otras propiedades.
	 */
	public void initialize() {
        this.setTitle("Practica Plg");
        this.setPreferredSize(new java.awt.Dimension(1024,680));
        this.setSize(new java.awt.Dimension(1024,680));
        this.setResizable(false);
        this.setContentPane(getJPanel());
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        	public void windowClosing(java.awt.event.WindowEvent e) {
        		cerrarVentana();
        	}
        });
        this.compilar=null;
        this.ejecutar=null;
	}

	/**
	 * Este método inicializa el atributo jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setSize(this.getSize());
			jPanel.setPreferredSize(this.getSize());
			jPanel.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.EAST);
			jPanel.add(getJPanel3(), java.awt.BorderLayout.WEST);
			jPanel.add(getJSplitPane1(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJPanel4(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * Este método inicializa el jButton que hace la compilación del programa. Si no hay un fichero que compilar mostrara
	 * un error.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			jButton.setText("Compilar");
			jButton.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 18));
			jButton.setToolTipText("Se compilara el archivo fuente con extension '.txt'");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_C);
			jButton.setActionCommand("Compilar");
			jButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			int h= this.jPanel3.getHeight()/3;
			int w= (this.jPanel3.getWidth()/3)*2;
			Dimension d= new Dimension(w,h);
			jButton.setSize(d);
			jButton.setPreferredSize(new java.awt.Dimension((this.jPanel3.getWidth()/3)*2,this.jPanel3.getHeight()/3));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					RandomAccessFile fuente;
					try {
						/*
						 * Tratamos de realizar todas las operaciones, si alguna falla y genera excepcion
						 * se recoge mas abajo.
						 */
						String rutacompilar = jTextFieldC.getText();
						if(rutacompilar != null){
							if (rutacompilar.endsWith("txt")){
								compilar = new File(rutacompilar);
								fuente = new RandomAccessFile(compilar,"r");
								Procesador p=new Procesador();
								p.procesa(fuente, compilar.getName());
								int i= compilar.getName().length();
								String fcod = new String(compilar.getName().substring( 0,i-3));
								fcod = fcod.concat("obj");
								ejecutar= new File(fcod);
								jTextPaneC.setText(mostrarFichero(ejecutar));
								jTextFieldE.setText(fcod);
							}
							else{
								JOptionPane.showMessageDialog(null,"Debe indicar el fichero fuente como parametro, con extension \".txt\"","Error",JOptionPane.ERROR_MESSAGE);
							}	
						}
						else{
							JOptionPane.showMessageDialog(null,"Debe indicar el fichero fuente como parametro, escribalo en el recuadro indicado y con extension \".txt\"","Error",JOptionPane.ERROR_MESSAGE);
						}
					} 
					catch (java.io.FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null,"Archivo no encontrado: " + compilar.getName(),"Error",JOptionPane.ERROR_MESSAGE);	
					}
				}
			});
		}
		return jButton;
	}

	
	/**
	 * Este método inicializa jPanel1. En este panel se encuentran los jLabel que inidican el "Archivo a compilar" y el "Archivo a 
	 * ejecutar"	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabelEC = new JLabel();
			jLabelEE = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setSize(new Dimension(this.jPanel.getWidth(),this.getHeight()/4));
			jPanel1.setPreferredSize(new Dimension(this.getWidth(),this.jPanel.getHeight()/4));
			jLabelEC.setSize(new Dimension(this.jPanel.getWidth()/2,this.jPanel.getHeight()/4));
			jLabelEC.setPreferredSize(new Dimension(this.jPanel.getWidth()/2,this.jPanel.getHeight()/4));
			jLabelEC.setText("             Archivo a Compilar");
			jLabelEE.setSize(new Dimension(this.jPanel.getWidth()/2,this.jPanel.getHeight()/4));
			jLabelEE.setPreferredSize(new Dimension(this.jPanel.getWidth()/2,this.jPanel.getHeight()/4));
			jLabelEE.setText("                 Archivo a Ejecutar");
			jPanel1.add(getJPanel11(), java.awt.BorderLayout.NORTH);
			jPanel1.add(jLabelEE, java.awt.BorderLayout.EAST);
			jPanel1.add(getJSplitPane(), java.awt.BorderLayout.SOUTH);
			jPanel1.add(jLabelEC, java.awt.BorderLayout.WEST);
		}
		return jPanel1;
	}

	/**
	 * Este método devuelve jPanel2. Lo inicializa y guarda sus componentes según el Layaout adecuado.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			int h= this.jPanel.getHeight()/4;
			int w= this.jPanel.getWidth()/3;
			Dimension d= new Dimension(w,h);
			jPanel2.setSize(d);
			jPanel2.setPreferredSize(d);
			jPanel2.add(getJPanel21(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJPanel22(), java.awt.BorderLayout.EAST);
			jPanel2.add(getJPanel23(), java.awt.BorderLayout.SOUTH);
			jPanel2.add(getJButton2(), java.awt.BorderLayout.WEST);
		}
		return jPanel2;
	}

	/**
	 * Este método devuelve el jPanel21. Lo inicializa y adecua su tamaño.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			jPanel21 = new JPanel();
			int h= this.jPanel2.getHeight()/3;
			int w= this.jPanel2.getWidth();
			Dimension d= new Dimension(w,h);
			jPanel21.setSize(d);
			jPanel21.setPreferredSize(d);
		}
		return jPanel21;
	}

	/**
	 * This method initializes jPanel22. Lo inicializa y adecua su tamaño	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel22() {
		if (jPanel22 == null) {
			jPanel22 = new JPanel();
			int h= this.jPanel2.getHeight()/3;
			int w= (this.jPanel2.getWidth()/3);
			Dimension d= new Dimension(w,h);
			jPanel22.setSize(d);
			jPanel22.setPreferredSize(d);
		}
		return jPanel22;
	}

	/**
	 * This method initializes jPanel23. Lo inicializa y guarda sus componentes según el Layaout adecuado.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel23() {
		if (jPanel23 == null) {
			jLabelE = new JLabel();
			jLabelE.setText("Resultado de la Ejecución");
			jPanel23 = new JPanel();
			int h= this.jPanel2.getHeight()/3;
			int w= this.jPanel2.getWidth();
			Dimension d= new Dimension(w,h);
			jPanel23.setSize(d);
			jPanel23.setPreferredSize(d);
			Dimension d1 = new Dimension(w,h+10);
			jLabelE.setSize(d1);
			jLabelE.setPreferredSize(d);
			jPanel23.add(jLabelE, null);
		}
		return jPanel23;
	}

	/**
	 * Este método inicializa y devuelve jButton1. El boton correspondiente a la acción de 
	 * ejecutar, sino hay un fichero adecuado mostrara un error.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			int h= this.jPanel2.getHeight()/3;
			int w= (this.jPanel2.getWidth()/3)*2;
			Dimension d= new Dimension(w,h);
			jButton2.setSize(d);
			jButton2.setPreferredSize(new Dimension((this.jPanel2.getWidth()/3)*2,h));
			jButton2.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 18));
			jButton2.setActionCommand("&Ejecutar");
			jButton2.setMnemonic(java.awt.event.KeyEvent.VK_E);
			jButton2.setToolTipText("Se ejecutara un fichero con extension '.obj'");
			jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			jButton2.setText("Ejecutar");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					/*
					 * Tratamos de realizar todas las operaciones, si alguna falla y genera excepcion
					 * se recoge mas abajo.
					 */
					String rutaejecutar=jTextFieldE.getText();
					if(rutaejecutar!=null){
						if (ejecutar==null){
							ejecutar= new File(rutaejecutar);
						}
						else{
							if(rutaejecutar.compareTo(ejecutar.getName())!=0){
								ejecutar= new File(rutaejecutar);
							}
						}
						if (ejecutar.getName().endsWith("obj")){
							try{
								MaquinaP maquina= new MaquinaP(ejecutar.getName());
								maquina.ejecuta();
								String pasos = maquina.getPasos();
								String resultado = maquina.resultadoMem();
								jTextPaneE.setText(pasos+"\n"+resultado);
							}
							catch (Exception e1) {
								JOptionPane.showMessageDialog(null,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);	
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"Debe indicar el fichero objeto, con extensi?n .obj","Error",JOptionPane.ERROR_MESSAGE);
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Debe indicar un fichero objeto a ejecutar como par?metro.","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * Este método devuelve jPanel3. Lo inicializa y guarda sus componentes según el Layaout adecuado.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			int h= this.jPanel.getHeight()/4;
			int w= this.jPanel.getWidth()/3;
			Dimension d= new Dimension(w,h);
			jPanel3.setSize(d);
			jPanel3.setPreferredSize(d);
			jPanel3.add(getJPanel31(), java.awt.BorderLayout.NORTH);
			jPanel3.add(getJButton(), java.awt.BorderLayout.EAST);
			jPanel3.add(getJPanel33(), java.awt.BorderLayout.SOUTH);
			jPanel3.add(getJPanel32(), java.awt.BorderLayout.WEST);
		}
		return jPanel3;
	}

	/**
	 * Este metodo devuelve jPanel31. Lo inicializa y adecua su tamaño.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel31() {
		if (jPanel31 == null) {
			jPanel31 = new JPanel();
			int h= this.jPanel3.getHeight()/3;
			int w= this.jPanel3.getWidth();
			Dimension d= new Dimension(w,h);
			jPanel31.setSize(d);
			jPanel31.setPreferredSize(d);
		}
		return jPanel31;
	}

	/**
	 * Este método devuelve jPanel33. Lo inicializa y guarda sus componentes según el Layaout adecuado.
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel33() {
		if (jPanel33 == null) {
			jPanel33 = new JPanel();
			int h= this.jPanel3.getHeight()/3;
			int w= this.jPanel3.getWidth();
			Dimension d= new Dimension(w,h);
			jPanel33.setSize(d);
			jPanel33.setPreferredSize(d);
			jLabelC = new JLabel();
			jLabelC.setText("          Resultado de la Compilación");
			jLabelC.setSize(d);
			jLabelC.setPreferredSize(d);
			jPanel33.add(jLabelC, null);
		}
		return jPanel33;
	}

	/**
	 * Este método devuelve jPanel32. Lo inicializa y guarda sus componentes según el Layaout adecuado.
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel32() {
		if (jPanel32 == null) {
			jPanel32 = new JPanel();
			int h= this.jPanel3.getHeight()/3;
			int w= (this.jPanel3.getWidth()/3);
			Dimension d= new Dimension(w,h);
			jPanel32.setSize(d);
			jPanel32.setPreferredSize(d);
		}
		return jPanel32;
	}

	/**
	 * Este método devuelve jPanel11. Lo inicializa y adecua el tamaño.
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			int h= (this.jPanel1.getHeight()/3)+(this.jPanel1.getHeight()/3)/2;
			int w= this.jPanel1.getWidth();
			Dimension d= new Dimension(w,h);
			jPanel11.setPreferredSize(new java.awt.Dimension(d));
			jPanel11.setSize(d);
		}
		return jPanel11;
	}

	/**
	 * Este método inicializa el jSplitPane. 	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			int h= (this.jPanel1.getHeight()/3)/2;
			int w= this.jPanel1.getWidth();
			jSplitPane.setOrientation(javax.swing.JSplitPane.HORIZONTAL_SPLIT);
			jSplitPane.setDividerSize(10);
			jSplitPane.setRightComponent(getJPanel11b());
			jSplitPane.setLeftComponent(getJPanel11a());
			int s = (w/2)-5;
			jSplitPane.setDividerLocation(s);
			Dimension d= new Dimension(w,h);
			jSplitPane.setSize(d);
			jSplitPane.setPreferredSize(new java.awt.Dimension(1024,28));
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel11a	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11a() {
		if (jPanel11a == null) {
			jPanel11a = new JPanel();
			jPanel11a.setLayout(new BorderLayout());
			int h=this.jSplitPane.getHeight();
			int w=this.jSplitPane.getWidth()/2;
			Dimension d = new Dimension(w,h);
			jPanel11a.setSize(d);
			jPanel11a.setPreferredSize(new java.awt.Dimension(512,28));
			jPanel11a.add(getJTextFieldC(), java.awt.BorderLayout.CENTER);
			jPanel11a.add(getJButtonEC(), java.awt.BorderLayout.EAST);
		}
		return jPanel11a;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel11b() {
		if (jPanel11b == null) {
			jPanel11b = new JPanel();
			jPanel11b.setLayout(new BorderLayout());
			int h=this.jSplitPane.getHeight();
			int w=this.jSplitPane.getWidth()/2;
			Dimension d = new Dimension(w,h);
			jPanel11b.setSize(d);
			jPanel11b.setPreferredSize(new java.awt.Dimension(512,28));
			jPanel11b.add(getJTextFieldE(), java.awt.BorderLayout.CENTER);
			jPanel11b.add(getJButtonEE(), java.awt.BorderLayout.EAST);
		}
		return jPanel11b;
	}
		
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldC() {
		if (jTextFieldC == null) {
			jTextFieldC = new JTextField();
			jTextFieldC.setToolTipText("Escriba la ruta del fichero a compilar...y pulse Enter");
			int h = this.jPanel11a.getHeight();
			int w = (this.jPanel11a.getWidth()/3)*2;
			Dimension d = new Dimension(w,h);
			jTextFieldC.setSize(d);
			jTextFieldC.setPreferredSize(new java.awt.Dimension(341,28));
			jTextFieldC.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (compilar != null && jTextFieldC.getText()== null){
						jTextFieldC.setText(compilar.getAbsolutePath());
					}
					else if(compilar == null && jTextFieldC.getText()!= null){
						compilar = new File(jTextFieldC.getText());
					}
				}
			});
		}
		return jTextFieldC;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonEC() {
		if (jButtonEC == null) {
			jButtonEC = new JButton();
			jButtonEC.setText("Examinar");
			jButtonEC.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
			jButtonEC.setToolTipText("Para buscar el archivo");
			jButtonEC.setActionCommand("ExaminarEC");
			int h = this.jPanel11a.getHeight();
			int w = this.jPanel11a.getWidth()/3;
			Dimension d = new Dimension(w,h);
			jButtonEC.setSize(d);
			jButtonEC.setPreferredSize(new java.awt.Dimension(171,28));
			jButtonEC.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cargaFichero(jTextFieldC, "Compilar");
				}
			});
		}
		return jButtonEC;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldE() {
		if (jTextFieldE == null) {
			jTextFieldE = new JTextField();
			jTextFieldE.setToolTipText("Escriba la ruta del fichero a ejecutar...");
			int h = this.jPanel11b.getHeight();
			int w = (this.jPanel11b.getWidth()/3)*2;
			Dimension d = new Dimension(w,h);
			jTextFieldE.setSize(d);
			jTextFieldE.setPreferredSize(new java.awt.Dimension(341,28));
			jTextFieldE.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (ejecutar != null && jTextFieldE.getText()== null){
						jTextFieldE.setText(ejecutar.getAbsolutePath());
					}
					else if(ejecutar == null && jTextFieldE.getText()!= null){
						ejecutar = new File(jTextFieldE.getText());
					}
				}
			});
		}
		return jTextFieldE;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonEE() {
		if (jButtonEE == null) {
			jButtonEE = new JButton();
			jButtonEE.setText("Examinar");
			jButtonEE.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
			jButtonEE.setToolTipText("Para buscar el archivo");
			jButtonEE.setActionCommand("ExaminarEE");
			int h = this.jPanel11b.getHeight();
			int w = this.jPanel11b.getWidth()/3;
			Dimension d = new Dimension(w,h);
			jButtonEE.setSize(d);
			jButtonEE.setPreferredSize(new java.awt.Dimension(171,28));
			jButtonEE.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cargaFichero(jTextFieldE, "Ejecutar");
				}
			});
		}
		return jButtonEE;
	}

	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			int h=(this.jPanel.getSize().height/4)*2;
			int w=this.jPanel.getSize().width;
			Dimension d= new Dimension(w,h);
			jSplitPane1.setSize(d);
			jSplitPane1.setRightComponent(getJScrollPaneE());
			jSplitPane1.setLeftComponent(getJScrollPaneC());
			jSplitPane1.setPreferredSize(d);
			jSplitPane1.setDividerSize(10);
			int s = (w/2);
			jSplitPane1.setDividerLocation(s);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneC() {
		if (jScrollPaneC == null) {
			jScrollPaneC = new JScrollPane();
			jScrollPaneC.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jScrollPaneC.setViewportView(getJTextPaneC());
			int h=(this.jPanel.getSize().height/4)*2;
			int w=this.jPanel.getSize().width/2;
			Dimension d= new Dimension(w,h);
			jScrollPaneC.setSize(d);
			jScrollPaneC.setPreferredSize(d);
		}
		return jScrollPaneC;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneE() {
		if (jScrollPaneE == null) {
			jScrollPaneE = new JScrollPane();
			jScrollPaneE.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
			jScrollPaneE.setViewportView(getJTextPaneE());
			int h=(this.jPanel.getSize().height/4)*2;
			int w=this.jPanel.getSize().width/2;
			Dimension d= new Dimension(w,h);
			jScrollPaneE.setSize(d);
			jScrollPaneE.setPreferredSize(d);
		}
		return jScrollPaneE;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneC() {
		if (jTextPaneC == null) {
			jTextPaneC = new JTextPane();
			jTextPaneC.setSize(this.jScrollPaneC.getSize());
			jTextPaneC.setEditable(false);
			jTextPaneC.setToolTipText("Se muestra el resultado de compilar el archivo");
			jTextPaneC.setPreferredSize(this.jScrollPaneC.getSize());
		}
		return jTextPaneC;
	}

	/**
	 * This method initializes jTextPane1	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneE() {
		if (jTextPaneE == null) {
			jTextPaneE = new JTextPane();
			jTextPaneE.setSize(this.jScrollPaneE.getSize());
			jTextPaneE.setEditable(false);
			jTextPaneE.setToolTipText("Se muestra el resultado de ejecutar el archivo");
			jTextPaneE.setText("");
			jTextPaneE.setPreferredSize(this.jScrollPaneE.getSize());
		}
		return jTextPaneE;
	}
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	private String mostrarFichero(File f){
		String s="";
		BufferedReader entrada = null;
	    try {
			FileReader f1= new FileReader(f);
			entrada = new BufferedReader(f1);
			String linea = null;
			while ((linea = entrada.readLine()) != null){
				s= s.concat(linea.concat(" \n"));
			}
	    }
	    catch (FileNotFoundException ex) {
	    	JOptionPane.showMessageDialog(null,"Archivo no encontrado: " + ex.getMessage()+
	    			"\n","Error",JOptionPane.ERROR_MESSAGE);  
	    	ex.printStackTrace();
		}
		catch (IOException ex){
			JOptionPane.showMessageDialog(null,"Archivo no encontrado: " + ex.getMessage()+
    			"\n","Error",JOptionPane.ERROR_MESSAGE);  
		    ex.printStackTrace();
		}
		return s;
	}
	
	/**
	 * This method initializes jPanel41	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			int h= this.jPanel.getHeight()/4;
			int w= this.jPanel.getWidth()/3;
			Dimension d= new Dimension(w,h);
			jPanel4.setSize(d);
			jPanel4.setPreferredSize(d);
		}
		return jPanel4;
	}
	
	/**
	 * 
	 * @param campo
	 * @param objetivo
	 */
	private void cargaFichero(JTextField campo, String objetivo){
		JFileChooser examina=new JFileChooser();
		examina.setDialogTitle("Cargar fichero para" + objetivo.toLowerCase() );
		if (examina.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			String ruta = examina.getSelectedFile().getAbsolutePath();
			campo.setText(ruta);
		}
	}
	
	/**
	 * Este método sirve para cerrar la ventana. Llamando al método adecuado de JFrame.
	 */
	private void cerrarVentana(){
		this.dispose();
	}
}
