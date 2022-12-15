import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/services/api/api_path.dart';
import 'package:breadgood_app/services/api/api_service.dart';
import 'package:breadgood_app/modules/my_page/model/user_info.dart';

Future<User> fetchUser() async {
  final response = await http.get(
    Uri.parse('$restApiUrl/user/me'),
    headers: await headers(),
  );
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  return User.fromJson(responseJson);
}

updateBreadStyle(int newBreadStyle) async {
  final response = await http.patch(
      Uri.parse('$restApiUrl/user/me/breadStyle/$newBreadStyle'),
      headers: await headers(),
      body: <String, String>{
        'breadStyleId': 'breadStyleId',
      });

  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print("updateBreadStyle $responseJson");
}

deleteUser() async {
  final response = await http.delete(
    Uri.parse('$restApiUrl/user/me/withdrawal'),
    headers: await headers(),
  );
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print("deleteUser $responseJson");
}
