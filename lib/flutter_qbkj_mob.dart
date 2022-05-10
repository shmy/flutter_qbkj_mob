
import 'dart:async';

import 'package:flutter/services.dart';

class FlutterQbkjMob {
  static const MethodChannel _channel = MethodChannel('flutter_qbkj_mob');

  static Future<bool> init() async {
    return await _channel.invokeMethod('init', '1330509223779385377');
  }
  static Future<void> loadSplash() async {
    await _channel.invokeMethod('loadSplash',
        '1330512946933022784');
  }
  static Future<void> loadInteraction() async {
    await _channel.invokeMethod('loadInteraction',
        '1330513058157576258');
  }
  static Future<void> loadRewardVideo() async {
    await _channel.invokeMethod('loadRewardVideo',
        ['1330515047692124225', 'VERTICAL', '传入userId或者设备唯一标识', '服务器回调额外信息']);
  }
}
