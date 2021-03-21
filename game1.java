import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class GameField extends JPanel implements ActionListener{
    private Image D;
    private Image apple;
    private final int A = 400;
    private final int B = 16;
    private final int C = 400;
    private int X;
    private int Y;
    private int[] x = new int[C];
    private int[] y = new int[C];
    private int M;
    private Timer T;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean IN = true;


    public GameField(){
        setBackground(Color.white);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame(){
        M = 3;
        for (int i = 0; i < M; i++) {
            x[i] = 48 - i*B;
            y[i] = 48;
        }
        T = new Timer(250,this);
        T.start();
        createApple();
    }

    public void createApple(){
        X = new Random().nextInt(20)*B;
        Y = new Random().nextInt(20)*B;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("2.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("1.png");
        D = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(IN){
            g.drawImage(apple,X,Y,this);
            for (int i = 0; i < M; i++) {
                g.drawImage(D,x[i],y[i],this);
            }
        } else{
            String str = "Game Over";
            //Font f = new Font("Arial",35,Font.BOLD);
            g.setColor(Color.BLACK);
            // g.setFont(f);
            g.drawString(str,170,A/3);
        }
    }

    public void move(){
        for (int i = M; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= B;
        }
        if(right){
            x[0] += B;
        } if(up){
            y[0] -= B;
        } if(down){
            y[0] += B;
        }
    }

    public void checkApple(){
        if(x[0] == X && y[0] == Y){
            M++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = M; i >0 ; i--) {
            if (i>4 && x[0] == x[i] && y[0] == y[i]){
                IN = false;
            }
        }

        if(x[0]>A){
            IN = false;
        }
        if(x[0]<0){
            IN = false;
        }
        if(y[0]>A){
            IN = false;
        }
        if(y[0]<0){
            IN = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(IN){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}
