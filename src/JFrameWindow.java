import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class JFrameWindow extends JFrame{
    // 定义组件
    JPanel jp1, jp2, jp3;
    JLabel jlb1, jlb2;
    JButton jb1, jb2, jb3, jb4;
    JTextField url;
    JTextField sleepTime;

    public static void createWindow() {
        // TODO Auto-generated method stub
        JFrameWindow d1 = new JFrameWindow();

    }

    // 构造函数
    public JFrameWindow() {

        try {
            InputStream imgIS = this.getClass().getResourceAsStream("z.png");
            Image image = ImageIO.read(imgIS);
            this.setIconImage(image);

        }catch (Exception e){

        }


        setResizable(false);
        setLocation(getToolkit().getScreenSize().width/3, getToolkit().getScreenSize().height/3);
        setSize(500, 180);

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

        jlb1 = new JLabel("文件地址");
        jlb2 = new JLabel("播放间隔");

        jb1 = new JButton("播放");
        jb2 = new JButton("停止");

        jb3 = new JButton("暂停");
        jb4 = new JButton("继续");

        url = new JTextField(30);
        sleepTime = new JTextField(30);


        Font font = new Font("宋体",Font.PLAIN,22);
        url.setFont(font);
        sleepTime.setFont(font);
        jlb1.setFont(font);
        jlb2.setFont(font);
        jb1.setFont(font);
        jb2.setFont(font);
        jb3.setFont(font);
        jb4.setFont(font);

        GridLayout gl = new GridLayout(3, 1);
        this.setLayout(gl);

        // 加入各个组件
        jp1.add(jlb1);
        jp1.add(url);

        jp2.add(jlb2);
        jp2.add(sleepTime);

        jp3.add(jb1);
        jp3.add(jb2);
        jp3.add(jb3);
        jp3.add(jb4);


        // 加入到JFrame
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);

        setListener(jb1, jb2, url, sleepTime);
        setListener2(jb3, jb4, sleepTime);

        this.setTitle("TXT音乐播放器");
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
    public static void setListener(JButton jb1, JButton jb2, final JTextField url, final JTextField sleepTime){

        //设置默认值
        url.setText("D:\\txtmusic\\omrg100.txt");
        sleepTime.setText("500");

        url.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点执行的代码
                try {
                    int autoTime = Integer.parseInt(url.getText().replaceAll("\\D", ""));
                    sleepTime.setText(String.valueOf(autoTime));
                }catch (Exception e3){

                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                //获得焦点执行的代码
            }
        });



        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Thread(){
                    @Override
                    public void run() {
                        //清空播放缓存数组
                        ReadUtil.file.clear();
                        //暂停记录从0开始
                        ReadUtil.current = 0;

                        ReadUtil.isPlay = true;
                        String urlStr = url.getText();
                        String sleepTimeStr = sleepTime.getText();

                        int time = 500;
                        try {
                            time = Integer.parseInt(sleepTimeStr);
                        }catch (Exception e1){
                            time = 500;
                        }


                        //如果间隔时间太长或为负数
                        if(time<0 || time> 5000){
                            time = 500;
                        }

                        ReadUtil.sleepTime = time;
                        ReadUtil.readTxt(urlStr);

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
                        ReadUtil.isPlay = false;
                        ReadUtil.current = 0;
                    }
                }.start();
            }
        });
    }
}
