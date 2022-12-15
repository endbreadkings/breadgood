// TODO: - 강현정: API Key 전달 받아야 함
// import 'package:breadgood_app/api_key.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery_renewal/model/kakao_search_data.dart';
import 'package:dio/dio.dart';

class KakaoPlaceSearchService {
  static Future<List<SearchData>> get(String searchWord, int page) async {
    var dio = Dio();
    dio.options.baseUrl = 'https://dapi.kakao.com';
    // dio.options.headers['Authorization'] = 'KakaoAK $kakaoRestApiKey';
    var response =
        await dio.get('/v2/local/search/keyword.json', queryParameters: {
      'query': searchWord,
      'size': 5,
      'page': page,
      'category_group_code': [
        'FD6',
        'CE7'
      ] // https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword-request-category-group-code
    });
    List<KakaoSearchData> kakaoSearchData = [];
    for (var e in response.data['documents']) {
      kakaoSearchData.add(KakaoSearchData.fromJson(e));
    }
    return kakaoSearchData
        .map((e) => SearchData(
            title: e.placeName,
            address: e.addressName,
            roadAddress: e.roadAddressName,
            category: e.categoryName,
            description: '',
            link: e.placeUrl,
            mapx: e.x,
            mapy: e.y))
        .toList();
  }
}
