package com.hailer.news;

import android.net.Uri;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.util.bean.UserInfo;
import com.socks.library.KLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 跟网络相关的工具类
 */
public class UserManager {

    private static volatile UserManager instance;

    private UserInfo userinfo = new UserInfo();
    private int platfromType = USER_TYPE_FACEBOOK;
    private String mServerToken;

    public static final int USER_TYPE_FACEBOOK = 1;

    private UserManager() {
        //null
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }

        return instance;
    }

    public UserInfo getUserinfo(){
        return userinfo;
    }

    /**
     *
     */
    public Boolean requestFBInfo(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();

        if (accessToken == null || profile == null) {
            KLog.d("bailei----need login");
            //userInfo.setFBToken(null);
            return false;
        }

        setUserInfo(profile.getId(), profile.getName(), profile.getProfilePictureUri(100,100),accessToken.getToken());
        KLog.d("bailei----getFBInfo,  token="+accessToken.getToken()+", id="+profile.getId());
        KLog.d("bailei----getFBInfo,  name="+profile.getName());
        KLog.d("bailei----getFBInfo,  linkuri="+profile.getLinkUri()+", picuri="+profile.getProfilePictureUri(100,100));
        return true;

    }

    public Boolean requestFBToken(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
         if (accessToken == null ) {
            KLog.d("bailei----need login");
            //userInfo.setFBToken(null);
            return false;
        }

        setUserInfo(null, null, null, accessToken.getToken());
        KLog.d("bailei----getFBInfo,  token="+accessToken.getToken()+", id="+accessToken.getToken());
        return true;

    }

    public void setPlatformToken(String platformToken){
        userinfo.setPlatformToken(platformToken);
    }


    public void setUserInfo(String platformId, String name, Uri iconUri, String platformToken){
        userinfo.setName(name);
        userinfo.setPlatformId(platformId);
        //userinfo.setIconUri(iconUri);
        userinfo.setPlatformToken(platformToken);
    }

    public void setUserInfo(String platformId, String name, String iconUri){
        userinfo.setName(name);
        userinfo.setPlatformId(platformId);
        userinfo.setIconUri(iconUri);
    }

    public void setServerToken(String token) {
        //userinfo.setServerToken(token);
        mServerToken = token;
    }

    public String getServerToken() {
        //userinfo.getServerToken();
        return mServerToken;
    }

    public String getName() {
        return userinfo.getName();
    }

    public String getIconUri() {
        return userinfo.getIconUri();
    }

    public void saveUserInfo(LoginInfo loginInfo){
        setServerToken(loginInfo.token);
        setUserInfo(loginInfo.userInfo.platformId,
                loginInfo.userInfo.name, loginInfo.userInfo.avatar);
    }

}
