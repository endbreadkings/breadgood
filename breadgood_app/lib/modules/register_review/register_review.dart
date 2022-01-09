import 'dart:io';
import 'dart:convert';
import 'dart:async';
import 'package:breadgood_app/modules/main/screens/main_map.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/screens/celebrate_register.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:image_picker/image_picker.dart';
import 'package:multi_image_picker/multi_image_picker.dart';
import 'package:flutter_svg/flutter_svg.dart';

import 'package:flutter_absolute_path/flutter_absolute_path.dart';
import 'package:http/http.dart' as http;

import 'package:hashtagable/hashtagable.dart';
import 'package:hashtagable/widgets/hashtag_text_field.dart';
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:breadgood_app/utils/services/rest_api_service.dart';
import 'package:heic_to_jpg/heic_to_jpg.dart';
import 'package:path/path.dart' as path_lib;
import 'package:path_provider/path_provider.dart';

// var cur_image_cnt = 0;
var max_image_cnt = 10;
Future<List<Emoji>> fetchEmoji() async {
  // final response = await http.get(
  //   Uri.parse('https://api.breadgood.com/api/v1/bakeryCategory/list'),
  //   // Uri.parse('breadgoodapp://breadgood.com/login/oauth2/success'),
  //   // Uri.parse('https://api.breadgood.com'),
  //   headers: {
  //     HttpHeaders.authorizationHeader: "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjI0OTcyMDk0LCJleHAiOjE2MjQ5NzM4OTR9.0PKHWxKY1eV3ogg2lutIkZHS0BPXt448oh2QWJ53eq5GcukMXceoT3milOF4T3xPHpf_QMSgm1te65BMBWjIMQ",
  //     // "Authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjI0OTcyMDk0LCJleHAiOjE2MjQ5NzM4OTR9.0PKHWxKY1eV3ogg2lutIkZHS0BPXt448oh2QWJ53eq5GcukMXceoT3milOF4T3xPHpf_QMSgm1te65BMBWjIMQ"
  //   },
  // );
  final response = await http
      .get(Uri.parse('https://api.breadgood.com/api/v1/emoji/list'),
      headers: await headers(),
  );
  // print(response.body);
  // final responseJson = json.decode(response.body);
  final responseJson = jsonDecode(utf8.decode(response.bodyBytes));

  // final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
  print(responseJson);
  //
  // return (responseJson['id']['imgUrl']['name']['sortNumber'] as List)
  //     .map((p) => Emoji.fromJson(p))
  //     .toList();
  return responseJson.map<Emoji>((json) => Emoji.fromJson(json)).toList();
  // return List<Emoji.fromJson(responseJson)>;
  // if (response.statusCode == 200) {
  // If the server did return a 200 OK response,
  // then parse the JSON.
  // return BakeryCategory.fromJson(jsonDecode(response.body));
  // } else if (response.statusCode == 400) {
  //   // If the server did not return a 200 OK response,
  //   // then throw an exception.
  //   throw Exception('Failed to load user 400');
  // } else {
  // If the server did not return a 200 OK response,
  // then throw an exception.
  // throw Exception('Failed to load user ${response.statusCode}');
  // throw Exception('Failed to load user ${response.statusCode}');
  // }
}

class Emoji {
  final int id;
  final String imgUrl;
  final String name;
  final int sortNumber;

  Emoji({
    this.id,
    this.imgUrl,
    this.name,
    this.sortNumber,
  });

  // factory BakeryCategory.fromJson(Map<String, dynamic> json) {
  factory Emoji.fromJson(Map<String, dynamic> json) {
    return new Emoji(
      id: json['id'] as int,
      imgUrl: json['imgUrl'] as String,
      name: json['name'] as String,
      sortNumber: json['sortNumber'] as int,
    );
  }
}
//
//
// Future<http.Response> uploadReviewImages(List<File> fileList) async {
//   print('uploadReviewImages');
//   final response = await http.post(
//       Uri.parse(
//           'https://api.breadgood.com/api/v1/s3/test/upload'),
//       headers: {
//         // 'Accept': 'application/json',
//         'Authorization': 'Bearer $token',
//       },
//       body: <String, String>{
//         'image': 'fileList',
//       });
//   if (response.statusCode == 200) {
//     print('Uploaded!');
//   } else {
//     print('error: ');
//     print(response.statusCode);
//   }
//   final responseJson = jsonDecode(utf8.decode(response.bodyBytes));
//   print(responseJson);
// }

Future<http.Response> uploadReviewImages(List<File> fileList) async {
  print('uploadReviewImages called');
  var uri = Uri.parse('https://api.breadgood.com/api/v1/s3/test/upload');
  var request = http.MultipartRequest('POST', uri);
  request.headers.addAll(
    await headers()
  );

  print(fileList.length);
  // print(cur_image_cnt);
  // for (int i = 0; i < fileList.length; i++) {
  //   print(fileList[i].toString());
  //   request.files.add(
  //       await http.MultipartFile.fromString(
  //       i.toString(), fileList[i].toString()));
  // }

  await Future.forEach(
    fileList,
    (file) async => {
      request.files.add(
        http.MultipartFile(
          'image',
          (http.ByteStream(file.openRead())).cast(),
          await file.length(),
          // filename: file.toString(),
          filename: file.path.split('/').last,
        ),
      )
    },
  );
  var response = await request.send();
  if (response.statusCode == 200) {
    print('Uploaded!');
  } else {
    print('error: ');
    print(response.statusCode);
  }
  final responseString = await response.stream.bytesToString();
  print(responseString);
}

Future<http.Response> postNewBakery(BakeryMapData newBakery) async {
  print('postNewBakery called');
  var uri = Uri.parse('https://api.breadgood.com/api/v1/bakery');
  var request = http.MultipartRequest('POST', uri);
  request.headers.addAll(
    await headers()
  );

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
  request.fields['signatureMenus'] = newBakery.signatureMenus;

  var response = await request.send();
  if (response.statusCode == 200) {
    print('Uploaded!');
  } else {
    print('error of post new bakery: ');
    print(response.statusCode);
  }

  print('response:');
  http.Response responseStream = await http.Response.fromStream(response);
  // final responseJson = jsonDecode(utf8.decode(responseStream.bodyBytes));
  // print("Result: ${response.statusCode}");
  // return responseStream.body;
  print(response.contentLength);
  print(responseStream.body);
}

class RegisterReviewPage extends StatefulWidget {
  const RegisterReviewPage({Key key}) : super(key: key);

  @override
  _RegisterReviewPageState createState() => _RegisterReviewPageState();
}

class _RegisterReviewPageState extends State<RegisterReviewPage> {
  List<Asset> imageList = List<Asset>();
  List<File> fileList = List<File>();
  File _image;
  Future<List<Emoji>> emoji;
  List<bool> isSelected = [false, false, false, false, false];
  var textcnt = 0;
  bool emoji_selected = false;
  // final controller = Get.put(ReviewController());
  final controller = Get.put(BakeryController());

  // var manager = BakeryMapDataManager();
  // BakeryMapData bakeryToRegister = BakeryMapData();
  var bakeryToRegister = BakeryMapData();

  var bakery = BakeryMapData();

  int cur_image_cnt = 0;
  final reviewTextController = TextEditingController();
  List<TextEditingController> signatureMenuTextControllers =
      List.generate(3, (i) => TextEditingController());
  List<String> allowedExtensions = ["JPG", "JPEG", "PNG", "WEBP", "SVG", "GIF"];

  @override
  void initState() {
    super.initState();
    emoji = fetchEmoji();
    print('init state');
    // print(emoji.toString());
    // fetchUser();
    // String nickName = futureUser.user;
  }

  _onChanged(String text) {
    setState(() {
      textcnt = text.length;
    });
  }

  @override
  Widget build(BuildContext context) {
    print('build');
    return GestureDetector(
        onTap: () => FocusManager.instance.primaryFocus?.unfocus(),
        child: Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: RegisterReviewPageAppbar(),
        body:
            // Column(
            //   children: [
            GetBuilder<BakeryController>(builder: (_) {
          print("bakeryCard called");
          return
              // Container(height: 0, width:0);
              // }
              // ),
              SingleChildScrollView(
                  child: Padding(
            padding: EdgeInsets.fromLTRB(31, 26, 30, 0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                RichText(
                  text: TextSpan(
                    style: TextStyle(
                      color: Colors.black,
                      fontSize: 16.0,
                      fontWeight: FontWeight.w700,
                    ),
                    children: <TextSpan>[
                      TextSpan(
                        text: '남기고 싶은 이야기가 있나요?\n아래 ',
                      ),
                      TextSpan(
                          text: '당신의 이야기',
                          style: TextStyle(
                            color: Color(0xFF4579FF),
                          )),
                      TextSpan(
                        text: ' 를 들려주세요!',
                      ),
                    ], // buildFutureBuilder(),
                  ),
                ),
                SizedBox(height: 8.0),
                Text("표정으로 당신의 마음을 보여주세요!",
                    style: TextStyle(
                      fontWeight: FontWeight.w400,
                      fontSize: 14,
                    )),
                // emoji 남기기
                Container(
                  child: (emoji == null)
                      ? Text('noEmoji')
                      : buildFutureEmojiBuilder(),
                ),
                // Text(emoji.),
                // Text(emoji[1].name),

                // 텍스트리뷰 남기기
                Container(
                  height: 160,
                  child: Material(
                    elevation: 1.0,
                    borderRadius: BorderRadius.circular(8),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        TextFormField(
                          controller: reviewTextController,
                          onChanged: _onChanged,
                          keyboardType: TextInputType.multiline,
                          maxLines: null,
                          minLines: 5,
                          // maxLength: 500,
                          style: TextStyle(
                            fontSize: 15,
                          ),
                          // onChanged: (text) {
                          //   textcnt = text.length;
                          // },
                          // initialValue: nickname,
                          decoration: InputDecoration(
                              contentPadding: new EdgeInsets.symmetric(
                                  vertical: 20.0, horizontal: 20.0),
                              border: InputBorder.none,
                              // border: OutlineInputBorder(
                              //   borderRadius: BorderRadius.circular(8),
                              //   borderSide: BorderSide(color: Colors.white, width: 1.0)
                              // ),
                              hintText: '진정한 빵덕후라면 10글자 이상 리뷰는 필수!',
                              hintStyle: TextStyle(
                                fontSize: 15,
                              ),
                              counterText: '$textcnt/500',
                              counterStyle: TextStyle(
                                fontSize: 16,
                              )),
                          // inputFormatters: [
                          //   LengthLimitingTextInputFormatter(1),
                          // ],
                        ),
                        // Align(
                        //   alignment: Alignment.bottomRight,
                        //   child: Padding(
                        //     padding: EdgeInsets.fromLTRB(0, 0, 15, 0),
                        //     child: Text('${textcnt}/500',
                        //         style: TextStyle(
                        //             fontSize: 16,
                        //             fontWeight: FontWeight.w400,
                        //             color: Color(0xFF5C5C5C))),
                        //   ),
                        // ),
                      ],
                    ),
                  ),
                ),
                // 시그니처메뉴 선택하기
                Padding(
                  padding: EdgeInsets.fromLTRB(0, 40, 0, 17),
                  child: RichText(
                    text: TextSpan(
                      style: TextStyle(
                        color: Colors.black,
                        fontSize: 15.0,
                      ),
                      children: <TextSpan>[
                        TextSpan(
                          text: '꼭 추천하고 싶은\n',
                        ),
                        TextSpan(
                            text: '시그니처 메뉴',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              color: Colors.blue,
                            )),
                        TextSpan(
                          text: '를 해시태그로 남겨주세요!',
                        ),
                      ],
                    ),
                  ),
                ),
                //해시태그
                // InputChip(
                // label: Stack(
                //   overflow: Overflow.visible,
                //   children: [
                // Row(
                Column(
                  // runSpacing: 5.0,
                  // spacing: 5.0,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Padding(
                    //   padding: EdgeInsets.only(top: 10.0),
                    //   child:
                    // IntrinsicWidth(
                    // child: FractionallySizedBox(
                    //   widthFactor: 0.5,
                    // child:


                    _createInputSignatureMenu(0),


                    // HashTagTextField(
                    //   // controller: signatureMenuTextController[0],
                    //   basicStyle: TextStyle(fontSize: 15, color: Colors.black),
                    //   decoratedStyle:
                    //       TextStyle(fontSize: 15, color: Colors.blue),
                    //   // keyboardType: TextInputType.multiline,
                    //   decoration: InputDecoration(
                    //     contentPadding: new EdgeInsets.symmetric(
                    //         vertical: 6.0, horizontal: 0.0),
                    //     // border: OutlineInputBorder(
                    //     //   borderSide: BorderSide(
                    //     //     width: 5.0,
                    //     //   )
                    //     // ),
                    //     // border: OutlineInputBorder(
                    //     //   borderRadius: const BorderRadius.all(
                    //     //     const Radius.circular(30.0),
                    //     //   ),
                    //     // ),
                    //     isDense: true,
                    //     hintText: '#시그니처메뉴1',
                    //   ),
                    //
                    //   /// Called when detection (word starts with #, or # and @) is being typed
                    //   onDetectionTyped: (text) {
                    //     print(text);
                    //   },
                    //
                    //   /// Called when detection is fully typed
                    //   onDetectionFinished: () {
                    //     print("detection finished");
                    //   },
                    //   // maxLines: null,
                    // ),





                    // ),
                    // ),
                    // ),
                    SizedBox(height: 17),
                    // Padding(
                    //   padding: EdgeInsets.only(top: 10.0),
                    //   child: IntrinsicWidth(
                    // child: FractionallySizedBox(
                    // alignment: Alignment.bottomRight,
                    // widthFactor: 0.5,
                    // child:

                    _createInputSignatureMenu(1),
                    // HashTagTextField(
                    //   basicStyle: TextStyle(fontSize: 15, color: Colors.black),
                    //   decoratedStyle:
                    //       TextStyle(fontSize: 15, color: Colors.blue),
                    //   // keyboardType: TextInputType.multiline,
                    //   decoration: InputDecoration(
                    //     contentPadding: new EdgeInsets.symmetric(
                    //         vertical: 6.0, horizontal: 0.0),
                    //     // border: OutlineInputBorder(
                    //     //   borderRadius: const BorderRadius.all(
                    //     //     const Radius.circular(30.0),
                    //     //   ),
                    //     // ),
                    //     isDense: true,
                    //     hintText: '#시그니처메뉴2',
                    //   ),
                    //
                    //   /// Called when detection (word starts with #, or # and @) is being typed
                    //   onDetectionTyped: (text) {
                    //     print(text);
                    //   },
                    //
                    //   /// Called when detection is fully typed
                    //   onDetectionFinished: () {
                    //     print("detection finished");
                    //   },
                    //   // maxLines: null,
                    // ),
                    // ),
                    // ),
                    // ),
                    SizedBox(height: 17),
                    // Padding(
                    //   padding: EdgeInsets.only(top: 10.0),
                    //   child: IntrinsicWidth(
                    // child: FractionallySizedBox(
                    //   widthFactor: 0.5,
                    // child:
                    _createInputSignatureMenu(2),
                    // HashTagTextField(
                    //
                    //   basicStyle: TextStyle(fontSize: 15, color: Colors.black),
                    //   decoratedStyle:
                    //       TextStyle(fontSize: 15, color: Colors.blue),
                    //   // keyboardType: TextInputType.multiline,
                    //   decoration: InputDecoration(
                    //     contentPadding: new EdgeInsets.symmetric(
                    //         vertical: 6.0, horizontal: 0.0),
                    //     // border: OutlineInputBorder(
                    //     //   borderRadius: const BorderRadius.all(
                    //     //     const Radius.circular(30.0),
                    //     //   ),
                    //     // ),
                    //     isDense: true,
                    //     hintText: '#시그니처메뉴3',
                    //   ),
                    //
                    //   /// Called when detection (word starts with #, or # and @) is being typed
                    //   onDetectionTyped: (text) {
                    //     print(text);
                    //   },
                    //
                    //   /// Called when detection is fully typed
                    //   onDetectionFinished: () {
                    //     print("detection finished");
                    //   },
                    //   // maxLines: null,
                    // ),
                    // ),
                    // ),
                    // ),
                  ],
                ),
                // ),

                //인증샷
                Padding(
                  padding: EdgeInsets.only(top: 40.0, bottom: 10.0),
                  child: RichText(
                    text: TextSpan(
                      style: TextStyle(
                        color: Colors.black,
                        fontSize: 16.0,
                        fontWeight: FontWeight.w700,
                      ),
                      children: <TextSpan>[
                        TextSpan(
                          text: '남기고 싶은 ',
                        ),
                        TextSpan(
                            text: '인증샷',
                            style: TextStyle(
                              color: Colors.blue,
                            )),
                        TextSpan(
                          text: '이 있나요?',
                        ),
                      ],
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 16.0),
                  child: Text("생생한 후기 사진은\n다른 빵덕후들에게도 도움이 됩니다",
                      style: TextStyle(
                        fontWeight: FontWeight.w700,
                        fontSize: 16,
                      )),
                ),
                RaisedButton(
                  child: Text("사진 추가하기 ($cur_image_cnt/$max_image_cnt) >",
                      style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                          color: Color(0xFF2D2D41))),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30.0),
                  ),
                  onPressed: () {
                    showCupertinoModalPopup<void>(
                      context: context,
                      builder: (BuildContext context) => CupertinoActionSheet(
                        // title: const Text('Title'),
                        // message: const Text('Message'),
                        actions: <CupertinoActionSheetAction>[
                          CupertinoActionSheetAction(
                            child: const Text('카메라로 추가'),
                            onPressed: () async {
                              PickedFile f = await ImagePicker()
                                  .getImage(source: ImageSource.camera);
                              File image = File(f.path);
                              setState(() {
                                cur_image_cnt++;
                                _image = image;
                              });
                              // //
                              // //   // upload(image);
                              // //
                              Navigator.pop(
                                context,
                              );
                            },
                            // onPressed: () => getImage(ImageSource.camera),
                          ),
                          CupertinoActionSheetAction(
                            child: const Text('앨범에서 추가'),
                            onPressed: () async {
                              getImage();
                              // getImage(ImageSource.gallery);
                              // PickedFile f = await ImagePicker().getImage(source:ImageSource.gallery);
                              // // var image = await ImagePicker().getImage(source:ImageSource.gallery);
                              // File image = File(f.path);
                              // setState(() {
                              //   _image = image;
                              //   // _counter++;
                              // });
                              Navigator.pop(
                                context,
                              );
                            },
                            //   onPressed: () => getImage(ImageSource.gallery),
                          )
                        ],
                      ),
                    );

                    // Get.to(CelebrateRegisterPage());
                  },
                  color: Color(0xFFE5EDFF),
                  elevation: 0,
                ),
                //사진 list

                Padding(
                  padding: EdgeInsets.fromLTRB(0, 24, 0, 32),
                  child: Container(
                    // margin: EdgeInsets.symmetric(vertical: 20.0),
                    height: 120.0,
                    child:
                        // ListView.builder(
                        //   itemBuilder: (BuildContext ctx, int index) {
                        //
                        //     return Image.file(imageList[index]);
                        //   },
                        //   itemCount: imageList.length,
                        // ),
                        ListView(
                            scrollDirection: Axis.horizontal,
                            children: List.generate(imageList.length, (index) {
                              // children: List.generate(10, (index) {
                              Asset asset = imageList[index];
                              // return ViewImages(index, asset, key: UniqueKey(),);}
                              return Padding(
                                  padding: EdgeInsets.only(right: 8.0),
                                  child: ClipRRect(
                                      borderRadius: BorderRadius.circular(10.0),
                                      child:Stack(
                                        children: <Widget>[
                                      AssetThumb(
                                        asset: asset,
                                        width: 120,
                                        height: 120,
                                      ),
                              Positioned(
                              right: -5,
                              top: -5,
                              child:
                              // SvgPicture.asset('asset/images/icon/registerReview/x.svg'),
                              IconButton(
                                icon:
                                    SvgPicture.asset('asset/images/icon/registerReview/x.svg'),
                                // Image.asset('asset/images/icon/main/Icon.png'),
                              iconSize: 32,
                              onPressed: () => setState(() {
                              imageList.removeAt(index);
                              })
                              )
                              )
                              ])));
                            })),
                  ),
                ),
                SizedBox(
                  width: double.infinity,
                  child: RaisedButton(
                    child: Text("완성!",
                        style: TextStyle(
                            color: Colors.white, fontWeight: FontWeight.bold)),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30.0),
                    ),
                    onPressed:
                        // 최초등록자 추가 등록자 구분 필요
                        ((textcnt > 9) && (controller.selected_emoji_id != -1))
                            // s3 이미지 업로드

                            ? () {
                                print('before calling uploadImage');
                                print(fileList.length);
                                print(cur_image_cnt);
                                // if(fileList.length != 0) uploadReviewImages(fileList);
                                // if (cur_image_cnt != 0) {
                                  // uploadReviewImages(fileList);
                                // controller.review_text = reviewTextController.text;
                                // controller.UpdateReviewText(reviewTextController.text);
                                // manager.bakery(bakeryCategoryId: controller.selected_bakery_category_id);
                                print('controller.selected.bakeryCategoryId: ${controller.selected_bakery_category_id}');
                                bakeryToRegister.bakeryCategoryId = controller.selected_bakery_category_id + 1;
                                print('bakeryToRegister.bakeryCategoryId: ${bakeryToRegister.bakeryCategoryId}');
                                bakeryToRegister.roadAddress = controller.selectedBakery.roadAddress;
                                bakeryToRegister.content = reviewTextController.text;
                                // bakeryToRegister.signatureMenus = [signatureMenuTextControllers[0].text.replaceAll("#", ""),
                                //   signatureMenuTextControllers[1].text, signatureMenuTextControllers[2].text,
                                // ];
                                print('signature menus:');
                                // bakeryToRegister.signatureMenus
                                // bakeryToRegister.signatureMenus = "";
                                for(int i = 0; i < 3; i++) {
                                  print('${i}: ${signatureMenuTextControllers[i]
                                      .text}');
                                }
                                  // bakeryToRegister.signatureMenus[i] = signatureMenuTextControllers[i].text.replaceAll("#", "");
                                  // bakeryToRegister.signatureMenus.add(signatureMenuTextControllers[i].text.replaceAll("#", ""));
                                  // print('${i}: ${bakeryToRegister.signatureMenus[i]}');
                                  // print('${i}: ${signatureMenuTextControllers[i].text}');
                                  // bakeryToRegister.signatureMenus = signatureMenuTextControllers[i].text.replaceAll("#", "");

                                  bakeryToRegister.signatureMenus = signatureMenuTextControllers[0].text.replaceAll("#", "");
                                bakeryToRegister.signatureMenus += ',';
                                bakeryToRegister.signatureMenus += signatureMenuTextControllers[1].text.replaceAll("#", "");
                                bakeryToRegister.signatureMenus += ',';
                                bakeryToRegister.signatureMenus += signatureMenuTextControllers[2].text.replaceAll("#", "");

                                  print('one string: ${bakeryToRegister.signatureMenus}');
                                // }
                                bakeryToRegister.emojiId = controller.selected_emoji_id;
                                print('emojiId: ${bakeryToRegister.emojiId}');
                                bakeryToRegister.title = controller.selectedBakery.title;
                                bakeryToRegister.city = controller.selectedBakery.roadAddress.split(" ")[0];
                                print('city: ${bakeryToRegister.city}');
                                bakeryToRegister.description = controller.selectedBakery.description;
                                bakeryToRegister.district = controller.selectedBakery.roadAddress.split(" ")[1];
                                print('district: ${bakeryToRegister.district}');
                                bakeryToRegister.mapX = controller.selectedBakery.mapx;
                                bakeryToRegister.mapY = controller.selectedBakery.mapy;
                                print("mapX: ${bakeryToRegister.mapX}, mapY: ${bakeryToRegister.mapY}");
                                bakeryToRegister.files = fileList;
                                  postNewBakery(bakeryToRegister);
                                // }
                                print('after calling uploadImage');

                                // if(controller.duplicateCheck.idDuplicate == true)
                                //   {
                                //     Get.to(BaseMapPage());
                                //   }
                                // else {
                                  Get.to(CelebrateRegisterPage());
                                // }
                              }
                            : null,
                    color: Colors.blue,

                    // () {
                    //   if(((textcnt > 9) && (controller.selected_emoji_id != -1)))
                    //   {
                    //     print('before calling uploadImage');
                    //     print(fileList.length);
                    //     uploadReviewImages(fileList);
                    //     color:Color(0xFF4579FF);
                    //     Get.to(CelebrateRegisterPage());
                    //   } else if(textcnt < 10) {
                    //
                    //   }
                    //   else {
                    //
                    //   }
                    // }
                  ),
                ),
              ],
            ),
          ));
        })

        // ])
        ));
  }

  FutureBuilder<List<Emoji>> buildFutureEmojiBuilder() {
    return FutureBuilder<List<Emoji>>(
      future: emoji,
      //   future: fetchUser(),
      builder: (context, snapshot) {
        // print(snapshot.data[0].name);
        if (snapshot.hasData) {
          // return ToggleButtons(
          //   children: List<Widget>.generate(5, (index) {
          //         // return Image.network(snapshot.data[index].imgUrl);
          //         return Column(
          //             children: <Widget>[
          //           Container(
          //             child: Padding(
          //               padding: EdgeInsets.all(10.0),
          //               child: Image.network(snapshot.data[index].imgUrl),
          //             ),
          //             margin: EdgeInsets.all(10.0),
          //             decoration: BoxDecoration(
          //               border: Border.all(
          //                   color: Colors.grey, style: BorderStyle.solid),
          //               borderRadius: BorderRadius.all(Radius.circular(10)),
          //             ),
          //           ),
          //           Text(snapshot.data[index].name),
          //   ],
          //         );
          //   }),
          //   onPressed: (int index) {
          //     setState(() {
          //       for (int buttonIndex = 0; buttonIndex < isSelected.length; buttonIndex++) {
          //         if (buttonIndex == index) {
          //           isSelected[buttonIndex] = !isSelected[buttonIndex];
          //         } else {
          //           isSelected[buttonIndex] = false;
          //         }
          //       }
          //     });
          //   },
          //   isSelected: isSelected,
          // );

          // return Row(
          //   mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          //   children: List<Widget>.generate(5, (index) {
          //   Column(
          //   children: <Widget>[
          //
          //     Text(snapshot.data[index].name),
          //   ],
          // );}),
          //     );
          //   ToggleButtons(
          //     children: List<Widget>.generate(5, (index) {
          //           // return Image.network(snapshot.data[index].imgUrl);
          //           return Column(
          //               children: <Widget>[
          //             Container(
          //               child: Padding(
          //                 padding: EdgeInsets.all(10.0),
          //                 child: Image.network(snapshot.data[index].imgUrl),
          //               ),
          //               margin: EdgeInsets.all(10.0),
          //               decoration: BoxDecoration(
          //                 border: Border.all(
          //                     color: Colors.grey, style: BorderStyle.solid),
          //                 borderRadius: BorderRadius.all(Radius.circular(10)),
          //               ),
          //             ),
          //             Text(snapshot.data[index].name),
          //     ],
          //           );
          //     }),
          //     onPressed: (int index) {
          //       setState(() {
          //         for (int buttonIndex = 0; buttonIndex < isSelected.length; buttonIndex++) {
          //           if (buttonIndex == index) {
          //             isSelected[buttonIndex] = !isSelected[buttonIndex];
          //           } else {
          //             isSelected[buttonIndex] = false;
          //           }
          //         }
          //       });
          //     },
          //     isSelected: isSelected,
          //   );

          // return Row(
          //     mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          //     children: List<Widget>.generate(5, (index) {
          //       // return Image.network(snapshot.data[index].imgUrl);
          //       return Column(children: <Widget>[
          // Container(
          //   child: Padding(
          //     padding: EdgeInsets.all(10.0),
          //     child: Image.network(snapshot.data[index].imgUrl),
          //   ),
          //   margin: EdgeInsets.all(10.0),
          //   decoration: BoxDecoration(
          //     border: Border.all(
          //         color: Colors.grey, style: BorderStyle.solid),
          //     borderRadius: BorderRadius.all(Radius.circular(10)),
          //   ),
          // ),
          // OutlinedButton(
          //   onPressed: () {
          //
          //   },
          //
          //   child: Image.network(snapshot.data[index].imgUrl),
          // ),
          return ToggleButtons(
            children: List<Widget>.generate(5, (index) {
              // return Image.network(snapshot.data[index].imgUrl);
              return Padding(
                  padding: EdgeInsets.fromLTRB(0, 20, 12, 18),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    // crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      // trial 1
                      //     Container(
                      //       child: Padding(
                      //         padding: EdgeInsets.all(12.0),
                      //         child:
                      // Image.network(snapshot.data[index].imgUrl),
                      //       ),
                      //       // margin: EdgeInsets.all(10.0),
                      //       decoration: BoxDecoration(
                      //         border: Border.all(
                      //             color: Colors.grey, style: BorderStyle.solid),
                      //         borderRadius: BorderRadius.all(Radius.circular(16)),
                      //       ),
                      //     ),
                      //     Align(
                      //       alignment: Alignment.bottomCenter,
                      //     child: Text(snapshot.data[index].name),
                      //     )
                      // end of trial 1
                      ButtonTheme(
                          // minWidth: 48,
                          // height: 48,
                          child: SizedBox(
                              width: 48,
                              height: 48,
                              child: ElevatedButton(
                                onPressed: () {
                                  print('pressed');
                                  // emoji_selected = true;
                                  // controller.UpdateEmoji(snapshot.data[index].id);
                                  controller
                                      .toggleEmoji(snapshot.data[index].id);
                                  // controller.toggleButton(0);
                                  // borderC = (controller.selected) ?Color(0xFF007AFF):Colors.transparent;
                                },

                                style: ElevatedButton.styleFrom(
                                  padding: EdgeInsets.symmetric(
                                      vertical: 12.0, horizontal: 12.0),
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(16),
                                    side: BorderSide(
                                        // color: Color(0xFF007AFF),
                                        //     width: 1.0,
                                        color: controller.emoji_border_color[
                                            snapshot.data[index].id]
                                        // (controller.selected_emoji_id == snapshot.data[index].id)
                                        //     ?Color(0xFF007AFF)
                                        //     :Colors.transparent,
                                        ),
                                  ),
                                  elevation: 1.0,
                                  primary: Colors.white,
                                ),

                                child:
                                    Image.network(snapshot.data[index].imgUrl),

                                // Row(
                                //     crossAxisAlignment: CrossAxisAlignment.start,
                                //     children: [
                                //       Padding(
                                //         padding: EdgeInsets.fromLTRB(0, 2, 16, 2),
                                //         child: Image.asset(
                                //             'asset/images/icon/map/with_bread.png',
                                //             height: 36,
                                //             width: 44),
                                //       ),
                                //       Text(
                                //         '커피&차와 함께 빵을\n즐길 수 있는 베이커리 카페',
                                //         // 'toggle? ${controller.selected}',
                                //         style: TextStyle(
                                //           fontSize: 16,
                                //           color: Colors.black,
                                //         ),
                                //       ),
                                //       // Text('color change'),
                                //     ])
                              ))),
                      SizedBox(height: 8),
                      Align(
                        alignment: Alignment.center,
                        child: Text(
                          snapshot.data[index].name,
                          style: TextStyle(
                            fontSize: 12,
                            fontWeight: FontWeight.w400,
                            color: Color(0xFF909090),
                          ),
                        ),
                      )
                    ],
                  ));
            }),
            onPressed: (int index) {
              setState(() {
                print('emoji select:');
                emoji_selected = !emoji_selected;

                print(emoji_selected);
                for (int buttonIndex = 0;
                    buttonIndex < isSelected.length;
                    buttonIndex++) {
                  if (buttonIndex == index) {
                    isSelected[buttonIndex] = !isSelected[buttonIndex];
                  } else {
                    isSelected[buttonIndex] = false;
                  }
                }
              });
            },
            isSelected: isSelected,
            renderBorder: false,
          );
        } else if (snapshot.hasError) {
          // print(snapshot.data[0].name);
          return Text("this: ${snapshot.error}");
        }

        // By default, show a loading spinner.
        // return CircularProgressIndicator();
        return
            // Text('err');
            Text('');
      },
    );
  }

  Future<void> getImage() async {
    List<Asset> resultList = List<Asset>();
    resultList = await MultiImagePicker.pickImages(
      // source: source,
      maxImages: 10,
      enableCamera: true,
      selectedAssets: imageList,
    );

    setState(() {
      cur_image_cnt = resultList.length;
      imageList = resultList;
    });

    for (int i = 0; i < imageList.length; i++) {
      String imagePath = await getPath(imageList[i].identifier);

      if (!(validateFileExtension(imagePath))) {
        /* TBD: action for the case when file
              of which extension is not allowed uploaded */
      }

      fileList.add(File(imagePath));
    }
  }

  Future<String> getPath(String uri) async {
    String path = await FlutterAbsolutePath.getAbsolutePath(uri);
    String fileExtension = extractFileExtension(path);
    if(isHeic(fileExtension)) {
      path = await convertHeicToJpg(path);
    }
    return path;
  }

  bool isHeic(String fileExtension) {
    if(fileExtension == 'HEIC') {
      return true;
    }
    return false;
  }

  String extractFileExtension(String uri) {
    return path_lib.extension(uri).replaceAll('.', '');
  }

  Future<String> convertHeicToJpg(String heicPath) async {
    String fileName = path_lib.basenameWithoutExtension(heicPath);
    String convertedPath =
        (await getTemporaryDirectory()).path + '/$fileName.JPG';
    return await HeicToJpg.convert(heicPath, jpgPath: convertedPath);
  }

  bool validateFileExtension(String uri) {
    String fileExtension = extractFileExtension(uri);
    if (!allowedExtensions.contains(fileExtension)) {
      return false;
    }
    return true;
  }

  Widget _createInputSignatureMenu(int index) {
    return HashTagTextField(
      controller: signatureMenuTextControllers[index],
      basicStyle: TextStyle(fontSize: 15, color: Colors.black),
      decoratedStyle: TextStyle(fontSize: 15, color: Colors.blue),
      // keyboardType: TextInputType.multiline,
      decoration: InputDecoration(
        contentPadding:
            new EdgeInsets.symmetric(vertical: 6.0, horizontal: 0.0),
        // border: OutlineInputBorder(
        //   borderSide: BorderSide(
        //     width: 5.0,
        //   )
        // ),
        // border: OutlineInputBorder(
        //   borderRadius: const BorderRadius.all(
        //     const Radius.circular(30.0),
        //   ),
        // ),
        isDense: true,
        hintText: '#시그니처메뉴${index+1}',
      ),

      /// Called when detection (word starts with #, or # and @) is being typed
      onDetectionTyped: (text) {
        print(text);
      },

      /// Called when detection is fully typed
      onDetectionFinished: () {
        print("detection finished");
      },
      // maxLines: null,
    );
  }
}

// Future<bool> upload(File file) async {
//   bool isSuccessfull = false;
//   var dio = Dio();
//   dio.options.baseUrl = "$baseUrl";
//   dio.interceptors.add(LogInterceptor(
//       requestBody: true,
//       request: true,
//       responseBody: true));
//
//   try {
//     FormData formData = FormData.from({
//       "iframeKey": "foofoo",
//       "apikey": "foo",
//       "secret": "foo",
//       "fields": [
//         {"key": "first_name", "value": "videoupload"},
//         {"key": "larst_name", "value": "videoupload"},
//         {"key": "test", "value": "videoupload"},
//         {"key": "checkboxtest", "value": "true"},
//         {"key": "email_address", "value": "somebody@gmail.com"}
//       ],
//       "file": [
//         new UploadFileInfo(new File(file.path), basename(file.path)),
//       ],
//     });
//
//     Response response;
//     response = await dio.post("/submit",
//         data: formData,
//         onSendProgress: showDownloadProgress,
//         options: new Options(
//           connectTimeout: 100000,
//           receiveTimeout: 100000,
//           contentType: ContentType.parse("application/x-www-form-urlencoded"),
//         ));
//     if (response.statusCode == 200) {
//       isSuccessfull = true;
//     }
//   } on DioError catch (e) {
//     print(e.message);
//   }
//   return isSuccessfull;
// }
class RegisterReviewPageAppbar extends DefaultAppBar {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      automaticallyImplyLeading: false,
      leading: Container(
          padding: const EdgeInsets.all(0.0),
          width: 8.0,
          height: 16.0,
          child: IconButton(
            icon: Image.asset('asset/images/Vector.png'),
            onPressed: () => Navigator.of(context).pop(),
          )),
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }
}
