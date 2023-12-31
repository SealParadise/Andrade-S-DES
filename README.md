# 信息安全导论-作业1

## S-DES算法实现报告

### 一、开发者概况

任课教师：胡海波  

小组代号：Andrade 

小组成员：徐涵浩 明子鸿

联系方式：shennmo@foxmall.com

单位：重庆大学大数据与软件学院

![Aibo](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/Aibo.jpg)

​	注：左起第一位为徐涵浩，第二位为明子鸿。



### 二、仓库说明

​	本仓库包含Andrade小组完成的信息安全导论作业一的全部内容，主要为源代码（Code文件夹）、测试报告（README.md）、开发手册（Andrade S-DES 开发手册.md）、用户指南（Andrade S-DES 用户指南.md）、图片（Image文件夹），如有内容缺少、内容错误或其他问题请随时联系我们，联系方式shennmo@foxmall.com（请备明来意）。



### 三、测试报告

#### 第一关:基本测试

**题目**：根据S-DES算法编写和调试程序，提供GUI解密支持用户交互。输入可以是8bit的数据和10bit的密钥，输出是8bit的密文。



**答案**：

##### 1.1 用户交互界面

​	“结果”处的密文框和原文框无法直接进行编辑，只能输出展示“加密”和“解密”的结果；

![img1](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img1.png)



##### 1.2 点击“随机生成密钥”按钮，可以随机生成十位二进制密钥；

![img2](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img2.png)



![img3](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img3.png)

​	注：也可以手动输入密钥！



##### 1.3 加密解密测试

​	本系统提供纯二进制加密解密功能及ASCⅡ编码字符串加密解密功能，在用户交互界面左上角可以选择，不勾选表示使用纯二进制数字进行加密解密，勾选表示使用ASCⅡ编码字符串进行加密解密。第一关为基础测试，因此不展示使用ASCⅡ码进行加密解密的功能，仅展示使用纯二进制数字进行加密解密的功能；



###### 	1.3.1 加密测试

​		随机生成密钥：1011001011

​		原文：10101010

​		求得密文：11101000

![img5](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img5.jpg)



###### 	1.3.2 解密测试

​		沿用之前密钥：1011001011

​		密文：11101000

​		解得原文：10101010

![img6](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img6.jpg)



###### 	1.3.3 异常处理

​		当输入原文或密钥不符合规范时；

![img4](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img4.jpg)

![img7](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img7.jpg)



##### 1.4 总结

​	在本关卡中，我们小组成功完成了任务要求，主要做到了以下几点：

​	①**S-DES算法的理解和实现**： 成功地理解了S-DES算法的核心概念，包括初始置换、轮函数、S盒、P盒等。并且，编写了一个程序，能够根据输入的8位数据和10位密钥执行S-DES的加密和解密过程；

​	②**GUI设计和用户交互**： 成功地创建了一个用户友好的GUI界面，其中包括文本输入字段和按钮，以便用户能够输入数据和密钥，并选择执行加密或解密操作。这增强了程序的可用性和用户体验；

​	③**加密和解密功能的实现**： 实现了S-DES算法的加密和解密功能，并进行了系统性的测试和调试，以确保程序能够正确处理各种情况。这确保了程序的正确性和稳定性；

​	④**用户友好性和错误处理**： 致力于确保GUI界面易于使用，并提供了错误提示和反馈，以帮助用户在出现问题时解决问题。这增加了程序的友善度。



#### 第二关:交叉测试

**题目**：考虑到是**算法标准**，所有人在编写程序的时候需要使用相同算法流程和转换单元(P-Box、S-Box等)，以保证算法和程序在异构的系统或平台上都可以正常运行。设有A和B两组位同学(选择相同的密钥K)；则A、B组同学编写的程序对明文P进行加密得到相同的密文C；或者B组同学接收到A组程序加密的密文C，使用B组程序进行解密可得到与A相同的P。



**答案**：

​	我方与向宏老师班的yyds小组进行交叉测试。



##### 2.1 我方主动加密，由对方进行协作测试；

###### 	2.1.1 我方加密结果

​		密钥：1011011111

​		原文：10101010

​		密文：00100100

![img8](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img8.jpg)



###### 	2.1.2 对方加密结果

​		密钥：1011011111

​		原文：10101010

​		密文：00100100

![img9](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img9.png)



###### 	2.1.3 我方解密结果

​		密钥：1011011111

​		密文：00100100

​		原文：10101010

![img11](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img11.jpg)



###### 	2.1.4 对方解密结果

​		密钥：1011011111

​		密文：00100100

​		原文：10101010

![img10](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img10.png)



##### 2.2 对方主动加密，由我方进行协作测试；

###### 	2.2.1 对方加密结果

​		密钥：0001111000

​		原文：11011111

​		密文：01011101

![img15](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img15.jpg)



###### 	2.2.2 我方加密结果

​		密钥：0001111000

​		原文：11011111

​		密文：01011101

![img12](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img12.jpg)



###### 	2.2.3 对方解密结果

​		密钥：0001111000

​		密文：01011101

​		原文：11011111

![img14](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img14.jpg)



###### 	2.2.4 我方解密结果

​		密钥：0001111000

​		密文：01011101

​		原文：11011111

![img13](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img13.jpg)



##### 2.3 总结

​	在本关卡中，我方小组与对方小组成功进行了交叉测试，当所选密钥相同时，加密、解密的结果均相同且无误，在该过程中，我们主要实现了以下目标：

​	①**理解算法标准化的重要性**： 通过这个实验，我们深刻理解了算法标准化的重要性。确保不同人编写的程序都能够使用相同的算法流程和转换单元对数据进行加密和解密，是信息安全领域的关键，以保证数据的安全性和一致性；

​	②**遵循相同的算法规范**： 在实验中，我们两个小组都遵循了相同的算法规范，包括使用相同的密钥K、相同的P-Box和S-Box等。这确保了我们的程序可以生成相同的密文C；

​	③**成功生成一致的密文**： 实验的主要目标是确保A组和B组同学编写的程序可以生成相同的密文C。通过密钥K和算法一致性，我们成功地实现了这一目标。这意味着无论是A组加密后由B组解密，还是B组加密后由A组解密，都能够得到与原始明文P相同的结果；

​	④**强调算法的异构性和跨平台性**： 实验还强调了算法的异构性和跨平台性。这对于信息安全领域至关重要，因为它确保了不同系统和平台上的程序都能够正确地处理数据，而不会导致不一致或错误的结果；

​	⑤**加深对信息安全的理解**： 这个实验使我更深入地了解了信息安全领域的一些基本概念，包括加密算法的实际应用、密钥管理和算法标准化的必要性。这将有助于我更好地理解和解决未来的信息安全挑战。



#### 第三关:拓展功能

**题目**：考虑到向实用性扩展，加密算法的数据输入可以是ASII编码字符串(分组为1 Byte)，对应地输出也可以是ACII字符串(很可能是乱码)。



**答案**：

​	本系统提供纯二进制加密解密功能及ASCⅡ编码字符串加密解密功能，在用户交互界面左上角可以选择，不勾选表示使用纯二进制数字进行加密解密，勾选表示使用ASCⅡ编码字符串进行加密解密。在本关卡将展示使用ASCⅡ编码字符串进行加密解密的功能；



##### 3.1 随机生成密钥

​	密钥：0111001000

![img16](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img16.png)



##### 3.2 加密解密测试

###### 	3.2.1 加密测试

​		密钥：0111001000

​		原文：Andrade

​		求得密文：µH%![icon1](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/icon1.png)%X

![img17](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img17.jpg)



###### 	3.2.2 解密测试

​		密钥：0111001000

​		密文：µH%![icon1](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/icon1.png)%X

​		解得原文：Andrade

![img18](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img18.jpg)



##### 3.3 总结

​	在本关卡中，我们小组成功实现了对原系统功能的扩展，实现了对ASCⅡ码进行加密解密的操作，在过程中我们主要实现了以下目标：

​	①**实现ASCII字符串输入**： 在实验中，我们成功地修改了加密算法，以接受ASCII编码的字符串输入，每个字符表示一个字节。这允许用户输入文本数据，而不是传统的二进制数据；

​	②**实现ASCII字符串输出**： 实验还要求我们将加密后的数据以ASCII字符串的形式输出。虽然这可能导致输出中出现乱码字符，但我们成功地将加密后的结果呈现为文本形式，以便用户能够理解；

​	③**实用性扩展思考**： 在实验过程中，我们思考了如何使加密算法更具实用性。这包括考虑如何处理文本数据的加密和解密，以确保数据的完整性和保密性。这是一个有益的思考过程，对于将来的信息安全工作有着重要的意义；

​	④**用户友好性**： 通过允许ASCII字符串输入和输出，我们提高了程序的用户友好性。用户现在可以更轻松地与程序交互，而不需要处理复杂的二进制数据；

​	⑤**安全性考虑**： 尽管输入和输出是ASCII编码的字符串，但我们确保了加密算法的安全性。保护数据的安全性仍然是信息安全领域的关键问题，无论数据是文本还是二进制。



#### 第四关:暴力破解

**题目**：假设你找到了使用相同密钥的明、密文对(一个或多个)，请尝试使用暴力破解的方法找到正确的密钥Key。在编写程序时，你也可以考虑使用多线程的方式提升破解的效率。请设定时间戳，用视频或动图展示你在多长时间内完成了暴力破解。

**答案**：

##### 4.1 生成一组明、密文对

​	密钥：1001001100

​	原文：01010101

​	密文：00101101

![img19](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img19.jpg)

##### 4.2 破解测试

###### 	4.2.1 单线程破解

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



###### 	4.2.2 双线程破解

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



##### 4.3 总结

​	在本关卡中，我们小组对随机生成的一组明、密文对进行了暴力破解，并得到了可能的密钥，可以发现不止一组密钥符合条件，在该过程中我们主要实现了以下目标：

​	①**了解暴力破解**： 通过这个实验，我们深入了解了暴力破解攻击方法的原理。暴力破解是一种基于穷举的攻击，尝试所有可能的密钥组合，以找到正确的密钥。这有助于我更好地理解密码学的重要性，尤其是密钥强度和安全性的概念；

​	②**多线程优化**： 在实验中，我们成功地使用多线程来提高破解效率。这是一种实用的技巧，可以加速密钥搜索过程，特别是在处理大密钥空间时。这对于实际应用中的密码破解和安全性评估非常重要；

​	③**时间戳记录**： 为了展示破解的时间成本，我们使用时间戳记录了整个破解过程所需的时间。这有助于我们理解破解复杂密钥的耗时情况，以及评估不同安全性级别的密码系统的重要性；

​	④**密码学原理的应用**： 这个实验强调了密码学原理的实际应用。我们不仅了解了如何破解密钥，还了解了如何保护密钥和数据以抵御暴力破解攻击。这对于未来在信息安全领域工作非常有价值。



#### 第五关:封闭测试

**题目**：根据第4关的结果，进一步分析，对于你随机选择的一个明密文对，是不是有不止一个密钥Key？进一步扩展，对应明文空间任意给定的明文分组P，是否会出现选择不同的密钥K加密得到相同密文C的情况？

**答案**：

##### 5.1 **问题1：对于一个随机选择的明密文对，是否存在不止一个密钥Key？**

​	对于关卡4中的结果，我们选取的明密文对为：01010101（明文）与00101101（原文）；

![img19](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img19.jpg)



​	经过暴力破解得到的可能的密钥为：

​	①0011010110；

​	②0111010110；

​	③1000000000；

​	④1001001100；

​	⑤1100000010；

​	⑥1101001100。

![img20](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img20.jpg)



​	我们由此可以知道，通常情况下，对于一个给定的明文-密文对，只存在一个密钥Key 能够得到相应的密文C。而加密算法的目标是确保密文C与特定的密钥Key 相关联，以保护数据的安全性。因此，在现代加密算法中，密钥通常被设计为唯一的，以确保加密和解密的唯一性和可逆性。



##### 5.2 **问题2：对应明文空间的任意给定明文分组P，是否会出现选择不同的密钥K加密得到相同密文C的情况？**

​	利用以上获得的密钥进行测试，得到如下结果：

​	① 密钥：0011010110

​	     原文：01010101 

​	     所得密文：00101101

![img24](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img24.jpg)



​	② 密钥：0111010110

​	     原文：01010101 

​	     所得密文：00101101

![img25](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img25.jpg)



​	③ 密钥：1000000010

​	     原文：01010101 

​	     所得密文：00101101

![img26](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img26.jpg)



​	④ 原文：01010101 

​	     密钥：1001001100

​	     所得密文：00101101

![img27](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img27.jpg)



​	⑤ 密钥：1100000010

​	     原文：01010101

​	     所得密文：00101101

![img28](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img28.jpg)



​	⑥ 密钥：1101001100

​	     原文：01010101

​	     所得密文：00101101

![img29](https://github.com/m916662613/Andrade-S-DES/blob/main/Image/img29.jpg)

​	注：以上六个密钥经测试均能得到相同且符合的明密文对。



​	在理论上，对于不同的密钥K和相同的明文分组P，应该得到不同的密文C。这是因为加密算法的设计目的是确保不同密钥产生不同的密文，以增加破解的难度。然而，在一些特殊情况下，可能会出现不同密钥K产生相同密文C的情况，这通常是由于加密算法的缺陷或弱点所致。在现代密码学中，这种情况被视为严重的安全漏洞，因此加密算法的设计和评估都致力于防止这种情况的发生。



##### 5.3 总结

​	在本关卡中，我们进一步探讨了密码学的概念，特别是有关密钥多样性和明文-密文对的分析，主要思考了以下问题：

​	①**密钥多样性**： 通过实验，我们深入了解了加密算法的密钥多样性概念。一般情况下，对于给定的明文-密文对，只有一个唯一的密钥Key 能够得到相应的密文C。这突显了加密算法的目标，即确保密钥与密文之间的唯一性关联，以保护数据的安全性；

​	②**密钥选择的影响**： 我们理解了密钥选择对加密结果的影响。选择不同的密钥会导致不同的密文，而不同的密文通常需要不同的密钥进行解密。这强调了密钥的选择和管理对于信息安全的重要性；

​	③**密文关联问题**： 虽然在理论上，不同密钥K和相同明文分组P应该得到不同的密文C，但我们也了解到在特殊情况下可能会出现相同密文对应于不同密钥的情况。这通常是由于加密算法的弱点或漏洞引起的，应被视为严重的安全问题；

​	④**密码学的实际应用**： 这个实验进一步加强了我们对密码学在信息安全领域中的实际应用的理解。它强调了密码学的关键作用，以保护数据的机密性和完整性；

​	⑤**实验的反思**： 通过这个实验，我们反思了密码学的复杂性和对于密钥强度的要求。它也强调了密码学领域对于算法设计和安全性评估的高标准。