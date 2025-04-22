
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class InterfaceGraphique implements Runnable {

    Jeu j;
    private IA ia1;
    private IA ia2;
    private JButton iaButton;
    private JButton iaFirstMoveButton;
    private JLabel statusLabel;
    private NiveauGraphique niv;
    private boolean isPlayer1Turn = true;
    private int gameMode;
    private String iaLevel1;
    private String iaLevel2;
    private Timer aiTimer;
    private JFrame frame;

    public InterfaceGraphique(Jeu jeu, int gameMode, String iaLevel1, String iaLevel2) {
        this.j = jeu;
        this.gameMode = gameMode;
        this.iaLevel1 = iaLevel1;
        this.iaLevel2 = iaLevel2;
    }

    public static void demarrer(Jeu j, int gameMode, String iaLevel1, String iaLevel2) {
        InterfaceGraphique vue = new InterfaceGraphique(j, gameMode, iaLevel1, iaLevel2);
        SwingUtilities.invokeLater(vue);
    }

    private void updateStatus() {
        if (j.verifyFinal()) {
            String message;
            if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_PLAYER) {
                message = !isPlayer1Turn ? "Partie terminee ! Joueur 2 a gagné !" : "Partie terminee ! Joueur 1 a gagné !";
            } else if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI) {
                message = !isPlayer1Turn ? "Partie terminee ! L'IA a gagné !" : "Partie terminee ! Vous avez gagné !";
            } else { // AI vs AI
                message = !isPlayer1Turn ? "Partie terminee ! IA 2 a gagné !" : "Partie terminee ! IA 1 a gagné !";
            }

            statusLabel.setText(message);
        } else {
            String turnMessage;
            if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_PLAYER) {
                turnMessage = isPlayer1Turn ? "Tour du Joueur 1" : "Tour du Joueur 2";
            } else if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI) {
                turnMessage = isPlayer1Turn ? "A votre tour" : "Tour de l'IA";
            } else { // AI vs AI
                turnMessage = isPlayer1Turn ? "Tour de l'IA 1" : "Tour de l'IA 2";
            }
            statusLabel.setText(turnMessage);
        }
    }

    private void makeAIMove(boolean isIA1) {
        if (!j.verifyFinal()) {
            IA currentIA = isIA1 ? ia1 : ia2;
            if (currentIA == null) { // Premiere partie, on initialise l'IA
                String level = isIA1 ? iaLevel1 : iaLevel2;
                currentIA = IA.setIA(j.getNiveau(), level);
                if (isIA1) {
                    ia1 = currentIA;
                } else {
                    ia2 = currentIA;
                }
            }

            int[] coup = currentIA.run();
            if (coup != null) { // Si l'IA a trouve un coup valide
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
                isPlayer1Turn = !isPlayer1Turn;
                updateStatus();
                niv.repaint();

                if (j.verifyFinal()) {
                    updateStatus();
                } else if (gameMode == InterfaceConfiguration.MODE_AI_VS_AI) {
                    // Si c'est IA vs IA, programmer le prochain coup automatiquement
                    aiTimer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            makeAIMove(!isIA1);
                        }
                    });
                    aiTimer.setRepeats(false);
                    aiTimer.start();
                }
            } else { // Si l'IA n'a pas trouve de coup valide
                String message = "Aucun coup valide trouvé";
                statusLabel.setText(message);
            }
        }
    }

    private void restartGame() {
        Niveau niveau = new Niveau(j.getNiveau().getLignes(), j.getNiveau().getColonnes());
        j = new Jeu(niveau);
        niv.setJeu(j);
        isPlayer1Turn = true;
        ia1 = null;
        ia2 = null;
        if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI || gameMode == InterfaceConfiguration.MODE_AI_VS_AI) {
            iaButton.setEnabled(true);
            iaFirstMoveButton.setEnabled(true);
        }
        updateStatus();
        niv.repaint();
    }

    public void run() {
        frame = new JFrame("Jeu de la gauffre");
        niv = new NiveauGraphique(j);

        niv.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (j.verifyFinal()) {
                    return;
                }

                // Pour le mode joueur vs joueur ou joueur vs IA (quand c'est le tour du joueur)
                if ((gameMode == InterfaceConfiguration.MODE_PLAYER_VS_PLAYER)
                        || (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI && isPlayer1Turn)) {

                    int x = e.getY() / niv.hauteurCase();
                    int y = e.getX() / niv.largeurCase();

                    boolean moveSuccessful = j.manger(x, y);
                    if (moveSuccessful) {
                        isPlayer1Turn = !isPlayer1Turn;
                        updateStatus();
                        if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI) {
                            iaFirstMoveButton.setEnabled(false);
                        }
                        niv.repaint();

                        if (j.verifyFinal()) {
                            updateStatus();
                        } else if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI && !isPlayer1Turn) {
                            // Si c'est le tour de l'IA après le coup du joueur
                            aiTimer = new Timer(500, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    makeAIMove(false);
                                    aiTimer.stop();
                                }
                            });
                            aiTimer.setRepeats(false);
                            aiTimer.start();
                        }
                    }
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 3));

        statusLabel = new JLabel("Partie en cours");
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);
        controlPanel.add(statusPanel);

        JButton restartButton = new JButton("Recommencer");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        JButton configButton = new JButton("Configuration");
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new InterfaceConfiguration().show();
                        frame.dispose();
                    }
                });
            }
        });

        controlPanel.add(configButton);
        controlPanel.add(restartButton);

        if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI || gameMode == InterfaceConfiguration.MODE_AI_VS_AI) {
            iaButton = new JButton("Tour de l'IA");
            iaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI && !isPlayer1Turn && !j.verifyFinal()) {
                        makeAIMove(false);
                    } else if (gameMode == InterfaceConfiguration.MODE_AI_VS_AI && !j.verifyFinal()) {
                        makeAIMove(isPlayer1Turn);
                    } else {
                        String message = "Ce n'est pas le tour de l'IA";
                        statusLabel.setText(message);
                    }
                }
            });

            // Adapter le libellé du bouton selon le mode
            if (gameMode == InterfaceConfiguration.MODE_AI_VS_AI) {
                iaButton.setText("Coup IA");
            }

            controlPanel.add(iaButton);

            iaFirstMoveButton = new JButton("IA commence");
            iaFirstMoveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameMode == InterfaceConfiguration.MODE_PLAYER_VS_AI && isPlayer1Turn) {
                        isPlayer1Turn = false;
                        updateStatus();
                        makeAIMove(false);
                        iaFirstMoveButton.setEnabled(false);
                    } else if (gameMode == InterfaceConfiguration.MODE_AI_VS_AI) {
                        // Démarrer la partie IA vs IA
                        iaFirstMoveButton.setEnabled(false);
                        makeAIMove(true);
                    }
                }
            });

            // Adapter le libellé du bouton selon le mode
            if (gameMode == InterfaceConfiguration.MODE_AI_VS_AI) {
                iaFirstMoveButton.setText("Démarrer IA vs IA");
            }

            controlPanel.add(iaFirstMoveButton);

        }

        frame.setLayout(new BorderLayout());
        frame.add(niv, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        updateStatus();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }
}
