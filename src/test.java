import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        System.out.println("begin");
        test main1 = new test();

        //URL u = main1.getClass().getClassLoader().getResource("music/z5.wav");//相对src路径，前面不要有 /
        //main1.playMusic1(u);

        main1.playMusic2("D:\\新音乐\\3.wav");

    }

    public static void radomPlay(test m){
       for(int i=0; i<10; i++){
           int a = (int)(Math.random()*7);
           while(a<=0 || a>=7){
               System.out.println("again");
               a = (int)(Math.random()*7);
           }
           System.out.println(a);
           m.playMusic("music/z"+a+".wav");
       }
    }
    /**
     * 可以播放wav格式的文件
     * @param url
     */
    public void playMusic(String url) {
        //URL u1 = this.getClass().getClassLoader().getResource("music/test1.wav");//相对src路径，前面不要有 /
        URL u1 = this.getClass().getClassLoader().getResource(url);//相对src路径，前面不要有 /
        AudioClip co1 = JApplet.newAudioClip(u1);
        co1.play();

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }



    public static void absMusic() throws MalformedURLException {
        System.out.println("abc");
        //选择播放文件
        File file = new File("E:\\tmp\\bullet.wav");
        //创建audioclip对象
        AudioClip audioClip = null;
        //将file转换为url
        audioClip = Applet.newAudioClip(file.toURL());
        //循环播放	播放一次可以使用audioClip.play
        audioClip.loop();

    }

    /**
     * 可以播放wav格式的文件
     */
    public void playMusic1(URL u) {

        //URL u1 = r.getClass().getClassLoader().getResource(url);//相对src路径，前面不要有 /
        String finalUrl = String.valueOf(u).replace("file:/","");

        FileInputStream ios = null;
        try {
            ios = new FileInputStream(finalUrl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(ios);
        //AudioClip co1 = JApplet.newAudioClip(u1);
        //co1.play();

        //System.out.println("end");
    }

    /**
     * 可以播放wav格式的文件
     */
    public void playMusic2(String finalUrl) {

        //URL u1 = r.getClass().getClassLoader().getResource(url);//相对src路径，前面不要有 /

        FileInputStream ios = null;
        try {
            ios = new FileInputStream(finalUrl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i< 5; i++) {
            AudioPlayer.player.start(ios);
            try {
                Thread.sleep(5000);
                //AudioPlayer.player.start(ios);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
/*
        AudioStream as = null;
        AudioData ad=null;
        try {
            as = new AudioStream(ios);
            //不能超过1M，因此不能用
            ad = as.getData();
        } catch (IOException e) {e.printStackTrace();}
        //设置循环播放
        ContinuousAudioDataStream cads = new ContinuousAudioDataStream(ad);
        //循环播放开始哦
        AudioPlayer.player.start(cads);
		try{
		    Thread.sleep(50000);
        }catch(Exception e){}
        //循环播放停止
        AudioPlayer.player.stop(cads);

*/

    }

}
