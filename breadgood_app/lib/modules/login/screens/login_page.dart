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

final imageList = [
  OnBoard(
      text: "asset/images/onboard/login_onboard_1_text.png",
      image: "asset/images/onboard/login_onboard_1_image.png",
      width: 225),
  OnBoard(
      text: "asset/images/onboard/login_onboard_2_text.png",
      image: "asset/images/onboard/login_onboard_1_image.png",
      width: 209),
  OnBoard(
      text: "asset/images/onboard/login_onboard_3_text.png",
      image: "asset/images/onboard/login_onboard_1_image.png",
      width: 171),
  OnBoard(
      text: "asset/images/onboard/login_onboard_4_text.png",
      image: "asset/images/onboard/login_onboard_1_image.png",
      width: 225),
  OnBoard(
      text: "asset/images/onboard/login_onboard_5_text.png",
      image: "asset/images/onboard/login_onboard_1_image.png",
      width: 175),
];



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
          Stack(
            alignment: Alignment.center,
            children: [
              Container(
                  width: MediaQuery.of(context).size.width,
                  margin: EdgeInsets.only(top: 74, bottom: 0),
                  child: CarouselSlider(
                    carouselController: _controller,
                    options: CarouselOptions(
                        height: 424.0,
                        autoPlay: true,
                        enlargeCenterPage: true,
                        aspectRatio: 2.0,
                        onPageChanged: (index, reason) {
                          setState(() {
                            _current = index;
                          });
                        }),
                    items: imageList.map((i) {
                      return Builder(
                        builder: (BuildContext context) {
                          return Container(
                              width: MediaQuery.of(context).size.width,
                              // margin: EdgeInsets.symmetric(horizontal: 5.0),
                              child: Container(
                                  width: MediaQuery.of(context).size.width,
                                  decoration: BoxDecoration(),
                                  child: Column(
                                    children: [
                                      Image.asset(i.text,
                                          fit: BoxFit.cover, width: i.width),
                                      Image.asset(
                                        i.image,
                                        fit: BoxFit.cover,
                                      )
                                    ],
                                  )));
                        },
                      );
                    }).toList(),
                  )),
              Positioned(
                  bottom: 25,
                  child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: imageList.asMap().entries.map((entry) {
                        return GestureDetector(
                          onTap: () => _controller.animateToPage(entry.key),
                          child: Container(
                            width: 12.0,
                            height: 12.0,
                            margin: EdgeInsets.symmetric(vertical: 8.0, horizontal: 4.0),
                            decoration: BoxDecoration(
                                shape: BoxShape.circle,
                                color: (Theme.of(context).brightness == Brightness.dark
                                    ? THEME.GRAY_216
                                    : THEME.MAIN)
                                    .withOpacity(_current == entry.key ? 0.9 : 0.2)),
                          ),
                        );
                      }).toList(),))
            ],
          ),
          Container(
              // margin: EdgeInsets.only(top: 20),
              child: Column(
                children: [
                  new Container(
                    margin: EdgeInsets.only(bottom: 10),
                    width:300,
                    child: ButtonTheme(
                      buttonColor: Color.fromRGBO(255, 232, 18, 1),
                      textTheme: ButtonTextTheme.primary,
                      minWidth: 300.0,
                      height: 45.0,
                      child:
                      RaisedButton(
                        elevation: 0,
                        child:
                        Row(
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
                  if (Platform.isIOS) new Container(
                      width:300,child: _appleLogin())
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
