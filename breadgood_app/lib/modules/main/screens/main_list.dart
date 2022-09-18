import 'package:breadgood_app/modules/main/screens/bakery_list_webview.dart';
import 'package:breadgood_app/utils/ui/bottomNavigation.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';

class MainList extends StatefulWidget {
  @override
  MainListState createState() => MainListState();
}

class MainListState extends State<MainList> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: DefaultAppBar(),
      body: BakeryListWebview(),
    );
  }
}
