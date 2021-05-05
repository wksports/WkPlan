# WkPlan
Wk规划APP version1.0(测试版)
-------------------------
**注意事项**：
<br>本次发布的是测试版，可能会有bug，很多地方欠缺考虑</br>
## APP介绍
*主要功能及设计思路*：生活类APP
<br>1.结合wheelview和CalendarView，viewpager,recycleview将日历与任务清单绑定显示</br>
<br>2.用SQLite储存数据，使数据能持久保存</br>
<br>3.用ViewPager，PagerAdapter实现banner轮播图，可以间隔一定时间自动轮转图片，也可以手动拖动图片实现切换，当前显示的页面下标的圆点会变色，其余未显示的页面下标的圆点则都是白色，
代表未显示</br>
<br>4.主页面运用toolbar、drawerlayout、navigationview实现滑动菜单功能，用viewpager+fregment+tablayout实现底部导航栏，导航栏和菜单栏点击图标会变色，未点击的则无色</br>
<br>5.个人中心界面采用自定义view，避免重复写布局</br>
<br>6.写清单时，先写出计划然后完成之后打勾未完成打差，退出后依然保存原来的内容</br>
<br>7.可以通过日历查看每一天的清单完成状况</br>
<br>8.启动APP有启动界面，用intent跳转到主界面</br>
### 功能展示





#### 心得体会
写了很多bug到现在还不知道错在了哪里，养成良好的敲代码习惯真的很重要

##### 待优化提升的地方
<br>1.UI过于简陋，逻辑有些混乱</br>
<br>2.写错的清单无法删除</br>
<br>3.个人中心还未实现具体功能,暂无登录注册<br>
<br>4.目前只能写当天的任务清单，将来会使用pickerview来实现该功能</br>
<br>5.**爱拼才会赢，爱拼才能无愧生活**</br>
