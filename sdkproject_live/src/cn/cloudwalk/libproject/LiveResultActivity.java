package cn.cloudwalk.libproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.cloudwalk.FaceInterface.CW_LivenessCode;
import cn.cloudwalk.libproject.bean.MsgBean;
import cn.cloudwalk.libproject.net.NetworkApi;
import cn.cloudwalk.libproject.util.FileUtil;
import cn.cloudwalk.libproject.util.NullUtils;
import cn.cloudwalk.libproject.util.PreferencesUtils;
import cn.cloudwalk.libproject.util.ProgressDialogUtils;
import cn.cloudwalk.libproject.view.RoundProgressBar;
import cn.fisc.utils.ImageUtils;

/**
 * 结果提示页面
 *
 * @author ysyhpc
 */
public class LiveResultActivity extends Activity {

	public static final String FACEDECT_RESULT_TYPE = "facedect_result_type";
	public static final String FACEDECT_RESULT_MSG = "facedect_result_msg";
	public static final String ISLIVEPASS = "islivepass";
	public static final String ISVERFYPASS = "isverfypass";
	public static final String FACESCORE = "facescore";
	public static final String SESSIONID = "sessionid";
	public static final String CAMERAID = "cameraid";
	private int cameraId;
	int type;
	boolean islivepass, isverfypass;
	double facescore;
	String sessionid;
    private ProgressDialogUtils progressDialog;
	Button bt_restart, bt_ok;
	TextView tv_tip, tv_title;
	SoundPool sndPool;
    Bundle bundle;
	RoundProgressBar pb_circle;
	private int progress = 0;
	ImageView iv_result;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				pb_circle.setProgress(progress);
				if (Math.abs(progress) <= pb_circle.getMax()) {
					progress = progress - 2;
					mHandler.sendEmptyMessageDelayed(1, 5);
				}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cloudwalk_activity_facedect_result);
		progressDialog = new ProgressDialogUtils(this, R.style.Progress);


		getIntentData();
		initViews();

		if (type == Bulider.FACE_VERFY_PASS || type == CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK) {
			pb_circle.setArcColor(getResources().getColor(R.color.face_result_ok));
			iv_result.setImageResource(R.drawable.cloudwalk_gou);
			bt_restart.setVisibility(View.GONE);
			bt_ok.setText(R.string.commit);
		} else {
			Log.e("Bulider.bestFaceData",Bulider.bestFaceData.length+"");
			pb_circle.setArcColor(getResources().getColor(R.color.face_result_fail));
			iv_result.setImageResource(R.drawable.cloudwalk_fail);
			bundle = getIntent().getExtras();
			progressDialog.showProgressDialogWithText("正在上传数据，请稍后！");
			if (null != Bulider.bestFaceData){
				//将抓拍的到的图片保存到本地
				Log.e("bundle",bundle.getInt("getMemberId")+"");
				Log.e("bundle",bundle.getString("getProjId")+"");
				Log.e("bundle",bundle.getString("getImgUrl")+"");
				FileUtil.writeByteArrayToFile(Bulider.bestFaceData, Environment.getExternalStorageDirectory().getAbsolutePath() + "/"  + "test.jpg");
				//生成的这张图片对接你们自己的接口做上报
                String imageSrc = ImageUtils.getImageStr(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"  + "test.jpg");

                new UpLoadImageAsyncTask().execute(imageSrc);
			}else {
				//检测失败抓拍到的那张最佳人脸如果没有，则用你们自己接口的头像做失败记录，这样至少可以看到是哪个对象的人脸失败了
				Log.e("app","使用特征图片上报");
				new UpLoadErrorAsyncTask().execute(bundle.getString("getImgUrl")+"");
			}

		}
		changeCircle();

		changeUI(type);

		bt_restart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                progressDialog.showProgressDialogWithText("获取权限中，请稍后！");
                new checkIsFrozenAsyncTask().execute();


			}

		});
		bt_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (type == Bulider.FACE_VERFY_PASS || type == CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK) {
					if(Bulider.mResultCallBack!=null)Bulider.mResultCallBack.result(islivepass, isverfypass, sessionid, facescore, type,
							Bulider.bestFaceData, Bulider.liveDatas,cameraId);
				}else{
					if(Bulider.mResultCallBack!=null)Bulider.mResultCallBack.result(islivepass, isverfypass, sessionid, facescore, type,
							null, Bulider.liveDatas,cameraId);
				}
				finish();
			}
		});

		String msg = getIntent().getStringExtra(FACEDECT_RESULT_MSG);
		if (NullUtils.isNotEmpty(msg)) {
			tv_tip.setText(msg);
		}

	}

	private void changeCircle() {
		pb_circle.setMax(100);
		progress--;
		mHandler.sendEmptyMessageDelayed(1, 500);
	}

	private void initViews() {
		bt_restart = (Button) findViewById(R.id.bt_restart);
		bt_ok = (Button) findViewById(R.id.bt_ok);
		iv_result = (ImageView) findViewById(R.id.iv_result);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		tv_title = (TextView) findViewById(R.id.tv_title);
		pb_circle = (RoundProgressBar) findViewById(R.id.pb_circle);
	}

	private void getIntentData() {
		type = getIntent().getIntExtra(FACEDECT_RESULT_TYPE, CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK);
		islivepass = getIntent().getBooleanExtra(ISLIVEPASS, false);
		isverfypass = getIntent().getBooleanExtra(ISVERFYPASS, false);
		facescore = getIntent().getDoubleExtra(FACESCORE, 0d);
		sessionid = getIntent().getStringExtra(SESSIONID);
		cameraId = getIntent().getIntExtra(CAMERAID,0);
	}

	private void changeUI(int type) {

		sndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		switch (type) {
		case Bulider.FACE_VERFY_PASS:// 人脸比对通过
			int streamID = sndPool.load(this, R.raw.cloudwalk_verfy_suc, 1);
			tv_title.setTextColor(0xff3abc38);
			tv_title.setText(R.string.faceverifysuc);
			tv_tip.setText(R.string.face_verfy_ok_tip);

			break;
		case CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK:// 活体检测通过
			streamID = sndPool.load(this, R.raw.cloudwalk_success, 1);
			tv_title.setTextColor(0xff3abc38);
			tv_tip.setText(R.string.facedect_ok_tip);
			tv_title.setText(R.string.facedectsuc);

			break;

		case Bulider.FACE_VERFY_FAIL:// 人脸比对不通过
			streamID = sndPool.load(this, R.raw.cloudwalk_verfy_fail, 1);
			tv_title.setTextColor(0xffd8282a);
			tv_title.setText(R.string.faceverifyfail);
			tv_tip.setText(R.string.face_verfy_fail_tip);
			break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND:// 没有人脸
				tv_tip.setText(R.string.cloudwalk_tip_no_face);
				streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);
				break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND + 1:// 换脸攻击
				if (PreferencesUtils.getBoolean(LiveResultActivity.this,"pref_showattack",false)) {
					tv_tip.setText(R.string.faceattack_1);
				}
				streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);
				break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND + 2:// 嘴被抠取
				if (PreferencesUtils.getBoolean(LiveResultActivity.this,"pref_showattack",false)) {
					tv_tip.setText(R.string.faceattack_2);
				}
				streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);
				break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND + 3:// 眼被抠取
				if (PreferencesUtils.getBoolean(LiveResultActivity.this,"pref_showattack",false)) {
					tv_tip.setText(R.string.faceattack_3);
				}
				streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);
				break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND + 4:// 黑白图片攻击
				if (PreferencesUtils.getBoolean(LiveResultActivity.this,"pref_showattack",false)) {
					tv_tip.setText(R.string.faceattack_4);
				}
				streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);
				break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND + 5:// 边框攻击
				if (PreferencesUtils.getBoolean(LiveResultActivity.this,"pref_showattack",false)) {
					tv_tip.setText(R.string.faceattack_5);
				}
				streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);
				break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND + 6:// 纸张攻击
				if (PreferencesUtils.getBoolean(LiveResultActivity.this,"pref_showattack",false)) {
					tv_tip.setText(R.string.faceattack_6);
				}
				streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);
				break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND + 7:// 视频攻击
				if (PreferencesUtils.getBoolean(LiveResultActivity.this,"pref_showattack",false)) {
					tv_tip.setText(R.string.faceattack_7);
				}
				streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);
				break;
			case CW_LivenessCode.CW_FACE_LIVENESS_NOT_ACTIONBLEND + 8:// 眼睛被遮挡
				if (PreferencesUtils.getBoolean(LiveResultActivity.this,"pref_showattack",false)) {
					tv_tip.setText(R.string.faceattack_8);
				}
			streamID = sndPool.load(this, R.raw.cloudwalk_failed_actionblend, 1);

			break;
		case CW_LivenessCode.CW_FACE_LIVENESS_OVERTIME:// 活体超时
			streamID = sndPool.load(this, R.raw.cloudwalk_failed_timeout, 1);
			tv_title.setTextColor(0xffd8282a);
			tv_title.setText(R.string.facedect_fail);
			tv_tip.setText(R.string.facedectfail_timeout);

			break;

		case Bulider.FACE_VERFY_NETFAIL:// 网络异常
			streamID = sndPool.load(this, R.raw.cloudwalk_net_fail, 1);

			tv_tip.setText(R.string.facedec_net_fail);
			break;
		case CW_LivenessCode.CW_FACE_LIVENESS_AUTH_ERROR:// 授权失败
			tv_tip.setTextColor(0xff7d7d7d);
			tv_tip.setText(R.string.facedectfail_appid);
			bt_restart.setVisibility(View.GONE);

			break;
		case Bulider.BESTFACE_FAIL:// 最佳人脸获取失败
			streamID = sndPool.load(this, R.raw.cloudwalk_failed, 1);
			tv_title.setTextColor(0xffd8282a);
			tv_title.setText(R.string.facedect_fail);
			tv_tip.setText(R.string.bestface_fail);

			break;
		}
		sndPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

				int playId = sndPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);

			}
		});
	}
    public class UpLoadImageAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            return new NetworkApi().uploadImage(params[0]);
        }

        @Override
        protected void onPostExecute(String data) {
            if(!data.equals("")){
                new UpLoadErrorAsyncTask().execute(data);
            }else{
                progressDialog.dismissProgressDialog();
                Toast.makeText(LiveResultActivity.this,"网络连接异常！",Toast.LENGTH_SHORT).show();
            }

        }



    }
    public class UpLoadErrorAsyncTask extends AsyncTask<String, Void, MsgBean> {
        @Override
        protected MsgBean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.e("bundle", bundle.getInt("getMemberId") + "");
            Log.e("bundle", bundle.getString("getProjId") + "");
            Log.e("bundle", bundle.getString("getImgUrl") + "");
            return new NetworkApi().uploadStreamInVivoDetection(bundle.getInt("getMemberId") + "", bundle.getString("getProjId") + "", bundle.getString("getImgUrl") + "", params[0]);
        }

        @Override
        protected void onPostExecute(MsgBean msgBean) {
            progressDialog.dismissProgressDialog();
            if (msgBean.getResult().equals("1")) {
            } else {
				AlertDialog.Builder builder = new AlertDialog.Builder(LiveResultActivity.this);
				// 设置提示框的标题
				builder.setTitle("提示：").
						// 设置提示框的图标
						// setIcon(R.drawable.icon).
						// 设置要显示的信息
								setMessage(msgBean.getMsg()).
						// 设置确定按钮
								setPositiveButton("确定", null);

				// 生产对话框
				AlertDialog alertDialog = builder.create();
				alertDialog.setCanceledOnTouchOutside(false);
				// 显示对话框
				alertDialog.show();
				alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88,190,252));
                //Toast.makeText(LiveResultActivity.this, msgBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }
        public class checkIsFrozenAsyncTask extends AsyncTask<Void, Void, MsgBean> {
            @Override
            protected MsgBean doInBackground(Void... params) {
                // TODO Auto-generated method stub
                Log.e("bundle",bundle.getInt("getMemberId")+"");
                Log.e("bundle",bundle.getString("getProjId")+"");
                Log.e("bundle",bundle.getString("getImgUrl")+"");
                return new NetworkApi().checkIsFrozen(bundle.getInt("getMemberId")+"",bundle.getString("getProjId")+"");
            }

            @Override
            protected void onPostExecute(MsgBean msgBean) {
                progressDialog.dismissProgressDialog();
                if(msgBean.getResult().equals("1")){
                    Bundle bundle = getIntent().getExtras();
                    Log.e("bundle",bundle.getInt("getMemberId")+"");
                    Log.e("bundle",bundle.getString("getProjId")+"");
                    Log.e("bundle",bundle.getString("getImgUrl")+"");
                    Intent intent = new Intent(LiveResultActivity.this, Bulider.startCls);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LiveResultActivity.this,msgBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }


    }
	@Override
	protected void onStop() {

		sndPool.stop(1);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		sndPool.release();
		super.onDestroy();
	}
}