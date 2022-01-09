import 'package:flutter/material.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:breadgood_app/utils/ui/bottomNavigation.dart';
import 'package:webview_flutter/webview_flutter.dart';

const String furtherInfoUrl = "https://antique-colt-66c.notion.site/d8ad1e9c427c40a38354717b86c6e224";

class FurtherInfo extends StatefulWidget {
  @override
  _FurtherInfoState createState() =>
      _FurtherInfoState();
}

class _FurtherInfoState extends State<FurtherInfo> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: DefaultAppBar(),
      body: WebView(
        initialUrl: furtherInfoUrl,
        javascriptMode: JavascriptMode.unrestricted,
      ),
      bottomNavigationBar: BottomNavigation(),
    );
  }
}