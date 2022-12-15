import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/services/api/api_path.dart' as api_path;
import 'package:breadgood_app/services/api/api_service.dart' as rest_api;
import 'package:breadgood_app/modules/register_bakery/model/bakery_category.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';

Future<List<BakeryCategory>> fetchBakeryCategoryList() async {
  final response = await http.get(
      Uri.parse('${api_path.restApiUrl}/bakeryCategory/list'),
      headers: await rest_api.headers());
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  return responseJson
      .map<BakeryCategory>((json) => BakeryCategory.fromJson(json))
      .toList();
}

checkBakeryInfo(int bakeryId) async {
  final response = await http.post(
      Uri.parse('${api_path.restApiUrl}/bakery/$bakeryId'),
      headers: await rest_api.headers(),
      body: <String, String>{
        'bakeryId': 'bakeryId',
      });
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print("checkBakeryInfo $responseJson");
}

Future<NaverMapData> fetchSearchData(String searchKeyword) async {
  var endpointUrl = 'https://openapi.naver.com/v1/search/local.json';
  Map<String, String> queryParams = {
    'query': '$searchKeyword',
    'display': '10',
    'start': '1',
    'sort': 'random',
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
