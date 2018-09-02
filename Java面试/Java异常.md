## Java异常

### 1.什么是Java异常

答：异常是发生在程序执行过程中阻碍程序正常执行的错误事件。比如：用户输入错误数据、硬件故障、网络阻塞等都会导致出现异常。 只要在Java语句执行中产生了异常，一个异常对象就会被创建，JRE就会试图寻找异常处理程序来处理异常。如果有合适的异常处理程序，异常对象就会被异常处理程序接管，否则，将引发运行环境异常，JRE终止程序执行。 Java异常处理框架只能处理运行时错误，编译错误不在其考虑范围之内。

### 2.Java异常处理中有哪些关键字？

- *throw*:有时我们需要显式地创建并抛出异常对象来终止程序的正常执行。throw关键字用来抛出并处理运行时异常。
- *throws*:当我们抛出任何“被检查的异常(checked exception)”并不处理时，需要在方法签名中使用关键字throws来告知调用程序此方法可能会抛出的异常。调用方法可能会处理这些异常，或者同样用throws来将异常传给上一级调用方法。throws关键字后可接多个潜在异常，甚至是在*main()*中也可以使用throws。
- *try-catch*:我们在代码中用try-catch块处理异常。当然，一个try块之后可以有多个catch子句，try-catch块也能嵌套。每个catch块必须接受一个（且仅有一个）代表异常类型的参数。
- *finally*:finally块是可选的，并且只能配合try-catch一起使用。虽然异常终止了程序的执行，但是还有一些打开的资源没有被关闭，因此，我们能使用finally进行关闭。不管异常有没有出现，finally块总会被执行。

### 3.描述一下异常的层级。

答：Java异常是层级的，并通过继承来区分不同种类的异常。

- Throwable是所有异常的父类，它有两个直接子对象Error,Exception，其中Exception又被继续划分为“被检查的异常(checked exception)”和”运行时的异常（runtime exception,即不受检查的异常）”。 Error表示编译时和系统错误，通常不能预期和恢复，比如硬件故障、JVM崩溃、内存不足等。
- 被检查的异常（Checked exception）在程序中能预期，并要尝试修复，如FileNotFoundException。我们必须捕获此类异常，并为用户提供有用信息和合适日志来进行调试。Exception是所有被检查的异常的父类。
- 运行时异常（Runtime Exception）又称为不受检查异常，源于糟糕的编程。比如我们检索数组元素之前必须确认数组的长度，否则就可能会抛出ArrayIndexOutOfBoundException运行时异常。RuntimeException是所有运行时异常的父类。

## 4.Java异常类有哪些的重要方法？

答：Exception和它的所有子类没有提供任何特殊方法供使用，它们的所有方法都是来自其基类Throwable。

- String getMessage():方法返回Throwable的String型信息，当异常通过构造器创建后可用。
- String getLocalizedMessage()：此方法通过被重写来得到用本地语言表示的异常信息返回给调用程序。Throwable类通常只是用getMessage()方法来实现返回异常信息。
- synchronized Throwable getCause():此方法返回异常产生的原因，如果不知道原因的话返回null。(原文有拼写错误 应该是if 不是id)
- String toString():方法返回String格式的Throwable信息，此信息包括Throwable的名字和本地化信息。
- void printStackTrace()：该方法打印栈轨迹信息到标准错误流。该方法能接受PrintStream 和PrintWriter作为参数实现重载，这样就能实现打印栈轨迹到文件或流中。

### 5、描述Java 7 ARM(Automatic Resource Management，自动资源管理)特征和多个catch块的使用

如果一个try块中有多个异常要被捕获，catch块中的代码会变丑陋的同时还要用多余的代码来记录异常。有鉴于此，Java 7的一个新特征是：一个catch子句中可以捕获多个异常。示例代码如下：

```
catch(IOException | SQLException | Exception ex){
     logger.error(ex);
     throw new MyException(ex.getMessage());
}
```

大多数情况下，当忘记关闭资源或因资源耗尽出现运行时异常时，我们只是用finally子句来关闭资源。这些异常很难调试，我们需要深入到资源使用的每一步来确定是否已关闭。因此，Java 7用try-with-resources进行了改进：在try子句中能创建一个资源对象，当程序的执行完try-catch之后，运行环境自动关闭资源。下面是这方面改进的示例代码：

```
try (MyResource mr = new MyResource()) {
            System.out.println("MyResource created in try-with-resources");
        } catch (Exception e) {
            e.printStackTrace();
        }
```