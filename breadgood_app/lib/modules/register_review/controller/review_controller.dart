import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ReviewController extends GetxController {
  List<Color> emojiBorderColor = List.filled(6, Colors.transparent);
  List<bool> isSelectedEmoji = List.filled(6, false);
  int selectedEmojiId = -1;
  String reviewText;

  toggleEmoji(index) {
    if (selectedEmojiId != -1) {
      emojiBorderColor[selectedEmojiId] = Colors.transparent;
      if (selectedEmojiId == index) {
        selectedEmojiId = -1;
      } else {
        isSelectedEmoji[selectedEmojiId] = false;
        selectedEmojiId = index;
      }
    } else {
      selectedEmojiId = index;
    }
    isSelectedEmoji[index] = !isSelectedEmoji[index];
    if (selectedEmojiId != -1) {
      emojiBorderColor[selectedEmojiId] = Color(0xFF007AFF);
    }
    update();
  }

  updateEmoji(int emojiId) {
    selectedEmojiId = emojiId;
  }
}
