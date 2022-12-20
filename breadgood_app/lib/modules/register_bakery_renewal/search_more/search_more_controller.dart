import 'package:breadgood_app/interface/kakao_place_search_interface.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:flutter/cupertino.dart';

class SearchMoreController {
  SearchMoreController(this.context, this.refresh, this.searchWord) {
    onSeeMoreButtonClicked();
  }

  BuildContext context;
  VoidCallback refresh;

  String searchWord;
  int page = 1;
  List<SearchData> searchList = [];

  Future<void> onSeeMoreButtonClicked() async {
    searchList.addAll(await KakaoPlaceSearchInterface.get(searchWord, page++));
    refresh();
  }
}