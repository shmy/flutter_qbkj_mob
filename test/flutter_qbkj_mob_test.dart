import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_qbkj_mob/flutter_qbkj_mob.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_qbkj_mob');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await FlutterQbkjMob.platformVersion, '42');
  });
}
