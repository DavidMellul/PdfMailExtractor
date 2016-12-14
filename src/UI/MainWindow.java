package UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import PDF.PDFDownloader;
import PDF.PDFExtractor;
//import PDF.PDFExtractor;
import Utils.Utils;
import XLS.XLSWriter;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JPanel contentPane;
	private static Point coords;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public MainWindow() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Resources/logo.png")));
		setTitle("PdfMailExtractor");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName().toString());
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850,575);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		contentPane.setBackground(Color.WHITE);
		contentPane.setVerifyInputWhenFocusTarget(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		coords = new Point();
		
		this.getRootPane().registerKeyboardAction(e -> {
            MainWindow.this.dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panelPrincipal.setBounds(1, 50, 847, 524);
		contentPane.add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JLabel Auteur = new JLabel("David Mellul");
		Auteur.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 21));
		Auteur.setBounds(740, 489, 107, 35);
		panelPrincipal.add(Auteur);
		
		JButton btnDistant = new JButton("");
		btnDistant.setToolTipText("S\u00E9lectionner un fichier distant");
		btnDistant.setFocusTraversalKeysEnabled(false);
		btnDistant.setFocusPainted(false);
		btnDistant.setBounds(447, 245, 253, 145);
		panelPrincipal.add(btnDistant);
		btnDistant.setContentAreaFilled(false);
		btnDistant.setBorder(null);
		btnDistant.setBackground(Color.WHITE);
		
		btnDistant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileURL = JOptionPane.showInputDialog(MainWindow.this, "Adresse web du fichier distant", "PdfMailExtractor - Fichier distant", JOptionPane.QUESTION_MESSAGE);
                if(fileURL != null && fileURL.isEmpty() == false && fileURL.contains(".pdf"))
                {
                    PDFDownloader pdfD = new PDFDownloader();
                    PDFExtractor  pdfE = new PDFExtractor();
                    try {
                        File tmpFile = pdfD.downloadPDF(new URL(fileURL));
                        ArrayList<String> listEmails = pdfE.extract(tmpFile.getAbsolutePath());
                        tmpFile.delete();
                        if(listEmails.isEmpty() == false && listEmails != null)
                        	processEmails(listEmails);
                    } catch (MalformedURLException e1) {
                        JOptionPane.showMessageDialog(null, "L'adresse web fournie est invalide.\nPensez à inclure le protocole dans l'URL. (http:// | ftp:// | etc)", "PdfMailExtractor - Erreur lors du téléchargement", JOptionPane.ERROR_MESSAGE, new ImageIcon(MainWindow.class.getResource("/Resources/logo.png")) );
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "L'adresse web fournie est invalide.\nPensez à inclure le protocole dans l'URL. (http:// | ftp:// | etc)", "PdfMailExtractor - Erreur lors du téléchargement", JOptionPane.ERROR_MESSAGE, new ImageIcon(MainWindow.class.getResource("/Resources/logo.png")) );
                }
			}

			
		});
		
		JLabel BoutonDistant = new JLabel("");
		BoutonDistant.setToolTipText("S\u00E9lectionner un fichier distant");
		BoutonDistant.setBounds(447, 245, 253, 145);
		panelPrincipal.add(BoutonDistant);
		BoutonDistant.setIcon(new ImageIcon(MainWindow.class.getResource("/Resources/BoutonDistant.png")));
		
		btnDistant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				BoutonDistant.setIcon(Utils.darken(BoutonDistant.getIcon()));
				MainWindow.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				BoutonDistant.setIcon(Utils.brighten(BoutonDistant.getIcon()));
				MainWindow.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		JButton btnLocal = new JButton("");
		btnLocal.setFocusTraversalKeysEnabled(false);
		btnLocal.setFocusPainted(false);
		btnLocal.setToolTipText("S\u00E9lectionner un fichier local");
		btnLocal.setBounds(184, 245, 253, 145);
		panelPrincipal.add(btnLocal);
		btnLocal.setContentAreaFilled(false);
		btnLocal.setBackground(Color.WHITE);
		btnLocal.setBorder(null);
		
		JLabel BoutonLocal = new JLabel("");
		BoutonLocal.setBounds(184, 245, 253, 145);
		panelPrincipal.add(BoutonLocal);
		BoutonLocal.setIcon(new ImageIcon(MainWindow.class.getResource("/Resources/BoutonLocal.png")));
		
		JLabel Fond = new JLabel("");
		Fond.setBounds(0, 0, 847, 524);
		panelPrincipal.add(Fond);
		Fond.setIcon(new ImageIcon(MainWindow.class.getResource("/Resources/Fond.png")));
		
		btnLocal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
                jfc.setCurrentDirectory(new File(System.getProperty("user.home")));
                jfc.setFileFilter(new FileNameExtensionFilter("Fichiers au format PDF","pdf"));
                jfc.setAcceptAllFileFilterUsed(false);
                jfc.setDialogTitle("Sélection du fichier à analyser...");
                jfc.setMultiSelectionEnabled(false);
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setVisible(true);

            int jfcReturn = jfc.showOpenDialog(MainWindow.this);

            if(jfcReturn == JFileChooser.APPROVE_OPTION) {
                File tmpFile = jfc.getSelectedFile();
                PDFExtractor extractor = new PDFExtractor();
                ArrayList<String> listEmails = extractor.extract(tmpFile.getAbsolutePath());
                if(listEmails.isEmpty() == false && listEmails != null)
                	processEmails(listEmails);
                }
				
			}
		});
		
		btnLocal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				BoutonLocal.setIcon(Utils.darken(BoutonLocal.getIcon()));
				MainWindow.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				BoutonLocal.setIcon(Utils.brighten(BoutonLocal.getIcon()));
				MainWindow.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		
		JPanel titleBar = new JPanel();
		titleBar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		titleBar.setBackground(new Color(255, 255, 255));
		titleBar.setBounds(0, 0, 850, 50);
		contentPane.add(titleBar);
		titleBar.setLayout(null);
		
		JButton btnMinus = new JButton("");
		btnMinus.setToolTipText("R\u00E9duire");
		btnMinus.setBorder(null);
		btnMinus.setFocusTraversalKeysEnabled(false);
		btnMinus.setFocusPainted(false);
		btnMinus.setBounds(770, 8, 33, 29);
		titleBar.add(btnMinus);
		btnMinus.setContentAreaFilled(false);
		
		btnMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.this.setState(ICONIFIED);
				
			}
		});
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/Resources/logo.png")));
		lblNewLabel.setBounds(6, 2, 46, 46);
		titleBar.add(lblNewLabel);
		
		JButton btnQuit = new JButton("");
		btnQuit.setToolTipText("Quitter");
		btnQuit.setFocusTraversalKeysEnabled(false);
		btnQuit.setFocusPainted(false);
		btnQuit.setBorder(null);
		btnQuit.setBounds(806, 10, 33, 31);
		titleBar.add(btnQuit);
		btnQuit.setContentAreaFilled(false);
		
		JLabel Minus = new JLabel("");
		Minus.setToolTipText("R\u00E9duire");
		Minus.setBounds(772, 12, 29, 28);
		titleBar.add(Minus);
		Minus.setIcon(new ImageIcon(MainWindow.class.getResource("/Resources/Reduire.png")));
		
		btnMinus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Minus.setIcon(Utils.darken(Minus.getIcon()));
				MainWindow.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				Minus.setIcon(Utils.brighten(Minus.getIcon()));
				MainWindow.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		JLabel Croix = new JLabel("");
		Croix.setBounds(806, 10, 33, 31);
		titleBar.add(Croix);
		Croix.setIcon(new ImageIcon(MainWindow.class.getResource("/Resources/Croix.png")));
		
		JLabel lblNewLabel_1 = new JLabel("Mail Extractor");
		lblNewLabel_1.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(58, 10, 138, 31);
		titleBar.add(lblNewLabel_1);
		
		btnQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.this.dispose();
			}
		});
		
		btnQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Croix.setIcon(Utils.darken(Croix.getIcon()));
				MainWindow.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				Croix.setIcon(Utils.brighten(Croix.getIcon()));
				MainWindow.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                coords = e.getPoint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                coords = null;  	
            }
        });
		
        titleBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getLocationOnScreen();
                MainWindow.this.setLocation((int)(p.getX()-coords.getX()),(int)(p.getY()-coords.getY()));
            }
        });
        
	}
	
	private void processEmails(ArrayList<String> listEmails) {
		XLSWriter xlsW = new XLSWriter();
		String fileName = xlsW.generateXLSData(listEmails);
		JTextPane jtf = new JTextPane();
		jtf.setContentType("text/html");
		jtf.setText("<html>Le rapport a été généré, il est ici ---> <strong>"+fileName+"</strong></html>");
		jtf.setEditable(false);
		jtf.setBorder(null);
		jtf.setDisabledTextColor(Color.black);
		
		int valeur = JOptionPane.showOptionDialog(MainWindow.this, jtf, "PdfMailExtractor - Classeur Excel généré", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,  new ImageIcon(MainWindow.class.getResource("/Resources/logo.png")), new String[]{"D'accord","Ouvrir ce fichier"}, JOptionPane.NO_OPTION);
		if(valeur == JOptionPane.NO_OPTION) {
			try {
				Desktop.getDesktop().open(new File(fileName));
			} catch (IOException e1) {
		        JOptionPane.showMessageDialog(null, "Impossible d'ouvrir le fichier. Vérifiez que Excel est bien installé sur votre machine.", "PdfMailExtractor - Erreur lors de l'ouverture du fichier", JOptionPane.ERROR_MESSAGE, new ImageIcon(MainWindow.class.getResource("/Resources/logo.png")) );

			}
		}
	}
}
