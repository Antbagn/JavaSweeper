import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sweeper.*;
import sweeper.Box;

public class JavaSweeper extends JFrame implements WindowListener {
    private Game game;
    private final int IMAGE_SIZE=50;
    //private final int COLS=9;
    //private final int BOMBS=10;
    //private final int ROWS=9;
    private JPanel panel;
    private JLabel label;
    static JavaSweeper javaSweeper;
    //int hardlvl;
    JMenuBar jMenuBar= new JMenuBar();
    public static void main(String[] args) {
        javaSweeper=new JavaSweeper(9,9,10);
    }
    private JavaSweeper(int COLS,int ROWS,int BOMBS){
        game=new Game(COLS,ROWS,BOMBS);
        game.start();
        setImages();
        intiLabel();
        initMenuBar(BOMBS);
        initPanel();
        initFrame();
        }

    private void initMenuBar(int Bomb) {
        JMenu dificalty=new JMenu("dificalty");
        jMenuBar.add(dificalty);
        JMenuItem novice=dificalty.add(new JMenuItem("Novice"));
        JMenuItem normal=dificalty.add(new JMenuItem("Normal"));
        JMenuItem expert=dificalty.add(new JMenuItem("Expert"));
        switch (Bomb){
            case 10:novice.setEnabled(false);break;
            case 40:normal.setEnabled(false);break;
            case 99:expert.setEnabled(false);break;
        }

        setJMenuBar(jMenuBar);
        novice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //hardlvl=1;
                javaSweeper.setVisible(false);
                javaSweeper=new JavaSweeper(9,9,10);

            }
        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //hardlvl=2;
                javaSweeper.setVisible(false);
                javaSweeper= new JavaSweeper(16,16,40);
            }
        });
        expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //hardlvl=3;
                javaSweeper.setVisible(false);
                javaSweeper= new JavaSweeper(30,16,99);
            }
        });
    }

    private void intiLabel(){
        label=new JLabel("Welcome");
        add(label,BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel= new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord:Ranges.getAllCoords()){

                    g.drawImage((Image)game.getBox(coord).image,coord.x*IMAGE_SIZE,coord.y*IMAGE_SIZE,this);
                }


            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x=e.getX()/IMAGE_SIZE;
                int y=e.getY()/IMAGE_SIZE;
                Coord coord=new Coord(x,y);
                if(e.getButton()==MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if(e.getButton()==MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if(e.getButton()==MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                panel.repaint();
                if(game.getState()== GameState.WINNER){JOptionPane.showMessageDialog(null,"ПОЗДРАВЛЯЕМ ВЫ ПОБЕДИЛИ");}
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE,
                                             Ranges.getSize().y*IMAGE_SIZE));
        add(panel);

    }
    private String getMessage(){
        switch (game.getState()){
            case PLAYED:return "Think twice!";
            case BOMBED:return "YOU LOSE!";
            case WINNER:return "if you want to start a new game, click on any button";
            default:return "Welcome";
        }
    }


    private void initFrame() {
        initPanel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setVisible(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));

    }
    public void setImages(){
        for(Box box:Box.values())
            box.image=getImage(box.name().toLowerCase());

    }
    private Image getImage(String name){
        String filename="img/"+name+".png";
        ImageIcon icon=new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        e.getSource();

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
