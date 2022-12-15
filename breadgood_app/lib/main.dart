import 'package:breadgood_app/modules/dashboard/dashboard_binding.dart';
import 'package:breadgood_app/routes/routes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      title: '빵집',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        fontFamily: 'AppleSDGothicNeo',
        scaffoldBackgroundColor: Colors.white,
        highlightColor: Colors.transparent,
        splashColor: Colors.transparent,
      ),
      defaultTransition: Transition.noTransition,
      initialRoute: initRoute(),
      initialBinding: DashboardBinding(),
      getPages: routes,
    );
  }
}
