import 'package:flutter/material.dart';
import 'package:breadgood_app/modules/main/scene/bakery_list_webview.dart';
import 'package:breadgood_app/common/ui/default_app_bar.dart';

class MainScene extends StatefulWidget {
  @override
  MainSceneState createState() => MainSceneState();
}

class MainSceneState extends State<MainScene> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: DefaultAppBar(),
      body: BakeryListWebview(),
    );
  }
}
