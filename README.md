Build.Gradle
---------------------------------------------------------------------------

repositories {
    mavenCentral()
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'

    compile 'com.jakewharton:butterknife:7.0.1'
    compile 'com.android.support:recyclerview-v7:23.0.+'
    compile 'com.android.support:cardview-v7:23.0.+'
    compile 'com.pavelsikun:vintage-chroma:1.4'
    compile 'me.priyesh:chroma:1.0.2'

    compile 'com.github.yukuku:ambilwarna:2.0.1'
    compile 'com.astuetz:pagerslidingtabstrip:1.0.1'
    compile 'com.squareup:android-times-square:1.6.4@aar'

    compile 'petrov.kristiyan.colorpicker:colorpicker-library:1.1.0'
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.facebook.android:facebook-android-sdk:4.+'
    compile 'com.facebook.android:facebook-android-sdk:[4,5)'

}
