package com.simpleflashlight;

//import com.felight.flashlight.R;

import android.app.Activity;
import android.app.AlertDialog;
//import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class FlashActivity extends Activity {

	ImageButton btnSwitch;
	private boolean hasFlash;
	private Camera camera;
	private boolean isLighOn = false;

	protected void onStop() {
		super.onStop();

		if (camera != null) {
			camera.release();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_layout);
		btnSwitch = (ImageButton) findViewById(R.id.btnSwitch);
		hasFlash = getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		if (!hasFlash) {
			AlertDialog alert = new AlertDialog.Builder(FlashActivity.this)
					.create();
			alert.setTitle("Error");
			alert.setMessage("Sorry, your device doesn't support flash light!");
			alert.show();
			return;
		}
		try {
			camera = Camera.open();
			final Parameters p = camera.getParameters();
			btnSwitch.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (isLighOn) {

						Log.i("info", "torch is turn off!");
						btnSwitch.setImageResource(R.drawable.btn_switch_off);
						p.setFlashMode(Parameters.FLASH_MODE_OFF);
						camera.setParameters(p);
						camera.stopPreview();
						isLighOn = false;
					} else {

						Log.i("info", "torch is turn on!");
						p.setFlashMode(Parameters.FLASH_MODE_TORCH);
						btnSwitch.setImageResource(R.drawable.btn_switch_on);
						camera.setParameters(p);
						camera.startPreview();
						isLighOn = true;

					}
				}
			});
		} catch (Exception e) {
			Toast.makeText(this,
					"Your device doesnot have FlashLight capability",
					Toast.LENGTH_LONG).show();
		}
	}
}
