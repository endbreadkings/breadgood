import 'package:breadgood_app/modules/main/model/current_positions/code.dart';
import 'package:breadgood_app/modules/main/model/current_positions/region.dart';

class LegalCode {
  String name;
  Map<String,dynamic> code;
  Map<String,dynamic> region;

  LegalCode({this.name, this.code, this.region});

  factory LegalCode.fromJson(Map<String, dynamic> parsedJson) {
    return LegalCode(
        name: parsedJson['name'],
        code: parsedJson['code'],
        region: parsedJson['region']);
  }
}
