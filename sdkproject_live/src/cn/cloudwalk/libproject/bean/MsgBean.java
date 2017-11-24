package cn.cloudwalk.libproject.bean;

/**
 * Created by administration on 2017/11/13.
 */

public class MsgBean {

    /**
     * result : 1
     *  msg  : 成功
     */

    private String result;
    private String msg;

    public MsgBean(String result,String msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
