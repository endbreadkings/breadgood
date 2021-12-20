class Code {
  int id;
  String type;
  int mappingId;

  Code({this.id, this.type, this.mappingId});

  factory Code.fromJson(Map<String, dynamic> parsedJson) {
    return Code(
        id: parsedJson['id'],
        type: parsedJson['type'],
        mappingId: parsedJson['mappingId']);
  }
}
