import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/utils/services/rest_api_service.dart';

Future<List<BakeryCategory>> fetchBakeryCategoryList() async {
  final response = await http.get(
      Uri.parse('https://api.breadgood.com/api/v1/bakeryCategory/list'),
      headers: await headers());
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print('fetch BakeryCategory');
  print(responseJson);
  // return BakeryCategory.fromJson(responseJson);
  return responseJson.map<BakeryCategory>((json) => BakeryCategory.fromJson(json)).toList();
}


class BakeryCategory {
  final int id;
  final String makerImgUrl;
  final String title;
  final String titleImgUrl;

  BakeryCategory({
    this.id,
    this.makerImgUrl,
    this.title,
    this.titleImgUrl,
  });

  factory BakeryCategory.fromJson(Map<String, dynamic> json) {
    return BakeryCategory(
      id: json['id'],
      makerImgUrl: json['makerImgUrl'],
      title: json['title'],
      titleImgUrl: json['titleImgUrl'],
    );
  }
}
