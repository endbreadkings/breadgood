import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

import 'package:breadgood_app/constant/api_path.dart' as api_path;
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
        icon: Container(
            height: 16,
            width: 8,
            child: SvgPicture.asset(
              'asset/images/Vector.svg',
              fit: BoxFit.scaleDown,
            )),
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
        url: "${api_path.url}${Get.arguments}",
        javascriptChannels: jsChannels,
        scrollBar: true,
        appBar: TermsWebviewAppbar(),
    );
  }
}
