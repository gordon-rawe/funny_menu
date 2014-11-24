funny_menu
==========

This is a menu mocking the Path but is more interesting...

The final effects are as follows:

![image](https://github.com/gordon-rawe/funny_menu/raw/master/1.png)
![image](https://github.com/gordon-rawe/funny_menu/raw/master/2.png)
![image](https://github.com/gordon-rawe/funny_menu/raw/master/3.png)

But these are just some static pictures, in fact the plus button can dock to the edges according to which side it is closer to, fixed some bugs such as becoming flat when dock exceed the right side.

And using it is quite easy, you just include the project files include:
```Java
Java files:
  +PositionHelper.java
  +MeFragment.java
  +AnimationHelper.java
xml files:
  +menu_fragment.xml
png files:
  +one.png
  +two.png
  +three.png
  +four.png
  +five.png
  +btn_main.png
```
After putting these files in the right places, use it as a fragment stating in a activity layout file, such as:
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >
    <fragment
        android:id="@+id/fragment"
        android:name="com.gordonrawe.funnymenu.MeFragment"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:layout_marginLeft="0dp"
        android:layout_marginTop="0dp"
        />
</LinearLayout>
```
Then every thing is done! Alright, if you want to customize the Radius or the animation speed parameters you can change the MeFragment.java contents.
Wish you a fabulous app...
