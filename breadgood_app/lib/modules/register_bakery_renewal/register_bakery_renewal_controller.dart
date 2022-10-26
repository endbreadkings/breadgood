import 'package:breadgood_app/interface/kakao_place_search_interface.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:flutter/material.dart';

class RegisterBakeryRenewalController {
  RegisterBakeryRenewalController(this.context, this.refresh);

  BuildContext context;
  VoidCallback refresh;
  
  TextEditingController searchController = TextEditingController();
  List<SearchData> searchList = [];

  Future<void> onSearch() async {
    searchList = await KakaoPlaceSearchInterface.get(searchController.text, 1);
    refresh();
  }
}