import 'dart:convert';
import 'package:breadgood_app/services/api/api_path.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/model/duplicated_bakery.dart';
import 'package:breadgood_app/services/api/api_service.dart';

class BakeryController extends GetxController {
  List<Color> bakeryCategoryBorderColor = List.filled(2, Colors.transparent);
  List<bool> selectedBakeryCategories = List.filled(2, false);
  int selectedBakeryCategoryId = -1;
  SearchData selectedBakery = null;
  CheckDuplicateBakery duplicateCheck;

  List<Color> emojiBorderColor = List.filled(6, Colors.transparent);
  List<bool> selectedEmoji = List.filled(6, false);
  int selectedEmojiId = -1;
  String reviewText;

  toggleButton(index) {
    var order = (index + 1) % 2;
    if (selectedBakeryCategoryId == index) {
      selectedBakeryCategoryId = -1;
    } else if (selectedBakeryCategories[order]) {
      selectedBakeryCategoryId = index;
      selectedBakeryCategories[order] = false;
    } else {
      selectedBakeryCategoryId = index;
    }

    selectedBakeryCategories[index] = !selectedBakeryCategories[index];

    for (int i = 0; i < 2; i++) {
      bakeryCategoryBorderColor[i] = (selectedBakeryCategories[i])
          ? Color(0xFF007AFF)
          : Colors.transparent;
    }

    update();
  }

  updateBakeryCategoryId(int categoryId) {}

  updateBakery(SearchData selected) {
    selectedBakery = selected;
  }

  updateDuplicateCheck(CheckDuplicateBakery duplicate) {
    duplicateCheck = duplicate;
  }

  /* review data functions */
  toggleEmoji(index) {
    if (selectedEmojiId != -1) {
      emojiBorderColor[selectedEmojiId] = Colors.transparent;
      if (selectedEmojiId == index) {
        selectedEmojiId = -1;
      } else {
        selectedEmoji[selectedEmojiId] = false;
        selectedEmojiId = index;
      }
    } else {
      selectedEmojiId = index;
    }
    selectedEmoji[index] = !selectedEmoji[index];

    if (selectedEmojiId != -1) {
      emojiBorderColor[selectedEmojiId] = Color(0xFF007AFF);
    }
    update();
  }

  updateReviewText(String text) {
    reviewText = text;
    update();
  }

  Future<CheckDuplicateBakery> checkRegisteredBakery(String roadAddress) async {
    final response = await http.post(
        Uri.parse('$restApiUrl/bakery/duplicate/roadAddress/$roadAddress'),
        headers: await post(),
        body: <String, String>{
          'roadAddress': 'roadAddress',
        });
    final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
    print("checkRegisteredBakery $responseJson");
    return CheckDuplicateBakery.fromJson(responseJson);
  }
}
