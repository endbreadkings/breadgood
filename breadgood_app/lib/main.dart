import 'package:breadgood_app/modules/dashboard/init_binding.dart';
import 'package:breadgood_app/modules/login/screens/login_page.dart';
import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import 'config/routes.dart';


void main() {
  runApp(MyApp());
}
// comment
class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        fontFamily: 'AppleSDGothicNeo',
        scaffoldBackgroundColor: Colors.white,
        highlightColor: Colors.transparent,
        splashColor: Colors.transparent,
      ),
      defaultTransition: Transition.noTransition,
      initialBinding: InitBinding(),
      getPages: Routes,
    );
  }

}

