package nGui;

import java.io.File;
import javax.swing.*;   
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;
import procesador.Procesador;
import maquinaP.MaquinaP;

public class GUI {
    protected JTextArea texto0, texto1;
    protected File file;
    protected MaquinaP maquinap;
    protected Procesador procesador = new Procesador();

    
    public GUI() {
    	super();
    }

    public Component createComponents() {
        texto0 = new JTextArea("Codigo procesado");
        JScrollPane area0ScrollPane = new JScrollPane(texto0);
        area0ScrollPane.setPreferredSize(new Dimension(250, 250));

        texto1 = new JTextArea("Estado de la maquina P");
        JScrollPane area1ScrollPane = new JScrollPane(texto1);
        area1ScrollPane.setPreferredSize(new Dimension(250, 250));

        Action button0Action = new Button0Action("Procesar archivo", null, "Descripcion", null);

        JButton button0 = new JButton(button0Action);

        Action button1Action = new Button1Action("Cargar en maquina P", null, "Descripcion", null);

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
        c.gridwidth = 2;
        pane.add(button2, c);
        c.gridwidth = 1;
        
        c.gridx = 0;
        c.gridy = 1;
        pane.add(area0ScrollPane, c);

        c.gridx = 1;
        c.gridy = 1;
        pane.add(area1ScrollPane, c);

        c.gridx = 0;
        c.gridy = 2;
        pane.add(button0, c);

        c.gridx = 1;
        c.gridy = 2;
        pane.add(button1, c);

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
            //texto0.append("Boton 0\n");
            if (file == null)
            	texto0.append("### ERROR: selecciona un archivo ###");
            else {
            	procesador.procesa(file);
            	texto0.append(procesador.getCodigo());
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
        	try{
        	String s = maquinap.ejecuta();
        	s = s.concat(maquinap.resultadoMem());
            texto1.append(s);
        	}
        	catch(Exception ex) {
				JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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
