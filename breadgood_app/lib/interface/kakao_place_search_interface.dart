import 'package:breadgood_app/api_key.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery_renewal/model/kakao_bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery_renewal/model/kakao_search_data.dart';
import 'package:dio/dio.dart';

class KakaoPlaceSearchInterface {
  static Future<KakaoSearchData> get(String searchWord, int page) async {
    if (searchWord.isEmpty) {
      return KakaoSearchData(
          [],
          true,
      );
    }

    var dio = Dio();
    dio.options.baseUrl = 'https://dapi.kakao.com';
    dio.options.headers['Authorization'] = 'KakaoAK $kakaoRestApiKey';
    var response = await dio.get('/v2/local/search/keyword.json', queryParameters: {
      'query': searchWord,
      'size': 5,
      'page': page,
      'category_group_code': ['FD6', 'CE7'], // https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword-request-category-group-code
      // 'x': 126.977829174031,
      // 'y': 37.5663174209601,
      // 'radius': 20000 // 서울시청 반경 20km 내의 결과만 조회
    });

    List<KakaoBakeryData> bakeries = [];
    dynamic meta = response.data['meta'];

    for (var e in response.data['documents']) {
      bakeries.add(KakaoBakeryData.fromJson(e));
    }

    List<SearchData> convertedBakeries = bakeries.map((e) => SearchData(
        title: e.placeName,
        address: e.addressName,
        roadAddress: e.roadAddressName,
        category: e.categoryName,
        description: '',
        link: e.placeUrl,
        mapx: e.x,
        mapy: e.y
    )).toList();

    return KakaoSearchData(
        convertedBakeries,
        meta['is_end']
    );
  }
}