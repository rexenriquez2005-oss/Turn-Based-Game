import javax.swing.*;
import java.awt.*;

class ImageBackgroundPanel extends JPanel {
    private Image backgroundImage;

    public ImageBackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class GameGUI extends JFrame {

    private JPanel gamePanel;
    private CardLayout gameCardLayout;
    private Hero[] myTeam;
    private Hero[] enemyTeam;
    private boolean isPlayerTurn = true;
    private boolean isAi = true;
    private int count = 0;

    private JButton skill1Btn, skill2Btn, skill3Btn, basicAttackBtn;
    private int currentPlayerIndex = 0;
    private boolean isPlayerTeamTurn = true;
    private JPanel enemyTeamPanel;
    private JPanel playerTeamPanel;
    private Hero selectedTarget = null;
    private int selectedSkillIndex = -1;
    private JLabel battleLogLabel;

    public GameGUI() {
        myTeam = new Hero[3];
        enemyTeam = new Hero[3];

        gameCardLayout = new CardLayout();
        gamePanel = new JPanel(gameCardLayout);

        setTitle("Shadows of Eldora");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainScreen = createMainScreen();
        JPanel gameModeScreen = createGameModeScreen();
        JPanel playScreen = createPlayScreen();
        JPanel selectionScreen = createSelectionScreen();
        JPanel battleScreen = createBattleScreen();

        gamePanel.add(mainScreen, "MainScreen");
        gamePanel.add(gameModeScreen, "GameMode");
        gamePanel.add(playScreen, "PlayScreen");
        gamePanel.add(selectionScreen, "SelectionScreen");
        gamePanel.add(battleScreen, "BattleScreen");

        add(gamePanel);
        gameCardLayout.show(gamePanel, "MainScreen");

        setVisible(true);
    }

    private JPanel createMainScreen() {
        ImageBackgroundPanel menuPanel = new ImageBackgroundPanel("/assets/castle.png");
        menuPanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JLabel menuLogo = new JLabel("Shadows of Eldora");
        menuLogo.setFont(new Font("Serif", Font.BOLD, 84));
        menuLogo.setForeground(Color.WHITE);
        menuLogo.setHorizontalAlignment(SwingConstants.CENTER);
        menuLogo.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));

        JPanel menuButtons = new JPanel(new GridLayout(3, 1, 15, 15));
        menuButtons.setOpaque(false);
        menuButtons.setBorder(BorderFactory.createEmptyBorder(0, 300, 200, 300));

        JButton playButton = new JButton("Play");
        JButton gameModeButton = new JButton("Game Mode");
        JButton exitButton = new JButton("Exit");

        JButton[] buttons = { playButton, gameModeButton, exitButton };
        for (JButton b : buttons) {
            b.setFont(new Font("Serif", Font.BOLD, 32));
            b.setForeground(Color.WHITE);
            styleButton(b);
            menuButtons.add(b);
        }

        playButton.addActionListener(e -> gameCardLayout.show(gamePanel, "PlayScreen"));
        gameModeButton.addActionListener(e -> gameCardLayout.show(gamePanel, "GameMode"));
        exitButton.addActionListener(e -> System.exit(0));

        centerPanel.add(menuLogo, BorderLayout.NORTH);
        centerPanel.add(menuButtons, BorderLayout.CENTER);
        menuPanel.add(centerPanel, BorderLayout.CENTER);

        return menuPanel;
    }

    private JPanel createGameModeScreen() {
        ImageBackgroundPanel settingPanel = new ImageBackgroundPanel("/assets/eldora-background.png");
        settingPanel.setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        JButton pvpButton = new JButton("PLAYER VS PLAYER");
        JButton pvaiButton = new JButton("PLAYER VS AI");
        JButton[] buttons = { pvpButton, pvaiButton };

        for (JButton b : buttons) {
            b.setFont(new Font("Serif", Font.BOLD, 32));
            b.setForeground(Color.WHITE);
            styleButton(b);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);

            center.add(Box.createRigidArea(new Dimension(0, 30)));
            center.add(b);
        }

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(center);
        settingPanel.add(wrapper, BorderLayout.CENTER);

        pvpButton.addActionListener(e -> {
            isAi = false;
            gameCardLayout.show(gamePanel, "MainScreen");
        });

        pvaiButton.addActionListener(e -> {
            isAi = true;
            gameCardLayout.show(gamePanel, "MainScreen");
        });

        return settingPanel;
    }

    private JPanel createPlayScreen() {
        ImageBackgroundPanel playScreen = new ImageBackgroundPanel("/assets/eldora-background.png");
        playScreen.setLayout(new BorderLayout());

        JPanel playIntro = new JPanel(new BorderLayout());
        playIntro.setOpaque(false);

        JTextArea playStory = new JTextArea(
                "Long ago, the peaceful village of Eldora was destroyed by the Overlord of Shadows, " +
                        "leaving five childhood friends as its only survivors. Adrian the Warrior, Jhush the " +
                        "Assassin, Cyberg the Mage, Rex the Archer, and Clarence the Tank swore a blood oath " +
                        "to fight as brothers and avenge their fallen home.\n\n" +
                        "Each trained in their own discipline: strength, speed, magic, precision, and defense, " +
                        "becoming the last sons of Eldonia. United by brotherhood and destiny, they now battle " +
                        "together to restore light to the kingdom.");
        playStory.setForeground(Color.WHITE);
        playStory.setFont(new Font("Serif", Font.PLAIN, 18));
        playStory.setOpaque(false);
        playStory.setEditable(false);
        playStory.setFocusable(false);
        playStory.setLineWrap(true);
        playStory.setWrapStyleWord(true);
        playStory.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JScrollPane scrollPane = new JScrollPane(playStory);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        playIntro.add(scrollPane, BorderLayout.CENTER);

        JPanel playButtonPanel = new JPanel();
        playButtonPanel.setOpaque(false);
        JButton playContinue = new JButton("CONTINUE");
        playContinue.setFont(new Font("Serif", Font.BOLD, 32));
        playContinue.setForeground(Color.WHITE);
        styleButton(playContinue);
        playButtonPanel.add(playContinue);

        playContinue.addActionListener(e -> gameCardLayout.show(gamePanel, "SelectionScreen"));

        playScreen.add(playIntro, BorderLayout.CENTER);
        playScreen.add(playButtonPanel, BorderLayout.SOUTH);

        return playScreen;
    }

    private JPanel createSelectionScreen() {
        ImageBackgroundPanel selectionScreen = new ImageBackgroundPanel("/assets/eldora-background.png");
        selectionScreen.setLayout(new BorderLayout());

        JPanel heroGrid = new JPanel(new GridBagLayout());
        heroGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 30, 20, 30);

        JPanel archerPanel = createHeroPanel("ARCHER", "HP: 160", "MP: 140");
        JPanel warriorPanel = createHeroPanel("WARRIOR", "HP: 200", "MP: 120");
        JPanel magePanel = createHeroPanel("MAGE", "HP: 120", "MP: 180");
        JPanel assassinPanel = createHeroPanel("ASSASSIN", "HP: 140", "MP: 150");
        JPanel tankPanel = createHeroPanel("TANK", "HP: 250", "MP: 100");

        gbc.gridx = 0;
        gbc.gridy = 0;
        heroGrid.add(archerPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        heroGrid.add(warriorPanel, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        heroGrid.add(magePanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        heroGrid.add(assassinPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        heroGrid.add(tankPanel, gbc);

        selectionScreen.add(heroGrid, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Serif", Font.BOLD, 28));
        backButton.setForeground(Color.WHITE);
        styleButton(backButton);

        JButton continueButton = new JButton("CONTINUE");
        continueButton.setFont(new Font("Serif", Font.BOLD, 28));
        continueButton.setForeground(Color.WHITE);
        continueButton.setEnabled(false);
        styleButton(continueButton);

        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(continueButton, BorderLayout.EAST);
        selectionScreen.add(bottomPanel, BorderLayout.SOUTH);

        addHeroClick(archerPanel, new Archer("Rex"), continueButton);
        addHeroClick(warriorPanel, new Warrior("Adrian"), continueButton);
        addHeroClick(magePanel, new Mage("Cyberg"), continueButton);
        addHeroClick(assassinPanel, new Assassin("Jhush"), continueButton);
        addHeroClick(tankPanel, new Tank("Clarence"), continueButton);

        backButton.addActionListener(e -> gameCardLayout.show(gamePanel, "PlayScreen"));
        continueButton.addActionListener(e -> {
            updateBattleDisplay();
            gameCardLayout.show(gamePanel, "BattleScreen");
        });

        return selectionScreen;
    }

    private JPanel createHeroPanel(String name, String hp, String mp) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(180, 180));
        panel.setBackground(new Color(60, 60, 90, 180));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        JLabel heroName = new JLabel(name, SwingConstants.CENTER);
        heroName.setFont(new Font("Serif", Font.BOLD, 20));
        heroName.setForeground(Color.WHITE);
        heroName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heroHP = new JLabel(hp, SwingConstants.CENTER);
        heroHP.setForeground(Color.LIGHT_GRAY);
        heroHP.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heroMP = new JLabel(mp, SwingConstants.CENTER);
        heroMP.setForeground(Color.LIGHT_GRAY);
        heroMP.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(heroName);
        panel.add(Box.createVerticalStrut(10));
        panel.add(heroHP);
        panel.add(heroMP);
        panel.add(Box.createVerticalGlue());

        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return panel;
    }

    private void addHeroClick(JPanel panel, Hero hero, JButton continueButton) {
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (count >= 3) {
                    continueButton.setEnabled(true);
                    return;
                }
                if (isPlayerTurn) {
                    myTeam[count] = hero;
                    System.out.println("Player selected: " + hero.getName());
                } else {
                    enemyTeam[count] = hero;
                    System.out.println("Enemy selected: " + hero.getName());
                }

                count++;

                if (count == 3) {
                    if (isPlayerTurn) {
                        if (isAi) {
                            selectRandomTeamForAI();
                            System.out.println("AI team automatically selected!");
                            System.out.println("Both teams ready! Starting battle...");
                            updateBattleDisplay();
                            gameCardLayout.show(gamePanel, "BattleScreen");
                        } else {
                            isPlayerTurn = false;
                            count = 0;
                            System.out.println("Now selecting enemy team...");
                        }
                    } else {
                        System.out.println("Both teams ready! Starting battle...");
                        updateBattleDisplay();
                        gameCardLayout.show(gamePanel, "BattleScreen");
                    }
                }
            }
        });
    }

    private void selectRandomTeamForAI() {
        java.util.Random rand = new java.util.Random();
        Hero[] availableHeroes = {
                new Archer("Rex"),
                new Warrior("Adrian"),
                new Mage("Cyberg"),
                new Assassin("Jhush"),
                new Tank("Clarence")
        };
        for (int i = 0; i < 3; i++) {
            int aiIndex = rand.nextInt(availableHeroes.length);
            enemyTeam[i] = availableHeroes[aiIndex];
            System.out.println("AI selected: " + enemyTeam[i].getName());
        }
    }

    private JPanel createBattleScreen() {
        ImageBackgroundPanel battleScreen = new ImageBackgroundPanel("/assets/eldora-background.png");
        battleScreen.setLayout(new BorderLayout());

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        battleLogLabel = new JLabel("Battle Start! Player's Turn", SwingConstants.CENTER);
        battleLogLabel.setFont(new Font("Serif", Font.BOLD, 20));
        battleLogLabel.setForeground(Color.YELLOW);
        battleLogLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        battleLogLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        enemyTeamPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        enemyTeamPanel.setOpaque(false);
        enemyTeamPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        playerTeamPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        playerTeamPanel.setOpaque(false);
        playerTeamPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));

        skill1Btn = new JButton("Skill 1");
        skill2Btn = new JButton("Skill 2");
        skill3Btn = new JButton("Skill 3");
        basicAttackBtn = new JButton("Basic Attack");

        JButton[] buttons = { skill1Btn, skill2Btn, skill3Btn, basicAttackBtn };

        for (JButton btn : buttons) {
            btn.setFont(new Font("Serif", Font.BOLD, 18));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(60, 60, 60));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            btn.setOpaque(true);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(130, 45));
            actionPanel.add(btn);
        }

        skill1Btn.addActionListener(e -> selectSkill(0));
        skill2Btn.addActionListener(e -> selectSkill(1));
        skill3Btn.addActionListener(e -> selectSkill(2));
        basicAttackBtn.addActionListener(e -> selectSkill(3));

        mainContent.add(battleLogLabel);
        mainContent.add(enemyTeamPanel);
        mainContent.add(Box.createVerticalGlue());
        mainContent.add(playerTeamPanel);
        mainContent.add(actionPanel);

        battleScreen.add(mainContent, BorderLayout.CENTER);

        return battleScreen;
    }

    private void updateBattleDisplay() {
        enemyTeamPanel.removeAll();
        playerTeamPanel.removeAll();

        for (int i = 0; i < enemyTeam.length; i++) {
            Hero enemy = enemyTeam[i];
            if (enemy != null && enemy.isAlive()) {
                enemyTeamPanel.add(createBattleHeroPanel(enemy, false, i));
            }
        }

        for (int i = 0; i < myTeam.length; i++) {
            Hero player = myTeam[i];
            if (player != null && player.isAlive()) {
                playerTeamPanel.add(createBattleHeroPanel(player, true, i));
            }
        }

        updateSkillButtons();

        enemyTeamPanel.revalidate();
        enemyTeamPanel.repaint();
        playerTeamPanel.revalidate();
        playerTeamPanel.repaint();
    }

    private void updateSkillButtons() {
        Hero currentHero;

        if (isPlayerTeamTurn) {
            currentHero = myTeam[currentPlayerIndex];
        } else {
            currentHero = enemyTeam[currentPlayerIndex];
        }

        if (currentHero != null) {
            skill1Btn.setText(currentHero.skill[0].getSkill_Name());
            skill1Btn.setEnabled(currentHero.isManaEnough(currentHero.skill[0]));

            skill2Btn.setText(currentHero.skill[1].getSkill_Name());
            skill2Btn.setEnabled(currentHero.isManaEnough(currentHero.skill[1]));

            skill3Btn.setText(currentHero.skill[2].getSkill_Name());
            skill3Btn.setEnabled(currentHero.isManaEnough(currentHero.skill[2]));

            basicAttackBtn.setEnabled(true);
        }
    }

    private void selectSkill(int skillIndex) {
        Hero currentHero = myTeam[currentPlayerIndex];

        if (skillIndex < 3 && !currentHero.isManaEnough(currentHero.skill[skillIndex])) {
            battleLogLabel.setText("Not enough mana! Select another skill.");
            return;
        }

        selectedSkillIndex = skillIndex;
        String skillName = skillIndex == 3 ? "Basic Attack" : currentHero.skill[skillIndex].getSkill_Name();
        battleLogLabel.setText(currentHero.getName() + " selected " + skillName + " | Now select a target!");
    }

    private void selectTarget(Hero target) {
        boolean hasTank = false;
        Hero tankHero = null;
        for (Hero enemy : enemyTeam) {
            if (enemy != null && enemy.isAlive() && enemy.isTank()) {
                hasTank = true;
                tankHero = enemy;
                break;
            }
        }

        if (hasTank && target != tankHero) {
            battleLogLabel.setText("Cannot attack! " + tankHero.getName() + " (Tank) must be targeted first!");
            return;
        }

        selectedTarget = target;
        executeAttack();
    }

    private void executeAttack() {
        Hero attacker = myTeam[currentPlayerIndex];

        attacker.attack(selectedSkillIndex, selectedTarget);

        updateBattleDisplay();

        if (!selectedTarget.isAlive()) {
            battleLogLabel.setText(selectedTarget.getName() + " has been defeated!");
        }

        Timer turnTimer = new Timer(1500, e -> nextPlayerTurn());
        turnTimer.setRepeats(false);
        turnTimer.start();
    }

    private void executeAITurn() {
        Hero aiHero = enemyTeam[currentPlayerIndex];

        int skillToUse = -1;
        for (int i = 2; i >= 0; i--) {
            if (aiHero.isManaEnough(aiHero.skill[i])) {
                skillToUse = i;
                break;
            }
        }

        if (skillToUse == -1) {
            skillToUse = 3;
        }

        Hero target = null;
        boolean playerHasTank = false;
        Hero playerTank = null;

        for (Hero player : myTeam) {
            if (player != null && player.isAlive() && player.isTank()) {
                playerHasTank = true;
                playerTank = player;
                break;
            }
        }

        if (playerHasTank) {
            target = playerTank;
        } else {
            int lowestHP = Integer.MAX_VALUE;
            for (Hero player : myTeam) {
                if (player != null && player.isAlive() && player.getHp() < lowestHP) {
                    lowestHP = player.getHp();
                    target = player;
                }
            }
        }

        if (target != null) {
            aiHero.attack(skillToUse, target);
            updateBattleDisplay();

            if (!target.isAlive()) {
                battleLogLabel.setText(target.getName() + " has been defeated!");
            }
        }
    }

    private void nextPlayerTurn() {
        selectedTarget = null;
        selectedSkillIndex = -1;

        currentPlayerIndex++;

        if (isPlayerTeamTurn) {
            while (currentPlayerIndex < 3
                    && (myTeam[currentPlayerIndex] == null || !myTeam[currentPlayerIndex].isAlive())) {
                currentPlayerIndex++;
            }
        } else {
            while (currentPlayerIndex < 3
                    && (enemyTeam[currentPlayerIndex] == null || !enemyTeam[currentPlayerIndex].isAlive())) {
                currentPlayerIndex++;
            }
        }

        if (currentPlayerIndex >= 3) {
            currentPlayerIndex = 0;
            isPlayerTeamTurn = !isPlayerTeamTurn;

            if (isPlayerTeamTurn) {
                while (currentPlayerIndex < 3
                        && (myTeam[currentPlayerIndex] == null || !myTeam[currentPlayerIndex].isAlive())) {
                    currentPlayerIndex++;
                }
            } else {
                while (currentPlayerIndex < 3
                        && (enemyTeam[currentPlayerIndex] == null || !enemyTeam[currentPlayerIndex].isAlive())) {
                    currentPlayerIndex++;
                }
            }
        }

        if (checkGameOver()) {
            return;
        }

        updateSkillButtons();
        updateBattleDisplay();

        if (isPlayerTeamTurn) {
            Hero currentHero = myTeam[currentPlayerIndex];
            battleLogLabel.setText("Player's Turn - " + currentHero.getName() + " | Select a skill then a target");
        } else {
            Hero currentHero = enemyTeam[currentPlayerIndex];
            battleLogLabel.setText("Enemy's Turn - " + currentHero.getName());

            if (isAi) {
                Timer aiTimer = new Timer(1500, e -> executeAITurn());
                aiTimer.setRepeats(false);
                aiTimer.start();
            }
        }
    }

    private boolean checkGameOver() {
        boolean playerTeamAlive = false;
        boolean enemyTeamAlive = false;

        for (Hero hero : myTeam) {
            if (hero != null && hero.isAlive()) {
                playerTeamAlive = true;
                break;
            }
        }

        for (Hero hero : enemyTeam) {
            if (hero != null && hero.isAlive()) {
                enemyTeamAlive = true;
                break;
            }
        }

        if (!playerTeamAlive) {
            battleLogLabel.setText("GAME OVER - Enemy Team Wins!");
            disableAllButtons();
            return true;
        }

        if (!enemyTeamAlive) {
            battleLogLabel.setText("VICTORY - Player Team Wins!");
            disableAllButtons();
            return true;
        }

        return false;
    }

    private void disableAllButtons() {
        skill1Btn.setEnabled(false);
        skill2Btn.setEnabled(false);
        skill3Btn.setEnabled(false);
        basicAttackBtn.setEnabled(false);
    }

    private JPanel createBattleHeroPanel(Hero hero, boolean isPlayer, int heroIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 100));

        boolean isActive = false;
        if (isPlayer && isPlayerTeamTurn && heroIndex == currentPlayerIndex) {
            isActive = true;
        } else if (!isPlayer && !isPlayerTeamTurn && heroIndex == currentPlayerIndex) {
            isActive = true;
        }

        if (isActive) {
            panel.setBackground(new Color(80, 80, 120, 255));
        } else {
            panel.setBackground(new Color(50, 50, 50, 150));
        }

        panel.setBorder(BorderFactory.createLineBorder(isActive ? Color.YELLOW : Color.WHITE, isActive ? 3 : 2));

        JLabel name = new JLabel(hero.getName(), SwingConstants.CENTER);
        name.setFont(new Font("Serif", Font.BOLD, 20));
        name.setForeground(Color.WHITE);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hp = new JLabel("HP: " + hero.getHp(), SwingConstants.CENTER);
        hp.setFont(new Font("Serif", Font.BOLD, 16));
        hp.setForeground(isPlayer ? Color.GREEN : Color.RED);
        hp.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mp = new JLabel("MP: " + hero.getMp(), SwingConstants.CENTER);
        mp.setFont(new Font("Serif", Font.BOLD, 16));
        mp.setForeground(isPlayer ? Color.CYAN : Color.BLUE);
        mp.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(10));
        panel.add(name);
        panel.add(Box.createVerticalStrut(5));
        panel.add(hp);
        panel.add(mp);

        if (!isPlayer && isPlayerTeamTurn) {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (selectedSkillIndex != -1) {
                        selectTarget(hero);
                    } else {
                        battleLogLabel.setText("Select a skill first!");
                    }
                }
            });
        }

        return panel;
    }

    private void styleButton(JButton b) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGUI::new);
    }
}