import 'dart:io';

import 'package:breadgood_app/modules/login/model/on_board.dart';
import 'package:breadgood_app/modules/main/controller/main_map_controller.dart';
import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:url_launcher/url_launcher.dart';
import 'dart:ui';

import 'package:breadgood_app/config/themes/light_theme.dart' as THEME;

class LoginPage extends StatefulWidget {
  const LoginPage({Key key}) : super(key: key);

  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<LoginPage> {
  launchBrowser(String url) async {
    if (await canLaunch(url)) {
      final bool succeeded =
          await launch(url, forceSafariVC: false, universalLinksOnly: true);
      if (!succeeded) {
        await launch(url, forceSafariVC: true, universalLinksOnly: true);
      } else
        throw 'Could not launch $url';
    }
  }

  final CarouselController _controller = CarouselController();

  int _current = 0;

  @override
  Widget build(BuildContext context) {
    // MainMapController _controllerWithGetX = Get.put(MainMapController());

    return Scaffold(
        body: Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          Image.asset("asset/images/onboard/login_onboard.png"),
          Container(
              child: Column(
            children: [
              new Container(
                margin: EdgeInsets.only(bottom: 10),
                width: 300,
                child: ButtonTheme(
                  buttonColor: Color.fromRGBO(255, 232, 18, 1),
                  textTheme: ButtonTextTheme.primary,
                  minWidth: 300.0,
                  height: 45.0,
                  child: RaisedButton(
                    elevation: 0,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Image.asset("asset/images/icon/kakao.png"),
                        SizedBox(width: 6),
                        Text("카카오 로그인", style: TextStyle(color: Colors.black)),
                      ],
                    ),
                    onPressed: () => {
                      Get.offAndToNamed('/login/oauth2/confirm',
                          arguments: 'KAKAO'),
                    },
                    shape: RoundedRectangleBorder(
                      borderRadius: new BorderRadius.circular(20.0),
                    ),
                  ),
                ),
              ),
              if (Platform.isIOS)
                new Container(width: 300, child: _appleLogin())
            ],
          ))
        ],
      ),
    ));
  }
}

class _appleLogin extends StatelessWidget {
  const _appleLogin({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ButtonTheme(
        buttonColor: Color.fromRGBO(0, 0, 0, 1),
        minWidth: 300.0,
        height: 45.0,
        child: RaisedButton(
          elevation: 0,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Image.asset("asset/images/icon/apple.png"),
              SizedBox(width: 10),
              Text("애플 계정으로 로그인", style: TextStyle(color: Colors.white)),
            ],
          ),
          textColor: Colors.white,
          onPressed: () =>
              {Get.offAndToNamed('/login/oauth2/confirm', arguments: 'APPLE')},
          shape: RoundedRectangleBorder(
            borderRadius: new BorderRadius.circular(20.0),
          ),
        ));
  }
}
