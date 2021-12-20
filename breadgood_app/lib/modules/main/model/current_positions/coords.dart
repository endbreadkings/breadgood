import 'package:breadgood_app/modules/main/model/current_positions/center.dart';

class Coords{
  Map<String,dynamic> center;

  Coords({this.center});

  factory Coords.fromJson(Map<String,dynamic> parsedJson){
    return Coords(
      center: parsedJson['center']
    );
  }
}