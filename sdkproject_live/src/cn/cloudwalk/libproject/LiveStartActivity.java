/**
 * Project Name:cwFaceForDev3
 * File Name:LiveStartActivity.java
 * Package Name:cn.cloudwalk.dev.mobilebank
 * Date:2016-5-16上午9:17:24
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */

package cn.cloudwalk.libproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.cloudwalk.libproject.callback.NoDoubleClickListener;
import cn.cloudwalk.libproject.util.LogUtils;

/**
 * ClassName: LiveStartActivity <br/>
 * Description: <br/>
 * date: 2016-5-16 上午9:17:24 <br/>
 *
 * @author 284891377
 * @since JDK 1.7
 */
public class LiveStartActivity extends TemplatedActivity {
	private final String TAG = LogUtils.makeLogTag("LiveActivity");

	Button mBt_startdect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cloudwalk_layout_facedect_start);
		setTitle(R.string.cloudwalk_live_title);
		mBt_startdect = (Button) findViewById(R.id.bt_startdect);
		mBt_startdect.setOnClickListener(new NoDoubleClickListener() {

			@Override
			public void onNoDoubleClick(View v) {
				if (v.getId() == R.id.bt_startdect) {
					Bundle bundle = getIntent().getExtras();
					Log.e("bundle",bundle+"");
                    Log.e("bundle",bundle.getInt("getMemberId")+"");
                    Log.e("bundle",bundle.getString("getProjId")+"");
                    Log.e("bundle",bundle.getString("getImgUrl")+"");
					Intent intent = new Intent(LiveStartActivity.this, LiveActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					finish();

				}

			}
		});


	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onStop() {

		super.onStop();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
}