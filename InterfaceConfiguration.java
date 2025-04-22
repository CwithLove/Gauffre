
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class InterfaceConfiguration {

    private JFrame frame;
    private JSpinner rowsSpinner;
    private JSpinner colsSpinner;
    private JComboBox<String> gameModeChoice;
    private JComboBox<String> iaLevelChoice1;
    private JComboBox<String> iaLevelChoice2;
    private JPanel iaSettingsPanel;

    public static final int MODE_PLAYER_VS_PLAYER = 0;
    public static final int MODE_PLAYER_VS_AI = 1;
    public static final int MODE_AI_VS_AI = 2;

    public InterfaceConfiguration() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Configuration du Jeu de la Gauffre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Taille du plateau
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sizePanel.add(new JLabel("Taille du plateau: "));

        JPanel spinnerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        spinnerPanel.add(new JLabel("Lignes:"));
        rowsSpinner = new JSpinner(new SpinnerNumberModel(8, 3, 20, 1));
        spinnerPanel.add(rowsSpinner);

        spinnerPanel.add(new JLabel("Colonnes:"));
        colsSpinner = new JSpinner(new SpinnerNumberModel(8, 3, 20, 1));
        spinnerPanel.add(colsSpinner);

        sizePanel.add(spinnerPanel);
        mainPanel.add(sizePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Mode de jeu
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modePanel.add(new JLabel("Mode de jeu: "));
        String[] modes = {"Joueur contre Joueur", "Joueur contre IA", "IA contre IA"};
        gameModeChoice = new JComboBox<>(modes);
        modePanel.add(gameModeChoice);
        mainPanel.add(modePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Paramètres IA
        iaSettingsPanel = new JPanel();
        iaSettingsPanel.setLayout(new BoxLayout(iaSettingsPanel, BoxLayout.Y_AXIS));

        JPanel ia1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ia1Panel.add(new JLabel("Niveau IA 1: "));
        String[] iaLevels = {"easy", "medium", "hard", "expert"};
        iaLevelChoice1 = new JComboBox<>(iaLevels);
        iaLevelChoice1.setSelectedIndex(1); // Medium par défaut
        ia1Panel.add(iaLevelChoice1);

        JPanel ia2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ia2Panel.add(new JLabel("Niveau IA 2: "));
        iaLevelChoice2 = new JComboBox<>(iaLevels);
        iaLevelChoice2.setSelectedIndex(1); // Medium par défaut
        ia2Panel.add(iaLevelChoice2);

        iaSettingsPanel.add(ia1Panel);
        iaSettingsPanel.add(ia2Panel);
        mainPanel.add(iaSettingsPanel);

        // Ajout du listener pour activer/désactiver les paramètres IA
        gameModeChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateIASettingsVisibility();
            }
        });

        // Bouton de démarrage
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startButton = new JButton("Démarrer la partie");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        buttonPanel.add(startButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        updateIASettingsVisibility();
    }

    private void updateIASettingsVisibility() {
        int selectedMode = gameModeChoice.getSelectedIndex();

        switch (selectedMode) {
            case MODE_PLAYER_VS_PLAYER:
                iaSettingsPanel.setVisible(false);
                break;
            case MODE_PLAYER_VS_AI:
                iaSettingsPanel.setVisible(true);
                iaLevelChoice1.setEnabled(true);
                iaLevelChoice2.setEnabled(false);
                iaSettingsPanel.getComponent(0).setVisible(true);
                iaSettingsPanel.getComponent(1).setVisible(false);
                break;
            case MODE_AI_VS_AI:
                iaSettingsPanel.setVisible(true);
                iaLevelChoice1.setEnabled(true);
                iaLevelChoice2.setEnabled(true);
                iaSettingsPanel.getComponent(0).setVisible(true);
                iaSettingsPanel.getComponent(1).setVisible(true);
                break;
        }
    }

    private void startGame() {
        int rows = (Integer) rowsSpinner.getValue();
        int cols = (Integer) colsSpinner.getValue();
        int gameMode = gameModeChoice.getSelectedIndex();
        String iaLevel1 = "medium";
        String iaLevel2 = "medium";

        switch (gameMode) {
            case MODE_PLAYER_VS_PLAYER:
                break;
            case MODE_PLAYER_VS_AI:
                iaLevel1 = (String) iaLevelChoice1.getSelectedItem();
                break;
            case MODE_AI_VS_AI:
                iaLevel1 = (String) iaLevelChoice1.getSelectedItem();
                iaLevel2 = (String) iaLevelChoice2.getSelectedItem();
                break;
        }

        Niveau niveau = new Niveau(rows, cols);
        Jeu jeu = new Jeu(niveau);

        InterfaceGraphique.demarrer(jeu, gameMode, iaLevel1, iaLevel2);

        frame.dispose();
    }

    public void show() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceConfiguration().show();
            }
        });
    }
}
