package nGui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Vector;

import javax.swing.*;   

import java.awt.*;
import java.awt.event.*;
import procesador.Procesador;
import procesador.Codigo;
import maquinaP.MaquinaP;

/**
 * Clase que contiene la interfaz grafica del compilador-procesador.
 * 
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
 *
 */
public class GUI {
	
    protected JTextArea texto0, texto1, textosrc;
    protected File file;
    protected Codigo codigo;
    protected MaquinaP maquinap;
    protected Procesador procesador = new Procesador();

    /**
     * Constructor de la clase. No necesita parametros.
     *
     */
    public GUI() {
    	super();
    }
    
    private String readFileIntoString(File f) {
    	FileReader fr = null;
    	String str = new String();
    	int c;
    	
    	try {
			fr = new FileReader(f);
			while ((c = fr.read()) != -1)
				str += (char)c;
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
    }

    public Component createComponents() {
    	textosrc = new JTextArea("Fuente");
    	JScrollPane srcScrollPane = new JScrollPane(textosrc);
    	srcScrollPane.setPreferredSize(new Dimension(250, 250));
    	
        texto0 = new JTextArea("Codigo procesado");
        JScrollPane area0ScrollPane = new JScrollPane(texto0);
        area0ScrollPane.setPreferredSize(new Dimension(250, 250));

        texto1 = new JTextArea("Estado de la maquina P");
        JScrollPane area1ScrollPane = new JScrollPane(texto1);
        area1ScrollPane.setPreferredSize(new Dimension(250, 250));

        Action button0Action = new Button0Action("Procesar", null, "Descripcion", null);

        JButton button0 = new JButton(button0Action);

        Action button1Action = new Button1Action("Ejecutar", null, "Descripcion", null);

        JButton button1 = new JButton(button1Action);
        
        Action button2Action = new Button2Action("Abrir archivo", null, "Abrir archivo", null);
        
        JButton button2 = new JButton(button2Action);

        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        pane.add(button2, c);
        
        c.gridx = 1;
        c.gridy = 0;
        pane.add(button0, c);

        c.gridx = 2;
        c.gridy = 0;
        pane.add(button1, c);
        
        c.gridx = 0;
        c.gridy = 1;
        pane.add(srcScrollPane, c);
        
        c.gridx = 1;
        c.gridy = 1;
        pane.add(area0ScrollPane, c);

        c.gridx = 2;
        c.gridy = 1;
        pane.add(area1ScrollPane, c);



        pane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        return pane;
    }

    public class Button0Action extends AbstractAction {
        public Button0Action(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public void actionPerformed(ActionEvent e) {
            if (file == null) {
            	texto0.setText("\n### ERROR: selecciona un archivo ###");
            } else {
            	texto0.setText(null);
            	procesador.procesa(file);
            	codigo = procesador.getCod();
            	texto0.append(codigo.getString());
            }
        }
    }

    public class Button1Action extends AbstractAction {
        public Button1Action(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public void actionPerformed(ActionEvent e) {
        	 if (file == null) {
             	texto1.setText("\n# ERROR: procese primero un archivo #");
             } 
        	 else {
	        	texto1.setText(null);
	        	//maquinap = new MaquinaP(file);
	        	//Codigo c = procesador.getCod();
	        	maquinap.setProg((Vector<String>)codigo.getCod());
	        	try {
	        		texto1.append(maquinap.ejecuta() + maquinap.resultadoMem());
	        	} catch(Exception ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
             }
        }
    }
    
    public class Button2Action extends AbstractAction {
        public Button2Action(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }
        public void actionPerformed(ActionEvent e) {
        	final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                textosrc.setText(readFileIntoString(file));
            }
        }
    }

    private void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Compilador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUI app = new GUI();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public void main() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
