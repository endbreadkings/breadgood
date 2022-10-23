import 'package:breadgood_app/api_key.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class RegisterBakeryRenewalController {
  RegisterBakeryRenewalController(this.context, this.refresh);

  BuildContext context;
  VoidCallback refresh;
  
  TextEditingController searchController = TextEditingController();

  Future<void> onSearch() async {
    var dio = Dio();
    dio.options.baseUrl = 'https://dapi.kakao.com';
    dio.options.headers['Authorization'] = 'KakaoAK $kakaoRestApiKey';
    var response = await dio.get('/v2/local/search/keyword.json', queryParameters: {
      'query': '안동제과',
      'size': 10,
      'page': 2
    });
    print(response.data);
  }
}