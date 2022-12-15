class Emoji {
  final int id;
  final String imgUrl;
  final String name;
  final int sortNumber;

  Emoji({
    this.id,
    this.imgUrl,
    this.name,
    this.sortNumber,
  });

  factory Emoji.fromJson(Map<String, dynamic> json) {
    return new Emoji(
      id: json['id'] as int,
      imgUrl: json['imgUrl'] as String,
      name: json['name'] as String,
      sortNumber: json['sortNumber'] as int,
    );
  }
}
