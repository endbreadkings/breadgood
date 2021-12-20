class Center {
  String crs;
  int x;
  int y;

  Center({this.crs, this.x, this.y});

  factory Center.fromJson(Map<String,dynamic> parsedJson){
    return Center(
      crs: parsedJson['crs'],
      x:parsedJson['x'],
      y:parsedJson['y']
    );
  }
}
