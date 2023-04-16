import 'package:breadgood_app/interface/kakao_place_search_interface.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery_renewal/model/kakao_search_data.dart';
import 'package:flutter/material.dart';

class RegisterBakeryRenewalController {
  RegisterBakeryRenewalController(this.context, this.refresh);

  BuildContext context;
  VoidCallback refresh;
  
  TextEditingController searchController = TextEditingController();
  List<SearchData> searchList = [];
  bool isEnd;

  final String seoul = '서울';

  Future<void> onSearch() async {
    initialize();

    int page = 1;
    String keyword = searchController.text;

    while(searchList.length < 5 && !isEnd) {
      KakaoSearchData result = await KakaoPlaceSearchInterface.get(keyword, page);

      for(SearchData data in result.searchData) {
        var city = data.getCity();
        if (city == seoul && searchList.length < 5) {
          searchList.add(data);
        }
      }

      isEnd = result.isEnd;
      page = page + 1;
    }

    refresh();
  }

  initialize() {
    searchList = [];
    isEnd = false;
  }
}