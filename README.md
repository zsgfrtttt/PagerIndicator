# PagerIndicator
viewPager背景渐变指示器

### 效果图
(https://github.com/donkingliang/GroupedRecyclerViewAdapter/blob/master/GroupedRecyclerViewAdapter%E6%BC%94%E7%A4%BA%E5%9B%BE/%E5%88%86%E7%BB%84%E7%9A%84%E5%88%97%E8%A1%A8.jpg) 


### 引入依赖 
在Project的build.gradle在添加以下代码
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在Module的build.gradle在添加以下代码
```
	implementation 'com.github.donkingliang:GroupedRecyclerViewAdapter:2.3.0'
```
**注意：** 依赖迁移至Androidx

### 基本使用

**1、布局容器IndicatorContainer**

```xml
 <com.indicator.library.IndicatorContainer
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:layout_marginTop="4dp"
        android:layout_marginBottom="4dp"
        android:id="@+id/indicator_wrap"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        app:indicatorColor="@color/color3"
        app:verticalPadding="6dp"
        app:horizontalPadding="12dp"/>

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/vp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

**2、绑定viewpager并设置Adapter**
```java
container = findViewById(R.id.indicator_wrap);
        viewPager = findViewById(R.id.vp);
        container.bind(viewPager).setAdapter(new IndicatorAdapter() {
            @NonNull
            @Override
            public List<String> getTitle() {
                return list;
            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
```
