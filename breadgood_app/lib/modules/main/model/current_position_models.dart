import 'package:breadgood_app/modules/main/model/current_positions/legalcode.dart';

import 'current_positions/status.dart';

class CurrentPositionModels{
  Map<String,dynamic> status;
  List<dynamic> results;

  CurrentPositionModels({
    this.status,
    this.results
  });

  factory CurrentPositionModels.fromJson(Map<String, dynamic> parsedJson){
    var list = parsedJson['results'] as List;
    List<LegalCode> resultLists = list.map((i) => LegalCode.fromJson(i)).toList();

    return CurrentPositionModels(
        status: parsedJson['status'],
        results: resultLists
    );
  }
}