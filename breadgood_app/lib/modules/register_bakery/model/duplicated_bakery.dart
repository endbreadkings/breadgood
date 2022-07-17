import 'dart:core';

class CheckDuplicateBakery {
  final int bakeryId;
  final bool idDuplicate;
  final String nickName;

  CheckDuplicateBakery({
    this.bakeryId,
    this.idDuplicate,
    this.nickName,
  });

  factory CheckDuplicateBakery.fromJson(Map<String, dynamic> json) {
    return CheckDuplicateBakery(
      bakeryId: json['bakeryId'],
      idDuplicate: json['idDuplicate'],
      nickName: json['nickName'],
    );
  }
}
