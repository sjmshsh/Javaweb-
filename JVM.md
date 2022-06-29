![image-20220629141656598](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629141656598.png)

**JVM(解释器) 和 VMware 以及 Virtual Box不是一会事儿**

虽然这三个都是虚拟机。

JVM只是对硬件设备进行了简单的抽象封装，能够达到跨平台的效果，而VMware以及VMware Box是用软件模拟。

# JVM运行时数据区

内存区域划分:

主要是分成

1. 堆
2. 栈
3. 程序计数器
4. 方法区

![image-20220629143521200](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629143521200.png)

一个操作系统是一个写字楼~

一个公司就视为是一个进程

这个公司就可以在这个写字楼里面租一块儿地方(JVM从系统申请到的整个内存)

装修的时候，可以对写字楼进行一些划分。

![image-20220629144105975](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629144105975.png)

堆：里面放的是 new 的对象

方法区：里面放的就是类对象

![image-20220629144447804](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629144447804.png)

程序计数器，是内存区域中最小的一个部分，里面只是放了一个内存地址，这个地址的含义，就是接下来要执行的指令地址。

我们写的.java代码 => .class(二进制字节码，里面就是一些指令) => 放到内存中 => 每个指令都有自己的地址 => CPU执行指令就需要去从内存里面取地址，然后在CPU上执行~

栈：放局部变量~

一个什么样的变量放到栈里，什么样的放到堆里。

new出来的都是在堆里，一个内置类型的变量就一定在栈里面吗(不一定)

一个变量在内存的哪个区域，取决于他是一个成员变量还是一个局部变量，还是一个静态变量，和他是不是引用类型没关系。

本地方法 (native method) 指的是JVM内部的方法(使用C++实现的方法)，虚拟机栈就是给上层的Java代码使用的

![image-20220629150137514](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629150137514.png)

![image-20220629151049469](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629151049469.png)

这个东西是哪个内存区域?

答案是栈!

![image-20220629151314751](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629151314751.png)

这个是局部变量，在栈上!!

![image-20220629151407667](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629151407667.png)

这个所在的内存区域是堆，因为这个时候t就是成员变量了。类在其他地方是要被new出来的。

![image-20220629151610818](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629151610818.png)

![image-20220629151938008](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629151938008.png)

引用类型如何理解?

把引用当作一个"低配指针"(从更严格的角度，从JVM源码的角度来看，引用不是一个指针)

![image-20220629152356824](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629152356824.png)

# JVM类加载

类加载是JVM中非常核心的一个流程，做的就是把.class文件转换成JVM中的类对象。

![image-20220629152959142](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629152959142.png)

![image-20220629153457118](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629153457118.png)

![image-20220629153827362](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629153827362.png)

![image-20220629153858918](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629153858918.png)

![image-20220629153940891](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629153940891.png)

![image-20220629154745005](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629154745005.png)

![image-20220629155024257](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629155024257.png)

## 类加载基本流程

![image-20220629161958599](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629161958599.png)

Loading : 把.class文件给找到，代码中需要加载某个类，就需要在某个特定目录下找到这个.class文件~找到之后，就需要打开这个文件，并且进行读取文件，此时这些数据就已经读到内存里面取了。

Verification : 验证就是把刚才读到内存里面的东西，进行一个校验，验证一下，刚才读到的这个内容是不是一个合法的.class文件~必须是编译器生成的.class文件才能通过验证，如果随便创建一个文件，后缀名设置为.class这是不能通过验证的。除了验证文件格式之外，也会验证一下文件里面的一些字节码指令对不对（方法里面要具体执行的指令）

Preparation : 这个准备阶段其实就是为了类对象中的一些成员来分配内存空间(静态变量...)，并且进行一个初步的初始化~~(把初始的空间设为全0)

![image-20220629163038454](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629163038454.png)

Resolution : 针对字符串常量进行的处理~.class文件中涉及到一些字符串常量，把这个类加载的过程中，就需要把这些字符串常量给替换成当前JVM内部已经持有的字符串常量的地址~

**不是程序一启动，就立即把所有的类都给加载了，用到哪个类就加载哪个类，而字符串常量池是最初启动JVM就有的(堆里面)**

Initialization : 这里就会真正的堆静态变量进行初始化，同时也会执行static代码块~

**static变量的初始化以及static代码块的执行是在对象的实例化之前的!!!**

```java
class A{
    public A() {
        System.out.println("A构造方法");
    }

    static {
        System.out.println("A static");
    }
}

class B extends A{
    public B() {
        System.out.println("A构造方法");
    }
    static {
        System.out.println("B static");
    }

}

public class TestClass {
    public static void main(String[] args) {
        B b = new B();
    }
}
```

**由父及子，静态先行**

## 类加载中的"双亲委派模型"

![image-20220629164740248](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629164740248.png)

![image-20220629165011497](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629165011497.png)

![image-20220629165552959](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629165552959.png)

![image-20220629165637826](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629165637826.png)

![image-20220629170030175](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629170030175.png)

**约定了扫描路线的优先级**

![image-20220629170408905](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629170408905.png)

![image-20220629171344055](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629171344055.png)

![image-20220629171957238](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629171957238.png)

# GC垃圾回收

![image-20220629172539566](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629172539566.png)

从代码编写的角度来看，内存的申请时机是非常明确的，但是内存释放的时机是非常模糊的，**典型问题就是，这个内存我是否还要继续使用?**

像C/C++这样的编程语言，内存释放是全手工的，一旦程序猿忘记了，那么就会造成**内存泄漏**

内存泄漏会导致系统可用资源越来越少，直到耗尽，此时其他进程再想申请，就申请不到了，内存泄漏，一向成为了程序猿幸福感的头部杀手。

![image-20220629173911393](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629173911393.png)

解决方案是有很多的:

1.C++智能指针，会基于C++中的RALL机制，在合适的时候自动释放内容(一般是通过引用计数的方式来衡量这个内存被引用了多少次，当引用计数为0的时候就真正释放内存)

**无法彻底根治**

在Rust中，采取的方案基于语法上的强校验，Rust引入了很多和内存操作相关的语法规则，编译器在编译期间，进行一个非常样的检查和校验，如果代码中存在内存泄漏的风险，直接编译就报错了~

**看起来很好，但是这样会导致语法非常丑陋，也限制了很多功能，以至于实现一些特殊功能的时候，必须要使用unsafe操作，一旦引入unsafe操作，之前校验的部分也就失效了**

![image-20220629175023111](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629175023111.png)

![image-20220629175304378](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629175304378.png)

![image-20220629175609174](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629175609174.png)

![image-20220629180206060](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629180206060.png)

**限制STW已经被控制在1ms之内了**



## **Java的垃圾回收的内存是哪些**

![image-20220629180906741](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629180906741.png)

![image-20220629181633233](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629181633233.png)

## 垃圾回收到底是如何回收的?

![image-20220629182230134](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629182230134.png)

**宁可放过也不可以错杀!!!**

## 如何找垃圾，标记垃圾，判定垃圾?

抛开Java，单说GC，判定垃圾有两种典型的方案~

1. 引用计数
2. 可达性分析

### 引用计数

当前这个对象，有多少个引用在指向它~~

![image-20220629182957104](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629182957104.png)

引用计数，就是通过一个变量来保存当前这个对象，被几个引用来指向~

![image-20220629231724514](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220629231724514.png)



















