package com.example.flutter_plugin_good;


import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterPluginGoodPlugin */
public class FlutterPluginGoodPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Camera camera;
  private Parameters parameters;
  public boolean hasClosed = true; // 定义开关状态，状态为false，打开状态，状态为true，关闭状态

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_plugin_good");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      Log.d("android信息打印", String.valueOf(hasClosed));
      if (hasClosed) {
        camera = Camera.open();
        Camera.Parameters mParameters;
        mParameters = camera.getParameters();
        mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(mParameters);
        hasClosed = false;
      } else {
        Camera.Parameters mParameters;
        mParameters = camera.getParameters();
        mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(mParameters);
        camera.release();
        hasClosed = true;
      }
      result.success("Android " + "myway");
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
