import 'package:flutter/foundation.dart';

const String url = kReleaseMode
    ? 'https://api.breadgood.com'
    : 'https://dev-api.breadgood.com';

const String version = "/api/v1";

const String restApiUrl = "${url}${version}";

const String kakaoLoginUrl =
    '${url}/oauth2/authorization/kakao?redirect_uri=breadgoodapp://breadgood.com/login/oauth2/success';

const String appleLoginUrl =
    '${url}/oauth2/authorization/apple?redirect_uri=breadgoodapp://breadgood.com/login/oauth2/success';