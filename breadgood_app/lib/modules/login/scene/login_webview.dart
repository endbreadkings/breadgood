import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/services/api/api_path.dart' as api_path;
import 'package:breadgood_app/storages/user_storage.dart';
import 'package:breadgood_app/common/ui/default_app_bar.dart';

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
  final tokens = new UserStorage();

  StreamSubscription<String> _onChanged;

  @override
  void initState() {
    super.initState();

    WidgetsFlutterBinding.ensureInitialized();

    _onChanged = flutterWebviewPlugin.onUrlChanged.listen((String url) {
      if (mounted) {
        if (url.contains("accessToken") && url.contains("refreshToken")) {
          bool isGuest =
              Uri.parse(url).queryParameters['isGuest'].toLowerCase() == 'true';

          String _accessToken = Uri.parse(url).queryParameters['accessToken'];
          String _refreshToken = Uri.parse(url).queryParameters['refreshToken'];

          flutterWebviewPlugin.close().then((value) => {
                if (isGuest)
                  {
                    Get.offAndToNamed('/signup/policy'),
                    tokens.setUserInfo(_accessToken, _refreshToken)
                  }
                else
                  {
                    tokens.getUserInfo().then((res) => {
                          if (res == null)
                            {
                              tokens
                                  .setUserInfo(_accessToken, _refreshToken)
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
                                  .setUserInfo(_accessToken, _refreshToken)
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
