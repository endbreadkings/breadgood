import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as rest_api;

Future<List<BakeryCategory>> fetchBakeryCategoryList() async {
  final response = await http.get(
      Uri.parse('${api_path.restApiUrl}/bakeryCategory/list'),
      headers: await rest_api.headers());
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print('fetch BakeryCategory');
  print(responseJson);
  // return BakeryCategory.fromJson(responseJson);
  return responseJson.map<BakeryCategory>((json) => BakeryCategory.fromJson(json)).toList();
}


class BakeryCategory {
  final int id;
  final String markerImgUrl;
  final String title;
  final String titleColoredImgUrl;
  final String titleUnColoredImgUrl;
  final int sortNumber;
  final String content;
  final String color;

  BakeryCategory({
    this.id,
    this.markerImgUrl,
    this.title,
    this.titleColoredImgUrl,
    this.titleUnColoredImgUrl,
    this.sortNumber,
    this.content,
    this.color,
  });

  factory BakeryCategory.fromJson(Map<String, dynamic> json) {
    return BakeryCategory(
      id: json['id'],
      markerImgUrl: json['markerImgUrl'],
      title: json['title'],
      titleColoredImgUrl: json['titleColoredImgUrl'],
      titleUnColoredImgUrl: json['titleUnColoredImgUrl'],
      color: json['color'],
      sortNumber: json['sortNumber'],
      content: json['content'],
    );
  }
}
