import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_qbkj_mob/flutter_qbkj_mob.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _inited = false;
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    final bool isSuccess = await FlutterQbkjMob.init();
    setState(() {
      _inited = isSuccess;
    });
    // FlutterQbkjMob.loadSplash();
  }
  void onFeedViewCreated(int id) {
    print(id);
    var feed_channel = MethodChannel('flutter_qbkj_mob/feedview_$id');
    feed_channel.setMethodCallHandler((call) async {
      print(call.method);
    });
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: ListView(
          children: [
            MaterialButton(onPressed: () {
              FlutterQbkjMob.loadInteraction();

            }, child: Text('loadInteraction'),),
            MaterialButton(onPressed: () {
              FlutterQbkjMob.loadSplash();

            }, child: Text('loadSplash'),),
            MaterialButton(onPressed: () {
              FlutterQbkjMob.loadRewardVideo();

            }, child: Text('loadRewardVideo'),),
            Text(_inited.toString()),
            if (_inited)
              // 这里不显示
            Container(
              width: 350,
              height: 200,
              child: AndroidView(
                viewType: 'flutter_qbkj_mob/feedview',
                creationParams: {
                  "codeId": "1330513366195650582",
                  "width": 350,
                  "height": 200,
                },
                creationParamsCodec: const StandardMessageCodec(),
                onPlatformViewCreated: this.onFeedViewCreated,
                // onPlatformViewCreated: this.onFeedViewCreated,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
