import 'package:flutter/material.dart';

import 'package:breadgood_app/constant/api_path.dart' as PATH;
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';

// ignore: prefer_collection_literals
final Set<JavascriptChannel> jsChannels = [
  JavascriptChannel(
      name: 'Print',
      onMessageReceived: (JavascriptMessage message) {
        print(message.message);
      }),
].toSet();


class TermsWebviewAppbar extends DefaultAppBar {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: IconButton(
        icon: Image.asset('asset/images/Vector.png'),
        onPressed: () => Navigator.pushReplacementNamed(context, '/signup/policy'),
      ),
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }
}

class TermsWebView extends StatefulWidget {

  const TermsWebView({Key key}) : super(key: key);

  @override
  _TermsWebViewState createState() => _TermsWebViewState();
}

class _TermsWebViewState extends State<TermsWebView> {
  @override
  Widget build(BuildContext context) {
    return WebviewScaffold(
        url: "${PATH.URL}${Get.arguments}",
        javascriptChannels: jsChannels,
        scrollBar: true,
        appBar: TermsWebviewAppbar(),
    );
  }
}
