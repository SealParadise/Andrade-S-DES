import java.util.HashMap;

public class sDES {
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

    public String leftShift(String halfKey){ //字符串左移一位的方法

        String str1 = halfKey.substring(1); //截取第二个字符到最后一个字符

        String str2 = halfKey.substring(0,1);//截取第一个字符

        return str1 + str2;//返回拼接字符串
    }

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

}
