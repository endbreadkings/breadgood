import 'dart:io';
import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:breadgood_app/modules/login/ui/apple_login_button.dart';

class LoginScene extends StatefulWidget {
  const LoginScene({Key key}) : super(key: key);

  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<LoginScene> {
  // TODO: - 출시 이후, deprecated 정리
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

  @override
  Widget build(BuildContext context) {
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
                width: 300,
                child: ButtonTheme(
                  buttonColor: Color(0xFFFEE500),
                  textTheme: ButtonTextTheme.primary,
                  minWidth: 300.0,
                  height: 56.0,
                  child: RaisedButton(
                    elevation: 0,
                    child: Row(
                      children: <Widget>[
                        Padding(
                            padding: EdgeInsets.fromLTRB(0, 16, 76, 16),
                            child: Container(
                                width: 24,
                                height: 24,
                                child: SvgPicture.asset(
                                  'asset/images/icon/login/icon_kakao.svg',
                                  fit: BoxFit.scaleDown,
                                ))),
                        Padding(
                            padding: EdgeInsets.only(top: 1),
                            child: Text("카카오 로그인",
                                style: TextStyle(
                                    color: Colors.black, fontSize: 15))),
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
                Column(children: [
                  SizedBox(height: 8),
                  new Container(width: 300, child: AppleLoginButton())
                ])
            ],
          ))
        ],
      ),
    ));
  }
}
