import sun.audio.AudioPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ReadUtil {

    private static ReadUtil r = new ReadUtil();
    //级别
    private static String level = "z";
    //类型
    private static String type = "";

    public static int sleepTime;

    public static boolean isPlay = true;
    public static boolean canCon = false;

    public static int current = 0;

    public static ArrayList<Integer> file = new ArrayList();


    public static void readTxt(String txtUrl){
        try {
            FileReader fr = new FileReader(txtUrl);
            int now ;

            //读取文件
            while ( (now = fr.read())!= -1){
                file.add(now);
            }

            //播放
            for(int o : file){
                if(isPlay){
                    String s = String.valueOf((char)o);
                    current++ ;
                    choose(s);
                }else{
                    break;
                }
            }



        } catch (Exception e) {
            //System.out.println("没有找到目标txt文件！");
            //e.printStackTrace();
        }
    }

    public static void choose(String judge){

        if("#".contains(judge)){
            type = "b";
            return;
        }

        if("\r".contains(judge)){
            return;
        }

        if(" ".contains(judge)){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
            return;
        }

        if("1234567".contains(judge)){
            play(judge);
            type = "";
        }else if("(".contains(judge)){
            level = "d";
        }else if(")".contains(judge)){
            level = "z";
        }else if("[".contains(judge)){
            level = "g";
        }else if("]".contains(judge)){
            level = "z";
        }else{
            //否则不处理
        }

    }

    //播放
    public static void play(String num){
        StringBuffer musicUrl = new StringBuffer();

        musicUrl.append("music/");
        musicUrl.append(type);
        musicUrl.append(level);
        musicUrl.append(num);
        musicUrl.append(".wav");
        //System.out.println(musicUrl.toString());
        playMusic(musicUrl.toString());
    }

    /**
     * 可以播放wav格式的文件
     * @param url
     */
    public static void playMusic(String url) {

        //读取内部wav，直接获取为流
        InputStream inputStream = r.getClass().getResourceAsStream(url);//相对src路径，前面不要有 /
        AudioPlayer.player.start(inputStream);

    }

    public static void continueMusic(){
        for(int i = current; i< file.size(); i++){
            if(isPlay){
                int c = file.get(i);
                String s = String.valueOf((char)c);
                current++ ;
                choose(s);
            }else{
                break;
            }
        }
    }


    /**
     * 可以播放wav格式的文件，学习用
     * @param url
     *//*
    public static void playMusic2(String url) {

        //读取内部wav
        //this.getClass().getResourceAsStream("/init.properties");
        //URL u1 = this.getClass().getClassLoader().getResource("music/test1.wav");//相对src路径，前面不要有 /
        URL u1 = r.getClass().getClassLoader().getResource(url);//相对src路径，前面不要有 /
        String finalUrl = String.valueOf(u1).replace("file:/","");

        System.out.println(finalUrl);

        FileInputStream ios = null;
        try {
            ios = new FileInputStream(finalUrl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(ios);

    }

    */
}
