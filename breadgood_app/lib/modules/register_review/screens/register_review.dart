import 'dart:io';
import 'dart:convert';
import 'dart:async';
import 'package:breadgood_app/modules/main/screens/main_map.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
import 'package:breadgood_app/modules/register_bakery/model/bakery_data.dart';
import 'package:breadgood_app/modules/register_bakery/screens/celebrate_register.dart';
import 'package:breadgood_app/utils/ui/colors.dart';
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

import 'package:breadgood_app/constant/api_path.dart' as api_path;
import 'package:breadgood_app/utils/services/rest_api_service.dart' as rest_api;
import 'package:breadgood_app/utils/ui/main_app_bar.dart';
import 'package:breadgood_app/modules/register_bakery/controller/bakery_controller.dart';
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

  factory Emoji.fromJson(Map<String, dynamic> json) {
    return new Emoji(
      id: json['id'] as int,
      imgUrl: json['imgUrl'] as String,
      name: json['name'] as String,
      sortNumber: json['sortNumber'] as int,
    );
  }
}

Future<http.Response> postNewBakery(BakeryMapData newBakery) async {
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
  if (response.statusCode != 200) {
    /* TBD: error should be handled */
  }
}

Future<http.Response> postReview(ReviewData newReview) async {
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
  if (response.statusCode != 200) {
    /* TBD: action for error case needed */
  }
}

class RegisterReviewPage extends StatefulWidget {
  const RegisterReviewPage({Key key}) : super(key: key);

  @override
  _RegisterReviewPageState createState() => _RegisterReviewPageState();
}

class _RegisterReviewPageState extends State<RegisterReviewPage> {
  int bakeryId = Get.arguments;
  File _image;
  int cur_image_cnt = 0;
  var textcnt = 0;
  var max_image_cnt = 10;
  bool emoji_selected = false;
  Future<List<Emoji>> emoji;
  var bakery = BakeryMapData();
  var bakeryToRegister = BakeryMapData();
  var reviewToRegister = ReviewData();
  List<Asset> imageList = List<Asset>();
  List<File> fileList = List<File>();
  List<String> signatureMenuList = [];
  List<bool> isSelected = [false, false, false, false, false];
  List<String> allowedExtensions = ["JPG", "JPEG", "PNG", "WEBP", "SVG", "GIF"];
  final reviewTextController = TextEditingController();
  List<TextEditingController> signatureMenuTextControllers =
      List.generate(3, (i) => TextEditingController());
  final controller = Get.put(BakeryController());

  @override
  void initState() {
    super.initState();
    emoji = fetchEmoji();
  }

  _onChanged(String text) {
    setState(() {
      textcnt = text.length;
    });
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
        onTap: () => FocusManager.instance.primaryFocus?.unfocus(),
        child: Scaffold(
            resizeToAvoidBottomInset: false,
            appBar: RegisterReviewPageAppbar(),
            body: GetBuilder<BakeryController>(builder: (_) {
              return SingleChildScrollView(
                  child: Padding(
                      padding: EdgeInsets.fromLTRB(31, 26, 30, 0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          ReviewIntro(),
                          SizedBox(height: 8.0),
                          ReviewEmoji(),
                          ReviewText(),
                          ReviewSinatureMenus(),
                          ReviewImages(),
                          ReviewUpload(),
                        ],
                      )));
            })));
  }

  ReviewIntro() {
    return RichText(
      text: TextSpan(
        style: TextStyle(
            color: Colors.black,
            fontSize: 16.0,
            fontFamily: 'NanumSquareRoundEB'),
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
            text: '를 들려주세요!',
          ),
        ], // buildFutureBuilder(),
      ),
    );
  }

  ReviewEmoji() {
    return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      Text("표정으로 당신의 마음을 보여주세요!",
          style: TextStyle(
            fontWeight: FontWeight.w400,
            fontSize: 14,
          )),
      /* emoji */
      Container(
        child: (emoji != null) ? buildFutureEmojiBuilder() : null,
      ),
    ]);
  }

  ReviewText() {
    return Container(
      height: 160,
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(16.0),
          color: Colors.white,
          boxShadow: [
            BoxShadow(
              color: Color(0xFF1C2F85).withOpacity(0.15),
              offset: Offset(2.0, 2.0),
              blurRadius: 10.0,
              spreadRadius: 0,
            )
          ]),
      child: TextFormField(
        controller: reviewTextController,
        onChanged: _onChanged,
        keyboardType: TextInputType.multiline,
        maxLines: null,
        minLines: 5,
        style: TextStyle(
          fontSize: 15,
        ),
        decoration: InputDecoration(
            contentPadding:
                new EdgeInsets.symmetric(vertical: 20.0, horizontal: 20.0),
            border: InputBorder.none,
            hintText: '진정한 빵덕후라면 10글자 이상 리뷰는 필수!',
            hintStyle: TextStyle(
              fontSize: 15,
            ),
            counterText: '$textcnt/500',
            counterStyle: TextStyle(
              fontSize: 16,
            )),
      ),
    );
  }

  ReviewSinatureMenus() {
    return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      Padding(
        padding: EdgeInsets.fromLTRB(0, 40, 0, 17),
        child: RichText(
          text: TextSpan(
            style: TextStyle(
                color: Colors.black,
                fontSize: 15.0,
                fontFamily: 'NanumSquareRoundEB'),
            children: <TextSpan>[
              TextSpan(
                text: '꼭 추천하고 싶은\n',
              ),
              TextSpan(
                  text: '시그니처 메뉴',
                  style: TextStyle(
                    color: Color(0xFF4579FF),
                  )),
              TextSpan(
                text: '를 해시태그로 남겨주세요!',
              ),
            ],
          ),
        ),
      ),
      Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          _createInputSignatureMenu(0),
          SizedBox(height: 17),
          _createInputSignatureMenu(1),
          SizedBox(height: 17),
          _createInputSignatureMenu(2),
        ],
      ),
    ]);
  }

  ReviewImages() {
    return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      Padding(
        padding: EdgeInsets.only(top: 40.0, bottom: 10.0),
        child: RichText(
          text: TextSpan(
            style: TextStyle(
                color: Colors.black,
                fontSize: 16.0,
                fontFamily: 'NanumSquareRoundEB'),
            children: <TextSpan>[
              TextSpan(
                text: '남기고 싶은 ',
              ),
              TextSpan(
                  text: '인증샷',
                  style: TextStyle(
                    color: Color(0xFF4579FF),
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
              fontSize: 15,
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
          SelectReviewImages();
        },
        color: Color(0xFFE5EDFF),
        elevation: 0,
      ),
      DisplayReviewImages(),
    ]);
  }

  SelectReviewImages() {
    return showCupertinoModalPopup<void>(
      context: context,
      builder: (BuildContext context) => CupertinoActionSheet(
        actions: <CupertinoActionSheetAction>[
          CupertinoActionSheetAction(
            child: const Text('카메라로 추가'),
            onPressed: () async {
              PickedFile f =
                  await ImagePicker().getImage(source: ImageSource.camera);
              File image = File(f.path);
              setState(() {
                cur_image_cnt++;
                _image = image;
              });
              Navigator.pop(
                context,
              );
            },
          ),
          CupertinoActionSheetAction(
            child: const Text('앨범에서 추가'),
            onPressed: () async {
              getImage();
              Navigator.pop(
                context,
              );
            },
          )
        ],
      ),
    );
  }

  DisplayReviewImages() {
    if (imageList.length > 0) {
      return Padding(
          padding: EdgeInsets.fromLTRB(0, 24, 0, 82),
          child: Container(
              height: 120.0,
              child: ListView(
                  scrollDirection: Axis.horizontal,
                  children: List.generate(imageList.length, (index) {
                    Asset asset = imageList[index];
                    return Padding(
                        padding: EdgeInsets.only(right: 8.0),
                        child: ClipRRect(
                            borderRadius: BorderRadius.circular(10.0),
                            child: Stack(children: <Widget>[
                              AssetThumb(
                                asset: asset,
                                width: 120,
                                height: 120,
                              ),
                              Positioned(
                                  right: -5,
                                  top: -5,
                                  child: IconButton(
                                      icon: SvgPicture.asset(
                                          'asset/images/icon/registerReview/x.svg'),
                                      iconSize: 32,
                                      onPressed: () => setState(() {
                                            imageList.removeAt(index);
                                          })))
                            ])));
                  }))));
    } else {
      return SizedBox(
        width: double.infinity,
        height: 82,
      );
    }
  }

  ReviewUpload() {
    return Flex(
      direction: Axis.vertical,
      children: [
        Padding(
          padding: EdgeInsets.only(bottom: 10),
          child: SizedBox(
            width: double.infinity,
            height: 56,
            child: RaisedButton(
                child: Text("완성!",
                    style: TextStyle(
                        color: Colors.white, fontWeight: FontWeight.w600)),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30.0),
                ),
                elevation: 0,
                color: ((textcnt > 9) &&
                        (controller.selected_emoji_id != -1) &&
                        (((bakeryId == -1) &&
                                (signatureMenuTextControllers[0]
                                    .text
                                    .isNotEmpty)) ||
                            (bakeryId != -1)))
                    ? Color(0xFF4579FF)
                    : Color(0xFFC7C7C7),
                onPressed: () {
                  /* 최초등록자는 리뷰 10자 이상, 이모지 선택, 시그니처 메뉴 1개 이상 입력 필수 */
                  if (bakeryId == -1) {
                    if ((textcnt > 9) &&
                        (controller.selected_emoji_id != -1) &&
                        (signatureMenuTextControllers[0].text.isNotEmpty)) {
                      bakeryToRegister.bakeryCategoryId =
                          controller.selected_bakery_category_id + 1;
                      bakeryToRegister.roadAddress =
                          controller.selectedBakery.roadAddress;
                      bakeryToRegister.content = reviewTextController.text;
                      for (int i = 0; i < 3; i++) {
                        if (signatureMenuTextControllers[i].text.isNotEmpty)
                          signatureMenuList.add(signatureMenuTextControllers[i]
                              .text
                              .replaceAll("#", ""));
                      }
                      bakeryToRegister.signatureMenus = signatureMenuList;
                      bakeryToRegister.emojiId = controller.selected_emoji_id;
                      bakeryToRegister.title = controller.selectedBakery.title;
                      bakeryToRegister.city =
                          controller.selectedBakery.roadAddress.split(" ")[0];
                      bakeryToRegister.description =
                          controller.selectedBakery.description;
                      bakeryToRegister.district =
                          controller.selectedBakery.roadAddress.split(" ")[1];
                      bakeryToRegister.mapX = controller.selectedBakery.mapx;
                      bakeryToRegister.mapY = controller.selectedBakery.mapy;
                      bakeryToRegister.files = fileList;
                      postNewBakery(bakeryToRegister);
                      Get.toNamed('/register_bakery/celebrate_register_page');
                    }
                  }

                  /* 추가등록자는 리뷰 10자 이상, 이모지 선택 필수 */
                  else {
                    if ((textcnt > 9) && (controller.selected_emoji_id != -1)) {
                      reviewToRegister.bakeryId = bakeryId;
                      reviewToRegister.content = reviewTextController.text;
                      for (int i = 0; i < 3; i++) {
                        if (signatureMenuTextControllers[i].text.isNotEmpty)
                          signatureMenuList.add(signatureMenuTextControllers[i]
                              .text
                              .replaceAll("#", ""));
                      }
                      reviewToRegister.signatureMenus = signatureMenuList;
                      reviewToRegister.emojiId = controller.selected_emoji_id;
                      reviewToRegister.files = fileList;
                      postReview(reviewToRegister);
                      Get.toNamed('/main');
                    }
                  }
                }),
          ),
        ),
        Padding(
            padding: EdgeInsets.only(bottom: 56),
            child: RichText(
                textAlign: TextAlign.center,
                text: TextSpan(children: [
                  WidgetSpan(
                      alignment: PlaceholderAlignment.middle,
                      child: Container(
                          margin: EdgeInsets.only(right: 4),
                          child: SvgPicture.asset(
                              'asset/images/icon/registerReview/register_review_info.svg',
                              height: 13,
                              width: 13))),
                  TextSpan(
                      text: bakeryId == -1
                          ? '표정, 리뷰 10글자 이상, 시그니처 메뉴 1개를 입력해주세요.'
                          : '표정, 리뷰 10글자 이상을 입력해주세요.',
                      style: TextStyle(
                          color: blue0700,
                          fontSize: 12,
                          fontFamily: 'AppleSDGothicNeo'))
                ])))
      ],
    );
  }

  FutureBuilder<List<Emoji>> buildFutureEmojiBuilder() {
    return FutureBuilder<List<Emoji>>(
      future: emoji,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return ToggleButtons(
            children: List<Widget>.generate(5, (index) {
              return Padding(
                  padding: EdgeInsets.fromLTRB(0, 20, 12, 18),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      ButtonTheme(
                          child: SizedBox(
                              width: 48,
                              height: 48,
                              child: ElevatedButton(
                                onPressed: () {
                                  controller
                                      .toggleEmoji(snapshot.data[index].id);
                                },
                                style: ElevatedButton.styleFrom(
                                  padding: EdgeInsets.symmetric(
                                      vertical: 12.0, horizontal: 12.0),
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(16),
                                    side: BorderSide(
                                        color: controller.emoji_border_color[
                                            snapshot.data[index].id]),
                                  ),
                                  elevation: 1.0,
                                  primary: Colors.white,
                                ),
                                child:
                                    Image.network(snapshot.data[index].imgUrl),
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
                emoji_selected = !emoji_selected;
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
          return Text("this: ${snapshot.error}");
        }

        return Text('');
      },
    );
  }

  Future<void> getImage() async {
    List<Asset> resultList = List<Asset>();
    resultList = await MultiImagePicker.pickImages(
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
    if (isHeic(fileExtension)) {
      return await convertHeicToJpg(path);
    }
    return path;
  }

  bool isHeic(String fileExtension) {
    if (fileExtension == 'HEIC') {
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

  bool validateFileExtension(String path) {
    String fileExtension = extractFileExtension(path);
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
      decoration: InputDecoration(
        contentPadding:
            new EdgeInsets.symmetric(vertical: 6.0, horizontal: 0.0),
        isDense: true,
        hintText: '#시그니처메뉴${index + 1}',
      ),

      /// Called when detection (word starts with #, or # and @) is being typed
      onDetectionTyped: (text) {
        print(text);
      },

      /// Called when detection is fully typed
      onDetectionFinished: () {
        print("detection finished");
      },
    );
  }
}

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
