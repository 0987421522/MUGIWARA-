# ProGuard rules for MUGIWARA

# Keep Room entities
-keep class com.mugiwara.data.model.** { *; }
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Keep Retrofit models
-keep class com.mugiwara.data.remote.** { *; }

# Keep Hilt
-keep class * extends dagger.hilt.android.HiltAndroidApp
-keep class * extends android.app.Application

# Keep Compose
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# General
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes Exceptions
