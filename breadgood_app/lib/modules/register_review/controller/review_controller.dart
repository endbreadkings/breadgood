// import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
// import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';

class ReviewController extends GetxController {
  // Color default_color = Colors.transparent;
  List<Color> emoji_border_color = List.filled(6, Colors.transparent);
  List<bool> emoji_selected = List.filled(6, false);
  int selected_emoji_id = -1;
  String review_text;
  // int the_other;
  // SearchData selectedBakery;
  // CheckDuplicateBakery duplicateCheck;


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
  //
  // UpdateBakery(SearchData selected) {
  //   selectedBakery = selected;
  //   print('selected Bakery Update: ${selected.title}');
  // }
  //
  // UpdateDuplicateCheck(CheckDuplicateBakery duplicate) {
  //   duplicateCheck = duplicate;
  //   print('duplicate check? ${duplicateCheck.idDuplicate}, ${duplicateCheck.nickName},');
  // }

  UpdateEmoji(int emojiId) {
    selected_emoji_id = emojiId;
    print('selected_emoji_id: ${selected_emoji_id}');
  }

}