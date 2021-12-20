import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';


class BakeryController extends GetxController {
  /* bakery data variables */
  List<Color> bakery_category_border_color = List.filled(2, Colors.transparent);
  List<bool> selected_bakery_category = List.filled(2, false);
  int selected_bakery_category_id = -1;
  SearchData selectedBakery;
  CheckDuplicateBakery duplicateCheck;
  // BakeryMapData newBakery;

  /* review data variables */
  List<Color> emoji_border_color = List.filled(6, Colors.transparent);
  List<bool> emoji_selected = List.filled(6, false);
  int selected_emoji_id = -1;
  String review_text;

  /* bakery data functions */
  toggleButton(index) {
    print('index: ${index}');
    var the_other = (index + 1) % 2;
    print('the other: ${the_other}');
    // 같은 카테고리 눌러서 해제할 경우
    if(selected_bakery_category_id == index) {
      selected_bakery_category_id = -1;
    }
    // 이전과 반대 카테고리 선택할 경우
    else if(selected_bakery_category[the_other] == true) {
      // 선택된 카테고리 id update
      selected_bakery_category_id = index;
      selected_bakery_category[the_other] = false;
    }
    // 이전에 선택된 카테고리가 없을 경우
    // selected_bakery_category_id == -1
    else {
      selected_bakery_category_id = index;
    }

    // 선택된 category toggle
    selected_bakery_category[index] = !selected_bakery_category[index];

    // 카테고리들 보더 색깔 update
    for(int i = 0; i < 2; i++) {
      bakery_category_border_color[i] = (selected_bakery_category[i]) ?Color(0xFF007AFF):Colors.transparent;
    }

    update();
  }

  UpdateBakeryCategoryId(int Categoryid) {

  }

  UpdateBakery(SearchData selected) {
    selectedBakery = selected;
    print('selected Bakery Update: ${selected.title}');
  }

  UpdateDuplicateCheck(CheckDuplicateBakery duplicate) {
    duplicateCheck = duplicate;
    print('duplicate check? ${duplicateCheck.idDuplicate}, ${duplicateCheck.nickName},');
  }


  /* review data functions */
  toggleEmoji(index) {
    print('toggle emoji called(index: ${index})');

    // 이전에 선택했던 이모지 있는 경우
    if(selected_emoji_id != -1) {
      // 이전에 선택했던 이모지 색깔 해
      emoji_border_color[selected_emoji_id] = Colors.transparent;
      // 이전에 선택했던 이모지 해제하는 경우
      if (selected_emoji_id == index) {
        selected_emoji_id = -1;
      }
      // 이전과 다른 이모지 선택하는 경우
      else {
        print('else');
        // 이전에 선택됐던 이모지 해제
        emoji_selected[selected_emoji_id] = false;
        // emoji_border_color[selected_emoji_id] = Colors.transparent;
        // 새로 선택된 이모지 아이디로 업데이트
        selected_emoji_id = index;
      }
    }
    // 이전에 선택했던 이모지 없는 경우
    else {
      // 이모지 아이디 업데이트
      selected_emoji_id = index;
    }
    // 선택한 이모지 상태 toggle
    emoji_selected[index] = !emoji_selected[index];

    // 선택된 이모지 있다면 색깔 업데이트
    if(selected_emoji_id != -1) {
      emoji_border_color[selected_emoji_id] = Color(0xFF007AFF);
    }
    update();
  }

  UpdateReviewText(String text) {
    review_text = text;
    print('updateReivewText ${review_text}');
    update();
  }
}