import 'package:breadgood_app/utils/services/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:dio/dio.dart';

class DioManager {
  DioManager._constructor();

  static DioManager _instance = DioManager._constructor();

  factory DioManager() => _instance;

  Dio _dio = _createDio();

  static Dio _createDio() {
    var dio = Dio(BaseOptions(
      baseUrl: 'http://' + 'api.breadgood.com',
      receiveTimeout: 300000,
      connectTimeout: 150000,
      sendTimeout: 150000,
    ));

    dio.options.contentType = Headers.jsonContentType;

    return dio;
  }

  // 로그인 된 이후 시점에서 호출될 것을 전제로 합니다.
  Future<void> setToken() async {
    Tokens token = Tokens();

    String accessToken = await token.getAccessToken();

    Map<String, String> header = {
      'Accept': 'application/json',
      'Authorization': 'Bearer ' + accessToken
    };
    _dio.options.headers = header;
  }

  void removeToken() {
    _dio.options.headers.remove('Authorization');
  }

  Future<dynamic> get(String path,
      {Map<String, dynamic> query = const {}}) async {
    var response = await _dio.get(path, queryParameters: query);
    return response.data;
  }

  Future<dynamic> put(String path,
      {Map<String, dynamic> query = const {},
      Map<String, dynamic> requestBody = const {}}) async {
    var response =
        await _dio.put(path, queryParameters: query, data: requestBody);
    return response.data;
  }

  Future<dynamic> patch(String path,
      {Map<String, dynamic> query = const {},
      Map<String, dynamic> requestBody = const {}}) async {
    var response =
        await _dio.patch(path, queryParameters: query, data: requestBody);
    return response.data;
  }

  Future<dynamic> patchMultiPartFormData(String path,
      {Map<String, dynamic> query = const {}, FormData formData}) async {
    _dio.options.contentType = 'multipart/form-data';
    try {
      var response =
          await _dio.patch(path, queryParameters: query, data: formData);
      _dio.options.contentType = Headers.jsonContentType;
      return response.data;
    } catch (e) {
      _dio.options.contentType = Headers.jsonContentType;
      return null;
    }
  }

  Future<dynamic> delete(String path,
      {Map<String, dynamic> query = const {},
      Map<String, dynamic> requestBody = const {}}) async {
    var response =
        await _dio.delete(path, queryParameters: query, data: requestBody);
    return response.data;
  }

  Future<dynamic> post(String path,
      {Map<String, dynamic> query = const {},
      Map<String, dynamic> requestBody = const {}}) async {
    var response =
        await _dio.post(path, queryParameters: query, data: requestBody);
    return response.data;
  }

  Future<dynamic> postFormUrlEncoded(String path,
      {Map<String, dynamic> query = const {},
      Map<String, dynamic> requestBody = const {}}) async {
    _dio.options.contentType = Headers.formUrlEncodedContentType;
    var response =
        await _dio.post(path, queryParameters: query, data: requestBody);
    _dio.options.contentType = Headers.jsonContentType;
    return response.data;
  }

  /// how to make FormData link : https://dkswnkk.tistory.com/334
  /// var formData = FormData.fromMap({'image': await MultipartFile.fromBytes(bytes, filename: 'filename')});
  Future<dynamic> postMultiPartFormData(String path,
      {Map<String, dynamic> query = const {}, FormData formData}) async {
    _dio.options.contentType = 'multipart/form-data';
    try {
      var response =
          await _dio.post(path, queryParameters: query, data: formData);
      _dio.options.contentType = Headers.jsonContentType;
      return response.data;
    } catch (e) {
      _dio.options.contentType = Headers.jsonContentType;
      return null;
    }
  }
}
