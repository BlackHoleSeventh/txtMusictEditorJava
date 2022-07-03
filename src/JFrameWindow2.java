import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.InputStream;

public class JFrameWindow2 extends JFrame{
    // 定义组件
    JPanel jp1, jp2, jp3;
    JLabel jlb1, jlb2;
    JButton jb1, jb2, jb3, jb4 ,jb5, jb6;
    JScrollPane scrollPane;
    JEditorPane text;
    JTextField sleepTime;

    int format_width = 800;
    int format_height = 800;

    private static StringBuffer sb = new StringBuffer();


    private static boolean writeBoolean = false;

    private static boolean needPressAgain = true;

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
        setLocation(getToolkit().getScreenSize().width/6, getToolkit().getScreenSize().height/100);
        setSize(1200, 1000);

        jp1 = new JPanel();
        //jp1.setSize(format_width,500);
        jp2 = new JPanel();
        //jp2.setSize(800,800);
        jp3 = new JPanel();
        //jp3.setSize(1200,500);

        jlb1 = new JLabel("简谱内容");

        jb1 = new JButton("开始录制(z)");
        jb2 = new JButton("停止录制(x)");
        jb5 = new JButton("清屏(b)");
        jb6 = new JButton("复制到剪贴板(space)");
        jb3 = new JButton("开始试听(c)");
        jb4 = new JButton("停止试听(v)");




        text = new JEditorPane();
        //text.setSize(1000,800);
        text.setPreferredSize(new Dimension(900,870));
        text.setEditable(false);


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
        jb5.setFont(font);
        jb6.setFont(font);
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
        jp3.add(jb6);
        jp3.add(jb5);

        // 加入到JFrame
        this.add(jp1,BorderLayout.NORTH);
        this.add(jp2,BorderLayout.CENTER);
        this.add(jp3,BorderLayout.SOUTH);

        setListener(jb1, jb2, text, sleepTime);

        setListener2(jb5, jb6);

        setListener3(jb3, jb4);

        listenKeyPress(text);

        this.setTitle("TXT音乐播放器简谱制作器");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    //播放与停止播放
    private void setListener3(JButton jb3,JButton jb4) {
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(){
                    @Override
                    public void run() {

                        char[] chars = sb.toString().toCharArray();
                        //清空播放缓存数组
                        ReadUtil.file.clear();
                        //暂停记录从0开始
                        ReadUtil.current = 0;
                        ReadUtil.isPlay = true;
                        //间隔时间固定100
                        ReadUtil.sleepTime = 100;
                        ReadUtil.readCharArray(chars);

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
                        ReadUtil.isPlay = false;
                        ReadUtil.current = 0;
                    }
                }.start();
            }
        });
    }

    //清屏
    private void setListener2(JButton jb5,JButton jb6) {
        jb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(){
                    @Override
                    public void run() {
                        sb = new StringBuffer();
                        text.setText(sb.toString());
                        text.requestFocus();
                    }
                }.start();
            }
        });

        jb6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(){
                    @Override
                    public void run() {
                        //不知道为什么按numlock会出现这样的，需要处理
                        if(sb.toString().contains("[4][4][4]")){
                            sb = new StringBuffer(sb.toString().replaceAll("\\[4\\]\\[4\\]\\[4\\]","\\[4\\]"));
                        }
                        //如果还有
                        if(sb.toString().contains("[4][4]")){
                            sb = new StringBuffer(sb.toString().replaceAll("\\[4\\]\\[4\\]","\\[4\\]"));
                        }
                        //如果还有
                        if(sb.toString().contains("[4][4][4][4]")){
                            sb = new StringBuffer(sb.toString().replaceAll("\\[4\\]\\[4\\]\\[4\\]\\[4\\]","\\[4\\]"));
                        }

                        text.setText(sb.toString());
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(sb.toString()),null);
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
                            sb.append(" ");
                            text.setText(sb.toString());

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
                        text.requestFocus();
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


                    switch (keyCode) {
                        case KeyEvent.VK_NUMPAD1: ReadUtil.play2("z1");sb.append("1");break;
                        case KeyEvent.VK_NUMPAD2: ReadUtil.play2("z2");sb.append("2");break;
                        case KeyEvent.VK_NUMPAD3: ReadUtil.play2("z3");sb.append("3");break;
                        case KeyEvent.VK_NUMPAD4: ReadUtil.play2("z4");sb.append("4");break;
                        case KeyEvent.VK_NUMPAD5: ReadUtil.play2("z5");sb.append("5");break;
                        case KeyEvent.VK_NUMPAD6: ReadUtil.play2("z6");sb.append("6");break;
                        case KeyEvent.VK_NUMPAD7: ReadUtil.play2("z7");sb.append("7");break;
                        case KeyEvent.VK_NUMPAD8: ReadUtil.play2("g1");sb.append("[1]");break;
                        case KeyEvent.VK_NUMPAD9: ReadUtil.play2("g2");sb.append("[2]");break;
                        case 107: ReadUtil.play2("g3");sb.append("[3]");break;

                        //如果键盘锁被按下，就重新锁上
                        case KeyEvent.VK_NUM_LOCK:
                            Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK, true);
                            ReadUtil.play2("g4");sb.append("[4]");
                            break;

                        case KeyEvent.VK_DIVIDE: ReadUtil.play2("g5");sb.append("[5]");break;
                        case KeyEvent.VK_MULTIPLY: ReadUtil.play2("g6");sb.append("[6]");break;
                        case KeyEvent.VK_SUBTRACT: ReadUtil.play2("g7");sb.append("[7]");break;
                        case KeyEvent.VK_ENTER: ReadUtil.play2("d7");sb.append("(7)");break;
                        case KeyEvent.VK_DECIMAL: ReadUtil.play2("d6");sb.append("(6)");break;
                        case KeyEvent.VK_NUMPAD0: ReadUtil.play2("d5");sb.append("(5)");break;
                        case KeyEvent.VK_UP: ReadUtil.play2("d4");sb.append("(4)");break;
                        case KeyEvent.VK_RIGHT: ReadUtil.play2("d3");sb.append("(3)");break;
                        case KeyEvent.VK_DOWN: ReadUtil.play2("d2");sb.append("(2)");break;
                        case KeyEvent.VK_LEFT: ReadUtil.play2("d1");sb.append("(1)");break;

                        case KeyEvent.VK_A: ReadUtil.play2("bd1");sb.append("(#1)");break;
                        case KeyEvent.VK_S: ReadUtil.play2("bd2");sb.append("(#2)");break;
                        case KeyEvent.VK_D: ReadUtil.play2("d3");sb.append("(3)");break;
                        case KeyEvent.VK_F: ReadUtil.play2("bd4");sb.append("(#4)");break;
                        case KeyEvent.VK_G: ReadUtil.play2("bd5");sb.append("(#5)");break;
                        case KeyEvent.VK_H: ReadUtil.play2("bd6");sb.append("(#6)");break;
                        case KeyEvent.VK_J: ReadUtil.play2("d7");sb.append("(7)");break;

                        case KeyEvent.VK_Q: ReadUtil.play2("bz1");sb.append("#1");break;
                        case KeyEvent.VK_W: ReadUtil.play2("bz2");sb.append("#2");break;
                        case KeyEvent.VK_E: ReadUtil.play2("z3");sb.append("3");break;
                        case KeyEvent.VK_R: ReadUtil.play2("bz4");sb.append("#4");break;
                        case KeyEvent.VK_T: ReadUtil.play2("bz5");sb.append("#5");break;
                        case KeyEvent.VK_Y: ReadUtil.play2("bz6");sb.append("#6");break;
                        case KeyEvent.VK_U: ReadUtil.play2("z7");sb.append("7");break;


                        case 49: ReadUtil.play2("bg1");sb.append("[#1]");break;
                        case 50: ReadUtil.play2("bg2");sb.append("[#2]");break;
                        case 51: ReadUtil.play2("g3");sb.append("[3]");break;
                        case 52: ReadUtil.play2("bg4");sb.append("[#4]");break;
                        case 53: ReadUtil.play2("bg5");sb.append("[#5]");break;
                        case 54: ReadUtil.play2("bg6");sb.append("[#6]");break;
                        case 55: ReadUtil.play2("g7");sb.append("[7]");break;


                        //按空格复制到剪贴板等
                        case KeyEvent.VK_Z: jb1.doClick(); break;
                        case KeyEvent.VK_X: jb2.doClick(); break;
                        case KeyEvent.VK_B: jb5.doClick(); break;
                        case KeyEvent.VK_SPACE: jb6.doClick(); break;
                        case KeyEvent.VK_C: jb3.doClick(); break;
                        case KeyEvent.VK_V: jb4.doClick(); break;



                    }

                    text.setText(sb.toString());
                }

        });

    }
}
