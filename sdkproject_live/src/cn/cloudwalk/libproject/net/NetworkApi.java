package cn.cloudwalk.libproject.net;

import org.json.JSONException;
import org.json.JSONObject;

import cn.cloudwalk.libproject.bean.MsgBean;
import cn.cloudwalk.libproject.util.HttpUtil;

/**
 * Created by administration on 2017/11/13.
 */

public class NetworkApi {
//    public static String BASE_URL = "http://120.35.11.49:4545";
//public static String BASE_URL = "http://192.168.1.186:4545";
    public static String BASE_URL = "http://ydkq.jsqqy.com";
    public static final String API ="/Services";
    public static final String upLoadImage64 ="/DataService.ashx?action=upLoadPhoto";
    public static final String saveStreamInVivoDetection ="/DataService.ashx?action=saveStreamInVivoDetection";
    public static final String CheckIsFrozen ="/DataService.ashx?action=checkIsFrozen";
    public MsgBean checkIsFrozen(String id,String projId){
        MsgBean msgBean = null;
        String url = BASE_URL+API+CheckIsFrozen+"&id="+id+"&projId="+projId;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            String resultData = json.getString("result");
            String msg = json.getString("msg");
            msgBean = new MsgBean(resultData,msg);
            return msgBean;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msgBean;
    }
    public MsgBean uploadStreamInVivoDetection(String id,String projId,String basemapImgUrl,String verificationImgUrl){
        MsgBean msgBean = null;
        String url = BASE_URL+API+saveStreamInVivoDetection+"&id="+id+"&projId="+projId+"&basemapImgUrl="+basemapImgUrl+"&verificationImgUrl="+verificationImgUrl;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            String resultData = json.getString("result");
            String msg = json.getString("msg");
            msgBean = new MsgBean(resultData,msg);
            return msgBean;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msgBean;
    }
    public String uploadImage(String imageUrl){
        String imageAddress = "";
        JSONObject param = new JSONObject();
        try {
            param.put("pic", imageUrl);

        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String result = HttpUtil.postToUrl(BASE_URL+API+upLoadImage64, param.toString());
        try {
            JSONObject json = new JSONObject(result);
            JSONObject resultJson = json.getJSONObject("data");
            imageAddress = resultJson.getString("picUrl");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imageAddress;

    }
}
