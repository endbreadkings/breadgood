import 'dart:io';

class ReviewData {
  int bakeryId;
  String content;
  int emojiId;
  List<File> files;
  List<String> signatureMenus;

  ReviewData({
    this.bakeryId,
    this.content,
    this.emojiId,
    this.files,
    this.signatureMenus,
  });

  factory ReviewData.toJson(Map<String, dynamic> json) {
    return ReviewData(
      bakeryId: json['bakeryId'],
      content: json['content'],
      emojiId: json['emojiId'],
      files: json['files'],
      signatureMenus: json['signatureMenus'],
    );
  }
}
