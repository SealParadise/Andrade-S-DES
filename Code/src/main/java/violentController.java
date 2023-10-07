import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class violentController {

    class thread1 extends Thread{//线程1 查找一半密钥空间的线程 适用于双线程破解
        public void run(){
            Decode(0,511,"线程1： ");//暴力查找0-511中的密钥
        }
    }

    class thread2 extends Thread{//线程2 查找一半密钥空间的线程
        public void run(){
            Decode(512,1023,"线程2： ");//暴力查找512-1023中的密钥
        }
    }

    class thread3 extends Thread{//线程三 查找全部密钥空间的线程 适用于单线程破解
        public void run(){
            Decode(0,1023,"线程3： ");//暴力查找0-1023中的密钥
        }
    }

    public void Decode(int begin, int end,String th){//密钥解密程序

        HashMap<Integer,String> hash = new HashMap<Integer,String>();//创建存储密钥的哈希表
        int amount = 0;//标记已查到的密钥数量

        for (int i = begin; i <= end; i++){//在指定的密钥空间遍历

            String key = Integer.toBinaryString(i);//将密钥从十进制转二进制

            //补0操作
            while (key.length() < 10) {
                key = "0" + key;
            }

            //用遍历密钥同原文加密
            sDES encoder = new sDES(key,originalText.getText());
            encoder.enCode();

            if (encoder.result.equals(secretText.getText())){//对照加密后原文是否与密文一致
                //时间戳返回每次找到的时间
                Long timeStamp = System.currentTimeMillis();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms");
                String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));

                //存储已查到的密钥
                hash.put(amount,key);
                amount++;//已查明密钥数量+1

                //输出时间戳
                System.out.println(th + "找到第"+amount+"个的时间：" + sd);
            }
        }
        for (int i = 0; i < amount; i++){//输出密钥集
            keyArea.appendText("\n" + hash.get(i) );
        }
    }

    @FXML
    private TextField originalText;

    @FXML
    private TextField secretText;

    @FXML
    private TextArea keyArea;

    @FXML
    void violentDecode(ActionEvent event) {

        keyArea.clear();
        //双线程执行
        thread1 thread1 = new thread1();
        thread2 thread2 = new thread2();

        thread1.start();
        thread2.start();

        /*
        //单线程执行
        thread3 thread3 = new thread3();
        thread3.start();
        */
    }

    @FXML
    void initialize() {
        //时间戳返回创建窗口的时间
        Long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        System.out.println("创建窗口时间：" + sd);

        keyArea.setEditable(false);//初始化设置密钥显示区域不可编辑
    }

}
