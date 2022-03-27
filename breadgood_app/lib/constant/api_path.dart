import 'package:flutter/foundation.dart';

const String url = kReleaseMode
    ? 'https://api.breadgood.com'
    : 'https://api.breadgood.com';

const String version = "/api/v1";

const String restApiUrl = "${url}${version}";

const String kakaoLoginUrl =
    'https://api.breadgood.com/oauth2/authorization/kakao?redirect_uri=breadgoodapp://breadgood.com/login/oauth2/success';

const String appleLoginUrl =
    'https://api.breadgood.com/oauth2/authorization/apple?redirect_uri=breadgoodapp://breadgood.com/login/oauth2/success';