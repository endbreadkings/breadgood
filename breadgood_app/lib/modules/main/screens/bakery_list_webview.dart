import 'dart:convert';

import 'package:breadgood_app/utils/ui/bottomNavigation.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'package:breadgood_app/constant/api_path.dart' as PATH;
// import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';
import 'package:get/get.dart';
import 'package:webview_flutter/webview_flutter.dart';

final Set<JavascriptChannel> jsChannels = [
  JavascriptChannel(
      name: 'MoveToDetail',
      onMessageReceived: (JavascriptMessage message) {
        Map<String, dynamic> jsonMessage = jsonDecode(message.message);
        Get.toNamed('/details', arguments: {
          'userId': jsonMessage['userId'],
          'bakeryId': jsonMessage['bakeryId'],
        });
      }),
  JavascriptChannel(
      name: 'MoveToRegister',
      onMessageReceived: (JavascriptMessage message) {
        Get.toNamed('/register_bakery/search_bakery_page');
      }),
].toSet();

class BakeryListWebview extends StatefulWidget {
  _BakeryListWebviewState createState() => _BakeryListWebviewState();
}

class _BakeryListWebviewState extends State<BakeryListWebview> {
  @override
  Widget build(BuildContext context) {
    return WebView(
      initialUrl: "${PATH.URL}/pages/bakery/list",
      javascriptMode: JavascriptMode.unrestricted,
      javascriptChannels: jsChannels,
    );
  }
}
