import 'dart:async';

import 'package:breadgood_app/constant/api_path.dart' as PATH;
import 'package:breadgood_app/modules/main/controller/main_map_controller.dart';
import 'package:breadgood_app/modules/main/screens/bakery_list_webview.dart';
import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';
import 'package:get/get.dart';
import 'package:naver_map_plugin/naver_map_plugin.dart';

String selectedUrl = "";

// ignore: prefer_collection_literals
final Set<JavascriptChannel> jsChannels = [
  JavascriptChannel(
      name: 'Print',
      onMessageReceived: (JavascriptMessage message) {
        print(message.message);
      }),
].toSet();

class LoginWebView extends StatefulWidget {
  @override
  _LoginWebViewState createState() => _LoginWebViewState();
}

class _LoginWebViewState extends State<LoginWebView> {
  final flutterWebviewPlugin = new FlutterWebviewPlugin();
  final tokens = new Tokens();

  // var controller = MainMapController();

  StreamSubscription<String> _onUrlChanged;

  @override
  void initState() {
    super.initState();

    WidgetsFlutterBinding.ensureInitialized();

    // Add a listener to on url changed
    _onUrlChanged = flutterWebviewPlugin.onUrlChanged.listen((String url) {
      if (mounted) {
        if (url.contains("accessToken") && url.contains("refreshToken")) {
          bool isGuest =
              Uri.parse(url).queryParameters['isGuest'].toLowerCase() == 'true';

          String _access_token = Uri.parse(url).queryParameters['accessToken'];
          String _refresh_token =
              Uri.parse(url).queryParameters['refreshToken'];

          print("isGeust : ${isGuest}");

          flutterWebviewPlugin.close().then((value) => {
                if (isGuest)
                  {
                    print("isGeust : ${isGuest}"),
                    Get.offAndToNamed('/signup/policy'),
                    print(_access_token),
                    tokens.setUserInfo(_access_token, _refresh_token)
                  }
                else
                  {
                    tokens.getUserInfo().then((res) => {
                          if (res == null)
                            {
                              tokens
                                  .setUserInfo(_access_token, _refresh_token)
                                  .then((res) => {
                                        if (res)
                                          {
                                            //set 성공시
                                            // controller
                                            //     .locationDenied()
                                            //     .catchError((e) {
                                            //   print("e.error : ${e.error}");
                                            // }).then((value) {
                                              // MainMapController()
                                              //     .getCurrentLatitudeLongtitude()
                                              //     .then((value) {
                                              //   MainMapController()
                                              //       .fetchAddress(
                                              //           value[0], value[1])
                                              //       .then((value) {
                                              //     MainMapController()
                                              //         .bakeryList(
                                              //             value[0], value[1])
                                              //         .then((value) {
                                              //       Get.offAndToNamed('/main');
                                              //     });
                                              //   });
                                              // });
                                              Get.offAndToNamed('/main')
                                            // })
                                          }
                                        else
                                          Get.offAndToNamed('/')
                                      })
                            }
                          else
                            {
                              tokens
                                  .setUserInfo(_access_token, _refresh_token)
                                  .then((res) => {
                                        if (res)
                                          {
                                            //set 성공시
                                            // controller
                                            //     .locationDenied()
                                            //     .catchError((e) {
                                            //   print("e.error : ${e.error}");
                                            // }).then((value) {
                                              // MainMapController()
                                              //     .getCurrentLatitudeLongtitude()
                                              //     .then((value) {
                                              //   MainMapController()
                                              //       .fetchAddress(
                                              //           value[0], value[1])
                                              //       .then((value) {
                                              //     MainMapController()
                                              //         .bakeryList(
                                              //             value[0], value[1])
                                              //         .then((value) {
                                              //       Get.offAndToNamed('/main');
                                              //     });
                                              //   });
                                              // });
                                              Get.offAndToNamed('/main')
                                            // })
                                          }
                                        else
                                          Get.offAndToNamed('/')
                                      })
                            }
                        })
                  }
              });
        }
      } else {}
    });
  }

  @override
  Widget build(BuildContext context) {
    return WebviewScaffold(
        url: Get.arguments == 'KAKAO'
            ? PATH.KAKAO_LOGIN_URL
            : PATH.APPLE_LOGIN_URL,
        javascriptChannels: jsChannels,
        appBar: DefaultAppBar());
  }
}
