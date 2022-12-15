class BakeryCategory {
  final int id;
  final String markerImgUrl;
  final String title;
  final String titleColoredImgUrl;
  final String titleUnColoredImgUrl;
  final int sortNumber;
  final String content;
  final String color;

  BakeryCategory({
    this.id,
    this.markerImgUrl,
    this.title,
    this.titleColoredImgUrl,
    this.titleUnColoredImgUrl,
    this.sortNumber,
    this.content,
    this.color,
  });

  factory BakeryCategory.fromJson(Map<String, dynamic> json) {
    return BakeryCategory(
      id: json['id'],
      markerImgUrl: json['markerImgUrl'],
      title: json['title'],
      titleColoredImgUrl: json['titleColoredImgUrl'],
      titleUnColoredImgUrl: json['titleUnColoredImgUrl'],
      color: json['color'],
      sortNumber: json['sortNumber'],
      content: json['content'],
    );
  }
}
