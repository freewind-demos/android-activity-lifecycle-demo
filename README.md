# Android Activity 生命周期演示

## 简介

本 Demo 演示 Android Activity 的完整生命周期，帮助开发者理解 Activity 在不同状态间的转换过程。

## 基本原理

### 什么是 Activity 生命周期？

Activity 是 Android 应用的基本组件，代表一个用户界面。每个 Activity 从创建到销毁会经历多个状态，这些状态的转换由 Android 系统自动管理，理解这些生命周期对于构建高质量应用至关重要。

### 生命周期回调方法

| 方法 | 调用时机 | 用途 |
|------|----------|------|
| onCreate() | Activity 首次创建 | 初始化界面、数据 |
| onStart() | Activity 即将可见 | 准备显示 |
| onResume() | Activity 可交互 | 获取焦点 |
| onPause() | 失去焦点 | 暂停动画、保存数据 |
| onStop() | 完全不可见 | 释放资源 |
| onDestroy() | 即将销毁 | 完全清理 |
| onRestart() | 从停止状态恢复 | 重新初始化 |

### 生命周期流程图

```
启动 App
    │
    ▼
onCreate() → onStart() → onResume()
    │                         │
    │                    [用户操作]
    │                         │
    ▼                         ▼
onPause() → onStop() ─────────┘
    │           │
    │    [返回应用]
    │           │
    ▼           ▼
onRestart() → onStart() → onResume()
    │
    [退出应用]
    │
    ▼
onPause() → onStop() → onDestroy()
```

## 启动和使用

### 环境要求
- Android Studio Arctic Fox 或更高版本
- JDK 11+
- Gradle 7.0+

### 安装和运行
1. 使用 Android Studio 打开本项目
2. 连接 Android 设备或启动模拟器
3. 点击 Run 运行项目

### 使用方法
1. 启动应用后，观察主界面显示的生命周期事件
2. 点击"启动第二个 Activity"按钮，观察生命周期变化
3. 返回主 Activity，再次观察生命周期回调

## 教程

### 1. 理解 onCreate()

onCreate() 是 Activity 生命周期中第一个调用的方法，在这个方法中我们通常：
- 设置布局文件 (setContentView)
- 初始化视图组件
- 绑定数据

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    statusText = findViewById(R.id.statusText)
}
```

**注意**：必须调用 super.onCreate()，否则会抛出异常。

### 2. 理解 onStart() 和 onResume()

- onStart()：Activity 即将可见，但还没有获得焦点
- onResume()：Activity 完全可见，并且获得焦点，可以与用户交互

这两个方法容易混淆，区别在于：
- onStart() 时 Activity 可能在其他 Activity 下方
- onResume() 时 Activity 必须在最前面

### 3. 理解 onPause() 和 onStop()

当用户离开 Activity 时，会依次调用 onPause() 和 onStop()：

- onPause()：Activity 失去焦点，但仍然可见（可能是弹出对话框）
- onStop()：Activity 完全不可见

**重要**：不要在 onPause() 中执行耗时操作，这会影响用户体验。

### 4. 配置变更与 onDestroy()

当屏幕旋转、语言改变等配置变更时：
1. Activity 会调用 onDestroy()
2. 重新创建新的 Activity 实例

可以使用 onSaveInstanceState() 保存数据，onRestoreInstanceState() 恢复数据。

## 关键代码详解

### MainActivity.kt 核心代码

```kotlin
// Activity 创建时调用
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    // 初始化视图和数据
}
```

- Bundle 参数包含之前保存的状态数据
- savedInstanceState 为 null 表示首次创建

```kotlin
// 保存状态数据
override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString("lifecycle_events", lifecycleEvents.toString())
}
```

```kotlin
// 恢复状态数据
override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    val events = savedInstanceState.getString("lifecycle_events", "")
}
```

## 注意事项

1. **不要在生命周期方法中做耗时操作**：这会导致应用卡顿
2. **合理使用 onPause() 和 onStop()**：释放资源但保留状态
3. **处理配置变更**：使用 ViewModel 或 onSaveInstanceState 保存数据
4. **避免内存泄漏**：确保在 onDestroy() 中取消注册监听器

## 使用场景

- 需要在 Activity 可见/不可见时执行特定逻辑
- 需要保存和恢复 Activity 状态
- 需要在用户离开时保存未保存的数据
- 需要正确管理资源以优化内存使用
