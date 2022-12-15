import 'dart:convert';

List<BreadStyleModels> breadStyleFromJson(String str) =>
    List<BreadStyleModels>.from(
        json.decode(str).map((x) => BreadStyleModels.fromJson(x)));

class BreadStyleModels {
  String content;
  int id;
  String imgUrl;
  String name;
  String profileImgUrl;
  bool check;

  BreadStyleModels(
      {this.content,
      this.id,
      this.imgUrl,
      this.name,
      this.profileImgUrl,
      this.check = false});

  @override
  String toString() {
    // TODO: implement toString
    return "check : ${check}";
  }

  factory BreadStyleModels.fromJson(Map<String, dynamic> parsedJson) =>
      BreadStyleModels(
        content: parsedJson['content'],
        id: parsedJson['id'],
        imgUrl: parsedJson['imgUrl'],
        name: parsedJson['name'],
        profileImgUrl: parsedJson['profileImgUrl'],
      );
}
