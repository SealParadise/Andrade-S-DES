import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class desController {//UI controller

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox checkBox;

    @FXML
    private TextField keyField;

    @FXML
    private TextField decodeSou;

    @FXML
    TextField decodeRes;

    @FXML
    private TextField encodeSou;

    @FXML
    TextField encodeRes;

    @FXML
    void Encode(ActionEvent event) {//加密按钮方法

        if ((keyField.getText().length() == 10 && encodeSou.getText().length() == 8 ) || checkBox.isSelected()){//检测输入格式

            if (checkBox.isSelected()){//检测输入类型 二进制 or ascii

                String res = "";
                //逐字符读取
                for (int i = 0; i < encodeSou.getText().length(); i++){
                    String tem = Integer.toBinaryString((int) encodeSou.getText().charAt(i));

                    //二进制补0
                    while (tem.length() < 8) {
                        tem = "0" + tem;
                    }

                    //读取输入并初始化编码器
                    sDES encoder = new sDES(keyField.getText(),tem);
                    encoder.enCode();//加密操作

                    //将加密结果转化成ascii字符加入输出集
                    res = res + (char)Integer.parseInt(encoder.result,2);
                }
                //显示输出集
                encodeRes.setText(res);
            }else {
                //读取输入并初始化编码器
                sDES encoder = new sDES(keyField.getText(), encodeSou.getText());
                encoder.enCode();//加密操作

                //显示输出集
                encodeRes.setText(encoder.result);
            }
        }else {
            //错误输入反馈
            encodeRes.setText("明文/密钥数量不规范");
        }
    }

    @FXML
    void Decode(ActionEvent event) {//解密按钮方法

        if ((keyField.getText().length() == 10 && decodeSou.getText().length() == 8) || checkBox.isSelected()){//检测输入格式

            if (checkBox.isSelected()){//检测输入类型 二进制 or ascii

                String res = "";
                //逐字符读取
                for (int i = 0; i < decodeSou.getText().length(); i++){

                    String tem = Integer.toBinaryString((int) decodeSou.getText().charAt(i));
                    //二进制补0
                    while (tem.length() < 8) {
                        tem = "0" + tem;
                    }

                    //读取输入并初始化解码器
                    sDES decoder = new sDES(keyField.getText(),tem);
                    decoder.deCode();//解码操作

                    //将解密结果转化成ascii字符加入输出集
                    res = res + (char)Integer.parseInt(decoder.result,2);
                }
                //显示输出集
                decodeRes.setText(res);

            }else {
                //读取输入并初始化解码器
                sDES decoder = new sDES(keyField.getText(), decodeSou.getText());
                decoder.deCode();//解码操作

                //显示输出集
                decodeRes.setText(decoder.result);
            }
        }else {
            //错误输入反馈
            decodeRes.setText("明文/密钥数量不规范");
        }
    }

    @FXML
    void randomKey(ActionEvent event) {//生成随机密钥并显示在keyField中
        //生成0-1023间的随机数
        Random random = new Random();
        int num = random.nextInt(1023);

        //将随机数转为二进制
        String binary = Integer.toBinaryString(num);

        //空位补0
        while (binary.length() < 10) {
            binary = "0" + binary;
        }

        //显示密钥
        keyField.setText(binary);
    }

    @FXML
    void initialize() {//初始化UI界面 设置encode_res框为不可编辑
        encodeRes.setEditable(false);
        decodeRes.setEditable(false);
    }

}