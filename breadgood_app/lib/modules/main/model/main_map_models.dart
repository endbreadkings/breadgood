import 'dart:convert';

List<MapModels> policyFromJson(String str) =>
    List<MapModels>.from(json.decode(str).map((x) => MapModels.fromJson(x)));

String policyFromJsonToJson(List<MapModels> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class MapModels {
  String categoryTitle;
  double mapX;
  double mapY;
  String profileImgUrl;
  String title;
  String nickName;
  String roadAddress;
  String makerImgUrl;
  List<dynamic> signatureMenus;
  String content;

  int id;

  MapModels(
      {this.categoryTitle,
      this.mapX,
      this.mapY,
      this.profileImgUrl,
      this.title,
      this.nickName,
      this.roadAddress,
      this.makerImgUrl,
      this.signatureMenus,
      this.content,
      this.id});

  factory MapModels.fromJson(Map<String, dynamic> json) {
    return MapModels(
        categoryTitle: json['categoryTitle'],
        mapX: json['mapX'],
        mapY: json['mapY'],
        profileImgUrl: json['profileImgUrl'],
        title: json['title'],
        nickName: json['nickName'],
        roadAddress: json['roadAddress'],
        makerImgUrl: json['makerImgUrl'],
        signatureMenus:json['signatureMenus'],
        content: json['content'],
        id: json['id']);
  }

  Map<String, dynamic> toJson() => {
        "categoryTitle": categoryTitle,
        "mapX": mapX,
        "mapY": mapY,
        "profileImgUrl": profileImgUrl,
        "title": title,
        "nickName": nickName,
        "roadAddress": roadAddress,
        "makerImgUrl": makerImgUrl,
        "content": content,
        "id": id
      };
}
