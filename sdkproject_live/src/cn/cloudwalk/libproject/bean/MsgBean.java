package cn.cloudwalk.libproject.bean;

/**
 * Created by administration on 2017/11/13.
 */

public class MsgBean {

    private String result;
    private String msg;
    private DataBean data;

    public MsgBean(String result, String msg, DataBean data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }
    public MsgBean(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }
    /**
     * result : 1
     * msg : 可报到
     * data : {"message":"","status":"3","messages":""}
     */


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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public DataBean(String message, String status, String messages) {
            this.message = message;
            this.status = status;
            this.messages = messages;
        }

        /**
         * message :
         * status : 3
         * messages :
         */

        private String message;
        private String status;
        private String messages;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessages() {
            return messages;
        }

        public void setMessages(String messages) {
            this.messages = messages;
        }
    }
}
