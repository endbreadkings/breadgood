import 'package:breadgood_app/modules/main/model/current_positions/coords.dart';

class Area {
  String name;
  Map<String,dynamic> coords;

  Area({
    this.name, this.coords
  });

  factory Area.fromJson(Map<String,dynamic> parsedJson){
    return Area(
      name: parsedJson['name'],
      coords: parsedJson['coords']
    );
  }
}