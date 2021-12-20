

class Status{
  int code;
  String name;
  String message;

  Status({
    this.code,
    this.name,
    this.message
  });

  factory Status.fromJson(Map<String, dynamic> parsedJson){
    return Status(
        code: parsedJson['code'],
        name: parsedJson['name'],
        message: parsedJson['message']
    );
  }
}