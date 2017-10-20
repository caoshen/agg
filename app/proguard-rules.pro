# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/cs/android/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Keep file name and line no
-keepattributes SourceFile,LineNumberTable

# Jsoup
-dontwarn org.jsoup.**
-keep class org.jsoup.**{*;}

# Tencent Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# Alibaba fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes Annotation

# Okio
-dontwarn okio.**
-dontwarn org.codehaus.**
-keep public class org.codehaus.**{*;}

-keep class xyz.dcme.agg.ui.postdetail.data.**{*;}
