

import 'dart:convert';

String signUpFromJsonToJson(SignupModels data) {
  data.termsTypeIds=data.setTermsTypeIds.toList();
  return json.encode(data.toJson());
}

class SignupModels {
  int breadStyleId=-1;
  String nickName="";
  List<int> termsTypeIds=[];
  Set<int> setTermsTypeIds={};


  toString() {
    return "breadStyleId: " + breadStyleId.toString() + " nickName: " + nickName.toString() + " termsTypeIds : " + termsTypeIds.toString();
  }

  Map<String, dynamic> toJson() => {
    "breadStyleId": breadStyleId,
    "nickName": nickName,
    "termsTypeIds": termsTypeIds
  };
}
