import 'dart:io';

import 'package:breadgood_app/modules/login/model/on_board.dart';
import 'package:breadgood_app/modules/main/controller/main_map_controller.dart';
import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:flutter_svg/flutter_svg.dart';
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
                  height: 56.0,
                  child: RaisedButton(
                    elevation: 0,
                    child: Row(
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.fromLTRB(0, 20, 78, 19),
                        child: Container(
                            width: 18,
                            height: 17,
                            child: SvgPicture.asset(
                              'asset/images/icon/login/kakao.svg',
                              fit: BoxFit.scaleDown,
                            )
                        )),
                        Center(child: Text("카카오 로그인", style: TextStyle(color: Colors.black, fontSize: 15))),
                      ],
                    ),
                    onPressed: () => {
                      Get.offAndToNamed('/login/oauth2/confirm',
                          arguments: 'KAKAO'),
                    },
                    shape: RoundedRectangleBorder(
                      borderRadius: new BorderRadius.circular(100.0),
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
        height: 56.0,
        child: RaisedButton(
          elevation: 0,
          child: Row(
            children: <Widget>[
              Padding(
          padding: EdgeInsets.fromLTRB(10, 20, 59, 19),
          child: Container(
                  width: 14,
                  height: 17.49,
                  child: SvgPicture.asset(
                    'asset/images/icon/login/apple.svg',
                    fit: BoxFit.scaleDown,
                  )
              )),
              Center(child:Text("애플 계정으로 로그인", style: TextStyle(color: Colors.white, fontSize: 15))),
            ],
          ),
          textColor: Colors.white,
          onPressed: () =>
              {Get.offAndToNamed('/login/oauth2/confirm', arguments: 'APPLE')},
          shape: RoundedRectangleBorder(
            borderRadius: new BorderRadius.circular(100.0),
          ),
        ));
  }
}
