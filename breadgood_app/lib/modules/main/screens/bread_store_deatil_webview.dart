import 'package:breadgood_app/modules/register_bakery/screens/already_registered_bakery.dart';
import 'package:breadgood_app/utils/ui/bottomNavigation.dart';
import 'package:flutter/material.dart';
import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:get/get.dart';
import 'package:webview_flutter/webview_flutter.dart';

final Set<JavascriptChannel> jsChannels = [
  JavascriptChannel(
      name: 'MoveToReview',
      onMessageReceived: (JavascriptMessage message) {
        Get.toNamed("/register_review/register_reivew_page", arguments: Get.arguments['bakeryId']);
      }),
].toSet();

class BreadStoreDetailWebView extends StatefulWidget {
  @override
  _BreadStoreDetailWebViewState createState() =>
      _BreadStoreDetailWebViewState();
}

class _BreadStoreDetailWebViewState extends State<BreadStoreDetailWebView> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AlreadyRegisteredBakeryAppbar(bakeryId: Get.arguments['bakeryId']),
      body: WebView(
        initialUrl:
            "${api_path.url}/pages/bakery/detail?userId=${Get.arguments['userId']}&bakeryId=${Get.arguments['bakeryId']}",
        javascriptMode: JavascriptMode.unrestricted,
        javascriptChannels: jsChannels,
      ),
    );
  }
}
