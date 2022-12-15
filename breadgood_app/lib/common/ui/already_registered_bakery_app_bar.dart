import 'package:flutter/material.dart';
import 'package:flutter_email_sender/flutter_email_sender.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/common/ui/default_app_bar.dart';

class AlreadyRegisteredBakeryAppbar extends DefaultAppBar {
  AlreadyRegisteredBakeryAppbar({this.bakeryId});

  final int bakeryId;

  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: IconButton(
          icon: Container(
              height: 16,
              width: 8,
              child: SvgPicture.asset(
                'asset/images/Vector.svg',
                fit: BoxFit.scaleDown,
              )),
          onPressed: () {
            Get.toNamed('/dashboard');
          }),
      actions: [_reportButton(context)],
      backgroundColor: Colors.transparent,
      elevation: 0.0,
    );
  }

  Widget _reportButton(BuildContext context) {
    return TextButton(
      child: Text("신고/문의하기"),
      onPressed: () async {
        Email email = Email(
          body:
              '''빵긋 서비스 이용에 불편을 드려 죄송합니다. 불편 사항을 알려 주시면 관리자 확인 후 이용 약관 및 운영 원칙에 따라 조치 될 예정입니다. 
신고 빵집명 :
신고 이유 : 아래 이유 중 하나를 선택해주세요
  1. 부적절한 리뷰가 있어요
  2. 잘못된 정보에요
  3. 없어진 가게에요
  4. 기타''',
          subject: '[빵긋 문의: bakeryId: ${bakeryId.toString()}]',
          recipients: ['breadgood.official@gmail.com'],
          cc: [],
          bcc: [],
          attachmentPaths: [],
          isHTML: false,
        );

        try {
          await FlutterEmailSender.send(email);
        } catch (e, s) {
          String title =
              "기본 메일 앱을 사용할 수 없기 때문에 앱에서 바로 문의를 전송하기 어려운 상황입니다.\n\n아래 이메일로 bakeryId 를 포함해 연락주시면 친절하게 답변해드릴게요 :)\n(bakeryId:${bakeryId}):\n\nbreadgood.official@gmail.com";
          String message = "";
          showDialog(
              context: context,
              builder: (cont) => Dialog(
                    child: Container(
                      width: 300,
                      height: 300,
                      padding: EdgeInsets.all(24),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(title),
                          SizedBox(height: 24),
                          Text(message),
                        ],
                      ),
                    ),
                  ));
        }
      },
    );
  }
}
