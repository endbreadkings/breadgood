import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

class AppleLoginButton extends StatelessWidget {
  const AppleLoginButton({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ButtonTheme(
        buttonColor: Color(0xFF000000),
        minWidth: 300.0,
        height: 56.0,
        child: RaisedButton(
          elevation: 0,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              Padding(
                  padding: EdgeInsets.fromLTRB(6, 16, 55, 16),
                  child: Container(
                      width: 24,
                      height: 24,
                      child: SvgPicture.asset(
                        'asset/images/icon/login/icon_apple.svg',
                        fit: BoxFit.scaleDown,
                      ))),
              Text("Apple로 로그인",
                  style: TextStyle(color: Colors.white, fontSize: 15)),
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
