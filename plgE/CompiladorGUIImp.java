import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;


//----------------------------------------------------------------------------
/** Implementacion del interfaz que usamos como parte grafica de nuestro sistema */
public class CompiladorGUIImp extends JFrame
{
  /*Referencia al compilador padre.*/
  Main padre;
  /*Atributos de control*/
  boolean modificado = false;
  boolean compilado = false;
  boolean ejecutado = false;
  String ruta;
  /*Máquina P*/
  MaquinaP machine;
  /*Atributos Gráficos*/
  JMenuBar Barra = new JMenuBar();
  JMenu menuArchivo = new JMenu();
  JMenuItem menuNuevo = new JMenuItem();
  JMenuItem menuAbrir = new JMenuItem();
  JMenuItem menuGuardar = new JMenuItem();
  JMenuItem menuGuardarComo = new JMenuItem();
  JMenuItem menuSalir = new JMenuItem();
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  JScrollPane ScrollTexto = new JScrollPane();
  JScrollPane ScrollErrores = new JScrollPane();
  JButton CompilarBut = new JButton();
  JLabel EtiqTabla = new JLabel();
  JLabel EtiqErrores = new JLabel();
  JEditorPane Errores = new JEditorPane();
  Border border1;
  TitledBorder titledBorder4;
  TitledBorder titledBorder5;
  private JEditorPane Texto = new JEditorPane();
  JLabel EtiqPrograma = new JLabel();
  private jTabla tabla;
  TitledBorder titledBorder6;
  GridBagLayout layoutTabla = new GridBagLayout();
  private JButton EjecutarBut = new JButton();
  JSplitPane PanelPrincipal = new JSplitPane();
  JSplitPane PanelTexto = new JSplitPane();
  JSplitPane PanelResto1 = new JSplitPane();
  Box Botonera;
  Box Visualizador = Box.createHorizontalBox();
  JSplitPane V2 = new JSplitPane();
  JSplitPane V3 = new JSplitPane();

  //----------------------------------------------------------------------------
  /** Constructor de la clase
   * @param c : Compilador padre del que cogemos informacion */
  public CompiladorGUIImp(Main c)
  {
    try
    {
      padre = c;
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  //----------------------------------------------------------------------------
  /** Metodo de inicio de la parte grafica -- JBuilder --
   * @throws Exception*/
  private void jbInit() throws Exception
  {
    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,new Color(225, 252, 255),new Color(158, 177, 207),new Color(53, 60, 70),new Color(77, 86, 101)),BorderFactory.createEmptyBorder(0,0,4,0));
    titledBorder4 = new TitledBorder("");
    titledBorder5 = new TitledBorder("");
    titledBorder6 = new TitledBorder("");
    this.setContentPane(PanelPrincipal);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setForeground(UIManager.getColor("TextField.selectionBackground"));
    this.setResizable(false);
    this.setResizable(false);
    this.setState(Frame.NORMAL);
    this.setTitle("Compilador");
    this.getContentPane().setLayout(null);
    menuArchivo.setBackground(SystemColor.activeCaption);
    menuArchivo.setForeground(Color.white);
    menuArchivo.setText("Archivo");
    menuNuevo.setBackground(SystemColor.activeCaption);
    menuNuevo.setForeground(Color.white);
    menuNuevo.setText("Nuevo");
    menuNuevo.addActionListener(new CompiladorGUIImp_menuNuevo_actionAdapter(this));
    menuAbrir.setBackground(SystemColor.activeCaption);
    menuAbrir.setForeground(Color.white);
    menuAbrir.setText("Abrir");
    menuAbrir.setArmed(false);
    menuAbrir.addActionListener(new CompiladorGUIImp_menuAbrir_actionAdapter(this));
    menuGuardar.setBackground(SystemColor.activeCaption);
    menuGuardar.setEnabled(false);
    menuGuardar.setForeground(Color.white);
    menuGuardar.setText("Guardar");
    menuGuardar.addActionListener(new CompiladorGUIImp_menuGuardar_actionAdapter(this));
    menuGuardarComo.setBackground(SystemColor.activeCaption);
    menuGuardarComo.setForeground(Color.white);
    menuGuardarComo.setText("Guardar Como");
    menuGuardarComo.addActionListener(new CompiladorGUIImp_menuGuardarComo_actionAdapter(this));
    menuSalir.setBackground(SystemColor.activeCaption);
    menuSalir.setForeground(Color.white);
    menuSalir.setActionCommand("");
    menuSalir.setText("Salir");
    menuSalir.addActionListener(new CompiladorGUIImp_menuSalir_actionAdapter(this));
    ScrollTexto.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    ScrollTexto.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    ScrollTexto.getViewport().setBackground(new Color(118, 131, 170));
    ScrollTexto.setForeground(Color.black);
    ScrollTexto.setBorder(BorderFactory.createRaisedBevelBorder());
    ScrollTexto.setMaximumSize(new Dimension(200, 47));
    ScrollTexto.setMinimumSize(new Dimension(200, 47));
    ScrollErrores.getViewport().setBackground(Color.white);
    ScrollErrores.setEnabled(true);
    ScrollErrores.setOpaque(true);
    CompilarBut.setBackground(Color.white);
    CompilarBut.setFont(new java.awt.Font("Dialog", 1, 14));
    CompilarBut.setForeground(SystemColor.activeCaption);
    CompilarBut.setActionCommand("Compilar");
    CompilarBut.setText("Compilar");
    CompilarBut.addActionListener(new CompiladorGUIImp_CompilarBut_actionAdapter(this));
    EtiqTabla.setFont(new java.awt.Font("Dialog", 1, 15));
    EtiqTabla.setForeground(Color.white);
    EtiqTabla.setHorizontalAlignment(SwingConstants.CENTER);
    EtiqTabla.setText("Resultado de ejecución");
    EtiqErrores.setEnabled(true);
    EtiqErrores.setFont(new java.awt.Font("Dialog", 1, 15));
    EtiqErrores.setForeground(Color.white);
    EtiqErrores.setBorder(null);
    EtiqErrores.setOpaque(false);
    EtiqErrores.setHorizontalAlignment(SwingConstants.CENTER);
    EtiqErrores.setLabelFor(ScrollErrores);
    EtiqErrores.setText("Errores");
    Errores.setEnabled(true);
    Errores.setFont(new java.awt.Font("Dialog", 0, 11));
    Errores.setForeground(Color.black);
    Errores.setMaximumSize(new Dimension(2147483647, 2147483647));
    Errores.setOpaque(true);
    Errores.setToolTipText("Errores hallados en la compilación.");
    Errores.setEditable(false);
    Errores.setText("");
    Barra.setBackground(SystemColor.activeCaption);
    Barra.setForeground(Color.white);
    Barra.setBorder(BorderFactory.createLoweredBevelBorder());
    Barra.setDebugGraphicsOptions(0);
    Barra.setBorderPainted(true);
    Texto.setBackground(new Color(223, 223, 236));
    Texto.setFont(new java.awt.Font("Dialog", 0, 15));
    Texto.setSelectionColor(new Color(235, 228, 118));
    EtiqPrograma.setFont(new java.awt.Font("Serif", 3, 20));
    EtiqPrograma.setForeground(Color.white);
    EtiqPrograma.setHorizontalAlignment(SwingConstants.LEFT);
    EtiqPrograma.setHorizontalTextPosition(SwingConstants.LEADING);
    EtiqPrograma.setText("");
    EtiqPrograma.setVerticalAlignment(SwingConstants.TOP);
    EjecutarBut.setText("Ejecutar");
    EjecutarBut.addActionListener(new CompiladorGUIImp_EjecutarBut_actionAdapter(this));
    EjecutarBut.setActionCommand("Ejecutar");
    EjecutarBut.setMnemonic('0');
    EjecutarBut.setFont(new java.awt.Font("Dialog", 1, 14));
    EjecutarBut.setForeground(SystemColor.activeCaption);
    EjecutarBut.setMaximumSize(new Dimension(97, 31));
    EjecutarBut.setMinimumSize(new Dimension(97, 31));
    EjecutarBut.setPreferredSize(new Dimension(97, 31));
    EjecutarBut.setBackground(Color.white);
    PanelPrincipal.setOrientation(JSplitPane.VERTICAL_SPLIT);
    PanelPrincipal.setBackground(UIManager.getColor("ProgressBar.selectionBackground"));
    PanelPrincipal.setAlignmentX((float) 0.0);
    PanelPrincipal.setMaximumSize(new Dimension(1000, 500));
    PanelPrincipal.setMinimumSize(new Dimension(1000, 500));
    PanelPrincipal.setPreferredSize(new Dimension(1000, 500));
    PanelPrincipal.setRequestFocusEnabled(true);
    PanelPrincipal.setDividerSize(0);
    PanelPrincipal.setLastDividerLocation(-1);
    PanelPrincipal.setResizeWeight(0.9);
    PanelPrincipal.setBounds(new Rectangle(0, 0, 221, 27));
    PanelTexto.setOrientation(JSplitPane.VERTICAL_SPLIT);
    PanelTexto.setBackground(UIManager.getColor("Menu.selectionBackground"));
    PanelTexto.setBottomComponent(ScrollTexto);
    PanelTexto.setTopComponent(EtiqPrograma);
    PanelTexto.setDividerSize(0);
    PanelTexto.setResizeWeight(1.0);
    Botonera = Box.createVerticalBox();
    PanelResto1.setDividerSize(0);
    PanelResto1.setBackground(SystemColor.activeCaption);
    PanelResto1.setMaximumSize(new Dimension(500, 500));
    PanelResto1.setPreferredSize(new Dimension(128, 64));
    V2.setBackground(SystemColor.activeCaption);
    V3.setBackground(SystemColor.activeCaption);
    V3.setMinimumSize(new Dimension(183, 50));
    V3.setPreferredSize(new Dimension(173, 50));
    Botonera.setForeground(UIManager.getColor("PasswordField.selectionBackground"));
    titledBorder6.setTitleColor(SystemColor.activeCaption);
    titledBorder5.setTitleColor(SystemColor.activeCaption);
    titledBorder4.setTitleColor(SystemColor.activeCaption);
    titledBorder3.setTitleColor(SystemColor.activeCaption);
    titledBorder2.setTitleColor(SystemColor.activeCaption);
    titledBorder1.setTitleColor(SystemColor.activeCaption);
    Botonera.add(CompilarBut);
    Botonera.add(EjecutarBut);
    Botonera.add(Box.createVerticalGlue());
    V3.setOrientation(JSplitPane.VERTICAL_SPLIT);
    V3.setDividerSize(0);
    V2.setOrientation(JSplitPane.VERTICAL_SPLIT);
    V2.setDividerSize(0);
    Visualizador.add(V2);
    Visualizador.add(V3);
    Visualizador.add(Box.createHorizontalGlue());
    PanelResto1.setDividerSize(0);
    Barra.add(menuArchivo);
    menuArchivo.add(menuNuevo);
    menuArchivo.add(menuAbrir);
    menuArchivo.add(menuGuardar);
    menuArchivo.add(menuGuardarComo);
    menuArchivo.addSeparator();
    menuArchivo.add(menuSalir);
    ScrollErrores.getViewport().add(Errores, null);
    ScrollTexto.getViewport().add(Texto, null);
    PanelPrincipal.add(PanelTexto, JSplitPane.TOP);
    PanelPrincipal.add(PanelResto1, JSplitPane.BOTTOM);
    PanelResto1.add(Botonera, JSplitPane.LEFT);
    PanelResto1.add(Visualizador, JSplitPane.RIGHT);

    /*Incluimos los elementos de los paneles*/
    V3.add(EtiqTabla,JSplitPane.TOP);
    V2.add(EtiqErrores,JSplitPane.TOP);
    V2.add(ScrollErrores,JSplitPane.BOTTOM);
    PanelTexto.add(EtiqPrograma,JSplitPane.TOP);
    PanelTexto.add(ScrollTexto,JSplitPane.BOTTOM);
    ruta = "";
   cargarTabla();
    PanelTexto.setDividerLocation(7);
    this.setJMenuBar(Barra);
    this.pack();
    this.show();
  }

  //----------------------------------------------------------------------------
  /** Metodo de inicio de la parte grafica */
  public void inicializar()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  //----------------------------------------------------------------------------
  /** Metodo asociado al evento del boton Compilar
   * @param e : Evento de Java asociado */
  void CompilarBut_actionPerformed(ActionEvent e)
  {
    ejecutado = false;
    try
    {
      //Escribe en el fichero especificado por la ruta el texto(código) y lo cierra
      File file = new File(ruta+"txt");
      FileWriter out = new FileWriter(file);
      out.write(Texto.getText());
      out.close();
      modificado=false;
    }
     catch(Exception ex){}
    String txt = Texto.getText();

    if (ruta.equals("")){
      JOptionPane.showMessageDialog(new JFrame(),"No hay archivo para compilar","Error",
                                    JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    else{
      padre.compilar(ruta+"txt");
      compilado = true;
      cargarTabla();
      cargarErrores();
    }

  }

  //----------------------------------------------------------------------------
  /** Carga la tabla grafica */
  void cargarTabla()
  {
   // int tam = padre.getTabla().memoria;
    EtiqTabla.setText(" Resultado de ejecución");
    if (machine!=null)
    tabla = new jTabla(padre.ts,machine.dameMemoria());
    else tabla=new jTabla();
    V3.add(tabla,JSplitPane.BOTTOM);
    this.print(this.getGraphics());

  }

  //----------------------------------------------------------------------------
  /** Metodo asociado al evento del boton Abrir
   * @param e : Evento de Java asociado */
  void menuAbrir_actionPerformed(ActionEvent e)
  {
     JFileChooser chooser = new JFileChooser();
     ExampleFileFilter filtro = new ExampleFileFilter("txt");
     chooser.setFileFilter(filtro);
     chooser.setCurrentDirectory(new File(ruta+"txt"));
     int returnVal = chooser.showOpenDialog(this);
     if(returnVal == JFileChooser.APPROVE_OPTION)
     {
       //Ahora vamos a abrir el fichero.
       String texto = Texto.getText();
       ruta = chooser.getSelectedFile().getAbsolutePath();
       this.setTitle("Compilador"+ruta);
       try
       {
     /*Abre el fichero con la rita especificada y lee uno a uno todos sus caracteres,que luego vuelca
     en la ventana del editor, pone modificado a falso*/
         File file = new File(ruta);
         int size = (int) file.length();
         int chars_read = 0;
         FileReader in = new FileReader(file);
         char[] data = new char[size];
         while (in.ready()) {
           chars_read += in.read(data, chars_read, size - chars_read);
         }
         in.close();
         Texto.setText(new String(data, 0, chars_read));
         modificado=false;
         menuGuardar.setEnabled(true);
         char[] r;
         r = ruta.toCharArray();
         ruta = ruta.copyValueOf(r,0,ruta.length()-3);
       }
       catch (IOException ex) {
       }
     }

  }

  //----------------------------------------------------------------------------
  /** Metodo asociado al evento del boton GuardarComo
   * @param e : Evento de Java asociado */
  void menuGuardarComo_actionPerformed(ActionEvent e)
  {
     JFileChooser chooser = new JFileChooser();
     chooser.setCurrentDirectory(new File(ruta));
     int returnVal = chooser.showSaveDialog(this);
     if(returnVal == JFileChooser.APPROVE_OPTION)
     {
       //Ahora vamos a abrir el fichero.
    //   ruta=
       String ruta1 =chooser.getSelectedFile().getAbsolutePath()+".txt";
       this.setTitle("Compilador"+ruta1);
       try
       {
         //Escribe en el fichero especificado por la ruta el texto(código) y lo cierra
         File file = new File(ruta1);
         FileWriter out = new FileWriter(file);
         out.write(Texto.getText());
         out.close();
         /*Informa al usuario de que se ha guardado con éxito y pone modificado a falso,
        ya que acaba de guardar*/
         JOptionPane.showMessageDialog(new JFrame(),"El archivo se ha guardado con exito en "+ruta1,"Guardado de archivos",
                                       JOptionPane.INFORMATION_MESSAGE);
         modificado=false;
         menuGuardar.setEnabled(true);
         ruta= ruta1.substring(0,ruta1.length()-3);

       }
       catch(Exception ex){}
     }
  }

  //----------------------------------------------------------------------------
  /** Metodo asociado al evento del boton Guardar
   * @param e : Evento de Java asociado */
  void menuGuardar_actionPerformed(ActionEvent e)
  {
     try
     {
       //Escribe en el fichero especificado por la ruta el texto(código) y lo cierra
       File file = new File(ruta+"txt");
       FileWriter out = new FileWriter(file);
       out.write(Texto.getText());
       out.close();
      /*Informa al usuario de que se ha guardado con éxito y pone modificaco a falso,
      ya qu acaba de guardar*/
       JOptionPane.showMessageDialog(new JFrame(),"El archivo se ha guardado con exito","Guardado de archivos",
                                     JOptionPane.INFORMATION_MESSAGE);
       modificado=false;
     }
     catch(Exception ex){}
  }

  //----------------------------------------------------------------------------
  /** Metodo asociado al evento del boton Nuevo
   * @param e : Evento de Java asociado */
  void menuNuevo_actionPerformed(ActionEvent e)
  {
     Texto.setText("");
     this.setTitle("Compilador");
     compilado = false;
     ejecutado = false;
     cargarErrores();

  }

  //----------------------------------------------------------------------------
  /** Metodo asociado al evento del boton Salir
   * @param e : Evento de Java asociado */
  void menuSalir_actionPerformed(ActionEvent e)
  {
     try
     {
       System.exit(0);
     }
     catch(Exception ex){}
  }

  //----------------------------------------------------------------------------
  /** Carga la lista gráfica de errores */
  void cargarErrores()
  {
    if(compilado)
    {
      if(!ejecutado)
      {
        if(padre.rA!=null)
        {
          if(padre.rA.dameErrores().size()>0)
          {compilado= false;
            EtiqErrores.setText("Errores");
            EtiqErrores.setForeground(new Color(255,0,0));
            String error="";
            for(int i=0;i<padre.rA.dameErrores().size(); i++)
              error=error+"\n"+(padre.rA.dameErrores().get(i));

             Errores.setText(error);
          }
          else
          {compilado=true;
            EtiqErrores.setText("Errores");

            Errores.setText("No hay errores de compilación");
          }
        }
        else
        {compilado=false;
          EtiqErrores.setText("Errores");
          EtiqErrores.setForeground(new Color(255,0,0));
          Errores.setText("Resultado analisis nulo");
        }
      }
      else
      {
        if(!((padre.rA).dameErrores().size()>0)) {

          EtiqErrores.setText("Errores");
  //        EtiqErrores.setForeground(new Color(0,255,0));
          Errores.setText("No hay errores de ejecución");
        }
        else
        {
          EtiqErrores.setText("Errores");
          EtiqErrores.setForeground(new Color(255,0,0));
          String error="";
          for(int i=0;i<padre.rA.dameErrores().size(); i++)
            error=error+"\n"+(padre.rA.dameErrores().get(i));
          Errores.setText(error);
        }
      }
    }
    else
    {
      EtiqErrores.setText("Errores");
      EtiqErrores.setForeground(new Color(255,0,0));
      Errores.setText("No hay ningun programa compilado");
    }
  }

  //----------------------------------------------------------------------------
  /** Metodo asociado al evento del boton Ejecutar
   * @param e : Evento de Java asociado */
  void EjecutarBut_actionPerformed(ActionEvent e)
  {
     if(!compilado)
     {
       JOptionPane.showMessageDialog(null,new JLabel(
               "No se ha compilado ningún programa o ha habido errores de compilación"),"OK",JOptionPane.ERROR_MESSAGE);
            return;

     }
     machine = new MaquinaP();
     if(padre.rA.dameInstrucciones()!=null)
     {
      machine.ejecuta(padre.rA,padre.ts.dameUltimaDir());
      ejecutado = true;
     }
     else
     {
       System.out.println("No hay instrucciones en RA");
     }

     cargarErrores();
     if(machine.sinFallo()){
     this.cargarTabla();
     }
  }

  //----------------------------------------------------------------------------

//----------------------------------------------------------------------------
/**Clase adaptador del boton Compilar.*/
  class CompiladorGUIImp_CompilarBut_actionAdapter implements java.awt.event.ActionListener
  {
    CompiladorGUIImp adaptee;

    //----------------------------------------------------------------------------
    /**
     * Clase constructora.
     * @param adaptee Ventana en la que se encuentra el botón.
     * */
    CompiladorGUIImp_CompilarBut_actionAdapter(CompiladorGUIImp adaptee)
     {
       this.adaptee = adaptee;
    }

    //----------------------------------------------------------------------------
    /**
     * Método que dispara la acción del botón.
     * @param e Evento disparado por el botón.
     * */
    public void actionPerformed(ActionEvent e)
    {
      adaptee.CompilarBut_actionPerformed(e);
    }
  }

//----------------------------------------------------------------------------
/**Clase adaptador del boton Abrir.*/
  class CompiladorGUIImp_menuAbrir_actionAdapter implements java.awt.event.ActionListener
  {
    CompiladorGUIImp adaptee;

    //----------------------------------------------------------------------------
    /**
     * Clase constructora.
     * @param adaptee Ventana en la que se encuentra el botón.
     * */
    CompiladorGUIImp_menuAbrir_actionAdapter(CompiladorGUIImp adaptee)
     {
       this.adaptee = adaptee;
    }

    //----------------------------------------------------------------------------
    /**
     * Método que dispara la acción del botón.
     * @param e Evento disparado por el botón.
     * */
    public void actionPerformed(ActionEvent e)
    {
      adaptee.menuAbrir_actionPerformed(e);
    }
  }

//----------------------------------------------------------------------------
/**Clase adaptador del boton GuardarComo.*/
  class CompiladorGUIImp_menuGuardarComo_actionAdapter implements java.awt.event.ActionListener
  {
    CompiladorGUIImp adaptee;

    //----------------------------------------------------------------------------
    /**
     * Clase constructora.
     * @param adaptee Ventana en la que se encuentra el botón.
     * */
    CompiladorGUIImp_menuGuardarComo_actionAdapter(CompiladorGUIImp adaptee)
     {
       this.adaptee = adaptee;
    }

    //----------------------------------------------------------------------------
    /**
     * Método que dispara la acción del botón.
     * @param e Evento disparado por el botón.
     * */
    public void actionPerformed(ActionEvent e)
    {
      adaptee.menuGuardarComo_actionPerformed(e);
    }
  }

//----------------------------------------------------------------------------
/**Clase adaptador del boton Guardar.*/
  class CompiladorGUIImp_menuGuardar_actionAdapter implements java.awt.event.ActionListener
  {
    CompiladorGUIImp adaptee;

    //----------------------------------------------------------------------------
    /**
     * Clase constructora.
     * @param adaptee Ventana en la que se encuentra el botón.
     * */
    CompiladorGUIImp_menuGuardar_actionAdapter(CompiladorGUIImp adaptee)
     {
       this.adaptee = adaptee;
    }

    //----------------------------------------------------------------------------
    /**
     * Método que dispara la acción del botón.
     * @param e Evento disparado por el botón.
     * */
    public void actionPerformed(ActionEvent e)
    {
      adaptee.menuGuardar_actionPerformed(e);
    }
  }

//----------------------------------------------------------------------------
/**Clase adaptador del boton Nuevo.*/
  class CompiladorGUIImp_menuNuevo_actionAdapter implements java.awt.event.ActionListener
  {
    CompiladorGUIImp adaptee;

    //----------------------------------------------------------------------------
    /**
     * Clase constructora.
     * @param adaptee Ventana en la que se encuentra el botón.
     * */
    CompiladorGUIImp_menuNuevo_actionAdapter(CompiladorGUIImp adaptee)
     {
       this.adaptee = adaptee;
    }

    //----------------------------------------------------------------------------
    /**
     * Método que dispara la acción del botón.
     * @param e Evento disparado por el botón.
     * */
    public void actionPerformed(ActionEvent e)
    {
      adaptee.menuNuevo_actionPerformed(e);
    }
  }

//----------------------------------------------------------------------------
/**Clase adaptador del boton Salir.*/
  class CompiladorGUIImp_menuSalir_actionAdapter implements java.awt.event.ActionListener
  {
    CompiladorGUIImp adaptee;

    //----------------------------------------------------------------------------
    /**
     * Clase constructora.
     * @param adaptee Ventana en la que se encuentra el botón.
     * */
    CompiladorGUIImp_menuSalir_actionAdapter(CompiladorGUIImp adaptee)
     {
       this.adaptee = adaptee;
    }

    //----------------------------------------------------------------------------
    /**
     * Método que dispara la acción del botón.
     * @param e Evento disparado por el botón.
     * */
    public void actionPerformed(ActionEvent e)
    {
      adaptee.menuSalir_actionPerformed(e);
    }
  }

//----------------------------------------------------------------------------
/**Clase que gestiona los filtros para los componentes JFileChooser.*/
  class ExampleFileFilter extends javax.swing.filechooser.FileFilter
  {

  //  private  String TYPE_UNKNOWN = "Type Unknown";
   // private  String HIDDEN_FILE = "Hidden File";

    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    //----------------------------------------------------------------------------
    /**
     * Creates a file filter. If no filters are added, then all
     * files are accepted.
     *
     * @see #addExtension
     */
    public ExampleFileFilter()
    {
      this.filters = new Hashtable();
    }

    //--------------------------------------------------------------------------
    /**
     * Creates a file filter that accepts files with the given extension.
     * Example: new ExampleFileFilter("jpg");
     *
     * @see #addExtension
     */
    public ExampleFileFilter(String extension)
    {
      this(extension,null);
    }

    //--------------------------------------------------------------------------
    /**
     * Creates a file filter that accepts the given file type.
     * Example: new ExampleFileFilter("jpg", "JPEG Image Images");
     *
     * Note that the "." before the extension is not needed. If
     * provided, it will be ignored.
     *
     * @see #addExtension
     */
    public ExampleFileFilter(String extension, String description)
    {
      this();
      if(extension!=null) addExtension(extension);
      if(description!=null) setDescription(description);
    }

    //--------------------------------------------------------------------------
    /**
     * Creates a file filter from the given string array.
     * Example: new ExampleFileFilter(String {"gif", "jpg"});
     *
     * Note that the "." before the extension is not needed adn
     * will be ignored.
     *
     * @see #addExtension
     */
    public ExampleFileFilter(String[] filters)
    {
      this(filters, null);
    }

    //--------------------------------------------------------------------------
    /**
     * Creates a file filter from the given string array and description.
     * Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
     *
     * Note that the "." before the extension is not needed and will be ignored.
     *
     * @see #addExtension
     */
    public ExampleFileFilter(String[] filters, String description)
    {
      this();
      for (int i = 0; i < filters.length; i++)
      {
        // add filters one by one
        addExtension(filters[i]);
      }
      if(description!=null) setDescription(description);
    }

    //--------------------------------------------------------------------------
    /**
     * Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *
     * Files that begin with "." are ignored.
     *
     * @see #getExtension
     * @see FileFilter#accepts
     */
    public boolean accept(File f)
    {
      if(f != null)
      {
        if(f.isDirectory())
        {
          return true;
        }
        String extension = getExtension(f);
        if(extension != null && filters.get(getExtension(f)) != null)
        {
          return true;
        };
      }
      return false;
    }

    //--------------------------------------------------------------------------
    /**
     * Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see FileFilter#accept
     */
    public String getExtension(File f)
    {
      if(f != null)
      {
        String filename = f.getName();
        int i = filename.lastIndexOf('.');
        if(i>0 && i<filename.length()-1)
        {
          return filename.substring(i+1).toLowerCase();
        };
      }
      return null;
    }

    //--------------------------------------------------------------------------
    /**
     * Adds a filetype "dot" extension to filter against.
     *
     * For example: the following code will create a filter that filters
     * out all files except those that end in ".jpg" and ".tif":
     *
     *   ExampleFileFilter filter = new ExampleFileFilter();
     *   filter.addExtension("jpg");
     *   filter.addExtension("tif");
     *
     * Note that the "." before the extension is not needed and will be ignored.
     */
    public void addExtension(String extension)
    {
      if(filters == null)
      {
        filters = new Hashtable(5);
      }
      filters.put(extension.toLowerCase(), this);
      fullDescription = null;
    }


    //--------------------------------------------------------------------------
    /**
     * Returns the human readable description of this filter. For
     * example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     * @see FileFilter#getDescription
     */
    public String getDescription()
    {
      if(fullDescription == null)
      {
        if(description == null || isExtensionListInDescription())
        {
          fullDescription = description==null ? "(" : description + " (";
          // build the description from the extension list
          Enumeration extensions = filters.keys();
          if(extensions != null)
          {
            fullDescription += "." + (String) extensions.nextElement();
            while (extensions.hasMoreElements())
            {
              fullDescription += ", ." + (String) extensions.nextElement();
            }
          }
          fullDescription += ")";
        }
        else
        {
          fullDescription = description;
        }
      }
      return fullDescription;
    }

    //--------------------------------------------------------------------------
    /**
     * Sets the human readable description of this filter. For
     * example: filter.setDescription("Gif and JPG Images");
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     */
    public void setDescription(String description)
    {
      this.description = description;
      fullDescription = null;
    }

    //--------------------------------------------------------------------------
    /**
     * Determines whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see isExtensionListInDescription
     */
    public void setExtensionListInDescription(boolean b)
    {
      useExtensionsInDescription = b;
      fullDescription = null;
    }

    //--------------------------------------------------------------------------
    /**
     * Returns whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see setExtensionListInDescription
     */
    public boolean isExtensionListInDescription()
    {
      return useExtensionsInDescription;
    }
  }


//----------------------------------------------------------------------------
/**Clase que gestiona la representación gráfica de la memoria.*/
  class jTabla extends JPanel
  {
    private JTable tabla;
    private JScrollPane panelScroll;
    private String titColumna[];
    private String datoColumna[][];

    //----------------------------------------------------------------------------
    /**
   Método constructor.
   @param ts Tabla de Simbolos que se quiere representar.
   */
    public jTabla(tablaSimbolos ts, Vector memo)
    {
      setLayout( new BorderLayout() );
      // Creamos las columnas y las cargamos con los datos que van a
      // aparecer en la pantalla
      CreaColumnas();
      if (ejecutado){
      CargaDatos(ts,memo);
      CargaTabla();
      }
    }

    public jTabla (){
      setLayout( new BorderLayout() );
      // Creamos las columnas y las cargamos con los datos que van a
      // aparecer en la pantalla
      CreaColumnas();
      CargaDatos();
      CargaTabla();
    }
    //----------------------------------------------------------------------------
    /**Método para cargar la configuración gráfica de la tabla.*/
    private void CargaTabla()
    {
      // Creamos una instancia del componente Swing
      tabla = new JTable( datoColumna,titColumna );
      // Aquí se configuran algunos de los parámetros que permite
      // variar la JTable
      tabla.setShowHorizontalLines( true );
      tabla.getTableHeader().setEnabled(false);
      tabla.setEnabled(false);
      // Incorporamos la tabla a un panel que incorpora ya una barra
      // de desplazamiento, para que la visibilidad de la tabla sea
      // automática
      panelScroll = new JScrollPane();
      panelScroll.getViewport().add(tabla,null);
      add( panelScroll, BorderLayout.CENTER );
    }

    //----------------------------------------------------------------------------
    /** Creamos las etiquetas que sirven de título a cada una de
   las columnas de la tabla*/
    private void CreaColumnas()
    {
      titColumna = new String[3];

      titColumna[0] = "Identificador";
      titColumna[1] = "Dirección de memoria";
      titColumna[2] = "Valor";

    }

    //----------------------------------------------------------------------------
    /** Creamos los datos para cada uno de los elementos de la tabla*/
    private void CargaDatos()
    {
      datoColumna = new String[0][4];
    }

    //----------------------------------------------------------------------------
    /** Método que rellena la tabla gráfica con el contenido de una tabla de simbolos
     *  @param ts Tabla de Símbolos que proporcionará los datos.
     *  */
    private void CargaDatos(tablaSimbolos ts, Vector memo)
    {
      Hashtable tab = ts.dameTabla();
      int tam = tab.size();
      //Inicializamos la matriz de datos.
      datoColumna = new String[tam][3];

      //Recorremos la tabla de simbolos, rellenando la matriz de datos.
      if (!padre.rA.dameInstrucciones().isEmpty()){
        String s = "";
        Enumeration x = tab.keys();
        String key;
        int cont=0;
        for (int i=0;i<tab.size();i++)
        {
          key = (String) x.nextElement();
          if (!(ts.dameTipo(key)==2)){
            datoColumna[cont][0] = key;
            datoColumna[cont][1] = new Integer(ts.dameDir(key)).toString();
            datoColumna[cont][2] = memo.get(ts.dameDir(key)-1).toString();
            cont++;
          }
        }

        CargaTabla();
      }
    }
  }

//----------------------------------------------------------------------------
/**Clase adaptador del boton Ejecutar.*/
  class CompiladorGUIImp_EjecutarBut_actionAdapter implements java.awt.event.ActionListener
  {

    private CompiladorGUIImp adaptee;

    //----------------------------------------------------------------------------
    /**
     * Clase constructora.
     * @param adaptee Ventana en la que se encuentra el botón.
     * */
    CompiladorGUIImp_EjecutarBut_actionAdapter(CompiladorGUIImp adaptee)
     {
       this.adaptee = adaptee;
    }

    //----------------------------------------------------------------------------
    /**
     * Método que dispara la acción del botón.
     * @param e Evento disparado por el botón.
     * */
    public void actionPerformed(ActionEvent e)
    {
      adaptee.EjecutarBut_actionPerformed(e);
    }
  }
}


