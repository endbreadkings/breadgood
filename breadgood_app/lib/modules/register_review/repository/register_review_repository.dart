import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:breadgood_app/services/api/api_path.dart' as api_path;
import 'package:breadgood_app/services/api/api_service.dart' as rest_api;
import 'package:breadgood_app/modules/register_review/model/emoji_data.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_review/model/review_data.dart';

Future<List<Emoji>> fetchEmoji() async {
  final response = await http.get(
    Uri.parse('${api_path.restApiUrl}/emoji/list'),
    headers: await rest_api.headers(),
  );
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  return responseJson.map<Emoji>((json) => Emoji.fromJson(json)).toList();
}

postNewBakery(BakeryMapData newBakery) async {
  var uri = Uri.parse('${api_path.restApiUrl}/bakery');
  var request = http.MultipartRequest('POST', uri);
  request.headers.addAll(await rest_api.headers());

  await Future.forEach(
    newBakery.files,
    (file) async => {
      request.files.add(
        http.MultipartFile(
          'files',
          (http.ByteStream(file.openRead())).cast(),
          await file.length(),
          filename: file.path.split('/').last,
        ),
      )
    },
  );

  request.fields['bakeryCategoryId'] = newBakery.bakeryCategoryId.toString();
  request.fields['city'] = newBakery.city;
  request.fields['content'] = newBakery.content;
  request.fields['description'] = newBakery.description;
  request.fields['district'] = newBakery.district;
  request.fields['emojiId'] = newBakery.emojiId.toString();
  request.fields['mapX'] = newBakery.mapX.toString();
  request.fields['mapY'] = newBakery.mapY.toString();
  request.fields['roadAddress'] = newBakery.roadAddress;
  request.fields['title'] = newBakery.title;

  if (newBakery.signatureMenus != null) {
    for (String item in newBakery.signatureMenus) {
      request.files.add(http.MultipartFile.fromString('signatureMenus', item));
    }
  }

  var response = await request.send();
  print("postNewBakery ${response.statusCode}");
}

postReview(ReviewData newReview) async {
  var uri =
      Uri.parse('${api_path.restApiUrl}/bakery/${newReview.bakeryId}/review');
  var request = http.MultipartRequest('POST', uri);
  request.headers.addAll(await rest_api.headers());

  await Future.forEach(
    newReview.files,
    (file) async => {
      request.files.add(
        http.MultipartFile(
          'files',
          (http.ByteStream(file.openRead())).cast(),
          await file.length(),
          filename: file.path.split('/').last,
        ),
      )
    },
  );

  request.fields['bakeryId'] = newReview.bakeryId.toString();
  request.fields['content'] = newReview.content;
  request.fields['emojiId'] = newReview.emojiId.toString();

  if (newReview.signatureMenus != null) {
    for (String item in newReview.signatureMenus) {
      request.files.add(http.MultipartFile.fromString('signatureMenus', item));
    }
  }

  var response = await request.send();
  print("postReview ${response.statusCode}");
}
