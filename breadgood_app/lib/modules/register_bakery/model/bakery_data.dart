import 'dart:io';


class NaverMapData {
  String lastBuildDate;
  String total;
  String start;
  String display;
  List<SearchData> items;

  NaverMapData(
      {this.lastBuildDate, this.total, this.start, this.display, this.items});

  factory NaverMapData.fromJson(Map<String, dynamic> json) {
    print("naverMapData called");
    if (json == null) {
      throw FormatException("Null json");
    }
    return NaverMapData(
      lastBuildDate: json['lastBuildDate'].toString(),
      total: json['total'].toString(),
      start: json['start'].toString(),
      display: json['display'].toString(),
      items: json['items'] != null
          ?(json['items'] as List).map((i) => SearchData.fromJson(i)).toList()
          : null,
    );
  }
}

class SearchData {
  final String category;
  final String title;
  final String link;
  final String description;
  final String address;
  final String roadAddress;
  final String mapx;
  final String mapy;

  SearchData({
    this.category,
    this.title,
    this.link,
    this.description,
    this.address,
    this.roadAddress,
    this.mapx,
    this.mapy,
  });

  factory SearchData.fromJson(Map<String, dynamic> json) {
    print("search data called");
    return SearchData(
      category: json['category'].toString(),
      title: json['title'].toString().replaceAll("<b>", "").replaceAll("</b>", ""),
      link: json['link'].toString(),
      description: json['description'].toString(),
      address: json['address'].toString(),
      roadAddress: json['roadAddress'].toString(),
      mapx: json['mapx'].toString(),
      mapy: json['mapy'].toString(),
    );
  }
}

class BakeryMapData {
  int bakeryCategoryId;
   String city;
   String content;
   String description;
   String district;
   int emojiId;
  List<File> files;
   String mapX;
   String mapY;
   String roadAddress;
  // List<String> signatureMenus;
  // = List<String>();
  String signatureMenus;
   String title;

  BakeryMapData({
    this.bakeryCategoryId,//
    this.city,//
    this.content,
    this.description,//
    this.district,//
    this.emojiId,
    this.files,
    this.mapX,//
    this.mapY,//
    this.roadAddress,//
    this.signatureMenus,
    this.title,//
  });

  // set bakeryData(int _bakeryCategoryId, String _city, String _description,
  //     String _district, int _mapX, int _mapY, String _roadAddress, String _title) {
  //   bakeryCategoryId = _bakeryCategoryId;
  //   city = _city;
  //   description = _description;
  //   district = _district;
  //   mapX = _mapX;
  //   mapY = _mapY;
  //   roadAddress = _roadAddress;
  //   title = _title;
  // }

  factory BakeryMapData.toJson(Map<String, dynamic> json) {
    return BakeryMapData(
      bakeryCategoryId: json['bakeryCategoryId'],
      city: json['city'],
      content: json['content'],
      description: json['description'] as String,
      district: json['district'],
      emojiId: json['emojiId'],
      files: json['files'],
      mapX: json['mapX'].toString(),
      mapY: json['mapY'].toString(),
      roadAddress: json['roadAddress'] as String,
      // signatureMenus: json['signatureMenus'] as List,
      signatureMenus: json['signatureMenus'],
      // signatureMenus: json['signatureMenus'] != null
      //     ?(json['signatureMenus'] as List).map((i) => String.fromJson(i)).toList()
      //     : null,
      title: json['title'] as String,
    );
  }
}
