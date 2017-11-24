package com.example.humencheckworkattendance.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

	public final static String shotScreenDir = Environment.getExternalStorageDirectory() + "/surfingscene/shotDir/";
	public final static String RangePhoto = "RangePhoto.jpg";
	public final static String MobilePhoto = "MobilePhoto.jpg";
	public final static String MobilePhoto2 = "MobilePhoto2.jpg";
	public final static String MobilePhoto3 = "MobilePhoto3.jpg";
	public final static String MobilePhoto4 = "MobilePhoto4.jpg";
	public final static String QualityPhoto = "QualityPhoto.jpg";
	public final static String QualityPhoto2 = "QualityPhoto2.jpg";
	public final static String QualityPhoto3 = "QualityPhoto3.jpg";
	public final static String QualityPhoto4 = "QualityPhoto4.jpg";
	/**�����Ͻ�(0,0) ��ʼ�ü�
	 * @param bitmap �������ɵ�λͼ
	 * @param PreViewCameraWidth ����Ԥ������(��Ļ���)������4:3�����������Ԥ����߶�
	 * @param Top Ԥ���ؼ����븸view�ĸ߶�
	 * @param Left Ԥ���ؼ����븸view�Ŀ��
	 * @param bmWidth �����
	 * @param bmHeight ���߶�
	 * @return
	 */
	public static void DisplayImage(ImageLoader imageLoader,String url,ImageView imageView,DisplayImageOptions options){
		imageLoader.displayImage(url, imageView,options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				((ImageView) arg1).setImageBitmap(arg2);
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public  static Bitmap TailorBmpForLeftAndTop(Bitmap bitmap,int PreViewCameraWidth,float X,float Y,int bmWidth,int bmHeight){

		double picWidth=bitmap.getWidth();//��ʵͼƬ�Ŀ�
		double picHeight=bitmap.getHeight();//��ʵͼƬ�ĸ�
		
		//�õ���ʵͼƬ����ĻԤ���ı���,��ʱ��������������Ŵ���ͼƬ
		double scaleX= (picWidth/PreViewCameraWidth);
		double scaleY =(picHeight /(PreViewCameraWidth*1.3333333));

		
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap,  (int)(X*scaleY), (int)(Y*scaleY),  (int)(bmWidth*scaleX),(int)(bmHeight*scaleY),null , false);

		return resizeBmp;
	}
	public static Bitmap imageZoom(Bitmap bitMap) {
		// ͼƬ�������ռ� ��λ��KB
		double maxSize = 150.00;
		// ��bitmap���������У�����bitmap�Ĵ�С����ʵ�ʶ�ȡ��ԭ�ļ�Ҫ��
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// ���ֽڻ���KB
		double mid = b.length / 1024;
		// �ж�bitmapռ�ÿռ��Ƿ�����������ռ� ���������ѹ�� С����ѹ��
		if (mid > maxSize) {
			// ��ȡbitmap��С ����������С�Ķ��ٱ�
			double i = mid / maxSize;
			// ��ʼѹ�� �˴��õ�ƽ���� ������͸߶�ѹ������Ӧ��ƽ������
			// ��1.���̶ֿȺ͸߶Ⱥ�ԭbitmap����һ�£�ѹ����Ҳ�ﵽ������Сռ�ÿռ�Ĵ�С��
			bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), bitMap.getHeight() / Math.sqrt(i));
		
			imageZoom(bitMap);
		}
		return bitMap;
	}
	

	public static Bitmap TailorBmp(Bitmap bitmap,double viewWidth,double viewHeight,int diameter){
		

		double picWidth=bitmap.getWidth();//��ʵͼƬ�Ŀ�
		double picHeight=bitmap.getHeight();//��ʵͼƬ�ĸ�

		//�õ�ԭͼ����ĻԤ���ı���,��ʱ��������������Ŵ�������Ԥ��ͼ��λ��
		double scaleX= (picWidth/viewWidth);
		double scaleY =(picHeight /viewHeight);
		
		//����������Ԥ���� ģ��ͼ����ʼλ��
		double scaleWidth= (viewWidth/2-diameter/2);//�����������ʵͼƬ�Ŀ�Ҫ�����￪ʼ����
		double scaleHeight=  (viewHeight/2-diameter/2);//�����������ʵͼƬ�ĸ�Ҫ�����￪ʼ����

		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, (int)(scaleX*scaleWidth), (int)(scaleY*scaleHeight), (int)(diameter*scaleX),
				 (int)(diameter*scaleX),null , false);
		return resizeBmp;
	}
	
	
	
	

	/***
	 * ͼƬ�����ŷ���
	 *
	 * @param bgimage
	 *            ��ԴͼƬ��Դ
	 * @param newWidth
	 *            �����ź���
	 * @param newHeight
	 *            �����ź�߶�
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
		// ��ȡ���ͼƬ�Ŀ�͸�
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();
		// ������������
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// ����ͼƬ����
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}

	public static Bitmap scaleImage(String srcPath, float width, float height) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = width;
		float ww = height;
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0) {
			be = 1;
		}
		newOpts.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;
	}

	/**
	 * ͼƬת��string
	 */
	public static String convertIconToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		byte[] appicon = baos.toByteArray();
		return Base64.encodeToString(appicon, Base64.DEFAULT);
	}

	/**
	 * stringת��bitmap
	 */
	public static Bitmap convertStringToIcon(String st) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	public static Bitmap convertViewToBitmap(View view) {

		view.setDrawingCacheEnabled(true);

		// this is the important code :)
		// Without it the view will have a dimension of 0,0 and the bitmap will
		// be null
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

		view.buildDrawingCache(true);
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false); // clear drawing cache

		return bitmap;

	}

	/**
	 * ���ر���ͼƬ
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		FileInputStream fis=null;
		try {
			 fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
		if (background == null) {
			return null;
		}

		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		// int fgWidth = foreground.getWidth();
		// int fgHeight = foreground.getHeight();
		// create the new blank bitmap ����һ���µĺ�SRC���ȿ��һ����λͼ
		Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
		Canvas cv = new Canvas(newbmp);
		// draw bg into
		cv.drawBitmap(background, 0, 0, null);// �� 0��0���꿪ʼ����bg
		// draw fg into
		cv.drawBitmap(foreground, 0, 0, null);// �� 0��0���꿪ʼ����fg �����Դ�����λ�û���
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		// store
		cv.restore();// �洢
		return newbmp;
	}

	public static Bitmap getRawResource(Context context, int RawId) {
		InputStream is = context.getResources().openRawResource(RawId);

		Bitmap bgimage = BitmapFactory.decodeStream(is);

		int height3 = context.getResources().getDisplayMetrics().widthPixels;

		// ��ȡ���ͼƬ�Ŀ�͸�
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();
		// ������������
		float scaleWidth = (float) height3 / width;
		float scaleHeight = 1;
		// ����ͼƬ����
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);

		return bitmap;
	}

	// ��ˮӡ Ҳ���Լ�����
	public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark, String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		// ��Ҫ����ͼƬ̫����ɵ��ڴ泬��������,�����ҵ�ͼƬ��С���Բ�д��Ӧ������
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ����λͼ
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// �� 0��0���꿪ʼ����src
		Paint paint = new Paint();
		// ����ͼƬ
		if (watermark != null) {
			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			paint.setAlpha(50);
			cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// ��src�����½ǻ���ˮӡ
		}
		// ��������
		if (title != null) {
			String familyName = "����";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			// �������Զ����е�
			StaticLayout layout = new StaticLayout(title, textPaint, w, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
			layout.draw(cv);
			// ���־ͼ����Ͻ�����
			cv.drawText(title, 0, 40, paint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		cv.restore();// �洢
		return newb;
	}

	/**
	 * ��ͼƬ������ֵ����Ͻ�
	 * 
	 * @param context
	 * @param bitmap
	 * @param text
	 * @return
	 */
	public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text, int size, int color,
			int paddingLeft, int paddingTop) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(dp2px(context, size));
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return drawTextToBitmap(context, bitmap, text, paint, bounds, dp2px(context, paddingLeft),
				dp2px(context, paddingTop) + bounds.height());
	}

	
	/**
	 * ��ͼƬ������ֵ����Ͻ� ���ƶ���
	 * 
	 * @param context
	 * @param bitmap
	 * @param text
	 * @return
	 */
	
//	public static Bitmap drawTextToLeftTopMultiLine(Context context, Bitmap bitmap, String[] text, int size, int color,
//			int paddingLeft, int paddingTop) {
//		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		paint.setColor(color);
//		paint.setTextSize(dp2px(context, size));
//		Rect bounds = new Rect();
//		paint.getTextBounds(text, 0, text.length(), bounds);
//		return drawTextToBitmap(context, bitmap, text, paint, bounds, dp2px(context, paddingLeft),
//				dp2px(context, paddingTop) + bounds.height());
//	}
//	
	// ͼƬ�ϻ�������
	private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text, Paint paint, Rect bounds,
			int paddingLeft, int paddingTop) {
		Config bitmapConfig = bitmap.getConfig();

		paint.setDither(true); // ��ȡ��������ͼ�����
		paint.setFilterBitmap(true);// ����һЩ
		if (bitmapConfig == null) {
			bitmapConfig = Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);
		Canvas canvas = new Canvas(bitmap);

		canvas.drawText(text, paddingLeft, paddingTop, paint);
		return bitmap;
	}

	/**
	 * dipתpix
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}


	public static  void saveBitmap(Bitmap bitmap,String FilePath) throws IOException
    {
        File file = new File(FilePath);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(CompressFormat.PNG, 100, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
	public static String convertIconToStringJPGE(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		byte[] appicon = baos.toByteArray();
		return Base64.encodeToString(appicon, Base64.DEFAULT);
	}

}
