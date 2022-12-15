import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/modules/signup/model/bread_style_model.dart';
import 'package:breadgood_app/services/api/api_path.dart' as api_path;
import 'package:breadgood_app/services/api/api_service.dart' as rest_api;

class BreadStyleService {
  static Future<dynamic> getBreadStyle() async {
    final url = Uri.parse("${api_path.restApiUrl}/breadstyle/list");
    Map<String, String> headers = await rest_api.headers();
    final response = await http.get(url, headers: headers);
    if (response.statusCode == 200) {
      var jsonString = utf8.decode(response.bodyBytes);
      return breadStyleFromJson(jsonString);
    }
  }
}
