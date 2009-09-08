package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


import com.l2fprod.common.swing.JDirectoryChooser;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.display.ApplicationFrame;
import com.sun.org.apache.bcel.internal.generic.LLOAD;


import net.java.games.jogl.GLCanvas;
import net.java.games.jogl.GLCapabilities;
import net.java.games.jogl.GLDrawableFactory;

import ui.image.ImageDisplay;
import ui.image.ThreadPlay;

import data.ImagesData;
import data.VisualData;
import data.io.DicomProperties;
import data.io.LoadFile;
import data.io.OpenSUR;
import data.io.Project;
import data.io.SaveFile;
import data.io.SaveFilteredImages;
import data.io.SaveMeshSur;
import draw2D.InitCircleOnScreen;
import draw2D.StaticInitCircle;
import draw2D.filtering.EnlargeCircle;
import draw2D.filtering.MeanFilter;
import draw2D.filtering.RegionGrowing;
import draw2D.filtering.edge.CannyEdgeDetector;
import draw2D.filtering.edge.EdgeDetectionAlls;
import draw2D.filtering.rg.Criterio;
import draw2D.filtering.rg.CriterioRGEntorno;
import draw2D.filtering.rg.CriterioRGGradiente;
import draw2D.filtering.rg.CriterioRGSimple;
import draw3D.Mesh;
import draw3D.OpenGLCanvas;
import draw3D.Setup3D;
import draw3D.scenario.screen.Point3D;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainFrame extends javax.swing.JFrame {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	private JMenuItem helpMenuItem;
	private JMenu jMenu5;
	private JMenu jMenu2D;
	private JScrollPane jScrollPane1;
	private JButton jButtonNewWin;
	private JTable jTable1;
	private JButton jButtonNext;
	private JCheckBoxMenuItem jCheckBoxMenuItemSeeds;
	private JCheckBoxMenuItem jCheckBoxViewLateral;
	private JLabel jLabelXY;
	private JButton jButtonSE;
	private JButton jButton1;
	private JScrollPane jScrollPaneL;
	private RepaintPanel jPanelLateral;
	private JButton jButtonSnake;
	private JButton jButtonEM;
	private JSeparator jSeparator8;
	private JButton jButtonSetIC;
	private JMenuItem jMenuConfigEnlarge;
	private JMenuItem jMenuConfigInitCircle;
	private JSeparator jSeparator7;
	private JSeparator jSeparator6;
	private JMenuItem jMenuItemEnlargeCircle;
	private JMenuItem jMenuItemEnlargeCircleforAll;
	private JMenuItem jMenuItemEnlargeCircleforAllReduce;
	private JMenuItem jMenuItemGenerateMesh;
	private JLabel jLabelNameImage;
	private JMenuItem jMenuItemDCircle;
	private JMenuItem jMenuItemPrue;
	private JMenuItem jMenuItemEnlargeCircleSnake;
	private JCheckBoxMenuItem jCheckBoxMenuItemCircle;
	private JCheckBoxMenuItem jCheckBoxMenuItemCircleAll;
	private JCheckBoxMenuItem jCheckBoxMCMenuItemSeeds;
	private JMenuItem jMenuItemOpenDir;
	private JMenuItem jMenuItemSaveFilters;
	private JButton jButtonBack;
	private JToolBar jToolBar1;
	private JMenuItem deleteMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem pasteMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem dicomPropMenuItem;
	private JMenuItem jMenuItemViewGraph;
	private JMenuItem loadDicomPropMenuItem;
	private JMenuItem load3DMenuItem;
	private JMenuItem filterMeanMenuItem;
	private JMenuItem filterMedianMenuItem;
	private JMenuItem cannyMenuItem;
	private JMenuItem filterRGMenuItem;
	private JMenuItem jMenuItemSaveProject;
	private JMenu jMenu4;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveMenuItem;
	private JButton jButtonPlay;
	private JMenu jMenuFilter;
	private JMenu jMenu3D;
	public JScrollPane jScrollPaneFilter;
	public JScrollPane jScrollPaneLateral;
	private JSeparator jSeparator5;
	private JInternalFrame jInternalFrame3D;
	public JTabbedPane jTabbedPane1;
	private JScrollPane jScrollPane2;
	private JLabel jLabelSBSpiner;
	private JSeparator jSeparator4;
	private JPanel jPanelStatusBar;
	private JSlider jSlider1;
	private JSeparator jSeparator3;
	private JSeparator jSeparator9;
	private JButton jButtonLast;
	private JButton jButtonFirst;
	private JSplitPane jSplitPane1;
	private JMenuItem openFileMenuItem;
	private JMenuItem openDicomirMenuItem;
	private JMenuItem jMenuItemDMalla;
	private JMenuItem jMenuItemOpenMalla;
	private JMenuItem jMenuItemDSeeds;
	private JMenuItem jMenuItemSaveMesh;
	private JMenuItem jMenuItemOpenMesh;
	private JMenuItem  jMenuItemEnlargeCircleManualy;
	private JMenu jMenu3;
	private JMenuBar jMenuBar1;
	private ThreadPlay tP=null;
	private ThreadPlay tPFilter=null;
	private JFileChooser filechooser = null;
	private JMenuItem openProjectMenuItem;
	private Distribucion ed;
	
	//Imagen
	private HandImage handImage;
	private HandImage handImageFiltered;
	


	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				
			}
		});
	}
	
	public MainFrame() {
		super();	
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/iconMF2.gif")).getImage());
		initGUI();
		VisualData.mf=this;
		
		
		
	}
	private GLCanvas glScene = null;
	private OpenGLCanvas glEvents = null;
	private boolean load3D = false;
	
	public void load3D(){
	glScene = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
	glEvents = new OpenGLCanvas(handImage);
	glScene.addGLEventListener(glEvents);
	
	this.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e)
		{
			glEvents.keyReleased(new KeyEvent((Component)e.getSource(), e.getID(), 0, 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED));
			System.exit(0);
		}
	});

	load3D=true;
	jInternalFrame3D.getContentPane().add(glScene, BorderLayout.CENTER);
	}
	private void initGUI() {
		try {
			this.setTitle("DICOM Viewer");
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			

			{
				jToolBar1 = new JToolBar();
				
				getContentPane().add(jToolBar1, BorderLayout.NORTH);
				jToolBar1.setPreferredSize(new java.awt.Dimension(700, 42));
				jToolBar1.setLayout(null);
				
				{
					jSeparator3 = new JSeparator();
					jToolBar1.add(jSeparator3);
					jSeparator3.setOrientation(SwingConstants.VERTICAL);
					jSeparator3.setBounds(489, -2, 6, 43);
				}
				{					jButtonBack = new JButton();
					jToolBar1.add(jButtonBack);
					jButtonBack.setBounds(102, 2, 45, 39);
					jButtonBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/back.gif")));
					jButtonBack.setToolTipText("Back Image");
					jButtonBack.setEnabled(false);
					jButtonBack.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonBackActionPerformed(evt);
						}
					});
				}
				{
					jButtonNext = new JButton();
					jToolBar1.add(jButtonNext);
					jButtonNext.setBounds(198, 2, 45, 39);
					jButtonNext.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/forward.gif")));
					jButtonNext.setToolTipText("Next Image");
					jButtonNext.setEnabled(false);
					jButtonNext.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonNextActionPerformed(evt);
						}
					});
				}
				{
					jButtonNewWin = new JButton();
					jToolBar1.add(jButtonNewWin);
					jButtonNewWin.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/new_win.gif")));
					jButtonNewWin.setBounds(500, 4, 39, 32);
					jButtonNewWin.setToolTipText("Open image below in a new Window");
					jButtonNewWin.setEnabled(false);
					jButtonNewWin.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtoncccActionPerformed(evt);
						}
					});
				}
				{
					jButtonFirst = new JButton();
					jToolBar1.add(jButtonFirst);
					jButtonFirst.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/first.png")));
					jButtonFirst.setBounds(55, 2, 45, 39);
					jButtonFirst.setEnabled(false);
					jButtonFirst.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonFirstActionPerformed(evt);
						}
					});
				}
				{
					jButtonLast = new JButton();
					jToolBar1.add(jButtonLast);
					jButtonLast.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/last.png")));
					jButtonLast.setBounds(248, 2, 45, 39);
					jButtonLast.setEnabled(false);
					jButtonLast.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonLastActionPerformed(evt);
						}
					});
				}
				{
					jButtonPlay = new JButton();
					jToolBar1.add(jButtonPlay);
					jButtonPlay.setBounds(151, 2, 45, 39);
					jButtonPlay.setEnabled(false);
					jButtonPlay.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent evt) {
							jButtonPlayKeyReleased(evt);
						}
					});

					jButtonPlay.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/play.gif")));
					jButtonPlay.setToolTipText("Play");
					jButtonPlay.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonPlayActionPerformed(evt);
						}
					});
				}
				{
					jSlider1 = new JSlider();
					jToolBar1.add(jSlider1);
					jSlider1.setBounds(304, 8, 169, 25);
					jSlider1.setMaximum(800);
					jSlider1.setMinimum(10);
					jSlider1.setValue(VisualData.sliderBarValue);
					jSlider1.setToolTipText("Slides velocity");
					jSlider1.setEnabled(false);
					jSlider1.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent evt) {
							jSlider1KeyReleased(evt);
						}
					});
					jSlider1.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent evt) {
							jSlider1StateChanged(evt);
						}
					});
				
					
				}
				{
					jButtonSE = new JButton();
					jToolBar1.add(jButtonSE);
					jButtonSE.setEnabled(false);
					jButtonSE.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/begin.png")));
					jButtonSE.setBounds(7, 1, 40, 40);
					jButtonSE.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jButtonSEMouseClicked(evt);
						}
					});
					jButtonSE.setToolTipText("Start Sequence");
					jButtonSE.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonSEActionPerformed(evt);
						}
					});
				}
				{
					jSeparator7 = new JSeparator();
					jToolBar1.add(jSeparator7);
					jSeparator7.setOrientation(SwingConstants.VERTICAL);
					jSeparator7.setBounds(52, 0, 4, 40);
				}
				{
					jButtonSetIC = new JButton();
					jToolBar1.add(jButtonSetIC);
					jButtonSetIC.setBounds(556, 1, 43, 39);
					jButtonSetIC.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/SetICLarge.png")));
					jButtonSetIC.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonSetICActionPerformed(evt);
						}
					});
				}
				{
					jSeparator8 = new JSeparator();
					jToolBar1.add(jSeparator8);
					jSeparator8.setOrientation(SwingConstants.VERTICAL);
					jSeparator8.setBounds(549, -5, 10, 47);
				}
				{
					jButtonEM = new JButton();
					jToolBar1.add(jButtonEM);
					jButtonEM.setBounds(601, 1, 44, 38);
					jButtonEM.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/EnlargeLarge.png")));
					jButtonEM.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonEMActionPerformed(evt);
						}
					});
				}
				{
					jButtonSnake = new JButton();
					jToolBar1.add(jButtonSnake);
					jButtonSnake.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/snakesLarge.png")));
					jButtonSnake.setBounds(645, 1, 39, 39);
					jButtonSnake.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButtonSnakeActionPerformed(evt);
						}
					});
				}
				{
					jButton1 = new JButton();
					jToolBar1.add(jButton1);
					jButton1.setText("jButton1");
					jButton1.setBounds(684, 12, 47, 21);
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton1ActionPerformed(evt);
						}
					});
				}
			}
			{
				jSplitPane1 = new JSplitPane();
				jSplitPane1.setDividerLocation(0);
				jSplitPane1.setDividerSize(6);
				jSplitPane1.setOneTouchExpandable(true);
				{
					jTabbedPane1 = new JTabbedPane();
					jSplitPane1.add(jTabbedPane1, JSplitPane.RIGHT);
					jTabbedPane1.setPreferredSize(new java.awt.Dimension(768, 571));
					
					jTabbedPane1.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							jTabbedPane1MouseClicked(evt);
						}
					});

					{
						jScrollPane1 = new JScrollPane();
						jTabbedPane1.addTab("2D", null, jScrollPane1, null);
						jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 291));
						
						{
							handImage = new HandImage(ImagesData.imagesB);
							getContentPane().add(handImage);
							jScrollPane1.setViewportView(handImage);
							handImage.addMouseMotionListener(new MouseMotionAdapter() {
								public void mouseMoved(MouseEvent evt) {
									handImageMouseMoved(evt);
								}
							});
							handImage.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent evt) {
									handImageMousePressed(evt);
								}
								public void mouseReleased(MouseEvent evt) {
									handImageMouseReleased(evt);
								}
								public void mouseClicked(MouseEvent evt) {
									handImageMouseClicked(evt);
								}
								
								
							});
							
							handImage.addMouseMotionListener(new MouseMotionAdapter() {
								
								public void mouseDragged(MouseEvent e) {
									handImageMouseDragged(e);
								}
							});
						}
						
						
					}
					{
						jScrollPaneL = new JScrollPane();
						jScrollPaneL.setPreferredSize(new java.awt.Dimension(600, 291));
						jTabbedPane1.addTab("Lateral", null, jScrollPaneL, null);
						{
							jPanelLateral = new RepaintPanel();
							
							getContentPane().add(jPanelLateral);
							jScrollPaneL.setViewportView(jPanelLateral);
							jPanelLateral.setLayout(null);
						}
					}
					{
						jScrollPaneFilter = new JScrollPane();
						jTabbedPane1.addTab("Filtered", null, jScrollPaneFilter, null);

						{
							handImageFiltered = new HandImage(ImagesData.imagesBFiltered);
							getContentPane().add(handImageFiltered);
							jScrollPaneFilter.setViewportView(handImageFiltered);
							
						}
					}

										{
						jInternalFrame3D = new JInternalFrame();
						jTabbedPane1.addTab("3D", null, jInternalFrame3D, null);
						jInternalFrame3D.setVisible(true);
						Image cursor = new ImageIcon(OpenGLCanvas.class.getClassLoader().getResource(Setup3D.cross)).getImage();
						jInternalFrame3D.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(16, 16),Setup3D.cross));
						
						jInternalFrame3D.setFrameIcon(	new ImageIcon(getClass().getClassLoader().getResource("images/load3D.gif")));
						
						jInternalFrame3D.addComponentListener(new ComponentAdapter() {
							public void componentHidden(ComponentEvent evt) {
								jInternalFrame3DComponentHidden(evt);
							}
							public void componentShown(ComponentEvent evt) {
								jInternalFrame3DComponentShown(evt);
							}
						});
						
					}

				}
				{
					jScrollPane2 = new JScrollPane();
					
					jSplitPane1.add(jScrollPane2, JSplitPane.LEFT);
					jScrollPane2.setPreferredSize(new java.awt.Dimension(99, 571));
					{
						TableModel jTable1Model = 
							new DefaultTableModel(
									new String[][] { { "", "" } },
									new String[] { "Property", "Value" });
						
						jTable1 = new JTable();
						jScrollPane2.setViewportView(jTable1);
						VisualData.jTable1=jTable1;
						TableColumn tC=new TableColumn();
						jTable1.addColumn(tC);
						jTable1.setModel(jTable1Model);
						jTable1.setPreferredSize(new java.awt.Dimension(300, 400));
						jTable1.setEnabled(false);
						jTable1.setBorder(BorderFactory.createCompoundBorder(
								null, 
								null));
						jTable1.setAutoCreateRowSorter(true);

					}
				}
				getContentPane().add(jSplitPane1, BorderLayout.CENTER);
			}
			{
				jPanelStatusBar = new JPanel();
				getContentPane().add(jPanelStatusBar, BorderLayout.SOUTH);
				jPanelStatusBar.setPreferredSize(new java.awt.Dimension(776, 25));
				jPanelStatusBar.setLayout(null);
				{
					jSeparator4 = new JSeparator();
					jPanelStatusBar.add(jSeparator4);
					jSeparator4.setOrientation(SwingConstants.VERTICAL);
					jSeparator4.setBounds(86, 0, 1, 33);
					jSeparator4.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
					jSeparator4.setEnabled(true);
				}
				{
					jLabelSBSpiner = new JLabel();
					jPanelStatusBar.add(jLabelSBSpiner);
					jLabelSBSpiner.setText("Speed 100 ms.");
					jLabelSBSpiner.setBounds(94, 5, 74, 14);
				}
				{
					jSeparator5 = new JSeparator();
					jPanelStatusBar.add(jSeparator5);
					jSeparator5.setOrientation(SwingConstants.VERTICAL);
					jSeparator5.setEnabled(true);
					jSeparator5.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,null,null,null,null));
					jSeparator5.setBounds(253, -2, 1, 38);
				}
				{
					VisualData.jLabelNameImage = new JLabel();
					jPanelStatusBar.add(VisualData.jLabelNameImage);
					VisualData.jLabelNameImage.setText("");
					VisualData.jLabelNameImage.setBounds(540, 6, 400, 14);
				}
				{
					VisualData.jProgressBar = new JProgressBar();
					jPanelStatusBar.add(VisualData.jProgressBar);
					VisualData.jProgressBar.setBounds(260, 5, 251, 14);
				}
				{
					jSeparator5 = new JSeparator();
					jPanelStatusBar.add(jSeparator5);
					jSeparator5.setOrientation(SwingConstants.VERTICAL);
					jSeparator5.setEnabled(true);
					jSeparator5.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,null,null,null,null));
					jSeparator5.setBounds(518, -2, 1, 38);
				}
				{
					jLabelXY = new JLabel();
					jPanelStatusBar.add(jLabelXY);
					jLabelXY.setText("x: y:");
					jLabelXY.setBounds(183, 5, 76, 14);
				}
				{
					jSeparator6 = new JSeparator();
					jPanelStatusBar.add(jSeparator6);
					jSeparator6.setOrientation(SwingConstants.VERTICAL);
					jSeparator6.setEnabled(true);
					jSeparator6.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,null,null,null,null));
					jSeparator6.setBounds(86, 0, 1, 33);
				}
				{
					VisualData.jLabelSBImages = new JLabel();
					jPanelStatusBar.add(VisualData.jLabelSBImages);
					VisualData.jLabelSBImages.setText("Image");
					VisualData.jLabelSBImages.setBounds(10, 5, 133, 14);
				}
				{
					VisualData.jLabelFPS = new JLabel();
					jPanelStatusBar.add(VisualData.jLabelFPS);
					VisualData.jLabelFPS.setText("");
					VisualData.jLabelFPS.setBounds(550, 5, 133, 14);
				}
				
			}
			this.setSize(784, 714);
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				{
					jMenu3 = new JMenu();
					jMenuBar1.add(jMenu3);
					jMenu3.setText("File");
				
					{
						openProjectMenuItem = new JMenuItem();
						jMenu3.add(openProjectMenuItem);
						openProjectMenuItem.setText("Open Project");
						openProjectMenuItem.setMnemonic('P');
						openProjectMenuItem.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit
								.getDefaultToolkit().getMenuShortcutKeyMask()));
						openProjectMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/openproject.gif")));
						
						openProjectMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								openProjectMenuItemActionPerformed(evt);
							}
						});
					}
					
					{
						openFileMenuItem = new JMenuItem();
						jMenu3.add(openFileMenuItem);
						openFileMenuItem.setText("Open Files...");
						openFileMenuItem.setMnemonic('O');
						openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit
								.getDefaultToolkit().getMenuShortcutKeyMask()));
						openFileMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/fldr_obj.gif")));
						
						openFileMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								openFileActionPerformed(evt);
							}
						});
					}
					{
						jMenuItemOpenDir = new JMenuItem();
						jMenu3.add(jMenuItemOpenDir);
						jMenuItemOpenDir.setText("Open Directory...");
						jMenuItemOpenDir.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/opendir.gif")));
						jMenuItemOpenDir.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								openDirActionPerformed(evt);
							}

							
						});
					}
					{
						openDicomirMenuItem = new JMenuItem();
						jMenu3.add(openDicomirMenuItem);
						openDicomirMenuItem.setText("Open DicomDir...");
					
						openDicomirMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/openDicomDir.gif")));
						
						openDicomirMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								openDicomirActionPerformed(evt);
							}
						});
					}
					{
						jSeparator2 = new JSeparator();
						jMenu3.add(jSeparator2);
					}
					{
						jMenuItemSaveProject = new JMenuItem();
						jMenu3.add(jMenuItemSaveProject);
						jMenuItemSaveProject.setText("Save Project");
						jMenuItemSaveProject.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/saveproject.gif")));
						jMenuItemSaveProject.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								saveProjectActionPerformed(evt);
							}
						});
					}
					
					{
						jMenuItemSaveFilters = new JMenuItem();
						jMenu3.add(jMenuItemSaveFilters);
						jMenuItemSaveFilters.setText("Save Images from Tab");
						jMenuItemSaveFilters.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/saveall_edit.gif")));
						jMenuItemSaveFilters.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								saveFilesActionPerformed(evt);
							}
						});
					}
					{
						jSeparator2 = new JSeparator();
//						jMenu3.add(jSeparator2);
					}
					{
						jSeparator1 = new JSeparator();
						jMenu3.add(jSeparator1);
					}
					{
						closeFileMenuItem = new JMenuItem();
						jMenu3.add(closeFileMenuItem);
						closeFileMenuItem.setText("Close");
						closeFileMenuItem.setMnemonic('C');
						closeFileMenuItem.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit
								.getDefaultToolkit().getMenuShortcutKeyMask()));
						
						closeFileMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/close.gif")));
						closeFileMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.exit(0);
								
							}
						});
					}
					

				}
				{
					jMenu5 = new JMenu();
					jMenuBar1.add(jMenu5);
					jMenu5.setText("Edit");
					
						{
							loadDicomPropMenuItem = new JMenuItem();
							loadDicomPropMenuItem.setEnabled(false);
							jMenu5.add(loadDicomPropMenuItem);
							loadDicomPropMenuItem.setText("Load DICOM Properties");
							
							loadDicomPropMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/loadDicomProp.gif")));
							loadDicomPropMenuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									loadDicomPropMenuItemActionPerformed(evt);
								}
							});
						
						}
						{
							jSeparator2 = new JSeparator();
							jMenu5.add(jSeparator2);
						}
						{
							jMenuConfigEnlarge = new JMenuItem();
							jMenu5.add(jMenuConfigEnlarge);
							jMenuConfigEnlarge.setText("Config. Enlarge");
							jMenuConfigEnlarge.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/configenlarge.gif")));
							jMenuConfigEnlarge.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									jMenuConfigEnlargeActionPerformed(evt);
								}
							});
						}
						{
							jMenuConfigInitCircle = new JMenuItem();
							jMenu5.add(jMenuConfigInitCircle);
							jMenuConfigInitCircle.setText("Config. Init Circle");
							jMenuConfigInitCircle.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/configInitC.gif")));
							jMenuConfigInitCircle.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									jMenuConfigInitCircleActionPerformed(evt);
								}
							});
						}
						
						
				}

				{
					jMenu4 = new JMenu();
					jMenuBar1.add(jMenu4);
					jMenu4.setText("View");
					{
						dicomPropMenuItem = new JCheckBoxMenuItem();
						jMenu4.add(dicomPropMenuItem);
						dicomPropMenuItem.setSelected(VisualData.viewDicomProperties);
						dicomPropMenuItem.setText("DICOM Properties");
						dicomPropMenuItem.setMnemonic('D');
						dicomPropMenuItem.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit
								.getDefaultToolkit().getMenuShortcutKeyMask()));
						
						dicomPropMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/viewDicomProp.gif")));
						dicomPropMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								dicomPropMenuItemActionPerformed(evt);
							}
						});
					
					}
					{
						jMenuItemViewGraph = new JMenuItem();
						jMenu4.add(jMenuItemViewGraph);
						jMenuItemViewGraph.setText("Graph");
						jMenuItemViewGraph.setEnabled(false);
						jMenuItemViewGraph.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/graph.gif")));
						jMenuItemViewGraph.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jMenuItemViewGraphActionPerformed(evt);
							}
						});
					}
					{
						jSeparator1 = new JSeparator();
						jMenu4.add(jSeparator1);
					}
					
					{
						jSeparator1 = new JSeparator();
						jMenu4.add(jSeparator1);
					}
					{
						jCheckBoxViewLateral = new JCheckBoxMenuItem();
						jMenu4.add(jCheckBoxViewLateral);
						jCheckBoxViewLateral.setText("View Lateral");
						jCheckBoxViewLateral.setSelected(false);
						jCheckBoxViewLateral.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								viewLateralMenuItemActionPerformed(evt);
							}

						});
					}
					{
						jCheckBoxMenuItemSeeds = new JCheckBoxMenuItem();
						jMenu4.add(jCheckBoxMenuItemSeeds);
						jCheckBoxMenuItemSeeds.setText("View Seeds");
						jCheckBoxMenuItemSeeds.setSelected(false);
						jCheckBoxMenuItemSeeds.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								viewSeedsMenuItemActionPerformed(evt);
							}

						});
					}
					{
						jCheckBoxMenuItemCircle = new JCheckBoxMenuItem();
						jMenu4.add(jCheckBoxMenuItemCircle);
						jCheckBoxMenuItemCircle.setText("View Circle");
						jCheckBoxMenuItemCircle.setSelected(true);
						jCheckBoxMenuItemCircle.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								viewCircleMenuItemActionPerformed(evt);
							}

						});
					}
					
					{
						jCheckBoxMenuItemCircleAll = new JCheckBoxMenuItem();
						jMenu4.add(jCheckBoxMenuItemCircleAll);
						jCheckBoxMenuItemCircleAll.setText("View All Circles");
						jCheckBoxMenuItemCircleAll.setSelected(true);
						jCheckBoxMenuItemCircleAll.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								viewCircleAllMenuItemActionPerformed(evt);
							}

						});
					}

				}

				{
					jMenu2D = new JMenu();
					jMenuBar1.add(jMenu2D);
					jMenu2D.setText("2D");
					{
						jCheckBoxMCMenuItemSeeds = new JCheckBoxMenuItem();
						jMenu2D.add(jCheckBoxMCMenuItemSeeds);
						jCheckBoxMCMenuItemSeeds.setText("Manualy Circle");
						jCheckBoxMCMenuItemSeeds.setSelected(StaticInitCircle.manualyC);
						jCheckBoxMCMenuItemSeeds.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								StaticInitCircle.manualyC = !StaticInitCircle.manualyC;
							}

						});
					}
					{
						jSeparator9 = new JSeparator();
						jMenu2D.add(jSeparator9);
					}
					{
						jMenuItemOpenMalla = new JMenuItem();
						jMenu2D.add(jMenuItemOpenMalla);
						jMenuItemOpenMalla.setText("Open Circle Mesh");
						jMenuItemOpenMalla.setMnemonic('M');
						jMenuItemOpenMalla.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
						jMenuItemOpenMalla.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/opencircle.gif")));
						jMenuItemOpenMalla.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								openCircleMeshActionPerformed(evt);
							}
						});
					}
					
					{
						jMenuItemDMalla = new JMenuItem();
						jMenu2D.add(jMenuItemDMalla);
						jMenuItemDMalla.setText("Save Circle Mesh");
						jMenuItemDMalla.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/savecircle.gif")));
						
						jMenuItemDMalla.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								saveMallaActionPerformed(evt);
							}

						
						});
					}
					
					{
						jMenu2D.add(jSeparator2);
					}
					{
						deleteMenuItem = new JMenuItem();
						jMenu2D.add(deleteMenuItem);
						deleteMenuItem.setText("Delete Filtered");
						deleteMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/delete_template.gif")));
						
						deleteMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								deleteFilteredActionPerformed(evt);
							}
						});
					}
					{
						jMenuItemDCircle = new JMenuItem();
						jMenu2D.add(jMenuItemDCircle);
						jMenuItemDCircle.setText("Delete Circle");
						jMenuItemDCircle.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/deleteCircle.gif")));
						
						jMenuItemDCircle.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								DeleteCircleActionPerformed(evt);
							}
						});
					}
					{
						jMenuItemDSeeds = new JMenuItem();
						jMenu2D.add(jMenuItemDSeeds);
						jMenuItemDSeeds.setText("Delete Seeds");
						jMenuItemDSeeds.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/deleteseeds.gif")));
						
						jMenuItemDSeeds.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								DeleteSeedsActionPerformed(evt);
							}
						});
					}
					
					
					

				}
				{
					
						jMenuFilter = new JMenu();
						jMenuBar1.add(jMenuFilter);
						jMenuFilter.setText("Filter");
						{
							cannyMenuItem = new JMenuItem();
							
							jMenuFilter.add(cannyMenuItem);
							cannyMenuItem.setText("Edge Detection");
							
							cannyMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/filtroED.gif")));
							cannyMenuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									cannyMenuItemActionPerformed(evt);
								}
							});
						
						}
						{
							filterRGMenuItem = new JMenuItem();
							
							jMenuFilter.add(filterRGMenuItem);
							filterRGMenuItem.setText("Region Growing");
							
							filterRGMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/filtroRG.gif")));
							filterRGMenuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									filterRGMenuItemActionPerformed(evt);
								}
							});
						
						}
						{
							filterMeanMenuItem = new JMenuItem();
							
							jMenuFilter.add(filterMeanMenuItem);
							filterMeanMenuItem.setText("Mean Filter");
							
							filterMeanMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/filtro.gif")));
							filterMeanMenuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									filterMeanMenuItemActionPerformed(evt);
								}
							});
						
						}
						{
							filterMedianMenuItem = new JMenuItem();
							
							jMenuFilter.add(filterMedianMenuItem);
							filterMedianMenuItem.setText("Median Filter");
							
							filterMedianMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/filtro.gif")));
							filterMedianMenuItem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									filterMedianMenuItemActionPerformed(evt);
								}
							});
						
						}
						
						{
							//jSeparator1 = new JSeparator();
							jMenuFilter.add(jSeparator1);
						}
						{
							jMenuItemPrue = new JMenuItem();
							jMenuFilter.add(jMenuItemPrue);
							jMenuItemPrue.setText("Generate Test ");
							jMenuItemPrue.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									PrueActionPerformed(evt);
								}
							});
						}
				}
				{
					jMenu3D = new JMenu();
					jMenuBar1.add(jMenu3D);
					jMenu3D.setText("3D");
					{
						jMenuItemEnlargeCircleSnake = new JMenuItem();
						jMenu3D.add(jMenuItemEnlargeCircleSnake);
						jMenuItemEnlargeCircleSnake.setText("Enlarge Circle Snakes");
						jMenuItemEnlargeCircleSnake.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/snakes.gif")));
						
						jMenuItemEnlargeCircleSnake.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jMenuItemEnlargeCircleSnakeActionPerformed(evt);
								jMenuItemGenerateMesh.setEnabled(true);
							}

							

						
						});
					}
					{
						jMenuItemEnlargeCircleManualy = new JMenuItem();
						jMenu3D.add(jMenuItemEnlargeCircleManualy);
						jMenuItemEnlargeCircleManualy.setText("Enlarge Circle Manualy");
						jMenuItemEnlargeCircleManualy.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/enlarge.gif")));
						
						jMenuItemEnlargeCircleManualy.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jMenuItemEnlargeCircleManualyActionPerformed(evt);
								jMenuItemGenerateMesh.setEnabled(true);
							}

							

						
						});
					}
					{
						jMenuItemEnlargeCircleforAll = new JMenuItem();
						jMenu3D.add(jMenuItemEnlargeCircleforAll);
						jMenuItemEnlargeCircleforAll.setText("Enlarge All Circles (Init)");
						jMenuItemEnlargeCircleforAll.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/enlarge.gif")));
						
						jMenuItemEnlargeCircleforAll.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jMenuItemEnlargeCircleforAllActionPerformed(evt);
								jMenuItemGenerateMesh.setEnabled(true);
							}

						
						});
					}
					{
						jMenuItemEnlargeCircleforAllReduce = new JMenuItem();
						jMenu3D.add(jMenuItemEnlargeCircleforAllReduce);
						jMenuItemEnlargeCircleforAllReduce.setText("Enlarge All Circles (Reduce)");
						jMenuItemEnlargeCircleforAllReduce.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/enlarge.gif")));
						
						jMenuItemEnlargeCircleforAllReduce.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jMenuItemEnlargeCircleforAllReduceActionPerformed(evt);
								jMenuItemGenerateMesh.setEnabled(true);
							}

						
						});
					}
					{
						jMenuItemGenerateMesh = new JMenuItem();
						jMenu3D.add(jMenuItemGenerateMesh);
						jMenuItemGenerateMesh.setText("Generate Mesh");
						jMenuItemGenerateMesh.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/generatemesh.gif")));
						jMenuItemGenerateMesh.setEnabled(false);
						jMenuItemGenerateMesh.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								handImage.generateMesh();
								load3DMenuItem.setEnabled(true);
							}
						});
					}
					{
						load3DMenuItem = new JMenuItem();
						load3DMenuItem.setEnabled(false);
						jMenu3D.add(load3DMenuItem);
						load3DMenuItem.setText("Load 3D");
						
						load3DMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/load3D.gif")));
						load3DMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								load3DMenuItemActionPerformed(evt);
							}
						});
					
					}
					{
						jSeparator1 = new JSeparator();
						jMenu3D.add(jSeparator1);
					}
					{
						jMenuItemOpenMesh = new JMenuItem();
						jMenu3D.add(jMenuItemOpenMesh);
						jMenuItemOpenMesh.setText("Open Mesh .Sur");
						jMenuItemOpenMesh.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/openmesh.gif")));
						
						jMenuItemOpenMesh.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								openMeshActionPerformed(evt);
							}

						
						});
					}
					{
						jMenuItemSaveMesh = new JMenuItem();
						jMenu3D.add(jMenuItemSaveMesh);
						jMenuItemSaveMesh.setText("Save Mesh .Sur");
						jMenuItemSaveMesh.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/savemesh.gif")));
						
						jMenuItemSaveMesh.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								saveMeshActionPerformed(evt);
							}

						
						});
					}
				}
				{
					jMenu5 = new JMenu();
					jMenuBar1.add(jMenu5);
					jMenu5.setText("Help");
					{
						helpMenuItem = new JMenuItem();
						jMenu5.add(helpMenuItem);
						helpMenuItem.setText("Help");
						helpMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/help.gif")));
						
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	protected void saveFilesActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		SaveFilteredImages sf;
		String path=getSelectedDirectory();
		if (jTabbedPane1.getSelectedIndex()==0)
			 sf=new SaveFilteredImages(path,0,ImagesData.imagesB);
		else
			 sf=new SaveFilteredImages(path,1,ImagesData.imagesBFiltered);
		sf.start();
		
	}

	private void openDicomirActionPerformed(ActionEvent evt) {
		File file = null;
		
		filechooser = new JFileChooser(new File("c:\\DICOM"));
		FFilter bothFilter; 
		String dicomdirFileName = null;
		
		bothFilter = new FFilter(new String[] {"dicomdir"}, "All types Images");
	
		filechooser.addChoosableFileFilter(bothFilter);
		filechooser.setAcceptAllFileFilterUsed(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			dicomdirFileName=filechooser.getSelectedFile().getAbsolutePath();
//			ImagesData.addImagePath(file);
//			ImagesData.processImage(file);
//			
			//ImagesData.processDicomDir(null, null);
			
			try {
				AttributeList list = new AttributeList();

				final String parentFilePath = new File(dicomdirFileName).getParent();

				list.read(dicomdirFileName);

				DicomDirectoryBrowser tree = new DicomDirectoryBrowser(list,parentFilePath,jScrollPane2,handImage);
				

			} catch (Exception e) {
				System.err.println(e);
				e.printStackTrace(System.err);
				System.exit(0);
			}
			jButtonBack.setEnabled(true);
			jButtonNext.setEnabled(true);
			jButtonNext.setEnabled(true);
			jButtonFirst.setEnabled(true);
			jButtonLast.setEnabled(true);
			jButtonNewWin.setEnabled(true);
			jButtonSE.setEnabled(true);
			jButtonPlay.setEnabled(true);
			jSlider1.setEnabled(true);
			loadDicomPropMenuItem.setEnabled(true);
			jScrollPane1.setViewportView(handImage);
			handImage.setImage();
			handImage.repaint();
			jSplitPane1.setDividerLocation(190);
		}
		
		
	}
	
	
	
	
	private void openFileActionPerformed(ActionEvent evt) {
		File file[] = null;
		
		filechooser = new JFileChooser(new File("c:\\DICOM"));
		filechooser.setMultiSelectionEnabled(true);
		FFilter jpgFilter, bmpFilter, dcmFilter,imgFilter,bothFilter; 
		
		jpgFilter = new FFilter("jpg", "JPEG Imege");
		bmpFilter = new FFilter("bmp", "Bitmap Image");
		dcmFilter = new FFilter("dcm", "DICOM Imege");
		imgFilter = new FFilter("img", "Img Image");
		bothFilter = new FFilter(new String[] {"jpg", "bmp","dcm","img"}, "All types Images");
		
		filechooser.addChoosableFileFilter(jpgFilter);
		filechooser.addChoosableFileFilter(bmpFilter);
		filechooser.addChoosableFileFilter(dcmFilter);
		filechooser.addChoosableFileFilter(imgFilter);
		filechooser.addChoosableFileFilter(bothFilter);
		filechooser.setAcceptAllFileFilterUsed(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFiles();
			
			jScrollPane1.setViewportView(handImage);
			ImagesData.openDicom(file,handImage);
			
			
			jButtonBack.setEnabled(true);
			jButtonNext.setEnabled(true);
			jButtonNext.setEnabled(true);
			jButtonFirst.setEnabled(true);
			jButtonLast.setEnabled(true);
			jButtonNewWin.setEnabled(true);
			jButtonPlay.setEnabled(true);
			jButtonSE.setEnabled(true);
			jSlider1.setEnabled(true);
			loadDicomPropMenuItem.setEnabled(true);
			
			
			
			
		}
		
		
	}
	

	private void jButtonBackActionPerformed(ActionEvent evt) {
		if (jTabbedPane1.getSelectedIndex()== 0)//2D
			handImage.backImage();
		if (jTabbedPane1.getSelectedIndex()== 2)//Filter
			handImageFiltered.backImage();
		
		
		
	}
	
	private void jButtonNextActionPerformed(ActionEvent evt) {
		
		if (jTabbedPane1.getSelectedIndex()== 0)//2D
			handImage.nextImage();
		if (jTabbedPane1.getSelectedIndex()== 2)//Filter
			handImageFiltered.nextImage();
	}
	
	private void jButtoncccActionPerformed(ActionEvent evt) {
	
		if (jTabbedPane1.getSelectedIndex()== 0)//2D
			new ImageDisplay(ImagesData.imagesB,handImage.getIndex());
		if (jTabbedPane1.getSelectedIndex()== 2)//Filter
			new ImageDisplay(ImagesData.imagesBFiltered,handImageFiltered.getIndex());
	}
	
	private void jButtonFirstActionPerformed(ActionEvent evt) {
		

		if (jTabbedPane1.getSelectedIndex()== 0)//2D
			handImage.firstImage();
		if (jTabbedPane1.getSelectedIndex()== 2)//Filter
			handImageFiltered.firstImage();
		
	}
	
	private void jButtonLastActionPerformed(ActionEvent evt) {
		
		if (jTabbedPane1.getSelectedIndex()== 0)//2D
			handImage.lastImage();
		if (jTabbedPane1.getSelectedIndex()== 2)//Filter
			handImageFiltered.lastImage();
	}
	
	private void jButtonPlayActionPerformed(ActionEvent evt) {
		if(jButtonPlay.getToolTipText().equals("Play")){
			dicomPropMenuItem.setText("Pause");
			jButtonPlay.setToolTipText(" Pause ( p )");
			jButtonPlay.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/pause.gif")));
			if(tP!=null){
				tP.stop();
			}
			if (jTabbedPane1.getSelectedIndex()== 0)//2D
				tP= new ThreadPlay(handImage);
			else //if (jTabbedPane1.getSelectedIndex()== 1)//Filter
				tP= new ThreadPlay(handImageFiltered);
			
			tP.setSleep(jSlider1.getValue());
			tP.start();
		}else{
			dicomPropMenuItem.setText("Play");
			jButtonPlay.setToolTipText("Play");
			jButtonPlay.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/play.gif")));
			tP.setPause(true);
			
		}
		
	}
	
	
	private void jSlider1StateChanged(ChangeEvent evt) {
		if(tP!=null){
			tP.setSleep(jSlider1.getValue());
			
			
		}
		VisualData.sliderBarValue =jSlider1.getValue(); 
		jLabelSBSpiner.setText("Speed "+jSlider1.getValue()+" ms.");
	}
	
	private void jButtonPlayKeyReleased(KeyEvent evt) {
		if(evt.getKeyCode()==80)//p
			jButtonPlayActionPerformed(null);
	}
	
	private void jSlider1KeyReleased(KeyEvent evt) {
		if(evt.getKeyCode()==80)//p
			jButtonPlayActionPerformed(null);
	}
	
	public void dicomPropMenuItemActionPerformed(ActionEvent evt) {
		
			VisualData.viewDicomProperties= !VisualData.viewDicomProperties;
			dicomPropMenuItem.setSelected(VisualData.viewDicomProperties);
			if(!VisualData.viewDicomProperties)
				jSplitPane1.setDividerLocation(0);
			else
				jSplitPane1.setDividerLocation(100);
		
		
	}
	public void loadDicomPropMenuItemActionPerformed(ActionEvent evt) {
		
			ImagesData.processImageProperties();
		
	}
	public void load3DMenuItemActionPerformed(ActionEvent evt) {
		
		load3D();
		jTabbedPane1.setSelectedIndex(2);
	
	}
	

	
	private void jInternalFrame3DComponentShown(ComponentEvent evt) {
		if(load3D){
			glScene.setVisible(true);
			glEvents.setDraw(true);
		}
	}
	
	
	private void jInternalFrame3DComponentHidden(ComponentEvent evt) {
		if(load3D){
			glScene.setVisible(false);
			glEvents.setDraw(false);
			VisualData.jLabelFPS.setText("FPS = 0 (paused)");
		}
	}
	
	
	
	public void filterRGMenuItemActionPerformed(ActionEvent evt) {
		RGDialog rG= new RGDialog(this,jTabbedPane1,handImageFiltered,handImage);
		rG.setVisible(true);
	
	}
	public void filterMeanMenuItemActionPerformed(ActionEvent evt) {
		
		MeanDialog mD= new MeanDialog(this,jTabbedPane1,handImageFiltered);
		mD.setVisible(true);
		
	}
	public void filterMedianMenuItemActionPerformed(ActionEvent evt) {
		
		MedianDialog mD= new MedianDialog(this,jTabbedPane1,handImageFiltered);
		mD.setVisible(true);
		
	}
	
	
	private void openDirActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String dirrr = getSelectedDirectory();
		
		File dir = new File(dirrr);
		File[] ficheros = dir.listFiles();
		if (ficheros == null)
			  System.out.println("No hay ficheros en el directorio especificado");
			else {
			  
				  ImagesData.openDicom(ficheros,handImage);
					jButtonBack.setEnabled(true);
					jButtonNext.setEnabled(true);
					jButtonNext.setEnabled(true);
					jButtonFirst.setEnabled(true);
					jButtonLast.setEnabled(true);
					jButtonNewWin.setEnabled(true);
					jButtonPlay.setEnabled(true);
					jButtonSE.setEnabled(true);
					jSlider1.setEnabled(true);
					loadDicomPropMenuItem.setEnabled(true);
			}
		
	}
	private String getSelectedDirectory() {
		JDirectoryChooser dc = new JDirectoryChooser();
		int choice = dc.showOpenDialog(this);
		if (choice != JDirectoryChooser.CANCEL_OPTION){
			return dc.getSelectedFile().getAbsolutePath()+"\\";
		}
		return null;
	}
	
	private void viewSeedsMenuItemActionPerformed(ActionEvent evt) {
		VisualData.viewSeeds=jCheckBoxMenuItemSeeds.isSelected();
		handImage.setImage();
	}
	private void viewCircleMenuItemActionPerformed(ActionEvent evt) {
		
		VisualData.viewCircle=jCheckBoxMenuItemCircle.isSelected();
		handImage.setImage();
	}
	private void viewCircleAllMenuItemActionPerformed(ActionEvent evt) {
		
		VisualData.viewCircleAll=jCheckBoxMenuItemCircleAll.isSelected();
		handImage.setImage();
		
	}
	
	private void deleteFilteredActionPerformed(ActionEvent evt) {
		
		ImagesData.deleteFilteredImages();
		handImageFiltered.deleteFilteredImages();
		
	}
	private void PrueActionPerformed(ActionEvent evt) {
		
		Vector <Criterio> criterios =new Vector<Criterio>();
		
		criterios.add(new CriterioRGGradiente(6,1.4,0.1,254));
		criterios.add(new CriterioRGGradiente(6,1.5,0.2,254));
		criterios.add(new CriterioRGGradiente(6,1.3,0.3,254));
		criterios.add(new CriterioRGGradiente(6,1.4,0.4,254));
		criterios.add(new CriterioRGGradiente(7,1.5,0.2,254));
		criterios.add(new CriterioRGGradiente(8,1.6,0.3,254));
		criterios.add(new CriterioRGGradiente(8,1.4,0.1,254));
		criterios.add(new CriterioRGEntorno(5,1.4,2));
		
		for (double K =1.4;K<1.5;K=K+0.1)
			for (int radio= 5;radio<13;radio++ ){
				
				criterios.add(new CriterioRGSimple(radio,K));
				
				for (int radioEntorno= 1;radioEntorno<6;radioEntorno++ ){
					criterios.add(new CriterioRGEntorno(radio,K,radioEntorno));
				}
				for (int maxG= 254;maxG<255;maxG++ )
					for (double pg =0.1;pg<0.2;pg=pg+0.1)
						criterios.add(new CriterioRGGradiente(radio,K,pg,maxG));
						
			}	
			
		RegionGrowing rG = new RegionGrowing(this.handImage,this.handImageFiltered,ImagesData.imagesB,criterios);
		rG.start();
		
	
	}
	private void DeleteCircleActionPerformed(ActionEvent evt) {
		handImage.deleteCircle();
	
	}
	private void DeleteSeedsActionPerformed(ActionEvent evt) {
		handImage.deleteSeeds();
	
	}

	

	
	private void saveMallaActionPerformed(ActionEvent evt) {
		filechooser = new JFileChooser(new File("c:\\"));
		filechooser.setMultiSelectionEnabled(false);
		FFilter crlFilter; 
		File file;
		crlFilter = new FFilter("crl", "Circle Files");
		filechooser.addChoosableFileFilter(crlFilter);
		filechooser.setAcceptAllFileFilterUsed(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFile();
			if(file.exists()){
				int response=JOptionPane.showConfirmDialog(null,"Overwrite existing file?","Confirm Overwrite",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(response==JOptionPane.CANCEL_OPTION){
					return;
				}
			}
			SaveFile sv=new SaveFile(file.getAbsolutePath());
			sv.save(handImage.getInitCircle().getCirclePoints());
		}
		
	}
	
	private void saveMeshActionPerformed(ActionEvent evt) {
		filechooser = new JFileChooser(new File("c:\\"));
		filechooser.setMultiSelectionEnabled(false);
		FFilter crlFilter; 
		File file;
		crlFilter = new FFilter("sur", "SUR Files");
		filechooser.addChoosableFileFilter(crlFilter);
		filechooser.setAcceptAllFileFilterUsed(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFile();
			if(file.exists()){
				int response=JOptionPane.showConfirmDialog(null,"Overwrite existing file?","Confirm Overwrite",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(response==JOptionPane.CANCEL_OPTION){
					return;
				}
			}
			SaveMeshSur sv=new SaveMeshSur(file.getAbsolutePath());
			sv.save2();
			
		}
		
	}
	
	
	private void openCircleMeshActionPerformed(ActionEvent evt) {
		filechooser = new JFileChooser(new File("c:\\"));
		filechooser.setMultiSelectionEnabled(false);
		FFilter crlFilter; 
		File file;
		crlFilter = new FFilter("crl", "Circle Files");
		filechooser.addChoosableFileFilter(crlFilter);
		filechooser.setAcceptAllFileFilterUsed(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFile();
			LoadFile ld=new LoadFile(file.getAbsolutePath());
			handImage.setCirclePoints(ld.load());
			handImage.setImage();
			
		}
	
	}
	private void jMenuItemEnlargeCircleforAllReduceActionPerformed(ActionEvent evt) {
		
		handImage.enlargeCircleForAllWithReduce();
		
	}
	
	private void jMenuItemEnlargeCircleforAllActionPerformed(ActionEvent evt) {
		
		handImage.enlargeCircleForAll();
		
	}

	private void jMenuConfigEnlargeActionPerformed(ActionEvent evt) {
		EnlargeDialog ed=new EnlargeDialog(this);
		ed.setVisible(true);
	}
	private void jMenuConfigInitCircleActionPerformed(ActionEvent evt) {
		EditInitcircleDialog ed=new EditInitcircleDialog(this,handImage);
		ed.setVisible(true);
	}
	
	
	private void openMeshActionPerformed(ActionEvent evt) {
		filechooser = new JFileChooser(new File("c:\\"));
		filechooser.setMultiSelectionEnabled(false);
		FFilter crlFilter; 
		File file;
		crlFilter = new FFilter("sur", "Mesh Files");
		filechooser.addChoosableFileFilter(crlFilter);
		filechooser.setAcceptAllFileFilterUsed(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFile();
			OpenSUR os=new OpenSUR(file.getAbsolutePath());
			load3DMenuItem.setEnabled(true);
		}
		
	}
	private void jMenuItemEnlargeCircleManualyActionPerformed(ActionEvent evt) {
		
		EnlargeManualyDialog e= new EnlargeManualyDialog(this,this.handImage);
		e.setVisible(true);
	}
	
	public void openProjectMenuItemActionPerformed(ActionEvent evt){
		filechooser = new JFileChooser(new File("c:\\"));
		filechooser.setMultiSelectionEnabled(false);
		FFilter crlFilter; 
		File file;
		crlFilter = new FFilter("rj", "Project Files");
		filechooser.addChoosableFileFilter(crlFilter);
		filechooser.setAcceptAllFileFilterUsed(false);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFile();
			Project p=new Project(file.getAbsolutePath());
			p.restoreProject(handImage);
			load3DMenuItem.setEnabled(true);
			jButtonBack.setEnabled(true);
			jButtonNext.setEnabled(true);
			jButtonNext.setEnabled(true);
			jButtonFirst.setEnabled(true);
			jButtonLast.setEnabled(true);
			jButtonNewWin.setEnabled(true);
			jButtonPlay.setEnabled(true);
			jButtonSE.setEnabled(true);
			jSlider1.setEnabled(true);
			loadDicomPropMenuItem.setEnabled(true);

		}

		
	}
	public void saveProjectActionPerformed(ActionEvent evt){
		
		filechooser = new JFileChooser(new File("c:\\"));
		filechooser.setMultiSelectionEnabled(false);
		FFilter crlFilter; 
		File file;
		crlFilter = new FFilter("rj", "Project Files");
		filechooser.addChoosableFileFilter(crlFilter);
		filechooser.setAcceptAllFileFilterUsed(false);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFile();
			if(file.exists()){
				int response=JOptionPane.showConfirmDialog(null,"Overwrite existing file?","Confirm Overwrite",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(response==JOptionPane.CANCEL_OPTION){
					return;
				}
			}
			String pathMesh ="";
			if (this.jMenuItemGenerateMesh.isEnabled()){
				SaveMeshSur sv=new SaveMeshSur(file.getAbsolutePath());
				sv.save2();
				pathMesh = sv.getPath();
			}else{
				try{
				    FileInputStream fis = new FileInputStream(Setup3D.pathMesh); 
				    pathMesh = file.getAbsolutePath()+".sur";
				    FileOutputStream fos = new FileOutputStream(pathMesh); 
				    FileChannel canalFuente = fis.getChannel(); 
				    FileChannel canalDestino = fos.getChannel(); 
				    canalFuente.transferTo(0, canalFuente.size(), canalDestino); 
				    fis.close(); 
				    fos.close(); 
				 }catch (IOException ex) {}
			}
			Project p=new Project(handImage.getAllCircles().getCircleAllPointsEnlarge(),ImagesData.imagesPaths,pathMesh);
			p.saveProject(file.getAbsolutePath());
			
			
		}	
	}
	
		public void cannyMenuItemActionPerformed(ActionEvent evt) {
			EdgeDetection mD= new EdgeDetection(this,jTabbedPane1,handImageFiltered);
			mD.setVisible(true);
		
	}
		
		private void jButtonSEActionPerformed(ActionEvent evt) {
			if (jButtonSE.getToolTipText().equals("Start Sequence")){
				jButtonSE.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/end.png")));
				jButtonSE.setToolTipText("In Sequence");
				Mesh.startSequence = handImage.getIndex();
				
			}else
				if (jButtonSE.getToolTipText().equals("In Sequence")){
					jButtonSE.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/endend.png")));
					jButtonSE.setToolTipText("Middle click to restart");
					Mesh.endSequence = handImage.getIndex();
				}
			
		}
		
		private void jButtonSEMouseClicked(MouseEvent evt) {
			if(evt.getButton()== evt.BUTTON2)
			{
				jButtonSE.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/begin.png")));
				jButtonSE.setToolTipText("Start Sequence");
			}
		}
		
		
		private void handImageMouseClicked(MouseEvent evt) {
			if(VisualData.viewLateral){
				if (VisualData.viewLPointCenter)
					handImage.setCenter(new Point(evt.getX(),evt.getY()));
				else
					handImage.setRefer(new Point(evt.getX(),evt.getY()));
				handImage.setImage();
			}
			if(StaticInitCircle.drawPC){
				StaticInitCircle.DPCxC=evt.getX();
				StaticInitCircle.DPCyC=evt.getY();
				
			}else{
				if (evt.getButton()== MouseEvent.BUTTON1){
					if (ImagesData.imagesB.size()>0)
						handImage.addSeed(new Point (evt.getX(),evt.getY()));
				}
				if (evt.getButton()== MouseEvent.BUTTON3){
					if(StaticInitCircle.manualyC)
						handImage.addPointToInitCircle(new Point (evt.getX(),evt.getY()));
				}}
		}
		
		
		private void handImageMouseReleased(MouseEvent evt) {
			if(StaticInitCircle.drawPC){
				StaticInitCircle.drawPerfectCircle(evt,handImage);
				StaticInitCircle.drawPC = false;
				StaticInitCircle.origen=null;	
				VisualData.jToggleButtonPC.setSelected(false);
			}
			if(evt.getModifiersEx()==4096){
				StaticInitCircle.destinoOP = new Point(evt.getX(),evt.getY());
			}
			if(VisualData.viewLateral){
				VisualData.viewLPoint=null;
				VisualData.viewLPointCenter=false;
				jPanelLateral.setImage(handImage.getImagesB(),handImage.getRefer(),handImage.getCenter());
				if(ed!=null)
					ed.resetDataset(jPanelLateral.getGraph(handImage.getIndex()));
				
			}
			StaticInitCircle.indexMoveOP=-1;
			handImage.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		private void handImageMousePressed(MouseEvent evt) {
			if(StaticInitCircle.drawPC){
				StaticInitCircle.DPCxC = evt.getX();
				StaticInitCircle.DPCyC = evt.getY();
			}
			if(VisualData.viewLateral){
				if(handImage.contatinPoint(handImage.getCenter(),new Point(evt.getX(),evt.getY()))){
					VisualData.viewLPointCenter=true;
					
				}
				VisualData.viewLPoint = new Point(evt.getX(),evt.getY());
			}
			if((evt.getModifiersEx()==4096)||(evt.getModifiersEx()==2048)){
				InitCircleOnScreen init = handImage.getInitCircle();
				StaticInitCircle.indexMoveOP =init.containts(new Point (evt.getX(),evt.getY())); 
				if(StaticInitCircle.indexMoveOP!=-1){
					handImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					StaticInitCircle.setOrigenOP(new Point (evt.getX(),evt.getY()),handImage);
					
						
				}
			}
			
		}
		protected void handImageMouseDragged(MouseEvent evt) {
	
			if(StaticInitCircle.drawPC){//Dibujar Draw Perfect Circle
				StaticInitCircle.drawPerfectCircle(evt,handImage);	
			}else{
				if(evt.getModifiersEx()==1024){//Button1 MOVER
					StaticInitCircle.moveInitCircle(evt,handImage);
				}else
					if((evt.getModifiersEx()==2048)&&(StaticInitCircle.indexMoveOP!=-1)){
						StaticInitCircle.destinoOP = new Point(evt.getX(),evt.getY());
						StaticInitCircle.moveOnePoint(evt, handImage);
					}else if((evt.getModifiersEx()==4096)&&(StaticInitCircle.indexMoveOP!=-1)){
						StaticInitCircle.destinoOP = new Point(evt.getX(),evt.getY());
						StaticInitCircle.moveNormalOnePoint(evt, handImage);
					}
			}
			if(VisualData.viewLateral && VisualData.viewLPoint!=null){
				
					if (VisualData.viewLPointCenter)
						handImage.setCenter(new Point(evt.getX(),evt.getY()));
					else
						handImage.setRefer(new Point(evt.getX(),evt.getY()));
					handImage.setImage();
					
				
			}
		}
		
		private void handImageMouseMoved(MouseEvent evt) {
			jLabelXY.setText("x: "+evt.getX()+" y: "+evt.getY());
			if(StaticInitCircle.drawPC)
				handImage.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			else{
				InitCircleOnScreen init = handImage.getInitCircle();
				int i = init.containts(new Point (evt.getX(),evt.getY()));

				if(i!=-1)
					handImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else
					handImage.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			if(VisualData.viewLateral){
				if(handImage.containtPoint(new Point (evt.getX(),evt.getY()))){
					handImage.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
				else
					handImage.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		private void jButtonSetICActionPerformed(ActionEvent evt) {
			jMenuConfigInitCircleActionPerformed(evt);
		}
		
		private void jButtonEMActionPerformed(ActionEvent evt) {
			jMenuItemEnlargeCircleManualyActionPerformed(evt);
			jMenuItemGenerateMesh.setEnabled(true);
		}
		public void jMenuItemEnlargeCircleSnakeActionPerformed(ActionEvent evt) {
			EnlargeSnakesDialog e= new EnlargeSnakesDialog(this,this.handImage);
			e.setVisible(true);
		}
		
		private void jButtonSnakeActionPerformed(ActionEvent evt) {
			jMenuItemEnlargeCircleSnakeActionPerformed(null);
		}
		
		
		private void jTabbedPane1MouseClicked(MouseEvent evt) {
			if(jTabbedPane1.getSelectedIndex()==0)
				handImage.setImage();
			else
				if(jTabbedPane1.getSelectedIndex()==2)
					handImageFiltered.setImage();
				else
					if(jTabbedPane1.getSelectedIndex()==1){
						if (VisualData.viewLateral){
							jPanelLateral.setImage(handImage.getImagesB(),handImage.getRefer(),handImage.getCenter());
							jPanelLateral.setImage();
						}
					}

		}
		private void viewLateralMenuItemActionPerformed(ActionEvent evt) {
			
			VisualData.viewLateral=jCheckBoxViewLateral.isSelected();
			jMenuItemViewGraph.setEnabled(true);
			jPanelLateral.setImage(handImage.getImagesB(),handImage.getRefer(),handImage.getCenter());
			handImage.setImage();
		}
		private void jMenuItemViewGraphActionPerformed(ActionEvent evt) {
			ed=new Distribucion(this,jPanelLateral.getGraph(handImage.getIndex()));
			ed.setVisible(true);
		}
		
		private void jButton1ActionPerformed(ActionEvent evt) {
			Packages e= new Packages(this,this.handImage);
			e.setVisible(true);
		}

}
