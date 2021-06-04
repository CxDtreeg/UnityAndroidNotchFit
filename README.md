# UnityAndroidNotchFit是什么
此库基于NotchFit库，用于给Unity提供Android 9.0以下版本的刘海屏高度

# 如何使用
1. 根据Unity中 `Project Setting -> Player -> Android -> Other Settings -> Configuration -> Scripting Backend` 中的配置的是Mono还是IL2CPP 替换项目中 `notch -> libs` 中的unitylib.jar
2. 运行`gradlew makeJar` 命令
3. 生成的jar文件在 `notch -> build -> libs -> notchkit.jar`
4. 将此jar文件放到Unity中的 `Plugins -> Android -> bin` 下，如果目录不存在就直接创建
5. 在Unity中调用 
```
var jc = new AndroidJavaClass("com.youle.unityproject.NotchFit");
var systemVersion = jc.CallStatic<int>("getSystemVersion");// 调用安卓原生方法获取系统版本
if (systemVersion < 28)//安卓版本小于9.0
{
  var offsets = jc.CallStatic<int[]>("getNotchSize");// 调用安卓原生犯法获取刘海尺寸，返回的是数组0是宽度 1是刘海的高度
  if (offsets != null && offsets.Length >= 2) leftOffset = offsets[1];
}
```

# 其它问题
1. Unity提供的jar文件在哪?

   文件Unity安装路径下的 `PlaybackEngines -> AndroidPlayer -> Variations` 根据Unity 的Scripting Backend的配置选择il2cpp目录或mono目录下的classes.jar文件
