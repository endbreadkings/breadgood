import 'package:breadgood_app/api_key.dart';
import 'package:breadgood_app/manager/dio_manager.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery_renewal/model/kakao_search_data.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class RegisterBakeryRenewalController {
  RegisterBakeryRenewalController(this.context, this.refresh);

  BuildContext context;
  VoidCallback refresh;
  
  TextEditingController searchController = TextEditingController();
  List<SearchData> searchedList = [];

  Future<void> onSearch() async {
    var dio = Dio();
    dio.options.baseUrl = 'https://dapi.kakao.com';
    dio.options.headers['Authorization'] = 'KakaoAK $kakaoRestApiKey';
    var response = await dio.get('/v2/local/search/keyword.json', queryParameters: {
      'query': searchController.text,
      'size': 5,
      'page': 1,
      'category_group_code': 'FD6' // https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword-request-category-group-code
    });
    List<KakaoSearchData> kakaoSearchData = [];
    for (var e in response.data['documents']) {
      kakaoSearchData.add(KakaoSearchData.fromJson(e));
    }
    searchedList = kakaoSearchData.map((e) => SearchData(
      title: e.placeName,
      address: e.addressName,
      roadAddress: e.roadAddressName,
      category: e.categoryName,
      description: '',
      link: e.placeUrl,
      mapx: e.x,
      mapy: e.y
    )).toList();
    print(searchedList.map((e) => e.title));
    refresh();
  }

  // true : registered one
  Future<bool> checkRegisteredBakery(String roadAddress) async {
    var response = await DioManager().post('/api/v1/bakery/duplicate/roadAddress/${roadAddress}');
    return response['isDuplicate'].toString() == 'true';
  }
}