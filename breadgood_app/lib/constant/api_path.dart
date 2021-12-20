import 'package:flutter/foundation.dart';

const String URL = kReleaseMode
    ? 'https://api.breadgood.com'
    : 'https://api.breadgood.com';

const String VERSION = "/api/v1";

const String REST_API_URL = "${URL}${VERSION}";

const String KAKAO_LOGIN_URL =
    'https://api.breadgood.com/oauth2/authorization/kakao?redirect_uri=breadgoodapp://breadgood.com/login/oauth2/success';

const String APPLE_LOGIN_URL =
    'https://api.breadgood.com/oauth2/authorization/apple?redirect_uri=breadgoodapp://breadgood.com/login/oauth2/success';