
import 'dart:async';

import 'package:flutter/services.dart';

class FlutterPluginGood {
  static const MethodChannel _channel =
      const MethodChannel('flutter_plugin_good');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
