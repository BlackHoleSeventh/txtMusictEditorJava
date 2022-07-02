import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;

public class JFrameWindow2 extends JFrame{
    // 定义组件
    JPanel jp1, jp2, jp3;
    JLabel jlb1, jlb2;
    JButton jb1, jb2, jb3, jb4;
    JScrollPane scrollPane;
    JEditorPane text;
    JTextField sleepTime;

    int format_width = 800;
    int format_height = 800;


    private static boolean writeBoolean = false;

    public static void createWindow() {
        // TODO Auto-generated method stub
        JFrameWindow2 d1 = new JFrameWindow2();

    }

    // 构造函数
    public JFrameWindow2() {
        try {
            InputStream imgIS = this.getClass().getResourceAsStream("z.png");
            Image image = ImageIO.read(imgIS);
            this.setIconImage(image);
        }catch (Exception e){ }
        setResizable(false);
        setLocation(getToolkit().getScreenSize().width/4, getToolkit().getScreenSize().height/100);
        setSize(1000, 1000);

        jp1 = new JPanel();
        //jp1.setSize(format_width,500);
        jp2 = new JPanel();
        //jp2.setSize(800,800);
        jp3 = new JPanel();
        //jp3.setSize(format_width,500);

        jlb1 = new JLabel("简谱内容");

        jb1 = new JButton("开始录制");
        jb2 = new JButton("停止录制");
        jb3 = new JButton("开始试听");
        jb4 = new JButton("停止试听");




        text = new JEditorPane();
        text.setSize(800,800);
        text.setPreferredSize(new Dimension(800,850));


        scrollPane = new JScrollPane(text);
        //scrollPane.setPreferredSize(new Dimension(800,800));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        sleepTime = new JTextField(30);


        Font font = new Font("宋体",Font.PLAIN,22);
        text.setFont(font);
        sleepTime.setFont(font);
        jlb1.setFont(font);
        jb1.setFont(font);
        jb2.setFont(font);
        jb3.setFont(font);
        jb4.setFont(font);

        //GridLayout gl = new GridLayout(3, 1);
        //this.setLayout(gl);

        // 加入各个组件
        //第一行
        jp1.add(jlb1);

        //第二行
        jp2.add(scrollPane);

        //第三行
        jp3.add(jb1);
        jp3.add(jb2);
        jp3.add(jb3);
        jp3.add(jb4);

        // 加入到JFrame
        this.add(jp1,BorderLayout.NORTH);
        this.add(jp2,BorderLayout.CENTER);
        this.add(jp3,BorderLayout.SOUTH);

        setListener(jb1, jb2, text, sleepTime);

        listenKeyPress(text);

        this.setTitle("TXT音乐播放器简谱制作器");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


    }

    //暂停与继续
    private void setListener2(JButton jb3, JButton jb4, final JTextField sleepTime) {
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(){
                    @Override
                    public void run() {
                        ReadUtil.isPlay = false;
                        ReadUtil.canCon = true;
                    }
                }.start();
            }
        });

        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(){
                    @Override
                    public void run() {
                        if(ReadUtil.canCon) {
                            ReadUtil.canCon = false;
                            ReadUtil.isPlay = true;
                            ReadUtil.sleepTime = getSleepTime(sleepTime.getText());
                            ReadUtil.continueMusic();
                        }
                    }
                }.start();
            }
        });
    }


    public static int getSleepTime(String str){
        try{
            int time = Integer.parseInt(str);
            if(time < 0 || time > 5000){
                time = 500;
            }
            return time;
        }catch (Exception e){
            return 500;
        }
    }


    //设置
    public static void setListener(JButton jb1, JButton jb2, final JEditorPane text, final JTextField sleepTime){


        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Thread(){
                    @Override
                    public void run() {
                        writeBoolean = true;
                        while (writeBoolean){
                            text.requestFocus();
                            KeyUtil.pressKongGe();
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }.start();

            }
        });

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        writeBoolean = false;
                    }
                }.start();
            }
        });
    }



    private void listenKeyPress(JEditorPane text){
        // 添加键盘监听
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_NUMPAD1) {
                    ReadUtil.play2("z1");
                }else if(keyCode == KeyEvent.VK_NUMPAD2){
                    ReadUtil.play2("z2");
                }else if(keyCode == KeyEvent.VK_NUMPAD3){
                    ReadUtil.play2("z3");
                }
            }
        });

    }
}
