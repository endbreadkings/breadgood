import 'package:breadgood_app/interface/kakao_place_search_interface.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class SearchMoreController {
  SearchMoreController(this.context, this.refresh, this.searchWord) {
    onSeeMoreButtonClicked();
  }

  BuildContext context;
  VoidCallback refresh;

  String searchWord;
  int page = 1;
  List<SearchData> searchList = [];
  List<SearchData> tempList = [];
  bool isEnd = false;

  final String seoul = '서울';

  Future<void> onSeeMoreButtonClicked() async {
    List<SearchData> addList = [];
    addList.addAll(tempList);
    tempList = [];

    while(addList.length < 5 && !isEnd) {
      var result = await KakaoPlaceSearchInterface.get(searchWord, page);

      for(SearchData data in result.searchData) {
        var city = data.getCity();
        print(city);
        if (city == seoul) {  // 서울일 경우에만 노출
          if (addList.length < 5) {
            addList.add(data);
          } else {
            tempList.add(data);
          }
        }
      }

      isEnd = result.isEnd;
      page = page + 1;
    }

    searchList.addAll(addList);
    refresh();
  }
}