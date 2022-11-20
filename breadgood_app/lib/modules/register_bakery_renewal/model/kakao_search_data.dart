class KakaoSearchData {
  KakaoSearchData.fromJson(dynamic json) {
    addressName = json['address_name'];
    categoryName = json['category_name'];
    placeName = json['place_name'];
    placeUrl = json['place_url'];
    roadAddressName = json['road_address_name'];
    x = json['x'];
    y = json['y'];
  }

  String addressName;
  String categoryName;
  String placeName;
  String placeUrl;
  String roadAddressName;
  String x;
  String y;
}

/*
    {
      "address_name": "서울 성동구 성수동1가 13-441",
      "category_group_code": "FD6",
      "category_group_name": "음식점",
      "category_name": "음식점 > 간식 > 제과,베이커리",
      "distance": "",
      "id": "27245239",
      "phone": "070-7806-1225",
      "place_name": "본노엘",
      "place_url": "http://place.map.kakao.com/27245239",
      "road_address_name": "서울 성동구 상원길 64",
      "x": "127.04800900705958",
      "y": "37.54974607971914"
    },
 */
