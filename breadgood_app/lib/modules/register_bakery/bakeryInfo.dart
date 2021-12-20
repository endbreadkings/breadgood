//
// class ReviewData {
//   String BreadStyleName;
//   String content;
//   String create_at;
//   List<String> detailImgUrls;
//   String emojiImgUrl;
//   String emojiName;
//   String nickName;
//   String profileImgUrl;
//   List<String> signatureMenus;
//   List<String> thumnailImgUrls;
//
// }
//
// class SearchData {
//   final String category;
//   final String title;
//   final String link;
//   // final String description;
//   // final String telephone;
//   final String address;
//   final String roadAddress;
//   // final int mapx;
//   // final int mapy;
//   final String mapx;
//   final String mapy;
//
//   SearchData({
//     this.category,
//     this.title,
//     this.link,
//     // this.description,
//     // this.telephone,
//     this.address,
//     this.roadAddress,
//     this.mapx,
//     this.mapy,
//   });
//
//   factory SearchData.fromJson(Map<String, dynamic> json) {
//     // factory NaverMapData.fromJson.cast<String, dynamic> {
//     // factory SearchData.fromJson(dynamic json) {
//     print("search data called");
//     return SearchData(
//       category: json['category'].toString(),
//       title: json['title'].toString().replaceAll("<b>", "").replaceAll("</b>", ""),
//       link: json['titlinkle'].toString(),
//       // description: json['description'] as String,
//       // telephone: json['telephone'] as String,
//       address: json['address'].toString(),
//       roadAddress: json['roadAddress'].toString(),
//       // mapx: int.parse(jsonMap['mapx']),
//       // mapy: json['mapy'] as int,
//       mapx: json['mapx'].toString(),
//       mapy: json['mapy'].toString(),
//       // mapx: json['mapx'] as String,
//       // mapy: json['mapy'] as String,
//     );
//     // return SearchData(
//     //   json['category'] as String,
//     //   json['title'] as String,
//     //   json['titlinkle'] as String,
//     //   json['description'] as String,
//     //   json['telephone'] as String,
//     //   json['address'] as String,
//     //   json['roadAddress'] as String,
//     //   json['mapx'] as int,
//     //   json['mapy'] as int
//     // );
//   }
//
// // Map<String, dynamic> toJson() => {
// //       "category": category,
// //       "title": title,
// //       "link": link,
// //       "address": address,
// //       "roadAddress": roadAddress,
// //       "mapx": mapx,
// //       "mapy": mapy,
// //     };
// }