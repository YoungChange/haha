## 目录结构
以MVP框架实现
### 一级目录
```
.
├── app
│   ├── build.gradle                                                            **当前模块的Gradle编译文件**
│   ├── proguard-rules.pro                                                      **当前模块的混淆文件**
│   └── src
│       ├── androidTest                                                         **系统自动生产的测试文件**
│       ├── main                                                                **源码目录**
│       └── test                                                                **系统自动生产的测试文件**
├── build.gradle                                                                **项目的Gradle编译文件**
├── gradle                                                                      **Gradle文件夹**
│   └── wrapper
├── gradle.properties                                                           **Gradle属性文件**
├── gradlew                                                                     **Gradle信息文件**
├── gradlew.bat                                                                 **Gradle执行文件**
├── hailernews.jks                                                              **release秘钥**
├── local.properties                                                            **本地属性文件，存放了SDk路径，可通过AS进行配置**         
└── settings.gradle                                                             **Gradle配置文件，包含当前目录的Mudule，可通过AS进行添加和删除**
```
### 代码目录
```
.
├── AndroidManifest.xml                                                         **Manifest文件**
├── java
│   └── com
│       └── hailer
│           └── news
│               ├── api
│               │   ├── APIConfig.java                                          **服务器端接口定义**
│               │   ├── BaseSchedulerTransformer.java                           **自定义的一个Rxjava线程模型**
│               │   ├── bean
│               │   │   ├── CommentInfo.java                                    **评论信息**
│               │   │   ├── LoginInfo.java                                      **登录信息**
│               │   │   ├── NewsDetail.java                                     **新闻详情**
│               │   │   └── NewsItem.java                                       **新闻列表中的单个信息**
│               │   ├── INewsAPI.java                                           **使用Retrofit框架的数据接口**
│               │   ├── NullOnEmptyConverterFactory.java                        **返回信息为空时的处理**
│               │   └── RetrofitService.java                                    **使用Retrofit框架的网络处理**
│               ├── common                                                      **各个界面模块使用的公共类和基类**
│               │   ├── BaseActivity.java
│               │   ├── BaseRecyclerAdapter.java
│               │   ├── BaseRecyclerViewHolder.java
│               │   ├── BaseRecycleViewDivider.java
│               │   ├── ErrMsg.java                                             **错误信息**
│               │   ├── LoadType.java                                           **list页面的加载类型**
│               │   ├── OnItemClickListener.java
│               │   ├── RecyclerNavigationAdapter.java
│               │   ├── RecyclerNavigationViewHolder.java
│               │   ├── RemoteSubscriber.java                                   **Retrofit订阅者，model层和网络端交互**
│               │   ├── RxCallback.java                                         **Rxjava的callback函数，P层和Model层交互**
│               │   └── ToolBarType.java
│               ├── feedback                                                    **feedback界面**
│               │   ├── FeedbackActivity.java
│               │   ├── FeedbackContract.java
│               │   └── FeedbackPresenter.java
│               ├── login                                                       **登录界面**
│               │   ├── LoginActivity.java
│               │   ├── LoginContract.java
│               │   └── LoginPresenter.java
│               ├── model
│               │   ├── FacebookDataSource.java                                 **facebook的model层**
│               │   ├── LocalDataSource.java                                    **本地的model层**
│               │   └── RemoteDataSource.java                                   **远端的model层**
│               ├── news                                                        **新闻列表界面**
│               │   ├── NewsActivity.java
│               │   ├── NewsContract.java
│               │   ├── NewsFragmentAdapter.java
│               │   ├── NewsItemViewType.java
│               │   ├── NewsListAdapter.java
│               │   ├── NewsListFragment.java
│               │   ├── NewsListViewHolder.java
│               │   └── NewsPresenter.java
│               ├── NewsApplication.java
│               ├── newsdetailandcomment                                                  **新闻详情和新闻评论列表**
│               │   ├── CommentsListAdapter.java
│               │   ├── CommentsListViewHolder.java
│               │   ├── NewsCommentFragment.java                                           **新闻评论列表界面**   
│               │   ├── NewsDetailAndCommentActivity.java
│               │   ├── NewsDetailAndCommentContract.java
│               │   └── NewsDetailAndCommentPresenter.java
│               │   ├── NewsDetailFragment.java                                            **新闻详情界面**
│               ├── splash
│               │   ├── SplashActivity.java                                                **启动页界面**
│               │   ├── SplashContract.java
│               │   ├── SplashModel.java
│               │   └── SplashPresenter.java
│               ├── UserManager.java                                            **用户信息**
│               └── util
│                   ├── annotation
│                   │   └── ActivityFragmentInject.java                         **activity的注解注入**
│                   ├── bean
│                   │   ├── FeedBackMessage.java                                 **反馈信息bean**
│                   │   ├── NavigationItem.java                                  **Nav的item bean**
│                   │   ├── NewsChannelBean.java                  
│                   │   ├── NewsComment.java
│                   │   └── UserInfo.java
│                   ├── CommentVoteUtil.java
│                   ├── FuncUtil.java
│                   ├── GlideUtils.java                                         **Glide的util函数**
│                   ├── InputMethodLayout.java
│                   ├── MeasureUtil.java
│                   ├── NetworkUtil.java                                        **网络处理的util函数**
│                   ├── RxBus.java                                                           
│                   └── TextUtil.java
└── res                                                                         **资源文件夹**
```