import 'package:breadgood_app/storages/user_storage.dart';

final String contentType = "application/json";
final String accept = "application/json";
final String charset = "utf-8";
final String bearer = "Bearer ";

UserStorage token = new UserStorage();

Future<Map<String, String>> headers() async {
  String accessToken = await token.getAccessToken();

  final Map<String, String> defaultHeader = {
    'Content-Type': contentType,
    'Accept': accept,
    'Authorization': bearer + accessToken
  };

  return defaultHeader;
}

Future<Map<String, String>> post() async {
  String accessToken = await token.getAccessToken();

  final Map<String, String> defaultHeader = {
    'Accept': contentType,
    'Authorization': bearer + accessToken
  };

  return defaultHeader;
}
