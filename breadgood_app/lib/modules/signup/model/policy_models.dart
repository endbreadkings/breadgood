import 'dart:convert';

List<PolicyModels> policyFromJson(String str) => List<PolicyModels>.from(
    json.decode(str).map((x) => PolicyModels.fromJson(x)));

String policyFromJsonToJson(List<PolicyModels> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class PolicyModels {
  int typeId;

  String name;

  bool required;
  int termsId;

  String termsTypeLink;
  String termsLink;

  bool check;

  PolicyModels(
      {this.typeId,
      this.name,
      this.required,
      this.termsId,
      this.termsTypeLink,
      this.termsLink,
      this.check});

  toString() {
    return "name: " + name + " typeid: " + typeId.toString() + " check : " + check.toString();
  }

  factory PolicyModels.fromJson(Map<String, dynamic> parsedJson) =>
      PolicyModels(
          typeId: parsedJson['typeId'],
          name: parsedJson['name'],
          required: parsedJson['required'],
          termsId: parsedJson['termsId'],
          termsTypeLink: parsedJson['termsTypeLink'],
          termsLink: parsedJson['termsLink'],
          check:false
      );

  Map<String, dynamic> toJson() => {
        "typeId": typeId,
        "name": name,
        "required": required,
        "termsId": termsId,
        "termsTypeLink": termsTypeLink,
        "termsLink": termsLink,
        "check":check
      };
}
