# auto-size
Java Annotations for component / variable scaling (screen resolution)

[![](https://jitpack.io/v/fcannizzaro/auto-size.svg)](https://jitpack.io/#fcannizzaro/auto-size)

![icon](https://raw.githubusercontent.com/fcannizzaro/auto-size/master/icon.png)

## Dependence

### Gradle
```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.fcannizzaro:auto-size:0.1.0'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.fcannizzaro</groupId>
        <artifactId>auto-size</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

###  Download JAR
[Release 0.1.0](https://github.com/fcannizzaro/auto-size/releases/tag/0.1.0)

## Init
```java
public static void main(String[] args){
  AutoSize.from( width , height );
}
```
where **width** / **height** is your workspace screen width/height resolution.

## Usage
```java
public MyClass(){
  AutoSize.bind(this);
}
```

## Annotations

### @AutoValue
Scale numeric value by scaling factor (int, float, double, long supported)
```java
@AutoValue
int space = 500,
        x = 150;
```

### @AutoFont
```java
@AutoFont(16)
JButton button;
```
Scale font size by scaling factor.

### @AutoScale
```java
@AutoScale(width = 400, height = 48, as = "010")
int space = 500,
        x = 150;
```
- width, height (*required*)
- as (*optional*)
  - format (**1** apply, **0** not), default value = "111"
      - setMinimumSize
      - setPreferredSize
      - setMaximumSize

### @AutoMargin
```java

// apply on each side
@AutoMargin(16)
JPanel panel;

// specific sides
@AutoMargin(l = 16, r = 16)
JPanel panel;

```
Add an empty border to component (as **CompoundBorder**)
- **l** = left side
- **r** = right side
- **t** = top side
- **b** = bottom side

### @AutoConstruct
auto create instance of graphic components fields (subclass of [Component](https://docs.oracle.com/javase/7/docs/api/java/awt/Component.html) )
```java
@AutoConstruct
class MyClass{

  Jframe frame;
  JPanel panel;
  Jbutton button;

  public MyClass(){
    AutoSize.bind(this);
    //frame, panel, button are already != null
  }

}
```

### @Ignore
skip field creation using **AutoConstruct**
```java
@Ignore
JButton button;
```
