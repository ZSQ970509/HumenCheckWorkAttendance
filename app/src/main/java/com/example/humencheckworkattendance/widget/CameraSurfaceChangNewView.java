package com.example.humencheckworkattendance.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.humencheckworkattendance.ui.activity.ShowPhotoActivity;
import com.example.humencheckworkattendance.utils.ImageUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class CameraSurfaceChangNewView extends SurfaceView
        implements SurfaceHolder.Callback, Camera.PreviewCallback, Camera.AutoFocusCallback, SensorEventListener {

    private static final String TAG = "CameraSurfaceView";
    // public static final String PICTURE_FILE = "tempface.jpg";

    private Context mContext;
    private SurfaceHolder holder;
    private Camera mCamera;

    private int mScreenWidth;
    private int mScreenHeight;

    private Handler mAutoFocusHandler;

    private boolean mPreviewing = true;
    private boolean mAutoFocus = true;
    private boolean mSurfaceCreated = false;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    ImageView imageViewCameraBold;
    //	private int getRequestedOrientation;
    private int mPhotoWidth = 640;//保存的照片 宽
    private int mPhotoHeight = 480;//保存的照片 高
    private int mSurfaceViewWidth;
    private int mSurfaceViewHeight;
    boolean isBack = true;
    protected boolean mScreenOrientation = false;//屏幕的方向 横屏true 竖屏false
    public void setImg(ImageView imageViewCameraBold) {
        this.imageViewCameraBold = imageViewCameraBold;
    }

    public CameraSurfaceChangNewView(Context context) {
        this(context, null);
    }

    public CameraSurfaceChangNewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceChangNewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getScreenMetrix(context);
        initView();
    }

    private void getScreenMetrix(Context context) {
        WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        WM.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mScreenHeight = outMetrics.heightPixels;
    }

    private void initView() {
        holder = getHolder();// ���surfaceHolder����
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// ��������

        mAutoFocusHandler = new Handler();
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        boolean sensor = mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (mCamera == null) {
            mSurfaceCreated = true;
            int CammeraIndex = FindBackCamera(); // Ĭ�ϵ��ú�������ͷ
            if (CammeraIndex == -1) {
                CammeraIndex = FindFrontCamera();
                isBack = false;
            }

            mCamera = Camera.open(CammeraIndex); // �������

            try {
                mCamera.setPreviewDisplay(holder);// ����ͷ������ʾ��Surface��
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // �����ֻ���Ҫ�ȵ���
        setCameraDisplayOrientation(getContext(), FindBackCamera(), mCamera);
        mSurfaceViewHeight = this.getHeight();
        mSurfaceViewWidth = this.getWidth();
        // ���ò�������ʼԤ��
        setCameraParams(mCamera, mScreenWidth, mScreenHeight);
        mCamera.startPreview();
        setAutoFocus(true);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();// ֹͣԤ��
        mCamera.release();// �ͷ������Դ
        mCamera = null;
        holder = null;
        mSurfaceCreated = false;
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAutoFocus(boolean success, Camera Camera) {
        if (success) {
        }
    }

    // ����˲�����
    private Camera.ShutterCallback shutter = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
        }
    };

    // ���û��ѹ������ͼƬ����
    private Camera.PictureCallback raw = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera Camera) {

        }
    };

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // ���ͼƬ�Ŀ��
        int width = bm.getWidth();
        int height = bm.getHeight();
        // �������ű���
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // ȡ����Ҫ���ŵ�matrix����
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // �õ��µ�ͼƬ
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    // ����jpegͼƬ�ص����ݶ���
    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera Camera) {
            BufferedOutputStream bos = null;
            File file = null;
            Bitmap bm = null;
            try {
                // ���ͼƬ
                bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                // Matrix m = new Matrix();
                // m.setRotate(-90, (float) bm.getWidth() / 2, (float)
                // bm.getHeight() / 2);
                /// bm = rotateBitmap(Bitmap.createBitmap(bm, 0, 0,
                // bm.getWidth(), bm.getHeight(), m, true), 180);

                // int tempheight =mScreenHeight/bm.getHeight();

                // Bitmap tempBM =ImageUtil.getRawResource(mContext,
                // R.drawable.degree_scale);
                // ImageUtil.zoomImage(tempBM, mScreenHeight, mScreenWidth);
                // int tempHeightScle=tempBM.getHeight()/bm.getHeight();
                //
                //
                // tempBM=ImageUtil.zoomImage(tempBM,
                // bm.getWidth(),tempBM.getHeight()*tempHeightScle);
                //
                // bm = ImageUtil.toConformBitmap(rotateBitmap(bm, 90), tempBM);

                Camera.CameraInfo info = new Camera.CameraInfo();
                int rotation = ((Activity) mContext).getWindowManager().getDefaultDisplay().getRotation();
                int degrees = 0;

                switch (rotation) {
                    case Surface.ROTATION_0:
                        degrees = 0;
                        break;
                    case Surface.ROTATION_90:
                        degrees = 90;
                        break;
                    case Surface.ROTATION_180:
                        degrees = 180;
                        break;
                    case Surface.ROTATION_270:
                        degrees = 270;
                        break;
                }
//                Matrix m = new Matrix();
//                if (android.os.Build.MODEL.equals("C68")) {
//                    Log.d("TAG", android.os.Build.MODEL);
//                    m.setRotate(90, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//                } else {
//                    Log.e("isBack", isBack+"");
//                    if(isBack) {
//                        m.setRotate(90, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//                    }else{
//                        m.setRotate(270, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//                        isBack = true;
//                    }
//                }
                // back-facing
                int result = (info.orientation - degrees + 360) % 360;
                if (result == 0) {
                    if(!isBack&&!mScreenOrientation){//竖屏且使用前置摄像头
                        bm = rotateBitmap(bm, 270);
                    }else {
                        bm = rotateBitmap(bm, 90);
                    }
                } else if (result == 90) {
                    bm = rotateBitmap(bm, 180);
                } else if (result == 180) {
                    if(!isBack&&!mScreenOrientation){//竖屏且使用前置摄像头
                        bm = rotateBitmap(bm, 90);
                    }else {
                        bm = rotateBitmap(bm, 270);
                    }
                } else {
                    bm = rotateBitmap(bm, 0);
                }
                if(!isBack)//初始化 默认为后置摄像头
                    isBack = true;

//				Bitmap bm1 = ImageUtil.TailorBmpForLeftAndTop(bm, mScreenWidth, imageViewCameraBold.getLeft()+ UIUtils.layX2sp(120), imageViewCameraBold.getTop()+UIUtils.layY2sp(285),
//						imageViewCameraBold.getWidth(), imageViewCameraBold.getHeight());


                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
                    String publicFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                            .append(File.separator).append("ivm").append(File.separator).append(sdf.format(new Date()))
                            .toString();

                    file = new File(Environment.getExternalStorageDirectory() + "/surfingscene/shotDir/uploadHead.jpg");
                    //File file = new File(((Activity) mContext).getIntent().getStringExtra("IMGFileUrl"));

                    File rootDirectory = new File(ImageUtil.shotScreenDir);
                    if (rootDirectory.exists()) {
                        if (rootDirectory.isDirectory())
                            rootDirectory.delete();
                    }
                    rootDirectory.mkdirs();

                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    //��Ҫ��ͼƬѹ����800*600
                    bm = zoomImg(bm,mPhotoWidth,mPhotoHeight);
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);// ��ͼƬѹ������

                } else {
                    Toast.makeText(mContext, "û�м�⵽�ڴ濨", Toast.LENGTH_SHORT).show();
                }
                Intent intent = ((Activity) mContext).getIntent();
                intent.setClass(((Activity) mContext), ShowPhotoActivity.class);
                intent.putExtra("imgSrc",Environment.getExternalStorageDirectory() + "/surfingscene/shotDir/uploadHead.jpg");
                intent.putExtra("isIdCard",false);
                ((Activity) mContext).startActivityForResult(intent, 0X111);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null) {
                        bos.flush();// ���
                        bos.close();// �ر�
                    }
                    if (bm != null) {
                        bm.recycle();// ����bitmap�ռ�
                    }

                    mCamera.stopPreview();// �ر�Ԥ��
                    // mCamera.startPreview();// ����Ԥ��
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    public Camera getCamera() {
        return mCamera;
    }

    public void takePicture() {
        // ���ò���,������\
//		this.getRequestedOrientation = getRequestedOrientation;
        setCameraParams(mCamera, mScreenWidth, mScreenHeight);
        // ������camera.takePiture������camera�ر���Ԥ������ʱ��Ҫ����startPreview()�����¿���Ԥ��
        mSurfaceViewHeight = this.getHeight();
        mSurfaceViewWidth = this.getWidth();
        mCamera.takePicture(null, null, jpeg);
    }
    public void takePicture(boolean screenOrientation) {
        mScreenOrientation = screenOrientation;
        setCameraParams(mCamera, mScreenWidth, mScreenHeight);
        mSurfaceViewHeight = this.getHeight();
        mSurfaceViewWidth = this.getWidth();
        mCamera.takePicture(null, null, jpeg);
    }
    private void setCameraParams(Camera camera, int width, int height) {
        try {
            mPreviewing = true;

            int PreviewWidth = 0;
            int PreviewHeight = 0;
            Log.i(TAG, "setCameraParams  width=" + width + "  height=" + height);
            Camera.Parameters parameters = mCamera.getParameters();

            // ��ȡ����ͷ֧�ֵ�PictureSize�б�
            List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
            for (Camera.Size size : pictureSizeList) {
                Log.i(TAG, "pictureSizeList size.width=" + size.width + "  size.height=" + size.height);
            }
            // ��ȡ����ͷ֧�ֵ�PreviewSize�б�
            List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();
            for (Camera.Size size : previewSizeList) {
                Log.i(TAG, "previewSizeList size.width=" + size.width + "  size.height=" + size.height);
            }

            Camera.Size preSize = MyCamPara.getInstance().getPreviewSize(previewSizeList, 2500);
            Camera.Size picSize = MyCamPara.getInstance().getPictureSize(pictureSizeList, 3100);
            Log.i(TAG, "previewSize and pictureSize" + picSize.width + "   " + picSize.height);

            // if (null == picSize) {
            // Log.i(TAG, "null == picSize");
            // picSize = parameters.getPictureSize();
            // }

            PreviewWidth = preSize.width;
            PreviewHeight = preSize.height;
            parameters.setPreviewSize(preSize.width, preSize.height); // �����������Ĵ�С
            parameters.setPictureSize(picSize.width, picSize.height);// �����ĳ�������Ļ��С
            //parameters.setPictureSize(1500, 1500);// �����ĳ�������Ļ��С
            Log.e(TAG, "pre width=" + preSize.width + "  height=" + preSize.height);
            Log.e(TAG, "pic width=" + picSize.width + "  height=" + picSize.height);

            // parameters.setPictureFormat(PixelFormat.JPEG);// ������Ƭ����ĸ�ʽ
            //
            mCamera.setParameters(parameters);// ����������� ��������ͷ

            parameters.setJpegQuality(100); // ������Ƭ����
            if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            } else if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE); // �����Խ�ģʽ
            }
            int CammeraIndex = FindBackCamera(); // Ĭ�ϵ��ú�������ͷ
            if (CammeraIndex == -1) {
                CammeraIndex = FindFrontCamera();
            }

            mCamera.setParameters(parameters);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int FindFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // ��������ͷ�ķ�λ��Ŀǰ�ж���ֵ�����ֱ�ΪCAMERA_FACING_FRONTǰ�ú�CAMERA_FACING_BACK����
                return camIdx;
            }
        }
        return -1;
    }

    private int FindBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // ��������ͷ�ķ�λ��Ŀǰ�ж���ֵ�����ֱ�ΪCAMERA_FACING_FRONTǰ�ú�CAMERA_FACING_BACK����
                return camIdx;
            }
        }
        return -1;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        // TODO Auto-generated method stub

    }

    /**
     * @param bmp    Ҫ��ת��ͼƬ
     * @param degree ͼƬ��ת�ĽǶȣ���ֵΪ��ʱ����ת����ֵΪ˳ʱ����ת
     * @return ��ת�õ�ͼƬ
     */
    public static Bitmap rotateBitmap(Bitmap bmp, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // �˴�bitmapĬ��ΪRGBA_8888
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    public static void setCameraDisplayOrientation(Context content, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = ((Activity) content).getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        camera.setDisplayOrientation(result);
    }

    public void setTorch(boolean newSetting) {
        Camera.Parameters parameters = mCamera.getParameters();
        doSetTorch(parameters, newSetting, false);
        mCamera.setParameters(parameters);
        // boolean currentSetting =
        // prefs.getBoolean(PreferencesActivity.KEY_FRONT_LIGHT, false);
        boolean currentSetting = false;
        if (currentSetting != newSetting) {
            // SharedPreferences.Editor editor = prefs.edit();
            // editor.putBoolean(PreferencesActivity.KEY_FRONT_LIGHT,
            // newSetting);
            // editor.commit();
        }
    }

    private void doSetTorch(Camera.Parameters parameters, boolean newSetting, boolean safeMode) {
        String flashMode;
        if (newSetting) {
            flashMode = findSettableValue(parameters.getSupportedFlashModes(), Camera.Parameters.FLASH_MODE_TORCH,
                    Camera.Parameters.FLASH_MODE_ON);
        } else {
            flashMode = findSettableValue(parameters.getSupportedFlashModes(), Camera.Parameters.FLASH_MODE_OFF);
        }
        if (flashMode != null) {
            parameters.setFlashMode(flashMode);
        }

		/*
         * SharedPreferences prefs =
		 * PreferenceManager.getDefaultSharedPreferences(context); if
		 * (!prefs.getBoolean(PreferencesActivity.KEY_DISABLE_EXPOSURE, false))
		 * { if (!safeMode) { ExposureInterface exposure = new
		 * ExposureManager().build(); exposure.setExposure(parameters,
		 * newSetting); } }
		 */
    }

    private static String findSettableValue(Collection<String> supportedValues, String... desiredValues) {
        String result = null;
        if (supportedValues != null) {
            for (String desiredValue : desiredValues) {
                if (supportedValues.contains(desiredValue)) {
                    result = desiredValue;
                    break;
                }
            }
        }
        return result;
    }

    public void safeAutoFocus() {
        try {
            mCamera.autoFocus(autoFocusCB);

        } catch (RuntimeException re) {
            // Horrible hack to deal with autofocus errors on Sony devices
            // See https://github.com/dm77/barcodescanner/issues/7 for example
            scheduleAutoFocus(); // wait 1 sec and then do check again

        }
    }

    private void scheduleAutoFocus() {
        mAutoFocusHandler.postDelayed(doAutoFocus, 1000);

    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {

            if (mPreviewing && mAutoFocus && mSurfaceCreated) {
                safeAutoFocus();
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {

            // scheduleAutoFocus();
        }
    };

    public void setAutoFocus(boolean state) {
        if (mPreviewing) {
            if (state == mAutoFocus) {
                return;
            }
            mAutoFocus = state;
            if (mAutoFocus) {
                if (mSurfaceCreated) { // check if surface created before using
                    // autofocus

                    safeAutoFocus();
                } else {
                    scheduleAutoFocus(); // wait 1 sec and then do check again
                }
            } else {
                mCamera.cancelAutoFocus();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    private boolean mIsAuto = false;

    private boolean mInitialized = false;
    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        mAutoFocus = true;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            mInitialized = true;
        }

        float deltaX = Math.abs(mLastX - x);
        float deltaY = Math.abs(mLastY - y);
        float deltaZ = Math.abs(mLastZ - z);

        if (mAutoFocus && (deltaX > .5 || deltaY > .5 || deltaZ > .5)) {
            mAutoFocus = false;
            mIsAuto = true;
        } else {
            mAutoFocus = true;

            if (mIsAuto) {
                scheduleAutoFocus();
            }
            mIsAuto = false;

        }
        mLastX = x;
        mLastY = y;
        mLastZ = z;
    }
    public int changeCamera(int cameraPosition , FrameLayout fl, LinearLayout.LayoutParams flParams) {

        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for(int i = 0; i < cameraCount; i++   ) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            Log.e("cameraPosition", cameraPosition+"");
            if(cameraPosition == 1) {
                //现在是后置，变更为前置
                if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(i);//打开当前选中的摄像头

                    try {
                        mCamera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setCameraDisplayOrientation(mContext, cameraPosition, mCamera);
                    setCameraParams(mCamera, mScreenWidth, mScreenHeight);
                    mCamera.startPreview();//开始预览
                    isBack = false;
                    return  0;
                }
            } else {
                //现在是前置， 变更为后置

                if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(i);//打开当前选中的摄像头

                    try {
                        mCamera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    setCameraDisplayOrientation(mContext, cameraPosition, mCamera);
                    setCameraParams(mCamera, mScreenWidth, mScreenHeight);
                    mCamera.startPreview();//开始预览
                    isBack = true;
                    return  1;
                }
            }

        }
        return 1;
    }
    /**
     * 设置照片的分辨率
     * @param height
     * @param width
     */
    public void setPhotoSize(int height,int width){
        mPhotoHeight = height;
        mPhotoWidth = width;
    }
}
