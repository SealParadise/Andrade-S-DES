# Andrade S-DES 开发手册

开发人员：徐涵浩 明子鸿

手册撰写人员：明子鸿 徐涵浩

单位：重庆大学大数据与软件学院

联系方式：shennmo@foxmall.com



## 1. 引言

### 1.1 关于本手册

​	本手册旨在提供有关S-DES算法实现程序的详细开发和使用信息。它包括了安装、配置、使用说明、实现细节以及测试等方面的信息，以帮助开发者正确使用和理解该程序。



### 1.2 目标与范围

​	编写S-DES（Simplified Data Encryption Standard）开发手册的主要目的是为了确保开发和使用S-DES算法实现程序的顺利进行，提供清晰的文档和指南，以支持用户、开发团队和维护人员，旨在提高程序的可维护性、可用性和用户体验的同时促进团队内部的知识共享和合作。主要包括以下目标：

​	①**提供使用指南**：Andrade S-DES开发手册旨在向开发者和用户提供清晰、易于理解的使用指南。它详细说明了如何安装、配置、编译和使用S-DES算法实现程序，以确保用户能够正确地运行和使用该工具;

​	②**解释算法原理**：手册解释了S-DES算法的原理和工作流程。这有助于用户理解程序内部的加密和解密过程，以及如何选择适当的输入数据和密钥;

​	③**提供实现细节**：开发手册涵盖了程序的实现细节，包括程序结构、数据处理流程、密钥生成和用户界面设计等方面。这对于开发团队的成员来说是一个重要的参考资源，帮助他们理解和维护代码;

​	④**提供常见问题解答**：手册包括了一章关于常见问题与故障排除，以帮助用户解决可能遇到的问题。这有助于提高用户的使用体验并减少潜在的困惑;

​	⑤**促进知识传递**：编写手册有助于知识的传递和分享。新成员可以通过手册快速了解项目，并且可以在团队内部共享和学习最佳实践;

​	⑥**支持维护与更新**：随着时间的推移，软件项目需要进行维护和更新。手册可以帮助开发团队理解项目的历史和设计决策，从而更好地管理和维护代码。



## 2. 项目概述

### 2.1 项目描述

​	S-DES算法实现程序是一个用于演示和理解Simplified Data Encryption Standard（S-DES）算法的工具。它提供了加密、解密和暴力破解密钥功能，支持用户交互，并可接受8bit二进制数据（或ASCⅡ编码字符串）和10位密钥。



### 2.2 功能特性

- 加密和解密功能
- 支持8bit二进制数据和ASCⅡ编码字符串输入
- 支持10位密钥的输入和随机生成
- 提供对已知明密文对进行密钥暴力破解的功能
- 提供图形用户界面（GUI）支持



### 2.3 技术栈

- 编程语言：Java
- 版本控制：Git
- 依赖管理：Maven



## 3. 安装与配置

### 3.1 环境要求

- 操作系统：Windows 10/11
- Java JDK：1.8及以上



### 3.2 获取源代码

- 源代码仓库：https://github.com/m916662613/Andrade-S-DES



### 3.3 构建与编译

- 构建工具：Maven
- 编译代码：使用IntelliJ IDEA编译运行代码



## 4. 程序结构

### 4.1 目录结构

![img43](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img43.png)



### 4.2 主要组件

#### 4.2.1 子密钥生成

​	①首先，使用P10置换盒对初始密钥`key`进行置换和压缩，生成一个包含10位的子密钥`subKeyA`，并将`key`的副本赋给`subKeyB`；

​	②接下来，将`subKeyA`分成两部分，分别为`keyLeft`和`keyRight`，然后对这两部分进行左移操作；

​	③将左移后的`keyLeft`和`keyRight`重新拼接成`subKeyB`，然后使用P8置换盒，从`subKeyB`中取出8位字符，生成k1；

​	④再次对`keyLeft`和`keyRight`进行左移操作，得到新的`subKeyB`，然后再次使用P8置换盒，从`subKeyB`中取出8位字符，生成k2；

​	⑤最后，将生成的k1和k2赋值给相应的变量。

```java
    public void setK12(){ //由密钥获得k1 k2的方法
        //使用P10和P8盒进行置换和压缩，生成两个8位的子密钥
        String subKeyA = "";
        String subKeyB = key;

        for (int i = 1; i <= 10; i++){//P10 Box 操作
            //按照P10盒的顺序从密钥中取出对应的字符，拼接成新的字符串
            subKeyA = subKeyA + subKeyB.charAt(P10.get(i) - 1);
        }

        //分割字符串 分别做leftshift
        String keyLeft = leftShift(subKeyA.substring(0,5)); //将左半部分字符串左移一位
        String keyRight = leftShift(subKeyA.substring(5)); //将右半部分字符串左移一位
        subKeyB = keyLeft + keyRight; //拼接左右两部分字符串

        subKeyA = "";

        for (int i = 1; i<= 8; i++){//P8 Box for k1
            //按照P8盒的顺序从拼接后的字符串中取出对应的字符，拼接成新的字符串，作为k1
            subKeyA = subKeyA + subKeyB.charAt(P8.get(i) - 1);
        }

        k1 = subKeyA; //设置k1

        subKeyA = "";

        //分别做leftshift
        keyLeft = leftShift(keyLeft); //将左半部分字符串再次左移一位
        keyRight = leftShift(keyRight); //将右半部分字符串再次左移一位
        subKeyB = keyLeft + keyRight; //拼接左右两部分字符串

        for (int i = 1; i<= 8; i++){//P8 Box for k2
            //按照P8盒的顺序从拼接后的字符串中取出对应的字符，拼接成新的字符串，作为k2
            subKeyA = subKeyA + subKeyB.charAt(P8.get(i) - 1);;
        }

        k2 = subKeyA; //设置k2
    }
```



#### 4.2.2 置换盒、子密钥初始化

​	①类`sDES`包含了一些成员变量，用于存储S-DES算法所需的数据，包括原始文本`original`、密钥`key`、子密钥`k1`和`k2`以及最终结果`result`；

​	②使用`HashMap`来定义多个置换盒（Permutation Box）和S-Box，这些盒子用于S-DES算法中的置换和替代操作。具体包括：

​		a)`ipBox`和`ipFinalBox`：用于初始置换（Initial Permutation）和最终置换（Final Permutation）；

​		b)`P10`和`P8`：用于生成子密钥的置换和压缩；

​		c)`epBox`：用于扩展置换（Expansion Permutation）；

​		d)`spBox`：用于S盒操作的置换；

​		e)`sBox1`和`sBox2`：S-DES算法中使用的两个S-Box；

​	③构造函数`public sDES(String key, String original)`接受两个参数，分别是初始密钥`key`和原始文本`original`。在构造函数中，首先初始化了类的成员变量`key`和`original`；

​	④随后，为各个置换盒和S-Box填充了预定义的映射关系；

​	⑤最后，调用了`setK12()`方法，用于生成S-DES算法中的子密钥k1和k2。

```java
    public String original,key,k1,k2,result;
    HashMap<Integer,Integer> ipBox = new HashMap<Integer,Integer>();
    HashMap<Integer,Integer> ipFinalBox = new HashMap<Integer,Integer>();
    HashMap<Integer,Integer> P10 = new HashMap<Integer,Integer>();
    HashMap<Integer,Integer> P8 = new HashMap<Integer,Integer>();
    HashMap<Integer,Integer> epBox = new HashMap<Integer,Integer>();
    HashMap<String,HashMap<String,String>> sBox1 = new HashMap<String,HashMap<String,String>>();
    HashMap<String,HashMap<String,String>> sBox2 = new HashMap<String,HashMap<String,String>>();
    HashMap<Integer,Integer> spBox = new HashMap<Integer,Integer>();

    public sDES(String key, String original){//初始化 密钥key 起始字符串original ipBox等转换盒

        this.key = key;

        this.original = original;

        ipBox.put(1,2);
        ipBox.put(2,6);
        ipBox.put(3,3);
        ipBox.put(4,1);
        ipBox.put(5,4);
        ipBox.put(6,8);
        ipBox.put(7,5);
        ipBox.put(8,7);

        ipFinalBox.put(1,4);
        ipFinalBox.put(2,1);
        ipFinalBox.put(3,3);
        ipFinalBox.put(4,5);
        ipFinalBox.put(5,7);
        ipFinalBox.put(6,2);
        ipFinalBox.put(7,8);
        ipFinalBox.put(8,6);

        P10.put(1,3);
        P10.put(2,5);
        P10.put(3,2);
        P10.put(4,7);
        P10.put(5,4);
        P10.put(6,10);
        P10.put(7,1);
        P10.put(8,9);
        P10.put(9,8);
        P10.put(10,6);

        P8.put(1,6);
        P8.put(2,3);
        P8.put(3,7);
        P8.put(4,4);
        P8.put(5,8);
        P8.put(6,5);
        P8.put(7,10);
        P8.put(8,9);


        epBox.put(1,4);
        epBox.put(2,1);
        epBox.put(3,2);
        epBox.put(4,3);
        epBox.put(5,2);
        epBox.put(6,3);
        epBox.put(7,4);
        epBox.put(8,1);

        spBox.put(1,2);
        spBox.put(2,4);
        spBox.put(3,3);
        spBox.put(4,1);

        sBox1.put("00",new HashMap<String, String>());
        sBox1.get("00").put("00","01");
        sBox1.get("00").put("01","00");
        sBox1.get("00").put("10","11");
        sBox1.get("00").put("11","10");

        sBox1.put("01",new HashMap<String, String>());
        sBox1.get("01").put("00","11");
        sBox1.get("01").put("01","10");
        sBox1.get("01").put("10","01");
        sBox1.get("01").put("11","00");

        sBox1.put("10",new HashMap<String, String>());
        sBox1.get("10").put("00","00");
        sBox1.get("10").put("01","10");
        sBox1.get("10").put("10","01");
        sBox1.get("10").put("11","11");

        sBox1.put("11",new HashMap<String, String>());
        sBox1.get("11").put("00","11");
        sBox1.get("11").put("01","01");
        sBox1.get("11").put("10","00");
        sBox1.get("11").put("11","10");

        sBox2.put("00",new HashMap<String, String>());
        sBox2.get("00").put("00","00");
        sBox2.get("00").put("01","01");
        sBox2.get("00").put("10","10");
        sBox2.get("00").put("11","11");

        sBox2.put("01",new HashMap<String, String>());
        sBox2.get("01").put("00","10");
        sBox2.get("01").put("01","11");
        sBox2.get("01").put("10","01");
        sBox2.get("01").put("11","00");

        sBox2.put("10",new HashMap<String, String>());
        sBox2.get("10").put("00","11");
        sBox2.get("10").put("01","00");
        sBox2.get("10").put("10","01");
        sBox2.get("10").put("11","10");

        sBox2.put("11",new HashMap<String, String>());
        sBox2.get("11").put("00","10");
        sBox2.get("11").put("01","01");
        sBox2.get("11").put("10","00");
        sBox2.get("11").put("11","11");

        setK12();
    }
```



#### 4.2.3 置换、异或运算、S盒替代等方法

​	①`leftShift(String halfKey)` 方法用于将输入的二进制字符串左移一位；

​	②`IP(String original)` 方法用于将输入的二进制字符串按照IP置换表进行置换；

​	③`ipFinal(String fin)` 方法用于将输入的二进制字符串按照IP逆置换表进行置换；

​	④`plus(String a, String b)` 方法实现二进制字符串的异或运算；

​	⑤`F(String input, String k12)` 方法执行S-DES算法中的F操作，包括扩展置换（EP）、S盒替代和置换（SP）；

​	⑥`DES(String input, String k12)` 方法执行S-DES算法中的DES操作，包括将输入的字符串分为左右两部分，与k12进行F操作，然后将左半部分与F操作的结果进行异或运算。

```java
    public String leftShift(String halfKey){ //字符串左移一位的方法

        String str1 = halfKey.substring(1); //截取第二个字符到最后一个字符

        String str2 = halfKey.substring(0,1);//截取第一个字符

        return str1 + str2;//返回拼接字符串
    }
    public String IP(String original){//将输入的字符串按照IP置换表进行置换
        String res = "";
        for (int i = 1; i <= 8; i++){
            res = res + original.charAt(ipBox.get(i) - 1);
        }

        return res;
    }

    public String ipFinal(String fin){//将输入的字符串按照IP逆置换表进行置换
        String res = "";
        for (int i = 1; i <= 8; i++){
            res = res + fin.charAt(ipFinalBox.get(i) - 1);
        }

        return res;
    }

    public String plus(String a ,String b){ //异或操作 Out = a 异或 b
        //对两个二进制字符串进行异或操作
        String res = "";
        for (int i = 0; i < a.length(); i++){
            if (a.charAt(i) == b.charAt(i)){
                res = res + "0";
            }else{
                res = res + "1";
            }
        }

        return res;
    }

    public String F(String input,String k12){//F操作 Out = In.F(k_In)
        //将输入的字符串按照EP置换表进行置换
        String output = "";
        String tem = "";
        for (int i = 1; i <= 8; i++){// EP 操作
            tem = tem + input.charAt(epBox.get(i) - 1);
        }

        //将EP置换后的字符串与k12进行异或操作
        tem = plus(tem,k12);

        //分割字符串 获得S box的四个下标
        char[] index1 = {tem.charAt(0),tem.charAt(3)};
        char[] index2 = {tem.charAt(4),tem.charAt(7)};

        //将S box操作后的结果拼接起来
        tem = sBox1.get(new String(index1)).get(tem.substring(1,3)) + sBox2.get(new String(index2)).get(tem.substring(5,7));

        //将S box操作后的结果按照SP置换表进行置换
        for (int i = 1; i <= 4; i++){
            output = output + tem.charAt(spBox.get(i) - 1);
        }

        return output;
    }

    public String DES(String input, String k12){ //DES操作
        //将输入的字符串分为左右两部分
        String Left = input.substring(0,4);
        String Right = input.substring(4);

        //将右半部分字符串与k12进行F操作
        String fTem = F(Right,k12);

        //将左半部分字符串与F操作后的结果进行异或操作
        Left = plus(Left, fTem);

        return Right + Left;
    }
```



#### 4.2.4 加密解密方法

​	①`enCode()` 方法用于执行S-DES算法的加密操作，包括按照IP置换表置换、两轮DES操作、字符串前后交换和IP逆置换；

​	②`deCode()` 方法用于执行S-DES算法的解密操作，也包括按照IP置换表置换、两轮DES操作、字符串前后交换和IP逆置换。

```java
    public void enCode(){ //加密方法 Effect = 设置this.result 为original的加密结果
        //将输入的字符串按照IP置换表进行置换
        String tem = IP(original);

        //将置换后的字符串分别与k1和k2进行DES操作
        tem = DES(tem,k1);
        tem = DES(tem,k2);

        //将DES操作后的字符串前后交换
        tem = tem.substring(4) + tem.substring(0,4);

        //将交换后的字符串按照IP逆置换表进行置换
        result = ipFinal(tem);
    }

    public void deCode(){ //解密方法 Effect = 设置this.result 为original的解密结果
        //将输入的字符串按照IP置换表进行置换
        String tem = IP(original);

        //将置换后的字符串分别与k2和k1进行DES操作
        tem = DES(tem,k2);
        tem = DES(tem,k1);

        //将DES操作后的字符串前后交换
        tem = tem.substring(4) + tem.substring(0,4);

        //将交换后的字符串按照IP逆置换表进行置换
        result = ipFinal(tem);
    }
```



#### 4.2.5 程序启动模块

​	①`public static void main(String[] args)` 方法是Java应用程序的入口点，它调用`launch(args)`来启动JavaFX应用程序;

​	②`public void start(Stage primaryStage) throws IOException` 是JavaFX的Application类中的方法，它是JavaFX应用程序的主要入口点。在这个方法中，主要的操作包括：

​	a)创建两个FXMLLoader对象，分别用于加载"des.fxml"和"violent.fxml"两个FXML文件，这些文件用于定义应用程序的用户界面;

​	b)使用这两个FXMLLoader对象加载FXML文件，并得到它们的根节点（Parent）;

​	c)通过`getController()`方法获取与这两个FXML文件关联的Controller对象，即`desController`和`violentController`，这些Controller类负责管理界面的行为和交互;

​	d)创建一个Stage对象，用于表示主界面，并设置其标题为"S-DES"，然后将主界面的根节点设置为加载的`des.fxml`文件的根节点，最后显示主界面;

​	e)创建另一个Stage对象，用于表示"Violent Decode"界面，并设置其标题为"Violent Decode"，然后将其根节点设置为加载的`violent.fxml`文件的根节点，最后显示"Violent Decode"界面。

```java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {// javafx + fxml 获取ui
        //加载fxml文件
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/des.fxml"));
        FXMLLoader violentLoader = new FXMLLoader(getClass().getResource("/violent.fxml"));

        //加载父结点
        Parent root = mainLoader.load();
        Parent parent = violentLoader.load();

        //获取Controller
        desController controller = mainLoader.getController();
        violentController violentController = violentLoader.getController();

        //显示Stage设置
        primaryStage.setTitle("S-DES");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Stage vioStage = new Stage();
        vioStage.setTitle("Violent Decode");
        vioStage.setScene(new Scene(parent));
        vioStage.show();

    }
}
```



#### 4.2.6 加解密主界面控制器

​	①`@FXML` 注解用于标记FXML文件中的UI组件，这些组件在Controller类中被引用和操作;

​	②`void Encode(ActionEvent event)` 方法是加密按钮的事件处理方法。它执行以下操作：

​		a)检查输入的密钥和明文的长度是否符合规范（密钥长度为10位，明文长度为8位）或者是否选择了ASCII字符输入;

​		b)如果选择了ASCII字符输入，则将明文逐字符转换为二进制，进行S-DES加密，然后将加密结果转换为ASCII字符;

​		c)如果没有选择ASCII字符输入，则直接进行S-DES加密;

​		d)将加密结果显示在`encodeRes`文本框中;

​	③`void Decode(ActionEvent event)` 方法是解密按钮的事件处理方法，与加密按钮方法类似，但是执行的是S-DES解密操作，并将解密结果显示在`decodeRes`文本框中;

​	④`void randomKey(ActionEvent event)` 方法用于生成随机的10位密钥，并将其显示在`keyField`文本框中;

​	⑤`void initialize()` 方法是JavaFX的初始化方法，在界面加载后自动调用。它设置`encodeRes`和`decodeRes`文本框为不可编辑状态，以防止用户手动输入。

```java
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
```



#### 4.2.7 暴力破解界面控制器

​	①`thread1`、`thread2` 和 `thread3` 是内部类，继承自 `Thread`，分别代表三个线程。它们负责在不同的密钥空间范围内执行密钥解密程序;

​	②`public void Decode(int begin, int end, String th)` 方法是密钥解密程序，用于在指定的密钥范围内暴力破解密钥并查找匹配的密钥。它执行以下操作：

​		a)遍历指定范围内的所有可能的密钥，将密钥转换为二进制形式;

​		b)用每个密钥对原文进行 S-DES 加密;

​		c)检查加密结果是否与给定的密文匹配，如果匹配则将该密钥存储在 `hash` 哈希表中，并记录已找到的密钥数量;

​		d)输出每个找到的密钥及其对应的时间戳;

​	③`violentDecode(ActionEvent event)` 方法是“暴力解密”按钮的事件处理方法。它执行以下操作：

​		a)清空密钥显示区域 `keyArea`;

​		b)创建两个线程 `thread1` 和 `thread2`，并启动它们，用于并行查找不同范围的密钥。注释部分的代码是单线程执行的示例，如果需要单线程破解，可以取消注释，并创建一个 `thread3` 线程;

​	④`initialize()` 方法是JavaFX的初始化方法，在界面加载后自动调用。它执行以下操作：

​		a)输出创建窗口的时间戳;

​		b)设置密钥显示区域 `keyArea` 为不可编辑状态，以防止用户手动输入。

```java
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
```



## 5. 实现细节

### 5.1 S-DES算法原理

#### 5.1.1 总览

![img45](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img45.png)



#### 5.1.2 标准设定

​	①分组长度：8-bit；

​	②密钥长度：10-bit；



#### 5.1.3 算法描述

​	①加密算法


$$
C=IP^{-1}(f_{k_2}(SW)(f_{k_1}(IP(P))))
$$


​	②解密算法


$$
P=IP^{-1}(f_{k_1}(SW(f_{k_2})(IP(C))))
$$


​	③密钥扩展


$$
k_i=P_8(Shift^i(P_{10}(K))),(i=1,2)
$$



#### 5.1.4 转换装置设定

​	①密钥扩展装置

![img44](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img44.png)



![img46](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img46.png)



![img47](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img47.png)



​	②初始置换盒

![img48](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img48.png)



​	③最终置换盒

![img49](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img49.png)



​	④轮函数

![img50](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img50.png)



![img51](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img51.png)



![img52](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img52.png)



![img53](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img53.png)



### 5.2 界面设计

#### 5.2.1 加解密用户交互界面

​	①该XML文件定义了一个名为`AnchorPane`的JavaFX布局，用于包含用户界面元素；

​	②用户界面包括两个按钮（加密和解密按钮）、四个文本框（用于输入原文、密文、密钥和输出结果）、两个标签（用于标识各个输入框的用途）、一个复选框（用于指示输入是否为ASCII字符）以及一个分隔线和一个结果标签；

​	③每个用户界面元素都有相应的属性，例如`fx:id`用于在Java代码中引用该元素，`layoutX`和`layoutY`用于指定元素的位置，`prefHeight`和`prefWidth`用于设置元素的首选高度和宽度，`onAction`用于指定按钮点击时执行的操作，`text`用于设置按钮和标签的文本，`style`用于设置文本框的样式等；

​	④该用户界面的主要功能是允许用户输入原文、密文和密钥，然后执行加密或解密操作，并在结果文本框中显示结果；

​	⑤用户还可以选择复选框来指示输入是否为ASCII字符，并可以通过点击“随机生成密钥”按钮来生成随机密钥；

​	⑥用户界面的设计旨在简化S-DES算法的使用，使用户可以轻松进行加密和解密操作。

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import java.lang.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.text.*?>

<AnchorPane prefHeight="357.0" prefWidth="621.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="desController">
   <children>
      <Button layoutX="166.0" layoutY="284.0" mnemonicParsing="false" onAction="#Encode" prefHeight="30.0" prefWidth="59.0" text="加密">
         <font>
            <Font size="14.0" />
         </font></Button>
      <Button layoutX="429.0" layoutY="284.0" mnemonicParsing="false" onAction="#Decode" prefHeight="30.0" prefWidth="59.0" text="解密">
         <font>
            <Font size="14.0" />
         </font></Button>
      <TextField fx:id="encodeSou" layoutX="106.0" layoutY="106.0">
         <font>
            <Font size="14.0" />
         </font></TextField>
      <TextField fx:id="decodeSou" layoutX="364.0" layoutY="106.0">
         <font>
            <Font size="14.0" />
         </font></TextField>
      <Label layoutX="59.0" layoutY="109.0" prefHeight="23.0" prefWidth="51.0" text="原文：">
         <font>
            <Font size="14.0" />
         </font></Label>
      <Label layoutX="311.0" layoutY="109.0" prefHeight="23.0" prefWidth="51.0" text="密文：">
         <font>
            <Font size="14.0" />
         </font></Label>
      <Label layoutX="59.0" layoutY="229.0" prefHeight="23.0" prefWidth="51.0" text="密文：">
         <font>
            <Font size="14.0" />
         </font></Label>
      <Label layoutX="311.0" layoutY="229.0" prefHeight="23.0" prefWidth="51.0" text="原文：">
         <font>
            <Font size="14.0" />
         </font></Label>
      <TextField fx:id="keyField" layoutX="217.0" layoutY="27.0">
         <font>
            <Font size="14.0" />
         </font></TextField>
      <Label layoutX="175.0" layoutY="30.0" prefHeight="23.0" prefWidth="51.0" text="密钥：">
         <font>
            <Font size="14.0" />
         </font></Label>
      <Button layoutX="439.0" layoutY="27.0" mnemonicParsing="false" onAction="#randomKey" text="随机生成密钥">
         <font>
            <Font size="14.0" />
         </font>
      </Button>
      <CheckBox fx:id="checkBox" layoutX="30.0" layoutY="30.0" mnemonicParsing="false" prefHeight="23.0" prefWidth="136.0" text="输入ASCII字符">
         <font>
            <Font size="14.0" />
         </font></CheckBox>
      <TextField fx:id="encodeRes" layoutX="106.0" layoutY="226.0" style="-fx-background-color: #d3d3d3;">
         <font>
            <Font size="14.0" />
         </font>
      </TextField>
      <TextField fx:id="decodeRes" layoutX="364.0" layoutY="226.0" style="-fx-background-color: #d3d3d3;">
         <font>
            <Font size="14.0" />
         </font>
      </TextField>
      <Separator layoutX="1.0" layoutY="177.0" prefHeight="0.0" prefWidth="622.0" />
      <Label layoutX="30.0" layoutY="156.0" text="结果" />
   </children>
</AnchorPane>

```



#### 5.2.1 暴力破解用户交互界面

​	①代码起始部分包括XML声明，用于标识XML版本和编码方式;

​	②在FXML文件中导入了一些JavaFX和Java的类和包，以便在FXML中使用这些类;

​	③`<AnchorPane>`标签用于定义一个JavaFX用户界面的布局容器，具有指定的首选高度和宽度;

​	④在`<AnchorPane>`标签内部，定义了多个用户界面元素，包括按钮、文本字段、标签和文本区域;

​	⑤`<Button>`标签定义了一个按钮，具有以下属性和设置：

​		a)`layoutX` 和 `layoutY` 属性定义按钮在用户界面中的位置;

​		b)`onAction` 属性指定了按钮被点击时调用的事件处理方法，即 `#violentDecode` 方法;

​		c)`text` 属性设置按钮上显示的文本为 "暴力解密";

​	⑥`<TextField>`标签定义了两个文本字段，分别用于输入原文和密文，具有指定的 `fx:id`、位置和字体设置;

​	⑦`<Label>`标签定义了三个标签，用于标识不同文本字段的用途，分别是原文、密文和密钥的标签;

​	⑧`<TextArea>`标签定义了一个文本区域，用于输入密钥，具有指定的 `fx:id`、位置和字体设置。

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.text.*?>
<?import java.lang.*?>
<?import java.util.*?>
<?import javafx.scene.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<AnchorPane prefHeight="515.0" prefWidth="601.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:controller="violentController">
   <children>
      <Button layoutX="260.0" layoutY="225.0" mnemonicParsing="false" onAction="#violentDecode" prefHeight="23.0" prefWidth="80.0" text="暴力解密">
         <font>
            <Font size="14.0" />
         </font>
      </Button>
      <TextField fx:id="originalText" layoutX="206.0" layoutY="84.0">
         <font>
            <Font size="14.0" />
         </font>
      </TextField>
      <TextField fx:id="secretText" layoutX="206.0" layoutY="160.0">
         <font>
            <Font size="14.0" />
         </font>
      </TextField>
      <Label layoutX="162.0" layoutY="87.0" prefHeight="23.0" prefWidth="44.0" text="原文">
         <font>
            <Font size="14.0" />
         </font>
      </Label>
      <Label layoutX="162.0" layoutY="163.0" prefHeight="23.0" prefWidth="44.0" text="密文">
         <font>
            <Font size="14.0" />
         </font>
      </Label>
      <Label layoutX="162.0" layoutY="358.0" prefHeight="23.0" prefWidth="44.0" text="密钥">
         <font>
            <Font size="14.0" />
         </font>
      </Label>
      <TextArea fx:id="keyArea" layoutX="201.0" layoutY="269.0" prefHeight="200.0" prefWidth="200.0">
         <font>
            <Font size="14.0" />
         </font>
      </TextArea>
   </children>
</AnchorPane>

```



## 6. 使用说明

### 6.1 安装与初始化

#### 6.1.1 安装Java JDK

- 如果您的计算机上没有安装Java JDK，请先下载并安装适用于您操作系统的Java JDK。您可以从Oracle官方网站（https://www.oracle.com/java/technologies/javase-downloads.html）或其他可信来源获取Java JDK的安装程序；
- 安装Java JDK时，请按照安装向导的步骤进行操作。完成后，您将具备Java运行环境。



#### 6.1.2 编译运行S-DES程序

- 推荐下载JetBrains IntelliJ IDEA（通常简称为IDEA），一款由JetBrains公司开发的强大的集成开发环境（IDE），专门用于Java开发，提供了丰富的功能、高度的可定制性和出色的性能；
- 选择src/main/java/mainApp.java点击运行即可。

![img30](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img30.jpg)



#### 6.1.3 版本控制

- 推荐使用Git进行版本控制；
- 每次更新需要撰写日志。



### 6.2 用户交互界面

​	①通过勾选左上角的“输入ASCⅡ字符”可以调整输入原文/密文的格式；

​	②界面左半部分是输入原文进行加密得到密文，右半部分是输入密文进行解密得到原文；

​	③“结果”处的密文框和原文框无法直接进行编辑，只能输出展示“加密”和“解密”的结果；

![img1](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img1.png)



### 6.3 输入

#### 6.3.1 输入格式

​	输入参数为8bit二进制数或者ASCⅡ编码(分组为1byte)的字符串、10bit密钥(也可以由本系统随机生成)；

![img1](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img1.png)

​	注：界面左上角的“输入ASCⅡ字符”勾选表示输入原文为ASCⅡ编码的字符串，不勾选表示输入为8bit二进制数。



#### 6.3.2 输入样例

​	① 密钥：1111001010

​	     原文：10101010

![img31](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img31.png)



​	② 密钥：1000110000

​	     原文：Andrade

![img33](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img33.png)



​	③ 密钥：0011111000

​	     密文：10101010

![img37](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img37.png)



​	④ 密钥：1000110000

​	     密文：\>ëVÓ

![img36](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img36.png)



### 6.4 输出

#### 6.4.1 输出格式

​	输出为8bit二进制数（输入为8bit二进制数时）或ASCⅡ编码字符串（输入为ASCⅡ编码字符串时）。



#### 6.4.2 输出样例

​	① 密钥：1111001010

​	     原文：10101010

​	     密文：01001001

![img32](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img32.jpg)



​	② 密钥：1000110000

​	     原文：Andrade

​	     密文：\>ëVÓ

![img34](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img34.jpg)



​	③ 密钥：0011111000

​	     密文：10101010

​	     原文：10111110

![img38](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img38.png)



​	④ 密钥：1000110000

​	     密文：\>ëVÓ

​	     原文：Andrade

![img35](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img35.jpg)



## 7. 测试与验证

### 7.1 加密解密测试

​	本系统提供纯二进制加密解密功能及ASCⅡ编码字符串加密解密功能，在用户交互界面左上角可以选择，不勾选表示使用纯二进制数字进行加密解密，勾选表示使用ASCⅡ编码字符串进行加密解密。



#### 	7.1.1 二进制加密测试

​		随机生成密钥：1011001011

​		原文：10101010

​		求得密文：11101000

![img5](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img5.jpg)



#### 	7.1.2 二进制解密测试

​		沿用之前密钥：1011001011

​		密文：11101000

​		解得原文：10101010

![img6](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img6.jpg)



#### 	7.1.3 ASCⅡ编码字符串加密测试

​		密钥：0111001000

​		原文：Andrade

​		求得密文：µH%![icon1](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/icon1.png)%X

![img17](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img17.jpg)



#### 	7.1.4 ASCⅡ编码字符串解密测试

​		密钥：0111001000

​		密文：µH%![icon1](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/icon1.png)%X

​		解得原文：Andrade

![img18](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img18.jpg)



#### 	7.1.5 异常处理

​		①当输入原文或密钥不符合规范时；

![img4](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img4.jpg)



![img7](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img7.jpg)



​		②输入为ASCⅡ编码字符串，但没有勾选相应选项；

![img43](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img43.jpg)



### 7.2 破解测试

#### 	7.2.1 单线程破解

​		破解所得可能的密钥：

​		①0011010110；

​		②0111010110；

​		③1000000000；

​		④1001001100；

​		⑤1100000010；

​		⑥1101001100。

​	

![img20](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img20.jpg)

![img21](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img21.jpg)



#### 	7.2.2 双线程破解

​		破解所得可能的密钥：

​		①1000000000；

​		②1001001100；

​		③1100000010；

​		④1101001100；

​		⑤0011010110；

​		⑥0111010110；

​		与单线程破解结果相同。



![img23](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img23.jpg)



![img22](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img22.jpg)



## 8. 常见问题与故障排除

### 8.1 问题1：程序无法启动或崩溃

- 排除方法：
  - 确保已安装所需的Java JDK并正确配置了JAVA_HOME环境变量；
  - 检查程序的依赖项，确保所有必需的库和文件都位于正确的位置；
  - 检查程序的日志文件或控制台输出，以查看是否有错误消息，根据错误消息进行进一步的排除。



### 8.2 问题2：加密或解密操作失败

- 排除方法：
  - 检查输入数据和密钥的格式，确保它们符合程序的要求；
  - 验证密钥生成过程，确保生成的密钥正确；
  - 检查加密或解密算法的实现，确保它遵循S-DES算法规范；
  - 如果可能，尝试使用其他输入数据和密钥进行测试，以确定问题是否特定于某个数据集。



### 8.3 问题3：程序性能不佳或响应时间过长

- 排除方法：
  - 检查计算密集型操作，如数据加密或解密，是否有优化的机会，例如使用更高效的算法或数据结构；
  - 确保程序没有内存泄漏问题，通过检查内存使用情况来排除此类问题；
  - 考虑并发处理，使用多线程来提高性能，特别是在大规模数据处理时。



### 8.4 问题4：用户界面问题

- 排除方法：
  - 检查用户界面元素的布局和设计，确保它们易于理解和使用；
  - 确保用户界面在不同操作系统和分辨率下都能正常工作；
  - 收集用户反馈，并根据反馈来改进用户界面和用户体验。



### 8.5 问题5：安全性问题

- 排除方法：
  - 定期更新程序以修复已知的安全漏洞；
  - 使用密码学库和工具来确保数据的机密性和完整性；
  - 进行安全审查，以识别潜在的安全问题并采取措施加以解决。



## 9. 维护与更新

### 9.1 更新计划

​	①未来计划支持网上加密服务，无需本地部署，远程即可体验我们开发的S-DES加密系统；

​	②未来计划更新汉字加密服务，方便中文用户进行使用；

​	③未来计划支持更灵活位数原文的加密，提高用户体验；

​	④……



### 9.2 联系方式

​	有任何建议欢迎联系我们的工作邮箱：shennmo@foxmall.com，届时请说明来意，谢谢！



## 10. 附录

### 10.1 术语表

​	①DES：全称为Data Encryption Standard（数据加密标准），是一种对称密钥加密算法。它于1977年由美国国家标准与技术研究所（NIST）发布，并被美国政府采用作为非机密数据的加密标准。DES的主要设计目标是提供一种高度安全的数据加密机制，以保护敏感数据免受未经授权的访问和窃取；

​	②S-DES：全称为Simplified Data Encryption Standard（简化版数据加密标准），是一个基于DES算法的简化版本，旨在提供更快的执行速度和更简单的实现。DES是一种广泛使用的对称密钥加密算法，但它的密钥长度较长，复杂度较高，因此，S-DES被创建为一种教育和理解加密算法原理的工具；

​	③P-Box：全称为Permutation Box（置换盒），是在密码学中经常用于加密和解密过程中的一种置换操作。P-Box用于重新排列输入数据的位，以创建输出数据。它的主要目的是增加密码算法的复杂性，提高安全性，以及打乱输入数据的分布，使密码更难以破解；

​	④S-Box：全称为Substitution Box（替代置换盒），是在密码学中常见的一种非线性变换，用于加密和解密过程中的数据转换。S-Box的主要作用是将输入数据的一部分映射到输出数据，通常用于替代明文中的位，以增加密码算法的混淆性和安全性；

​	⑤IP：全称为Initial Permutation（初始置换），是在密码学中用于数据加密的一种置换操作。它通常用于对明文数据进行初始置换，以改变数据的排列顺序和分布，从而增加密码算法的安全性；

​	⑥SP-Box：全称为Substitution-Permutation Box，是在密码学中常见的一种组合置换操作，通常用于分组密码（block cipher）的加密和解密算法中。SP-Box包括两部分，即替代（Substitution）和置换（Permutation），它的主要目的是对输入数据块进行非线性替代和线性置换，以增加密码算法的复杂性和安全性；

​	⑦SW：全称为Swap，是一种基本的计算机编程操作，用于交换两个变量的值或交换数据结构中两个元素的位置。SW操作通常用于改变变量或数据结构的状态，以便实现特定的算法或逻辑。SW操作是计算机科学中的一种常见操作，通常用于排序算法、数据交换和优化等领域。



### 10.2 参考资料

​	① Haibo,Hu.(2023).作业一：S-DES算法实现与GUI.shimo.[作业1：S-DES算法实现 (shimo.im)](https://shimo.im/docs/m5kvdlMaKvcENy3X/read);

​	② Schneier, B. (1996). Applied Cryptography: Protocols, Algorithms, and Source Code in C. Wiley.



## 结束语

​	在这份S-DES算法实现的开发手册中，我们提供了详细的信息和指南，旨在帮助您了解、安装、配置和使用S-DES算法实现程序。S-DES算法是信息安全领域中的经典加密算法之一，通过本程序，您可以更好地理解它的工作原理和应用；

​	我们的目标是使这份手册成为一个有用的资源，无论您是一名开发者还是一名用户。无论您是在学术研究中使用S-DES算法，还是在实际应用中保护数据的安全性，我们希望这份手册能够满足您的需求；

​	我们鼓励您在使用S-DES算法实现程序时，按照手册中的指南进行操作，并随时查阅手册以解决可能出现的问题。如果您有任何反馈、建议或疑问，请随时联系我们(shennmo@foxmall.com)，我们将尽力提供支持和帮助；

​	最后，感谢您选择使用S-DES算法实现程序。我们希望它能够满足您的加密需求，并为您的项目和研究提供有价值的支持。