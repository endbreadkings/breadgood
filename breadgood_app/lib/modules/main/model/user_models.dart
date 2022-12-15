class UserModels {
  int breadStyleId;
  int id;
  String nickName;
  String profileImgUrl;

  UserModels({this.breadStyleId, this.id, this.nickName, this.profileImgUrl});

  factory UserModels.fromJson(Map<String, dynamic> parsedJson) {
    return UserModels(
        breadStyleId: parsedJson['breadStyleId'],
        id: parsedJson['id'],
        nickName: parsedJson['nickName'],
        profileImgUrl: parsedJson['profileImgUrl']);
  }
}
