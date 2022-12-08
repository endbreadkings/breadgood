import 'dart:async';

import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';
import 'package:get/get.dart';

String selectedUrl = "";

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

          flutterWebviewPlugin.close().then((value) => {
                if (isGuest)
                  {
                    Get.offAndToNamed('/signup/policy'),
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
                                            Get.offAndToNamed('/dashboard'),
                                            tokens.setLoggedIn(true)
                                          }
                                        else
                                          {
                                            Get.offAndToNamed('/'),
                                            tokens.setLoggedIn(false)
                                          }
                                      })
                            }
                          else
                            {
                              tokens
                                  .setUserInfo(_access_token, _refresh_token)
                                  .then((res) => {
                                        if (res)
                                          {
                                            Get.offAndToNamed('/dashboard'),
                                            tokens.setLoggedIn(true)
                                          }
                                        else
                                          {
                                            Get.offAndToNamed('/'),
                                            tokens.setLoggedIn(false)
                                          }
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
            ? api_path.kakaoLoginUrl
            : api_path.appleLoginUrl,
        javascriptChannels: jsChannels,
        appBar: DefaultAppBar());
  }
}
