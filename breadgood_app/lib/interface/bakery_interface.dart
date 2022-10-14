import 'dart:convert';

import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:http/http.dart' as http;

class BakeryInterface {
  // [offset] 개 이후로 [limit] 개 를 가져옴
  static Future<NaverMapData> fetchSearchData(String searchKeyword, int offset, int limit) async {
    var endpointUrl = 'https://openapi.naver.com/v1/search/local.json';
    Map<String, String> queryParams = {
      if (searchKeyword.isNotEmpty) 'query': searchKeyword,
      'display': limit.toString(),
      'start': offset.toString(),
    };
    String queryString = Uri(queryParameters: queryParams).query;

    var requestUrl = endpointUrl + '?' + queryString;
    var response = await http.get(
      Uri.parse(requestUrl),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Charset': 'utf-8',
        'X-Naver-Client-id': 'p24UOax9LBbitflVcBAv',
        'X-Naver-Client-Secret': 'G7hXtfqhP3',
      },
    );

    final responseJson = json.decode(response.body);
    return NaverMapData.fromJson(responseJson);
  }
}