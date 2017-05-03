<h1 align="center">RecyclerView Life</h1>

<p align="center">
  <a href="http://developer.android.com/index.html"><img src="https://img.shields.io/badge/platform-android-green.svg?style=flat-square"></a>
  <a href="https://android-arsenal.com/api?level=14"><img src="https://img.shields.io/badge/API-7%2B-brightgreen.svg?style=flat-square"></a>
  <a href="https://github.com/allsoft777/RecyclerViewLife/blob/master/LICENSE"><img src="https://img.shields.io/badge/license-APACHE-blue.svg?style=flat-square"></a>
</p>

RecyclerView를 활용하여 리스트뷰를 구현할때 사용되는 많은 Framework Api를 캡슐화 시키고 다양한 ViewBinder의 관리를 쉽게 할 수 있도록 편의 기능을 제공한다. 또한 Infinite Scrolling을 지원하는 별도의 Listener 구현체를 제공하고 HeaderView와 FooterView도 하나의 리스트 타입으로 제공해주고 있습니다. 해당 Listener를 통하여 자동으로 데이터를 로드할 수 있고 이를 HeaderView 또는 FooterView에(이하 HF) 진행 상황을 표시할 수 있으며 모두 커스텀하게 ViewBinder를 구현 할 수 있다.
<br>

## Screenshot
![](./gif/infinite_loading_bottom.gif)__
![](./gif/infinite_loading_top.gif)
<br><br>
![](./gif/infinite_loading_top_bottom.gif)__
![](./gif/touch_loading_top_bottom.gif)
<br><br>
![](./gif/infinite_loading_20ea_bottom.gif)


## Build Settings

##### Gradle
```groovy
dependencies {
    compile 'com.seongil:recyclerviewlife:1.0.4'
}
```
##### Maven
```xml
<dependency>
    <groupId>com.seongil</groupId>
    <artifactId>recyclerviewlife</artifactId>
    <version>1.0.4</version>
    <type>pom</type>
</dependency>
```
<br>
<a id="simple_listview"></a>

<b>자세한 설명은 하기 블로그 페이지를 참고바랍니다.<b><br>
[페이지 바로가기](http://softwaree.tistory.com/5)
<br><br>


License
-------

    Copyright (C) 2017 Seongil Kim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
