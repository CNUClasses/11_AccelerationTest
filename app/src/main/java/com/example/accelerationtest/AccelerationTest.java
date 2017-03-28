package com.example.accelerationtest;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;

//************** implementing sensoreventlistener, need to register for this
public class AccelerationTest extends Activity implements SensorEventListener {

  //**************  
  private SensorManager sensorManager;
  
  private TextView x;
  private TextView y;
  private TextView z;
  private TextView x_avg;
  private TextView y_avg;
  private TextView z_avg;

  private Queue<Float> vals_x;
  private Queue<Float> vals_y;
  private Queue<Float> vals_z;

  private float xtotal;
  private float ytotal;
  private float ztotal;

  private static final int NUMBER_TO_AVERAGE = 100;
  
/** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
	
	 //**************  No title and fill the screen, useful bit of code for graphics and games
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_acceleration_test);

	x = (TextView) findViewById(R.id.x);
	y = (TextView) findViewById(R.id.y);
	z = (TextView) findViewById(R.id.z);
	x_avg = (TextView) findViewById(R.id.x_avg);
	y_avg = (TextView) findViewById(R.id.y_avg);
	z_avg = (TextView) findViewById(R.id.z_avg);

	 //**************  
	sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

	vals_x = new LinkedList<Float>();
	vals_y = new LinkedList<Float>();
	vals_z = new LinkedList<Float>();

	xtotal = (float) 0.0;
	ytotal = (float) 0.0;
	ztotal = (float) 0.0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	    float[] values = event.values;

	    // get movements
	    float xx = values[0];
	    float yy = values[1];
	    float zz = values[2];

	    // display instant reads
	    x.setText(Float.toString(xx));
	    y.setText(Float.toString(yy));
	    z.setText(Float.toString(zz));

	    xtotal = rollingTotal(xx, vals_x, xtotal);
	    ytotal = rollingTotal(yy, vals_y, ytotal);
	    ztotal = rollingTotal(zz, vals_z, ztotal);

	    // display them
	    x_avg.setText(Float.toString(xtotal / NUMBER_TO_AVERAGE));
	    y_avg.setText(Float.toString(ytotal / NUMBER_TO_AVERAGE));
	    z_avg.setText(Float.toString(ztotal / NUMBER_TO_AVERAGE));
	}
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	private float rollingTotal(float f, Queue<Float> vals, float total) {
	if (vals.size() == NUMBER_TO_AVERAGE) {
	    total -= vals.poll();
	}
	vals.add(f);
	total += f;
	return total;
    }


    @Override
    protected void onResume() {
	super.onResume();
	// register this class as a listener for the orientation and
	// accelerometer sensors
	sensorManager.registerListener(this, 
		sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
		SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
	// unregister listener
	super.onPause();
	sensorManager.unregisterListener(this);
    }
    
 
} 