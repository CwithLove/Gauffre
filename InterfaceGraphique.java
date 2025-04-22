import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class InterfaceGraphique implements Runnable {

    Jeu j;
    private IA ia;
    private JButton iaButton;
    private JButton iaFirstMoveButton;
    private JLabel statusLabel;
    private JComboBox<String> iaLevelChoice;
    private NiveauGraphique niv;
    private boolean isPlayerTurn = true;

    public InterfaceGraphique(Jeu jeu) {
        j = jeu;
    }

    public static void demarrer(Jeu j) {
        InterfaceGraphique vue = new InterfaceGraphique(j);
        SwingUtilities.invokeLater(vue);
    }

    private void updateStatus() {
        if (j.verifyFinal()) {
            if (isPlayerTurn) {
                statusLabel.setText("Partie terminee ! L'IA a perdu !");
            } else {
                statusLabel.setText("Partie terminee ! Vous avez perdu !");
            }
            iaButton.setEnabled(false);
            iaFirstMoveButton.setEnabled(false);
        } else {
            statusLabel.setText(isPlayerTurn ? "A votre tour" : "Tour de l'IA");
        }
    }

    private void makeAIMove() {
        if (!j.verifyFinal()) {
            String level = (String) iaLevelChoice.getSelectedItem();
            ia = IA.setIA(j.getNiveau(), level);
            
            int[] coup = ia.run();
            if (coup != null) {
                if (coup[0] == 0 && coup[1] == 0) {
                    Niveau niveau = j.getNiveau();
                    boolean foundAlternative = false;
                    
                    for (int i = 0; i < niveau.getLignes() && !foundAlternative; i++) {
                        for (int j = 0; j < niveau.getColonnes() && !foundAlternative; j++) {
                            if ((i != 0 || j != 0) && niveau.get(i, j) != Niveau.VIDE) {
                                coup[0] = i;
                                coup[1] = j;
                                foundAlternative = true;
                            }
                        }
                    }
                }
                
                j.manger(coup[0], coup[1]);
                isPlayerTurn = true;
                updateStatus();
                niv.repaint();
                
                if (j.verifyFinal()) {
                    updateStatus();
                }
            }
        }
    }

    public void run() {
        JFrame frame = new JFrame("Jeu de la gauffre");
        niv = new NiveauGraphique(j);

        niv.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPlayerTurn && !j.verifyFinal()) {
                    int x = e.getY() / niv.hauteurCase();
                    int y = e.getX() / niv.largeurCase();

                    boolean moveSuccessful = j.manger(x, y);
                    if (moveSuccessful) {
                        isPlayerTurn = false;
                        updateStatus();
												iaFirstMoveButton.setEnabled(false);
                        niv.repaint();

                        if (j.verifyFinal()) {
                            updateStatus();
                        }
                    }
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 3));

        String[] iaLevels = {"easy", "medium", "hard", "expert"};
        iaLevelChoice = new JComboBox<>(iaLevels);
        JPanel levelPanel = new JPanel();
        levelPanel.add(new JLabel("Niveau IA: "));
        levelPanel.add(iaLevelChoice);
        controlPanel.add(levelPanel);

        statusLabel = new JLabel("Partie en cours");
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);
        controlPanel.add(statusPanel);

        JButton restartButton = new JButton("Recommencer");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
							// reinitialiser le jeu
                j = new Jeu();
                niv.setJeu(j);
                isPlayerTurn = true;
                ia = null;
                updateStatus();
								iaButton.setEnabled(true);
                iaFirstMoveButton.setEnabled(true);
                niv.repaint();
                
            }
        });

				// add blank space
				controlPanel.add(new JLabel(""));

        controlPanel.add(restartButton);

        iaButton = new JButton("Tour de l'IA");
        iaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPlayerTurn && !j.verifyFinal()) {
                    makeAIMove();
                } else {
                    statusLabel.setText("C'est votre tour, cliquez sur le plateau");
                }
            }
        });
        controlPanel.add(iaButton);

        iaFirstMoveButton = new JButton("IA commence");
        iaFirstMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlayerTurn) {
                    isPlayerTurn = false;
                    updateStatus();
                    makeAIMove();
										iaFirstMoveButton.setEnabled(false);
                }
            }
        });
        controlPanel.add(iaFirstMoveButton);

        frame.setLayout(new BorderLayout());
        frame.add(niv, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        updateStatus();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }
}
