import 'package:breadgood_app/modules/main/model/current_positions/area.dart';

class Region {
  Map<String,dynamic> area0;
  Map<String,dynamic> area1;
  Map<String,dynamic> area2;
  Map<String,dynamic> area3;
  Map<String,dynamic> area4;

  Region({
    this.area0, this.area1, this.area2, this.area3, this.area4
  });

  factory Region.fromJson(Map<String, dynamic> parsedJson){
    return Region(
        area0: parsedJson['area0'],
        area1: parsedJson['area1'],
        area2: parsedJson['area2'],
        area3: parsedJson['area3'],
        area4: parsedJson['area4']
    );
  }
}